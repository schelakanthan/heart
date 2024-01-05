package workbenchconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Workbenchconnection {

public static void main(String[] args) {
	Connection con = null;
	
	try {
		con =(Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/tomato","root","Zujanshan83@");
	
		if(con!=null){
			System.out.println("database is connected");
			
		}
	}catch(Exception e){
		System.out.println("not connected");
	}
}
	
}
