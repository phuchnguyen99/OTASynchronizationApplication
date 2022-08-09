package ota.sync.gui;

import ota.sync.notifier.Notifier;
import ota.sync.user.UserService;
import ota.sync.vehicle.VehicleData;
import ota.sync.vehicle.VehicleService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;;

/**
 * MainScreen class is to search vin.
 */
public class MainScreen extends MainFrame
{
    private final VehicleService vehicleService;
    private final UserService userService;
    private final static int TEXT_SIZE = 30;
    private JPanel panel;
    private JButton searchVinButton;
    private JLabel createUserLabel;
    private JLabel logoutLabel;
    private JTextField vinTextField;
    private MainFrame mainFrame;
    private Notifier notifier;
    private JLabel errorLabel;

    /**
     * Constructor.
     */
    public MainScreen()
    {
        vehicleService = Activator.getVehicleService();
        notifier = Activator.getNotifier();
        userService = Activator.getUserService();
        createScreenLayout();
        onClickSearchButton();
    }

    /**
     * create screen layout.
     */
    public void createScreenLayout()
    {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        constraints.gridx = 0;
        constraints.gridwidth = 1;

        final JPanel childPanel = new JPanel(new GridBagLayout());
        final JPanel headerPanel = ApplicationHeader.getHeaderPanel();
        createUserLabel = ApplicationHeader.getCreateUserLabel();
        logoutLabel = ApplicationHeader.getLogoutLabel();
        logoutLabel.setVisible(true);


        final JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel1.setPreferredSize(new Dimension(1000, 500));
        final JLabel jLabel = ScreenHelper.addLabel("Enter Vin: ", 300 , 50, TEXT_SIZE);
        vinTextField = ScreenHelper.addTextField(300, 40, TEXT_SIZE);
        searchVinButton = ScreenHelper.addButton("Search vin", TEXT_SIZE, 200, 40);

        final JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        errorLabel = ScreenHelper.addLabel("", 1000, 100, TEXT_SIZE);
        errorLabel.setFont(new Font(Font.SERIF, Font.ITALIC, TEXT_SIZE));
        errorLabel.setForeground(Color.red);
        errorPanel.add(errorLabel);

        childPanel.add(headerPanel);
        panel1.add(jLabel);
        panel1.add(vinTextField);
        panel1.add(searchVinButton);
        panel1.add(errorPanel);

        panel.add(childPanel);
        panel.add(panel1, constraints);

    }

    /**
     * set main frame.
     * @param mainFrame main frame.
     */
    public void setMainFrame(final MainFrame mainFrame)
    {
       this.mainFrame = mainFrame;
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
     * on click search button.
     */
    private void onClickSearchButton()
    {
        searchVinButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                errorLabel.setText("");
                try
                {
                    final List<VehicleData> vehicleDataList = vehicleService.getVehicleData(vinTextField.getText());
                    final VehicleScreen vehicleScreen = new VehicleScreen(vehicleDataList);
                    dispose();
                    mainFrame.window().panel(vehicleScreen.getPanel());
                    mainFrame.build();
                }
                catch(final Exception ex)
                {
                    errorLabel.setText(ex.getMessage());
                }

            }
        });
    }
}
