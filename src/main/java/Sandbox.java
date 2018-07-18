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
import bot.discord.yeti.game.trivia.TriviaGame;
import bot.discord.yeti.game.trivia.TriviaHolder;
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
import java.util.ArrayList;
import java.util.Collections;

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

           URL url = null;

        try {
            url = new URL("https://opentdb.com/api.php?amount=10");
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                JSONObject arr = new JSONObject(br.readLine());
                //String text =   obj.getJSONObject("pvp_sales").toString();
                //  JSONObject arr = obj.getJSONObject("player");

                System.out.println(arr.toString());

                for (int i = 0; i < 10; i++)
                {
                    ArrayList<String> answerChoice = new ArrayList<>();

                    String category = arr.getJSONArray("results").getJSONObject(i).get("category").toString();
                    String difficulty = arr.getJSONArray("results").getJSONObject(i).get("difficulty").toString();
                    String question = arr.getJSONArray("results").getJSONObject(i).get("question").toString();
                    String correct_answer = arr.getJSONArray("results").getJSONObject(i).get("correct_answer").toString();
                    answerChoice.add(correct_answer);
for(int x=0;x<arr.getJSONArray("results").getJSONObject(i).getJSONArray("incorrect_answers").length();x++){

    answerChoice.add(arr.getJSONArray("results").getJSONObject(i).getJSONArray("incorrect_answers").get(x).toString());

}
if(answerChoice.size()!=2){
    Collections.shuffle(answerChoice);
}
                    System.out.println(category + " " + difficulty  + " " + question + " " + correct_answer + answerChoice );



                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        */

        TriviaHolder x = new TriviaHolder();
        x.getGames().add(new TriviaGame("dsdsd"));
        try {
            FileOutputStream fileOut = new FileOutputStream("trivia.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(x);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }

    }

}