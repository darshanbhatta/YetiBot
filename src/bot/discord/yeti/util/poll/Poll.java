package bot.discord.yeti.util.poll;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Poll implements Serializable {
    int[] choices;
    ArrayList<String> choi = new ArrayList();
    ArrayList<String> users = new ArrayList<>();
    String serverID;

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public Poll(String poll){
        serverID = poll;

    }

    public void setUpChoices(int amount){

        choices = new int[amount];

    }

    public int[] getChoices() {
        return choices;
    }

    public void setChoices(int[] choices) {
        this.choices = choices;
    }

    public ArrayList<String> getChoi() {
        return choi;
    }

    public void setChoi(ArrayList<String> choi) {
        this.choi = choi;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}
