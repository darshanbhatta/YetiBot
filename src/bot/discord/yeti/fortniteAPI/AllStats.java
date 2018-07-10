package bot.discord.yeti.fortniteAPI;

public class AllStats {
    private StatsPC pc;
    private StatsPS4 ps4;
    private StatsXB1 xb1;
    public AllStats(StatsPC pc, StatsPS4 ps4, StatsXB1 xb1){

        this.pc = pc;
        this.ps4 = ps4;
        this.xb1 = xb1;



    }

    public StatsPC getPc() {
        return pc;
    }

    public StatsPS4 getPs4() {
        return ps4;
    }

    public StatsXB1 getXb1() {
        return xb1;
    }
}
