package ch.fhnw.webec.contactlistrest.rest;

import ch.fhnw.webec.contactlistrest.model.Contact;
import ch.fhnw.webec.contactlistrest.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/contacts")
public class ContactRestController {

    private final ContactService contactService;

    @Autowired
    public ContactRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping             // http://localhost:8080/api/contacts
    public List<Contact> getAll() {
        return contactService.getAllContacts();
    }

    @GetMapping("{id}")    // http://localhost:8080/api/contacts/{id}
    public ResponseEntity<Contact> getContactById(@PathVariable long id) {
        final Optional<Contact> contact = contactService.findContact(id);
        return contact.isPresent()?
                ResponseEntity.ok(contact.get()) : ResponseEntity.notFound().build();
    }
}
