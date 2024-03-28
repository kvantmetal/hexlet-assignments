package exercise.controller;

import exercise.dto.ContactCreateDTO;
import exercise.dto.ContactDTO;
import exercise.model.Contact;
import exercise.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @PostMapping("")
    private ResponseEntity<ContactDTO> saveContact(@RequestBody ContactCreateDTO contactCreateDTO) {
        Contact contact = toEntity(contactCreateDTO);

        contactRepository.save(contact);

        ContactDTO contactDTO = toDto(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactDTO);
    }

    private ContactDTO toDto(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setFirstName(contact.getFirstName());
        contactDTO.setLastName(contact.getLastName());
        contactDTO.setCreatedAt(contact.getCreatedAt());
        contactDTO.setUpdatedAt(contact.getUpdatedAt());
        return contactDTO;
    }

    private Contact toEntity(ContactCreateDTO dto) {
        Contact contact = new Contact();
        contact.setPhone(dto.getPhone());
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        return contact;
    }
    // END
}
