import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class riotAPITest {
    @Test
    void MatchId_test() {
        JsonParser parser = new JsonParser();
        String match_id = "KR_6508880299";
        String apiKey = "RGAPI-601e3ffa-15ea-4dc5-8473-e47e96c71d74";
        String url_match = "https://asia.api.riotgames.com/lol/match/v5/matches/" + match_id + "?api_key=" + apiKey;
        JsonObject matchResult = parser.parse(riot.getResult(url_match)).getAsJsonObject();
        JsonObject info = matchResult.get("info").getAsJsonObject();
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


        }
    }
    
    @Test
    void getChampionId() throws FileNotFoundException {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject1 = new JsonParser().parse(new FileReader("championData.json")).getAsJsonObject().get("data").getAsJsonObject();
        for(String key : jsonObject1.keySet()) {
            jsonObject.addProperty(jsonObject1.get(key).getAsJsonObject().get("key").getAsString(), key);
        }
        System.out.println("jsonObject = " + jsonObject);
    }
}
