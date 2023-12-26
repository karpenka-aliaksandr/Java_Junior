package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerSocketThread implements Runnable {
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);
    private int interval = 100;
    private Server server;
    private Socket socket;
    private ServerSocket serverSocket;
    private final int PORT = 9999;


    public ServerSocketThread(Server server) {
        this.server = server;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
        //System.out.println("ServerSocketThread start()");
        try {
            serverSocket = new ServerSocket(PORT);
            server.addTotaLog(getServerSocketToString());
            server.addTotaLog("The server started.");
            //System.out.println("Cоздан serverSocket сервера.");
        } catch (IOException e) {
            System.out.println("ServerSocketThread IOException - не создался serverSocket");
        }

    }

    public void interrupt() {
        //System.out.println("ServerSocketThread interrupt()");
        running.set(false);  
        try {
            serverSocket.close();
            //System.out.println("ServerSocketThread serverSocket.close()");
        } catch (IOException e) {
            System.out.println("ServerSocketThread IOException в serverSocket.close");
        }
        System.out.println("Thread in ServerSocket interrupt()");
        thread.interrupt();
    }

    public void run() {
        //System.out.println("ServerSocketThread run()");
        running.set(true);
        //System.out.println("ServerSocketThread run() running.set(true)");
        while (running.get()) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e){
                //System.out.println("Thread in ServerSocket was interrupted, Failed to complete operation");
                Thread.currentThread().interrupt();
            }
            // do something
            try {
                //System.out.println("ServerSocket.accept()");
                socket = serverSocket.accept();
                //System.out.println("ServerSocketThread вызов создание подключения");
                server.startConnectionToClient(socket);
            } catch (IOException e) {
            }
            
        }
    }
    public String getServerSocketToString(){
        return serverSocket.toString();
    }
}