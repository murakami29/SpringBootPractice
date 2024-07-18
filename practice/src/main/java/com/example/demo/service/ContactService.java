package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.form.ContactUpdateForm;

public interface ContactService {

	void saveContact(ContactForm contactForm);
	List<Contact> getAllContacts();
	Optional<Contact> getContactById(Long id);
	void updateContact(ContactUpdateForm contactUpdateForm);
	void deleteContactById(Long id);
}
