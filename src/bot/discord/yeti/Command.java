package bot.discord.yeti;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;

public class Command extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getMessage().getContentRaw().startsWith("!")) {
            String[] arg = e.getMessage().getContentRaw().replaceFirst("!", "").split(" ");

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

                case "fortniteStats":
                    try {
                        FortniteStats.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case "fortniteLeaderboard":
                    try {
                        FortniteLeaderboard.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;


                case "fortniteShop":
                    try {
                        FortniteShop.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "bank":
                    try {
                        BankManager.run(e.getMessage(),e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
break;
                case "blackjack":


                        BlackjackManager.run(e);

                    break;
                case "slot":

                    try {
                        SlotGameManager.run(e.getMessage(),e);
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
                case "c4":


                    Connect4Manager.run(e);

                    break;
                case "numguess":


                    NumGuessManager.run(e);

                    break;
                case "weather":


                    Weather.run(e);

                    break;
                case "poll":
                    PollManager.run(e);
                    break;
                case "reddit":
                    try {
                        RedditManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

            }

            }

            }

        }



