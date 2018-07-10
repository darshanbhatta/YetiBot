package main.java;

import bot.discord.yeti.game.slot.SlotMachine;

public class Sandbox {

    public static void main(String[] args) {



        for(int w=0;w<100;w++) {
            SlotMachine machine = new SlotMachine();
            for (int x = 0; x < 3; x++) {
                machine.play();
            }
            if(machine.getWinMultiplier()==4) {
                System.out.println(machine.toString());
                System.out.println(machine.getWinMultiplier());
                System.out.println();
            }
        }
    }
}