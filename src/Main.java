import java.util.LinkedList;
import java.util.Queue;

// Reservation class represents a guest booking request
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayRequest() {
        System.out.println("Guest: " + guestName + " requested " + roomType + " room");
    }
}

// Booking Request Queue
class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // View all requests in queue
    public void displayRequests() {
        System.out.println("\nCurrent Booking Requests (FIFO Order):");

        for (Reservation r : requestQueue) {
            r.displayRequest();
        }
    }

    // Get next request for future processing
    public Reservation getNextRequest() {
        return requestQueue.peek();
    }
}

// Main Application
public class Main {

    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Guests submit booking requests
        Reservation r1 = new Reservation("Alice", "Single");
        Reservation r2 = new Reservation("Bob", "Double");
        Reservation r3 = new Reservation("Charlie", "Suite");

        // Add requests to queue
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queued requests
        bookingQueue.displayRequests();

        // Peek next request to be processed later
        System.out.println("\nNext request to process:");
        bookingQueue.getNextRequest().displayRequest();
    }
}