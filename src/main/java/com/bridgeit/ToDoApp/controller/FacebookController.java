package com.bridgeit.ToDoApp.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.bridgeit.ToDoApp.fbLogin.FaceBook;
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
public class FacebookController {

	@Autowired
	IuserService userModelService;
	@Autowired
	IToken token;

	@RequestMapping(value = "/facebookLogin")
	public void facebookConnection(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uuid = UUID.randomUUID().toString();
		request.getSession().setAttribute("State", uuid);
		String facebookLoginURL = FaceBook.getFacebookURL(uuid);
		response.sendRedirect(facebookLoginURL);
	}

	@RequestMapping(value = "/connectFB")
	public ResponseEntity<Response> redirectURL(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, UriComponentsBuilder ucBuilder) throws IOException {
		Response message = new Response();
		String sessionState = (String) request.getSession().getAttribute("State");
		String googlestate = request.getParameter("state");

		if (sessionState == null || !sessionState.equals(googlestate)) {
			response.sendRedirect("facebookLogin");
		}

		String error = request.getParameter("error");
		if (error != null && error.trim().isEmpty()) {
			message.setMessage("Error occured Try again.");
			return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
		}

		String authCode = request.getParameter("code");
		String fbAccessToken = FaceBook.getAccessToken(authCode);
		JsonNode profile = FaceBook.getUserProfile(fbAccessToken);

		String email = profile.get("email").asText();
		System.out.println("Profile :" + profile);
		UserModel user = userModelService.getDataByEmail(email);

		if (user == null) {
			user = new UserModel();
			user.setUserName((profile.get("name").asText()));
			user.setEmail(profile.get("email").asText());
			System.out.println(profile.get("email").asText());
			System.out.println("value" + profile.get("picture").get("data").get("url").toString());
			user.setPassword("");
			user.setProFile(profile.get("picture").get("data").get("url").asText());
			user.setActive(1);
			int userId = userModelService.registration(user);
			if (userId > 0) {
				String token1 = token.genratedToken(userId);
				response.setHeader("Authorization", token1);
				session.setAttribute("jwt", token1);
				message.setMessage("User Successfully registered.");
				response.sendRedirect("https://note-keep.herokuapp.com/#!/DummyHome");
				return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
			} else {
				message.setMessage("User is not registered.");
				return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
			}
		} else {

			String token2 = token.genratedToken(user.getId());
			userModelService.update(user);
			session.setAttribute("jwt", token2);
			message.setMessage("User already exist.");
			response.sendRedirect("https://note-keep.herokuapp.com/#!/DummyHome");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
	}

	/*
	 * ===================================get
	 * token===========================================================
	 */
	@RequestMapping(value = "/getToken")
	public ResponseEntity<Response> getToken(HttpSession session) {
		Response responseMessage = new Response();
		responseMessage.setMessage((String) session.getAttribute("jwt"));

		return new ResponseEntity<Response>(responseMessage, HttpStatus.ACCEPTED);
	}
}
