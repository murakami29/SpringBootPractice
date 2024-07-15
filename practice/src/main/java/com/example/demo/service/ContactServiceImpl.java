package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepository contactRepository;
	
	@Override
	public void saveContact(ContactForm contactForm) {

        Contact contact = new Contact();

        contact.setLastName(contactForm.getLastName());
        contact.setFirstName(contactForm.getFirstName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhone(contactForm.getPhone());
        contact.setZipCode(contactForm.getZipCode());
        contact.setAddress(contactForm.getAddress());
        contact.setBuildingName(contactForm.getBuildingName());
        contact.setContactType(contactForm.getContactType());
        contact.setBody(contactForm.getBody());

        contactRepository.save(contact);
	}
	
	@Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
	
	@Override
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }
	
	@Override
    public void updateContact(Contact contact) {
        Contact existingContact = contactRepository.findById(contact.getId()).orElse(null);
        if (existingContact != null) {
            existingContact.setLastName(contact.getLastName());
            existingContact.setFirstName(contact.getFirstName());
            existingContact.setEmail(contact.getEmail());
            existingContact.setPhone(contact.getPhone());
            existingContact.setZipCode(contact.getZipCode());
            existingContact.setAddress(contact.getAddress());
            existingContact.setBuildingName(contact.getBuildingName());
            existingContact.setContactType(contact.getContactType());
            existingContact.setBody(contact.getBody());
            contactRepository.save(existingContact);
        }
    }
	
	@Override
    public void deleteContactById(Long id) {
		contactRepository.deleteById(id);
	}
}
