package bot.discord.yeti.game.tictactoe;

import java.io.Serializable;
import java.util.ArrayList;

public class TicTacToeGame implements Serializable {

    private String userid;
    private String userid2;
    private int turn;

    public String getName() {
        return name;
    }

    private String name;
    private String name2 = "";

    public String getUserid() {
        return userid;
    }

    public String getUserid2() {
        return userid2;
    }

    public int getBet() {
        return bet;
    }

    public int getBet2() {
        return bet2;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    int bet;
    int bet2;
    private boolean whoGoes = true;
    private ArrayList<Integer> alreadyPicked = new ArrayList<Integer>();
    private String gameBoard = "123456789";


    public TicTacToeGame(String userid, String userid2, String name) {
        this.userid = userid;
        this.userid2 = userid2;
        this.name = name;
    }

    public int move(int where, String whoSaid,String whoIs){
if(whoSaid.equals(userid2)&&name2.equals("")){
    name2 = whoIs;
}

        if((whoGoes==true&&userid.equals(whoSaid))||(whoGoes==false&&userid2.equals(whoSaid))){
            whoGoes=!whoGoes;
            if(!alreadyPicked.contains(where)) {

                if(whoGoes){
                    gameBoard = gameBoard.substring(0, where - 1) + "X" + gameBoard.substring(where);
                }else{
                    gameBoard = gameBoard.substring(0, where - 1) + "O" + gameBoard.substring(where);

                }
                turn++;
                if(turn==9&&(!isWinnerX()||!isWinnerY())){
                    return 3;
                }
                return 0;


            }else{

                return  2;

            }





        }



        return 1;



    }


    public boolean isWinnerX() {
        char who = 'X';
        if (gameBoard.charAt(7 - 1) == who && gameBoard.charAt(8 - 1) == who && gameBoard.charAt(9 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(4 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(6 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(1 - 1) == who && gameBoard.charAt(2 - 1) == who && gameBoard.charAt(3 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(7 - 1) == who && gameBoard.charAt(4 - 1) == who && gameBoard.charAt(1 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(8 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(2 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(9 - 1) == who && gameBoard.charAt(6 - 1) == who && gameBoard.charAt(3 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(7 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(3 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(9 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(1 - 1) == who) {

            return true;
        } else {
            return false;
        }

    }

    public boolean isWinnerY() {
        char who = 'O';
        if (gameBoard.charAt(7 - 1) == who && gameBoard.charAt(8 - 1) == who && gameBoard.charAt(9 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(4 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(6 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(1 - 1) == who && gameBoard.charAt(2 - 1) == who && gameBoard.charAt(3 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(7 - 1) == who && gameBoard.charAt(4 - 1) == who && gameBoard.charAt(1 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(8 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(2 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(9 - 1) == who && gameBoard.charAt(6 - 1) == who && gameBoard.charAt(3 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(7 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(3 - 1) == who) {

            return true;
        } else if (gameBoard.charAt(9 - 1) == who && gameBoard.charAt(5 - 1) == who && gameBoard.charAt(1 - 1) == who) {

            return true;
        } else {
            return false;
        }

    }


    @Override
    public String toString() {

        String board = "";

        for (int x = 0; x < gameBoard.length(); x++) {
            if (x == 3 || x == 6) {
                board += "\n";

            }
            if (gameBoard.charAt(x) == 'X') {
                board += ":x:";
            } else if (gameBoard.charAt(x) == 'O') {
                board += ":o:";
            } else {

                if (gameBoard.charAt(x) == '1') {
                    board += ":one:";

                } else if (gameBoard.charAt(x) == '2') {
                    board += ":two:";
                } else if (gameBoard.charAt(x) == '3') {
                    board += ":three:";
                } else if (gameBoard.charAt(x) == '4') {
                    board += ":four:";
                } else if (gameBoard.charAt(x) == '5') {
                    board += ":five:";
                } else if (gameBoard.charAt(x) == '6') {
                    board += ":six:";
                } else if (gameBoard.charAt(x) == '7') {
                    board += ":seven:";
                } else if (gameBoard.charAt(x) == '8') {
                    board += ":eight:";
                } else if (gameBoard.charAt(x) == '9') {
                    board += ":nine:";
                }


            }

        }

return  board;
    }


}
