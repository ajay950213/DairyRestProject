package com.rest_api.dairy.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("/entries")
public class EntryController {

	@Autowired
	private EntryService entryService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getEntry(@PathVariable("userId") Long userId) {
	    try {
	        if (userId == null || userId <= 0) {
	            throw new IllegalArgumentException("Invalid userId provided");
	        }
	            Optional<Entry> entry = entryService.findById(userId);

	            if (entry == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
	            }

	            return ResponseEntity.status(HttpStatus.OK).body(entry);
	        }
	   
	    catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	    } 
	    catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}

	
	@GetMapping("/")
	public ResponseEntity<?> getEntries(){
		
		try {
			List<Entry> entries = entryService.findAll();
			
			if(entries.size()<=0 || entries.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(entries);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	
	@PostMapping("/")
	public ResponseEntity<?> saveAllEntries(@RequestBody List<Entry> entries){
		
		try {
			List<Entry> allEntries = entryService.saveEntries(entries);
			
			if(allEntries.size()<=0 || entries == null || entries.isEmpty()) {
				
				ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Entries found");
			}
			return new ResponseEntity<>(allEntries, HttpStatus.CREATED);
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEntry(@PathVariable("id") long id,@RequestBody Entry entry) {
		
		try {
				if (id <= 0) {
					ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id+" Invalid id, it should be positive only");
		        }
				
				Entry existingEntry = entryService.findById(id).get();
				
				if(existingEntry == null || entry == null) {
					ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry with "+id+" not found");
				}
				
				existingEntry.setDesription(entry.getDesription());
				existingEntry.setEntrydate(entry.getEntrydate());
				existingEntry.setUserid(entry.getUserid());
				
				Entry updatedEntry = entryService.saveEntry(existingEntry);
			
				return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
			
			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> patchEntry(@PathVariable("id") long id, @RequestBody Entry entry) {
		
		try {
			if(id<0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(id+" Invalid id, it should be positive only");
			}
			
				
				Entry updatedEntry = entryService.findById(id).get();
				
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
				return ResponseEntity.status(HttpStatus.OK).body(entry1.getId()+" Updated succesfully");
			
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			
		}

	}
		
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEntry(@PathVariable("id") long id) {
	    try {
	        if (id <= 0) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Id " + id);
	        }
	        
	        Optional<Entry> entryOptional = entryService.findById(id);

	        if (entryOptional.isPresent()) {
	            Entry existingEntry = entryOptional.get();
	            String output = entryService.deleteEntry(existingEntry);
	            
	            return ResponseEntity.status(HttpStatus.OK).body(output);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}




	@GetMapping("/userid/{id}")
	public ResponseEntity<?> getAllEntriesByUserId(@PathVariable("id") long id){
		
		try{
			List<Entry> entries = entryService.getAllEntriesByUserId(id);
		
			if(entries.isEmpty() || entries.size()<=0 || entries == null) {
				
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No data found");
					
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(entries);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
			
	}
	
	
	@GetMapping("/allEntryIds")
	public ResponseEntity<?> getAllEntryIds(){
		try {
			List<Long> entries = entryService.findAllEntryIds();
			return ResponseEntity.status(HttpStatus.OK).body(entries);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		
	}
	
}
