package bot.discord.yeti.game.slot;

import java.util.Random;

public class SlotMachine {
    private final Slot[] slotTypes = Slot.values();
    public final static String emptySlot = "◻";
    private final Random ran;
    private final int[] vals;
    private final int col;
    private Slot winSlot = slotTypes[0];
    private int winSlotTimes = 0;
    private int winMultiplier = 0;
    private int currentCol;

    public SlotMachine() {
        ran = new Random();
        col = 3;
        currentCol = 0;
        vals = new int[col];
    }

    public void play() {
        if (currentCol < col) {
            vals[currentCol] = ran.nextInt(slotTypes.length) + slotTypes.length;
            currentCol++;
        }
        if (!gameInProgress()) {
            winnerCalc();
        }
    }

    private void winnerCalc() {

        if (vals[1] == vals[2] && slotTypes[vals[1] % slotTypes.length].getX2() > 0) {
            winSlot = slotTypes[vals[1] % slotTypes.length];
            winMultiplier = slotTypes[vals[1] % slotTypes.length].getX2();
            winSlotTimes = 2;
        } else if (vals[0] == vals[1] && vals[1] == vals[2]) {
            winSlot = slotTypes[vals[0] % slotTypes.length];
            winMultiplier = slotTypes[vals[0] % slotTypes.length].getX3();
            winSlotTimes = 3;
        } else if ((vals[0] == vals[1] || vals[0] == vals[2]) && slotTypes[vals[0] % slotTypes.length].getX2() > 0) {
            winSlot = slotTypes[vals[0] % slotTypes.length];
            winMultiplier = slotTypes[vals[0] % slotTypes.length].getX2();
            winSlotTimes = 2;

        } else {
            for (int result : vals) {
                if (slotTypes[result % slotTypes.length].getX1() > 0) {
                    winSlot = slotTypes[result % slotTypes.length];
                    winMultiplier = slotTypes[result % slotTypes.length].getX1();
                    winSlotTimes = 1;
                    break;
                }
            }
        }
    }

    public int getWinMultiplier() {
        return winMultiplier;
    }

    public boolean gameInProgress() {
        return col > currentCol;
    }

    private String getIconForIndex(int i) {
        if (i <= 0) {
            return emptySlot;
        }
        return slotTypes[i % slotTypes.length].getpic();
    }

    @Override
    public String toString() {
        String table = "\uD83C\uDFB0 Slot Machine" + "\n";
        String[] machineLine = new String[col];
        for (int i = 0; i < col; i++) {
            machineLine[i] = "";
        }
        int totalrows = 3;
        for (int col = 0; col < this.col; col++) {
            int offset = -1;
            for (int row = 0; row < totalrows; row++) {
                if (vals[col] > 0) {
                    machineLine[row] += "|" + getIconForIndex(offset + vals[col]);
                } else {
                    machineLine[row] += "|" + getIconForIndex(vals[col]);
                }
                offset++;
            }

        }
        for (int i = 0; i < col; i++) {
            table += machineLine[i] + "|" + "\n";
        }
        return table;
    }

}