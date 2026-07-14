import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.IntSupplier;
import java.util.InputMismatchException;


public class adding extends checking {

    private static Scanner scanner = new Scanner(System.in);

    ArrayList<Flight> flightsList = new ArrayList<Flight>() ;

    public static ArrayList<Flighter> flightersList = new ArrayList<Flighter>() ;
    public static ArrayList<modiefoun> copilots = new ArrayList<modiefoun>();
    private static ArrayList<modiefoun> choosencopilots ;
    public static ArrayList<Flight> flightslists = new ArrayList<Flight>();

    private static double salaryofflighter ;
    private static double floughthours ;
    checking checss =  new checking() ;





    


/*---------------------------------------------------------------------------------------------------------------------------- */

    public static void addFlighter(){

        String step1ofnaming = "flighter" ;




        


             checking checc = new checking() ;
             String nameofflighter = checc.entername(step1ofnaming);
             String identityoftheflighter = checc.enteridentity(step1ofnaming);
             String nationalityofflighter = checc.enternationationality(step1ofnaming);





        
        boolean flighterslalarychecking = true ;

        while(flighterslalarychecking){
        System.out.println("enter the salary of the flighter");


         salaryofflighter = 1 ;
         salaryofflighter = checking.getValidDouble();   
         if (salaryofflighter != 0 ) {
            flighterslalarychecking = false ;
         }
         else if (salaryofflighter == 0 ) {
            flighterslalarychecking = true ;
            
         }
        }
         
         
        


        


        

         
        boolean floughtedchecking = true ;

        while(floughtedchecking){
        System.out.println("enter the hours of the flighter that flought");


        floughthours = checking.getValidDouble(); // Use the method to get a valid double
         if (floughthours != 0 ) {
            floughtedchecking = false ;
         }
         else if (floughthours == 0 ) {
            floughtedchecking = true ;
            
         }
        }

        
        
        ArrayList<String> rokhas = new ArrayList<String>();
        int numberofrokhas = 0 ;
        int number = 0 ;

          boolean rokhaschecking = true ;

        while(rokhaschecking){
        System.out.println("enter how many rokhas does the flighter have");


         number = checking.getValidInt(); // Use the method to get a valid int                
         if (number != 0 ) {
            rokhaschecking = false ;
         }
         else if (number == 0 ) {
            rokhaschecking = true ;
            
         }
        }



         
        

        for (int x = 0 ; x <number ; x++)
        {
            System.out.println("enter the rokhsa number :"+(x+1));
            String rokhsa = scanner.nextLine();
            rokhas.add(rokhsa);

        }        
        Flighter s1 =  new Flighter(nameofflighter, identityoftheflighter, nationalityofflighter, salaryofflighter, floughthours, rokhas) ;

        flightersList.add(s1);
        System.out.println("flighter been added succesfully");
    }
    /*------------------------------------------------------------------------------------------------------- */


