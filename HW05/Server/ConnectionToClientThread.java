package Server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionToClientThread implements Runnable {
    private Thread thread;
    private AtomicBoolean running = new AtomicBoolean(false);
    private AtomicBoolean isRun = new AtomicBoolean(true);
    private int interval = 100;
    private Server server;
    private Socket socket;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
    
    public ConnectionToClientThread(Server server, Socket socket) {
        //System.out.println("ConnectionToClientThread constructor");
        this.server = server;
        this.socket = socket;
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
        //System.out.println("ConnectionToClientThread start()");
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            //System.out.println("созданы input output у сервера.");
        } catch (Exception e) {
            System.out.println("не созданы input output у сервера.");
        }
    }

    public void interrupt() {
        //System.out.println("ConnectionToClientThread interrupt()");
        running.set(false);
        isRun.set(false);
        try {
            output.close();
            input.close();
            socket.close();
            //System.out.println("ConnectionToClientThread output.close() intput.close() socket.close()");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("ConnectionToClientThread IOException output.close() intput.close() socket.close()");

        }
        //System.out.println("Thread in ConnectionToClient interrupt()");
        thread.interrupt();
    }

    public void run() {
        //System.out.println("ConnectionToClientThread run()");
        String message;
        running.set(true);
        //System.out.println("ConnectionToClientThread run running.set(true)");
        while (running.get()) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e){
                System.out.println("Thread in Connection was interrupted, Failed to complete operation");
                running.set(false);
                Thread.currentThread().interrupt();
            }
            // do something;
            while(isRun.get()){
                try{
                    message = (String) input.readObject();
                    //System.out.println("server.receivedMessage");
                    server.receivedMessage(message);
                } catch (ClassNotFoundException e) {
                    System.out.println("ClassNotFoundException");
                } catch (IOException e) {
                    System.out.println("IOException нет данных");
                    isRun.set(false);
                    //System.out.println("running.get() = " + running.get() + ", isRun.get() = " + isRun.get());
                    server.interruptConnectionToClientThread(this);
                }

            }
            
            
        }
    }

    public void sendMessage(String message) {
        //System.out.println(message);
        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            System.out.println("не получается отправить данные");
        }
    }
}