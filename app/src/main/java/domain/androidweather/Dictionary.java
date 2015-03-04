package domain.androidweather;



import domain.androidweather.weatherService.IWeatherService;
import domain.androidweather.weatherService.models.Weather;
import domain.androidweather.weatherService.models.WeatherDesc;

/**
 * Created by Константин on 04.03.2015.
 */

public class Dictionary {

    public String textInfo;
    private String description;
    private String main;

    public void Cloud(String Description,String Main) {

        description = Description;
        main = Main;

            switch (main.toLowerCase()) {
                case "clouds":
                    if (description.toLowerCase().equals("scattered clouds")) {
                        textInfo = "В городе небольшая облачность";

                    } else if (description.toLowerCase().equals("clear sky")) {
                        textInfo = "В городе ясная погода";

                    } else if (description.toLowerCase().equals("few clouds")) {
                        textInfo = "В городе малооблачная погода";
                        //textView1.setText(textInfo);
                    } else if (description.toLowerCase().equals("broken clouds")) {
                        textInfo = "В городе переменная облачность";

                    } else if (description.toLowerCase().equals("overcast clouds")) {
                        textInfo = "В городе пасмурная погода";

                    }
                    break;
            }

    }
}
