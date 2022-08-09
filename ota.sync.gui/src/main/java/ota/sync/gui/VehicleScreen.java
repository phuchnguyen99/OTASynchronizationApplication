package ota.sync.gui;

import ota.sync.gui.helper.ConverterHelper;
import ota.sync.gui.window.CreateNewVehicleDataWindow;
import ota.sync.notifier.DefaultNotifier;
import ota.sync.notifier.Listener;
import ota.sync.notifier.Notifier;
import ota.sync.notifier.notification.VehicleNotification;
import ota.sync.vehicle.VehicleData;
import ota.sync.vehicle.VehicleService;
import ota.sync.vehicle.exception.VehicleServiceException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

/**
 * VehicleScreen class handles
 * 1. Display vehicle data table and functional buttons.
 * 2. Interact with VehicleService to perform associated funcationalities.
 * 3. Listen to CreateNewVehicleWindow notification.
 */
public class VehicleScreen extends MainFrame
{
    private static final int TEXT_SIZE = 20;
    private final VehicleService vehicleService;
    private JButton exportFileButton;
    private JButton createNewVehicleDataButton;
    private JButton refreshDataButton;
    private JButton returnMainScreenButton;
    private JPanel panel;
    private JLabel modifiedLabel;
    private final Notifier notifier;
    private List<VehicleData> vehicleDataList;
    private final String[] columnHeaders = new String[]{"VIN", "Module", "Software", "Status", "Update time", "Creation Time", ""};
    private static final int WIDTH;
    private static final int HEIGHT;
    static
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        WIDTH = (int)((int) screenSize.width/3);
        HEIGHT = (int)((int) screenSize.height/3);
    }

    /**
     * Constructor.
     * @param vehicleDataList vehicle data list.
     */
    public VehicleScreen(final List<VehicleData> vehicleDataList)
    {
        vehicleService = Activator.getVehicleService();
        notifier = Activator.getNotifier();
        this.vehicleDataList = vehicleDataList;
        createLayout();
        returnMainScreenButtonOnClick();
        createNewVehicleDataButtonOnClick();
        exportFileButtonOnClick();
        refreshDataButtonOnClick();
        new VehicleScreenListener();

    }

    /**
     * create layout.
     */
    public void createLayout()
    {
        panel = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.ipadx = 200;
        constraints.ipady = 200;
        constraints.insets = new Insets(50, 80, 0, 50);
        ApplicationHeader.getLogoutLabel().setVisible(true);
        panel.add(ApplicationHeader.getHeaderPanel(),constraints);


        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.insets = new Insets(0, 100, 50, 50);
        final JPanel functionalPanel = new JPanel();
        final BoxLayout boxLayout = new BoxLayout(functionalPanel, BoxLayout.X_AXIS);
        functionalPanel.setLayout(boxLayout);
        refreshDataButton = ScreenHelper.addButton("Refresh Data", TEXT_SIZE);
        createNewVehicleDataButton = ScreenHelper.addButton("Create New Record", TEXT_SIZE);
        exportFileButton = ScreenHelper.addButton("Export File", TEXT_SIZE);
        returnMainScreenButton = ScreenHelper.addButton("Return Main Screen", TEXT_SIZE);

        functionalPanel.add(refreshDataButton);
        functionalPanel.add(Box.createRigidArea(new Dimension(30, 30)));
        functionalPanel.add(createNewVehicleDataButton);
        functionalPanel.add(Box.createRigidArea(new Dimension(30, 30)));
        functionalPanel.add(exportFileButton);
        functionalPanel.add(Box.createRigidArea(new Dimension(30, 30)));
        functionalPanel.add(returnMainScreenButton);
        panel.add(functionalPanel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.ipadx = WIDTH;
        constraints.ipady = HEIGHT;
        constraints.gridwidth = 3;

        final Object[][] tableData = convertVehicleDataToTableModel();

        final JTable vehicleDataTable = new JTable(tableData, columnHeaders);
        vehicleDataTable.getTableHeader().setFont(new Font(Font.SERIF, Font.BOLD, 20));
        vehicleDataTable.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        vehicleDataTable.setRowHeight(50);
        vehicleDataTable.setPreferredScrollableViewportSize(new Dimension(500, 800));
        vehicleDataTable.setFillsViewportHeight(true);
        vehicleDataTable.setBounds(5, 18, 884, 194);
        vehicleDataTable.setEnabled(true);
        vehicleDataTable.setCellSelectionEnabled(true);
        vehicleDataTable.getColumn("").setCellRenderer(new LabelRender());

        final ListSelectionModel selectionModel = vehicleDataTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        vehicleDataTable.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                super.mousePressed(e);
                new CreateNewVehicleDataWindow(vehicleDataList.get(vehicleDataTable.getSelectedRow()),
                                               vehicleService, false);
            }
        });

        JScrollPane jScrollPane = new JScrollPane(vehicleDataTable);
        jScrollPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.add(jScrollPane, constraints);

    }

    /**
     * get panel.
     * @return panel.
     */
    public JPanel getPanel()
    {
        return panel;
    }

    /**
     * convert vehicle data to table model.
     * @return table model object.
     */
    private Object[][] convertVehicleDataToTableModel()
    {
        Object[][] tableModel = new Object[vehicleDataList.size()][7];
        for(int i = 0; i < vehicleDataList.size(); i++)
        {
            final VehicleData vehicleData = vehicleDataList.get(i);
            tableModel[i][0] = vehicleData.getVin();
            tableModel[i][1] = vehicleData.getModuleName();
            tableModel[i][2] = vehicleData.getSoftwareNumber();
            tableModel[i][3] = ConverterHelper.convertStatusBooleanToString(vehicleData.getStatus());
            tableModel[i][4] = ConverterHelper.convertDateToString(vehicleData.getTimestamp());
            tableModel[i][5] = ConverterHelper.convertDateToString(vehicleData.getCreationTime());
        }
        return tableModel;
    }

    /**
     * return main screen button on click.
     */
    private void returnMainScreenButtonOnClick()
    {
        returnMainScreenButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                dispose();
                notifier.pushNotification(new VehicleNotification(true, false));
            }
        });
    }

    /**
     * create new vehicle data button on click.
     */
    private void createNewVehicleDataButtonOnClick()
    {
        createNewVehicleDataButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                new CreateNewVehicleDataWindow(vehicleDataList.get(0), vehicleService, true);
            }
        });
    }

    /**
     * export file button on click.
     */
    private void exportFileButtonOnClick()
    {
        exportFileButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                try
                {
                    final File file = vehicleService.exportVehicleData(vehicleDataList);
                    final String message = String.format("file is successfully saved at %s", file.getCanonicalPath());
                    showPopupMessage(message);

                }
                catch(final Exception ex)
                {
                   showPopupMessage(ex.getMessage());
                }
            }
        });
    }

    /**
     * show popup message.
     * @param message message.
     */
    private void showPopupMessage(final String message)
    {
        final JLabel fileLabel = ScreenHelper.addLabel(message, 800, 100, 20);
        final JPanel filePopupPanel = new JPanel();
        filePopupPanel.setPreferredSize(new Dimension(800, 150));
        filePopupPanel.add(fileLabel);
        JOptionPane.showConfirmDialog(null, filePopupPanel);
    }

    /**
     * refresh data button on click.
     */
    private void refreshDataButtonOnClick()
    {
        refreshDataButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                try
                {
                   vehicleDataList = vehicleService.refreshVehicleData(vehicleDataList.get(0).getVin());
                   dispose();
                   VehicleScreen.super.window().panel(new VehicleScreen(vehicleDataList).getPanel());
                   build();

                }
                catch(final Exception ex)
                {
                    showPopupMessage(ex.getMessage());
                }
            }
        });
    }

    /**
     * Label Render class to add Modify label to the table cell.
     */
    private class LabelRender extends JLabel implements TableCellRenderer
    {

        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected, final boolean hasFocus,
                                                       final int row, final int column)
        {
            modifiedLabel = ScreenHelper.addLabel("Modify", 200, 10, TEXT_SIZE);
            modifiedLabel.setFont(new Font(Font.SERIF, Font.ITALIC, TEXT_SIZE));
            modifiedLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            modifiedLabel.setForeground(Color.BLUE.darker());
            return modifiedLabel;
        }
    }

    /**
     * VehicleScreenListener to listen to notifications and handle accordingly.
     */
    private class VehicleScreenListener implements Listener
    {
        /**
         * Constructor.
         */
        private VehicleScreenListener()
        {
            new DefaultNotifier().registerNotification(this);
        }

        @Override
        public void update(final Object value)
        {
            if(value instanceof VehicleNotification)
            {
                final VehicleNotification vehicleNotification = (VehicleNotification)value;
                if(vehicleNotification.isVehicleDataUpdated())
                {
                    try
                    {
                        vehicleDataList = vehicleService.getVehicleData(vehicleDataList.get(0).getVin());
                        dispose();
                        VehicleScreen.super.window().panel(new VehicleScreen(vehicleDataList).getPanel());
                        build();
                    }
                    catch(final VehicleServiceException e)
                    {
                        showPopupMessage(e.getMessage());
                    }
                }
            }
        }
    }
}

