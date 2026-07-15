import java.util.*;

public class Flighter extends Employee{
    
    private static int ided ;
    private int id ;
    private double salary ;
    private double hours ;
    private double hourprice = 30;

    private ArrayList<String> rokhasList = new ArrayList<String>();

    


    public Flighter(String name, String identity, String nationality, double salary, double hours, ArrayList<String> rokhas) {
        super(name, identity, nationality);
        this.salary = (salary + (hours * hourprice)) ;
        this.id = (this.id + (++this.ided)) ;
        this.hours = hours ;
        this.rokhasList = rokhas ;
 
    }
   
    

    public Flighter(){
        
    }

    

    



    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }


    public double getHours() {
        return hours;
    }
    public void setHourprice(double hourprice) {
        this.hourprice = hourprice;
    }

    

    public void setRokhasList(ArrayList<String> rokhasList) {
        this.rokhasList = rokhasList;
    }

    public int getId() {
        return id;
    }

    public double getSalary() {
        return salary;
    }

    public ArrayList<String> getRokhasList() {
        return rokhasList;
    }


    }
