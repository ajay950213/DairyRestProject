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
		return entryRepository.findById(id).get();
	}

	@Override
	public List<Entry> findAll() {
		return entryRepository.findAll();
	}


	
}
