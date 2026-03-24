import java.util.*;

// Reservation
class Reservation {
    String id;
    String roomType;
    String roomId;
    boolean isCancelled = false;

    Reservation(String id, String roomType, String roomId) {
        this.id = id;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// Inventory
class Inventory {
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

    int getCount(String type) {
        return rooms.getOrDefault(type, 0);
    }
}

// Booking Store
class BookingStore {
    Map<String, Reservation> bookings = new HashMap<>();

    void add(Reservation r) {
        bookings.put(r.id, r);
    }

    Reservation get(String id) {
        return bookings.get(id);
    }
}

// Cancellation Service
class CancellationService {

    private Inventory inventory;
    private BookingStore store;

    // Stack for rollback (released room IDs)
    private Stack<String> releasedRooms = new Stack<>();

    CancellationService(Inventory inventory, BookingStore store) {
        this.inventory = inventory;
        this.store = store;
    }

    void cancel(String reservationId) {

        Reservation r = store.get(reservationId);

        // Validation
        if (r == null) {
            System.out.println("Cancellation failed: Reservation not found");
            return;
        }

        if (r.isCancelled) {
            System.out.println("Cancellation failed: Already cancelled");
            return;
        }

        // Rollback steps
        releasedRooms.push(r.roomId);              // track released room
        inventory.increase(r.roomType);            // restore inventory
        r.isCancelled = true;                      // update state

        System.out.println("Cancelled booking: " + reservationId);
        System.out.println("Released Room ID: " + r.roomId);
    }
}

// Main
public class Main {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        BookingStore store = new BookingStore();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("R1", "DELUXE", "D101");
        Reservation r2 = new Reservation("R2", "STANDARD", "S201");

        store.add(r1);
        store.add(r2);

        inventory.decrease("DELUXE");
        inventory.decrease("STANDARD");

        CancellationService service = new CancellationService(inventory, store);

        // Valid cancellation
        service.cancel("R1");

        // Duplicate cancellation
        service.cancel("R1");

        // Invalid cancellation
        service.cancel("R3");

        // Check inventory
        System.out.println("DELUXE Available: " + inventory.getCount("DELUXE"));
    }
}