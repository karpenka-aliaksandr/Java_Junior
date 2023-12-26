package Server;

import java.net.*;
import java.util.*;

public class Server {
    private ServerWindow serverWindow;
    private Repository repository;
    private boolean isServerWorking;
    private LinkedList<ConnectionToClientThread> listConnectionToClientThread = new LinkedList<>();
    private ServerSocketThread serverSocketThread;
    private ConnectionToClientThread connectionToClientThread;


    
    Server(ServerWindow serverWindow){
        this.isServerWorking = false;
        this.serverWindow = serverWindow;
        repository = new Repository("Server/history");
    }

    public void btnStartClick(){
        if (isServerWorking) {
            addTotaLog("The server has already started.");
        } else {
            try {
                serverSocketThread = new ServerSocketThread(this);
                serverSocketThread.start();
                isServerWorking = true;
            } catch (Exception e) {
                addTotaLog("The server could not start.");
                e.getMessage();
            }
        }
    }

    public void btnStopClick(){
        if (isServerWorking) {
            isServerWorking = false;
            serverSocketThread.interrupt();
            stopAllConnectionToClient();
            addTotaLog("The server stopped.");  
        } else {
            addTotaLog("The server has already stopped.");
        }
    }

    public void receivedMessage(String message){
        addTotaLog(message);
        sendMessageToAll(message);
    }
    private void sendMessageToAll(String message){
        for (ConnectionToClientThread connectionToClientThread : listConnectionToClientThread) {
            connectionToClientThread.sendMessage(message);
        }
        repository.writeToFile(message);
    }
    
    public void startConnectionToClient(Socket socket){
        try{
            connectionToClientThread = new ConnectionToClientThread(this, socket);
            connectionToClientThread.start();     
            listConnectionToClientThread.add(connectionToClientThread);
            String message = repository.readFile();
            if (message != null) connectionToClientThread.sendMessage(message);
            addTotaLog("Присоединился клиент.");
            System.out.println("Присоединился клиент."); 
            System.out.println("listConnectionToClientThread = " + listConnectionToClientThread);   
        } catch (Exception e) {
            System.out.println("Server Соединение не создалось ClientConnection");
        }   
    }
    public void interruptConnectionToClientThread(ConnectionToClientThread connectionToClientThread){
        System.out.println("Server interruptConnectionToClientThread: " + connectionToClientThread);
        if (listConnectionToClientThread.remove(connectionToClientThread)) {
            System.out.println(connectionToClientThread);
            connectionToClientThread.interrupt();
            try {
                serverWindow.addTotaLog("Клиент отключен.");
            } catch (Exception e) {
                System.out.println("не вывелось сообщение Клиент отключен.");
            }
        }  
    }
    public void stopAllConnectionToClient(){
        //System.out.println("listConnectionToClientThread = " + listConnectionToClientThread);
        while (!listConnectionToClientThread.isEmpty()){
            connectionToClientThread = listConnectionToClientThread.getFirst();
            interruptConnectionToClientThread(connectionToClientThread);    
            //System.out.println("Server Соединение разорвал соединение с клиентом:" + connectionToClientThread);
        }
        System.out.println("Server Соединение разорвал все соединения");
        System.out.println("listConnectionToClientThread = " + listConnectionToClientThread);;
    }  

    public void addTotaLog(String text){
        serverWindow.addTotaLog(text);
    }

    public boolean getServerStatus(){
        return isServerWorking;
    }
}

    
