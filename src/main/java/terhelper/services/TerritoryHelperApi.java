package terhelper.services;


import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.auth.oauth.OAuthGetTemporaryToken;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.json.jackson2.JacksonFactory;
import terhelper.services.storages.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class TerritoryHelperApi extends Api{
    protected Date date = new Date();
    protected Credential credential;
    private TerritoryHelperApi.Builder builder;
    
    public static class Builder {
        private final String urlBase = "https://territoryhelper.com";
        private final String urlAuth = urlBase + "/api/authorize";
        private final AuthorizationCodeInstalledApp authCodeManager;
        private  String userId;
        
        public Builder(String userId, String signature) throws NoSuchAlgorithmException, IOException, GeneralSecurityException {
            this.userId = userId;
            String codeVerifier = "qwertyasdfghzxcvbn";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String codeChallenge = Base64.getEncoder().encodeToString(
                    digest.digest(codeVerifier.getBytes(StandardCharsets.UTF_8))
            );
            OAuthGetAccessToken accessTokenUrl = new OAuthGetAccessToken(urlAuth);
            accessTokenUrl.set("response_type", "code");
            accessTokenUrl.set("client_id", this.userId);
            accessTokenUrl.set("redirect_uri", "http://localhost/Callback");
            accessTokenUrl.set("state", "ewrrqfrgtqegr");
            accessTokenUrl.set("code_challenge_method", "S256");
            accessTokenUrl.set("code_challenge", codeChallenge);
            
            OAuthGetTemporaryToken url = new OAuthGetTemporaryToken(accessTokenUrl.build());
            url.set("grant_type", "authorization_code");
            url.set("client_secret", signature);
            url.setScheme("https");
            url.setHost("territoryhelper.com");
            url.setRawPath("/api/token");
            
            AuthorizationCodeFlow flow = new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                url,
                new HttpExecuteInterceptor() {
                    @Override
                    public void intercept(HttpRequest hr) throws IOException {}
                },
                userId,
                accessTokenUrl.build()
            )
                .setCredentialDataStore(new LocalFileStorage("./cashe/main", "auth"))
                .build();
            
            authCodeManager =  new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver());
        }

        
        public AuthorizationCodeInstalledApp getAuthCodeManager() {
            return authCodeManager;
        }
        
        
        public TerritoryHelperApi build() throws IOException, GeneralSecurityException {
            return new TerritoryHelperApi(this);
        }
    }
    
    
    private TerritoryHelperApi(TerritoryHelperApi.Builder builder) throws NoSuchAlgorithmException, IOException, GeneralSecurityException {
        this.builder = builder;
        doAuth();
    }

    
    protected boolean isValidAuth () {
        Long Timestamp = credential.getExpirationTimeMilliseconds();
        return (null != Timestamp) && (date.getTime() < Timestamp);
    }
        
    
    public Credential doAuth() throws IOException {
        credential = builder.getAuthCodeManager().authorize(builder.userId);
        return credential;
    }
    
    
    public String getAccessToken() throws IOException, GeneralSecurityException {
        if (!isValidAuth() && null != credential.getRefreshToken()) {
            credential.refreshToken();
        } else {
            doAuth();
        }
        return credential.getAccessToken();
    }

    
    public Credential getCredential() {
        return credential;
    }
}