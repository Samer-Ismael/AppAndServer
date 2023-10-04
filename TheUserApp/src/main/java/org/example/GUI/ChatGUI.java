package org.example.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.KafkaMessageConsumer;
import org.example.KafkaProducerConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

public class ChatGUI {
    private JTextField textField1;
    private JTextPane textPane1;
    private JPanel Chat;
    private KafkaProducerConfig menu;


    public ChatGUI() {
        this.menu = new KafkaProducerConfig();
        String userName = JOptionPane.showInputDialog(Chat, "Enter your username:");
        menu.setUserName(userName);
        menu.sendFromGUI( menu.getUserName()+ " Entered the chat!", "System: ");
        textField1.requestFocusInWindow();


        // Create a SwingWorker to run the Kafka message consumer in the background
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws JsonProcessingException {
                KafkaMessageConsumer messageConsumer = new KafkaMessageConsumer(
                        "localhost:9092",
                        "general-group"+ generateRandomGroup(),
                        "general", textPane1);

                // Start consuming messages
                messageConsumer.consumeMessages();



                return null;
            }
        };

        // Update the text pane as messages arrive
        worker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("message".equals(evt.getPropertyName())) {
                    String message = (String) evt.getNewValue();
                    SwingUtilities.invokeLater(() -> {
                        textPane1.setText(textPane1.getText() + "\n" + message);
                    });
                }
            }
        });
        // Execute the SwingWorker that I talked about in line 30
        worker.execute();

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = textField1.getText();
                    if (message.equalsIgnoreCase("clear")) textPane1.setText("");
                    else if (message.equalsIgnoreCase("exit")) System.exit(0);
                    else menu.sendFromGUI(message, menu.getUserName());
                    textField1.setText("");
                }
            }
        });
    }

    public void run (){
        JFrame frame = new JFrame("Chat GUI " + menu.getUserName());
        frame.setContentPane(Chat);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
        textField1.requestFocusInWindow();

        frame.setVisible(true);
    }
    // Tow consumers cant listen to the same topic if they have the same group.
    // I hade to give it a random number and send it as a String everytime a new user comes in
    // Why 1000000000 ? because I can :)
    private String generateRandomGroup() {
        Random random = new Random();
        int randomInt = random.nextInt(1000000000);
        return Integer.toString(randomInt);
    }
}
