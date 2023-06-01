import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class riot {
    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        String apiKey = "RGAPI-42d7b748-58de-4237-8d47-77f758281f43";
        String summonerName = "Beatofmydrum";
        String tag = "9930";
        String summonerId = "QPTWJNVT3dvX_vlczQAW2YRkEojaWcUj5kTzlE0vYyZzpCE";
        String puuid = "Q6Q74HnhdXxqRVYU4Y6qLvuQvF4j6yPAsXTtRNby_G37bW90VPEoEsUIFRhM5Snok6Py8zW6Oa-Khg";
        String accountId = "U7eMoYVGcr6zq4nwYpUdGRktagQYUzh_bBogp7enkP7LJik";
        // 전적 최신순 몇개 받을지
        int count = 1;

        // Riot API 요청 URL 생성
        // 소환서 정보 (summoner name)
//        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + apiKey;
        // 소환사 정보 (tag, riot id)
//        String url = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/"+summonerName+"/"+tag+"?api_key=" + apiKey;
        /** 게임 전적 match id
         * 최근 일반게임 100게임의 match id를 가져옴.
        **/
        String url_match_id = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?type=normal&start=0&count=" + count + "&api_key=" + apiKey;

        String match_result1 = getResult(url_match_id);
        String matchResult = parser.parse(match_result1).getAsJsonArray().get(0).getAsString();

        String url_match = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchResult + "?api_key=" + apiKey;
        JsonObject match_result = parser.parse(riot.getResult(url_match)).getAsJsonObject();
        JsonObject info = match_result.get("info").getAsJsonObject();
        JsonArray participants = info.get("participants").getAsJsonArray();
        List<String> participants_name = new ArrayList<>();
        JsonObject result = new JsonObject();
        participants.forEach(s -> {
            String name = s.getAsJsonObject().get("summonerName").getAsString();
            participants_name.add(name);
            result.add(name, s);
        });
        if (participants_name.toString().contains("나는머영") && participants_name.toString().contains("장보로") && participants_name.toString().contains("Crime") && participants_name.toString().contains("커부렁") && participants_name.toString().contains("대리받은저능아")) {
            JsonObject 나는머영 = result.get("나는머영").getAsJsonObject();
            JsonObject 장보로 = result.get("장보로").getAsJsonObject();
            JsonObject Crime = result.get("Crime").getAsJsonObject();
            JsonObject 커부렁 = result.get("커부렁").getAsJsonObject();
            JsonObject 대리받은저능아 = result.get("대리받은저능아").getAsJsonObject();

            System.out.println("나는머영 = " + 나는머영);
            System.out.println("장보로 = " + 장보로);
            System.out.println("Crime = " + Crime);
            System.out.println("커부렁 = " + 커부렁);
            System.out.println("대리받은저능아 = " + 대리받은저능아);

            riot.putDB("나는머영", 나는머영);
            riot.putDB("장보로", 장보로);
            riot.putDB("Crime", Crime);
            riot.putDB("커부렁", 커부렁);
            riot.putDB("대리받은저능아", 대리받은저능아);


        } else {
            System.out.println("전부 게임을 하지 않았습니다." + participants_name);
        }



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

    public static void putDB(String name , JsonObject object) {
        String url = "jdbc:postgresql://localhost:5432/ipbh";
        String username = "postgres";
        String password = "";
        try {
            // PostgreSQL 드라이버 로드
            Class.forName("org.postgresql.Driver");

            // 데이터베이스 연결
            Connection connection = DriverManager.getConnection(url, username, password);

            // 데이터 삽입을 위한 INSERT 쿼리 작성
            String insertQuery = "INSERT INTO ghltlrql_data (name, data, time) VALUES (?, cast(? as jsonb), NOW())";

            // PreparedStatement 생성
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // 데이터 바인딩 및 실행
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, object.toString());
            preparedStatement.executeUpdate();

            // 리소스 정리
            preparedStatement.close();
            connection.close();

            System.out.println("데이터가 성공적으로 삽입되었습니다.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
