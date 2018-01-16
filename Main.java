import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import static java.lang.System.out;

class ServerReader extends Thread {

    DataInputStream serverInput;

    public ServerReader(DataInputStream serverInput)
    {
        this.serverInput = serverInput;
    }

    @Override
    public void run() {
        try {
            while (true) {
                out.print(serverInput.readUTF());
            }
        } catch (IOException e) {
            out.println("[Client] : message read error");
            System.exit(1);
        }
    }
}

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Socket socket;
    public static DataInputStream serverInput;
    public static DataOutputStream serverOutput;

    public static void main(String [] args) {

        out.print("Set user name : ");
        String userName = scanner.nextLine();

        try {
            socket = new Socket("localhost", 1111);
            serverInput = new DataInputStream(socket.getInputStream());
            serverOutput = new DataOutputStream(socket.getOutputStream());

            out.println("Connected");
            serverOutput.writeUTF(userName);

            ServerReader serverReader = new ServerReader(serverInput);
            serverReader.start();

            String message;

            while (true) {
                message = scanner.nextLine();

                if (message.equals("EXIT"))
                    break;

                serverOutput.writeUTF(message);
            }

            socket.close();
            serverOutput.close();
            serverInput.close();
            serverReader.interrupt();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.println("Disconnected");
        }
    }
}
