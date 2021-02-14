package gui;

import java.util.PropertyResourceBundle;

public class GuiResource
{
    private static final String RESOURCES = "GuiRes";


    private static PropertyResourceBundle components = null;

    // loads attributes from specified config file
    public static void initComponentResources() {
       components = (PropertyResourceBundle) PropertyResourceBundle.getBundle(RESOURCES);
    }

    // Returns values of specified attribute
    public static String getLabel(String formId, String componentId) {
        return components.getString(formId + "." + componentId);
    }
}
