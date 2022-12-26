package com.example.contactsbook.dao;

import com.example.contactsbook.model.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class ContactsDao {
    public static final RowMapper<Contact> CONTACT_ROW_MAPPER = (rs, i) -> Contact.builder()
            .id(rs.getInt("id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .email(rs.getString("email"))
            .phoneNumber(rs.getLong("phone_number"))
            .build();
    public static final String SELECT_ALL = "SELECT id ,first_name, last_name, email, phone_number FROM contacts";
    public static final String SELECT_BY_ID = "SELECT id ,first_name, last_name, email, phone_number FROM contacts WHERE id=?";
    public static final String SELECT_BY_NAME = "SELECT id ,first_name, last_name, email, phone_number FROM contacts WHERE first_name=?";
    public static final String SELECT_BY_LASTNAME = "SELECT id ,first_name, last_name, email, phone_number FROM contacts WHERE last_name=?";
    public static final String SELECT_BY_PHONE_NUMBER = "SELECT id ,first_name, last_name, email, phone_number FROM contacts WHERE phone_number=?";
    public static final String SELECT_BY_EMAIL = "SELECT id ,first_name, last_name, email, phone_number FROM contacts WHERE email=?";
    public static final String ADD_CONTACT = "INSERT INTO contacts (first_name, last_name, email, phone_number)VALUES (?,?,?,?)";

    private final JdbcTemplate jdbcTemplate;

    public List<Contact> getAll() {
        return jdbcTemplate.query(
                SELECT_ALL,
                CONTACT_ROW_MAPPER
        );
    }

    public int addContact(Contact contact) {
        return jdbcTemplate.update(ADD_CONTACT, ps -> {
                ps.setString(1,contact.getFirstName());
                ps.setString(2,contact.getLastName());
                ps.setString(3,contact.getEmail());
                ps.setLong(4,contact.getPhoneNumber());
        });
    }

    public void deleteById(int id){
        jdbcTemplate.update("DELETE FROM contacts WHERE id=?",id);
    }

    public void update(int id, Contact contact) {
        jdbcTemplate.update("UPDATE contacts SET first_name=?, last_name=?, email=?, phone_number=? WHERE id=?",ps -> {
            ps.setString(1,contact.getFirstName());
            ps.setString(2,contact.getLastName());
            ps.setString(3,contact.getEmail());
            ps.setLong(4,contact.getPhoneNumber());
            ps.setInt(5,id);
        });
    }

    public List<Contact> getByName(String firstName){
        return jdbcTemplate.query(SELECT_BY_NAME, new Object[]{firstName},CONTACT_ROW_MAPPER);
    }

    public Contact getById(int id) {
        return jdbcTemplate.query(SELECT_BY_ID, new Object[]{id}, CONTACT_ROW_MAPPER)
                .stream()
                .findAny()
                .orElse(null);
    }

    public List<Contact> getByLastName(String lastName){
        return jdbcTemplate.query(SELECT_BY_LASTNAME, new Object[]{lastName},CONTACT_ROW_MAPPER);
    }
    public List<Contact> getByEmail(String email){
        return jdbcTemplate.query(SELECT_BY_EMAIL, new Object[]{email},CONTACT_ROW_MAPPER);
    }

    public List<Contact> getByPhoneNumber(Long phoneNumber){
        return jdbcTemplate.query(SELECT_BY_PHONE_NUMBER, new Object[]{phoneNumber},CONTACT_ROW_MAPPER);
    }
}
