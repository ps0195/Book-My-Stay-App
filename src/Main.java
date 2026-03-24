import java.util.*;

// Core Reservation (unchanged)
class Reservation {
    String id;
    double basePrice;

    Reservation(String id, double basePrice) {
        this.id = id;
        this.basePrice = basePrice;
    }
}

// Add-On Service
class AddOnService {
    String name;
    double price;

    AddOnService(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

// Manager
class AddOnServiceManager {
    Map<String, List<AddOnService>> map = new HashMap<>();

    void addService(String resId, AddOnService service) {
        map.computeIfAbsent(resId, k -> new ArrayList<>()).add(service);
    }

    double getTotalCost(String resId) {
        double total = 0;
        List<AddOnService> list = map.get(resId);
        if (list != null) {
            for (AddOnService s : list) {
                total += s.price;
            }
        }
        return total;
    }
}

// Main
public class Main {
    public static void main(String[] args) {
        Reservation r = new Reservation("R1", 5000);

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService("R1", new AddOnService("Breakfast", 500));
        manager.addService("R1", new AddOnService("Pickup", 1000));

        double extra = manager.getTotalCost("R1");

        System.out.println("Base Price: " + r.basePrice);
        System.out.println("Extra Cost: " + extra);
        System.out.println("Total Price: " + (r.basePrice + extra));
    }
}