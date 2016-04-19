package org.enciende.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.enciende.service.FacebookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
@Service
public class FacebookServiceImpl implements FacebookService {
	private static final Logger log = LoggerFactory.getLogger(FacebookServiceImpl.class);
	
	@Override
	public String getFacebookIdByToken(String token) {
		String url="https://graph.facebook.com/v2.6/me?fields=id&access_token="+token;
		CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);

        try{
			CloseableHttpResponse response = httpclient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				Map<String, String> respuesta = getMapResponse(response);
				return respuesta.get("id");
			}
		}catch(Exception e){
			log.error("Error al consultar facebook by token",e);
		}
        return null;
	}
	
	private static Map<String, String> getMapResponse(CloseableHttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null)
            result.append(line);
        log.debug(result.toString());
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();

		return gson.fromJson(result.toString(),type);
	}


}
