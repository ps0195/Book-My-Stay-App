import java.io.*;
import java.util.*;

// Reservation
class Reservation implements Serializable {
    String id;
    String guestName;
    String roomType;

    Reservation(String id, String guestName, String roomType) {
        this.id = id;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return id + " | " + guestName + " | " + roomType;
    }
}

// Inventory
class Inventory implements Serializable {
    Map<String, Integer> rooms = new HashMap<>();

    Inventory() {
        rooms.put("DELUXE", 2);
        rooms.put("STANDARD", 3);
    }

    void decrease(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    void increase(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    @Override
    public String toString() {
        return rooms.toString();
    }
}

// Booking System
class BookingSystem implements Serializable {
    Inventory inventory;
    List<Reservation> bookings;

    BookingSystem() {
        inventory = new Inventory();
        bookings = new ArrayList<>();
    }

    void book(String id, String guest, String type) {
        Reservation r = new Reservation(id, guest, type);
        bookings.add(r);
        inventory.decrease(type);
        System.out.println("Booked: " + r);
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE = "booking_data.ser";

    static void save(BookingSystem system) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(system);
            System.out.println("System state saved.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    static BookingSystem load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            BookingSystem system = (BookingSystem) ois.readObject();
            System.out.println("System state restored.");
            return system;
        } catch (FileNotFoundException e) {
            System.out.println("No previous state found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error restoring state: " + e.getMessage());
        }
        return new BookingSystem();
    }
}

// Main
public class Main {
    public static void main(String[] args) {

        // Load previous state
        BookingSystem system = PersistenceService.load();

        // Simulate bookings
        system.book("R1", "Arun", "DELUXE");
        system.book("R2", "Divya", "STANDARD");

        // Show current state
        System.out.println("Current Inventory: " + system.inventory);
        System.out.println("Bookings: " + system.bookings);

        // Save state
        PersistenceService.save(system);
    }
}