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

import terhelper.AppConfig;
import terhelper.models.Territory;
import terhelper.services.TerritoryHelperApi;
import terhelper.services.repo.TerritoriesRepo;
import terhelper.services.storages.LocalFileStorage;


public class AuthConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AuthConfirmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			TerritoryHelperApi api = new TerritoryHelperApi.Builder(
        		AppConfig.getInstance().getUserIdForApi(), 
        		AppConfig.getInstance().getSecretForApi())
    		.build();
            String code = request.getParameter("code");
            TokenResponse tokenResponse = api.getFlow().newTokenRequest(code).setRedirectUri(api.getUrlRedirect()).execute();
            api.getFlow().createAndStoreCredential(tokenResponse, api.getUserId());
	        response.getWriter().append("AT: " + api.getUrlRedirect());
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
