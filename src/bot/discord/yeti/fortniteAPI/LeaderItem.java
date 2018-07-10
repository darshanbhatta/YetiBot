package bot.discord.yeti.fortniteAPI;


public class LeaderItem{

    private final String name;

    private final String wins;


    private final String userData;


    public LeaderItem(final String name,
                       final String wins,
                       final String userData) {
        this.name = name;

        this.wins = wins;
        this.userData = userData;

    }


    public String getName() {
        return name;
    }


    public String getWins() {
        return wins;
    }


    public String getUserData() {
        return userData;
    }


    @Override
    public String toString() {
        return "LaderData{" +
                "name='" + name + '\'' +
                ", wins='" + wins + '\'' +
                ", userData='" + userData + '\'' +

                '}';
    }
}
