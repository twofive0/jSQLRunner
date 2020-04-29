/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsqlrunner;
/**
 *
 * @author Devin
 */
import java.io.*;
import java.util.*;

public class appSettings {
    
    private static String databaseName;
    private static String serverName;
    private static String userName;
    private static String userPW;
    private static String dbType;
    private static String fileName;
    private static String dbPort;
        
    //default constructor
    public appSettings(){
        
        databaseName = "NoDB";
        serverName = "localhost";
        userName = "root";
        userPW = "NoPW";
        dbType = "MySQL";
        dbPort = "";
    }
    
    //Constructor with passed parameters
    public appSettings(String dbName, String dbServerName, String dbUserName, String dbUserPW, String databaseType, String databasePort){
        
        databaseName = dbName;
        serverName = dbServerName;
        userName = dbUserName;
        userPW = dbUserPW;
        dbType = databaseType;
        dbPort = databasePort;
    
    }
    
    public boolean saveSettings(){
        
        getSettingsFile();
        return true;
    }
    
    public boolean saveSettings(String dbServerName, String dbName, String dbUserName, String dbUserPW, String databaseType, String dbFile, String databasePort)
    {
        databaseName = dbName;
        serverName = dbServerName;
        userName = dbUserName;
        userPW = dbUserPW;
        dbType = databaseType;
        fileName = dbFile;
        dbPort = databasePort;
        
        saveSettingsFile();

        return true;
    }
    
    
        public boolean getSettings(){
        
        
        return true;
        }
        
        public String getDBName() {
            getSettingsFile();
            return databaseName;
        }
         
        public String getServerName() {
            getSettingsFile();
            return serverName;
        }
                
        public String getUserName() {
            getSettingsFile();
            return userName;
        }
        
        public String getUserPW() {
            getSettingsFile();
            return userPW;
        }
            
        public String getDatabaseType() {
            getSettingsFile();
            return dbType;
        }
        
        public String getFile() {
            getSettingsFile();
            return fileName;
        }
        
        public String getPort(){
            getSettingsFile();
            return dbPort;
        }
        
        private String saveSettingsFile(){
        
            String strFile = System.getProperty("user.home") + File.separator + ".jsqlrunnerold" + File.separator+ "settings.cfg";
            File f = new File(strFile);
            File d = new File(System.getProperty("user.home") + File.separator+ ".jsqlrunnerold");
            Properties prop = new Properties();
            prop.put("Server", serverName);
            prop.put("Database", databaseName);
            prop.put("User", userName);
            prop.put("PW", userPW);
            prop.put("DBType", dbType);
            prop.put("fileName", fileName);
            prop.put("Port", dbPort);
            
            try {
                //let's try to create a settings file if it doesn't exist, shall we?
                if(!d.exists()){d.mkdir();}
                if(!f.exists()){f.createNewFile();}
                FileOutputStream fout = new FileOutputStream(strFile);
                prop.storeToXML(fout,"jSQLRunnerSettings");
            }
            catch (Exception e){
                return "";
            }
           
            return strFile;
            
        }
        
        private String getSettingsFile(){
        
            String strFile = System.getProperty("user.home") + File.separator+ ".jsqlrunnerold" + File.separator+ "settings.cfg";
            File f = new File(strFile);
            Properties prop = new Properties();
            
            try {
                //let's try to create a settings file if it doesn't exist, shall we?
                if(!f.exists()){return "";}

                FileInputStream fin = new FileInputStream(strFile);
                prop.loadFromXML(fin);
                serverName = prop.getProperty("Server");
                databaseName = prop.getProperty("Database");
                userName = prop.getProperty("User");
                userPW = prop.getProperty("PW");
                dbType = prop.getProperty("DBType");
                fileName = prop.getProperty("fileName");
                dbPort = prop.getProperty("Port");
            }
            catch (Exception e){
                return "";
            }
           
            return strFile;
            
        }
}
