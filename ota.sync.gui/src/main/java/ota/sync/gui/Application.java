package ota.sync.gui;

import ota.sync.notifier.DefaultNotifier;
import ota.sync.notifier.Listener;
import ota.sync.notifier.notification.LoginNotification;
import ota.sync.notifier.notification.VehicleNotification;

/**
 * Application class is the main class of the OTA Sync Application.
 *
 * Application class performs the following functionalities:
 * 1. Initiates and displays loginScreen class.
 * 2. Register listeners to manage screens displayed.
 * 3. Initiate application header.
 */
public class Application
{
    /** main frame */
    private static MainFrame mainFrame;
    static
    {
        new ApplicationListener();
        new ApplicationHeader();
    }

    public static void main(String[] args)
    {
        final LoginScreen loginScreen = new LoginScreen();
        mainFrame = new MainFrame();
        mainFrame.window().panel(loginScreen.getPanel());
        mainFrame.build();
    }

    /**
     * Application listener
     */
    private static class ApplicationListener implements Listener
    {
        public ApplicationListener()
        {
            new DefaultNotifier().registerNotification(this);
        }

        @Override
        public void update(final Object value)
        {
            mainFrame = new MainFrame();
            if(value instanceof LoginNotification)
            {
                final LoginNotification loginNotification = (LoginNotification) value;
                if(loginNotification.getLogin())
                {
                    final MainScreen mainScreen = new MainScreen();
                    mainScreen.setMainFrame(mainFrame);
                    mainFrame.window().panel(mainScreen.getPanel());
                    mainFrame.build();
                }
                else
                {
                    final LoginScreen loginScreen = new LoginScreen();
                    mainFrame.window().panel(loginScreen.getPanel());
                    mainFrame.build();
                }
            }

            if(value instanceof VehicleNotification)
            {
                final VehicleNotification vehicleNotification = (VehicleNotification) value;
                if(vehicleNotification.isReturnMainScreen())
                {
                    final MainScreen mainScreen = new MainScreen();
                    mainScreen.setMainFrame(mainFrame);
                    mainFrame.window().panel(mainScreen.getPanel());
                    mainFrame.build();
                }
            }
        }
    }
}
