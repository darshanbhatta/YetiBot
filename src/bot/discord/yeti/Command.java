package bot.discord.yeti;

import bot.discord.yeti.command.*;
import bot.discord.yeti.dictionary.API;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.discordbots.api.client.DiscordBotListAPI;

import java.awt.*;
import java.io.IOException;

public class Command extends ListenerAdapter {
    MusicManager musicManager = new MusicManager();
    String allGuilds = "";
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
                case "invite":
                    e.getChannel().sendMessage("You can add me to your guild/server with the following link : \n\nhttps://discordapp.com/oauth2/authorize?client_id=465945948925984769&scope=bot&permissions=2146958591").queue();

                    break;
                case "ping":

                    long ping = e.getJDA().getPing();
                    e.getTextChannel().sendMessage(new EmbedBuilder().setColor(getColorByPing(ping)).setDescription(
                            String.format(":ping_pong:   **Pong!**\n\nThe ping is `%s` milliseconds.",
                                    ping)
                    ).build()).queue();

                    break;
                case "music":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    break;
                case "play":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    break;
                case "volume":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    break;
                case "pause":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    break;
                case "stop":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    break;
                case "choose":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "skip":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    break;
                case "queue":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "loop":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "repeat":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "shuffle":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                    case "current":

                    try {
                        musicManager.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "server":

                    if(e.getAuthor().getId().equals(API.darshDiscordID)||e.getAuthor().getId().equals(API.arcticDiscordID)) {
                        e.getChannel().sendMessage(allGuilds).queue();
                    }
                    break;
                case "broadcast":
                    Broadcast.run(e);

                    break;
                case "unsubscribe":
                    Broadcast.run(e);

                    break;
                case "subscribe":
                    Broadcast.run(e);

                    break;
                default:
                    break;

            }


        }

    }

    private Color getColorByPing(long ping) {
        if (ping < 100)
            return Color.green;
        if (ping < 500)
            return Color.yellow;
        if (ping < 1000)
            return Color.orange;
        return Color.red;
    }

    public void onGuildJoin(GuildJoinEvent event) {
        int count =0;
        String out = "\nThis bot is running on\n";
        for (Guild g : event.getJDA().getGuilds()) {
            out += g.getName() + "\n";
            count++;


        }
        allGuilds = out;
        DiscordBotListAPI api = new DiscordBotListAPI.Builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NTk0NTk0ODkyNTk4NDc2OSIsImJvdCI6dHJ1ZSwiaWF0IjoxNTMyMzA3NTcyfQ.Md14g0ZnY8mksl4wWsQQSBCe1Of2z7jUgCIVdZH0KIg")
                .build();



        api.setStats("465945948925984769",count );

        event.getGuild().getDefaultChannel().sendMessage("Hello, I'm Yeti! Thanks for adding me.\n" +
                "\n" +
                "Enter **!help** for the list of commands that I am authorized to execute.\n" +
                "\n" +
                "Want to play some tunes? Enter **!music**.\n" +
                "\n" +
                "You can join my community server if you'd like to support me or if you need space to interact.\n\n" +
                "https://discord.gg/sbrNjcd").queue ();



    }

    public void onReady(ReadyEvent e) {
        int count =0;
        String out = "\nThis bot is running on\n";
        for (Guild g : e.getJDA().getGuilds()) {
            out += g.getName() + "\n";
            count++;


        }

        allGuilds = out;

        DiscordBotListAPI api = new DiscordBotListAPI.Builder()
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NTk0NTk0ODkyNTk4NDc2OSIsImJvdCI6dHJ1ZSwiaWF0IjoxNTMyMzA3NTcyfQ.Md14g0ZnY8mksl4wWsQQSBCe1Of2z7jUgCIVdZH0KIg")
                .build();



        api.setStats("465945948925984769",count );
        System.out.println(out);
    }

}



