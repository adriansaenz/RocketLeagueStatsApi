package rlstats.api.classes;

import com.ssplugins.rlstats.RLStats;
import com.ssplugins.rlstats.RLStatsAPI;
import com.ssplugins.rlstats.entities.Platform;
import com.ssplugins.rlstats.entities.Player;
import com.ssplugins.rlstats.entities.Playlist;
import com.ssplugins.rlstats.entities.PlaylistInfo;
import java.util.concurrent.Future;

public class Statistic {

    static final String KEY = "RLOETWPFTMTNE65PWGTS1CW1KMIH7OLI";
    RLStatsAPI api;
    
    public Statistic() {
        this.api = RLStats.getAPI(KEY);
    }
    
    public void closeConnection(){
        this.api.shutdownThreads();
    }

    private void initialization(String user, String platform){
        Future<Player> future;
        switch(platform){
            case "PS4":
                future = this.api.getPlayer(user, Platform.PS4);
            case "XBOX":
                future = this.api.getPlayer(user, Platform.XBOX);
            case "STEAM":
                future = this.api.getPlayer(user, Platform.STEAM);
            default:
        }
        
    }
    
    public void getStatistics(String user, String platform, int season, int typeMatch) {
        
        
        Player player = null;
        
        try{ player = future.get(); }catch(Exception ex){}
        
        PlaylistInfo info = player.getSeasonInfo(season).getPlaylistInfo(typeMatch);

        int tier = info.getTier();
        int division = info.getDivision();
        System.out.println(info.getMatchesPlayed() + " " + tier + " " + division + " " + info.getRankPoints() + " " + player.getStats().raw());

    }
    
    

}
