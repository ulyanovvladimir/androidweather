package domain.androidweather;


public class Dictionary {

    public String textInfo;
    private String description;
    private String main;



    public void Cloud(String Description,String Main) {
        description = Description;
        main = Main;

            switch (main.toLowerCase()) {
                case "clouds":
                    getCloudTranslation(description);
                    break;
            }

    }
    public String getCloudTranslation(String Description){
        description = Description;
        switch (description.toLowerCase()){
               case "scattered clouds":
                   textInfo = "В городе небольшая облачность";
                   break;
               case "clear sky":
                   textInfo = "В городе ясная погода";
                   break;
               case "few clouds":
                   textInfo = "В городе малооблачная погода";
                   break;
               case "broken clouds":
                   textInfo = "В городе переменная облачность";
                   break;
               case "overcast clouds":
                   textInfo = "В городе пасмурная погода";
                   break;
           }
        return description;
    }
}
