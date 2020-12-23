import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi tBApi = new TelegramBotsApi();
        try {
            tBApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Model model = new Model();
        String message_text;

        if (update.hasMessage() && (update.getMessage().hasText() || update.getMessage().hasLocation())) {
            long chat_id = update.getMessage().getChatId();

            if (update.getMessage().hasLocation()) {
                String lat = update.getMessage().getLocation().getLatitude().toString();
                String lon = update.getMessage().getLocation().getLongitude().toString();
//                message_text = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&lang=ru&appid=";
                message_text = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=metric&lang=ru&appid=";
            } else {
                message_text = update.getMessage().getText();
                switch (message_text) {
                    case "/start":
                        message_text = "\uD83C\uDF2A\uD83C\uDF08☀️\uD83C\uDF24⛅️\uD83C\uDF25☁️\uD83C\uDF26\uD83C\uDF27⛈\n" +
                                "\n" +
                                "Привет, Бро! \n" +
                                "Я Погодный Телеграм Бот! \n" +
                                "Я знаю о погоде ВСЁ. \n" +
                                "Напиши мне название города, в котором ты хочешь узнать погоду, \n" +
                                "и я моментально дам тебе ответ!\n" +
                                "\n" +
                                "\uD83C\uDF29\uD83C\uDF28❄️☃️⛄️\uD83C\uDF2C\uD83D\uDCA8\uD83D\uDCA7\uD83D\uDCA6☔️";
                        break;
                    case "/about":
                        message_text =
                                "\uD83C\uDF2A\uD83C\uDF08☀️\uD83C\uDF24⛅️\uD83C\uDF25☁️\uD83C\uDF26\uD83C\uDF27⛈\n" +
                                "\n" +

                                "Я Погодный Телеграм Бот! \n" +
                                "Я знаю о погоде ВСЁ. \n" +
                                "Напиши мне название города, \nв котором ты хочешь узнать погоду, \n" +
                                "и я моментально дам тебе ответ!\n" +
                                "\n" +
                                "\uD83C\uDF29\uD83C\uDF28❄️☃️⛄️\uD83C\uDF2C\uD83D\uDCA8\uD83D\uDCA7\uD83D\uDCA6☔️";
                        break;
                    default:
                        message_text = "http://api.openweathermap.org/data/2.5/weather?q=" + message_text + "&units=metric&lang=ru&appid=";
                }
            }

            try {
                if (message_text.contains("http://api.openweathermap.org")) {
                    message_text = Weather.getWeather(message_text, model);
                    String photo_link = message_text.substring(0, 3);
                    message_text = message_text.substring(3);
                    SendPhoto foto = new SendPhoto().setChatId(chat_id).setPhoto(new File("C:\\Program Project\\WeatherTelegramBot\\src\\main\\resources\\icon\\" + photo_link.substring(0, 2) + ".png"));
                    execute(foto);
                }
            } catch (IOException | TelegramApiException e) {
                message_text = "К сожалению такой город не найден. \uD83D\uDE22 Проверь название города \uD83D\uDE1C";

            }


            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(chat_id)
                    .setText(message_text);
            try {
                execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }


        }

    }

    public String getBotUsername() {
        return "LitTestBotMyBot";
    }

    public String getBotToken() {
        return "1142910546:AAGL3-iOFZkBe5yJPoIvNazxhqy6eckC7-I";
    }
}
