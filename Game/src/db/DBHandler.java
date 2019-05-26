package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {
	
	private String dbName = "testDB";
	private String user = "root";
	private String password = "root";
	private Connection connection;
	
	public void setDBName (String dbName) {
		this.dbName = dbName;
	}
	public void setUser (String user) {
		this.user = user;
	}
	public void setPassword (String password) {
		this.password = password;
	}
	
	public void connect() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			connection = DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/"+ dbName, user, password);  
			}
		catch(Exception e){ System.out.println(e);}  
	}
	
	public void execute(String sqlCommand) {
		Statement stmt;
		try {
			stmt = connection.createStatement();
			stmt.execute(sqlCommand);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String sqlQuery) {
		Statement stmt;
		ResultSet rs = null;;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}

	public static void main(String[] args) {
		
		//Create a DB handler through which we will interact with the database
		DBHandler dbHandler = new DBHandler();
		
		// Set DB configuration
		dbHandler.setDBName("testDB");
		dbHandler.setUser("root");
		dbHandler.setPassword("root");
		
		//Open a connection to the DB
		dbHandler.connect();
		
		//Execute an SQL command (e.g., create, update, delete)
		dbHandler.execute("update users set Age = 99 where Name = 'Rami'");
		
		//Execute an SQL query and get the results into a result set
		ResultSet rs=dbHandler.executeQuery("select * from users");  
		try {
			//Iterate over the rows and print each column according to its type (int, string, etc.)
			while(rs.next())  
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getInt(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Close the connection to the DB
		dbHandler.close();

	}

}
