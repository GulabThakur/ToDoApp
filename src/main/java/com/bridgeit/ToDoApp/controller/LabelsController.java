package com.bridgeit.ToDoApp.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.ToDoApp.model.Labels;
import com.bridgeit.ToDoApp.model.Response;
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.service.ILabelsService;
import com.bridgeit.ToDoApp.service.IuserService;
/**
 * @author ThakurGulab 
 *
 */
import com.bridgeit.ToDoApp.token.IToken;
@RestController
public class LabelsController {
	@Autowired 
	ILabelsService lavelservice;
	@Autowired
	IToken token;
	@Autowired
	IuserService userModelService;
	
	
	/*======================================================================================================*/
	
	@RequestMapping(value="/createLabel",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> createLable(HttpServletRequest request,@RequestBody Labels label){
		Response message=new Response();
		String jwtToken= (String) request.getHeader("jwt");
		System.out.println(jwtToken);
		int id=token.varifyToken(jwtToken);
		if(id>0) {
			UserModel userId=userModelService.getDataById(id);
			label.setUserId(userId);
			lavelservice.createService(label, jwtToken);
			message.setMessage("Labals Create");
			return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
		}
		message.setMessage("Fall.........");
		return new ResponseEntity<Response>(message,HttpStatus.BAD_REQUEST);
		
	}
	
	/*======================================================================================================*/
	
	@RequestMapping(value="/deleteLable",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Response> deleteLabals(HttpServletRequest request,@RequestBody Labels labels){
		Response message=new Response();
		System.out.println("welcome To delete lavelllllllll"+labels.getLabelsName());
		String jwtToken=(String) request.getHeader("jwt");
		lavelservice.deleteService(labels);
		message.setMessage("Delete lables sucessfully..");
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
	}
	
	/*======================================================================================================*/
	
	@RequestMapping(value="/fetchLabls", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Labels>> fetchLabels(HttpServletRequest request){
		Response message=new Response();
		String jwtToken=(String) request.getHeader("jwt");
		List<Labels> lavel=lavelservice.getLabelsService(jwtToken);
		message.setMessage("Fetch lables ...");
		return new ResponseEntity<List<Labels>>(lavel,HttpStatus.ACCEPTED);
	}
	
	/*======================================================================================================*/
	
	@RequestMapping(value="updateLables", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateLabes(@RequestBody Labels lables,HttpServletRequest request){
		/*System.out.println(lables.getLabelsName());*/
		Response message=new Response();
		String jwtToken=(String) request.getHeader("jwt");
		/*System.out.println(jwtToken);*/
		lavelservice.updateService(lables, jwtToken);
		message.setMessage("Update sucessfully.....");
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
		
	}
	
	/*======================================================================================================*/
}
