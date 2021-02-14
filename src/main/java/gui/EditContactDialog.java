package gui;

import entity.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditContactDialog extends JDialog implements ActionListener
{
    // Names of attributes in the config file for names of labels
    private static final String DIALOG = "dialog";
    private static final String C_GIVEN = "firstname";
    private static final String C_SURNAME = "lastname";
    private static final String C_PHONE = "phone";
    private static final String C_EMAIL = "email";

    // Button's text/headers
    private static final String SAVE = "SAVE";
    private static final String CANCEL = "CANCEL";

    // padding size
    private static final int PAD = 10;
    // width of a label
    private static final int W_L = 100;
    // text field's width
    private static final int W_T = 300;
    // width of a button
    private static final int W_B = 120;
    // height of each elements
    private static final int H_B = 25;

    // Text fields for entering data
    private final JTextPane txtFirstName = new JTextPane();
    private final JTextPane txtLastName = new JTextPane();
    private final JTextPane txtPhone = new JTextPane();
    private final JTextPane txtEmail = new JTextPane();

    // Needed when updating a contact
    private Long contactId = null;
    // Records the state of SAVE button
    private boolean save = false;

    public EditContactDialog() {
        this(null);
    }

    public EditContactDialog(Contact contact) {
        // Erases default layout
        // Instead we position everything manually
        setLayout(null);

        // Build/positions text field on in dialog windows manually
        buildFields();
        // Fill the text fields automatically, if contact wasn't empty
        initFields(contact);
        // Positions buttons
        buildButtons();

        // Means, the dialog is the only active area on the screen while it's open
        setModal(true);
        setResizable(false);
        setBounds(300, 300, 450, 200);
        setVisible(true);
    }

    private void buildFields() {
        /*
        * Retrieves names of needed labels from the config file
        * */

        JLabel lblFirstName = new JLabel(GuiResource.getLabel(DIALOG, C_GIVEN) + ":");
        lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
        // Coordinates for a label
        lblFirstName.setBounds(new Rectangle(PAD, 0 * H_B + PAD, W_L, H_B));
        add(lblFirstName);
        // Coordinates for a text field
        txtFirstName.setBounds(new Rectangle(W_L + 2 * PAD, 0 * H_B + PAD, W_T, H_B));
        txtFirstName.setBorder(BorderFactory.createEtchedBorder());
        add(txtFirstName);

        // Set's up label for last name
        JLabel lblLastName = new JLabel(GuiResource.getLabel(DIALOG, C_SURNAME) + ":");
        lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLastName.setBounds(new Rectangle(PAD, 1 * H_B + PAD, W_L, H_B));
        add(lblLastName);
        txtLastName.setBounds(new Rectangle(W_L + 2 * PAD, 1 * H_B + PAD, W_T, H_B));
        txtLastName.setBorder(BorderFactory.createEtchedBorder());
        add(txtLastName);

        // Set's up label for phone #
        JLabel lblPhone = new JLabel(GuiResource.getLabel(DIALOG, C_PHONE) + ":");
        lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPhone.setBounds(new Rectangle(PAD, 2 * H_B + PAD, W_L, H_B));
        add(lblPhone);
        txtPhone.setBounds(new Rectangle(W_L + 2 * PAD, 2 * H_B + PAD, W_T, H_B));
        txtPhone.setBorder(BorderFactory.createEtchedBorder());
        add(txtPhone);

        // // Set's up label for email
        JLabel lblEmail = new JLabel(GuiResource.getLabel(DIALOG, C_EMAIL) + ":");
        lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEmail.setBounds(new Rectangle(PAD, 3 * H_B + PAD, W_L, H_B));
        add(lblEmail);
        txtEmail.setBounds(new Rectangle(W_L + 2 * PAD, 3 * H_B + PAD, W_T, H_B));
        txtEmail.setBorder(BorderFactory.createEtchedBorder());
        add(txtEmail);
    }

    // If passed instance of Contact isn't empty
    // use its data to pass fill the text field preliminarily
    private void initFields(Contact contact) {
        if (contact != null) {
            contactId = contact.getContactId();
            txtFirstName.setText(contact.getFirstName());
            txtLastName.setText(contact.getLastName());
            txtEmail.setText(contact.getEmail());
            txtPhone.setText(contact.getPhone());
        }
    }

    // Positions 2 buttons on the dialog
    private void buildButtons() {
        JButton btnSave = new JButton("SAVE");
        btnSave.setActionCommand(SAVE);
        btnSave.addActionListener(this);
        btnSave.setBounds(new Rectangle(PAD, 5 * H_B + PAD, W_B, H_B));
        add(btnSave);

        JButton btnCancel = new JButton("CANCEL");
        btnCancel.setActionCommand(CANCEL);
        btnCancel.addActionListener(this);
        btnCancel.setBounds(new Rectangle(W_B + 2 * PAD, 5 * H_B + PAD, W_B, H_B));
        add(btnCancel);
    }

    @Override
    // Handles presses of buttons
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        // If SAVE button was pressed - record that action/ update its state
        save = SAVE.equals(action);
        // Close the form after the press on any of 2 buttons
        setVisible(false);
    }

    // Allows to figure out whether should you store input data to DB
    public boolean isSave() {
        return save;
    }

    // Creates a Contact instance from filled text fields
    public Contact getContact() {
        Contact contact = new Contact(contactId, txtFirstName.getText(),
                txtLastName.getText(), txtPhone.getText(), txtEmail.getText());
        return contact;
    }
}
