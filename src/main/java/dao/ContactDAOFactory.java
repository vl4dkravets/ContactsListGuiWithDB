package dao;

import config.GlobalConfig;

import java.lang.reflect.InvocationTargetException;

/**
 * Factory which first let's to specify the actual implementation of DAO interface
 * Name of the class in retrieved from config file
 */
public class ContactDAOFactory 
{   
    public static ContactDAO getContactDAO() {
        try {
            Class dao = Class.forName(GlobalConfig.getProperty("dao.class"));
            return (ContactDAO)dao.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
