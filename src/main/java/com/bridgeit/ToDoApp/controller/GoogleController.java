package com.bridgeit.ToDoApp.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

/**
 * @author ThakurGulab
 *
 */
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
	public ResponseEntity<Response> redirectFromGoogle(HttpSession session,HttpServletRequest request,
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
			System.out.println(profile.get("image").get("url").asText());
			user.setProFile(profile.get("image").get("url").asText());
			user.setActive(1);
			/* user.setPicUrl(profile.get("image").get("url")); */
			user.setPassword("");
			int id=userModelService.registration(user);
			String jwtToken=token.genratedToken(id);
		/*	 responseForMessage.setMessage(jwtToken);*/
			 session.setAttribute("jwt", jwtToken);
			 responseForMessage.setMessage("Hello "+user.getUserName()+" you are new user.");
			 response.sendRedirect("http://localhost:8080/ToDoApp/#!/DummyHome");
			return new ResponseEntity<Response>(responseForMessage,
					HttpStatus.ACCEPTED);
			
		} else {
			user = userModelService.getDataByEmail(user.getEmail());
			String token1 = token.genratedToken(user.getId());
			user.setUserName(profile.get("displayName").asText());
			user.setEmail(profile.get("emails").get(0).get("value").asText());
			System.out.println(profile.get("image").get("url").asText());
			user.setProFile(profile.get("image").get("url").asText());
			user.setActive(1);
			/* user.setPicUrl(profile.get("image").get("url")); */
			user.setPassword("");
			userModelService.update(user);
			response.setHeader("Authentication", token1);
			Cookie accCookie = new Cookie("socialaccessToken", token1);
			response.addCookie(accCookie);
			session.setAttribute("jwt", token1);
			responseForMessage.setMessage("Hello " + user.getUserName() + " you are alredy visited here.");
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/DummyHome");
			return new ResponseEntity<Response>(responseForMessage, HttpStatus.ACCEPTED);
		}
	}
}
