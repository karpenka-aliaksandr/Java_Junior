package Client;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public class ClientWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_POSX = 700;
    private static final int WINDOW_POSY = 100;

    private JPanel panBottom, panTop;
    private JButton btnLogin, btnSend;
    private JTextArea taLog;
    private JTextField tfAddress, tfPort, tfLogin, tfMessage;
    private JLabel lblNothing;
    private JPasswordField pfPassword;
    private JScrollPane sp;

    private Client client;

    public ClientWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Random rnd = new Random();
        setBounds(WINDOW_POSX + rnd.nextInt(10)*100, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setAlwaysOnTop(true);
        panTop = getPanelTop();
        add(panTop, BorderLayout.NORTH);
        add(getPanelBottom(), BorderLayout.SOUTH);
        taLog = new JTextArea();
        taLog.setEditable(false);
        taLog.setLineWrap(true);
        taLog.setWrapStyleWord(true);
        sp = new JScrollPane(taLog, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp);
        addListeners();
        client = new Client(this);

        setVisible(true);

    }

    private void addListeners() {
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
                client.interruptConnectionToServerThread();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        ActionListener alSend = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.btnSendClick(tfMessage.getText());
            }
        };

        btnSend.addActionListener(alSend);
        tfMessage.addActionListener(alSend);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.btnLoginClick(tfAddress.getText(), tfPort.getText(), tfLogin.getText(),
                        pfPassword.getPassword());
            }
        });
    }

    private JPanel getPanelTop() {
        JPanel panel = new JPanel(new GridLayout(2, 3));
        tfAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("9999");
        lblNothing = new JLabel();
        tfLogin = new JTextField("login");
        pfPassword = new JPasswordField();
        btnLogin = new JButton("login");
        panel.add(tfAddress);
        panel.add(tfPort);
        panel.add(lblNothing);
        panel.add(tfLogin);
        panel.add(pfPassword);
        panel.add(btnLogin);
        return panel;
    }

    private JComponent getPanelBottom() {
        panBottom = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        btnSend = new JButton("Send");
        panBottom.add(btnSend, BorderLayout.EAST);
        panBottom.add(tfMessage);
        return panBottom;
    }

    public void addTotaLog(String text) {
        taLog.append(text + "\n");
        SwingUtilities.invokeLater(() -> {
            sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
        });
    }

    public void setTextTotaLog(String text) {
        taLog.setText(text + "\n");
        SwingUtilities.invokeLater(() -> {
            sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
        });
    }

    public void setTextTotfMessage(String text) {
        tfMessage.setText(text);
    }

    public void setVisiblePanTop(boolean visible) {
        panTop.setVisible(visible);
    }

}
