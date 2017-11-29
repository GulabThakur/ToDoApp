package com.bridgeit.ToDoApp.controller;

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

	/**
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> store_note(@RequestBody Notes note) {
		long currentTime = System.currentTimeMillis();
		note.setCurrenTime(currentTime);
		note.setUpdateTime(currentTime);
		boolean status = noteService.create_note(note);
		if (status) {
			return new ResponseEntity<String>("succesfull data stored", HttpStatus.OK);
		}
		return new ResponseEntity<String>("data did not store", HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update_note1(@RequestBody Notes note, @PathVariable("id") int id) {
		long update_time = System.currentTimeMillis();
		note.setUpdateTime(update_time);
		//boolean status = noteService.update_note(id, note);
		if (true) {
			return new ResponseEntity<String>("sucessUpdate", HttpStatus.OK);
		}
		return new ResponseEntity<String>("this record not upadte ", HttpStatus.BAD_REQUEST);

	}
}
