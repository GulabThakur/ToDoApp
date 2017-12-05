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
import com.bridgeit.ToDoApp.service.INoteService;

/**
 * @author ThakurGulab
 */
@RestController
public class NoteController {
	@Autowired
	private INoteService noteService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> store_note(@RequestBody Notes note, HttpServletRequest request) {
		String token = request.getHeader("jwt");
		boolean status = noteService.create_note(note, token);
		if (status) {
			return new ResponseEntity<String>("succesfull data stored", HttpStatus.OK);
		}
		return new ResponseEntity<String>("data did not store", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update_note1(@RequestBody Notes note, @PathVariable("id") int id) {
		boolean status = noteService.update_note(id, note);
		if (status) {
			return new ResponseEntity<String>("sucessUpdate", HttpStatus.OK);
		}
		return new ResponseEntity<String>("this record not upadte ", HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete_note(@PathVariable("id") int id) {
		boolean status = noteService.delete_note(id);
		if (status) {
			return new ResponseEntity<String>(" delete record", HttpStatus.OK);
		}
		return new ResponseEntity<String>("delete process is not done", HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/record/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Notes> getdata(@PathVariable("id") int id) {
		Notes notes = noteService.get_note(id);
		if (notes != null) {
			return new ResponseEntity<Notes>(notes, HttpStatus.OK);
		}
		return new ResponseEntity<Notes>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Notes>> getsRecord() {
		List<Notes> notes = noteService.allNotes();
		if (notes != null) {
			return new ResponseEntity<List<Notes>>(notes, HttpStatus.OK);
		}
		return new ResponseEntity<List<Notes>>(HttpStatus.BAD_REQUEST);

	}
}
