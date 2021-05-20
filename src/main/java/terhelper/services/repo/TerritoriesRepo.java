package terhelper.services.repo;

import com.google.api.client.http.GenericUrl;
import terhelper.models.Territory;
import terhelper.services.TerritoryHelperApi;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import org.apache.http.client.methods.CloseableHttpResponse;

public class TerritoriesRepo extends Repo{
	private static TerritoriesRepo self;
    private TerritoryHelperApi api;

    private Territory[] territories = new Territory[0];

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
            CloseableHttpResponse response =  api.sendRequest(url);
            territories = (Territory[]) api.getResultFromRessponse(response, Territory[].class);
        }
        return territories;
    }    
}
