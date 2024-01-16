package workPlace;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Manager");
        frame.setTitle("Task Manager");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2, screenSize.height / 2 - frame.getSize().height / 2);
        frame.setLayout(null);
        frame.setVisible(true);

        eventIpconfig(frame);
        eventList(frame);
        weather(frame);
        flooder(frame);
    }

    public static void eventIpconfig(JFrame frame) {
        JButton button = new JButton("IP Address");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBounds(20, 20, 160, 40);
        frame.add(button);
        button.addActionListener(e -> {
            frame.dispose();
            new Ipconfig();
        });
    }

    public static void eventList(JFrame frame) {
        JButton button = new JButton("Task List");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBounds(20, 80, 160, 40);
        frame.add(button);
        button.addActionListener(e -> {
            frame.dispose();
            new TaskList();
        });
    }

    public static void weather(JFrame frame) {
        JButton button = new JButton("Weather");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBounds(200, 20, 160, 40);
        frame.add(button);
        button.addActionListener(e -> {
            frame.dispose();
            new Weather();
        });
    }

    public static void flooder(JFrame frame) {
        JButton button = new JButton("Flooder");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBounds(200, 80, 160, 40);
        frame.add(button);
        button.addActionListener(e -> {
            frame.dispose();
            new InstagramFlooder();
        });
    }
}
