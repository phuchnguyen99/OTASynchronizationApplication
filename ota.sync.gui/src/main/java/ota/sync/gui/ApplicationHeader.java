package ota.sync.gui;

import ota.sync.gui.window.CreateUserWindow;
import ota.sync.notifier.Notifier;
import ota.sync.notifier.notification.LoginNotification;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * ApplicationHeader class handles createUser and logout
 * functionalities throughout the application.
 */
public class ApplicationHeader extends MainFrame
{
    static JLabel createUserLabel;
    static JLabel logoutLabel;
    static JPanel headerPanel;
    private final Notifier notifier;
    private static final int TEXT_SIZE = 30;

    /**
     * Constructor.
     */
    public ApplicationHeader()
    {
        notifier = Activator.getNotifier();
    }
    static
    {
        headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        createUserLabel = ScreenHelper.addLabel("Create User", 200, 50, TEXT_SIZE);
        createUserLabel.setForeground(Color.BLUE.darker());
        createUserLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        logoutLabel = ScreenHelper.addLabel("Log out", 200, 50, TEXT_SIZE);
        logoutLabel.setForeground(Color.BLUE.darker());
        logoutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        headerPanel.add(createUserLabel);
        headerPanel.add(logoutLabel);

    }
    {
        onClickCreateUserLabel();
        onClickLogoutLabel();
    }

    /**
     * get create user label.
     * @return create user label.
     */
    static JLabel getCreateUserLabel()
    {
        return createUserLabel;
    }

    /**
     * get logout label.
     * @return logout label.
     */
    static JLabel getLogoutLabel()
    {
        return logoutLabel;
    }

    /**
     * get header panel.
     * @return header panel.
     */
    static JPanel getHeaderPanel()
    {
        return headerPanel;
    }

    /**
     * on click create user label.
     */
    private void onClickCreateUserLabel()
    {
        createUserLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                super.mousePressed(e);
                new CreateUserWindow();
            }
        });
    }

    /**
     * on click logout label.
     */
    private void onClickLogoutLabel()
    {
        logoutLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                super.mousePressed(e);
                dispose();
                notifier.pushNotification(new LoginNotification(false));
            }
        });
    }
}
