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
            currentCol++;
            if (Math.random() < .2) {
                vals[currentRow][currentCol] = first;

            } else {
                vals[currentRow][currentCol] = (int) (Math.random() * slotTypes.length);

            }
            currentCol++;
            if (Math.random() < .3&&vals[currentRow][currentCol-1]==vals[currentRow][currentCol-2]) {
                vals[currentRow][currentCol] = first;

            } else {
                vals[currentRow][currentCol] = (int) (Math.random() * slotTypes.length);

            }

            currentRow++;
            currentCol = 0;

        }
        winnerCalc();
        System.out.println(Arrays.deepToString(vals));


    }


    private void winnerCalc() {

       if (vals[0][0] == vals[0][1] && vals[0][1] == vals[0][2]) {
            winMultiplier = slotTypes[vals[0][0]].getX3();

        }
        if (vals[1][0] == vals[1][1] && vals[0][1] == vals[1][2]) {
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