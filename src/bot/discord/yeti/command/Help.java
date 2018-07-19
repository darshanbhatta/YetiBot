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



        msg.getChannel().sendMessage(":game_die: Yeti Casino :game_die: \n\t !blackjack\n\t !coinflip\n\t !slot \n\t !roulette").queue();
        msg.getChannel().sendMessage(":gem: Yeti Reserve :gem:  \n\t !bank init\n\t !bank balance\n\t !bank send").queue();
    }
}