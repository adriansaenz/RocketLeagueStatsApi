package rlstats.api.classes;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ssplugins.rlstats.RLStats;
import com.ssplugins.rlstats.RLStatsAPI;
import com.ssplugins.rlstats.entities.Platform;
import com.ssplugins.rlstats.entities.Player;
import com.ssplugins.rlstats.entities.Playlist;
import com.ssplugins.rlstats.entities.PlaylistInfo;
import com.ssplugins.rlstats.entities.Stat;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;
import org.json.JSONObject;

public class Statistic {

    static final String KEY = "RLOETWPFTMTNE65PWGTS1CW1KMIH7OLI";
    RLStatsAPI api;

    public Statistic() {
        this.api = RLStats.getAPI(KEY);
    }

    public void closeConnection() {
        this.api.shutdownThreads();
    }

    private Player initialization(String user, String platform) {
        Future<Player> future = null;
        Player player = null;
        switch (platform.toUpperCase()) {
            case "PS4":
                future = this.api.getPlayer(user, Platform.PS4);
            case "XBOX":
                future = this.api.getPlayer(user, Platform.XBOX);
            case "STEAM":
            default:
                future = this.api.getPlayer(user, Platform.STEAM);

        }
        try {
            player = future.get();
        } catch (Exception ex) {
        }
        return player;
    }
    
    public String getStatistics(String user, String platform) {

        Player player = this.initialization(user, platform);
        PlaylistInfo info = null;

        String response = "";

        if (player != null) {
                response = player.raw().toString();
        } else {
            JsonObject json = new JsonObject();
            json.addProperty("Error", "1");
            response = json.toString();
        }
        return response;
    }

    public String getStatistics(String user, String platform, int season, int typeMatch) {

        Player player = this.initialization(user, platform);
        PlaylistInfo info = null;

        String response = "";

        if (player != null) {
            if (season < 5) {
                response = player.raw().toString();
            } else {
                info = player.getSeasonInfo(season).getPlaylistInfo(typeMatch);
                response = this.getJsonPlayer(player.getStats().getAllStats(), info);
            }
        } else {
            JsonObject json = new JsonObject();
            json.addProperty("Error", "1");
            response = json.toString();
        }
        return response;
    }

    private String getJsonPlayer(Map<Stat, Integer> raw, PlaylistInfo info) {
        JsonObject json = new JsonObject();

        JsonObject div = new JsonObject();
        div.addProperty("division", info.getDivision());
        div.addProperty("matchesPlayed", info.getMatchesPlayed());
        div.addProperty("rankPoints", info.getRankPoints());
        div.addProperty("Tier", info.getTier());
        json.add("stats", div);
        return json.toString();
    }

    private String objectToJson(PlaylistInfo info) {
        Gson parser = new Gson();
        String o = parser.toJson(info);
        return o;
    }

}
