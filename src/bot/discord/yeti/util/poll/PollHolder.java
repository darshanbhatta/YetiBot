package bot.discord.yeti.util.poll;

import java.io.Serializable;
import java.util.ArrayList;

public class PollHolder implements Serializable{
    ArrayList<Poll> polls = new ArrayList<>();

    public ArrayList<Poll> getPolls() {
        return polls;
    }

    public void setPolls(ArrayList<Poll> polls) {
        this.polls = polls;
    }
}
