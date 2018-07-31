package bot.discord.yeti;

import bot.discord.yeti.dictionary.API;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import org.discordbots.api.client.DiscordBotListAPI;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA jda;


    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);


        builder.setToken(API.darshTestBot);

        builder.setAutoReconnect(true);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListener(new ReadyListener());
        builder.addEventListener(new Command());

        try {
            jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
