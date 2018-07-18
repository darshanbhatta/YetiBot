package bot.discord.yeti.game.trivia;

import java.io.Serializable;

public class TriviaUser implements Serializable {
    String id;
    String name;
    String answerChoice;

    public TriviaUser(String id, String name, String answerChoice) {
        this.id = id;
        this.name = name;
        this.answerChoice = answerChoice;
    }

    public String getAnswer() {
        return answerChoice;
    }

    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
