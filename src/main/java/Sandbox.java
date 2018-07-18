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
import java.net.MalformedURLException;
import java.net.URL;

public class Sandbox {

    public static void main(String[] args) {

        PollHolder x = new PollHolder();

        x.getPolls().add(new Poll("sdsds"));
        try {
            FileOutputStream fileOut = new FileOutputStream("poll.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(x);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }





    }
}