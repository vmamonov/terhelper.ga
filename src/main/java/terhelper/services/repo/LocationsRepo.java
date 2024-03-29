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
import terhelper.models.Location;
import terhelper.models.Status;
import terhelper.services.TerritoryHelperApi;

public class LocationsRepo extends Repo{
	private static LocationsRepo self;
    private TerritoryHelperApi api;
    private HashMap<String, ArrayList<Location>> locations = new HashMap<String, ArrayList<Location>>();
    private ArrayList<Status> statuses = new ArrayList<Status>();

    private LocationsRepo() {}
    
    public static LocationsRepo getInstance() {
        if (null == self) {
            self = new LocationsRepo();
        }
        return self;
    }
    
    public LocationsRepo setApi(TerritoryHelperApi api) {
        this.api = api;
        return this;
    }
    
    
    public ArrayList<Status> getStatuses() throws IOException, GeneralSecurityException, MalformedURLException, ClassNotFoundException, TokenExpiresException {
    	if (statuses.size() > 0) {
    		return statuses;
    	}
    	GenericUrl url = new GenericUrl("https://territoryhelper.com/api/locationstatuses").set("access_token", api.getAccessToken());
        CloseableHttpResponse response =  api.sendRequest(url);
        if (400 == response.getStatusLine().getStatusCode()) {
        	throw new TokenExpiresException();
        }
        Status[] res = (Status[]) api.getResultFromRessponse(response, Status[].class);
        statuses = new ArrayList<Status>(
        	Arrays.asList(res)
		);
        return statuses;
    }

     
    public ArrayList<Location> getLocationsByTerritoryId(String territoryId) throws TokenExpiresException, IOException, GeneralSecurityException, MalformedURLException, ClassNotFoundException {
    	if (locations.containsKey(territoryId)) {
    		return locations.get(territoryId);
    	}
    	GenericUrl url = new GenericUrl("https://territoryhelper.com/api/territories/"+territoryId+"/locations").set("access_token", api.getAccessToken());
        CloseableHttpResponse response =  api.sendRequest(url);
        if (400 == response.getStatusLine().getStatusCode()) {
        	throw new TokenExpiresException();
        }       
        //TODO
        Location[] tmp = (Location[]) api.getResultFromRessponse(response, Location[].class);
        ArrayList<Location> result = new ArrayList<Location>(Arrays.asList(tmp));  
        locations.put(territoryId, result); 
        return result;
    }   
}
