package workPlace;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.*;

public class Ipconfig {
    public Ipconfig() {
        JFrame frame = new JFrame("IP Address");
        StringBuilder ipAddresses = checkIP();
        JLabel label = new JLabel(ipAddresses.toString(), SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        JScrollPane scrollPane = new JScrollPane(label);
        frame.add(scrollPane);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(frame.getWidth() + 100, frame.getHeight() + 100);
        frame.setLocation(screenSize.width/2 - frame.getSize().width/2, screenSize.height/2 - frame.getSize().height/2);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public StringBuilder checkIP (){
        StringBuilder ipAddresses = new StringBuilder("<html>");
        try {
            Process process = Runtime.getRuntime().exec("ipconfig");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("IPv4")) {
                    String ipAddress = line.substring(line.indexOf(":") + 2);
                    ipAddresses.append("Your IPv4 address is: ").append(ipAddress).append("<br/>");
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ipAddresses.append("</html>");
        return ipAddresses;
    }
}
