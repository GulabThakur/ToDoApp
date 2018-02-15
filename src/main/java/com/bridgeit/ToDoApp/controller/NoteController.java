package com.bridgeit.ToDoApp.controller;

import java.util.List;

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

import com.bridgeit.ToDoApp.model.Notes;
import com.bridgeit.ToDoApp.model.Response;
import com.bridgeit.ToDoApp.model.UserModel;
import com.bridgeit.ToDoApp.service.INoteService;
import com.bridgeit.ToDoApp.service.IuserService;
import com.bridgeit.ToDoApp.token.IToken;

/**
 * @author ThakurGulab
 */
@RestController
public class NoteController {
	@Autowired
	private IToken token;
	@Autowired
	private IuserService userModelService;
	@Autowired
	private INoteService noteService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> store_note(@RequestBody Notes note, HttpServletRequest request) {
		Response meResponse = new Response();
		System.out.println("come in side node");
		String token = request.getHeader("jwt");
		System.out.println(token);
		boolean status = noteService.create_note(note, token);
		if (status) {
			meResponse.setMessage("succesfull data stored");
			return new ResponseEntity<Response>(meResponse, HttpStatus.OK);
		}
		meResponse.setMessage("data did not store");
		return new ResponseEntity<Response>(meResponse, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> update_note1(@RequestBody Notes note, @PathVariable("id") int id) {
		
		System.out.println("label ................."+note.getLabels());
		Response meResponse = new Response();

		System.out.println("Updating...................." + note.getArchive());
		boolean status = noteService.update_note(id, note);
		if (status) {
			meResponse.setMessage("sucessfully Update");
			return new ResponseEntity<Response>(meResponse, HttpStatus.OK);
		}
		meResponse.setMessage("This record is not avilable");
		return new ResponseEntity<Response>(meResponse, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> delete_note(@PathVariable("id") int id) {
		Response meResponse = new Response();
		boolean status = noteService.delete_note(id);
		if (status) {
			meResponse.setMessage("Delete record sucessful...");
			return new ResponseEntity<Response>(meResponse, HttpStatus.OK);
		}
		meResponse.setMessage("this record is not avilable....");
		return new ResponseEntity<Response>(meResponse, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/record/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Notes> getdata(@PathVariable("id") int id) {
		Notes notes = noteService.get_note(id);
		if (notes != null) {
			return new ResponseEntity<Notes>(notes, HttpStatus.OK);
		}
		return new ResponseEntity<Notes>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/all", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Notes>> getsRecord(HttpServletRequest request) {
		String jwtToken = request.getHeader("jwt");
		int id = token.varifyToken(jwtToken);
		UserModel user = userModelService.getDataById(id);
		System.out.println(user);
		List<Notes> notes = noteService.allNotes(user);
		
		if (notes != null) {
			return new ResponseEntity<List<Notes>>(notes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Notes>>(HttpStatus.BAD_REQUEST);

	}

	// get owner
	// ..................................................................................

	@RequestMapping(value = "/getOwner", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserModel> getOwner(@RequestBody Notes note) {
		UserModel user = userModelService.getDataById((int) note.getUsr_id());
		System.out.println(user.getUserName());
		return new ResponseEntity<UserModel>(user, HttpStatus.ACCEPTED);
	}

	/*
	 * =========================================Collaborator========================
	 */
	@RequestMapping(value = "/notesShare/{email:.+}/{noteId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> notesShare(@PathVariable String email, @PathVariable int noteId,
			HttpServletRequest request) {
		Response message = new Response();
		System.out.println("email " + email);
		System.out.println("email " + noteId);
		int userId = token.varifyToken(request.getHeader("jwt"));
		UserModel user = userModelService.getDataByEmail(email);
		System.out.println("email " + userId);
		System.out.println("email " + user.getId());

		Notes note = noteService.shareNote(email, noteId, user.getId());
		System.out.println("notes" + note);
		if (note != null) {
			return new ResponseEntity<Response>(HttpStatus.ACCEPTED);
		} else {
			message.setMessage("Email id is not exits");
			return new ResponseEntity<Response>(message, HttpStatus.BAD_REQUEST);

		}
	}

	/*
	 * ================================collabUser=============================================
	 */
	@RequestMapping(value = "collabUser")
	public ResponseEntity<Object> collabUser(@RequestBody Notes noteId) {
		System.out.println("noteID value "+noteId.getId());
		Notes notes = noteService.get_note(noteId.getId());
		System.out.println("Total size of user"+notes.getCollaboratorSet().size());
		return new ResponseEntity<Object>(notes.getCollaboratorSet(), HttpStatus.ACCEPTED);
	}
	
	/*
	 * ================================collabUserDelete=============================================
	 */
	@RequestMapping(value = "/deleteUser/{email:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> collabDeleteUser(@PathVariable String email,@RequestBody Notes noteId){
		Response message=new Response();
		boolean status=noteService.removeShareUser(noteId.getId(),email);
		
			message.setMessage("Delete SucessFull");
	
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
			
	}
	
	/*
	 * ================================LsbelsAdd=============================================
	 */
	
	@RequestMapping(value="/setLabels/{noteId}/{lableId}" , method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> setLabels(@PathVariable("lableId") int lableId,@PathVariable("noteId") int noteId ){
		Response message=new Response();
		noteService.addLavel(noteId,lableId);
		message.setMessage("lavel added");
		return new ResponseEntity<Response>(message,HttpStatus.ACCEPTED);
		
		
	} 
	
	/*
	 * ================================LableDelete=============================================
	 */
	
	@RequestMapping(value="/lavelDelete/{noteId}/{lableId}",method=RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteLables(@PathVariable("noteId") int noteId,@PathVariable("lableId") int lableId){
		System.out.println("note id ....................:"+noteId);
		System.out.println("Lables ID...................:"+lableId);
		Response message=new Response();
		noteService.deleteLable(noteId,lableId);
		message.setMessage("DeleteLabel");
		return new ResponseEntity<Response>(HttpStatus.ACCEPTED);
	}
}
