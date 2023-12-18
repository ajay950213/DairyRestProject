package com.rest_api.dairy.service;

import java.util.List;

import com.rest_api.dairy.entity.Entry;
import com.rest_api.dairy.entity.User;

public interface EntryService {

	public Entry saveEntry(Entry entry);
	public List<Entry> saveEntries(List<Entry> entry);
	public Entry updateEntry(Entry entry);
	public String deleteEntry(Entry entry);
	public Entry findById(long id);
	public List<Entry> findAll();

	
}
