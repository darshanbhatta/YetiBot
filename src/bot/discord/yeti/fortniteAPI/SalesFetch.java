package bot.discord.yeti.fortniteAPI;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;




public class SalesFetch {





    public SalesFetch() {

    }



    ArrayList<String> titlea, datea, descripa,linka,imgSrca,price,type;
    private String fornitess;
    private String selector = "div.row.sale__items";
    private String title = "p";
    private String imgsec = "img.col";

    private String imgsecc = "img.col-12.col-md-6";
ArrayList<SaleItem> shop = new ArrayList<>();
    public ArrayList<SaleItem> doInBackground(final Void... voids) {
        titlea = new ArrayList<>();
        imgSrca = new ArrayList<>();
        price = new ArrayList<>();
        type = new ArrayList<>();
        fornitess = new String("https://stormshield.one/v2/api.json");
        int xx =0;




            //     titlea.clear();
//            datea.clear();
            //   descripa.clear();
            //   linka.clear();
            //  imgSrca.clear();

            xx =1;
        URL url = null;
        try {
            url = new URL(fornitess);
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                try {
                    JSONObject obj = new JSONObject(br.readLine());
                    String text =   obj.getJSONObject("pvp_sales").toString();
                    JSONArray arr = obj.getJSONObject("pvp_sales").getJSONArray("daily");
                    JSONArray arrr = obj.getJSONObject("pvp_sales").getJSONArray("weekly");


                    System.out.println("work " +arr.toString());
                    for (int i = 0; i < arrr.length(); i++)
                    {
                        titlea.add(arrr.getJSONObject(i).getString("title"));
                        price.add(arrr.getJSONObject(i).getString("final_price"));
                        imgSrca.add(arrr.getJSONObject(i).getString("path"));
type.add("w");
                    }

                    for (int i = 0; i < arr.length(); i++)
                    {
                        titlea.add(arr.getJSONObject(i).getString("title"));
                        price.add(arr.getJSONObject(i).getString("final_price"));
                        imgSrca.add(arr.getJSONObject(i).getString("path"));
                        type.add("d");

                    }


                    for(int x=0;x<titlea.size();x++){
                        System.out.println(titlea.get(x));
                        shop.add(new SaleItem(titlea.get(x),price.get(x),imgSrca.get(x),type.get(x)));

                    }
                    return shop;
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




        return null;


    }





}

