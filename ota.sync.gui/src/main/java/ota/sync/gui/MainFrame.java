package ota.sync.gui;


import javax.swing.JFrame;;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * MainFrame builder class handles generating screen to LoginScreen,
 * MainScreen, and VehicleScreen.
 */
public class MainFrame
{
    /** window */
    private static JFrame window;
    /** panel */
    private JPanel panel;

    /**
     * build window.
     * @return this.
     */
    public MainFrame window()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window = new JFrame("OTA Synchronization Application");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        window.pack();
        window.setVisible(true);
        window.setSize(screenSize.width, screenSize.height);
        return this;
    }

    /**
     * build panel.
     * @param panel panel.
     * @return this.
     */
    public MainFrame panel(final JPanel panel)
    {
        this.panel = panel;
        return this;
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
     * build.
     */
    public void build()
    {
        window.add(panel);
    }

    /**
     * dispose window.
     */
    protected void dispose()
    {
        if(window != null)
        {
            window.dispose();
        }
    }

}
