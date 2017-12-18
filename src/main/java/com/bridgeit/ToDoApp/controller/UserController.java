package com.bridgeit.ToDoApp.controller;

import java.io.IOException;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.ToDoApp.email.EmailProperties;
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
public class UserController {
	
	@Autowired
	EmailProperties emailService;
	
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

	/*
	 * ................this my register API........
	 */

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> registere(@RequestBody UserModel user, HttpServletRequest request)
			throws JMSException {
		Response message = new Response();
		String status = valid.valid(user);
		UserModel user1 = userModelService.getDataByEmail(user.getEmail());
		if (user1 == null) {
			String url = request.getRequestURL().toString();
			//System.out.println(url);
			if (status == null) {
				String endodePsd = encode.endode(user.getPassword());
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
				return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
			}
			message.setMessage(status);
			return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
		}
		message.setMessage("already register ");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
	}

	/*
	 * ................this my login API........
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> login(@RequestBody UserModel user, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Response message = new Response();
		System.out.println("value ...." + user.getEmail());
		UserModel user1 = userModelService.getDataByEmail(user.getEmail());
		if (user1 == null) {
			message.setMessage("You dont have account plesse register first");
			return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
		}
		String token2 = token.genratedToken(user1.getId());
		int condition = user1.isActive();
		if (condition > 0) {
			System.out.println(token2);
			String url = request.getRequestURI().toString();
			//url = "http://localhost:8080/" + url.substring(0, url.lastIndexOf("/")) + "/verify/" + token2;
			//email.registration(user.getEmail(), url);
			boolean status=userModelService.login(user.getEmail(), user.getPassword());
			if(status) 
			{
				message.setMessage(token2);
				return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
			}
			// calling home page.......
			//response.sendRedirect("http://localhost:8080/ToDoApp/#!/homepage");
			message.setMessage("wrong password");
			return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
		}
		message.setMessage("please acivate your ragistration chech mail");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
	}

	/*
	 * ................this my activate API........
	 */

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
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
	}

	/*
	 * ................this my verification API........
	 */
	@RequestMapping(value = "/verify/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> varify(@PathVariable("token") String token1,HttpServletResponse response) throws IOException {
		Response message = new Response();
		int id = token.varifyToken(token1);
		if (id > 0) {
			message.setMessage("verification is sucessfully....");
			response.sendRedirect("http://localhost:8080/ToDoApp/#!/homepage");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
		message.setMessage("sorry registration first...");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}

	/*
	 * ................this my verification for reset API........
	 */
	@RequestMapping(value = "/verifyReset/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> varifyReset(@PathVariable("token") String token1, HttpServletResponse response,
			HttpSession session) throws IOException {
		Response message = new Response();
		int id = token.varifyToken(token1);
		session.setAttribute("id", id);
		System.out.println("This token id is :" + id);
		if (id > 0) {
			message.setMessage("done verfy");
			// here send user inside the reset password page...

			response.sendRedirect("http://localhost:8080/ToDoApp/#!/resetpassword");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
		message.setMessage("this is not valid....");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}

	/*
	 * ................this my reset API........
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	public ResponseEntity<Response> setPassword(@RequestBody UserModel user, HttpSession session) {
		Response message = new Response();

		System.out.println("fhgfghhg");
		
		int id = (int) session.getAttribute("id");

		// here i am using get the data from data base by id....

		UserModel user1 = userModelService.getDataById(id);

		// then check condition user is null so return bad request...
		if (user1 == null) {
			message.setMessage("This is incorect email ...");
			return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
		}
		// again i am checking here that data password and conform password will be
		// match or not match

		if (user.getPassword().equals(user.getConform_psd())) {
			System.out.println("password  corrrect");
			user1.setConform_psd(user.getPassword());
			user1.setPassword(user.getConform_psd());

			// this line will be use for check password is follow instruction ..

			String status = valid.valid(user1);
			// when status is null then enter the inside
			if (status == null) {
				String codepsd = encode.endode(user1.getPassword());
				user1.setPassword(codepsd);
				userModelService.update(user1);
				message.setMessage("Your password is change..");

				return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
			}
			message.setMessage("please enter the valid password....");
			return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);
		}
		System.out.println("password incorrrect");
		message.setMessage("password and conform password are not match");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}

	/*
	 * ................this my forgot API........
	 */

	@RequestMapping(value = "/test/forgot", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotpassword(@RequestBody UserModel user, HttpServletRequest request) {
		Response message = new Response();
		UserModel user_arg = userModelService.getDataByEmail(user.getEmail());

		if (user_arg != null) {
			String url = request.getRequestURI().toString();
			int id = user_arg.getId();
			String token2 = token.genratedToken(id);
			url = "http://localhost:8080/" + url.substring(0, url.lastIndexOf("/")) + "/verifyReset/" + token2;
			email.registration(user.getEmail(), url);
			message.setMessage("check your email....");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
		message.setMessage("please enter the valid user");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public ResponseEntity<Response> test(@RequestBody UserModel user, HttpServletRequest request) {
		Response message = new Response();
		UserModel user_arg = userModelService.getDataByEmail(user.getEmail());

		if (user_arg != null) {
			String url = request.getRequestURI().toString();
			int id = user_arg.getId();
			String token2 = token.genratedToken(id);
			url = "http://localhost:8080/" + url.substring(0, url.lastIndexOf("/")) + "/verifyReset/" + token2;
			email.registration(emailService.getEmail(), url);
			message.setMessage("check your email....");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
		message.setMessage("please enter the valid user");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}
}
