package com.bridgeit.ToDoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.service.IuserService;

/**
 * @author ThakurGulab
 *
 */
@RestController
public class ControllerUser {
   @Autowired
   private IuserService userModelService;
   @RequestMapping(value="/register" ,method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<String> registere(@RequestBody UserModel user)
   {
	   userModelService.registration(user);
	   
	return new ResponseEntity<String>("sucessfull ragister",HttpStatus.OK);
	   
   }
}
