import java.util.ArrayList;

public class modiefoun extends Employee {
    private static int ided ;
    private int id ;
    private double salary = 23 ;
    private ArrayList<String> langsList = new ArrayList<String>();
    private int hourprice;
    private double hours ;


    

    public modiefoun(String name, String identity, String nationality, double salary, ArrayList<String> langsList, double hours) {
        super(name, identity, nationality);
        this.salary = salary ;
        this.id = (this.id + (++this.ided)) ;
        this.langsList = langsList ;
        this.hours = hours ;
    }
    

    public double getSalary() {
        return salary;
    }


    public double getHours() {
        return hours;
    }
    


    public void setSalary(double salary) {
        this.salary = (hours * hourprice);
    }

    public void setLangsList(ArrayList<String> langsList) {
        this.langsList = langsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void resetIdCounter(int value) {
        ided = value;
    }

    

    public ArrayList<String> getLangsList() {
        return langsList;
    }


        
    }
    