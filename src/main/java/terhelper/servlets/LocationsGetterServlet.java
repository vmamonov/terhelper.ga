package terhelper.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.jackson2.JacksonFactory;

import redis.clients.jedis.Jedis;
import terhelper.AppConfig;
import terhelper.Env;
import terhelper.exceptions.TokenExpiresException;
import terhelper.models.Location;
import terhelper.models.Territory;
import terhelper.models.Unit;
import terhelper.services.TerritoryHelperApi;
import terhelper.services.repo.LocationsRepo;
import terhelper.services.repo.TerritoriesRepo;
import terhelper.services.repo.UnitsRepo;
import terhelper.services.storages.CacheStorage;
import terhelper.services.storages.LocalFileStorage;
import terhelper.services.storages.Redis;


public class LocationsGetterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LocationsGetterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	System.out.println("Get locations: Start...");
            TerritoryHelperApi api = new TerritoryHelperApi.Builder(
        		AppConfig.getInstance().getUserIdForApi(), 
        		AppConfig.getInstance().getSecretForApi())
    		.build();
            if(null == api.doAuth()) {
            	response.sendRedirect(api.getUrlAuth().build());
            } 
            Redis redis = new Redis();
            TerritoriesRepo repoTer = TerritoriesRepo.getInstance().setApi(api);
            repoTer.setCache(redis);
            LocationsRepo repoLoc = LocationsRepo.getInstance().setApi(api);
            repoLoc.setCache(redis);
            UnitsRepo repoUnit = UnitsRepo.getInstance().setApi(api);
            repoUnit.setCache(redis);
            CacheStorage.statusesSave(repoLoc.getStatuses());           
            Territory[] territories = repoTer.getAllTerritories();
            ArrayList<Unit> units = new ArrayList<Unit>();
            for (Territory terItem : territories) {
            	Thread.sleep(1000);
            	System.out.println("terItem.Id: " + terItem.Id + " -> " + terItem.Address);
            	ArrayList<Location> locations = repoLoc.getLocationsByTerritoryId(terItem.Id);
            	CacheStorage.locationsSave(locations);           	
            	for (Location loc : locations ) {
            		units.addAll(repoUnit.getUnitsByLocationId(loc.Id));
            	}
            }
            System.out.println("unitsSave...");
            CacheStorage.unitsSave(units);
            System.out.println("Get locations: Completed ");
        } catch (Exception ex) {
        	System.out.println("Get locations: Failed");
            ex.printStackTrace();
        } 
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
