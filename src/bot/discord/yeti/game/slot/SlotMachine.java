package bot.discord.yeti.game.slot;

import java.util.Arrays;
import java.util.Random;

public class SlotMachine {
    private final Slot[] slotTypes = Slot.values();
    public final static String emptySlot = "◻";
    private final int[][] vals;
    private final int col;
    private int row = 0;
    private Slot winSlot = slotTypes[0];
    private int winSlotTimes = 0;
    private int winMultiplier = 0;
    private int currentCol;
    private int currentRow;

    public SlotMachine() {
        // ran = new Random();
        row = 3;
        col = 3;
        currentCol = 0;
        currentRow = 0;
        vals = new int[row][col];
    }

    public void play() {
        for (int x = 0; x < 3; x++) {

            int first = (int) (Math.random() * slotTypes.length);
            vals[currentRow][currentCol] = first;
            if(first==2){
                first=0;
            }else if(first==3){
                first=1;
            }else if(first==8){
                first=5;
            }else if(first==9){
                first=6;
            }else if(first==10){
                first=7;
            }
            currentCol++;
            if (Math.random() < .2) {
                vals[currentRow][currentCol] = first;

            } else {
                int second = (int) (Math.random() * slotTypes.length);
                if(second==2){
                    second=0;
                }else if(second==3){
                    second=1;
                }else if(second==8){
                    second=5;
                }else if(second==9){
                    second=6;
                }else if(second==10){
                    second=7;
                }
                vals[currentRow][currentCol] = second;

            }
            currentCol++;
            if (Math.random() < .3&&vals[currentRow][currentCol-1]==vals[currentRow][currentCol-2]) {
                vals[currentRow][currentCol] = first;

            } else {
                int third = (int) (Math.random() * slotTypes.length);
                if(third==2){
                    third=0;
                }else if(third==3){
                    third=1;
                }else if(third==8){
                    third=5;
                }else if(third==9){
                    third=6;
                }else if(third==10){
                    third=7;
                }
                vals[currentRow][currentCol] = third;

            }

            currentRow++;
            currentCol = 0;

        }
        winnerCalc();
        System.out.println(Arrays.deepToString(vals));


    }


    private void winnerCalc() {
        winMultiplier = 0;
       if (vals[0][0] == vals[0][1] && vals[0][1] == vals[0][2]) {
            winMultiplier = slotTypes[vals[0][0]].getX3();


        }
        if (vals[1][0] == vals[1][1] && vals[1][1] == vals[1][2]) {
            if(winMultiplier<slotTypes[vals[1][0]].getX3())
            winMultiplier = slotTypes[vals[1][0]].getX3();

        }
        if (vals[2][0] == vals[2][1] && vals[2][1] == vals[2][2]) {
            if(winMultiplier<slotTypes[vals[2][0]].getX3())
            winMultiplier = slotTypes[vals[2][0]].getX3();

        }




    }

    public int getWinMultiplier() {
        return winMultiplier;
    }

    public boolean gameInProgress() {
        return col > currentCol;
    }

    private String getIconForIndex(int i) {
        if (i < 0) {
            return emptySlot;
        }
        return slotTypes[i].getpic();
    }

    public String empty() {
        String table = ":slot_machine: Slots :slot_machine: " + "\n";
        for (int x = 0; x < 3; x++) {
            table += "|" + emptySlot;
            table += "|" + emptySlot;
            table += "|" + emptySlot + "|\n";

        }

        return table;
    }


    public String firstIter() {
        String table = ":slot_machine: Slots :slot_machine: " + "\n";
        for (int x = 0; x < 3; x++) {
            table += "|" + getIconForIndex(vals[x][0]);
            table += "|" + emptySlot;
            table += "|" + emptySlot + "|\n";

        }

return table;
    }

    public String secondIter() {
        String table = ":slot_machine: Slots :slot_machine: " + "\n";
        for (int x = 0; x < 3; x++) {
            table += "|" + getIconForIndex(vals[x][0]);
            table += "|" + getIconForIndex(vals[x][1]);
            table += "|" + emptySlot + "|\n";

        }

        return table;
    }

    public String thirdIter() {
        String table = ":slot_machine: Slots :slot_machine: " + "\n";
        for (int x = 0; x < 3; x++) {
            table += "|" + getIconForIndex(vals[x][0]);
            table += "|" + getIconForIndex(vals[x][1]);
            table += "|" + getIconForIndex(vals[x][2]) + "|\n";

        }

        return table;
    }

}