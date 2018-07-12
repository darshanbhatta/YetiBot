package main.java;

import bot.discord.yeti.game.jackpot.JackpotGame;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;

import java.io.*;

public class Sandbox {

    public static void main(String[] args) {

       JackpotGameHolder rouletteGame = new JackpotGameHolder();
        rouletteGame.getJackPotGame().add(new JackpotGame(true, "5455454545"));
        try {
            FileOutputStream fileOut = new FileOutputStream("jackpot.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(rouletteGame);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }


    }


    }