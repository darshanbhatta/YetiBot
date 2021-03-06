package bot.discord.yeti;

import bot.discord.yeti.dictionary.API;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.discordbots.api.client.DiscordBotListAPI;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA jda;


    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);

        EventWaiter waiter = new EventWaiter();

        builder.setToken(API.discordToken);

        builder.setAutoReconnect(true);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(Game.listening("yhelp | yinvite"));
       //builder.addEventListener(new ReadyListener());
        builder.addEventListener(waiter);
        builder.addEventListener(new Command(waiter));
        try {
            jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





    }


}
