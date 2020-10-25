package ch.fhnw.webec.contactlistrest.dao;

import ch.fhnw.webec.contactlistrest.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByLastNameContainingOrFirstNameContainingAllIgnoreCase(String lastName, String firstName);

}