    public static void addFlight(){
         int numofchairs  =  0 ;

        System.out.println("enter the destination of the flight");
        String destination = scanner.nextLine();


        boolean chairscheccking = true ;
        int numberofchairs = 0 ;

        while(chairscheccking){
        System.out.println("enter the number of chairs in the flight");


        numberofchairs = checking.getValidInt(); // Use the method to get a valid int
         if (numberofchairs != 0 ) {
            chairscheccking = false ;
         }
         else if (numberofchairs == 0 ) {
            chairscheccking = true ;
            
         }
        }


        System.out.println("enter the name of the captain");
        String nameofcaptin = scanner.nextLine();

        Flighter captin = null;
        for (Flighter flighter : flightersList) {
            if (flighter.getName().equalsIgnoreCase(nameofcaptin)) {
                captin = flighter;
                break;
            }
        }

        if (captin == null) {
            System.out.println("Captain not found. Please add the captain first.");
            return;
        }

        int numberofpassengeres = 0 ;



        boolean checkingiftrue = true ;      
        
        

          boolean passengereschecking = true ;

        while(passengereschecking){
         System.out.println("enter the number of passengeres in this flight");


         numberofpassengeres = checking.getValidInt(); // Use the method to get a valid int
         if (numberofpassengeres != 0 ) {
            passengereschecking = false ;
         }
         else if (numberofpassengeres == 0 ) {
            passengereschecking = true ;
            
         }
        }

      
        boolean numofchairwithpasschecking = true ;
        while (numofchairwithpasschecking) {
            
        
        if (numberofpassengeres > numberofchairs) {
            System.out.println("no enough place for passengeres");
            System.out.println("try again");
            numofchairwithpasschecking = true ;
            
        }
        else {
            System.out.println("num of passengeres been added successfuly");
            numofchairwithpasschecking = false ;
        }



    }
    
    
      


    boolean checkcopilotshere = true ;
    int numberofcopilots = 0 ;
     

    while (checkcopilotshere) {
    System.out.println("enter number of copilots of this flight");
    numberofcopilots = checking.getValidInt();
    if (numberofcopilots != 0) {
        checkcopilotshere = false ;
        
    }
    else if (numberofcopilots == 0 ){
        checkcopilotshere = false ;

    }

    System.out.println("add copilots from these");
    for ( int i = 0 ; i < numberofcopilots ; i++ ){
        String nameofcopilothere = copilots.get(i).getName();
        System.out.println("the copilot number :"+ (i+1)+"is :"+nameofcopilothere);
    }

    System.out.println("enter the number of the copilots you want");
    for (int i = 0 ; i < numberofcopilots ; i++){
        choosencopilots = new ArrayList<modiefoun>();
        System.out.println("enter the copilts you want to put in number :"+(i+1));
        int choosen = scanner.nextInt();
        choosencopilots.add(copilots.get((choosen - 1)));
        


    }
    




        
    }


    

    ArrayList<Passengere> listofthepassengeres = new ArrayList<Passengere>();


    System.out.println("now starting to add passengerees");
    System.out.println("===================================");
    for(int i = 0 ; i < numberofpassengeres ; i++){
    System.out.println("enter the info of passengere numer :"+ (i+1));
    listofthepassengeres.add(addairportattendents());

    }






    Flight s1 = new Flight(destination, listofthepassengeres, captin, copilots, numofchairs);
    flightslists.add(s1);
    



    



    }







        
        


/*-------------------------------------------------------------------------------------------------------------------- */
        

    public static Passengere addairportattendents(){


        String step1ofnaming = "passenger" ;
        checking checc = new checking() ;
        String name = checc.entername(step1ofnaming);
        String identity = checc.enteridentity(step1ofnaming);
        String nationality = checc.enternationationality(step1ofnaming);



        System.out.println("enter the passport of the passenger");
        String passport = scanner.nextLine();

        System.out.println("enter the passport end date of the passenger");
        String passEndDate = scanner.nextLine();

        System.out.println("enter the destination of the passenger");
        String destination = scanner.nextLine();
        return new Passengere(name, identity, nationality, passport, passEndDate, destination) ;



    }


/*--------------------------------------------------------------------------------------------------------------------- */
 
public static void addcopilot(){
     /*    public modiefoun(String name, String identity,
             String nationality, double salary,
              ArrayList<String> langsList, double hours) ; */
             String step1ofnaming = "copilot" ;
             checking checc = new checking() ;
             String step2ofnaming = checc.entername(step1ofnaming);
             String identity = checc.enteridentity(step1ofnaming);
             String nationality = checc.enternationationality(step1ofnaming);

              boolean salarycehckbolean = true ;
              double cehckingsalary = 0;

        while(salarycehckbolean){
        System.out.println("enter the salary of the flighter");


        cehckingsalary = checking.getValidDouble(); // Use the method to get a valid double
         if (cehckingsalary != 0 ) {
            salarycehckbolean = false ;
         }
         else if (cehckingsalary == 0 ) {
            salarycehckbolean = true ;
            
         }
        }

        int numoflangs = 0 ;
        boolean checklangs = true ;
        ArrayList<String> languages = new ArrayList<String>() ;
    
        while (checklangs == true ) {

        System.out.println("how many languages does the copilot speak");
        numoflangs = adding.getValidInt();
        if (numoflangs != 0) {
            checklangs = false ;
            
        }
        else if (numoflangs == 0 ){
            checklangs = true ;
        }


        }

        for (int x = 0 ; x < numoflangs ; x++){
            System.out.println("enter the language number " + (x+1));
            String laguagesread = scanner.nextLine();
            languages.add(laguagesread);
        }

        System.out.println("enter the hours that the copilot worked");



        boolean hourshere = true ;
              double hoursherehour = 0 ;


        while(hourshere == true){


        hoursherehour = checking.getValidDouble(); // Use the method to get a valid double
         if (hoursherehour != 0 ) {
            hourshere = false ;
         }
         else if (hoursherehour == 0 ) {
            hourshere = true ;
            
         }
        }

        copilots.add(new modiefoun(step2ofnaming, identity, nationality, cehckingsalary, languages, hoursherehour)) ;

       






}


}



        
        


       
        
        



