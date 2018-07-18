package bot.discord.yeti;


import bot.discord.yeti.game.trivia.TriviaGame;
import bot.discord.yeti.game.trivia.TriviaHolder;
import bot.discord.yeti.game.trivia.TriviaUser;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;

import java.awt.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class TriviaManager {
   private Timer timer = new Timer();
   public void run(MessageReceivedEvent event){
       //!trivia init //!trivia A/B/C/D
       String[] code = event.getMessage().getContentRaw().split(" ");

       final TriviaHolder[] triviaHolders = new TriviaHolder[1];
       try {
           FileInputStream fileIn = new FileInputStream("trivia.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           triviaHolders[0] = (TriviaHolder) in.readObject();
           in.close();
           fileIn.close();
       } catch (IOException i) {
           i.printStackTrace();
           return;
       } catch (ClassNotFoundException c) {
           c.printStackTrace();
           return;
       }


       if(code.length==2&&(code[1].equals("init")||code[1].equals("start"))){
           System.out.println("working1");
String serverid = event.getGuild().getId();
boolean gameInProg = false;
           for (int x = 0; x < triviaHolders[0].getGames().size(); x++) {


               if (serverid.equals(triviaHolders[0].getGames().get(x).getId())) {

                   gameInProg = true;
break;

               }
           }

           if(!gameInProg){
               triviaHolders[0].getGames().add(new TriviaGame(serverid));

event.getChannel().sendMessage("Trivia Game started! You will get 10 questions and 15 seconds to answer each one!").queue();
               try {
                   FileOutputStream fileOut = new FileOutputStream("trivia.ser");
                   ObjectOutputStream out = new ObjectOutputStream(fileOut);
                   out.writeObject(triviaHolders[0]);
                   out.close();
                   fileOut.close();
               } catch (IOException ww) {
                   ww.printStackTrace();
               }
               final int[] num = {0};

                       triviaHolders[0].getGames().get(triviaHolders[0].getGames().size() - 1).getQuestions().get(num[0]).getAnswerChoices();
String an = triviaHolders[0].getGames().get(triviaHolders[0].getGames().size() - 1).getQuestions().get(num[0]).getQuestion();
               String doc = Jsoup.parse(an).text();
                       EmbedBuilder eb = new EmbedBuilder();
                       eb.setTitle((num[0] + 1) + ") " +doc );
                       String build = "";
                       for (int w = 0; w < triviaHolders[0].getGames().get(triviaHolders[0].getGames().size() - 1).getQuestions().get(num[0]).getAnswerChoices().size(); w++) {
                           build += (w + 1) + ") " + triviaHolders[0].getGames().get(triviaHolders[0].getGames().size() - 1).getQuestions().get(num[0]).getAnswerChoices().get(w) + "\n";
                       }
if( triviaHolders[0].getGames().get(triviaHolders[0].getGames().size() - 1).getQuestions().get(num[0]).getDifficulty().equals("easy")){
      eb.setColor(new Color(51, 204, 51));
}else if(triviaHolders[0].getGames().get(triviaHolders[0].getGames().size() - 1).getQuestions().get(num[0]).getDifficulty().equals("medium")){
    eb.setColor(new Color(255, 153, 0));
}else{
    eb.setColor(new Color(255, 77, 77));
}
               eb.setAuthor(triviaHolders[0].getGames().get(triviaHolders[0].getGames().size() - 1).getQuestions().get(num[0]).getCategory());
                       eb.setDescription(build);

                       event.getChannel().sendMessage(eb.build()).queue();



                   TimerTask w = new TimerTask() {
                       @Override
                       public void run() {
                           if(num[0]<=10){
                               try {
                                   FileInputStream fileIn = new FileInputStream("trivia.ser");
                                   ObjectInputStream in = new ObjectInputStream(fileIn);
                                   triviaHolders[0] = (TriviaHolder) in.readObject();
                                   in.close();
                                   fileIn.close();
                               } catch (IOException i) {
                                   i.printStackTrace();
                                   return;
                               } catch (ClassNotFoundException c) {
                                   c.printStackTrace();
                                   return;
                               }

                               int ind = -1;
                               for (int w = 0; w < triviaHolders[0].getGames().size(); w++) {


                                   if (serverid.equals(triviaHolders[0].getGames().get(w).getId())) {
                                       ind = w;

                                       break;

                                   }
                               }
                               triviaHolders[0].getGames().get(ind).setCurrentQuestion(triviaHolders[0].getGames().get(ind).getCurrentQuestion() + 1);

                               if (ind != -1) {

                                   String users = "";
                                   for (int i = 0; i < triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getUsers().size(); i++) {
                                       if (triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getUsers().get(i).getAnswer().equals(triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getCorrecAnswer())) {
                                           users += triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getUsers().get(i).getName();


                                       }
                                   }
                                   EmbedBuilder embedBuilder = new EmbedBuilder();
                                   int which = 0;
                                   for (int v = 0; v < triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getAnswerChoices().size(); v++) {
                                       if (triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getAnswerChoices().get(v).equals(triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getCorrecAnswer())) {

                                           which = v + 1;
                                           break;


                                       }

                                   }
                                   embedBuilder.setTitle(which + ") " + triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getCorrecAnswer());
                                   if (users.equals("")) {
                                       embedBuilder.setDescription("No one got it correct");
                                       embedBuilder.setColor(new Color(255, 77, 77));
                                   } else {
                                       embedBuilder.setColor(new Color(51, 204, 51));
                                       embedBuilder.setDescription("Correct answers\n" + users);

                                   }

                                   event.getChannel().sendMessage(embedBuilder.build()).queue();
                                   try {
                                       FileOutputStream fileOut = new FileOutputStream("trivia.ser");
                                       ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                       out.writeObject(triviaHolders[0]);
                                       out.close();
                                       fileOut.close();
                                   } catch (IOException ww) {
                                       ww.printStackTrace();
                                   }
                                   num[0]++;
                                   try{
                                       triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getAnswerChoices();

                                       EmbedBuilder eb = new EmbedBuilder();
                                       String an = triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getQuestion();
                                       String doc = Jsoup.parse(an).text();

                                       eb.setTitle((num[0] + 1) + ") " + doc);
                                       String build = "";
                                       for (int w = 0; w < triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getAnswerChoices().size(); w++) {
                                           build += (w + 1) + ") " + triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getAnswerChoices().get(w) + "\n";
                                       }

                                       eb.setDescription(build);
                                       if( triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getDifficulty().equals("easy")){
                                           eb.setColor(new Color(51, 204, 51));
                                       }else if(triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getDifficulty().equals("medium")){
                                           eb.setColor(new Color(255, 153, 0));
                                       }else{
                                           eb.setColor(new Color(255, 77, 77));
                                       }
                                       eb.setAuthor(triviaHolders[0].getGames().get(ind).getQuestions().get(num[0]).getCategory());
                                       event.getChannel().sendMessage(eb.build()).queue();


                                   }catch (Exception woah){
                                       triviaHolders[0].getGames().remove(ind);
                                       try {
                                           FileOutputStream fileOut = new FileOutputStream("trivia.ser");
                                           ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                           out.writeObject(triviaHolders[0]);
                                           out.close();
                                           fileOut.close();
                                       } catch (IOException ww) {
                                           ww.printStackTrace();
                                       }
                                       timer.cancel();


                                   }



                               }else{
triviaHolders[0].getGames().remove(ind);
                                   try {
                                       FileOutputStream fileOut = new FileOutputStream("trivia.ser");
                                       ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                       out.writeObject(triviaHolders[0]);
                                       out.close();
                                       fileOut.close();
                                   } catch (IOException ww) {
                                       ww.printStackTrace();
                                   }
                                   timer.cancel();
                               }


                           } else {
                               event.getChannel().sendMessage("Error no current game!").queue();
                               //error no game

                           }




                       }
                   };
                   timer.scheduleAtFixedRate(w,15000,15000);












           }else{
               event.getChannel().sendMessage("Game in progress").queue();
               //game in prog

           }



//!trivia 1

       }else if(code.length==2&&code[1].equals("quit")){
           String serverid = event.getGuild().getId();
           int ind = -1;
           for (int w = 0; w < triviaHolders[0].getGames().size(); w++) {


               if (serverid.equals(triviaHolders[0].getGames().get(w).getId())) {
                   ind = w;

                   break;

               }

           }

           if(ind!=-1){
               triviaHolders[0].getGames().remove(ind);
               try {
                   FileOutputStream fileOut = new FileOutputStream("trivia.ser");
                   ObjectOutputStream out = new ObjectOutputStream(fileOut);
                   out.writeObject(triviaHolders[0]);
                   out.close();
                   fileOut.close();
               } catch (IOException ww) {
                   ww.printStackTrace();
               }
               timer.cancel();
               event.getChannel().sendMessage("Ended trivia game").queue();



           }else{

               event.getChannel().sendMessage("Error no current games").queue();


           }


       }else if(code.length==2){
           try{
               int choice = Integer.parseInt(code[1]);
               int ind = -1;
               String serverid = event.getGuild().getId();
               for (int w = 0; w < triviaHolders[0].getGames().size(); w++) {


                   if (serverid.equals(triviaHolders[0].getGames().get(w).getId())) {
                       ind = w;

                       break;

                   }
               }

               if(ind!=-1){
                   int a = -1;
                   for (int w = 0; w < triviaHolders[0].getGames().get(ind).getQuestions().get(triviaHolders[0].getGames().get(ind).getCurrentQuestion()).getUsers().size(); w++) {


                       if (triviaHolders[0].getGames().get(ind).getQuestions().get(triviaHolders[0].getGames().get(ind).getCurrentQuestion()).getUsers().get(w).getId().equals(event.getAuthor().getId())) {
                           a = w;

                           break;

                       }
                   }
                   System.out.println(a);
                   if(a==-1){
                       String ans =  triviaHolders[0].getGames().get(ind).getQuestions().get( triviaHolders[0].getGames().get(ind).getCurrentQuestion()).getAnswerChoices().get(choice-1);
                       triviaHolders[0].getGames().get(ind).getQuestions().get( triviaHolders[0].getGames().get(ind).getCurrentQuestion()).getUsers().add(new TriviaUser(event.getAuthor().getId(),event.getAuthor().getName(),ans));
                       try {
                           FileOutputStream fileOut = new FileOutputStream("trivia.ser");
                           ObjectOutputStream out = new ObjectOutputStream(fileOut);
                           out.writeObject(triviaHolders[0]);
                           out.close();
                           fileOut.close();
                       } catch (IOException ww) {
                           ww.printStackTrace();
                       }

                   }else{

                       event.getChannel().sendMessage("Error you have already answered").queue();

                   }


               }else{
                   event.getChannel().sendMessage("Error no current game!").queue();
                   //no current game

               }


           }catch (Exception e){
               System.out.println(e.toString());
               event.getChannel().sendMessage("Answer choice has to be a number").queue();
               //else not an answerchoice
           }




       }else{

           event.getChannel().sendMessage("Error invalid command. Try !trivia start to start the game and !trivia %answer choice%").queue();
       }



   }

}
