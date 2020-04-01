import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

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
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Set variables
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            switch (message_text) {
                case "/help":
                    message_text = "What are you want, bitch?";
                    break;
                case "/settings":
                    message_text = "Go to sleep, freak!";
                    break;
                case "/about":
                    message_text = "I'm the best Gparser!";
                    break;
                case "/parsing":
                    message_text = "Not now, motherfucker!";
                    break;
                default:
                    message_text = "I don't understand you, fagot!";
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
        return "WorldWeatherTelegramBot";
    }

    public String getBotToken() {
        return "1142910546:AAGL3-iOFZkBe5yJPoIvNazxhqy6eckC7-I";
    }
}
