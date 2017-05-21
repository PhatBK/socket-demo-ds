/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backupdatabase;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Mr-Tuy
 */
public class ConnectToDatabase {
    
    public static Connection connect() throws Exception{
        
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        System.out.println("#- Driver loaded");
            
        String dbUrl = "jdbc:sqlserver://PHAT_NGUYEN\\PHATBK:1433;databasename=Banking;user=sa;password=123456789";
        Connection con = DriverManager.getConnection(dbUrl);
        return con;
    }
}
