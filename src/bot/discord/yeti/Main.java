package bot.discord.yeti;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA jda;


    public static void main(String[] args) {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
//
        //NDY1OTU1NzgzODQ3MzEzNDQ5.DikfTg.ahBOBRUhceNFxPsaWvpU3WCGDmA
        //NDY1OTQ1OTQ4OTI1OTg0NzY5.Dih9TA.eB6WD26KkcN8_hVkv5tZs7Egkjc
        builder.setToken("NDY1OTU1NzgzODQ3MzEzNDQ5.DikfTg.ahBOBRUhceNFxPsaWvpU3WCGDmA");

        builder.setAutoReconnect(true);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListener(new ReadyListener());
        builder.addEventListener(new Command());
        builder.addEventListener(new BlackJack());

        try {
            jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
