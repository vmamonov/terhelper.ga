package terhelper.services.repo;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.google.api.client.http.GenericUrl;

import terhelper.ResultStatus;
import terhelper.exceptions.TokenExpiresException;
import terhelper.models.Location;
import terhelper.models.Status;
import terhelper.models.Territory;
import terhelper.services.TerritoryHelperApi;

public class LocationsRepo extends Repo{
	private static LocationsRepo self;
    private TerritoryHelperApi api;
    private Location[] locations = new Location[0];
    private Status[] statuses = new Status[0];

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
    
    private void saveInCache(String key, Location[] locations) {
    	for (Location loc : locations) { 		
    		String savedLocIds = cache.get(key + ".ids");
    		String locIdsToSave = null == savedLocIds ? loc.Id : savedLocIds+"_"+ loc.Id;
    		cache.set(key+".ids", locIdsToSave);
    		//cache.set(key+"."+loc.Id+".TerritoryId", loc.TerritoryId);
    		cache.set(key+"."+loc.Id+".StatusId", loc.StatusId);
    		cache.set(key+"."+loc.Id+".Address", loc.Address);
    	}
    }
    
    public Status[] getStatuses() throws IOException, GeneralSecurityException, MalformedURLException, ClassNotFoundException, TokenExpiresException {
    	GenericUrl url = new GenericUrl("https://territoryhelper.com/api/locationstatuses").set("access_token", api.getAccessToken());
        System.out.println(url.build());
        CloseableHttpResponse response =  api.sendRequest(url);
        if (400 == response.getStatusLine().getStatusCode()) {
        	api.getCredential().refreshToken();
//        	throw new TokenExpiresException();
        }
        statuses = (Status[]) api.getResultFromRessponse(response, Status[].class);
        if (0 != statuses.length) {
        	/** TODO */
        	for (Status statusItem : statuses) {
        		cache.set("status."+statusItem.Id+".InternalName", statusItem.InternalName);
        		cache.set("status."+statusItem.Id+".StatusId", statusItem.LocalizedName);
        	}
        }
        return statuses;
    }
    
    public Location[] getLocationsByTerritoryId(String territoryId) throws TokenExpiresException, IOException, GeneralSecurityException, MalformedURLException, ClassNotFoundException {
    	GenericUrl url = new GenericUrl("https://territoryhelper.com/api/territories/"+territoryId+"/locations").set("access_token", api.getAccessToken());
        System.out.println(url.build());
        CloseableHttpResponse response =  api.sendRequest(url);
        if (400 == response.getStatusLine().getStatusCode()) {
        	api.getCredential().refreshToken();
        }
        
        locations = (Location[]) api.getResultFromRessponse(response, Location[].class);
        if (0 != locations.length) {
        	saveInCache("loc", locations);
        }
        return locations;
    }
    
   // /api/locations/{location id}/units
   // https://territoryhelper.com/api/locations/20758471/units?access_token=hTqYJxROOsjTNHG_KS7k_nsHL3WavTKES6N7nThMNK41
    //https://territoryhelper.com/api/locationstatuses?access_token=hTqYJxROOsjTNHG_KS7k_nsHL3WavTKES6N7nThMNK41
    // https://territoryhelper.com/api/locations/20758471/units?access_token=hTqYJxROOsjTNHG_KS7k_nsHL3WavTKES6N7nThMNK41
}
