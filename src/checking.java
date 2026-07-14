import java.util.Scanner;
public class checking {


    // Constructor to pass your existing scanner
    private static Scanner scanner = new Scanner(System.in);

    public checking(){

    }
    // Method to validate and return an INT
    public static int getValidInt() {
        if (!scanner.hasNextInt()) {
             System.out.println("not int");
            scanner.nextLine();
            return 0 ;
           
            // Stop the program
        }
        int value = scanner.nextInt();
        scanner.nextLine();
         // Clear the leftover newline bug! 
       
        return value;
        
    }



    // Method to validate and return a DOUBLE
    public static double getValidDouble() {
        if (!scanner.hasNextDouble()) {
            System.out.println("not double");
            scanner.nextLine();
            return 0 ;
            
             // Stop the program
        }
        double value = scanner.nextDouble();
        scanner.nextLine(); // Clear the leftover newline bug!
        return value;

    }


    public String entername(String name){
        System.out.println("enter the name of: "+ name);
        String naming = scanner.nextLine();
        return naming ;
    }

    public String enteridentity(String name){
        System.out.println("enter the identity of: "+ name);
        String naming = scanner.nextLine();
        return naming ;
    }

    public String enternationationality(String name){
        System.out.println("enter the nationality of: "+ name);
        String naming = scanner.nextLine();
        return naming ;
    }

   

}
