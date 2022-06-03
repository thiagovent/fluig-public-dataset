package com.samplecomponent.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.customappkey.KeyVO;
import com.samplecomponent.activate.oauth.Activate;
import com.samplecomponent.util.ErrorStatus;
import com.samplecomponent.util.RestConstant;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

@Path("/")
public class PublicDatasetRest {

    @GET
    @Path("/{tenantId}/aws/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSearch(@PathParam("tenantId") Long tenantId, @PathParam("key") String awsKey) throws Exception {
        HttpURLConnection conn = null; 
    	
        try {
        	KeyVO key = Keyring.getKeys(tenantId, Activate.APP_KEY);
        	
        	if (!hasOnlyAlphanumeric(awsKey)) {
        		return Response.status(Status.BAD_REQUEST).build();
        	}
        	
            OAuthConsumer config = config(key);
            URL url = new URL(key.getDomainUrl() + "/dataset/api/v2/dataset-handle/search"
            		+ "?datasetId=aws-instances"
            		+ "&constraintsField=INSTANCE_CODE"
            		+ "&constraintsInitialValue=" + awsKey 
            		+ "&constraintsFinalValue=" + awsKey 
            		+ "&constraintsType=MUST");

        	
            
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(RestConstant.REQUEST_METHOD_GET);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            config.sign(conn);

            conn.connect();

            String response = getConnectionResponse(conn);
            int code = conn.getResponseCode();
            
            System.out.println(String.format("RESPONSE: %d - %s: data: %s", code, conn.getResponseMessage(), response));

            return Response.status(code).entity(response).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e)).build();
        } finally {
			if (conn != null) {
				try {
					conn.disconnect();
				} catch (Exception e) {
					System.err.println(String.format("Ocorreu um erro ao finalizar a conexão: %s", e.getMessage()));
					e.printStackTrace();
				}
			}
		}
    }

    private OAuthConsumer config(KeyVO key) {
        OAuthConsumer consumer = new DefaultOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
        consumer.setTokenWithSecret(key.getToken(), key.getTokenSecret());
        return consumer;
    }
    
    private boolean hasOnlyAlphanumeric(String value) {
    	String regex = "^[a-zA-Z0-9]+$";
   	 
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(value);
    	if (!matcher.matches()) {
    		return false;
    	}
    	
    	return true;
    }
    
    private String getConnectionResponse(HttpURLConnection conn) throws UnsupportedEncodingException, IOException {
    	Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), RestConstant.UTF_8_ENCODE));
    	
    	StringBuffer buffer = new StringBuffer();
    	for (int c = reader.read(); c != -1; c = reader.read()) {
            buffer.append((char) c);
        }
    	
    	return buffer.toString();
    }
    
    
}