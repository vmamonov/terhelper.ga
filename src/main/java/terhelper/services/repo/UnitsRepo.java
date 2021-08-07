package terhelper.services.repo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.http.client.methods.CloseableHttpResponse;
import com.google.api.client.http.GenericUrl;
import terhelper.exceptions.TokenExpiresException;
import terhelper.models.Unit;
import terhelper.services.TerritoryHelperApi;

public class UnitsRepo extends Repo {
	private static UnitsRepo self;
    private TerritoryHelperApi api;
    private HashMap<String, ArrayList<Unit>> units = new HashMap<String, ArrayList<Unit>>();
    private UnitsRepo() {}
    
    public static UnitsRepo getInstance() {
        if (null == self) {
            self = new UnitsRepo();
        }
        return self;
    }
    
    public UnitsRepo setApi(TerritoryHelperApi api) {
        this.api = api;
        return this;
    }
        
    public ArrayList<Unit> getUnitsByLocationId(String locationId) throws IOException, GeneralSecurityException, MalformedURLException, ClassNotFoundException, TokenExpiresException {
    	if (units.containsKey(locationId)) {
    		return units.get(locationId);
    	}
    	GenericUrl url = new GenericUrl("https://territoryhelper.com/api/locations/"+locationId+"/units").set("access_token", api.getAccessToken());
        CloseableHttpResponse response =  api.sendRequest(url);
        if (400 == response.getStatusLine().getStatusCode()) {
        	throw new TokenExpiresException();
        }
        //TODO
        Unit[] tmp = (Unit[]) api.getResultFromRessponse(response, Unit[].class); 
        ArrayList<Unit> result = new ArrayList<Unit>(Arrays.asList(tmp));  
        units.put(locationId, result); 
        //System.out.println("get units by Locations: " + url.build() + " result(size): " + result.size());
        return result;
    }    
}
