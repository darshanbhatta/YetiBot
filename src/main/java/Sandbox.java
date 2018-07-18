package main.java;

import bot.discord.yeti.game.blackjack.BlackjackGame;
import bot.discord.yeti.game.blackjack.BlackjackGameHolder;
import bot.discord.yeti.game.connect4.Connect4Game;
import bot.discord.yeti.game.connect4.Connect4Holder;
import bot.discord.yeti.game.jackpot.JackpotGame;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;
import bot.discord.yeti.game.numguesser.NumGuessGame;
import bot.discord.yeti.game.numguesser.NumGuessHolder;
import bot.discord.yeti.game.slot.Slot;
import bot.discord.yeti.game.slot.SlotMachine;
import bot.discord.yeti.game.tictactoe.TicTacToeGame;
import bot.discord.yeti.game.tictactoe.TicTacToeHolder;
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Sandbox {

    public static void main(String[] args) {
String id = "";
        String search = "New york";
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
          String build="\t";
            for(int x=0;x<channel.getItem().getForecasts().size();x++){
                String v = " will be ";
                if(channel.getItem().getForecasts().get(x).getText().charAt(channel.getItem().getForecasts().get(x).getText().length()-1)=='s'||channel.getItem().getForecasts().get(x).getText().contains("rain")||channel.getItem().getForecasts().get(x).getText().contains("thunder")){
                    v = " there will be ";

                }
                if(x<=5)
                    build+=channel.getItem().getForecasts().get(x).getDay() + v +channel.getItem().getForecasts().get(x).getText() + " with a high of "+channel.getItem().getForecasts().get(x).getHigh()  + " and a low of " + channel.getItem().getForecasts().get(x).getLow() +"\n\t";


            }
            String location = channel.getTitle().substring((channel.getTitle().indexOf("Yahoo! Weather - ")+17));
            location = location.substring(0,location.indexOf(","));
            System.out.println(location);
            System.out.println(build);
        } catch (Exception e) {
            //error location not found
            System.out.println(e.toString());

        }





    }
}