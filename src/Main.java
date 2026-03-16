import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
    private String type;
    private int beds;
    private double price;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: $" + price);
    }
}

// Concrete Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single", 1, 100);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double", 2, 180);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite", 3, 350);
    }
}

// Centralized Room Inventory
class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}

// Search Service (Read-only access)
class RoomSearchService {

    private RoomInventory inventory;
    private HashMap<String, Room> roomCatalog;

    public RoomSearchService(RoomInventory inventory, HashMap<String, Room> roomCatalog) {
        this.inventory = inventory;
        this.roomCatalog = roomCatalog;
    }

    public void searchAvailableRooms() {
        System.out.println("Available Rooms:");

        for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {

            String type = entry.getKey();
            int available = entry.getValue();

            if (available > 0) {
                Room room = roomCatalog.get(type);

                room.displayDetails();
                System.out.println("Available Count: " + available);
                System.out.println();
            }
        }
    }
}

// Main Application
public class Main {

    public static void main(String[] args) {

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Room catalog
        HashMap<String, Room> roomCatalog = new HashMap<>();
        roomCatalog.put("Single", single);
        roomCatalog.put("Double", doubleRoom);
        roomCatalog.put("Suite", suite);

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single", 5);
        inventory.addRoomType("Double", 3);
        inventory.addRoomType("Suite", 0);

        // Search service
        RoomSearchService searchService = new RoomSearchService(inventory, roomCatalog);

        // Guest searches for rooms
        searchService.searchAvailableRooms();
    }
}