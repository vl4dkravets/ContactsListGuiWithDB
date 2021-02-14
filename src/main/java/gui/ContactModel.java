package gui;

import entity.Contact;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ContactModel extends AbstractTableModel
{
    private static final String MODEL = "model";
    
    // Names of attributes fro, config files which contains names of columns
    private static final String[] HEADERS = {
        "id", "firstname", "lastname", "phone", "email"
    };
    
    // All the contact in the table are store as list
    private final List<Contact> contacts;

    public ContactModel(List<Contact> contacts) {
        this.contacts = contacts;
    }
    
    @Override
    public int getRowCount() {
        return contacts.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    // Gets column name from config file
    public String getColumnName(int col) {
        return GuiResource.getLabel(MODEL, HEADERS[col]);
    }
    
    @Override
    // Based on row & column, returns specific piece of data
    public Object getValueAt(int row, int col) {
        Contact contact = contacts.get(row);
        // В зависимости от номера колонки возвращаем то или иное поле контакта
        switch (col) {
            case 0:
                return contact.getContactId().toString();
            case 1:
                return contact.getFirstName();
            case 2:
                return contact.getLastName();
            case 3:
                return contact.getPhone();
            case 4:
                return contact.getEmail();
            default:
                return "";
        }
    }
}
