package workPlace;

import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather {
    private static final String API_KEY = "9c71c1bdbb132e90a1d9ace437fb2d50";
    private static workPlace.status status;

    public Weather() {
        JFrame frame = new JFrame("Weather");
        frame.setSize(400, 400);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2, screenSize.height / 2 - frame.getSize().height / 2);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        JTextField textField = new JTextField(20);
        frame.getContentPane().add(textField);

        JTextArea textArea = new JTextArea(5, 30);
        textArea.setBackground(Color.getColor(frame.getBackground().toString()));
        textArea.setSize(300, 150);
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        frame.getContentPane().add(textArea);

        JButton button = new JButton("Submit");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setLocation(100, 100);
        frame.getContentPane().add(button);

        button.addActionListener(e -> {
            String city = textField.getText();
            JSONObject json = getWeather(city);
            try {
                JSONObject main = json.getJSONObject("main");
                double tempInKelvin = main.getDouble("temp");
                double tempInCelsius = tempInKelvin - 273.15;
                double feelsLikeInKelvin = main.getDouble("feels_like");
                double feelsLikeInCelsius = feelsLikeInKelvin - 273.15;
                int humidity = main.getInt("humidity");
                JSONObject wind = json.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                JSONObject sys = json.getJSONObject("sys");
                String country = sys.getString("country");

                JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
                String weatherMain = weather.getString("main");

                if(weatherMain.equals("Rain") || weatherMain.equals("Snow")) {
                    if (windSpeed > 40 && weatherMain.equals("Snow")) {
                        status = workPlace.status.THUNDERSTORM;
                    } else if (weatherMain.equals("Snow")) {
                        status = workPlace.status.SNOWY;
                    } else {
                        status = workPlace.status.RAINY;
                    }
                } else if (weatherMain.equals("Clouds")) {
                    if(humidity > 0 && humidity < 50) {
                        status = workPlace.status.CLOUD;
                    } else if(humidity >= 50 && humidity < 90) {
                        status = workPlace.status.CLOUDY_DAY;
                    } else {
                        status = workPlace.status.FULL_CLOUDY;
                    }
                } else if (windSpeed > 25) {
                    status = workPlace.status.WINDY;
                } else {
                    status = workPlace.status.SUN;
                }

                BufferedImage myPicture = null;
                String path = "C:\\Users\\roman\\IdeaProjects\\TaskManager\\src\\main\\java\\workPlace\\pictures\\";
                String finalPath = "";
                try {
                    switch (status) {
                        case CLOUDY_DAY: {
                            finalPath = path + "cloudy-day.png";
                            break;
                        }
                        case CLOUD: {
                            finalPath = path + "cloud.png";
                            break;
                        }
                        case RAINY: {
                            finalPath = path + "rainy.png";
                            break;
                        }
                        case THUNDERSTORM: {
                            finalPath = path + "thunderstorm.png";
                            break;
                        }
                        case SUN: {
                            finalPath = path + "sun.png";
                            break;
                        }
                        case FULL_CLOUDY: {
                            finalPath = path + "full-cloudy.png";
                            break;
                        }
                        case WINDY: {
                            finalPath = path + "wind.png";
                            break;
                        }
                        case SNOWY: {
                            finalPath = path + "snowing.png";
                            break;
                        }
                        case null: {
                            finalPath = path + "unknown.png";
                            break;
                        }
                    }
                    myPicture = ImageIO.read(new File(finalPath));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Image scaledImage = myPicture.getScaledInstance(150, 150, Image.SCALE_DEFAULT);
                JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
                frame.getContentPane().add(picLabel);

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                textArea.append("Humidity: " + humidity + "%\n");

                textArea.setText(String.format("Time: %s\nCountry: %s  City: %s\nTemperature: %.1f°C  Feels Like: %.1f°C\nWind Speed: %.1fkm/h  Humidity: %d%%\"",
                        formatter.format(new Date()), country, city, tempInCelsius, feelsLikeInCelsius, windSpeed, humidity));

                button.addActionListener(e1 -> {
                    frame.dispose();
                });
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        });
        frame.setVisible(true);
    }

    public JSONObject getWeather(String city){
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            return new JSONObject(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
