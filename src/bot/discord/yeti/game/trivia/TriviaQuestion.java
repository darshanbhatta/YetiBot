package bot.discord.yeti.game.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class TriviaQuestion implements Serializable {
    String category;
    String difficulty;
    String question;
    ArrayList<String> answerChoices = new ArrayList<>();
    ArrayList<TriviaUser>  users = new ArrayList();

    public ArrayList<TriviaUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<TriviaUser> users) {
        this.users = users;
    }

    String correcAnswer;

    public TriviaQuestion(String category, String difficulty, String question, String correcAnswer,ArrayList<String> answerChoices){
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.correcAnswer = correcAnswer;
        this.answerChoices = answerChoices;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(ArrayList<String> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public String getCorrecAnswer() {
        return correcAnswer;
    }

    public void setCorrecAnswer(String correcAnswer) {
        this.correcAnswer = correcAnswer;
    }
}
