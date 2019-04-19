package bot.discord.yeti;

import bot.discord.yeti.command.*;
import bot.discord.yeti.dictionary.API;
import bot.discord.yeti.dictionary.Dic;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
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

    EventWaiter waiter;
    public Command(EventWaiter waiter) {
        this.waiter = waiter;
    }

    MusicManager musicManager = new MusicManager();



    String allGuilds = "";
    public void onMessageReceived(MessageReceivedEvent e) {
        System.out.println(e.getMessage().getContentRaw());
        /*
        try{

            if(e.getMessage().getMentionedMembers().size()>0&&e.getMessage().getMentionedMembers().get(0).equals(e.getGuild().getSelfMember())&&!e.getAuthor().isBot()){
                try {
                    Help.run(e.getMessage(), e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }

        }catch (Exception weee){


        }
*/
        if (e.getMessage().getContentRaw().toLowerCase().startsWith("y")&&!e.getAuthor().isBot()) {
            String[] arg = e.getMessage().getContentRaw().toLowerCase().replaceFirst("y", "").split(" ");

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
                    Ban.run(e.getMessage(), e,waiter);
                    break;

                case "unban":
                    Unban.run(e.getMessage(), e);
                    break;

                case "fstats":
                    try {
                        FortniteStats.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case "flb":
                    try {
                        FortniteLeaderboard.run(e.getMessage(), e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;


                case "fshop":

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

                    e.getChannel().sendMessage(new EmbedBuilder().setColor((Color.RED)).setTitle("Jackpot")
                            .setThumbnail("https://i.imgur.com/t4yagto.png")
                            .setTitle("Jackpot")
                            .setDescription("Jackpot is currently unavailable")
                            .build()).queue();
                    //   JackpotManager.run(e);

                    break;
                case "tic":


                    TicTacToeManager.run(e);

                    break;
                case "tictactoe":
                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Tic-Tac-Toe")
                            .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                            .addField("Creating","```ytic start @user```",true)
                            .addField("Playing","```ytic <move number>```",true)
                            .addField("Board","```ytic board```",true)
                            .addField("End game","```ytic quit```",true)
                            .build()).queue();
                    break;
                case "c4":


                    Connect4Manager.run(e);

                    break;
                case "connect4":
                      e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                            .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                            .addField("Creating","```yc4 start @user```",true)
                            .addField("Playing","```yc4 <column number>```",true)
                            .addField("Board","```yc4 board```",true)
                            .addField("End game","```c4 quit```",true)
                            .build()).queue();
                    break;
                case "numguess":

                    NumGuessManager.run(e);

                    break;
                case "numberguess":

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Number Guessing")
                            .setThumbnail("http://swarm.rocks/storage/badges/casino_300.png")
                            .addField("Creating","Game is started with a fixed bet amount ```ynumguess start <bet amount>```",true)
                            .addField("Playing","Guess number within 1-100 ```ynumguess guess <number>```",true)
                            .build()).queue();
                    break;
                case "weather":


                    Weather.run(e);

                    break;
                case "poll":
                    PollManager.run(e);
                    break;
                case "polling":
                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Poll").setThumbnail("https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png").addField(
                            "Creating","```ypoll (title) [list of options separated by commas] <time limit (sec)>```",true)
                            .addField("Example","```ypoll (Do you enjoy using YetiBot?) [Yes, Sure, Absolutely] <20>```",true)
                            .addField("Voting","```ypoll <vote number>```",true)
                            .addField("Ending","```ypoll end```",true)
                            .build()).queue();
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
                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Snap").setThumbnail("https://i.imgur.com/ISOFDE5.png").addField(
                           "```ysnap```","Bans half of the members in the discord server under Thanos' will",false).build()).queue();

                    break;
                case "invite":
                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("You can add me to your guild/server with the following link").setDescription(
                           "https://discordapp.com/oauth2/authorize?client_id=465945948925984769&scope=bot&permissions=2146958591").build()).queue();


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
                case "lmgtfy":
                    Lmgtfy.run(e);
                    break;
                case "fortune":
                    try {
                        Fortune.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                case "dice":
                    try {
                        DiceRoll.run(e);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
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
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NTk0NTk0ODkyNTk4NDc2OSIsImJvdCI6dHJ1ZSwiaWF0IjoxNTMzNTg1NjU1fQ.b2MDLNGIWjmIcS9XAHgFIfR9ld3fKgkTSO7nLW9nc2Q")
                .build();



        api.setStats("465945948925984769",count );
        System.out.println(out);
        event.getGuild().getDefaultChannel().sendMessage(new EmbedBuilder().setTitle("Hello, I'm Yeti! Thanks for adding me!")
                .addField("Help","```yhelp```",true)
                .addField("Music","```ymusic```",true).setDescription(
                "You can join my community server if you'd like to support me or if you need space to interact.\n\n" +
                "**https://discord.gg/sbrNjcd**").setThumbnail("https://darshanbhatta.com/img/hero-img.png").setColor(new Color(0x8CC8FF)).setFooter("Server count: " + count,"https://discordbots.org/images/dblnew.png").build()).queue();




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
                .token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjQ2NTk0NTk0ODkyNTk4NDc2OSIsImJvdCI6dHJ1ZSwiaWF0IjoxNTMzNTg1NjU1fQ.b2MDLNGIWjmIcS9XAHgFIfR9ld3fKgkTSO7nLW9nc2Q")
                .build();



        api.setStats("465945948925984769",count );
        System.out.println(out);
    }

}



