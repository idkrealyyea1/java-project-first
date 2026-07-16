import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class DataStorage {

    private static final String PILOTS_FILE = "pilots.txt";
    private static final String COPILOTS_FILE = "copilots.txt";
    private static final String FLIGHTS_FILE = "flights.txt";
    private static final String PASSENGERS_FILE = "passengers.txt";
    private static final String EMPLOYEES_FILE = "employees.txt";
    private static final String FLIGHTS_LOG_FILE = "flights-log.txt";

    public static void saveAll() {
        savePilots();
        saveCopilots();
        saveFlights();
        savePassengers();
        saveEmployees();
    }

    public static void loadAll() {
        loadPilots();
        loadCopilots();
        loadFlights();
        loadPassengers();
        loadEmployees();
    }

    // =============================================
    //  PILOTS
    // =============================================
    public static void savePilots() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Flighter p : adding.flightersList) {
                sb.append(escape(p.getName())).append("|")
                  .append(escape(p.getIdentity())).append("|")
                  .append(escape(p.getNationality())).append("|")
                  .append(p.getSalary()).append("|")
                  .append(p.getHours()).append("|")
                  .append(escape(String.join(",", p.getRokhasList()))).append("\n");
            }
            Files.writeString(Path.of(PILOTS_FILE), sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPilots() {
        try {
            Path path = Path.of(PILOTS_FILE);
            if (!Files.exists(path)) return;

            int maxId = 0;
            for (String line : Files.readAllLines(path)) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                String name = unescape(parts[0]);
                String identity = unescape(parts[1]);
                String nationality = unescape(parts[2]);
                double salary = parseDouble(parts[3]);
                double hours = parseDouble(parts[4]);
                ArrayList<String> licenses = new ArrayList<>();
                if (parts.length > 5 && !unescape(parts[5]).isEmpty()) {
                    for (String l : unescape(parts[5]).split(",")) {
                        licenses.add(l.trim());
                    }
                }

                Flighter pilot = new Flighter(name, identity, nationality, salary, hours, licenses);
                if (parts.length > 6) {
                    int id = parseInt(parts[6]);
                    pilot.setId(id);
                    if (id > maxId) maxId = id;
                }
                adding.flightersList.add(pilot);
            }
            Flighter.resetIdCounter(maxId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =============================================
    //  CO-PILOTS
    // =============================================
    public static void saveCopilots() {
        try {
            StringBuilder sb = new StringBuilder();
            for (modiefoun c : adding.copilots) {
                sb.append(escape(c.getName())).append("|")
                  .append(escape(c.getIdentity())).append("|")
                  .append(escape(c.getNationality())).append("|")
                  .append(c.getSalary()).append("|")
                  .append(c.getHours()).append("|")
                  .append(escape(String.join(",", c.getLangsList()))).append("\n");
            }
            Files.writeString(Path.of(COPILOTS_FILE), sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadCopilots() {
        try {
            Path path = Path.of(COPILOTS_FILE);
            if (!Files.exists(path)) return;

            int maxId = 0;
            for (String line : Files.readAllLines(path)) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                String name = unescape(parts[0]);
                String identity = unescape(parts[1]);
                String nationality = unescape(parts[2]);
                double salary = parseDouble(parts[3]);
                double hours = parseDouble(parts[4]);
                ArrayList<String> languages = new ArrayList<>();
                if (parts.length > 5 && !unescape(parts[5]).isEmpty()) {
                    for (String l : unescape(parts[5]).split(",")) {
                        languages.add(l.trim());
                    }
                }

                modiefoun copilot = new modiefoun(name, identity, nationality, salary, languages, hours);
                if (parts.length > 6) {
                    int id = parseInt(parts[6]);
                    copilot.setId(id);
                    if (id > maxId) maxId = id;
                }
                adding.copilots.add(copilot);
            }
            modiefoun.resetIdCounter(maxId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =============================================
    //  FLIGHTS
    // =============================================
    public static void saveFlights() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Flight f : adding.flightslists) {
                String captainName = f.getNameofCaptin() != null ? f.getNameofCaptin().getName() : "";
                StringBuilder copilotNames = new StringBuilder();
                if (f.getCopiloList() != null) {
                    for (int i = 0; i < f.getCopiloList().size(); i++) {
                        if (i > 0) copilotNames.append(",");
                        copilotNames.append(escape(f.getCopiloList().get(i).getName()));
                    }
                }
                int passengerCount = (f.getPassengersList() != null) ? f.getPassengersList().size() : 0;

                sb.append(f.getId()).append("|")
                  .append(escape(f.getDestination())).append("|")
                  .append(escape(captainName)).append("|")
                  .append(f.getNumberofchairs()).append("|")
                  .append(f.getFlightStatus()).append("|")
                  .append(copilotNames.toString()).append("|")
                  .append(passengerCount).append("\n");
            }
            Files.writeString(Path.of(FLIGHTS_FILE), sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFlights() {
        try {
            Path path = Path.of(FLIGHTS_FILE);
            if (!Files.exists(path)) return;

            int maxId = 0;
            for (String line : Files.readAllLines(path)) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 6) continue;

                int id = parseInt(parts[0]);
                String destination = unescape(parts[1]);
                String captainName = unescape(parts[2]);
                int seats = parseInt(parts[3]);
                String statusStr = unescape(parts[4]);

                Flighter captain = null;
                for (Flighter p : adding.flightersList) {
                    if (p.getName().equalsIgnoreCase(captainName)) {
                        captain = p;
                        break;
                    }
                }

                ArrayList<modiefoun> copilotList = new ArrayList<>();
                if (parts.length > 5 && !unescape(parts[5]).isEmpty()) {
                    String[] copilotNames = unescape(parts[5]).split(",");
                    for (String cname : copilotNames) {
                        for (modiefoun c : adding.copilots) {
                            if (c.getName().equalsIgnoreCase(cname.trim())) {
                                copilotList.add(c);
                                break;
                            }
                        }
                    }
                }

                ArrayList<Passengere> passengerList = new ArrayList<>();
                Flight flight = new Flight(destination, passengerList, captain, copilotList, seats);
                flight.setId(id);
                try {
                    flight.setFlightStatus(status.FlightStatus.valueOf(statusStr));
                } catch (Exception e) {
                    flight.setFlightStatus(status.FlightStatus.SCHEDULED);
                }
                adding.flightslists.add(flight);
                if (id > maxId) maxId = id;
            }
            Flight.resetIdCounter(maxId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =============================================
    //  PASSENGERS
    // =============================================
    public static void savePassengers() {
        try {
            StringBuilder sb = new StringBuilder();
            for (Flight f : adding.flightslists) {
                if (f.getPassengersList() != null) {
                    for (Passengere p : f.getPassengersList()) {
                        sb.append(escape(p.getName())).append("|")
                          .append(escape(p.getIdentity())).append("|")
                          .append(escape(p.getNationality())).append("|")
                          .append(escape(p.getPassport())).append("|")
                          .append(escape(p.getPassEndDate())).append("|")
                          .append(escape(p.getDistination())).append("|")
                          .append(f.getId()).append("|")
                          .append(p.getId()).append("\n");
                    }
                }
            }
            Files.writeString(Path.of(PASSENGERS_FILE), sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadPassengers() {
        try {
            Path path = Path.of(PASSENGERS_FILE);
            if (!Files.exists(path)) return;

            int maxId = 0;
            for (String line : Files.readAllLines(path)) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 7) continue;

                Passengere p = new Passengere(
                    unescape(parts[0]), unescape(parts[1]), unescape(parts[2]),
                    unescape(parts[3]), unescape(parts[4]), unescape(parts[5])
                );

                if (parts.length > 7) {
                    int pId = parseInt(parts[7]);
                    p.setId(pId);
                    if (pId > maxId) maxId = pId;
                }

                int flightId = parseInt(parts[6]);
                for (Flight f : adding.flightslists) {
                    if (f.getId() == flightId) {
                        if (f.getPassengersList() == null) {
                            f.setPassengersList(new ArrayList<>());
                        }
                        f.getPassengersList().add(p);
                        break;
                    }
                }
            }
            Passengere.resetIdCounter(maxId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =============================================
    //  NORMAL EMPLOYEES
    // =============================================
    public static void saveEmployees() {
        try {
            StringBuilder sb = new StringBuilder();
            for (normalemployee e : adding.normalEmployees) {
                sb.append(escape(e.getName())).append("|")
                  .append(escape(e.getIdentity())).append("|")
                  .append(escape(e.getNationality())).append("|")
                  .append(e.getSalary()).append("\n");
            }
            Files.writeString(Path.of(EMPLOYEES_FILE), sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadEmployees() {
        try {
            Path path = Path.of(EMPLOYEES_FILE);
            if (!Files.exists(path)) return;

            int maxId = 0;
            for (String line : Files.readAllLines(path)) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 4) continue;

                String name = unescape(parts[0]);
                String identity = unescape(parts[1]);
                String nationality = unescape(parts[2]);
                double salary = parseDouble(parts[3]);

                normalemployee emp = new normalemployee(name, identity, nationality, salary);
                if (parts.length > 4) {
                    int id = parseInt(parts[4]);
                    emp.setId(id);
                    if (id > maxId) maxId = id;
                }
                adding.normalEmployees.add(emp);
            }
            normalemployee.resetIdCounter(maxId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =============================================
    //  FLIGHTS LOG (human-readable)
    // =============================================
    public static void writeFlightsLog() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("========================================\n");
            sb.append("       FLIGHTS LOG\n");
            sb.append("       Generated: ").append(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
            sb.append("========================================\n\n");
            sb.append("Total Flights: ").append(adding.flightslists.size()).append("\n\n");

            for (Flight f : adding.flightslists) {
                int passengerCount = (f.getPassengersList() != null) ? f.getPassengersList().size() : 0;
                int copilotCount = (f.getCopiloList() != null) ? f.getCopiloList().size() : 0;

                sb.append("--- Flight #").append(f.getId()).append(" ---\n");
                sb.append("  Destination:    ").append(f.getDestination()).append("\n");
                sb.append("  Captain:        ").append(f.getNameofCaptin() != null ? f.getNameofCaptin().getName() : "N/A").append("\n");
                sb.append("  Co-Pilots:      ").append(copilotCount).append("\n");
                sb.append("  Seats:          ").append(f.getNumberofchairs()).append("\n");
                sb.append("  Passengers:     ").append(passengerCount).append("\n");
                sb.append("  Status:         ").append(f.getFlightStatus()).append("\n");

                if (passengerCount > 0) {
                    sb.append("  Passenger List:\n");
                    for (int i = 0; i < f.getPassengersList().size(); i++) {
                        Passengere p = f.getPassengersList().get(i);
                        sb.append("    ").append(i + 1).append(". ").append(p.getName())
                          .append(" (").append(p.getIdentity()).append(") - ").append(p.getNationality()).append("\n");
                    }
                }
                sb.append("\n");
            }

            Files.writeString(Path.of(FLIGHTS_LOG_FILE), sb.toString());
            System.out.println("Flights log saved to " + FLIGHTS_LOG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =============================================
    //  HELPERS
    // =============================================
    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("|", "/").replace("\n", " ");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.trim();
    }

    private static double parseDouble(String s) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
