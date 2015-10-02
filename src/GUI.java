import javax.swing.*;
import java.awt.*;

/**
 * Created by Theo Theodoridis.
 * Class    : GUI
 * Version  : v1.0
 * Date     : 06/05/15
 * User     : ttheod
 * email    : ttheod@gmail.com
 * Comments : Class that creates a Graphical Users Interface.
 **/

public class GUI
{
    public static final int IMAGE_COLUMN = 4;
    public static final int IMAGE_LINE   = 4;

    // [+]Graphical components:
    private JPanel panel;
    private JPanel imagePanel;
    private JSlider slider_L_IR;
    private JSlider slider_R_IR;
    private JSlider slider_fusion;
    private JSlider slider_motor;
    private JTextPane textPane;
    private JPanel tablePanel[][];

    private JFrame frame;
    private byte data[];

   /**
    * Method     : GUI::GUI()
    * Purpose    : Constructor of the GUI class.
    * Parameters : None.
    * Returns    : Nothing.
    * Notes      : None.
    **/
    public GUI()
    {
        start();

    }

   /**
    * Method     : GUI::start()
    * Purpose    : Configure and show the graphical interface.
    * Parameters : None.
    * Returns    : Nothing.
    * Notes      : None.
    **/
    public void start()
    {
        // [+]Initialisation of the camera's picture:
        tablePanel = new JPanel[IMAGE_LINE][IMAGE_COLUMN];
        //imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(IMAGE_LINE, IMAGE_COLUMN));
        for(int i=0 ; i<IMAGE_LINE   ; i++)
        for(int j=0 ; j<IMAGE_COLUMN ; j++)
        {
            tablePanel[i][j] = new JPanel();
            tablePanel[i][j].setBackground(new Color((i+j)*18, 0, 0));
            imagePanel.add(tablePanel[i][j]);
        }

        // [+]Configure and show the frame:
        frame = new JFrame("GUI");
        frame.setContentPane(this.panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public void setData(byte data[]){
        this.data = data;
        System.out.print("setData");
        System.out.print(data+", ");
    }
}
