package Server;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;

public class ServerWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 300;
    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_POSX = 300;// по горизонтали от левой стороны
    private static final int WINDOW_POSY = 100;// по вертикали от верха
    private JPanel panBottom;
    private JButton btnStart, btnStop;
    private JTextArea taLog;
    private JScrollPane sp;
    private Server server;
    private ServerWindow sw;

    public ServerWindow() {
        sw = this;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);
        add(getPanelBottom(), BorderLayout.SOUTH);
        taLog = new JTextArea("Server stopped.\n");
        taLog.setEditable(false);
        taLog.setLineWrap(true);
        taLog.setWrapStyleWord(true);
        sp = new JScrollPane(taLog, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(sp);
        addListeners();
        server = new Server(sw);
        setVisible(true);
    }

    private void addListeners() {

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.btnStartClick();
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.btnStopClick();
            }
        });
    }

    private JComponent getPanelBottom() {
        panBottom = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        panBottom.add(btnStart);
        panBottom.add(btnStop);
        return panBottom;
    }

    public void addTotaLog(String text) {
        taLog.append(text + "\n");
        SwingUtilities.invokeLater(() -> {
            sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
        });
    }
}
