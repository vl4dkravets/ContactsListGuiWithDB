package gui;

import business_logic.ContactManager;
import entity.Contact;
import exception.ContactLogicException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ContactFrame extends JFrame implements ActionListener
{
    //Names of attributes from the config file
    // Use it for retrieving button names from config
    private static final String FRAME = "frame";
    private static final String C_REFRESH = "refresh";
    private static final String C_ADD = "add";
    private static final String C_UPDATE = "update";
    private static final String C_DELETE = "delete";

    // Action commands for events when a button is pressed
    // used to distinguish what button was pressed
    private static final String LOAD = "LOAD";
    private static final String ADD = "ADD";
    private static final String EDIT = "EDIT";
    private static final String DELETE = "DELETE";

    // Instance which used DAO implementation to work with DB
    private final ContactManager contactManager = new ContactManager();
    private final JTable contactTable = new JTable();

    public ContactFrame() {
        // Settings an option in the table
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        // Each element will be the only one in its row
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Each elements take all the available space
        gbc.fill = GridBagConstraints.BOTH;

        // Manually settings margins for each element
        gbc.insets = new Insets(5, 5, 0, 5);

        // Button's panel
        JPanel btnPanel = new JPanel();

        btnPanel.setLayout(gridbag);
        // Creating buttons, specifying their names, connecting listeners & adding them to panel
        // Extra, it also retrieves button's text from the config file
        btnPanel.add(createButton(gridbag, gbc, GuiResource.getLabel(FRAME, C_REFRESH), LOAD));
        btnPanel.add(createButton(gridbag, gbc, GuiResource.getLabel(FRAME, C_ADD), ADD));
        btnPanel.add(createButton(gridbag, gbc, GuiResource.getLabel(FRAME, C_UPDATE), EDIT));
        btnPanel.add(createButton(gridbag, gbc, GuiResource.getLabel(FRAME, C_DELETE), DELETE));

        // Separate panel to hold the panel with buttons
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        left.add(btnPanel, BorderLayout.NORTH);
        add(left, BorderLayout.WEST);

        // Adding the table to the main frame
        add(new JScrollPane(contactTable), BorderLayout.CENTER);


        setBounds(100, 200, 900, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Loading contacts/filling the table on the start-up
        try {
            loadContact();
        } catch (ContactLogicException ex) {
            ex.printStackTrace();
        }
    }

    // Creates buttons & specifies its characteristics
    private JButton createButton(GridBagLayout gridbag, GridBagConstraints gbc, String title, String action) {
        JButton button = new JButton(title);
        button.setActionCommand(action);
        button.addActionListener(this);
        gridbag.setConstraints(button, gbc);

        return button;
    }

    @Override
    // Handles presses of buttons
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        try {
            switch (action) {
                case LOAD:
                    loadContact();
                    break;
                case ADD:
                    addContact();
                    break;
                case EDIT:
                    editContact();
                    break;
                case DELETE:
                    deleteContact();
                    break;
            }
        } catch (ContactLogicException ex) {
            // Upon an exception, shows a dialog window with error's description
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // Loads the list of contacts, adds it to the table
    // & updates the table representation on the screen
    private void loadContact() throws ContactLogicException {
        List<Contact> contacts = contactManager.findContacts();
        // Creates new, updated model/representation of the table
        ContactModel cm = new ContactModel(contacts);
        // Puts new model to our table on the screen
        contactTable.setModel(cm);
    }

    private void addContact() throws ContactLogicException {
        EditContactDialog ecd = new EditContactDialog();
        // pass instance of the dialog window
        // from there, we retrieve input data from text fields
        // initialize a new contact with them & add it to DB
        saveContact(ecd);
    }

    private void editContact() throws ContactLogicException {
        // Gets the highlighted row to edit
        int sr = contactTable.getSelectedRow();
        // if there was a highlighted row, then =>
        if (sr != -1) {
            // Retrieves the id of the contact from the highlighted row in the table
            Long id = Long.parseLong(contactTable.getModel().getValueAt(sr, 0).toString());
            // Finds contact by its ID
            Contact cnt = contactManager.getContact(id);
            // Create a dialog window & pass Contact there
            EditContactDialog ecd = new EditContactDialog(cnt);
            saveContact(ecd);
        } else {
            // If a row wasn't highlighted => error
            JOptionPane.showMessageDialog(this, "You should choose a row for editing");
        }
    }

    private void deleteContact() throws ContactLogicException {
        // Gets the highlighted row
        int sr = contactTable.getSelectedRow();
        if (sr != -1) {
            // Retrieves the id of the contact from the highlighted row in the table
            Long id = Long.parseLong(contactTable.getModel().getValueAt(sr, 0).toString());
            // Uses id to delete the contact
            contactManager.deleteContact(id);
            // Reload the visual table on the main frame
            loadContact();
        } else {
            JOptionPane.showMessageDialog(this, "You should choose a row for deletion");
        }
    }

    private void saveContact(EditContactDialog ecd) throws ContactLogicException {
        // If you pressed SAVE button in the dialog screen,
        // we can proceed with saving input data
        if (ecd.isSave()) {
            // Fills an instance of Contact with input data from text fields
            Contact cnt = ecd.getContact();

            if (cnt.getContactId() != null) {
                // if ID field was initialized
                contactManager.updateContact(cnt);
            } else {
                // Если у контакта нет ID - значит он новый и мы его добавляем
                contactManager.addContact(cnt);
            }
            loadContact();
        }
    }
}
