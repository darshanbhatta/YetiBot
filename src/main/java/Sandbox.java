package main.java;

import bot.discord.yeti.game.jackpot.JackpotGame;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;
import bot.discord.yeti.game.slot.Slot;
import bot.discord.yeti.game.slot.SlotMachine;
import bot.discord.yeti.game.tictactoe.TicTacToeGame;
import bot.discord.yeti.game.tictactoe.TicTacToeHolder;

import java.io.*;

public class Sandbox {

    public static void main(String[] args) {

        TicTacToeHolder x = new TicTacToeHolder();
        x.getTicTacToeGameArrayList().add(new TicTacToeGame("dsdsd","454","ssdsd"));

        try {
            FileOutputStream fileOut = new FileOutputStream("tictactoe.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(x);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }





    }
}