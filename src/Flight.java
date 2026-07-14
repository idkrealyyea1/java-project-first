import java.util.ArrayList;

public class Flight extends status {
    private static int numofFlights ;
    private static int ided ;
    private int id ;
    private String destination ;
    private int numberofchairs;
    private Flighter nameofCaptin ;
    private ArrayList<Passengere> passengersList ;
    private FlightStatus flightStatus;
    ArrayList<modiefoun> copiloList ;


    public Flight(String destination, ArrayList<Passengere> passengersList, Flighter nameofCaptin,ArrayList<modiefoun> copiloList, int numberofchairs) {

        this(numberofchairs, nameofCaptin);
        if (copiloList == null) {
            copiloList = new ArrayList<>();
        }

        

        ++numofFlights ;
        this.copiloList = copiloList;
        
        this.destination = destination ;
        this.id = (this.id + (++this.ided)) ;
    
        this.passengersList  = passengersList ;        
        this.flightStatus = FlightStatus.SCHEDULED;
        }

        public void setFlightStatus(FlightStatus flightStatus) {
            this.flightStatus = flightStatus;
        }

        public boolean bookPassenger(Passengere passenger) {
        if (passengersList.size() < numberofchairs) {
            passengersList.add(passenger);
            return true;
        }
        else
        System.out.println("عذراً، الرحلة ممتلئة!");
        return false;
    }


    
    

    

    public int getId() {
            return id;
        }

    public ArrayList<modiefoun> getCopiloList() {
            return copiloList;
        }

    public static int getNumofFlights() {
            return numofFlights;
        }

        public String getDestination() {
            return destination;
        }

        public int getNumberofchairs() {
            return numberofchairs;
        }

        public Flighter getNameofCaptin() {
            return nameofCaptin;
        }

        public ArrayList<Passengere> getPassengersList() {
            return passengersList;
        }

        public FlightStatus getFlightStatus() {
            return flightStatus;
        }

    public Flight(int numberofchairs, Flighter nameofCaptin){
        this.nameofCaptin = nameofCaptin ;
        this.numberofchairs = numberofchairs ;
    }

    
    public int getnumberofflights() {
        return numofFlights;
    }
    @Override
    public String toString() {
        return "Flight ID: " + id + ", Destination: " + destination + ", Number of Chairs: " + numberofchairs +", Captain: " + nameofCaptin.getName() + ", Flight Status: " + flightStatus;
                
    }

    public int getnumofpassengeres(){
        
        return passengersList.size() ;
    }

    public String getarrayofpassengerString(){
        return this.passengersList.toString() ;
        
    }
    

}
