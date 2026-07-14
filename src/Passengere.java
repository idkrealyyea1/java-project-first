public class Passengere  {
    private static int ided ;
    private int id ;
    private double salary ;
    private String passport, passEndDate, distination ;
    String name, identity, nationality ;
    
    public Passengere(String name, String identity, String nationality, String passport, String passEndDate, String distination) {
        this.name = name ;
        this.identity = identity ;
        this.nationality = nationality ;
        this.id = (this.id + (++this.ided)) ;
        this.passport = passport ;
        this.distination = distination ;
        this.passEndDate = passEndDate ;
    }







    public void setPassport(String passport) {
        this.passport = passport;
    }



    public void setPassEndDate(String passEndDate) {
        this.passEndDate = passEndDate;
    }


    public void setDistination(String distination) {
        this.distination = distination;
    }


    public int getId() {
        return id;
    }


   

    public String getPassport() {
        return passport;
    }


    public String getPassEndDate() {
        return passEndDate;
    }


    public String getDistination() {
        return distination;
    }

    public String getName() {
        return name;
    }


    public String getIdentity() {
        return identity;
    }


    public String getNationality() {
        return nationality;
    }

    


    public void setName(String name) {
        this.name = name;
    }


    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public void setNationality(String nationality) {
        this.nationality = nationality;
    }


    @Override
    public String toString() {
        return "Passengere ID: " + id + ", Name: " + getName() + ", Identity: " + getIdentity() +
                ", Nationality: " + getNationality() + ", Salary: " + salary + ", Passport: " + passport +
                ", Passport End Date: " + passEndDate + ", Destination: " + distination;
    }
    
    


}
