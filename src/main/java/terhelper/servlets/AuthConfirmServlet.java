package terhelper.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
//import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import java.security.MessageDigest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.jackson2.JacksonFactory;

import terhelper.models.Territory;
import terhelper.services.TerritoryHelperApi;
import terhelper.services.repo.TerritoriesRepo;
import terhelper.services.storages.LocalFileStorage;

/**
 * Servlet implementation class AuthConfirmServlet
 */
public class AuthConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		//response.getWriter().append("Code: " + code);
//		
//		OAuthGetTemporaryToken url = new OAuthGetTemporaryToken("https://territoryhelper.com");
//        url.set("grant_type", "authorization_code");
//        url.set("redirect_uri", "http://terhelper.ga:80/auth-confirm");
//        url.set("code", code);
//        url.set("client_id", "5dc2c712c9c5472c859392b103064814");      
//        url.set("client_secret", "6722084eb68640b4b9a6d8ab540e6f6f");
//        url.setScheme("https");
//        url.setHost("territoryhelper.com");
//        url.setRawPath("/api/token");
//        
//        response.getWriter().append("Link: " + url.build());
//        
//        response.sendRedirect(url.build());
        
        
        
        
        
        
		/*
		 * String userId = "5dc2c712c9c5472c859392b103064814"; String signature =
		 * "6722084eb68640b4b9a6d8ab540e6f6f"; String urlAuth =
		 * "https://territoryhelper.com/api/authorize"; String codeVerifier =
		 * "qwertyasdfghzxcvbn"; String redirectUrl =
		 * "http://terhelper.ga:80/auth-confirm"; MessageDigest digest; try { digest =
		 * MessageDigest.getInstance("SHA-256"); String codeChallenge =
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
		 */
//			String code = request.getParameter("code");
//	        TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectUrl).execute();
//	        // store credential and return it
//	        flow.createAndStoreCredential(tokenResponse, userId);
//	        response.getWriter().append("AT: " + tokenResponse.getAccessToken());
//	        
//		} catch (GeneralSecurityException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
        
		try {
            TerritoryHelperApi api = new TerritoryHelperApi.Builder("5dc2c712c9c5472c859392b103064814", "6722084eb68640b4b9a6d8ab540e6f6f").build();
            String code = request.getParameter("code");
            TokenResponse tokenResponse = api.getFlow().newTokenRequest(code).setRedirectUri(api.getUrlRedirect()).execute();
            api.getFlow().createAndStoreCredential(tokenResponse, api.getUserId());
	        response.getWriter().append("AT: " + tokenResponse.getAccessToken());
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        
        //response.getWriter().append("Viktor");
        //System.out.println(url.build());
		
//		TerritoryHelperApi api;
//		try {
//			api = new TerritoryHelperApi.Builder("5dc2c712c9c5472c859392b103064814", "6722084eb68640b4b9a6d8ab540e6f6f").build();
//			TokenResponse tokenResponse = api
//					   .getBuilder()
//					   .getAuthCodeManager()
//					   .getFlow()
//					   .newTokenRequest(code)
//					   .setRedirectUri(api.getBuilder().getAuthCodeManager().getReceiver().getRedirectUri())
//					   .execute();
//			api.getBuilder().getAuthCodeManager().getFlow().createAndStoreCredential(tokenResponse, "5dc2c712c9c5472c859392b103064814");
//			api.getBuilder().getAuthCodeManager().getReceiver().stop();
//		} catch (IOException | GeneralSecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
