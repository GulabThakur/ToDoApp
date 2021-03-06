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
	private EmailProperties emailService;
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

	/*=================================REGISTRATION-API===============================================*/

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> register(@RequestBody UserModel user, HttpServletRequest request)
			throws JMSException {

		Response message = new Response();
		String status = valid.valid(user);
		UserModel user1 = userModelService.getDataByEmail(user.getEmail());
		if (user1 == null) {
			String url = request.getRequestURL().toString();
			if (status == null) {
				String endodePsd = encode.endode(user.getPassword());
				user.setActive(1);
				user.setPassword(endodePsd);
				int id = userModelService.registration(user);
				String token1 = token.genratedToken(id);
				url = url.substring(0, url.lastIndexOf("/")) + "/active/" + token1;
				System.out.println(url);
				EmailSet data = new EmailSet();
				data.setEmail(user.getEmail());
				data.setToken(url);
				// This use for for jms technology.....
				/* messageProducer.send(data); */
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

	/*===========================================LOGIN-API============================================*/

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> login(@RequestBody UserModel user, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Response resp = new Response();
		System.out.println("value ...." + user.getEmail());
		UserModel user1 = userModelService.getDataByEmail(user.getEmail());
		if (user1 == null) {
			resp.setMessage("You dont have account plesse register first");
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		}
		String token2 = token.genratedToken(user1.getId());
		int condition = user1.isActive();
		if (condition > 0) {
			System.out.println(token2);
			String url = request.getRequestURI().toString();
			// url = "http://localhost:8080/" + url.substring(0, url.lastIndexOf("/")) +
			// "/verify/" + token2;
			// email.registration(user.getEmail(), url);
			boolean status = userModelService.login(user.getEmail(), user.getPassword());
			if (status) {
				resp.setToken(token2);
				return new ResponseEntity<Response>(resp, HttpStatus.ACCEPTED);
			}
			// calling home page.......
			// response.sendRedirect("http://localhost:8080/ToDoApp/#!/homepage");
			resp.setMessage("wrong password");
			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		}
		resp.setMessage("please acivate your ragistration chech mail");
		return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
	}

	/*========================================================ACTIVE-API=======================*/

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

	/*================================================VERIFICATION-API===============================*/
	@RequestMapping(value = "/verify/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<Response> varify(@PathVariable("token") String token1, HttpServletResponse response)
			throws IOException {
		Response message = new Response();
		int id = token.varifyToken(token1);
		if (id > 0) {
			message.setMessage("verification is sucessfully....");
			response.sendRedirect("https://note-keep.herokuapp.com/#!/homepage");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
		message.setMessage("sorry registration first...");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}

	/*================================================VERIYFY-API========================================= */
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
			response.sendRedirect("https://note-keep.herokuapp.com/#!/resetpassword");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
		message.setMessage("this is not valid....");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}

	/*======================================REST-API========================================== */
	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	public ResponseEntity<Response> setPassword(@RequestBody UserModel user, HttpSession session) {
		Response message = new Response();

		System.out.println("fhgfghhg");

		int id = (Integer) session.getAttribute("id");

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
		message.setMessage("password and conform password are not match");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}

	/*
	 * ====================================FORGOT-API===============================================================
	 */

	@RequestMapping(value = "/test/forgot", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotpassword(@RequestBody UserModel user, HttpServletRequest request) {
		Response message = new Response();
		UserModel user_arg = userModelService.getDataByEmail(user.getEmail());
		System.out.println(user.getEmail());
		if (user_arg != null) {
			String url = request.getRequestURI().toString();
			int id = user_arg.getId();
			String token2 = token.genratedToken(id);
			url = "https://note-keep.herokuapp.com/" + url.substring(0, url.lastIndexOf("/")) + "/verifyReset/"
					+ token2;
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
			url = "https://note-keep.herokuapp.com/" + url.substring(0, url.lastIndexOf("/")) + "/verifyReset/"
					+ token2;
			email.registration(user_arg.getEmail(), url);
			message.setMessage("check your email....");
			return new ResponseEntity<Response>(message, HttpStatus.ACCEPTED);
		}
		message.setMessage("please enter the valid user");
		return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

	}

	//===================================this API use for get user detail======================================
	 

	@RequestMapping(value = "/userData", method = RequestMethod.POST)
	public ResponseEntity<UserModel> userProfile(HttpServletRequest request) {
		String jwtToken = request.getHeader("jwt");
		int id = token.varifyToken(jwtToken);
		if (id > 0) {
			UserModel newUser = userModelService.getDataById(id);
			return new ResponseEntity<UserModel>(newUser, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<UserModel>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/profilChage", method = RequestMethod.POST)
	public ResponseEntity<Response> updateProfile(@RequestBody UserModel user, HttpServletRequest request) {
		String jwtToken = request.getHeader("jwt");
		int userId = token.varifyToken(jwtToken);
		System.out.println("token id :" + userId);
		if (userId > 0) {
			UserModel userUpdate = userModelService.getDataById(userId);
			userUpdate.setProFile(user.getProFile());
			userModelService.update(userUpdate);
			return new ResponseEntity<Response>(HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
		}

	}

}
