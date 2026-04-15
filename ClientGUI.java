import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientGUI extends JFrame implements ActionListener 
{
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Socket socket;
    private BufferedReader br;
    private PrintStream ps;

    public ClientGUI()
     {
        setTitle("Client Chat");
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        try
        {
            socket = new Socket("localhost", 1100);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());
            chatArea.append("Connected to Server...\n");
            
            new Thread(() -> 
            {
                try
                {
                    String serverMsg;
                    while ((serverMsg = br.readLine()) != null)
                    {
                        chatArea.append("Server says: " + serverMsg + "\n");
                    }
                }
                catch (Exception e)
                {
                    chatArea.append("Connection closed.\n");
                }
            }).start();

        }
        catch (Exception e)
        {
            chatArea.append("Error: Could not connect to server.\n");
        }
        sendButton.addActionListener(this);
        inputField.addActionListener(this); 
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String msg = inputField.getText();
        if (!msg.isEmpty())
        {
            ps.println(msg); 
            chatArea.append("You: " + msg + "\n");
            inputField.setText(""); 
            if (msg.equalsIgnoreCase("gn"))
            {
                try
                {
                    socket.close(); System.exit(0); 
                } 
                catch (Exception ex) 
                {}
            }
        }
    }

    public static void main(String[] args)
    {
        new ClientGUI();
    }
} 
    

