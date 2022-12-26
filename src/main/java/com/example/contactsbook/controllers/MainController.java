package com.example.contactsbook.controllers;

import com.example.contactsbook.dao.ContactsDao;
import com.example.contactsbook.model.Contact;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

    private final ContactsDao contactsDao;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("contacts", contactsDao.getAll());
        return "main";
    }

    @GetMapping("/add-contact")
    public String addContact(@ModelAttribute("contact") Contact contact) {
        return "add-contact";
    }

    @PostMapping("/add-contact")
    public String add(@ModelAttribute("contact") @Valid Contact contact, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-contact";
        } else {
            contactsDao.addContact(contact);
            return "redirect:/";
        }
    }


    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        contactsDao.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("contact", contactsDao.getById(id));
        log.info("get contact by id{}", contactsDao.getById(id).toString());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("contact") @Valid Contact contact, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        } else {
            contactsDao.update(id, contact);
            return "redirect:/";
        }
    }

    @GetMapping( "/name")
    public String getByName(Model model, @RequestParam String name){
        model.addAttribute("contacts", contactsDao.getByName(name));
        return "search-by-name";
    }

    @GetMapping("/lastName")
    public String getByLastName(Model model, @RequestParam String lastName){
        model.addAttribute("contacts", contactsDao.getByLastName(lastName));
        return "search-by-lastName";
    }

    @GetMapping("/email")
    public String getByEmail(Model model, @RequestParam String email){
        model.addAttribute("contacts", contactsDao.getByEmail(email));
        return "search-by-email";
    }

    @GetMapping("/phoneNumber")
    public String getByPhoneNumber(Model model, @RequestParam Long phoneNumber){
        model.addAttribute("contacts", contactsDao.getByPhoneNumber(phoneNumber));
        return "search-by-phoneNumber";
    }

}
