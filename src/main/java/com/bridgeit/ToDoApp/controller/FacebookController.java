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
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.service.IuserService;
import com.bridgeit.ToDoApp.token.IToken;
import com.fasterxml.jackson.databind.JsonNode;
@RestController
public class FacebookController {
		
	@Autowired
	IuserService userModelService;
	@Autowired
	IToken token;
	
	@RequestMapping(value="/facebookLogin")
	public void facebookConnection(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String uuid = UUID.randomUUID().toString();
		request.getSession().setAttribute("State", uuid);
		String facebookLoginURL = FaceBook.getFacebookURL(uuid);
		response.sendRedirect(facebookLoginURL);
	}
	
	@RequestMapping(value="/connectFB")
	public ResponseEntity<String/*Response*/> redirectURL(HttpServletRequest request,HttpServletResponse response,HttpSession session,UriComponentsBuilder ucBuilder) throws IOException
	{
		//Response errorMessage = new Response();
		String sessionState = (String) request.getSession().getAttribute("State");
		String googlestate = request.getParameter("state");
		
		if(sessionState==null || !sessionState.equals(googlestate))
		{
			response.sendRedirect("facebookLogin");
		}

		String error = request.getParameter("error");
		if (error != null && error.trim().isEmpty()) {
			//errorMessage.setMessage("Error occured Try again.");
			return new ResponseEntity<String>(" "/*errorMessage*/, HttpStatus.BAD_REQUEST);
		}
		
		String authCode = request.getParameter("code");
		String fbAccessToken = FaceBook.getAccessToken(authCode);
		JsonNode profile = FaceBook.getUserProfile(fbAccessToken);
		
		String email= profile.get("email").asText();
		
		UserModel user = userModelService.getDataByEmail(email);
		
		if(user==null) {
			user = new UserModel();
			user.setUserName((profile.get("name").asText()));
			user.setEmail(profile.get("email").asText());
			user.setPassword("");
			user.setActive(1);
			int userId=userModelService.registration(user);
			if(userId>0) {
				String token1 = token.genratedToken(userId);
    			response.setHeader("Authorization", token1);
    			session.setAttribute("token", token1);
    			//errorMessage.setMessage("User Successfully registered.");
    			return new ResponseEntity<String>("User Successfully registered", HttpStatus.ACCEPTED);
 			}
			else
			{
				//errorMessage.setMessage("User is not registered.");
    			return new ResponseEntity<String>("User is not registered", HttpStatus.BAD_REQUEST);
			}
		}else {
			
			String token2 = token.genratedToken(user.getId());
			userModelService.update(user);
			session.setAttribute("token", token);
			//errorMessage.setMessage("User already exist.");
			return new ResponseEntity<String>("User already exist.", HttpStatus.ALREADY_REPORTED);
		}
}
}