package terhelper.services;


import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.jackson2.JacksonFactory;
import terhelper.services.storages.*;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class TerritoryHelperApi extends Api{
    protected Date date = new Date();
    private TerritoryHelperApi.Builder builder;
    protected Credential credential;    
//    private String authUrl;
      
    public static class Builder {
        private AuthorizationCodeFlow flow;
        private String userId;
        private String signature;
        private OAuthGetAccessToken urlAccessToken;
        private String redirect_uri = "http://terhelper.ga:80/auth-confirm";
        
        public Builder(String userIdParam, String signatureParam) throws NoSuchAlgorithmException, IOException, GeneralSecurityException {
            userId = userIdParam;
            signature = signatureParam;
            urlAccessToken = new OAuthGetAccessToken("https://territoryhelper.com/api/authorize");
            urlAccessToken.set("response_type", "code");
            urlAccessToken.set("client_id", userId);
            urlAccessToken.set("redirect_uri", redirect_uri);
            urlAccessToken.set("state", "ewrrqfrgtqegr");
            urlAccessToken.set("code_challenge_method", "S256");
            String codeVerifier = "qwertyasdfghzxcvbn";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String codeChallenge = Base64.getEncoder().encodeToString(
                    digest.digest(codeVerifier.getBytes(StandardCharsets.UTF_8))
            );
            urlAccessToken.set("code_challenge", codeChallenge);
            
            OAuthGetTemporaryToken urlTempToken = new OAuthGetTemporaryToken(urlAccessToken.build());
            urlTempToken.set("grant_type", "authorization_code");
            urlTempToken.set("client_secret", signature);
            urlTempToken.setScheme("https");
            urlTempToken.setHost("territoryhelper.com");
            urlTempToken.setRawPath("/api/token");
            
            flow = new AuthorizationCodeFlow
	    		.Builder(
				    BearerToken.authorizationHeaderAccessMethod(),
				    GoogleNetHttpTransport.newTrustedTransport(),
				    JacksonFactory.getDefaultInstance(),
				    urlTempToken,
				    new HttpExecuteInterceptor() {
				        @Override
				        public void intercept(HttpRequest hr) throws IOException {}
				    },
				    userId,
				    urlAccessToken.build()
					)
			    .setCredentialDataStore(new LocalFileStorage("./cashe/main", "auth"))
			    .build();
            
//         	private final String hostConfirm = "terhelper.ga";
//        	private final Integer portConfirm = 80;
//        	private final String collbackPathConfirm = "/auth-confirm";
//            authCodeManager =  new AuthorizationCodeInstalledApp(flow, 
//            	new LocalServerReceiver.Builder().setHost(hostConfirm).setPort(portConfirm).setCallbackPath(collbackPathConfirm).build()     		
//    		);
        }

//        new LocalServerReceiver(urlConfirm, portConfirm, collbackPathConfirm, null, null)
        
//        public AuthorizationCodeInstalledApp getAuthCodeManager() {
//            return authCodeManager;
//        }
        
//        public OAuthGetAccessToken 
        
        public TerritoryHelperApi build() throws IOException, GeneralSecurityException {
            return new TerritoryHelperApi(this);
        }
    }
    
    
    private TerritoryHelperApi(TerritoryHelperApi.Builder builder) throws NoSuchAlgorithmException, IOException, GeneralSecurityException {
        this.builder = builder;
        doAuth();
    }

    public Credential getCredential() {
        return credential;
    }
    
    public String getUrlRedirect() {
    	return builder.redirect_uri;
    }
    
    protected boolean isValidAuth () {
        Long Timestamp = credential.getExpirationTimeMilliseconds();
        return (null != Timestamp) && (date.getTime() < Timestamp);
    }
        
    public OAuthGetAccessToken getUrlAuth() {
    	return builder.urlAccessToken;
    }
    
    
    public Credential doAuth() throws IOException {
    	credential = builder.flow.loadCredential(builder.userId);
		if (credential != null
		    && (credential.getRefreshToken() != null
		    || credential.getExpiresInSeconds() == null
		    || credential.getExpiresInSeconds() > 60)
	    ) {
			return credential;
		} else {
			return null;
		}
    	
//    	AuthorizationCodeRequestUrl rr = builder.getAuthCodeManager().getFlow()
//		.newAuthorizationUrl()
//		.setRedirectUri(
//			builder.getAuthCodeManager().getReceiver().getRedirectUri()
//		);
//    	
//    	CloseableHttpClient httpClient = HttpClients.createDefault();
//    	
//    	CloseableHttpResponse response = httpClient.execute(new HttpGet(rr.build()));
//    	
//    	
//    	System.out.println("Link " + rr.build());
//    	
//    	BufferedReader br = new BufferedReader(    
//    			new InputStreamReader(response.getEntity().getContent())
//		);
//    	StringBuilder output = new StringBuilder();
//    	  
//	    String line = br.readLine();
//	    while (line != null) {
//	      output.append(line);
//	      line = br.readLine();
//	    }
//    	authUrl = rr.build();
//    	builder.getAuthCodeManager().getReceiver().stop();
//    	System.out.println("dffd Link ");
//        credential = builder.getAuthCodeManager().authorize(builder.userId);
//        return credential;
    }
    public String getUserId() {
    	return builder.userId;
    }
    
    public AuthorizationCodeFlow getFlow() {
    	return builder.flow;
    }
    
    public String getAccessToken() throws IOException, GeneralSecurityException {
        if (!isValidAuth() && null != credential.getRefreshToken()) {
            credential.refreshToken();
        } else {
//            doAuth();
        }
        return credential.getAccessToken();
    }
}