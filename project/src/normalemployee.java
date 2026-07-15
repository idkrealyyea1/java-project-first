public class normalemployee extends Employee  {
    private static int ided ;
    private int id ;
    private double salary ;

    public normalemployee(String name, String identity, String nationality, double salary) {
        super(name, identity, nationality);
        this.salary = salary ;
        this.id = (this.id + (++this.ided)) ;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }
    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee " + getId() + " | " + getName() + " | " + getIdentity() + " | " + getNationality() + " | Salary: $" + String.format("%.2f", salary);
    }
}
