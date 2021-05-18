package terhelper.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
import terhelper.Env;
import terhelper.models.Location;
import terhelper.models.Territory;
import terhelper.services.TerritoryHelperApi;
import terhelper.services.repo.LocationsRepo;
import terhelper.services.repo.TerritoriesRepo;
import terhelper.services.repo.UnitsRepo;
import terhelper.services.storages.LocalFileStorage;
import terhelper.services.storages.Redis;


public class LocationsGetterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationsGetterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setStatus(605);
		/*
		 * try { String userId = "5dc2c712c9c5472c859392b103064814"; String signature =
		 * "6722084eb68640b4b9a6d8ab540e6f6f"; String urlAuth =
		 * "https://territoryhelper.com/api/authorize"; String codeVerifier =
		 * "qwertyasdfghzxcvbn"; String redirectUrl =
		 * "http://terhelper.ga:80/auth-confirm"; MessageDigest digest;
		 * 
		 * digest = MessageDigest.getInstance("SHA-256"); String codeChallenge =
		 * Base64.getEncoder().encodeToString(
		 * digest.digest(codeVerifier.getBytes(StandardCharsets.UTF_8)) );
		 * OAuthGetAccessToken urlAccessToken = new OAuthGetAccessToken(urlAuth);
		 * urlAccessToken.set("response_type", "code"); urlAccessToken.set("client_id",
		 * userId); urlAccessToken.set("redirect_uri", redirectUrl);
		 * urlAccessToken.set("state", "ewrrqfrgtqegr");
		 * urlAccessToken.set("code_challenge_method", "S256");
		 * urlAccessToken.set("code_challenge", codeChallenge);
		 * 
		 * OAuthGetTemporaryToken urlTempToken = new
		 * OAuthGetTemporaryToken(urlAccessToken.build());
		 * urlTempToken.set("grant_type", "authorization_code");
		 * urlTempToken.set("client_secret", signature);
		 * urlTempToken.setScheme("https"); urlTempToken.setHost("territoryhelper.com");
		 * urlTempToken.setRawPath("/api/token");
		 * 
		 * AuthorizationCodeFlow flow = new AuthorizationCodeFlow .Builder(
		 * BearerToken.authorizationHeaderAccessMethod(),
		 * GoogleNetHttpTransport.newTrustedTransport(),
		 * JacksonFactory.getDefaultInstance(), urlTempToken, new
		 * HttpExecuteInterceptor() {
		 * 
		 * @Override public void intercept(HttpRequest hr) throws IOException {} },
		 * userId, urlAccessToken.build() ) .setCredentialDataStore(new
		 * LocalFileStorage("./cashe/main", "auth")) .build();
		 * 
		 * Credential credential = flow.loadCredential(userId); if (credential != null
		 * && (credential.getRefreshToken() != null || credential.getExpiresInSeconds()
		 * == null || credential.getExpiresInSeconds() > 60)) { }
		 */
			
			
			
			
			
				/*
				 * String codeVerifier = "qwertyasdfghzxcvbn"; MessageDigest digest =
				 * MessageDigest.getInstance("SHA-256"); String codeChallenge =
				 * Base64.getEncoder().encodeToString(
				 * digest.digest(codeVerifier.getBytes(StandardCharsets.UTF_8)) );
				 * OAuthGetAccessToken accessTokenUrl = new
				 * OAuthGetAccessToken("https://territoryhelper.com/api/authorize");
				 * accessTokenUrl.set("response_type", "code"); accessTokenUrl.set("client_id",
				 * "5dc2c712c9c5472c859392b103064814"); accessTokenUrl.set("redirect_uri",
				 * "http://terhelper.ga:80/auth-confirm"); accessTokenUrl.set("state",
				 * "ewrrqfrgtqegr"); accessTokenUrl.set("code_challenge_method", "S256");
				 * accessTokenUrl.set("code_challenge", codeChallenge);
				 */
            
		//Location[] locations = repo.getLocationsByTerritoryId("5039169");
        //Redis red = new Redis();
        //red.set("vv", locations);
            
//            
            //response.getWriter().append("Redirect auf " + accessTokenUrl.build());
            //response.sendRedirect(accessTokenUrl.build());
   
//		String key = "rr";
//		//Jedis redis = new Jedis("localhost", 6379);
//		Redis redis = new Redis();
//		redis.set(key, "74385278542");
//		String savedLocIds = redis.get(key);
//		System.out.println("savedLocIds " + savedLocIds);
//		String newLoId = "fff";
//		String locIdsToSave = null == savedLocIds ? newLoId : savedLocIds+"_"+ newLoId;
//		System.out.println("locIdsToSave " + locIdsToSave);
//		redis.set(key, locIdsToSave);
//		String res = redis.get(key);
//		System.out.println("result " + res);
//		//System.out.println("result " + res);
//		if (res == null) {
//			System.out.println("AGA");
//		}
		
		
        try {
        	System.out.println("Start...");
            TerritoryHelperApi api = new TerritoryHelperApi.Builder("5dc2c712c9c5472c859392b103064814", "6722084eb68640b4b9a6d8ab540e6f6f").build();
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
//            repoUnit.getUnitsByLocationId("20767459");
//            repoLoc.getLocationsByTerritoryId("3178876");
            repoLoc.getStatuses();
            
            Territory[] territories = repoTer.getAllTerritories();
            for (Territory terItem : territories) {
            	Thread.sleep(1000);
            	Location[] locations = repoLoc.getLocationsByTerritoryId(terItem.Id);
            	for (Location loc : locations ) {
//            		System.out.println("Find units for location " +loc.Address+ " ...");
            		repoUnit.getUnitsByLocationId(loc.Id);
            	}
            }
            System.out.println("Complited!!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
