import java.util.*;

// Custom Exception
class InvalidBookingException extends Exception {
    InvalidBookingException(String message) {
        super(message);
    }
}

// Inventory (room availability)
class Inventory {
    private Map<String, Integer> rooms = new HashMap<>();

    Inventory() {
        rooms.put("DELUXE", 2);
        rooms.put("STANDARD", 3);
    }

    boolean isValidRoomType(String type) {
        return rooms.containsKey(type);
    }

    int getAvailable(String type) {
        return rooms.getOrDefault(type, 0);
    }

    void reduceRoom(String type) throws InvalidBookingException {
        int available = getAvailable(type);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + type);
        }
        rooms.put(type, available - 1);
    }
}

// Validator (Fail-Fast)
class BookingValidator {
    static void validate(String roomType, Inventory inventory) throws InvalidBookingException {

        if (roomType == null || roomType.isEmpty()) {
            throw new InvalidBookingException("Room type cannot be empty");
        }

        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.getAvailable(roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + roomType);
        }
    }
}

// Booking Service
class BookingService {
    private Inventory inventory;

    BookingService(Inventory inventory) {
        this.inventory = inventory;
    }

    void bookRoom(String guestName, String roomType) {
        try {
            // Validate first (Fail-Fast)
            BookingValidator.validate(roomType, inventory);

            // Process booking
            inventory.reduceRoom(roomType);

            System.out.println("Booking successful for " + guestName + " (" + roomType + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
}

// Main
public class Main {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        BookingService service = new BookingService(inventory);

        // Valid booking
        service.bookRoom("Arun", "DELUXE");

        // Invalid room type
        service.bookRoom("Divya", "SUITE");

        // Exhaust inventory
        service.bookRoom("Kumar", "DELUXE");
        service.bookRoom("Ravi", "DELUXE"); // should fail

        // Empty input
        service.bookRoom("Meena", "");
    }
}