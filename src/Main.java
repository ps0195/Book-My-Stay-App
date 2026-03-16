// Abstract Room class
abstract class Room {
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(int numberOfBeds, double size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq ft");
        System.out.println("Price: $" + price);
    }
}

// Single Room class
class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }
}

// Double Room class
class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }
}

// Suite Room class
class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 350);
    }

    @Override
    public String getRoomType() {
        return "Suite Room";
    }
}

// Main Application
public class Main {
    public static void main(String[] args) {

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // Display details
        single.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println();

        suite.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);
    }
}