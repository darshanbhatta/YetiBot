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

        String casino = ":game_die: Yeti Casino :game_die:  \n\t y!blackjack\n\t y!coinflip\n\t y!jackpot\n\t y!numberguess\n\t y!roulette\n\t y!slot";
        String reserve = ":gem: Yeti Reserve :gem:  \n\t y!bank init\n\t y!bank bal\n\t y!bank send\n\t y!claim";
        String games = ":joystick: Yeti Games :joystick:  \n\t y!connectfour\n\t y!tictactoe\n\t y!trivia";
        String community = ":earth_americas: Yeti Community :earth_americas:  \n\t y!polling\n\t y!reddit\n\t y!weather";
        String server = ":floppy_disk: Discord Server :floppy_disk:   \n\t y!addtext\n\t y!addvoice\n\t y!ban\n\t y!unban\n\t y!kick\n\t y!role\n\t y!snappy\n\t y!invite";
        String videoGames = ":video_game: Fortnite :video_game:   \n\t y!fortnitestats\n\t y!fortniteshop\n\t y!fortniteleaderboard";

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