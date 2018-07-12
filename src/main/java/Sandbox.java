package main.java;

import bot.discord.yeti.game.roulette.Roulette;
import bot.discord.yeti.game.roulette.RouletteGame;
import bot.discord.yeti.game.roulette.RouletteGameHolder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sandbox {

    public static void main(String[] args) {

        RouletteGameHolder rouletteGame = new RouletteGameHolder();
        rouletteGame.getRouletteGames().add(new RouletteGame(true, "5455454545"));
        try {
            FileOutputStream fileOut = new FileOutputStream("roulette.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(rouletteGame);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }


    }
}