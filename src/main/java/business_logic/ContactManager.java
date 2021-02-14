package business_logic;

import dao.ContactDAO;
import dao.ContactDAOFactory;
import entity.Contact;
import exception.ContactDaoException;
import exception.ContactLogicException;

import java.util.List;

/**
 * Proxy class - provides functionality working with DB
 * Performs the work with DAO instance
 */
public class ContactManager 
{
    private final ContactDAO dao;
    
    public ContactManager() {
        dao = ContactDAOFactory.getContactDAO();
    }

    public Long addContact(Contact contact) throws ContactLogicException {
        try {
            return dao.addContact(contact);
        } catch (ContactDaoException ex) {
            throw new ContactLogicException(ex);
        }
    }

    public void updateContact(Contact contact) throws ContactLogicException {
        try {
            dao.updateContact(contact);
        } catch (ContactDaoException ex) {
            throw new ContactLogicException(ex);
        }
    }

    public void deleteContact(Long contactId) throws ContactLogicException {
        try {
            dao.deleteContact(contactId);
        } catch (ContactDaoException ex) {
            throw new ContactLogicException(ex);
        }
    }

    public Contact getContact(Long contactId) throws ContactLogicException {
        try {
            return dao.getContact(contactId);
        } catch (ContactDaoException ex) {
            throw new ContactLogicException(ex);
        }
    }

    public List<Contact> findContacts() throws ContactLogicException {
        try {
            return dao.findContacts();
        } catch (ContactDaoException ex) {
            throw new ContactLogicException(ex);
        }
    }
}
