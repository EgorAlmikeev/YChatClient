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
                out.println(serverInput.readUTF());
            }
        } catch (IOException e) {
            out.println("[Client] : message read error");
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

            MenuAlgorithm();

        } catch (IOException e) {

        } finally {
            out.println("Disconnected");
        }
    }

    public static void MenuAlgorithm() throws IOException {
        String answer;

        while (!socket.isClosed()) {
            out.println();
            out.println();
            out.println(serverInput.readUTF());         //getting default message
            out.print("set : ");
            serverOutput.writeUTF(scanner.nextLine());  //sending command
            out.println();
            answer = serverInput.readUTF();             //getting answer

            if (answer.equals("START CHAT"))
                ChatAlgorithm();
            else out.println(answer);
        }
    }

    public static void ChatAlgorithm() throws IOException {

//        ServerReader serverReader = new ServerReader(serverInput);
//        serverReader.start();
//        serverOutput.writeUTF(scanner.nextLine());
    }
}
