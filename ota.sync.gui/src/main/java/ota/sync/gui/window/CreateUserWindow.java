package ota.sync.gui.window;

import ota.sync.gui.Activator;
import ota.sync.gui.ScreenHelper;
import ota.sync.user.UserService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CreateUserWindow class to display user interface which allows
 * users to create a new user.
 */
public class CreateUserWindow
{
    private final UserService userService;
    private static final int TEXT_SIZE = 30;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton createUserButton;
    private JLabel errorLabel;
    private JFrame window;

    /**
     * Constructor
     */
    public CreateUserWindow()
    {
        userService = Activator.getUserService();
        createUserFrame();
        createUserButtonOnClick();
    }

    /**
     * create user frame.
     */
    private void createUserFrame()
    {
        window = new JFrame();
        window = new JFrame("OTA Synchronization Application");
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(300, 300));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.weightx = 0.3;
        constraints.weighty = 0.3;
        constraints.gridx = 1;
        constraints.ipady = 3;
        constraints.ipadx = 3;
        constraints.insets = new Insets(250, 80, 400, 60);
        final JPanel childPanel = new JPanel(new GridBagLayout());
        final GridBagConstraints childConstrains = new GridBagConstraints();
        childConstrains.fill = GridBagConstraints.BOTH;
        childConstrains.anchor = GridBagConstraints.NORTHWEST;

        childConstrains.weightx = 0.2;
        childConstrains.weighty = 0.3;
        childConstrains.gridx = 0;
        childConstrains.gridy = 0;

        final JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        usernamePanel.setPreferredSize(new Dimension(50, 100));
        final JLabel usernameLabel = ScreenHelper.addLabel("Username:", 200, 50, TEXT_SIZE);
        usernameTextField = ScreenHelper.addTextField(400, 40, TEXT_SIZE);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        childPanel.add(usernamePanel, childConstrains);

        final JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        passwordPanel.setPreferredSize(new Dimension(50, 100));
        final JLabel passwordLabel = ScreenHelper.addLabel("Password:", 200, 50, TEXT_SIZE);
        passwordTextField = ScreenHelper.addTextField(400, 40, TEXT_SIZE);
        childConstrains.gridx = 0;
        childConstrains.gridy = 1;
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);
        childPanel.add(passwordPanel, childConstrains);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        childConstrains.gridx = 0;
        childConstrains.gridy = 2;
        createUserButton = ScreenHelper.addButton("Create User", TEXT_SIZE);
        createUserButton.setPreferredSize(new Dimension(200, 50));
        buttonPanel.add(createUserButton);
        childPanel.add(buttonPanel, childConstrains);

        final JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        childConstrains.gridx = 0;
        childConstrains.gridy = 3;

        errorLabel = ScreenHelper.addLabel("", 800, 50, TEXT_SIZE);
        errorLabel.setFont(new Font(Font.SERIF, Font.ITALIC, TEXT_SIZE));
        errorLabel.setForeground(Color.red);
        errorPanel.add(errorLabel);
        childPanel.add(errorPanel, childConstrains);

        panel.add(childPanel, constraints);
        window.add(panel);
        window.setPreferredSize(new Dimension(1000, 1000));
        window.pack();
        window.setVisible(true);
    }

    /**
     * create user button on click.
     */
    private void createUserButtonOnClick()
    {
        createUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                errorLabel.setText("");
                try
                {
                    userService.createUser(usernameTextField.getText(), passwordTextField.getText());
                    errorLabel.setText("Successfully create user.");
                }
                catch(final Exception ex)
                {
                    errorLabel.setText(ex.getMessage());
                }
            }
        });
    }
}
