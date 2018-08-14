package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.game.trivia.TriviaHolder;
import bot.discord.yeti.util.reward.RewardUser;
import bot.discord.yeti.util.reward.RewardUserHolder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Claim {

    public static void run(MessageReceivedEvent event){
        String code[] = event.getMessage().getContentRaw().split(" ");
        final RewardUserHolder[] rewardUserHolders = new RewardUserHolder[1];
        try {
            FileInputStream fileIn = new FileInputStream("claim.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            rewardUserHolders[0] = (RewardUserHolder) in.readObject();
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

        //!claim daily
        if(code.length == 1){
            event.getChannel().sendMessage("y!claim hourly - Claim 50 :gem: - Available every 60 minutes\ny!claim daily - Claim 500 :gem: - Available every 24 hours").queue();
        }
        if(code.length==2&&code[1].equals("daily")){
int indx = -1;
            for(int x=0;x<rewardUserHolders[0].getRewardUsers().size();x++){

                if(rewardUserHolders[0].getRewardUsers().get(x).getUserid().equals(event.getAuthor().getId())){
                    indx = x;
                    break;




                }


            }

            if(indx==-1){
                rewardUserHolders[0].getRewardUsers().add(new RewardUser(event.getAuthor().getId()));
                rewardUserHolders[0].getRewardUsers().get(rewardUserHolders[0].getRewardUsers().size()-1).setDailyTime(System.currentTimeMillis()+TimeUnit.DAYS.toMillis(1));

               int w =  bank[0].getAccountIndex(event.getAuthor().getId());
              bank[0].getAllBalance().get(w).setBalance(bank[0].getAllBalance().get(w).getBalance()+500);
              event.getChannel().sendMessage("Successfully redeemed " + event.getAuthor().getName()+"'s daily reward of 500 \uD83D\uDC8E").queue();
                try {
                    FileOutputStream fileOut = new FileOutputStream("bank.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(bank[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }
                try {
                    FileOutputStream fileOut = new FileOutputStream("claim.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(rewardUserHolders[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }

            }else{

               if(rewardUserHolders[0].getRewardUsers().get(indx).getDailyTime()+TimeUnit.DAYS.toMillis(1)<=System.currentTimeMillis()){
                   rewardUserHolders[0].getRewardUsers().get(indx).setDailyTime(System.currentTimeMillis()+TimeUnit.DAYS.toMillis(1));
                   int w =  bank[0].getAccountIndex(event.getAuthor().getId());
                   bank[0].getAllBalance().get(w).setBalance(bank[0].getAllBalance().get(w).getBalance()+500);
                   event.getChannel().sendMessage("Successfully redeemed " + event.getAuthor().getName()+"'s daily reward of 500 \uD83D\uDC8E").queue();
                   try {
                       FileOutputStream fileOut = new FileOutputStream("bank.ser");
                       ObjectOutputStream out = new ObjectOutputStream(fileOut);
                       out.writeObject(bank[0]);
                       out.close();
                       fileOut.close();
                   } catch (IOException ww) {
                       ww.printStackTrace();
                   }
                   try {
                       FileOutputStream fileOut = new FileOutputStream("claim.ser");
                       ObjectOutputStream out = new ObjectOutputStream(fileOut);
                       out.writeObject(rewardUserHolders[0]);
                       out.close();
                       fileOut.close();
                   } catch (IOException ww) {
                       ww.printStackTrace();
                   }

               }else if(rewardUserHolders[0].getRewardUsers().get(indx).getDailyTime()==0){
                   rewardUserHolders[0].getRewardUsers().get(indx).setDailyTime(System.currentTimeMillis());
                   int w =  bank[0].getAccountIndex(event.getAuthor().getId());
                   bank[0].getAllBalance().get(w).setBalance(bank[0].getAllBalance().get(w).getBalance()+500);
                   event.getChannel().sendMessage("Successfully redeemed " + event.getAuthor().getName()+"'s daily reward of 500 \uD83D\uDC8E").queue();
                   try {
                       FileOutputStream fileOut = new FileOutputStream("bank.ser");
                       ObjectOutputStream out = new ObjectOutputStream(fileOut);
                       out.writeObject(bank[0]);
                       out.close();
                       fileOut.close();
                   } catch (IOException ww) {
                       ww.printStackTrace();
                   }
                   try {
                       FileOutputStream fileOut = new FileOutputStream("claim.ser");
                       ObjectOutputStream out = new ObjectOutputStream(fileOut);
                       out.writeObject(rewardUserHolders[0]);
                       out.close();
                       fileOut.close();
                   } catch (IOException ww) {
                       ww.printStackTrace();
                   }

               }else{
                   long time = rewardUserHolders[0].getRewardUsers().get(indx).getDailyTime()-System.currentTimeMillis();

                   int day = (int) TimeUnit.MILLISECONDS.toDays(time);
                   long hours = TimeUnit.MILLISECONDS.toHours(time) -
                           TimeUnit.DAYS.toHours(day);
                   long minute = TimeUnit.MILLISECONDS.toMinutes(time) -
                           TimeUnit.DAYS.toMinutes(day) -
                           TimeUnit.HOURS.toMinutes(hours);
                   long second = TimeUnit.MILLISECONDS.toSeconds(time) -
                           TimeUnit.DAYS.toSeconds(day) -
                           TimeUnit.HOURS.toSeconds(hours) -
                           TimeUnit.MINUTES.toSeconds(minute);
                   String bulid = "";
                   if(day==0){
                       if(hours==0){
                           if(minute>1){
                               bulid+=minute+" minutes ";
                           }else{
                               bulid+=minute+ " minute ";
                           }
                           if(second>1){
                               bulid+=+ second+" seconds " ;
                           }else{
                               bulid+=+ second+" second " ;
                           }

                       }else{
                           if(hours>1){
                               bulid+=+ hours+" hours " ;
                           }else{
                               bulid+=+ hours+" hour ";
                           }
                           if(minute>1){
                               bulid+=+ minute+" minutes " ;
                           }else{
                               bulid+=+ minute+" minute ";
                           }
                           if(second>1){
                               bulid+=+ second+" seconds " ;
                           }else{
                               bulid+=+ second+" second ";
                           }

                       }
                       if(minute==0){
                           if(second>1){
                               bulid+=+ second+" seconds " ;
                           }else{
                               bulid+=+ second+" second ";
                           }

                       }

                   }else{
                       if(day>1){
                           bulid+=+ day+" days " ;
                       }else{
                           bulid+=+ day+" day ";
                       }
                       if(hours>1){
                           bulid+=+ hours+" hours " ;
                       }else{
                           bulid+=+ hours+" hour ";
                       }
                       if(minute>1){
                           bulid+=+ minute+" minutes " ;
                       }else{
                           bulid+=+ minute+" minute ";
                       }
                       if(second>1){
                           bulid+=+ second+" seconds " ;
                       }else{
                           bulid+=+ second+" second ";
                       }
                   }


                   event.getChannel().sendMessage("Error you still have " +bulid+"until you can redeem daily reward").queue();



               }


            }





        }else if(code.length==2&&code[1].equals("hourly")){
            int indx = -1;
            for(int x=0;x<rewardUserHolders[0].getRewardUsers().size();x++){

                if(rewardUserHolders[0].getRewardUsers().get(x).getUserid().equals(event.getAuthor().getId())){
                    indx = x;
                    break;




                }


            }

            if(indx==-1){
                rewardUserHolders[0].getRewardUsers().add(new RewardUser(event.getAuthor().getId()));
                rewardUserHolders[0].getRewardUsers().get(rewardUserHolders[0].getRewardUsers().size()-1).setHourTime(System.currentTimeMillis()+TimeUnit.HOURS.toMillis(1));

                int w =  bank[0].getAccountIndex(event.getAuthor().getId());
                bank[0].getAllBalance().get(w).setBalance(bank[0].getAllBalance().get(w).getBalance()+50);
                event.getChannel().sendMessage("Successfully redeemed " + event.getAuthor().getName()+"'s hourly reward of 50 \uD83D\uDC8E").queue();
                try {
                    FileOutputStream fileOut = new FileOutputStream("bank.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(bank[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }
                try {
                    FileOutputStream fileOut = new FileOutputStream("claim.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(rewardUserHolders[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }

            }else{

                if(rewardUserHolders[0].getRewardUsers().get(indx).getHourTime()+TimeUnit.HOURS.toMillis(1)<=System.currentTimeMillis()){
                    rewardUserHolders[0].getRewardUsers().get(indx).setHourTime(System.currentTimeMillis()+TimeUnit.HOURS.toMillis(1));
                    int w =  bank[0].getAccountIndex(event.getAuthor().getId());
                    bank[0].getAllBalance().get(w).setBalance(bank[0].getAllBalance().get(w).getBalance()+50);
                    event.getChannel().sendMessage("Successfully redeemed " + event.getAuthor().getName()+"'s hourly reward of 50 \uD83D\uDC8E").queue();
                    try {
                        FileOutputStream fileOut = new FileOutputStream("bank.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(bank[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }
                    try {
                        FileOutputStream fileOut = new FileOutputStream("claim.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(rewardUserHolders[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }

                }else if(rewardUserHolders[0].getRewardUsers().get(indx).getHourTime()==0){
                    rewardUserHolders[0].getRewardUsers().get(indx).setHourTime(System.currentTimeMillis());
                    int w =  bank[0].getAccountIndex(event.getAuthor().getId());
                    bank[0].getAllBalance().get(w).setBalance(bank[0].getAllBalance().get(w).getBalance()+50);
                    event.getChannel().sendMessage("Successfully redeemed " + event.getAuthor().getName()+"'s daily reward of 500 \uD83D\uDC8E").queue();
                    try {
                        FileOutputStream fileOut = new FileOutputStream("bank.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(bank[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }
                    try {
                        FileOutputStream fileOut = new FileOutputStream("claim.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(rewardUserHolders[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }

                }else{
                    long time = rewardUserHolders[0].getRewardUsers().get(indx).getHourTime()-System.currentTimeMillis();

                    int day = (int) TimeUnit.MILLISECONDS.toDays(time);
                    long hours = TimeUnit.MILLISECONDS.toHours(time) -
                            TimeUnit.DAYS.toHours(day);
                    long minute = TimeUnit.MILLISECONDS.toMinutes(time) -
                            TimeUnit.DAYS.toMinutes(day) -
                            TimeUnit.HOURS.toMinutes(hours);
                    long second = TimeUnit.MILLISECONDS.toSeconds(time) -
                            TimeUnit.DAYS.toSeconds(day) -
                            TimeUnit.HOURS.toSeconds(hours) -
                            TimeUnit.MINUTES.toSeconds(minute);
                    String bulid = "";
                    if(day==0){
                        if(hours==0){
                            if(minute>1){
                                bulid+=minute+" minutes ";
                            }else{
                                bulid+=minute+ " minute ";
                            }
                            if(second>1){
                                bulid+=+ second+" seconds " ;
                            }else{
                                bulid+=+ second+" second " ;
                            }

                        }else{
                            if(hours>1){
                                bulid+=+ hours+" hours " ;
                            }else{
                                bulid+=+ hours+" hour ";
                            }
                            if(minute>1){
                                bulid+=+ minute+" minutes " ;
                            }else{
                                bulid+=+ minute+" minute ";
                            }
                            if(second>1){
                                bulid+=+ second+" seconds " ;
                            }else{
                                bulid+=+ second+" second ";
                            }

                        }
                        if(minute==0){
                            if(second>1){
                                bulid+=+ second+" seconds " ;
                            }else{
                                bulid+=+ second+" second ";
                            }

                        }

                    }else{
                        if(day>1){
                            bulid+=+ day+" days " ;
                        }else{
                            bulid+=+ day+" day ";
                        }
                        if(hours>1){
                            bulid+=+ hours+" hours " ;
                        }else{
                            bulid+=+ hours+" hour ";
                        }
                        if(minute>1){
                            bulid+=+ minute+" minutes " ;
                        }else{
                            bulid+=+ minute+" minute ";
                        }
                        if(second>1){
                            bulid+=+ second+" seconds " ;
                        }else{
                            bulid+=+ second+" second ";
                        }
                    }


                    event.getChannel().sendMessage("Error you still have " +bulid+"until you can redeem hourly reward").queue();


                }


            }



        }else{


        }
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                event.getMessage().delete().queue();

            }
        }, 5000);



    }

}
