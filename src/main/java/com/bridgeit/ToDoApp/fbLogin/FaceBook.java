package com.bridgeit.ToDoApp.fbLogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FaceBook {
	public static final String app_Id = "1976981345893014";
	public static final String secret_id = "1c9c64e45c074a1af386e6d5b6e5abee";
	public static final String Redirect_URI = "https://note-keep.herokuapp.com/connectFB";

	public static String getFacebookURL(String uuid) {
		String facebookLoginURL = "";
		try {
			facebookLoginURL = "http://www.facebook.com/dialog/oauth?" + "client_id=" + app_Id + "&redirect_uri="
					+ URLEncoder.encode(Redirect_URI, "UTF-8") + "&state=" + uuid + "&response_type=code"
					+ "&scope=public_profile,email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return facebookLoginURL;
	}

	public static String getAccessToken(String authCode) throws UnsupportedEncodingException {
		String fbAccessTokenURL = "https://graph.facebook.com/v2.9/oauth/access_token?" + "client_id=" + app_Id
				+ "&redirect_uri=" + URLEncoder.encode(Redirect_URI, "UTF-8") + "&client_secret=" + secret_id + "&code="
				+ authCode;
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(fbAccessTokenURL);
		Form form = new Form();

		form.param("client_id", app_Id);
		form.param("client_secret", secret_id);
		form.param("redirect_uri", Redirect_URI);
		form.param("code", authCode);
		form.param("grant_type", "authorization_code");

		Response response = target.request().accept(MediaType.APPLICATION_JSON).post(Entity.form(form));
		String facebookAccessToken = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		String acc_token = null;
		try {
			acc_token = mapper.readTree(facebookAccessToken).get("access_token").asText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acc_token;
	}

	public static JsonNode getUserProfile(String fbAccessToken) {

		String fbgetUserURL = "https://graph.facebook.com/v2.9/me?access_token=" + fbAccessToken
				+ "&fields=id,name,email,picture";
		ResteasyClient restCall = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = restCall.target(fbgetUserURL);

		String headerAuth = "Bearer " + fbAccessToken;
		Response response = target.request().header("Authorization", headerAuth).accept(MediaType.APPLICATION_JSON)
				.get();
		String profile = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();

		JsonNode FBprofile = null;
		try {
			FBprofile = mapper.readTree(profile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		restCall.close();
		return FBprofile;
	}
}
