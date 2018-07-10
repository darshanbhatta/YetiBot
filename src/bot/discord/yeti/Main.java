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
        //NDY1OTU1NzgzODQ3MzEzNDQ5.Diadxw.Mq2K5BjW4kUiNmEM6C3HzUFX_nk
        builder.setToken("NDY1OTQ1OTQ4OTI1OTg0NzY5.Dia2Jw._wd1777XafK_lqjplkVmS2bBADA");

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
