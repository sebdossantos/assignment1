/**
 * Created by Theo Theodoridis.
 * Class    : Run
 * Version  : v1.0
 * Date     : 06/05/15
 * User     : ttheod
 * email    : ttheod@gmail.com
 * Comments : Class used to launch the interface.
 **/
       //shake that ass for me
public class Run
{
   /**
    * Method     : Run::main()
    * Purpose    : Default main method used for launch the interface.
    * Parameters : - args : Initialization parameters.
    * Returns    : Nothing.
    * Notes      : None.
    **/

    public static void main(String[] args)
    {
        GUI gui = new GUI();
        serializer.SerialPort sp = new serializer.SerialPort();

        sp.initialize();

        while(true){
            gui.setData(sp.getData());
        }
    }
}
