import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    Socket socket;
    BufferedWriter out;
    NetworkRead readThread;
    //BufferedInputStream bufferedInputStream;
    public static Scanner in = new Scanner(System.in);

    public Client(Map map) {
        try {
            socket = new Socket("localhost", 12345);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            readThread = new NetworkRead(map, socket, this);
            Thread read = new Thread(readThread);
            read.start();
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to server.");
            System.exit(1);
            System.out.println(e.toString());
        }
    }


    public void send(String line) {

        try {
            out.write(line + "\n");
            out.flush();

        } catch (IOException e) {
            System.err.println("Couldn't read or write");
            System.exit(1);
        }
    }


}
