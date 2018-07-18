package bot.discord.yeti.game.trivia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TriviaHolder implements Serializable {

    ArrayList<TriviaGame> games = new ArrayList<>();

    public ArrayList<TriviaGame> getGames() {
        return games;
    }

    public void setGames(ArrayList<TriviaGame> games) {
        this.games = games;
    }
}
