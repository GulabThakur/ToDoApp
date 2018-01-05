package com.bridgeit.ToDoApp.controller;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bridgeit.ToDoApp.model.Labels;
import com.bridgeit.ToDoApp.model.Response;
import com.bridgeit.ToDoApp.service.ILabelsService;
/**
 * @author ThakurGulab 
 *
 */
public class LabelsController {
	@Autowired 
	ILabelsService lavelservice;
	
	String jwtToken;
	
	/*======================================================================================================*/
	
	@RequestMapping(value="/createLabel",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> createLable(HttpServletRequest request,@RequestBody Labels label){
		Response message=new Response();
		jwtToken= (String) request.getAttribute("jwt");
		lavelservice.createService(label, jwtToken);
		message.setMessage("Labals Create");
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
	}
	
	/*======================================================================================================*/
	
	@RequestMapping(value="/deleteLable",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Response> deleteLabals(HttpServletRequest request,@RequestBody Labels labels){
		Response message=new Response();
		/*jwtToken=(String) request.getAttribute("jwt");*/
		lavelservice.deleteService(labels);
		message.setMessage("Delete lables sucessfully..");
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
	}
	
	/*======================================================================================================*/
	
	@RequestMapping(value="/fetchLabls", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> fetchLabels(HttpServletRequest request){
		Response message=new Response();
		jwtToken=(String) request.getAttribute("jwt");
		lavelservice.getLabelsService(jwtToken);
		message.setMessage("Frtch lables ...");
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
	}
	
	/*======================================================================================================*/
	
	@RequestMapping(value="updateLables", method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateLabes(@RequestBody Labels lables,HttpServletRequest request){
		Response message=new Response();
		jwtToken=(String) request.getAttribute("jwt");
		lavelservice.updateService(lables, jwtToken);
		message.setMessage("Update sucessfully.....");
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
		
	}
	
	/*======================================================================================================*/
}
