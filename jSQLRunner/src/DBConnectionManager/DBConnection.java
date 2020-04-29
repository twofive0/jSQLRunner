/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnectionManager;

import org.json.simple.*;
/**
 *
 * @author devin
 */
public class DBConnection implements java.io.Serializable {
    
        public String ConnectionName;
        public String ConnectionType;
        public String TargetDatabase;
        public String Server;
        public String Database;
        public String Port;
        public String UserName;
        public String UserPassword;
        public String FileLocation;
        public String FilePassword;
        public boolean UseTrustedConnection;
        public boolean InUse;
        public String LastConnectionDate;
        public String LastUser;
        
        public DBConnection()
        {
            ConnectionName = "";
            ConnectionType = "";
            TargetDatabase = "";
            Server = "";
            Database = "";
            Port = "";
            UserName = "";
            UserPassword = "";
            FileLocation = "";
            FilePassword = "";
            UseTrustedConnection = false;
            InUse = false;
            LastConnectionDate = "Never";
            LastUser = "None";
        }
        
        public String toJSON()
        {
            String jsonString;    
            
            jsonString = "{ConnectionName:\" " + ConnectionName + "\", ";
            jsonString += "ConnectionType:\" " + ConnectionType + "\", ";
            jsonString += "TargetDatabase:\" " + TargetDatabase + "\", ";
            jsonString += "Server:\" " + Server + "\", ";
            jsonString += "Database:\" " + Database + "\", ";
            jsonString += "Port:\" " + Port + "\", ";
            jsonString += "UserName:\" " + UserName + "\", ";
            jsonString += "UserPassword:\" " + UserPassword + "\", ";
            jsonString += "FileLocation:\" " + FileLocation + "\", ";
            jsonString += "FilePassword:\" " + FileLocation + "\", ";
            jsonString += "UseTrustedConnection:\" " + UseTrustedConnection + "\", ";
            jsonString += "InUse:\" " + InUse + "\", ";
            jsonString += "LastConnectionDate:\" " + LastConnectionDate + "\", ";
            jsonString += "LastUser:\" " + LastUser + "\"}";
            
            return jsonString;            
        }
        
        public String toString()
        {
            return ConnectionName;
        }
        
        public String getDelimitedString()
        {
            String csvString;    
            
            csvString = ConnectionName + "|";
            csvString += ConnectionType + "|";
            csvString += TargetDatabase + "|";
            csvString += Server + "|";
            csvString += Database + "|";
            csvString += Port + "|";
            csvString += UserName + "|";
            csvString += UserPassword + "|";
            csvString += FileLocation + "|";
            csvString += FilePassword + "|";
            csvString += UseTrustedConnection + "|";
            csvString += InUse + "|";
            csvString += LastConnectionDate + "|";
            csvString += LastUser;
            
            return csvString;  
        }
        
        public void setFromString(String delimitedText)
        {
            String items[] = delimitedText.split("\\|");
            
            for (String itm : items)
            {
                if (itm == "null") itm = "";
                if (itm.isEmpty()) itm = "";
            }

            ConnectionName = items[0];
            ConnectionType = items[1];
            TargetDatabase = items[2];
            Server = items[3];
            Database = items[4];
            Port = items[5];
            UserName = items[6];
            UserPassword = items[7];
            FileLocation = items[8];
            FilePassword = items[9];
            UseTrustedConnection = Boolean.valueOf(items[10]);
            InUse = Boolean.valueOf(items[11]);
            LastConnectionDate = items[12];
            LastUser = items[13];
        }
    
}
