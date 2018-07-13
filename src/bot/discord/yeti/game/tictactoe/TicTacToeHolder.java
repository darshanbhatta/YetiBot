package bot.discord.yeti.game.tictactoe;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TicTacToeHolder implements Serializable {

    ArrayList<TicTacToeGame> ticTacToeGameArrayList = new ArrayList<>();

    public ArrayList<TicTacToeGame> getTicTacToeGameArrayList() {
        return ticTacToeGameArrayList;
    }

    public void setTicTacToeGameArrayList(ArrayList<TicTacToeGame> ticTacToeGameArrayList) {
        this.ticTacToeGameArrayList = ticTacToeGameArrayList;
    }
}
