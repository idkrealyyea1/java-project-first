import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class DataStorage {

    private static final String PASSENGERS_FILE = "passengers.txt";
    private static final String FLIGHTS_LOG_FILE = "flights-log.txt";

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
                          .append(f.getId()).append("\n");
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

            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(path));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 7) continue;

                Passengere p = new Passengere(
                    unescape(parts[0]), unescape(parts[1]), unescape(parts[2]),
                    unescape(parts[3]), unescape(parts[4]), unescape(parts[5])
                );

                int flightId = Integer.parseInt(parts[6].trim());
                for (Flight f : adding.flightslists) {
                    if (f.getId() == flightId) {
                        f.getPassengersList().add(p);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("|", "/").replace("\n", " ");
    }

    private static String unescape(String s) {
        if (s == null) return "";
        return s.trim();
    }
}
