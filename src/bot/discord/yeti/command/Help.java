package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Help {

    public static void run(Message msg, MessageReceivedEvent e) throws IOException {

        String casino = ":game_die: Yeti Casino :game_die:  \n\t !blackjack\n\t !coinflip\n\t !jackpot\n\t !numberguess\n\t !roulette\n\t !slot";
        String reserve = ":gem: Yeti Reserve :gem:  \n\t !bank init\n\t !bank bal\n\t !bank send\n\t !claim";
        String games = ":joystick: Yeti Games :joystick:  \n\t !connectfour\n\t !tictactoe\n\t !trivia";
        String community = ":earth_americas: Yeti Community :earth_americas:  \n\t !polling\n\t !reddit\n\t !weather";
        String server = ":floppy_disk: Discord Server :floppy_disk:   \n\t !addtext\n\t !addvoice\n\t !ban\n\t !unban\n\t !kick\n\t !role\n\t !snappy\n\t !invite";
        String videoGames = ":video_game: Fortnite :video_game:   \n\t !fortnitestats\n\t !fortniteshop\n\t !fortniteleaderboard";

        msg.getChannel().sendMessage(casino + "\n\n" + reserve +"\n\n" + games + "\n\n" + community + "\n\n" + server+ "\n\n" +videoGames).queue();
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                e.getMessage().delete().queue();

            }
        }, 5000);
    }
}