package org.enciende.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.enciende.exception.ServiceException;
import org.enciende.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Override
	public void sendEmail(String to, String message,String subject) throws ServiceException {
        try{
        	String url="https://api.mailgun.net/v3/enciende.org/messages";
    		CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost request = new HttpPost(url);
           
            String encoding = Base64.encodeBase64String("api:key-e5be65cdf8c61902d6a5da258776b928".getBytes());
            request.setHeader("Authorization", "Basic "+encoding);
            
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("from", "Contacto enciende <hola@enciende.org>"));
            postParameters.add(new BasicNameValuePair("to", to));
            postParameters.add(new BasicNameValuePair("subject", subject));
            postParameters.add(new BasicNameValuePair("text", message));
            request.setEntity(new UrlEncodedFormEntity(postParameters));
            
			CloseableHttpResponse response = httpclient.execute(request);
			getMapResponse(response);
			if(response.getStatusLine().getStatusCode()!=200){
				throw new ServiceException("Error al enviar email a: "+to);
			}else{
				log.debug("Email enviado con exito a "+to);
			}
		}catch(Exception e){
			log.error("Error al consultar facebook by token",e);
			throw new ServiceException("Error al enviar email a: "+to,e);
		}

	}
	
	public static void main(String[] args) {
		new EmailServiceImpl().sendEmail("luishpm@gmail.com", "Bienvenido al rally", "Bienvenido");
	}
	
	private static Map<String, String> getMapResponse(CloseableHttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null)
            result.append(line);
        log.debug(result.toString());

		return null;
	}

}
