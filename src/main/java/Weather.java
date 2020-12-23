

import com.vdurmont.emoji.EmojiParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        String result = "";
        Scanner sc;
        URL url;
        String weatherApiKey = "875fbe04cfc6839915bd2247951d268a";
        String tmp_url = message + weatherApiKey;
        url = new URL(tmp_url);
        sc = new Scanner((InputStream) url.getContent());
        while (sc.hasNext()) {
            result += sc.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName((String) object.get("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemperature(main.getDouble("temp"));

        model.setHumidity(main.getDouble("humidity"));

        JSONObject sys = object.getJSONObject("sys");
        result = ":" + sys.get("country").toString().toLowerCase() + ":";
        result = EmojiParser.parseToUnicode(result);
        model.setCountry(result);

        JSONArray objarr = object.getJSONArray("weather");
        for (int i = 0; i < objarr.length(); i++) {
            JSONObject weather = objarr.getJSONObject(i);
            model.setDescription((String) weather.get("description"));
            model.setIcon_url((String) weather.get("icon"));
            result = ":" + weather.get("main").toString().toLowerCase() + ":";
            result = EmojiParser.parseToUnicode(result);
            model.setMain(result);


        }

        JSONObject coord = object.getJSONObject("coord");
        model.setLon(coord.getDouble("lon"));
        model.setLat(coord.getDouble("lat"));

        result = model.getIcon_url() + "\uD83D\uDCCC Место: " + model.getName() + " " + model.getCountry() + "\n" +
                "☀️ Погода: " + model.getDescription().toUpperCase().charAt(0) + model.getDescription().substring(1) + "\n" +
                "\uD83C\uDF21 Температура воздуха: " + model.getTemperature() + " \u00b0С\n" +
                "\uD83C\uDF00 Относительная влажность: " + model.getHumidity() + "%" + "\n";

        return result;
    }
}
