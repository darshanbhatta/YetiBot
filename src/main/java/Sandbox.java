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

import java.io.*;

public class Sandbox {

    public static void main(String[] args) {

        NumGuessHolder x = new NumGuessHolder();
        x.getNumGuessHolders().add(new NumGuessGame("43434",454));

        try {
            FileOutputStream fileOut = new FileOutputStream("numguess.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(x);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }
        System.out.println((int)(Math.random()*(100))+1);




    }
}