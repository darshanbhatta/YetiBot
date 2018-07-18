package main.java;

import bot.discord.yeti.PollManager;
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
import bot.discord.yeti.util.poll.Poll;
import bot.discord.yeti.util.poll.PollHolder;
import com.github.fedy2.weather.YahooWeatherService;
import com.github.fedy2.weather.data.Channel;
import com.github.fedy2.weather.data.unit.DegreeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Sandbox {

    public static void main(String[] args) throws IOException {
/*
        String search = "https://www.reddit.com/r/funny/hot.json?limit=100";

        int ran = (int) (Math.random() * 100) + 1;
        URL url = null;
        try {
            url = new URL(search);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        InputStream mInputStream = httpConn.getInputStream();
String jsonString = "";
        int i = 0;
        while ((i = mInputStream.read()) != -1) {
            jsonString += (char) i;
        }
            JSONObject arr = null;
            try {
                arr = new JSONObject(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println(arr.toString());
            String title = arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).toString();
               System.out.println(title);

*/       final long start = System.currentTimeMillis();
        String search = "https://www.reddit.com/r/funny/hot.json?limit=100";

        int ran = (int)(Math.random()*100)+1;
        URL url = null;
        try {
            url = new URL(search);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        URLConnection hc = url.openConnection();
        hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        //    br = new BufferedReader(new InputStreamReader());
        final InputStream in = new BufferedInputStream(hc.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        JSONObject arr = new JSONObject(reader.readLine());
        String title = arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).getJSONObject("data").get("title").toString();
        String imgSrc = arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).getJSONObject("data").get("thumbnail").toString();
        String linkSrc = "https://www.reddit.com"+arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).getJSONObject("data").get("permalink").toString();

        in.close();
        System.out.println(arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).getJSONObject("data").toString());
        System.out.println("Elapsed " + (System.currentTimeMillis() - start) + " ms");


    }
}