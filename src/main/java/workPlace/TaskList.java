package workPlace;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.awt.*;

public class TaskList {
    private String[] queries;

    public TaskList() {
        JFrame frame = new JFrame("Search Queries");
        frame.setSize(400, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2, screenSize.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);

        JButton button = new JButton("Submit");
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(e -> {
            String text = textArea.getText();
            queries = text.split("\\n");
            openBrowser(queries);
            frame.dispose();
        });
        frame.getContentPane().add(button, "South");

        frame.setVisible(true);
    }

    private void openBrowser(String[] queries) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "C:\\My program\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (String request : queries) {
//            js.executeScript("window.open('https://www.google.com/search?q=" + request + " mod minecraft 1.12.2')");
            js.executeScript("window.open('https://www.google.com/search?q=" + request + "')");
        }
    }
}

