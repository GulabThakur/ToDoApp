package com.bridgeit.ToDoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.ToDoApp.email.IEmail;
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.security.IPasswordencode;
import com.bridgeit.ToDoApp.service.IuserService;
import com.bridgeit.ToDoApp.token.IToken;
import com.bridgeit.ToDoApp.validation.IValidation;

/**
 * @author ThakurGulab
 *
 */
@RestController
public class ControllerUser {
	@Autowired
	private IuserService userModelService;
	@Autowired
	private IPasswordencode encode;
	@Autowired
	private IValidation valid;
	@Autowired
	private IEmail email;
	@Autowired
	private IToken token;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registere(@RequestBody UserModel user ,HttpRequest request) {
		String status = valid.valid(user);
		String url=request.getHeaders().toString();
		System.out.println(url);
		if (status != null) {
			String endodePsd = encode.endode(user);
			// we are using false because that time this user only register
			user.setActive(false);
			user.setPassword(endodePsd);
			int id = userModelService.registration(user);
			String token1 = token.genratedToken(id);
			String url1="/active/"+token1+"";
			email.registration(user.getEmail(), url1, "7024082813");
			return new ResponseEntity<String>("sucessfull ragister", HttpStatus.OK);
		}
		return null;
	}

	@RequestMapping(value = "/login/{email}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@PathVariable("email") String email, @PathVariable("password") String psd) {
		userModelService.login(email, psd);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/active/{token:.+}", method = RequestMethod.POST)
	public ResponseEntity<String> activeLogin(@PathVariable("token") String jwt) {
		int id = token.varifyToken(jwt);
		UserModel user=userModelService.update(id);
		if(id==1) 
		{
			user.setActive(true);
		}
		return null;

	}
}
