package terhelper.services;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import com.google.api.client.http.GenericUrl;
import java.net.MalformedURLException;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Api {
	public CloseableHttpResponse sendRequest(GenericUrl url) throws MalformedURLException, IOException, ClassNotFoundException {
        return sendRequest(url, "GET");
    }
    
    public CloseableHttpResponse sendRequest(GenericUrl url, String method) throws MalformedURLException, IOException, ClassNotFoundException {
        CloseableHttpResponse response;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        switch (method) {
            case "POST":
                response = httpClient.execute(new HttpPost(url.build()));
                break;
            case "DELETE":
                response = httpClient.execute(new HttpDelete(url.build()));
                break;
            case "GET":
            default:
               response = httpClient.execute(new HttpGet(url.build()));
        }
        return response;
    }
    
    public Object getResultFromRessponse(CloseableHttpResponse response, Class mappedClass) throws IOException {
        if (null == response) {
            return new Object();
        }
       return new ObjectMapper().readValue(EntityUtils.toString(response.getEntity()), mappedClass);
    }
}
