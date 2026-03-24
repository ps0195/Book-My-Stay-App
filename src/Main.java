import java.util.*;

// Booking Request
class BookingRequest {
    String guestName;
    String roomType;

    BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (Thread-Safe)
class Inventory {
    private Map<String, Integer> rooms = new HashMap<>();

    Inventory() {
        rooms.put("DELUXE", 2);
    }

    // Critical Section
    public synchronized boolean bookRoom(String roomType) {
        int available = rooms.getOrDefault(roomType, 0);

        if (available > 0) {
            // simulate delay (to expose race condition if unsynchronized)
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            rooms.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public int getAvailable(String type) {
        return rooms.getOrDefault(type, 0);
    }
}

// Shared Booking Queue
class BookingQueue {
    private Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest req) {
        queue.add(req);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// Worker Thread
class BookingProcessor extends Thread {
    private BookingQueue queue;
    private Inventory inventory;

    BookingProcessor(BookingQueue queue, Inventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {
        while (true) {
            BookingRequest req = queue.getRequest();
            if (req == null) break;

            boolean success = inventory.bookRoom(req.roomType);

            if (success) {
                System.out.println(req.guestName + " booked " + req.roomType);
            } else {
                System.out.println(req.guestName + " failed (no rooms)");
            }
        }
    }
}

// Main
public class Main {
    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        BookingQueue queue = new BookingQueue();

        // Simulate concurrent requests
        queue.addRequest(new BookingRequest("Arun", "DELUXE"));
        queue.addRequest(new BookingRequest("Divya", "DELUXE"));
        queue.addRequest(new BookingRequest("Kumar", "DELUXE"));

        // Multiple threads (guests)
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {}

        System.out.println("Final Available Rooms: " + inventory.getAvailable("DELUXE"));
    }
}