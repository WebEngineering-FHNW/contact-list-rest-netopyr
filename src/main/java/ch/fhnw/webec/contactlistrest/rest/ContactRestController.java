package ch.fhnw.webec.contactlistrest.rest;

import ch.fhnw.webec.contactlistrest.model.Contact;
import ch.fhnw.webec.contactlistrest.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(params = "name")
    public List<Contact> getAll(@RequestParam String name) {
        return contactService.findByName(name);
    }

    @GetMapping("{id}")    // http://localhost:8080/api/contacts/{id}
    public ResponseEntity<Contact> getContactById(@PathVariable long id) {
        final Optional<Contact> contact = contactService.findContact(id);
        return contact.isPresent()?
                ResponseEntity.ok(contact.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        contactService.createContact(contact);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactService.createContact(contact));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateContact(@PathVariable long id, @RequestBody Contact contact) {
        contact.setId(id);
        try {
            contactService.updateContact(contact);
            return ResponseEntity.noContent().build();
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable long id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.ok().build();
        } catch (IndexOutOfBoundsException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
