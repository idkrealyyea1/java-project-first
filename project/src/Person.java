public abstract class Person {
    private String name;
    private String identity;
    private String nationality;

    public Person(String name,String identity, String nationality ) {
        this.name = name;
        this.identity = identity;
        this.nationality = nationality ;
    }

    public Person() {
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

    public String getName() {
        return name;
    }

    public String getIdentity() {
        return identity;
    }

    public String getNationality() {
        return nationality;
    }

    
}