package ch.fhnw.webec.contactlistrest;

import ch.fhnw.webec.contactlistrest.model.Contact;
import ch.fhnw.webec.contactlistrest.model.Phone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ContactRestControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnAllContacts() {
        // when
        final ResponseEntity<List> result = restTemplate.getForEntity("/api/contacts", List.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(30);
    }

    @Test
    void shouldReturnSingleContactById() {
        // given
        final Contact expected = new Contact();
        expected.setId(1L);
        expected.setFirstName("Mabel");
        expected.setLastName("Guppy");
        final Phone phone = new Phone("+41", "405", "5806403");
        phone.setId(2L);
        expected.getPhones().add(phone);
        expected.setJobTitle("Librarian");
        expected.setCompany("Photolist");

        // when
        final ResponseEntity<Contact> result = restTemplate.getForEntity("/api/contacts/1", Contact.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturnContactsFilteredByName() {
        // when
        final ResponseEntity<List> result = restTemplate.getForEntity("/api/contacts?name=ma", List.class);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(3);

    }

}
