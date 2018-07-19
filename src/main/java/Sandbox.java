package main.java;

import bot.discord.yeti.game.blackjack.BlackjackGame;
import bot.discord.yeti.game.blackjack.BlackjackGameHolder;
import bot.discord.yeti.game.connect4.Connect4Game;
import bot.discord.yeti.game.connect4.Connect4Holder;

import java.io.*;
import java.util.Arrays;

public class Sandbox {

    public static void main(String[] args) throws IOException {
/*
        String search = "https://www.reddit.com/r/funny/hot.json?limit=100";

        int ran = (int) (Math.random() * 100) + 1;
        URL url = null;
        try {
            url = new URL(search);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.addRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 Firefox/56.0");
        InputStream mInputStream = httpConn.getInputStream();
String jsonString = "";
        int i = 0;
        while ((i = mInputStream.read()) != -1) {
            jsonString += (char) i;
        }
            JSONObject arr = null;
            try {
                arr = new JSONObject(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        System.out.println(arr.toString());
            String title = arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).toString();
               System.out.println(title);

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
                    System.out.println(category + " " + difficulty  + " " + question + " " + correct_answer + answerChoice );



                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

         RewardUserHolder x = new RewardUserHolder();
        x.getRewardUsers().add(new RewardUser("dsdsd"));
        try {
            FileOutputStream fileOut = new FileOutputStream("claim.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(x);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }


String list = "0  - \"Create Instant Invite\"\n" +
        "1  - \"Kick Members\"\n" +
        "2  - \"Ban Members\"\n" +
        "3  - \"Administrator\"\n" +
        "4  - \"Manage Channels\"\n" +
        "5  - \"Manage Server\"\n" +
        "6  - \"Add Reactions\"\n" +
        "7  - \"View Audit Logs\"\n" +
        "10 - \"Read Text Channels & See Voice Channels\"\n" +
        "11 - \"Send Messages\"\n" +
        "12 - \"Send TTS Messages\"\n" +
        "13 - \"Manage Messages\"\n" +
        "14 - \"Embed Links\"\n" +
        "15 - \"Attach Files\"\n" +
        "16 - \"Read History\"\n" +
        "17 - \"Mention Everyone\"\n" +
        "18 - \"Use External Emojis\"\n" +
        "20 - \"Connect\"\n" +
        "21 - \"Speak\"\n" +
        "22 - \"Mute Members\"\n" +
        "23 - \"Deafen Members\"\n" +
        "24 - \"Move Members\"\n" +
        "25 - \"Use Voice Activity\"\n" +
        "26 - \"Change Nickname\"\n" +
        "27 - \"Manage Nicknames\"\n" +
        "28 - \"Manage Roles\"\n" +
        "29 - \"Manage Webhooks\"\n" +
        "30 - \"Manage Emojis\"";



        System.out.println(list);
String code = "!role add woah man <15, 16>";
        String allPerm = code.substring(code.indexOf("<")+1,code.indexOf(">"));
allPerm = allPerm.replace(" ", "");
String allPer[] = allPerm.split(",");
int[] allPerNum = new int[allPer.length];
boolean isVaild = true;
for(int x=0;x<allPer.length;x++){
    try{

        allPerNum[x] = Integer.parseInt(allPer[x]);

    }catch (Exception e){
        isVaild = false;
        break;
    }

}
if(isVaild){

    System.out.println(Arrays.toString(allPerNum));
}else{


}


    }


        Connect4Holder x = new Connect4Holder();
        x.getConnect4GameArrayList().add(new Connect4Game("343434", "dsd", "ddsd"));
        try {
            FileOutputStream fileOut = new FileOutputStream("connect4.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(x);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }

    }
     */
for(int x=0;x<100;x++) {
    int rollNumber = (int) ((Math.random() * (37)));
    System.out.println(rollNumber);
    String icon = "";

    if (rollNumber == 1 || rollNumber == 3 || rollNumber == 5 || rollNumber == 7 || rollNumber == 9 || rollNumber == 12 || rollNumber == 14 || rollNumber == 16 || rollNumber == 18 || rollNumber == 19 || rollNumber == 21 || rollNumber == 23 || rollNumber == 25 || rollNumber == 27 || rollNumber == 30 || rollNumber == 32 || rollNumber == 34 || rollNumber == 36) {
        icon = "🔴  Red";

        //red

    } else if (rollNumber == 2 || rollNumber == 4 || rollNumber == 6 || rollNumber == 8 || rollNumber == 10 || rollNumber == 11 || rollNumber == 13 || rollNumber == 15 || rollNumber == 17 || rollNumber == 20 || rollNumber == 22 || rollNumber == 24 || rollNumber == 26 || rollNumber == 28 || rollNumber == 29 || rollNumber == 31 || rollNumber == 33 || rollNumber == 35) {
        icon = "⚫ Black";
        //black

    } else if (rollNumber == 0) {
        icon = "🍀 Green";
        //green


    }

    System.out.println(icon);
}

    }
}