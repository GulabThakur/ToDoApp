package com.bridgeit.ToDoApp.controller;

import javax.servlet.http.HttpServletRequest;

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
	public ResponseEntity<String> registere(@RequestBody UserModel user, HttpServletRequest request) {
		String status = valid.valid(user);
		UserModel user1 = userModelService.getDataByEmail(user.getEmail());
		if (user1 == null) {
			String url = request.getRequestURL().toString();
			System.out.println(url);
			if (status == null) {
				String endodePsd = encode.endode(user);
				// we are using false because that time this user only register
				user.setActive(0);
				user.setPassword(endodePsd);
				int id = userModelService.registration(user);
				String token1 = token.genratedToken(id);
				url = url.substring(0, url.lastIndexOf("/")) + "/active/" + token1;
				System.out.println(url);
				email.registration(user.getEmail(), url, "7024082813");
				return new ResponseEntity<String>("sucessfull ragister", HttpStatus.OK);
			}
			return new ResponseEntity<String>(status, HttpStatus.OK);
		}
		return new ResponseEntity<String>("already register ", HttpStatus.OK);
	}

	@RequestMapping(value = "/login/{email}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@PathVariable("email") String email2, @PathVariable("password") String psd,
			HttpServletRequest request) {
		UserModel user = userModelService.getDataByEmail(email2);
		if (user == null) {
			return new ResponseEntity<String>("You dont have account plesse register first", HttpStatus.OK);
		}
		String token2 = token.genratedToken(user.getId());
		int condition = user.isActive();
		if (condition > 0) {
			System.out.println(token2);
			String url = request.getRequestURI().toString();
			url = url.substring(0, url.lastIndexOf("/")) + "/verify/" + token2;
			email.registration(email2, url, "7024082813");
			userModelService.login(email2, psd);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("active method ", HttpStatus.OK);
	}

	@RequestMapping(value = "/active/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> activeLogin(@PathVariable("token") String jwt) {
		int id = token.varifyToken(jwt);
		UserModel user = userModelService.getDataById(id);
		if (user != null && user.isActive() == 0) {
			userModelService.update(user);
			return new ResponseEntity<String>("sucessful ", HttpStatus.OK);
		}
		return new ResponseEntity<String>("you have already active ", HttpStatus.OK);
	}

	@RequestMapping(value = "/verify/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> varify(@PathVariable("token") String token1) {
		int id = token.varifyToken(token1);
		if (id > 0) {
			return new ResponseEntity<String>("Sucessfull verfy", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not varify ", HttpStatus.OK);

	}

	@RequestMapping(value = "/reset/{email}/{password}/{conform}", method = RequestMethod.PUT)
	public ResponseEntity<String> setPassword(@PathVariable("email") String eamil, @PathVariable("password") String psd,
			@PathVariable("conform") String cpd) {
		UserModel user = userModelService.getDataByEmail(eamil);

		if (user == null) {
			return new ResponseEntity<String>("This email is not avilable data base", HttpStatus.OK);
		}
		if (psd.equals(cpd)) {
			user.setConform_psd(cpd);
			user.setPassword(psd);
			String status = valid.valid(user);
			if (status == null) {
				String codepsd = encode.endode(user);
				user.setPassword(codepsd);
				userModelService.update(user);
				return new ResponseEntity<String>("Sucessfull reset password", HttpStatus.OK);
			}
			return new ResponseEntity<String>("password Length is not valid", HttpStatus.OK);
		}
		return new ResponseEntity<String>("MisMatch conform  and password ", HttpStatus.OK);

	}
}
