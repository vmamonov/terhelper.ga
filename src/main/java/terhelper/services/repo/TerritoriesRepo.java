package terhelper.services.repo;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.GenericUrl;

import redis.clients.jedis.Jedis;
import terhelper.models.Location;
import terhelper.models.Territory;
import terhelper.services.TerritoryHelperApi;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;

public class TerritoriesRepo extends Repo{
	private static TerritoriesRepo self;
    private TerritoryHelperApi api;

    private Territory[] territories = new Territory[0];
    private Location[] locations = new Location[0];

    private TerritoriesRepo() {}
    
    public static TerritoriesRepo getInstance() {
        if (null == self) {
            self = new TerritoriesRepo();
        }
        return self;
    }
       
    public TerritoriesRepo setApi(TerritoryHelperApi api) {
        this.api = api;
        return this;
    }
        
    public Territory[] getAllTerritories() throws IOException, GeneralSecurityException, MalformedURLException, ClassNotFoundException {
        if (0 == territories.length) {       
            GenericUrl url = new GenericUrl("https://territoryhelper.com/api/territories").set("access_token", api.getAccessToken());
            System.out.println(url.build());
            CloseableHttpResponse response =  api.sendRequest(url);
            territories = (Territory[]) api.getResultFromRessponse(response, Territory[].class);
        }
        return territories;
    }    
    
	/*
	 * public boolean removeAllLocationsByTerritory(String territoryId) { try {
	 * GenericUrl url = new
	 * GenericUrl("https://territoryhelper.com/api/territories/" + territoryId +
	 * "/locations").set("access_token", api.getAccessToken());
	 * System.out.println(url.build()); CloseableHttpResponse response =
	 * api.sendRequest(url, "DELETE"); if (null != response) {
	 * System.out.println(response.getStatusLine().toString()); } else {
	 * System.out.println("nullable response"); } } catch (Exception ex) {
	 * System.out.println(ex.getMessage()); return false; } return true; }
	 */
}
