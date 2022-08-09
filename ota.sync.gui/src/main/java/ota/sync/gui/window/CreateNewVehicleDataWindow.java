package ota.sync.gui.window;

import ota.sync.gui.Activator;
import ota.sync.gui.ScreenHelper;
import ota.sync.gui.helper.ConverterHelper;
import ota.sync.notifier.Notifier;
import ota.sync.notifier.notification.VehicleNotification;
import ota.sync.vehicle.VehicleData;
import ota.sync.vehicle.VehicleService;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * CreateNewVehicleData Window is a UI class that displays
 * vehicle data information and allows users to create new record,
 * modify a record, and delete a record.
 */
public class CreateNewVehicleDataWindow
{
    private final VehicleData vehicleData;
    private final VehicleService vehicleService;
    private final Notifier notifier;
    private static final int TEXT_SIZE = 20;
    private static final int LABEL_WIDTH = 200;
    private JTextField moduleNameTextField;
    private JTextField softwareNumberTextField;
    private JCheckBox statusCheckbox;
    private Date creationDate;
    private JPanel panel;
    private JFrame window;
    private JButton createOrModifyVehicleDataButton;
    private JButton deleteVehicleDataButton;
    private final boolean isCreatedNewRecord;
    private JLabel resultMessageLabel;

    /**
     * Constructor.
     * @param vehicleData vehicle data.
     * @param vehicleService vehicle service.
     * @param isCreatedNewRecord is created new record flag.
     */
    public CreateNewVehicleDataWindow(final VehicleData vehicleData, final VehicleService vehicleService,
                                      final boolean isCreatedNewRecord)
    {
        this.vehicleData = vehicleData;
        this.vehicleService = vehicleService;
        this.isCreatedNewRecord = isCreatedNewRecord;
        notifier = Activator.getNotifier();
        createVehicleDataFrame();
        createVehicleButtonOnClick();
        deleteVehicleDataButtonOnCLick();
        addWindowOnClosedListener();
    }

