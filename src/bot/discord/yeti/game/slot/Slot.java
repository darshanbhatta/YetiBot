package bot.discord.yeti.game.slot;

public enum Slot {
    BALL("Ball", "\uD83C\uDFB1", 10),
    CROWN("Crown", "\uD83D\uDC51", 10),
    BALLL("Ball", "\uD83C\uDFB1", 10),
    CROWNN("Crown", "\uD83D\uDC51", 10),
    SEVEN("Seven", ":seven:", 30),
    BELL("Bell", "\uD83D\uDD14", 10),
    DICE("Dice", "\uD83C\uDFB2", 10),
    CHERRY("Cherry", "\uD83C\uDF52", 10),
    BELLL("Bell", "\uD83D\uDD14", 10),
    DICEE("Dice", "\uD83C\uDFB2", 10),
    CHERRYY("Cherry", "\uD83C\uDF52", 10);


    private final String name;
    private final String pic;
    private final int X3;
    private final int X2;
    private final int X1;

    Slot(String name, String pic, int X3) {
        this(name, pic, X3, 0, 0);
    }

    Slot(String name, String pic, int X3, int X2) {
        this(name, pic, X3, X2, 0);
    }

    Slot(String name, String pic, int X3, int X2, int X1) {

        this.name = name;
        this.pic = pic;
        this.X3 = X3;
        this.X2 = X2;
        this.X1 = X1;
    }

    public int getX3() {
        return X3;
    }

    public String getpic() {
        return pic;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return pic;
    }

    public int getX2() {
        return X2;
    }

    public int getX1() {
        return X1;
    }


}
