import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ServerGUI extends JFrame 
{
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private ServerSocket ss;
    private Socket s;
    private BufferedReader br;
    private PrintStream ps;

    public ServerGUI()
    {
        setTitle("Server Chat");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(240, 240, 240));
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
           sendButton.setBackground(Color.YELLOW);
     bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
        setVisible(true);
        startServer();
    }

    private void startServer()
     {
       
        new Thread(() -> 
        {
            try {
                updateChat("Server Application is Running...");
                ss = new ServerSocket(1100);
                updateChat("Waiting for client on port 1100...");
                
                s = ss.accept();
                updateChat("Connection Successful!");

                br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                ps = new PrintStream(s.getOutputStream());

                String str;
                while ((str = br.readLine()) != null) 
                {
                    updateChat("Client Says: " + str);
                    if (str.equalsIgnoreCase("gn"))
                    {
                        updateChat("Client disconnected.");
                        break;
                    }
                }
            } 
            catch (Exception e) 
            {
                updateChat("Error: " + e.getMessage());
            }
        }).start();
    }

    private void sendMessage()
    {
        String msg = inputField.getText();
        if (!msg.isEmpty() && ps != null) 
        {
            ps.println(msg);
            updateChat("Server (You): " + msg);
            inputField.setText("");
        }
    }

    private void updateChat(String msg) 
    {
        SwingUtilities.invokeLater(() -> chatArea.append(msg + "\n"));
    }

    public static void main(String[] args) 
    {
        new ServerGUI();
    }
} 
    

