package serializer;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.SerialPortEvent;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEventListener;

/**
 * Created by Theo Theodoridis.
 * Class    : SerialPort
 * Version  : v1.0
 * Date     : © Copyright 27-Jul-2012
 * User     : ttheod
 * email    : ttheod@gmail.com
 * Comments : This class reads/writes data bytes from the serial port.
 **/

public class SerialPort implements SerialPortEventListener
{
	private byte data[] = null;
	private gnu.io.SerialPort serialPort;
	private static final String PORT_NAMES[] =
    {
        //"/dev/tty.usbserial-A9007UX1", // Mac OS X
        //"/dev/ttyUSB0",                // Linux
        "COM12"                         // Windows
    };

	private InputStream input;                 // Buffered input stream from the port.
	private OutputStream output;               // The output stream to the port.
	private static final int TIME_OUT = 2000;  // Milliseconds to block while waiting for port open.
	private static final int DATA_RATE = 9600; // Default bits per second for COM port.

   /**
    * Method     : SerialPort::SerialPort()
    * Purpose    : Default SerialPort class constructor.
    * Parameters : None.
    * Returns    : Nothing.
    * Notes      : None.
    **/
    public SerialPort()
    {

    }

   /**
    * Method     : SerialPort::initialize()
    * Purpose    : To initialize the serial port.
    * Parameters : None.
    * Returns    : Nothing.
    * Notes      : None.
    **/
	public void initialize()
    {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// [+]Iterate through, looking for the port.
		while(portEnum.hasMoreElements())
        {
			CommPortIdentifier currPortId = (CommPortIdentifier)portEnum.nextElement();
            for(String portName : PORT_NAMES)
            {
                if(currPortId.getName().equals(portName))
                {
                    portId = currPortId;
                    break;
                }
            }
		}

		if(portId == null)
        {
			System.out.println("Could not find COM port.");
			return;
		}
		try
        {
			// [+]Open serial port, and use class name for the appName.
			serialPort = (gnu.io.SerialPort)portId.open(this.getClass().getName(), TIME_OUT);

			// [+]Set port parameters.
			serialPort.setSerialPortParams(DATA_RATE, gnu.io.SerialPort.DATABITS_8, gnu.io.SerialPort.STOPBITS_1, gnu.io.SerialPort.PARITY_NONE);

			// [+]Open the streams.
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// [+]Add event listeners.
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		}
        catch (Exception e)
        {
			System.err.println(e.toString());
		}
	}

   /**
    * Method     : SerialPort::close()
    * Purpose    : To prevent port locking on platforms like Linux.
    * Parameters : None.
    * Returns    : Nothing.
    * Notes      : This method should be called when you stop using the port.
    **/
	private synchronized void close()
    {
		if(serialPort != null)
        {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

   /**
    * Method     : SerialPort::serialEvent()
    * Purpose    : To handle events on the serial port.
    * Parameters : - oEvent : The serial event.
    * Returns    : Nothing.
    * Notes      : None.
    **/
	public synchronized void serialEvent(SerialPortEvent oEvent)
    {
		if(oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
			try
            {
				// [+]Acquire bytes from serial port.
                int available = input.available();
				data = new byte[available];
				input.read(data, 0, available);

                // [+]Read incoming bytes:
                //data[i] ...
                for(int i=0; i<data.length; i++){
                    System.out.print(data[i]+", ");
                }
			}
            catch(Exception e)
            {
				System.err.println(e.toString());
			}
		}
	}

   /**
    * Method     : SerialPort::rxByte()
    * Purpose    : Receive bytes from serial port.
    * Parameters : None.
    * Returns    : A byte array.
    * Notes      : None.
    **/
//    public byte[] rxByte()
//    {
//        return((data == null) ? new byte[0] : data);
//    }

   /**
    * Method     : SerialPort::txByte()
    * Purpose    : Send a byte to serial port.
    * Parameters : - x : The byte to send.
    * Returns    : Nothing.
    * Notes      : None.
    **/
    public void txByte(byte x)
    {
        try
        {
            output.write(x);
        }
        catch(Exception e)
        {
            System.err.println("SerialPort::sendByte error : " + e.toString());
        }
    }

   /**
    * Method     : Delay::delayMs()
    * Purpose    : To delay in milliseconds (ms).
    * Parameters : - ms : The milliseconds scalar.
    * Returns    : Nothing.
    * Notes      : None.
    **/
    public static void delayMs(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(Exception e)
        {
            System.out.println("Exception<Delay>: " + e);
        };
    }

   /**
    * Method     : SerialPort::main()
    * Purpose    : Default main method used for testing the SerialPort class.
    * Parameters : - args : Initialization parameters.
    * Returns    : Nothing.
    * Notes      : None.
    **/
	public static void main(String args[]) throws Exception
    {
		SerialPort sp = new SerialPort();
		sp.initialize();
	}
}
