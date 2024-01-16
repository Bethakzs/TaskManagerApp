package workPlace;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InstagramFlooder {
    private static String PROFILE;
    private static String USERNAME;
    private static String PASSWORD;
    private static int NUMBER_OF_USERS;
    private static Boolean CHECKER; // true - followers, false - following
    private static String MESSAGE;
    private static final String PATH_TO_DRIVER = "C:\\My program\\chromedriver.exe";

    public InstagramFlooder() {
        starting();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", PATH_TO_DRIVER);
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, 10);

        try {
            login(driver, wait);
            String[] users = searchUsers(driver, wait);
            sendMessage(users, driver, wait);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void login(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        driver.get("https://www.instagram.com/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        usernameInput.sendKeys(USERNAME);
        Thread.sleep(1000);
        passwordInput.sendKeys(PASSWORD);
        Thread.sleep(1000);
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'x1i10hfl')]")));
    }

    private static void sendMessage(String[] users, WebDriver driver, WebDriverWait wait) throws InterruptedException {
        boolean isMessageSent = false;
        for (String username : users) {
            driver.get("https://www.instagram.com/" + username + "/");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'x1i10hfl')]")));
            Thread.sleep(2000);

            List<WebElement> privateAccountMessage = driver.findElements(By.xpath("//h2[@class='_aa_u' and text()='This Account is Private']"));
            if (!privateAccountMessage.isEmpty()) {
                System.out.println("Account " + username + " is private");
                continue;
            } else {
                WebElement sendMessageButton = driver.findElement(By.xpath("//div[contains(@class, 'x1i10hfl')]"));
                sendMessageButton.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'xzsf02u x1a2a7pz x1n2onr6 x14wi4xw x1iyjqo2 x1gh3ibb xisnujt xeuugli x1odjw0f notranslate') and @role='textbox']")));
            }

            if (!isMessageSent) {
                WebElement notNowButton = driver.findElement(By.xpath("//button[contains(@class, '_a9-- _ap36 _a9_1') and text()='Not Now']"));
                notNowButton.click();
                isMessageSent = true;
            }

            WebElement messageInput = driver.findElement(By.xpath("//div[contains(@class, 'xzsf02u x1a2a7pz x1n2onr6 x14wi4xw x1iyjqo2 x1gh3ibb xisnujt xeuugli x1odjw0f notranslate') and @role='textbox']"));
            messageInput.sendKeys(MESSAGE);
            Thread.sleep(2000);

            WebElement sendButton = driver.findElement(By.xpath("//div[contains(@class, 'x1i10hfl') and text()='Send']"));
            sendButton.click();
            Thread.sleep(2500);
            System.out.println("Message was sent to " + username);
        }
    }

    private static String[] searchUsers(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        driver.get("https://www.instagram.com/" + PROFILE);

        WebElement link;
        if (CHECKER) {
            link = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href, '/followers/')]")));
        } else {
            link = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@href, '/following/')]")));
        }
        link.click();

        List<WebElement> userElements = new ArrayList<>();
        int currentUser = 0;
        while (NUMBER_OF_USERS == 0 || userElements.size() < NUMBER_OF_USERS) {
            Thread.sleep(100);
            List<WebElement> newUsers = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[contains(@class, '_ap3a _aaco _aacw _aacx _aad7 _aade')]")));
            for (WebElement user : newUsers) {
                if (!userElements.contains(user)) {
                    userElements.add(user);
                    currentUser++;
                    if (currentUser == 6) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", user);
                        currentUser = 0;
                    }
                    if (NUMBER_OF_USERS != 0 && userElements.size() >= NUMBER_OF_USERS) {
                        break;
                    }
                }
            }
            if (NUMBER_OF_USERS != 0 && userElements.size() == NUMBER_OF_USERS) {
                break;
            }
        }

        String[] users = new String[userElements.size()];
        for (int i = 0; i < userElements.size(); i++) {
            users[i] = userElements.get(i).getText();
        }

        return users;
    }

    public static void starting() {
        System.out.println(" _____ _                            _____ _");
        System.out.println("/  ___| |      _____  _____  _____ /  ___| | ");
        System.out.println("\\ `--.| |__  | ____|| ____|| ____|\\ `--.| |__ ");
        System.out.println(" `--. \\ '_ \\|  _|  |  _|  |  _|   `--. \\  '_ \\");
        System.out.println("/\\__/ / | | || |___ | |___ | |___ /\\__/ /| | |");
        System.out.println("\\____/|_| |_||_____||_____||_____|\\____/ |_| |_|");
        System.out.println();
        System.out.println("Starting...");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user profile");
        PROFILE = scanner.nextLine();
        System.out.println("Enter your username");
        USERNAME = scanner.nextLine();
        System.out.println("Enter your password");
        PASSWORD = scanner.nextLine();
        System.out.println("Enter number of users to send message");
        NUMBER_OF_USERS = scanner.nextInt();
        System.out.println("Enter true if you want to send message to followers, false if you want to send message to following");
        CHECKER = scanner.nextBoolean();
        scanner.nextLine();
        System.out.println("Enter message text");
        MESSAGE = scanner.nextLine();
        scanner.close();
        starting();
    }
}