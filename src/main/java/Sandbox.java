package main.java;

import bot.discord.yeti.game.jackpot.JackpotGame;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;
import bot.discord.yeti.game.slot.Slot;
import bot.discord.yeti.game.slot.SlotMachine;

import java.io.*;

public class Sandbox {

    public static void main(String[] args) {
        double count = 0;

for(int x=0;x<10000;x++){
    SlotMachine game = new SlotMachine();
    game.play();
    System.out.println(game.thirdIter());
    System.out.println(game.getWinMultiplier());

    if(game.getWinMultiplier()==5){
        count++;
    }
}
        System.out.println((double)count/10000);






}
}