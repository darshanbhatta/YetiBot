package bot.discord.yeti;

import bot.discord.yeti.command.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;

public class Command extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getMessage().getContentRaw().toLowerCase().startsWith("!")) {
            String[] arg = e.getMessage().getContentRaw().toLowerCase().replaceFirst("!", "").split(" ");

            switch (arg[0]) {
                case "help":
                    try {
                        Help.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case "snap":
                    Snap.run(e.getMessage(), e);

                    break;

                case "ban":
                    Ban.run(e.getMessage(), e);
                    break;

                case "unban":
                    Unban.run(e.getMessage(), e);
                    break;

                case "fortnitestats":
                    try {
                        FortniteStats.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case "fortniteleaderboard":
                    try {
                        FortniteLeaderboard.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;


                case "fortniteshop":
                    System.out.println("shoprunning");
                    try {
                        FortniteShop.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "bank":
                    try {
                        BankManager.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "blackjack":


                    BlackjackManager.run(e);

                    break;
                case "slot":

                    try {
                        SlotGameManager.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "coinflip":


                    try {
                        Coinflip.run(e.getMessage());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    break;
                case "roulette":


                    RouletteManager.run(e);

                    break;
                case "jackpot":


                    JackpotManager.run(e);

                    break;
                case "tic":


                    TicTacToeManager.run(e);

                    break;
                case "tictactoe": e.getChannel().sendMessage("!tic start @user - Start a match\n!tic play <move number> to move\n!tic board - Print out your current match board\n!tic quit - End any unfinished games").queue();
                    break;
                case "c4":


                    Connect4Manager.run(e);

                    break;
                case "connectfour":
                    e.getChannel().sendMessage("!c4 start @user - Start a match\n!c4 <column number> - Play Column\n!c4 board - Print our your current match board\n!c4 quit - End any unfinished games").queue();
                    break;
                case "numguess":

                    NumGuessManager.run(e);

                    break;
                case "numberguess": e.getChannel().sendMessage("!numguess start <bet amount> - Start a match with a fixed bet amount\n!numguess guess <number> - Guess number within 1-100").queue();
                    break;
                case "weather":


                    Weather.run(e);

                    break;
                case "poll":
                    PollManager.run(e);
                    break;
                case "polling": e.getChannel().sendMessage("Start a poll - !poll (title) [list of options separated by commas] <time limit (sec)>\nExample: !poll (Do you enjoy using YetiBot?) [Yes, Sure, Absolutely] <20>\n\nVote in a poll - !poll <vote number>\nEnd a poll - !poll end").queue();

                    break;
                case "reddit":
                    try {
                        RedditManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "trivia":
                    TriviaManager triviaManager = new TriviaManager();
                    triviaManager.run(e);


                    break;
                case "claim":

                    Claim.run(e);


                    break;
                case "kick":

                    Kick.run(e.getMessage(), e);


                    break;
                case "addtext":

                    CreateTextChannel.run(e);


                    break;
                case "addvoice":

                    CreateVoiceChannel.run(e);


                    break;
                case "role":

                    Role.run(e);
                    break;
                case "snappy":
                    e.getChannel().sendMessage("Bans half of the members in the discord server under Thanos' will - !snap").queue();

                    break;
                default:
                    break;

            }


        }

    }

}



