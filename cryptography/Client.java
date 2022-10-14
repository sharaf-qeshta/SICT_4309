package SICT_4309.cryptography;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * this class will do the user operations for sending and receiving
 * messages.
 * for now we hardcoded the rsa data for simplicity.
 * the server can`t see the messages between the users ("clients")
 * see rsa.gif for testing
 * */
public class Client
{
    private Socket socket;
    private String username;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    /**
     * this object will hold
     * the rsa data
     * with
     * N : 11303
     * public key: 5
     * Private key: 6653
     * */
    private static final RSA R_S_A = new RSA(11303, 5, 6653);


    public Client(Socket socket, String username)
    {
        try
        {
            this.socket = socket;
            this.username = username;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (Exception exception)
        {
            close();
        }
    }

    /**
     * this method will run on the main thread
     * and it will keep reading messages from
     * the keyboard and send it to the server
     * */
    public void sendMessage()
    {
        try
        {
            // send the username to the server
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            /*
            * while the server is running keep reading messages
            * from the keyboard and send it to the server.
            * the server will redirect this message to
            * every active user.
            * but before sending the message to the server
            * we encrypt it using
            * our public key and then every receiver of this
            * message will decrypt it using the same private key
            * */
            while (socket.isConnected())
            {
                String message = scanner.nextLine();
                message = R_S_A.encrypt("(" + username + ") " + message);
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch (Exception exception)
        {
            close();
        }
    }

    /**
     * this method will run on a separate
     * thread and it will keep listen for the incoming
     * messages from different active users
     * */
    public void listen()
    {
        new Thread(() ->
        {
            /*
             * while the server is functioning
             * keep reading the incoming messages
             * and decrypt it using the private key
             * which is the same for all
             * clients.
             * */
            while (socket.isConnected())
            {
                try
                {
                    String message = bufferedReader.readLine();
                    message = R_S_A.decrypt(message);
                    System.out.println(message);
                }
                catch (Exception exception)
                {
                    close();
                }
            }
        }).start();
    }


    public void close()
    {
        try
        {
            socket.close();
            bufferedWriter.close();
            bufferedReader.close();
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }


    public static void main(String[] args) throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String username = scanner.nextLine();

        Socket socket = new Socket(Server.HOSTNAME, Server.PORT);
        Client client = new Client(socket, username);

        client.listen();
        client.sendMessage();
    }
}
