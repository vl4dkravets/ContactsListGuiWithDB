package dao;

import entity.Contact;
import exception.ContactDaoException;

import java.util.List;

/**
* Defines DAO interface for working with DB
* */
public interface ContactDAO 
{
    public Long addContact(Contact contact) throws ContactDaoException;

    public void updateContact(Contact contact) throws ContactDaoException;

    public void deleteContact(Long contactId) throws ContactDaoException;

    public Contact getContact(Long contactId) throws ContactDaoException;

    public List<Contact> findContacts() throws ContactDaoException;
}
