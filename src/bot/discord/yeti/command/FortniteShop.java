package bot.discord.yeti.command;

import bot.discord.yeti.fortniteAPI.SaleItem;
import bot.discord.yeti.fortniteAPI.SalesFetch;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;
import org.omg.CORBA.portable.ApplicationException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class FortniteShop {
    public static void run(Message msg, MessageReceivedEvent event) throws IOException {

Color first = null;
 ArrayList<String> images = new ArrayList<>();
String name = "";

                SalesFetch shop = new SalesFetch();

                System.setProperty("http.agent", "Chrome");
                ArrayList<SaleItem> shopItem = shop.doInBackground();
                StringBuilder leaderList = new StringBuilder();
                LocalTime now = LocalTime.now(DateTimeZone.forID("Europe/London"));
                int i = now.millisOfDay().get();
                int ii = DateTimeConstants.MILLIS_PER_HOUR - i;

                if (ii <= 0) {
                    ii = (DateTimeConstants.MILLIS_PER_DAY - i) + DateTimeConstants.MILLIS_PER_HOUR;

                }

                long hours = TimeUnit.MILLISECONDS.toHours(ii);
                ii -= TimeUnit.HOURS.toMillis(hours);
                long minutes = TimeUnit.MILLISECONDS.toMinutes(ii);
                ii -= TimeUnit.MINUTES.toMillis(minutes);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(ii);

                StringBuilder sb = new StringBuilder(64);
                sb.append(hours);
                if(hours!=1)
                sb.append(" hours ");
                else
                    sb.append(" hour ");
                sb.append(minutes);
                if(minutes!=1)
                sb.append(" minutes ");
                else
                sb.append(" minute ");
                sb.append(seconds);
                if(seconds!=1)
                sb.append(" seconds");
                else
                    sb.append(" second");
                leaderList.append("Fortnite Item Shop\n" +"Shop resets in " + "*"+sb+"*" );

                BufferedImage result = new BufferedImage(
                        128*3, 128*((int)Math.ceil((double) shopItem.size()/3)), //work these out
                        BufferedImage.TYPE_INT_RGB);
                Graphics g = result.getGraphics();


                for (int x = 0; x < shopItem.size(); x++) {
                    try {
                        URL url = new URL(shopItem.get(x).getImgSrc());
                        InputStream a = new BufferedInputStream(url.openStream());
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        int n = 0;
                        while (-1 != (n = a.read(buf))) {
                            out.write(buf, 0, n);
                        }
                        out.close();
                        a.close();
                        int height = 128;
                        int width = 128;

                        byte[] response = out.toByteArray();
                        ByteArrayInputStream in = new ByteArrayInputStream(response);
                        try {
                            BufferedImage img = ImageIO.read(in);
                            if (height == 0) {
                                height = (width * img.getHeight()) / img.getWidth();
                            }
                            if (width == 0) {
                                width = (height * img.getWidth()) / img.getHeight();
                            }
                            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                            String word = shopItem.get(x).getImgSrc();
                            Color color =  new Color(0, 0, 0);
                            int price = Integer.parseInt(shopItem.get(x).getPrice());
                            if (word.contains("Soldier")) {
                                if(price==2000){
                                    color = new Color(196,113,69);

                                }else if(price==1500){
                                   color = new Color(152,105,205);

                                }else if(price==1200){
                                    color = new Color(96,145,210);

                                }else if(price==800){

                                    color = new Color(83,139,75);
                                }


                            } else if (word.contains("Glider")) {

                                if(price==2000){
                                    color = new Color(196,113,69);

                                }else if(price==1500||price==1200){
                                    color = new Color(152,105,205);

                                }else if(price==800){
                                    color = new Color(96,145,210);

                                }else if(price==500){

                                    color = new Color(83,139,75);
                                }


                            } else if (word.contains("Emote")) {
                                if(price==800){
                                    color = new Color(152,105,205);

                                }else if(price==500){
                                    color = new Color(96,145,210);

                                }else if(price==200){

                                    color = new Color(83,139,75);
                                }

                            }else if (word.contains("Pickaxe")) {
                                if(price==1500|price==1200){
                                    color = new Color(152,105,205);

                                }else if(price==800){
                                    color = new Color(96,145,210);

                                }else if(price==500){

                                    color = new Color(83,139,75);
                                }
                            }
if(first==null)
first = color;
                            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, color, null);

                            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

                            ImageIO.write(imageBuff, "jpg", buffer);

                            response = buffer.toByteArray();
                            name +=x+1+")   "+shopItem.get(x).getTitle() + "  " + "|" + "  " + shopItem.get(x).getPrice() + " V-Bucks \n\n";
                            FileOutputStream fos = new FileOutputStream("temp"+x+".jpg",false);
                            try {
                                fos.write(response);
                                images.add("temp"+x+".jpg");
                            } finally {
                                fos.close();
                            }



                        } catch (Exception aaaa) {

                        }


                    } catch (Exception e) {
                        System.out.println(e.toString());
                        msg.getChannel().sendMessage("Format: y!fortniteshop").queue();
                    }




                }
        for(int w = images.size();w<3*((int)Math.ceil((double) shopItem.size()/3));w++){

            images.add("trans.jpg");
        }
int x =0;
                int y = 0;
                for(String image : images){
                    BufferedImage bi = ImageIO.read(new File(image));
                    g.drawImage(bi, x, y, null);
                    x += 128;
                   // System.out.println(result.getWidth() + " " + x);
                    if(x >= result.getWidth()){
                        x = 0;
                        y += bi.getHeight();
                    }
                }
                ImageIO.write(result,"png",new File("result.png"));
                try{
                    msg.getChannel().sendFile(new File("result.png"),new MessageBuilder().append(leaderList).build()).queue();
                    event.getTextChannel().sendMessage(new EmbedBuilder().setColor(first).setDescription(name).build()).queue();
                }catch (Exception ea){
                    event.getTextChannel().sendMessage(new EmbedBuilder().setColor(first).setTitle(leaderList.toString()).setDescription(name).build()).queue();


                }



        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                event.getMessage().delete().queue();

            }
        }, 5000);
    }

}


