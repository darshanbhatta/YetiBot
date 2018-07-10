package bot.discord.yeti.currency;

import net.dv8tion.jda.core.entities.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Bank  implements Serializable {
    private ArrayList<Jewels> allBalance = new ArrayList();

    public void addUser(String userId,String name, int balance){
        allBalance.add(new Jewels(userId,name,balance));
    }

    public ArrayList<Jewels> getAllBalance() {
        return allBalance;
    }

    public int getAccountIndex(String id){
        for(int x=0; x<allBalance.size();x++) {
            if (allBalance.get(x).getPlayerId().contains(id)) {
                return x;


            }
        }
        return -1;

    }
}
