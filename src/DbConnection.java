

/**
 * 
 * @author rwangari
 * In this class, we will be connecting to the Database
 */

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
public class DbConnection {
	
	     static String dbUrl = "";  
	     //jdbc:mysql://localhost:3306/customers
	     static String jdbcDriver = "com.mysql.jdbc.Driver";   
	     static String username = "";   
	     static String password = "";
	     static Connection con;
	     static String databaseString;
	    
	    
		public DbConnection(String dbUrl, String username, String password) {
			DbConnection.dbUrl=dbUrl;
			DbConnection.username=username;
			DbConnection.password=password;
		}
		
		
		public static Connection getConnection() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				try {
					con = DriverManager.getConnection(dbUrl, username, password);
					
					//if the connection is okay
					System.out.println("Connection Established Successfull and the DATABASE NAME IS:"
	                        + con.getMetaData().getDatabaseProductName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("Could not connect to the database");
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return con;			
		}

	

	 public static void main(String[] args) {
		 String dbUrl = "jdbc:mysql://localhost:3306/customers";
		 String username = "root";
		 String password ="";
		 
		 DbConnection myConnection = new DbConnection(dbUrl,username,password);
		 
		 myConnection.getConnection();
		
		
		
	}

}
