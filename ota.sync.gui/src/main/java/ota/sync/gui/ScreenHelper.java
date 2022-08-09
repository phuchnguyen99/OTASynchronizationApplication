package ota.sync.gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Screen Helper utility class handles initiations
 * components such as label, text fields, button.
 */
public class ScreenHelper
{
    private static final String FONT = "Serif";

    /**
     * private constructor to avoid
     * initiation of the class.
     */
    private ScreenHelper()
    {

    }

    /**
     * add label.
     * @param labelStr label string.
     * @param width width.
     * @param height height.
     * @param textSize text size.
     * @return jlabel.
     */
    public static JLabel addLabel(final String labelStr, final int width, final int height,  final int textSize)
    {
        final JLabel label = new JLabel(labelStr);
        label.setPreferredSize(new Dimension(width, height));
        label.setFont(new Font(FONT, Font.BOLD, textSize));
        return label;
    }

    /**
     * add text field.
     * @param width width.
     * @param height height.
     * @param textSize text size.
     * @return text field.
     */
    public static JTextField addTextField(final int width, final int height, final int textSize)
    {
        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(width, height));
        textField.setFont(new Font(FONT, Font.BOLD, textSize));
        return textField;
    }

    /**
     * add button.
     * @param buttonText button text.
     * @param textSize text size
     * @return button.
     */
    public static JButton addButton(final String buttonText, final int textSize)
    {
        final JButton button = new JButton(buttonText);
        button.setFont(new Font(FONT, Font.BOLD, textSize));
        return button;
    }

    /**
     * add button.
     * @param buttonText button text.
     * @param textSize text size.
     * @param width width.
     * @param height height.
     * @return button.
     */
    public static JButton addButton(final String buttonText, final int textSize, final int width, final int height)
    {
        final JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(width, height));
        button.setFont(new Font(FONT, Font.BOLD, textSize));
        return button;
    }
}
