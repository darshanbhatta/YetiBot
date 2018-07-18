package bot.discord.yeti.game.trivia;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class TriviaGame implements Serializable {

    ArrayList<TriviaQuestion> questions = new ArrayList<>();
    String id;

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    int currentQuestion =0;

    public ArrayList<TriviaQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<TriviaQuestion> questions) {
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TriviaGame(String serverId){
        id = serverId;

        URL url = null;
        try {
            url = new URL("https://opentdb.com/api.php?amount=10");
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                JSONObject arr = new JSONObject(br.readLine());
                //String text =   obj.getJSONObject("pvp_sales").toString();
                //  JSONObject arr = obj.getJSONObject("player");

                System.out.println(arr.toString());

                for (int i = 0; i < 10; i++)
                {
                    ArrayList<String> answerChoice = new ArrayList<>();

                    String category = arr.getJSONArray("results").getJSONObject(i).get("category").toString();
                    String difficulty = arr.getJSONArray("results").getJSONObject(i).get("difficulty").toString();
                    String question = arr.getJSONArray("results").getJSONObject(i).get("question").toString();
                    String correct_answer = arr.getJSONArray("results").getJSONObject(i).get("correct_answer").toString();
                    answerChoice.add(correct_answer);
                    for(int x=0;x<arr.getJSONArray("results").getJSONObject(i).getJSONArray("incorrect_answers").length();x++){

                        answerChoice.add(arr.getJSONArray("results").getJSONObject(i).getJSONArray("incorrect_answers").get(x).toString());

                    }
                    if(answerChoice.size()!=2){
                        Collections.shuffle(answerChoice);
                    }
                    questions.add(new TriviaQuestion(category,difficulty,question,correct_answer,answerChoice));



                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }




    }
