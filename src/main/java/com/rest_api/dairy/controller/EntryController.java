package com.rest_api.dairy.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest_api.dairy.entity.Entry;
import com.rest_api.dairy.entity.User;
import com.rest_api.dairy.service.EntryService;

@RestController
@RequestMapping("/entries")
public class EntryController {

	@Autowired
	private EntryService entryService;
	
	@GetMapping("/{id}")
	public Entry getEntry(@PathVariable("id") long id) {
		
		Entry entry = entryService.findById(id);
		
		return entry;
		
	}
	
	
	@GetMapping("/")
	public List<Entry> getEntries(){
		
		List<Entry> entries = entryService.findAll();
		
		return entries;
	}
	
	
	@PostMapping("/")
	public List<Entry> saveAllEntries(@RequestBody List<Entry> entries){
		
		List<Entry> allAntries = entryService.saveEntries(entries);
	
		return allAntries;
	}
	
	
	@PutMapping("/{id}")
	public Entry updateEntry(@PathVariable("id") long id,@RequestBody Entry entry) {
		
		Entry existingEntry = entryService.findById(id);
		
		existingEntry.setDesription(entry.getDesription());
		existingEntry.setEntrydate(entry.getEntrydate());
		existingEntry.setUserid(entry.getUserid());
		
		Entry updatedEntry = entryService.saveEntry(existingEntry);
		
		return updatedEntry;
	}
	
	@PatchMapping("/{id}")
	public Entry patchEntry(@PathVariable("id") long id, @RequestBody Entry entry) {
		
		Entry updatedEntry = entryService.findById(id);
		
		Date entryDate = entry.getEntrydate();
		String description = entry.getDesription();
		User userObject = entry.getUserid();
		
		long userid = userObject.getId();
		
		if(description!=null && description.length()>0) {
			updatedEntry.setDesription(description);
		}
		if(entryDate!=null) {
			updatedEntry.setEntrydate(entryDate);
		}
		if(userid>0) {
			updatedEntry.setUserid(userObject);
		}
		
		Entry entry1 = entryService.saveEntry(updatedEntry);
		
		return entry1;
		
	}
	
	@DeleteMapping("/{id}")
	public String deletEntry(@PathVariable("id") long id) {
		
		Entry existingEntry = entryService.findById(id);
		
		String output =entryService.deleteEntry(existingEntry);
		
		return output;
		
	}
	
	
	
}
