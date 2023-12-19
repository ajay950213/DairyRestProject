package com.rest_api.dairy.service;

import java.util.List;
import java.util.Optional;

import com.rest_api.dairy.entity.Entry;

public interface EntryService {
	
	public Entry saveEntry(Entry existingEntry);
	public List<Entry> saveEntries(List<Entry> entry);
	public Entry updateEntry(Entry entry);
	public String deleteEntry(Entry entry);
	public Entry findById(long id);
	public List<Entry> findAll();
	public List<Entry> getAllEntriesByUserId(long userId);
	public List<Long> findAllEntryIds();
	
}
