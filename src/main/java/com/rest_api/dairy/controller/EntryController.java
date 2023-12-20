package com.rest_api.dairy.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/entries")
public class EntryController {

	@Autowired
	private EntryService entryService;

	private List<Long> entryIdList;

	@PostConstruct
    private void initializeEntryIdList() {
        this.entryIdList = entryService.findAllEntryIds();
    }
	
	@GetMapping("/{userId}")
	public ResponseEntity<Entry> getEntry(@PathVariable("userId") Long userId) {
	    try {
	        if (userId == null || userId <= 0) {
	            throw new IllegalArgumentException("Invalid userId provided");
	        }

	        if (this.entryIdList.contains(userId)) {
	            Entry entry = entryService.findById(userId);

	            if (entry == null) {
	                return ResponseEntity.notFound().build();
	            }

	            return ResponseEntity.ok(entry);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } 
	    catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().build();
	    } 
	    catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@GetMapping("/")
	public ResponseEntity<List<Entry>> getEntries(){
		
		try {
			List<Entry> entries = entryService.findAll();
			
			if(entries.size()<=0 || entries.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
			return ResponseEntity.ok(entries);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@PostMapping("/")
	public ResponseEntity<List<Entry>> saveAllEntries(@RequestBody List<Entry> entries){
		
		try {
			List<Entry> allEntries = entryService.saveEntries(entries);
			
			if(allEntries.size()<=0 || entries == null || entries.isEmpty()) {
				
				ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return new ResponseEntity<>(allEntries, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Entry> updateEntry(@PathVariable("id") long id,@RequestBody Entry entry) {
		
		try {
			
			if(this.entryIdList.contains(id)) {
				Entry existingEntry = entryService.findById(id);
				
				if(existingEntry == null || entry == null) {
					ResponseEntity.status(HttpStatus.NOT_FOUND).build();
				}
				
				existingEntry.setDesription(entry.getDesription());
				existingEntry.setEntrydate(entry.getEntrydate());
				existingEntry.setUserid(entry.getUserid());
				
				Entry updatedEntry = entryService.saveEntry(existingEntry);
			
				return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
			}
			else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<Entry> patchEntry(@PathVariable("id") long id, @RequestBody Entry entry) {
		
		try {
			if(this.entryIdList.contains(id)) {
				
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
				return ResponseEntity.status(HttpStatus.OK).body(entry1);
			
			}
			else {
				return ResponseEntity.notFound().build();
			}

		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		
		}

	}
		
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEntry(@PathVariable("id") long id) {
		
		try {
			
			Entry existingEntry = entryService.findById(id);
		    
		    if (existingEntry != null) {
		        String output = entryService.deleteEntry(existingEntry);
		        
		        return ResponseEntity.status(HttpStatus.OK).body(output);
		    }
		    else{	
		    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
		    }
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
	
	@GetMapping("/userid/{id}")
	public ResponseEntity<List<Entry>> getAllEntriesByUserId(@PathVariable("id") long id){
		
		try{
			List<Entry> entries = entryService.getAllEntriesByUserId(id);
		
			if(entries.isEmpty() || entries.size()<=0 || entries == null) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
						
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(entries);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
			
	}
	
	
	@GetMapping("/allEntryIds")
	public ResponseEntity<List<Long>> getAlEntryIds(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(this.entryIdList);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	
}
