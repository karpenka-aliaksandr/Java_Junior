package Client;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private ClientWindow clientWindow;
    private ConnectionToServerThread connectionToServerTread;
    private Socket socket;
    private boolean isConnectedToServer;
    
    Client(ClientWindow clientWindow){
        this.clientWindow = clientWindow;
        isConnectedToServer = false;
        setTextTotaLog("Введите данные и подключитесь к серверу.");
    }

    public void btnSendClick(String message){
        if (isConnectedToServer) {
            if (message.isEmpty()) {    
                clientWindow.addTotaLog("! Нет данных для отправки !");
            } else {
                connectionToServerTread.sendMessage(message);
                clientWindow.setTextTotfMessage("");
            }
        } else {
            clientWindow.addTotaLog("! Нет подключения к серверу, отправить сообщение невозможно !");
        }
    }
    public void btnLoginClick(String address, String port, String login, char[] password) {
        if (isConnectedToServer) {
            clientWindow.addTotaLog("Вы уже подключены к серверу.");
        } else {
            try {
                socket = new Socket(InetAddress.getByName(address), Integer.parseInt(port));
                System.out.println("Создан Сокет");
                connectionToServerTread = new ConnectionToServerThread(this, socket, login, password);
                connectionToServerTread.start();
            } catch (UnknownHostException eh) {
                clientWindow.addTotaLog("Не удалось подключиться, проверьте данные и попробуйте позже");
            } catch (Exception e) {
                clientWindow.addTotaLog("Не удалось подключиться, проверьте данные и попробуйте позже...");                
            }
        }
    }
    public void receivedMessage(String message){
        addTotaLog(message);
    }
    public void addTotaLog(String text){
        clientWindow.addTotaLog(text);
    }
    public void setTextTotaLog(String text){
        clientWindow.setTextTotaLog(text);
    }

    public void setConnected(){
        this.isConnectedToServer = true;
        clientWindow.setTextTotaLog("Вы подключены к серверу.");
        clientWindow.setVisiblePanTop(false);
    }
    public void interruptConnectionToServerThread(){
        System.out.println("Client interruptConnectionToServerThread");
        System.out.println("Client isConnectedToServer = " + isConnectedToServer);
        if (isConnectedToServer){
            connectionToServerTread.interrupt();
            isConnectedToServer = false;
            clientWindow.setVisiblePanTop(true);
            clientWindow.addTotaLog("Вы отключены от сервера.");
        }
    }
}
