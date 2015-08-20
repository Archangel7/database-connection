package lab7;

public class Menu {

	public static int Menuopts()
	{
		int choice =0;
		System.out.println("1. enter the first or last name of a student to search: ");
		System.out.println("2. enter a course number to find a section: ");
		System.out.println("3. enter a student ID and the year " +
				               "to find the total cost of all courses ");
		System.out.println("4. To quit application");
		System.out.println("5. enter a course to search: ");
		System.out.print("enter your choice: ");
		return IO.readInt();
		
	}

}
