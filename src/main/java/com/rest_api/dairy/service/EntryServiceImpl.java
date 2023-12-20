package com.rest_api.dairy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest_api.dairy.entity.Entry;
import com.rest_api.dairy.repository.EntryRepository;

@Service
public class EntryServiceImpl implements EntryService {

	@Autowired
	private EntryRepository entryRepository;
	
	@Override
	public Entry saveEntry(Entry entry) {
		return entryRepository.save(entry);
	}
	
	

	public List<Entry> saveEntries(List<Entry> entries){
		
		List<Entry> entries1 = entryRepository.saveAll(entries);
		
		return entries1;
	}

	@Override
	public Entry updateEntry(Entry entry) {
		return entryRepository.save(entry);
	}

	@Override
	public String deleteEntry(Entry entry) {
		
		entryRepository.delete(entry);
		
		return "Entry Deleted i.e "+entry;
	}
	
	
	@Override
	public Entry findById(long id) {
		
		Entry entry = entryRepository.findById(id).get();
		
		return entry;
	}

	@Override
	public List<Entry> findAll() {
		return entryRepository.findAll();
	}
	
	@Override
	public List<Entry> getAllEntriesByUserId(long userId){
		return entryRepository.getEntriesByUserId(userId);
	}


	@Override
	public List<Long> findAllEntryIds() {
		return entryRepository.findAllEntryIds();
	}
	
}
