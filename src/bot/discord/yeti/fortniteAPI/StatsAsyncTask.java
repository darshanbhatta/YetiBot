package bot.discord.yeti.fortniteAPI;


import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;



public class StatsAsyncTask{


    private HttpRequestFactory factory;
    private String acessToken;
    private String authURL = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/token";
    private String[] endpoints = {"grant_type", "username", "password", "includePerms"};
    private String[] values = {"password", "dar.shplanet@gmail.com", "woah123$", "true"};
    private String[] emails = {"dar.shplanet@gmail.com","darshplanet@gmail.com","d.arshplanet@gmail.com","da.rshplanet@gmail.com"};
    String userId;
    public StatsAsyncTask(String username){
        this.userId = username;
    }



    public AllStats getStats() {
        GenericUrl tokenUrl = new GenericUrl(authURL);

        int num = (int)(Math.random() * emails.length) + 0;

        values[1] = emails[num];

        String initialLoginPayload = urlFormer(endpoints, values);
        factory =  new NetHttpTransport().createRequestFactory();
        ByteArrayContent initLog = new ByteArrayContent("application/x-www-form-urlencoded", initialLoginPayload.getBytes());
        HttpRequest initReq = null;
        try {
            initReq = factory.buildPostRequest(tokenUrl, initLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initReq.getHeaders().setAuthorization("basic " + "MzRhMDJjZjhmNDQxNGUyOWIxNTkyMTg3NmRhMzZmOWE6ZGFhZmJjY2M3Mzc3NDUwMzlkZmZlNTNkOTRmYzc2Y2Y");
        String initialLoginResponse = null;
        try {
            initialLoginResponse = initReq.execute().parseAsString();
            acessToken = initialLoginResponse.substring(initialLoginPayload.indexOf("access_token") + 23, initialLoginResponse.indexOf(",") - 1);



            String authExchangeEndpoint = "https://account-public-service-prod03.ol.epicgames.com/account/api/oauth/exchange";
            GenericUrl exchangeUrl = new GenericUrl(authExchangeEndpoint);
            HttpRequest exchangeRequest = null;
            try {
                exchangeRequest = factory.buildGetRequest(exchangeUrl);

                exchangeRequest.getHeaders().setAuthorization("bearer " + acessToken);

                String exRes = null;
                try {
                    exRes = exchangeRequest.execute().parseAsString();

                    String exCode = exRes.substring(exRes.indexOf("code") + 9, exRes.lastIndexOf(",") - 1);
                    String[] types = {"grant_type", "exchange_code", "includePerms", "token_type"};
                    String[] vals = {"exchange_code", exCode, "true", "eg1"};
                    String exchangeTokenPayload = urlFormer(types, vals);

                    ByteArrayContent exToken = new ByteArrayContent("application/x-www-form-urlencoded", exchangeTokenPayload.getBytes());
                    HttpRequest exTokenReq = null;
                    try {
                        exTokenReq = factory.buildPostRequest(tokenUrl, exToken);
                        exTokenReq.getHeaders().setAuthorization("basic " + "ZWM2ODRiOGM2ODdmNDc5ZmFkZWEzY2IyYWQ4M2Y1YzY6ZTFmMzFjMjExZjI4NDEzMTg2MjYyZDM3YTEzZmM4NGQ");
                        String exchangeTokenResponse = null;
                        try {
                            exchangeTokenResponse = exTokenReq.execute().parseAsString();
                            acessToken = exchangeTokenResponse.substring(exchangeTokenResponse.indexOf("access_token") + 17, exchangeTokenResponse.indexOf(",") - 1);
                            GenericUrl url = new GenericUrl("https://persona-public-service-prod06.ol.epicgames.com/persona/api/public/account/lookup?q=" + userId);
                            HttpRequest request = null;
                            try {
                                request = factory.buildGetRequest(url);
                                request.getHeaders().setAuthorization("bearer " + acessToken);
                                String json = "";
                                try {
                                    json = request.execute().parseAsString();
                                    System.out.println(json.substring(json.indexOf("id") + 7, json.indexOf(",") - 1));
                                    userId = json.substring(json.indexOf("id") + 7, json.indexOf(",") - 1);
                                    GenericUrl urll = new GenericUrl("https://fortnite-public-service-prod11.ol.epicgames.com/fortnite/api/stats/accountId/" + userId + "/bulk/window/alltime");
                                    HttpRequest requestt = null;
                                    try {
                                        requestt = factory.buildGetRequest(urll);
                                        requestt.getHeaders().setAuthorization("bearer " + acessToken);
                                        try {
                                            String jsonn = requestt.execute().parseAsString();
                                            RawData[] rawStats = new Gson().fromJson(jsonn, new TypeToken<RawData[]>() {}.getType());
                                            StringBuilder gson = new StringBuilder();
                                            for (int x = 0; x < rawStats.length; x++) {
                                                gson.append(",\"").append(rawStats[x].getName()).append("\":").append(rawStats[x].getValue());
                                            }

                                            String jsonStats = "{" + gson.toString().substring(1) + "}";

                                            StatsPC statsPC = new Gson().fromJson(jsonStats, StatsPC.class);
                                            StatsXB1 statsXB1 = new Gson().fromJson(jsonStats, StatsXB1.class);
                                            StatsPS4 statsPS4 = new Gson().fromJson(jsonStats, StatsPS4.class);

                                            if (statsPC.hasAccount()) {
                                                statsPC.calc();
                                                System.out.println(statsPC.lifeTime());
                                                System.out.println(statsPC.soloStat());
                                                System.out.println(statsPC.duoStat());
                                                System.out.println(statsPC.squadStat());
                                            }
                                            if (statsXB1.hasAccount()) {
                                                statsXB1.calc();
                                                System.out.println(statsXB1.lifeTime());
                                                System.out.println(statsXB1.soloStat());
                                                System.out.println(statsXB1.duoStat());
                                                System.out.println(statsXB1.squadStat());
                                            }
                                            if (statsPS4.hasAccount()) {
                                                statsPS4.calc();
                                                System.out.println(statsPS4.lifeTime());
                                                System.out.println(statsPS4.soloStat());
                                                System.out.println(statsPS4.duoStat());
                                                System.out.println(statsPS4.squadStat());
                                            }
                                            return new AllStats(statsPC,statsPS4,statsXB1);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.toString());
                                    System.out.println("\nError, cannot find user");


                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




















        return null;
    }






    private String urlFormer(String[] keys, String[] values) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < keys.length; i++) {
            sb.append("&");
            sb.append(keys[i]);
            sb.append("=");
            sb.append(values[i]);
        }

        return sb.toString().substring(1);
    }


}