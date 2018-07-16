package bot.discord.yeti.game.connect4;

import java.io.Serializable;
import java.util.ArrayList;

public class Connect4Game implements Serializable {

    private String userid;
    private String userid2;
    private int turn;

    public String getName() {
        return name;
    }

    private String name;
    private String name2 = "";
    private int[][] gameBoard  = new int[6][7];

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


    public Connect4Game(String userid, String userid2, String name) {
        this.userid = userid;
        this.userid2 = userid2;
        this.name = name;
    }

    public int move(int where, String whoSaid,String whoIs){
if(whoSaid.equals(userid2)&&name2.equals("")){
    name2 = whoIs;
}

        if((whoGoes==true&&userid.equals(whoSaid))||(whoGoes==false&&userid2.equals(whoSaid))){
    int wh = updategameBoard(where-1);
            if(wh!=-1) {
                whoGoes=!whoGoes;
                turn++;
                if(turn==42&&(checkWin()==0)){
                    return 3;
                }
                return 0;


            }else{

                return  2;

            }





        }



        return 1;



    }

    public int updategameBoard(int column) {
        for (int row = gameBoard.length - 1; row >= 0; row--) {

            if (whoGoes) {
                if (gameBoard[row][column] == 0) {
                    gameBoard[row][column] = 1;
                    return row;

                }
            } else if (gameBoard[row][column] == 0) {
                gameBoard[row][column] = 2;
                return row;

            }
        }
        return -1;
    }


    public int checkWin() {
        String gameString = "";

        //horizontal win check
        for (int r = 0; r < gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                gameString = gameString + gameBoard[r][c];
            }
            if (gameString.contains("1111")) {
                return 1;
            } else if (gameString.contains("2222")) {
                return 2;
            }
            gameString = "";
        }

        //vertical win check
        for (int r = 0; r < gameBoard[0].length; r++) {
            for (int c = 0; c < gameBoard.length; c++) {
                gameString = gameString + gameBoard[c][r];
            }
            if (gameString.contains("1111")) {
                return 1;
            } else if (gameString.contains("2222")) {
                return 2;
            }
            gameString = "";
        }

        //ascending diagonal check
        for (int i = 3; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length - 3; j++) {
                if (gameBoard[i][j] == 1 && gameBoard[i - 1][j + 1] == 1 && gameBoard[i - 2][j + 2] == 1 && gameBoard[i - 3][j + 3] == 1)
                    return 1;
                if (gameBoard[i][j] == 2 && gameBoard[i - 1][j + 1] == 2 && gameBoard[i - 2][j + 2] == 2 && gameBoard[i - 3][j + 3] == 2)
                    return 2;

            }
        }

        //descending diagonal check
        for (int i = 3; i < gameBoard.length; i++) {
            for (int j = 3; j < gameBoard[0].length; j++) {
                if (gameBoard[i][j] == 1 && gameBoard[i - 1][j - 1] == 1 && gameBoard[i - 2][j - 2] == 1 && gameBoard[i - 3][j - 3] == 1)
                    return 1;
                if (gameBoard[i][j] == 2 && gameBoard[i - 1][j - 1] == 2 && gameBoard[i - 2][j - 2] == 2 && gameBoard[i - 3][j - 3] == 2)
                    return 2;
            }
        }


        return 0;
    }


    @Override
    public String toString() {

        String board = "";


            for (int r = 0; r < gameBoard.length; r++) {
                for (int c = 0; c < gameBoard[0].length; c++) {
                    if(gameBoard[r][c]==1){
board+="\uD83D\uDD35 ";

                    }else if(gameBoard[r][c]==2){

board+="\uD83D\uDD34 ";
                    }else if(gameBoard[r][c]==0){
board+="⚪ ";
                    }
                    System.out.print(gameBoard[r][c] + " ");
                }
                board+="\n";
            }

return  board;
    }


}
