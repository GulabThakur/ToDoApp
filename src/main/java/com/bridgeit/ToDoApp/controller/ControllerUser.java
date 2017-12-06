package com.bridgeit.ToDoApp.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.ToDoApp.email.IEmail;
import com.bridgeit.ToDoApp.jms.MessageProducer;
import com.bridgeit.ToDoApp.model.EmailSet;
import com.bridgeit.ToDoApp.model.Response;
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
	@Autowired(required = true)
	private MessageProducer messageProducer;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> registere(@RequestBody UserModel user, HttpServletRequest request)
			throws JMSException {
		Response message = new Response();
		String status = valid.valid(user);
		UserModel user1 = userModelService.getDataByEmail(user.getEmail());
		if (user1 == null) {
			String url = request.getRequestURL().toString();
			System.out.println(url);
			if (status == null) {
				String endodePsd = encode.endode(user);
				user.setActive(0);
				user.setPassword(endodePsd);
				int id = userModelService.registration(user);
				String token1 = token.genratedToken(id);
				url = url.substring(0, url.lastIndexOf("/")) + "/active/" + token1;
				System.out.println(url);
				EmailSet data = new EmailSet();
				data.setEmail(user.getEmail());
				data.setToken(url);
				// This use for for jms technology.....
				messageProducer.send(data);
				// if you want to email process in your project remove in blow line then use
				// email process
				// email.registration(user.getEmail(), url);
				message.setMessage("sucessfull ragister");
				return new ResponseEntity<Response>(message, HttpStatus.OK);
			}
			message.setMessage(status);
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		message.setMessage("already register ");
		return new ResponseEntity<Response>(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/login/{email}/{password}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> login(@PathVariable("email") String email2, @PathVariable("password") String psd,
			HttpServletRequest request) {
		Response message = new Response();
		UserModel user = userModelService.getDataByEmail(email2);
		if (user == null) {
			message.setMessage("You dont have account plesse register first");
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		String token2 = token.genratedToken(user.getId());
		int condition = user.isActive();
		if (condition > 0) {
			System.out.println(token2);
			String url = request.getRequestURI().toString();
			url = url.substring(0, url.lastIndexOf("/")) + "/verify/" + token2;
			email.registration(email2, url);
			userModelService.login(email2, psd);
			message.setMessage("Welcome ..");
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		message.setMessage("please acivate your ragistration chech mail");
		return new ResponseEntity<Response>(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/active/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> activeLogin(@PathVariable("token") String jwt) {
		Response message = new Response();
		int id = token.varifyToken(jwt);
		UserModel user = userModelService.getDataById(id);
		if (user != null && user.isActive() == 0) {
			userModelService.update(user);
			message.setMessage("Your registration activate ...");
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		message.setMessage("Please ragistration first then activate ");
		return new ResponseEntity<Response>(message, HttpStatus.OK);
	}

	@RequestMapping(value = "/verify/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> varify(@PathVariable("token") String token1) {
		Response message = new Response();
		int id = token.varifyToken(token1);
		if (id > 0) {
			message.setMessage("verification is sucessfully....");
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		message.setMessage("sorry registration first...");
		return new ResponseEntity<Response>(message, HttpStatus.OK);

	}

	@RequestMapping(value = "/reset/{token:.+}", method = RequestMethod.PUT)
	public ResponseEntity<Response> setPassword(@RequestBody UserModel user, @PathVariable("token") String jwt) {
		Response message = new Response();
		int id = token.varifyToken(jwt);
		System.out.println(id);
		UserModel user1 = userModelService.getDataById(id);
		if (user1 == null) {
			message.setMessage("This is incorect email ...");
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		if (user.getPassword().equals(user.getConform_psd())) {
			user1.setConform_psd(user.getPassword());
			user1.setPassword(user.getConform_psd());
			String status = valid.valid(user1);
			if (status == null) {
				String codepsd = encode.endode(user1);
				user1.setPassword(codepsd);
				userModelService.update(user1);
				message.setMessage("Your password is change..");
				return new ResponseEntity<Response>(message, HttpStatus.OK);
			}
			message.setMessage("please enter the valid password....");
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		message.setMessage("password and conform password are not match");
		return new ResponseEntity<Response>(message, HttpStatus.OK);

	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotpassword(@RequestBody UserModel user, HttpServletRequest request) {
		Response message = new Response();
		UserModel user_arg = userModelService.getDataByEmail(user.getEmail());
		if (user_arg != null) {
			String url = request.getRequestURI().toString();
			int id = user_arg.getId();
			String token2 = token.genratedToken(id);
			url = url.substring(0, url.lastIndexOf("/")) + "/reset/" + token2;
			email.registration(user.getEmail(), url);
			message.setMessage("your password changed....");
			return new ResponseEntity<Response>(message, HttpStatus.OK);
		}
		message.setMessage("please enter the valid user");
		return new ResponseEntity<Response>(message, HttpStatus.OK);

	}
}
