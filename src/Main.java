import java.util.*;

// Reservation class
class Reservation {
    String id;
    String guestName;
    double amount;

    Reservation(String id, String guestName, double amount) {
        this.id = id;
        this.guestName = guestName;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return id + " | " + guestName + " | ₹" + amount;
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings
    List<Reservation> getAllReservations() {
        return history;
    }
}

// Report Service (read-only operations)
class BookingReportService {

    // Print all bookings
    void printAllBookings(List<Reservation> list) {
        System.out.println("Booking History:");
        for (Reservation r : list) {
            System.out.println(r);
        }
    }

    // Generate summary
    void generateSummary(List<Reservation> list) {
        int totalBookings = list.size();
        double totalRevenue = 0;

        for (Reservation r : list) {
            totalRevenue += r.amount;
        }

        System.out.println("\nSummary Report:");
        System.out.println("Total Bookings: " + totalBookings);
        System.out.println("Total Revenue: ₹" + totalRevenue);
    }
}

// Main
public class Main {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addReservation(new Reservation("R1", "Arun", 5000));
        history.addReservation(new Reservation("R2", "Divya", 6500));
        history.addReservation(new Reservation("R3", "Kumar", 4000));

        // Admin views reports
        BookingReportService report = new BookingReportService();

        List<Reservation> data = history.getAllReservations();

        report.printAllBookings(data);
        report.generateSummary(data);
    }
}