    /**
     * create vehicle data frame.
     */
    private void createVehicleDataFrame()
    {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)((int) screenSize.width);
        int height = (int)((int) screenSize.height);
        window = new JFrame();
        window = new JFrame("OTA Synchronization Application");
        panel = new JPanel(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.weightx = 0.3;
        constraints.weighty = 0.3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.ipady = 2;
        constraints.ipadx = 2;
        constraints.insets = new Insets(50, 10, 10, 10);

        final JPanel childPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints childConstraints = new GridBagConstraints();
        childConstraints.fill = GridBagConstraints.BOTH;
        childConstraints.anchor = GridBagConstraints.NORTHWEST;
        childConstraints.weightx = 0;
        childConstraints.weighty = 0;
        childConstraints.insets = new Insets(20, 20, 20, 20);
        childConstraints.gridx = 0;
        childConstraints.gridy = 0;
        final JLabel vinLabel = ScreenHelper.addLabel(String.format("Vin:  %s", vehicleData.getVin()), 200, 50, TEXT_SIZE);
        creationDate = new Date();
        final JLabel dateLabel = ScreenHelper.addLabel(String.format("Timestamp:  %s", ConverterHelper.convertDateToString(creationDate)), 200, 50, TEXT_SIZE);
        statusCheckbox = new JCheckBox("Succeed");
        statusCheckbox.setFont(new Font(Font.SERIF, Font.BOLD, TEXT_SIZE));
        statusCheckbox.setBounds(200, 200, 250, 150);
        statusCheckbox.setPreferredSize(new Dimension(200, 100));
        if(!isCreatedNewRecord)
        {
            statusCheckbox.setSelected(vehicleData.getStatus());
        }

        final JPanel softwareNumberPanel = new JPanel(new GridLayout());
        final JLabel softwareNumberLabel = ScreenHelper.addLabel("Software Number", LABEL_WIDTH, 50, TEXT_SIZE);
        softwareNumberTextField = ScreenHelper.addTextField(LABEL_WIDTH, 50, TEXT_SIZE);
        if(!isCreatedNewRecord)
        {
            softwareNumberTextField.setText(vehicleData.getSoftwareNumber());
        }
        softwareNumberPanel.add(softwareNumberLabel);
        softwareNumberPanel.add(softwareNumberTextField);

        final JPanel moduleNamePanel = new JPanel(new GridLayout());
        final JLabel moduleNameLabel = ScreenHelper.addLabel("Module Name", LABEL_WIDTH, 50, TEXT_SIZE);
        moduleNameTextField = ScreenHelper.addTextField(LABEL_WIDTH, 50, TEXT_SIZE);
        if(!isCreatedNewRecord)
        {
            moduleNameTextField.setText(vehicleData.getModuleName());
        }
        moduleNamePanel.add(moduleNameLabel);
        moduleNamePanel.add(moduleNameTextField);


        createOrModifyVehicleDataButton = ScreenHelper.addButton(isCreatedNewRecord ? "Create Record" : "Modify Record", TEXT_SIZE);
        deleteVehicleDataButton = ScreenHelper.addButton("Delete Record", TEXT_SIZE);
        resultMessageLabel = ScreenHelper.addLabel("", 200, 50, TEXT_SIZE);

        childPanel.add(vinLabel,childConstraints);
        childConstraints.gridy = 1;
        childPanel.add(dateLabel, childConstraints);
        childConstraints.gridy = 2;
        childPanel.add(statusCheckbox, childConstraints);
        childConstraints.gridy = 3;
        childPanel.add(moduleNamePanel, childConstraints);
        childConstraints.gridy = 4;
        childPanel.add(softwareNumberPanel, childConstraints);
        childConstraints.gridy = 5;
        childPanel.add(createOrModifyVehicleDataButton, childConstraints);
        childConstraints.gridy = 6;
        if(!isCreatedNewRecord)
        {
            childPanel.add(deleteVehicleDataButton, childConstraints);
            childConstraints.gridy = 7;
            childPanel.add(resultMessageLabel, childConstraints);
        }
        else
        {
            childPanel.add(resultMessageLabel, childConstraints);
        }

        panel.add(childPanel, constraints);
        window.add(panel);
        window.setPreferredSize(new Dimension(width, height));
        window.pack();
        window.setVisible(true);
    }

    /**
     * add window on closed listener.
     */
    private void addWindowOnClosedListener()
    {
        window.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(final WindowEvent e)
            {
                notifier.pushNotification(new VehicleNotification(false, true));
            }
        });
    }

    /**
     * delete vehicle data button on click.
     */
    private void deleteVehicleDataButtonOnCLick()
    {
        deleteVehicleDataButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                try
                {
                    vehicleService.deleteVehicleData(vehicleData);
                    resultMessageLabel.setText("Delete vehicle record successfully.");
                }
                catch(final Exception ex)
                {
                    resultMessageLabel.setText("Failed due to: " + ex.getMessage());
                }
            }
        });
    }

    /**
     * create vehicle button on click.
     */
    private void createVehicleButtonOnClick()
    {
        createOrModifyVehicleDataButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                try
                {

                    if(isCreatedNewRecord)
                    {
                        final VehicleData createdVehicleData = new VehicleData(vehicleData.getVin(),
                                                                               moduleNameTextField.getText(),
                                                                               softwareNumberTextField.getText(),
                                                                               statusCheckbox.isSelected(),
                                                                               creationDate, creationDate);
                        vehicleService.addVehicleData(createdVehicleData);
                        resultMessageLabel.setText("Create vehicle record successfully.");
                    }
                    else
                    {
                        final VehicleData createdVehicleData = new VehicleData(vehicleData.getVin(),
                                                                               moduleNameTextField.getText(),
                                                                               softwareNumberTextField.getText(),
                                                                               statusCheckbox.isSelected(),
                                                                               creationDate, vehicleData.getCreationTime());
                        vehicleService.modifyVehicleData(createdVehicleData);
                        resultMessageLabel.setText("Modify vehicle record successfully.");
                    }
                }
                catch(final Exception ex)
                {
                    resultMessageLabel.setText("Failed due to: " + ex.getMessage());
                }
            }
        });
    }
}
