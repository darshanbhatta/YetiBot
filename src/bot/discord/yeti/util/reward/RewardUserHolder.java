package bot.discord.yeti.util.reward;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RewardUserHolder implements Serializable {
    ArrayList<RewardUser> rewardUsers = new ArrayList<>();

    public ArrayList<RewardUser> getRewardUsers() {
        return rewardUsers;
    }

    public void setRewardUsers(ArrayList<RewardUser> rewardUsers) {
        this.rewardUsers = rewardUsers;
    }
}
