package terhelper.services.repo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.GeneralSecurityException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;

import terhelper.exceptions.TokenExpiresException;
import terhelper.models.Location;
import terhelper.models.Unit;
import terhelper.services.TerritoryHelperApi;

public class UnitsRepo extends Repo {
	private static UnitsRepo self;
    private TerritoryHelperApi api;
    private Unit[] units = new Unit[0];

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
    
    private void saveInCache(String key, Unit[] units) {
    	for (Unit unitItem : units) {
    		System.out.println("Find unit " + unitItem.Number);
    		String savedUnitIds = cache.get(key + ".ids");
    		String unitIdsToSave = null == savedUnitIds ? unitItem.Id : savedUnitIds+"_"+ unitItem.Id;
    		cache.set(key+".ids", unitIdsToSave);
    		
    		cache.set(key+"."+unitItem.Id+".LocationId", unitItem.LocationId);
    		cache.set(key+"."+unitItem.Id+".Number", unitItem.Number);
    		cache.set(key+"."+unitItem.Id+".StatusId", unitItem.StatusId);
    	}
    }
    
    public Unit[] getUnitsByLocationId(String locationId) throws IOException, GeneralSecurityException, MalformedURLException, ClassNotFoundException, TokenExpiresException {
    	GenericUrl url = new GenericUrl("https://territoryhelper.com/api/locations/"+locationId+"/units").set("access_token", api.getAccessToken());
        System.out.println(url.build());
        CloseableHttpResponse response =  api.sendRequest(url);
        if (400 == response.getStatusLine().getStatusCode()) {
        	throw new TokenExpiresException();
        }
//        System.out.println("UUUUU " + new ObjectMapper().readValue(
//	    		EntityUtils.toString(response.getEntity()),
//				Unit.class
//    		)
//		);
//        System.out.println("response " + response.getEntity().getContent().toString());
        units = (Unit[]) api.getResultFromRessponse(response, Unit[].class);
        
//        for (Unit u : units) {
//        	System.out.println(u.Number);
//        }
        
        
//        System.out.print("units.length -> " + response.getEntity().getContent().toString());
        if (0 != units.length) {
        	saveInCache("unit", units);
        }  
        return units;
    }
}
