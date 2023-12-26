package Client;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionToServerThread extends Thread {
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);
    private int interval = 100;
    ObjectOutputStream output = null;
    ObjectInputStream input = null;
    Socket socket;
    String address, port, login, message;
    char[] password;
    Client client;

    ConnectionToServerThread(Client client, Socket socket, String login, char[] password) {
        System.out.println("ConnectionToServerThread constructor");
        this.client = client;
        this.socket = socket;
        this.login = login;
        this.password = password;
    }
    public void start() {
        System.out.println("Client Start");
        thread = new Thread(this);
        thread.start();
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("созданы input output у клиента.");
            client.setConnected();
        } catch (Exception e) {
            System.out.println("не созданы input output у клиента.");
        }
    }

    @Override
    public void run(){
        System.out.println("ConnectionToServerThread run()");
        String message;
        running.set(true);
        System.out.println("ConnectionToServerThread run running.set true");
        while (running.get()) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted, Failed to complete operation");
            }
            // do something
            while (running.get()) {
                try {
                    message = (String) input.readObject();
                    System.out.println("client.receivedMessage");
                    client.receivedMessage(message);
                } catch (ClassNotFoundException e) {
                    System.out.println("ClassNotFoundException");
                } catch (IOException e) {
                    System.out.println("IOException нет данных");
                    running.set(false);
                    client.interruptConnectionToServerThread();
                }
            }
            
        }
    }

    public void sendMessage(String message) {
        // System.out.println(message);
        try {
            output.writeObject(login + ": " + message);
            output.flush();
        } catch (IOException e) {
            // System.out.println("не получается отправить данные");
        }
    }
}
