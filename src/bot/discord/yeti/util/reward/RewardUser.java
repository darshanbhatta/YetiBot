package bot.discord.yeti.util.reward;

import java.io.Serializable;

public class RewardUser implements Serializable {
    String userid;
    long hourTime =0;
    long dailyTime =0;

    public RewardUser(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getHourTime() {
        return hourTime;
    }

    public void setHourTime(long hourTime) {
        this.hourTime = hourTime;
    }

    public long getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(long dailyTime) {
        this.dailyTime = dailyTime;
    }
}
