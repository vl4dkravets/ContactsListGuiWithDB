package test;

import config.GlobalConfig;
import gui.ContactFrame;
import gui.GuiResource;

/**
 * Entry point
 */
public class ContactsTest
{
    public static void main(String[] args) {
        try {
            GlobalConfig.initGlobalConfig();
            GuiResource.initComponentResources();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            return;
        }
        ContactFrame cf = new ContactFrame();
        cf.setVisible(true);
    }
}
