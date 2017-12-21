package com.bridgeit.ToDoApp.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.ToDoApp.fbLogin.GoogleLogin;
import com.bridgeit.ToDoApp.model.Response;
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.service.IuserService;
import com.bridgeit.ToDoApp.token.IToken;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class GoogleController {
	@Autowired
	IuserService userModelService;
	@Autowired
	IToken token;

	@RequestMapping(value = "/googleLogin")
	public void googleConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uuid = UUID.randomUUID().toString();
		request.getSession().setAttribute("STATE", uuid);
		String googleLoginURL = GoogleLogin.getGoogleAuthURL(uuid);
		response.sendRedirect(googleLoginURL);
	}

	@RequestMapping(value = "/connectgoogle")
	public ResponseEntity<Response> redirectFromGoogle(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Response responseForMessage=new Response();
		String sessionState = (String) request.getSession().getAttribute("STATE");
		String googlestate = request.getParameter("state");

		if (sessionState == null || !sessionState.equals(googlestate)) {
			response.sendRedirect("googleLogin");
		}

		String error = request.getParameter("error");
		if (error != null && error.trim().isEmpty()) {
			response.sendRedirect("login");
		}

		String authCode = request.getParameter("code");
		String googleaccessToken = GoogleLogin.getAccessToken(authCode);
		JsonNode profile = GoogleLogin.getUserProfile(googleaccessToken);
		System.out.println("profile"+profile);
		System.out.println("User Name : "+profile.get("emails").get(0).get("value").asText());
		UserModel user = userModelService.getDataByEmail(profile.get("emails").get(0).get("value").asText());
		if (user == null) {
			user = new UserModel();
			user.setUserName(profile.get("displayName").asText());
			user.setEmail(profile.get("emails").get(0).get("value").asText());
			user.setActive(1);
			/* user.setPicUrl(profile.get("image").get("url")); */
			user.setPassword("");
			userModelService.registration(user);
			 responseForMessage.setMessage("Hello "+user.getUserName()+" you are new user.");
			return new ResponseEntity<Response>(responseForMessage,
					HttpStatus.ACCEPTED);
			
		} else {
			user = userModelService.getDataByEmail(user.getUserName());
			String token1 = token.genratedToken(user.getId());
			response.setHeader("Authentication", token1);
			Cookie accCookie = new Cookie("socialaccessToken", token1);
			response.addCookie(accCookie);
			responseForMessage.setMessage("Hello " + user.getUserName() + " you are alredy visited here.");
			return new ResponseEntity<Response>(responseForMessage, HttpStatus.ACCEPTED);
		}
	}
}
