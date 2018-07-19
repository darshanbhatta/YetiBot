package bot.discord.yeti.command;

import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather {
    public static void run(MessageReceivedEvent event){
        //!weather current dallas tx
        String[] code = event.getMessage().getContentRaw().split(" ");
        if(code.length>=3&&code[1].equals("current")){
            String id = "";
            String search = code[2];
            if(code.length>3){

                for(int x=3; x<code.length;x++){

                    search+=" "+code[x];


                }

            }

            char quotes = '"';
            search = search.replace(" ", "%20");
            String what = "https://query.yahooapis.com/v1/public/yql?q=select*from%20geo.places%20where%20text=%22"+search+"%22&format=json";

            URL url = null;
            try {
                url = new URL(what);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String num = br.readLine();
                System.out.println(num);
                id = num.substring(num.indexOf("woeid")+8,num.indexOf("placeTypeName")-3);
                System.out.println(id);
                YahooWeatherService service = null;
                try {
                    service = new YahooWeatherService();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                Channel channel = null;
                try {
                    channel = service.getForecast(id, DegreeUnit.FAHRENHEIT);
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String cond = channel.getItem().getCondition().toString().substring(channel.getItem().getCondition().toString().indexOf("text=")+5,channel.getItem().getCondition().toString().indexOf("code")-2).toLowerCase();
                String temp = channel.getItem().getCondition().toString().substring(channel.getItem().getCondition().toString().indexOf("temp=")+5,channel.getItem().getCondition().toString().indexOf("date")-2);
                String location = channel.getTitle().substring((channel.getTitle().indexOf("Yahoo! Weather - ")+17));
                location = location.substring(0,location.indexOf(","));
                String v = " it is ";
                if(cond.charAt(cond.length()-1)=='s'||cond.contains("rain")||cond.contains("thunder")){
                    v = " there are ";

                }
                event.getChannel().sendMessage("Currently in "+ location + v+cond+ " with a temperature of " + temp + " °F").queue();

            } catch (Exception e) {
                event.getChannel().sendMessage("Error location not found").queue();
                System.out.println(e.toString());

            }

        }else if(code.length>=3&&code[1].equals("forecast")){
            String id = "";
            String search = code[2];
            if(code.length>3){

                for(int x=3; x<code.length;x++){

                    search+=" "+code[x];


                }

            }

            char quotes = '"';
            search = search.replace(" ", "%20");
            String what = "https://query.yahooapis.com/v1/public/yql?q=select*from%20geo.places%20where%20text=%22"+search+"%22&format=json";

            URL url = null;
            try {
                url = new URL(what);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String num = br.readLine();
                System.out.println(num);
                id = num.substring(num.indexOf("woeid")+8,num.indexOf("placeTypeName")-3);
                System.out.println(id);
                YahooWeatherService service = null;
                try {
                    service = new YahooWeatherService();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                Channel channel = null;
                try {
                    channel = service.getForecast(id, DegreeUnit.FAHRENHEIT);
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String location = channel.getTitle().substring((channel.getTitle().indexOf("Yahoo! Weather - ")+17));
                String build="\n\t";
                for(int x=0;x<channel.getItem().getForecasts().size();x++){
                    String v = " will be ";
                    if(channel.getItem().getForecasts().get(x).getText().charAt(channel.getItem().getForecasts().get(x).getText().length()-1)=='s'||channel.getItem().getForecasts().get(x).getText().contains("rain")||channel.getItem().getForecasts().get(x).getText().contains("thunder")){
                        v = " there will be ";

                    }
                    if(x<=5)
                        build+=channel.getItem().getForecasts().get(x).getDay() + v +channel.getItem().getForecasts().get(x).getText().toLowerCase() + " with a high of "+channel.getItem().getForecasts().get(x).getHigh()  + " °F and a low of " + channel.getItem().getForecasts().get(x).getLow() +" °F\n\t";


                }
                location = location.substring(0,location.indexOf(","));
                event.getChannel().sendMessage("Future forecasts for "+ location + "\n"+build).queue();

            } catch (Exception e) {
                event.getChannel().sendMessage("Error location not found").queue();
                System.out.println(e.toString());

            }


        }else if(code.length>=3&&code[1].equals("tmr")){
            String id = "";
            String search = code[2];
            if(code.length>3){

                for(int x=3; x<code.length;x++){

                    search+=" "+code[x];


                }

            }

            char quotes = '"';
            search = search.replace(" ", "%20");
            String what = "https://query.yahooapis.com/v1/public/yql?q=select*from%20geo.places%20where%20text=%22"+search+"%22&format=json";

            URL url = null;
            try {
                url = new URL(what);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                String num = br.readLine();
                System.out.println(num);
                id = num.substring(num.indexOf("woeid")+8,num.indexOf("placeTypeName")-3);
                System.out.println(id);
                YahooWeatherService service = null;
                try {
                    service = new YahooWeatherService();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                Channel channel = null;
                try {
                    channel = service.getForecast(id, DegreeUnit.FAHRENHEIT);
                } catch (JAXBException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String location = channel.getTitle().substring((channel.getTitle().indexOf("Yahoo! Weather - ")+17));
                String build="";
                String v = " it will be ";
                if(channel.getItem().getForecasts().get(0).getText().charAt(channel.getItem().getForecasts().get(0).getText().length()-1)=='s'||channel.getItem().getForecasts().get(0).getText().contains("rain")||channel.getItem().getForecasts().get(0).getText().contains("thunder")){
                    v = " there will be ";

                }
                        build+= v +channel.getItem().getForecasts().get(0).getText().toLowerCase() + " with a high of "+channel.getItem().getForecasts().get(0).getHigh()  + " °F and a low of " + channel.getItem().getForecasts().get(0).getLow() +" °F\n\t";



                location = location.substring(0,location.indexOf(","));
                event.getChannel().sendMessage("Tomorrow in "+ location + " "+build).queue();

            } catch (Exception e) {
                event.getChannel().sendMessage("Error location not found").queue();
                System.out.println(e.toString());

            }


            //error invalid

        }else{

            event.getChannel().sendMessage("Error command not found. Try these commands\n\n\t!weather current %location name/zipcode%\n\t!weather tmr %location name/zipcode%\n\t!weather forecast %location name/zipcode%").queue();
        }










    }
}
