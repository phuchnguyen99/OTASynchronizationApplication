package ota.sync.gui;

import ota.sync.notifier.Notifier;
import ota.sync.notifier.notification.LoginNotification;
import ota.sync.user.UserService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginScreen class handles LogIn Screen UserInterface
 * 1. Initiate LoginScreen UI.
 * 2. Push notification when user login.
 */
public class LoginScreen extends MainFrame
{
    /** user service */
    private final UserService userService;
    /** text size */
    private final static int TEXT_SIZE = 30;
    /** label width */
    private final static int LABEL_WIDTH = 500;
    /** text field width */
    private final static int TEXTFIELD_WIDTH = 500;
    /** components height */
    private final static int HEIGHT = 50;
    /** panel */
    private JPanel panel;
    /** login button */
    private JButton loginButton;
    /** username text field */
    private JTextField usernameTextField;
    /** password textfield */
    private JPasswordField passwordTextField;
    /** error label */
    private JLabel errorLabel;
    /** notifier */
    private final Notifier notifier;
    /** succeed login */
    private boolean succeedLogin;
    /** screen size */
    private final static  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Constructor.
     */
    public LoginScreen()
    {
        userService = Activator.getUserService();
        notifier = Activator.getNotifier();
        createScreenLayOut();
        onLoginButtonClick();
    }

    /**
     * create screen layout.
     */
    public void createScreenLayOut()
    {
        panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.weightx = 0.3;
        constraints.weighty = 0.3;
        constraints.gridx = 1;
        constraints.ipady = 3;
        constraints.ipadx = 3;
        constraints.insets = new Insets(150, 200, 50, 300);


        final JPanel childPanel = new JPanel(new GridBagLayout());
        childPanel.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        final GridBagConstraints childConstrains = new GridBagConstraints();
        childConstrains.fill = GridBagConstraints.BOTH;
        childConstrains.anchor = GridBagConstraints.NORTHWEST;
        childConstrains.weightx = 0.2;
        childConstrains.weighty = 0.2;
        childConstrains.gridx = 0;
        childConstrains.gridy = 1;
        final JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        usernamePanel.setPreferredSize(new Dimension(300, HEIGHT));
        final JLabel usernameLabel = ScreenHelper.addLabel("Username:", LABEL_WIDTH, HEIGHT, TEXT_SIZE);
        usernameTextField = ScreenHelper.addTextField(TEXTFIELD_WIDTH, HEIGHT, TEXT_SIZE);

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameTextField);
        childPanel.add(usernamePanel, childConstrains);

        final JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        passwordPanel.setPreferredSize(new Dimension(300, HEIGHT));
        final JLabel passwordLabel = ScreenHelper.addLabel("Password:", LABEL_WIDTH, HEIGHT, TEXT_SIZE);
        passwordTextField = new JPasswordField();
        passwordTextField.setPreferredSize(new Dimension(TEXTFIELD_WIDTH, HEIGHT));
        passwordTextField.setFont(new Font(Font.SERIF, Font.BOLD, TEXT_SIZE));
        childConstrains.gridx = 0;
        childConstrains.gridy = 3;
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);
        childPanel.add(passwordPanel, childConstrains);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        childConstrains.gridx = 0;
        childConstrains.gridy = 4;
        loginButton = ScreenHelper.addButton("Login", TEXT_SIZE);
        loginButton.setPreferredSize(new Dimension(LABEL_WIDTH, HEIGHT));
        buttonPanel.add(loginButton);
        childPanel.add(buttonPanel, childConstrains);

        final JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        childConstrains.gridx = 0;
        childConstrains.gridy = 5;

        errorLabel = ScreenHelper.addLabel("", 800, 50, TEXT_SIZE);
        errorLabel.setFont(new Font(Font.SERIF, Font.ITALIC, TEXT_SIZE));
        errorLabel.setForeground(Color.red);
        errorPanel.add(errorLabel);
        childPanel.add(errorPanel, childConstrains);

        panel.add(childPanel, constraints);
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
     * on login button click.
     */
    private void onLoginButtonClick()
    {
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                try
                {
                    succeedLogin = userService.login(usernameTextField.getText(), passwordTextField.getText());
                    dispose();
                    notifier.pushNotification(new LoginNotification(true));
                }
                catch(final Exception ex)
                {
                    errorLabel.setText(ex.getMessage());
                }
            }
        });
    }
}
