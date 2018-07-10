package bot.discord.yeti.fortniteAPI;




import org.json.JSONArray;
import org.json.JSONException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



public class LeaderFetch{




    private int userSelect = 01;

    public LeaderFetch(int w) {
        userSelect = w;

    }








    private  String pcSolo = "https://api.partybus.gg/v1/leaderboards/pc/2/place_a";
    private String xboxSolo = "https://api.partybus.gg/v1/leaderboards/xb1/2/place_a";
    private String psSolo = "https://api.partybus.gg/v1/leaderboards/ps4/2/place_a";


    private  String pcDuo = "https://api.partybus.gg/v1/leaderboards/pc/10/place_a";
    private String xboxDuo = "https://api.partybus.gg/v1/leaderboards/xb1/10/place_a";
    private String psDuo = "https://api.partybus.gg/v1/leaderboards/ps4/10/place_a";


    private  String pcSquad = "https://api.partybus.gg/v1/leaderboards/pc/9/place_a";
    private String xboxSquad = "https://api.partybus.gg/v1/leaderboards/xb1/9/place_a";
    private String psSquad = "https://api.partybus.gg/v1/leaderboards/ps4/9/place_a";




    ArrayList<String> name, wins;
    private String fornitess;
ArrayList<LeaderItem> leader= new ArrayList();



    public ArrayList<LeaderItem> leaderFetch() {
        String defaultt = pcSolo;
        name = new ArrayList<>();
        wins = new ArrayList<>();

        if(userSelect==11){
            defaultt=pcSolo;
        }else if(userSelect==21){
            defaultt=pcDuo;
        }else if(userSelect==31){
            defaultt=pcSquad;
        }else  if(userSelect==12){
            defaultt=psSolo;
        }else if(userSelect==22){
            defaultt=psDuo;
        }else if(userSelect==32){
            defaultt=psSquad;
        } if(userSelect==13){
            defaultt=xboxSolo;
        }else if(userSelect==23){
            defaultt=xboxDuo;
        }else if(userSelect==33){
            defaultt=xboxSquad;
        }


        System.out.println(defaultt);





        name.clear();
        wins.clear();


        URL url = null;
        try {
            url = new URL(defaultt);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                try {
                    JSONArray arr = new JSONArray(br.readLine());
                    //String text =   obj.getJSONObject("pvp_sales").toString();
                  //  JSONObject arr = obj.getJSONObject("player");

                    System.out.println(arr.toString());

                    for (int i = 0; i < arr.length(); i++)
                    {
                        name.add(arr.getJSONObject(i).getJSONObject("player").get("displayName").toString());

                        wins.add(arr.getJSONObject(i).get("placeA").toString());


                    }

String type = "pc";
                   int num =  userSelect%10;
                    if(num==1){
                        type = "pc";
                    }else if(num==2){
                        type = "psn";
                    }else if(num==3){
                        type = "xbone";
                    }
                    for(int x=0;x<name.size();x++){
                       // System.out.println(name.get(x));
                        //System.out.println(type);
                        leader.add(new LeaderItem(name.get(x),wins.get(x),type));

                    }
                    return leader;
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (JSONException e) {

                    e.printStackTrace();
                }


            } catch (IOException e) {

                e.printStackTrace();
            }




        } catch (MalformedURLException e) {

            e.printStackTrace();
        }










return  null;
    }







}

