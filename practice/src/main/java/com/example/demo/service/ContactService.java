package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;

public interface ContactService {

	void saveContact(ContactForm contactForm);
	List<Contact> getAllContacts();
	Optional<Contact> getContactById(Long id);
	void updateContact(Contact contact);
	void deleteContactById(Long id);
}
