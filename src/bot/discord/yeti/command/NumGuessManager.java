package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.game.numguesser.NumGuessGame;
import bot.discord.yeti.game.numguesser.NumGuessHolder;
import bot.discord.yeti.game.numguesser.NumGuessUser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NumGuessManager {

    public static void run(MessageReceivedEvent e) {
        NumGuessHolder[] numGuessHolder = new NumGuessHolder[1];
        String[] code = e.getMessage().getContentRaw().split(" ");
        try {
            FileInputStream fileIn = new FileInputStream("numguess.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            numGuessHolder[0] = (NumGuessHolder) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }

        final Bank[] bank = {new Bank()};
        try {
            FileInputStream fileIn = new FileInputStream("bank.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            bank[0] = (Bank) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }

        //!numguess init 500

        if (code.length == 3 && code[1].equals("start")) {
try{
    int betAmount = Integer.parseInt(code[2]); //try catch //see if greater than 1 //see if positive // see if account has enough
    if(betAmount>0) {
       int accountIndx =  bank[0].getAccountIndex(e.getAuthor().getId()); // see if account exists
        if (accountIndx != -1) {
            int accountBal = bank[0].getAllBalance().get(accountIndx).getBalance();
            if (betAmount <= accountBal) {
                boolean gameInProg = false;
                String serverid = null;
                for (int x = 0; x < numGuessHolder[0].getNumGuessHolders().size(); x++) {
                    serverid = e.getGuild().getId();

                    if (serverid.equals(numGuessHolder[0].getNumGuessHolders().get(x).getServerID())) {

                        gameInProg = true;


                    }
                }

                if (!gameInProg) {

                    numGuessHolder[0].getNumGuessHolders().add(new NumGuessGame(serverid,betAmount));


                    try {
                        FileOutputStream fileOut = new FileOutputStream("numguess.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(numGuessHolder[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }
                    try {
                        FileOutputStream fileOut = new FileOutputStream("bank.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(bank[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }
//!numguess guess %bet amount% %number from 1-100%

                    e.getChannel().sendMessage("Game created 20 seconds to place bets, type !numguess guess %number from 1-100%").queue(m -> {

                    });

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            e.getChannel().sendMessage("Place your final bets rolling in 3 seconds!...").queue(m -> {
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {

                                    @Override
                                    public void run() {
                                        m.editMessage("Place your final bets rolling in 2 seconds!...").queue(m -> {
                                            Timer timer = new Timer();
                                            timer.schedule(new TimerTask() {

                                                @Override
                                                public void run() {
                                                    m.editMessage("Place your final bets rolling in 1 seconds!...").queue(m -> {
                                                        Timer timer = new Timer();
                                                        timer.schedule(new TimerTask() {

                                                            @Override
                                                            public void run() {


    try {
        FileInputStream fileIn = new FileInputStream("numguess.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        numGuessHolder[0] = (NumGuessHolder) in.readObject();
        in.close();
        fileIn.close();
    } catch (IOException i) {
        i.printStackTrace();
        return;
    } catch (ClassNotFoundException c) {
        c.printStackTrace();
        return;
    }

    final Bank[] bank = {new Bank()};
    try {
        FileInputStream fileIn = new FileInputStream("bank.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        bank[0] = (Bank) in.readObject();
        in.close();
        fileIn.close();
    } catch (IOException i) {
        i.printStackTrace();
        return;
    } catch (ClassNotFoundException c) {
        c.printStackTrace();
        return;
    }
    int ind = 0;
    String server = e.getGuild().getId();
    for (int x = 0; x < numGuessHolder[0].getNumGuessHolders().size(); x++) {


        if (server.equals(numGuessHolder[0].getNumGuessHolders().get(x).getServerID())) {

            ind = x;
            break;


        }
    }
if(numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().size()!=0){
    int number = (int)(Math.random()*(100))+1;
    int distance = 9999;
    System.out.println(numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().size());
    for(int x=0;x<numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().size();x++){


        numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x).setDistance(Math.abs(numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x).getNumber()-number));

        if(distance>=Math.abs(numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x).getNumber()-number)){
            distance = Math.abs(numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x).getNumber()-number);


        }

        System.out.println(distance);


    }
    ArrayList<NumGuessUser> users = new ArrayList<>();
    for(int x=0;x<numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().size();x++){


        numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x).setDistance(Math.abs(numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x).getNumber()-number));

        if(distance==numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x).getDistance()){
            users.add(numGuessHolder[0].getNumGuessHolders().get(ind).getNumGuessUsers().get(x));


        }




    }

    int shareBet = numGuessHolder[0].getNumGuessHolders().get(ind).getPotSize()/users.size();
    String build = "";
    for(int x=0;x<users.size();x++){
        int in = bank[0].getAccountIndex(users.get(x).getUser());
        bank[0].getAllBalance().get(in).setBalance(bank[0].getAllBalance().get(in).getBalance()+shareBet);
        build+=users.get(x).getName()+" won " + shareBet + " \uD83D\uDC8E with a guess of " + users.get(x).getNumber()+"\n\t";




    }
    m.editMessage("The number was " + number+"\n\t"+build).queue();

    numGuessHolder[0].getNumGuessHolders().remove(ind);

    try {
        FileOutputStream fileOut = new FileOutputStream("numguess.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(numGuessHolder[0]);
        out.close();
        fileOut.close();
    } catch (IOException ww) {
        ww.printStackTrace();
    }
    try {
        FileOutputStream fileOut = new FileOutputStream("bank.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(bank[0]);
        out.close();
        fileOut.close();
    } catch (IOException ww) {
        ww.printStackTrace();
    }

}else{

    m.editMessage("No one guessed!").queue();
    numGuessHolder[0].getNumGuessHolders().remove(ind);

    try {
        FileOutputStream fileOut = new FileOutputStream("numguess.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(numGuessHolder[0]);
        out.close();
        fileOut.close();
    } catch (IOException ww) {
        ww.printStackTrace();
    }
    try {
        FileOutputStream fileOut = new FileOutputStream("bank.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(bank[0]);
        out.close();
        fileOut.close();
    } catch (IOException ww) {
        ww.printStackTrace();
    }

}




                                                            }
                                                        }, 1000);
                                                    });
                                                }
                                            }, 1000);
                                        });
                                    }
                                }, 1000);


//some animation


                            });





                        }
                    },20000);


                }else{

                    e.getChannel().sendMessage("Game already in progress").queue();
                    //error game already in progress
                }




            } else {
                e.getChannel().sendMessage("Not enough funds").queue();
                //error not enough funds

            }


        } else {
            e.getChannel().sendMessage("You do not have a bank account").queue();
          //erorr you do not have a bank account

        }
    }else{
        e.getChannel().sendMessage("Can only bet positive integers").queue();
        //error have to bed postive
    }


}catch (Exception woah){
    e.getChannel().sendMessage("Error can only bet integers").queue();
    //error not an integer

}



//!numguess guess %number from 1-100%
        }else if(code.length == 3 && code[1].equals("guess")){

            try{
int guesser =Integer.parseInt(code[2]);
                if(guesser>0&&guesser<101) {
                    int accountIndx =  bank[0].getAccountIndex(e.getAuthor().getId()); // see if account exists
                    if (accountIndx != -1) {
                        int accountBal =  bank[0].getAllBalance().get(accountIndx).getBalance(); // see if account exists
                        boolean gameInProg = false;
                        int gameIndx =0;
                        String serverid = null;
                        for (int x = 0; x < numGuessHolder[0].getNumGuessHolders().size(); x++) {
                            serverid = e.getGuild().getId();

                            if (serverid.equals(numGuessHolder[0].getNumGuessHolders().get(x).getServerID())) {
                                gameIndx = x;
                                gameInProg = true;
break;

                            }
                        }
                        int betaAmount = numGuessHolder[0].getNumGuessHolders().get(gameIndx).getBetAmount();
                        if(gameInProg){
                            if (betaAmount <= accountBal) {

                                bank[0].getAllBalance().get(accountIndx).setBalance(accountBal-betaAmount);
                                numGuessHolder[0].getNumGuessHolders().get(gameIndx).setPotSize(numGuessHolder[0].getNumGuessHolders().get(gameIndx).getPotSize()+betaAmount);
                                numGuessHolder[0].getNumGuessHolders().get(gameIndx).getNumGuessUsers().add(new NumGuessUser(e.getAuthor().getId(),e.getAuthor().getName(),betaAmount,guesser));
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("numguess.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(numGuessHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("bank.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(bank[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }



                            } else {
                                e.getChannel().sendMessage("Not enough funds").queue();
                                //error not enough funds

                            }


                        } else {
                            e.getChannel().sendMessage("No game in progess. try !numguess %bet amount% to start a game").queue();
                            //erorr you do not have a bank account

                        }
                    }else{
                        e.getChannel().sendMessage("Error you do not have a bank account").queue();
                        //error have to bed postive
                    }

                        }else{


                    e.getChannel().sendMessage("Error can only guess number from 1-100").queue();


                        }



            }catch (Exception woah){
                e.getChannel().sendMessage("Error can only guess integers").queue();
                //error not an integer

            }



        }else{
            if(!e.getAuthor().isBot())
            e.getChannel().sendMessage("!numguess start <bet amount> - Start a match with a fixed bet amount\n!numguess guess <number> - Guess number within 1-100").queue();
        }

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                e.getMessage().delete().queue();

            }
        }, 5000);

    }

}
