package lab7;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.sql.*;

public class MainClass {
		
	public static void main(String[] args) {  // start of main program
		
		boolean infinite_loop = true;
		
		try{
			//1. LOAD AND REGISTER THE DRIVER
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch(ClassNotFoundException x){
			System.out.println("Unable to load Driver class.");
			return;
		} 

		try{  //start of try catch block
			String connString = "jdbc:oracle:thin:@//munro.humber.ca:1521/msit.humber.ca";
			String userName="cxmc0015";
			String password="oracle";

			// CONNECT TO THE DATABASE
			Connection con = DriverManager.getConnection(connString,userName,password);
			
			//  CREATE A STATEMENT
			Statement stmt = con.createStatement();
		
		while(infinite_loop)
		{  //start of while loop
		
			int choice = Menu.Menuopts();  //displays the menu options from the menu class
			
	      switch(choice)
	      {   // start of switch 
	    
	    
	      case 1:   // searches for a student by last name or first name
	    	  
			    	  System.out.print("Please enter the student's name: ");
					  String stuName = IO.readString();  //gets the user input
			    	 
					  String sql_student_name = "SELECT first_name, last_name FROM Student WHERE first_name LIKE INITCAP(?) OR last_name LIKE INITCAP(?)";
					  PreparedStatement prepStmt = con.prepareStatement(sql_student_name);
					  prepStmt.setString(1, "%" + stuName + "%");
					  prepStmt.setString(2,"%"+ stuName + "%" );
		
						ResultSet rs = prepStmt.executeQuery();
		
						System.out.println("\tFirst Name\t\tLast Name");
						while (rs.next())
						{
							System.out.println("\t" + rs.getString("first_name") + "\t\t\t" + rs.getString("last_name"));
						}
						rs.close();
						System.out.println("\n\n");
						 break;
	    	  
	      case 2: //finds sections by course number 
	    	  
	    	  System.out.print("please enter a course number: ");
	    	  int COURSE_NO = IO.readInt();   // gets the user input 
	    	  
	    	  String sql_course_no = "SELECT SECTION_ID FROM SECTION WHERE COURSE_NO = ?";
	    	  PreparedStatement statement = con.prepareStatement(sql_course_no);
	    	  
	    	  statement.setInt(1,COURSE_NO);
	    	  
	    	  ResultSet RS = statement.executeQuery();
	    	  
	    	  System.out.println("\tSection ID\t");
	    	  while(RS.next())
	    	  {
	    		  System.out.println("\t"+RS.getInt("SECTION_ID"));
	    	  }
	    	  RS.close();
	    	  System.out.println("\n\n");
	    	  break;
	    	  
	      case 3:   
	    	      System.out.print("enter the student ID: ");
	    	      String student_id = IO.readString();
	    	      System.out.print("enter the year: ");
	    	      String year = IO.readString();
	    	   
	    	   //   java.sql.Date date = java.sql.Date.valueOf(year);
	    	      
	    	          
	    	     
	    	   //   int var = year-1;
	    	      String sql_ID_year = "SELECT sum(COST) AS COST, STUDENT_ID, trunc(ENROLL_DATE,'YYYY') AS YEAR FROM ENROLLMENT e JOIN SECTION s ON e.SECTION_ID = s.SECTION_ID JOIN COURSE c ON c.COURSE_NO = s.COURSE_NO group by STUDENT_ID, ENROLL_DATE HAVING STUDENT_ID = ?  AND trunc(ENROLL_DATE,'YYYY') = TRUNC(TO_DATE(?,'YYYY'),'YYYY')";
	    	      
	    	      PreparedStatement ID_YEAR = con.prepareStatement(sql_ID_year);
	    	      
	    	      ID_YEAR.setString(1, student_id);
	    	      ID_YEAR.setString(2,year);
	    	      
	    	     // ID_YEAR.setDate(2, new java.sql.Date(year));
	    	      
	    	    //  System.out.println(new java.sql.Date(year));
	    	      
	    	    // /*
	    	      ResultSet set = ID_YEAR.executeQuery();
	    	      
	    	      
	    	      System.out.println("\ttotal cost\tstudent id\tdate");
	    	      while(set.next())
	    	      {
	    	    	  System.out.println("\t"+set.getString("COST")+"\t\t"+set.getString("STUDENT_ID")+"\t\t"+set.getString("YEAR"));
	    	      }
	    	     
	    	     
	    	      set.close(); //*/
	    	      System.out.println("\n\n");
	    	       break;
	         	  
	         	  
	      case 4:  // exit the program
	    	      
	    	   // CLOSE THE CONNECTION			    
			    stmt.close();
			    con.close();
			    
	    	     System.out.println("thank you for using this app...");
	    	     infinite_loop = false;
	    	     break;
	    	     
	      case 5:
    	      System.out.println("enter a course: ");
    	      String course = IO.readString();
    	      
    	      String sql_course = "SELECT description,cost,course_no,prerequisite FROM COURSE WHERE DESCRIPTION LIKE INITCAP(?)";
    	      PreparedStatement course_stmt = con.prepareStatement(sql_course);
    	      
    	      course_stmt.setString(1, "%"+ course + "%");
    	      
    	      ResultSet exestmt = course_stmt.executeQuery();
    	      	    	      
    	      System.out.println("\tcourse number\t\tcost\t\t\tprerequisite\t\tdescription");
    	      
    	      while(exestmt.next())
    	      {
    	    	  System.out.println("\t"+exestmt.getInt("course_no")+"\t\t"
    	          +"\t"+exestmt.getInt("cost")+
    	          "\t\t\t"+exestmt.getInt("prerequisite")+"\t\t\t"+exestmt.getString("description"));
    	    	  
    	      }
    	      exestmt.close();
    	      System.out.println("\n\n");
    	      break;
	    	     
	            default: System.out.println("please enter correct date..."); break;
	      }  // end of switch statement
		      
	     
		} // end of while loop
		
		
		}  // end of try block

  	  catch(Exception ex)
  	  {
  		  System.out.println("SQL Exception: " + ex.getMessage());
			  ex.printStackTrace();
  	  }

	}// end of main program

} //end of class
