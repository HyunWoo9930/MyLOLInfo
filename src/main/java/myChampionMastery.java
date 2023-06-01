import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class myChampionMastery {


    public static final JsonObject info;
    public static final JsonObject championId;

    static {
        try {
            info = new JsonParser().parse(new FileReader("jsonCfg/info.json")).getAsJsonObject();
            championId = new JsonParser().parse(new FileReader("jsonCfg/championId.json")).getAsJsonObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("파일을 찾지 못하였습니다.");
        }
    }

    public static final String apiKey = getInfo("api_key");
    public static final String summonerName = getInfo("summoner_name");
    public static final String tag = getInfo("tag");
    public static final String summonerId = getInfo("encrypted_summoner_id");
    public static final String puuid = getInfo("encrypted_puuid");
    public static final String accountId = getInfo("encrypted_account_id");


    public static void main(String[] args) {
        String url = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/" + summonerId + "?api_key=" + apiKey;
        JsonArray championMastery = new JsonParser().parse(getResult(url)).getAsJsonArray();
        JsonArray result_championMastery = new JsonArray();

        for (JsonElement s : championMastery) {
            JsonObject innerObj = new JsonObject();
            Long champion_id = s.getAsJsonObject().get("championId").getAsLong();
            String championName = "null";
            try {
                championName = championId.get(String.valueOf(champion_id)).getAsString();
            } catch (Exception e) {
                System.out.println("championId " + champion_id + "는 존재하지 않은 챔피언 입니다. 업데이트 해주십시오.");
            }
            int championLevel = s.getAsJsonObject().get("championLevel").getAsInt();
            Long championsPoints = s.getAsJsonObject().get("championPoints").getAsLong();

            innerObj.addProperty("championName", championName);
            innerObj.addProperty("championLevel", championLevel + " Lv");
            innerObj.addProperty("championsPoints", championsPoints + "점");
            result_championMastery.add(innerObj);
        }

        System.out.println("result_championMastery = " + result_championMastery);
    }

    public static String getResult(String url) {
        String result = "";
        try {
            // HTTP 연결 설정
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            // 응답 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            result = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getInfo(String param) {
        return info.get(param).getAsString();
    }
}
