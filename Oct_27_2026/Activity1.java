
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 1. Interface
interface Rentable {
    double calculateRent(int days);
    boolean isAvailable();
}

// 2. Abstract parent class
abstract class Vehicle implements Rentable {
    private String id;
    private String model;
    private double dailyRate;
    private boolean available;

    public Vehicle(String id, String model, double dailyRate) {
        this.id = id;
        this.model = model;
        this.dailyRate = dailyRate;
        this.available = true;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public void markRented() {
        available = false;
    }

    public void markAvailable() {
        available = true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + model
                + " (" + id + ")";
    }
}

// 3. Car class
class Car extends Vehicle {
    public Car(String id, String model, double rate) {
        super(id, model, rate);
    }

    @Override
    public double calculateRent(int days) {
        return getDailyRate() * days;
    }
}

// 4. Bike class
class Bike extends Vehicle {
    public Bike(String id, String model, double rate) {
        super(id, model, rate);
    }

    @Override
    public double calculateRent(int days) {
        double total = getDailyRate() * days;

        if (days >= 7) {
            total = total * 0.90; // 10% discount
        }

        return total;
    }
}

// 5. Truck class
class Truck extends Vehicle {
    private static final double INSURANCE_CHARGE = 500.0;

    public Truck(String id, String model, double rate) {
        super(id, model, rate);
    }

    @Override
    public double calculateRent(int days) {
        return getDailyRate() * days + INSURANCE_CHARGE;
    }
}

// 6. Custom exception
class VehicleNotAvailableException extends Exception {
    public VehicleNotAvailableException(String message) {
        super(message);
    }
}

// 7. Rental class
class Rental {
    private Vehicle vehicle;
    private Customer customer;
    private int days;
    private double totalRent;

    public Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.totalRent = vehicle.calculateRent(days);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    public double getTotalRent() {
        return totalRent;
    }
}

// 8. Customer class
class Customer {
    private String name;
    private List<Rental> rentalHistory = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addRental(Rental rental) {
        rentalHistory.add(rental);
    }

    public List<Rental> getRentalHistory() {
        return Collections.unmodifiableList(rentalHistory);
    }
}

// 9. RentalShop class
class RentalShop {
    private Map<String, Vehicle> fleet = new LinkedHashMap<>();

    public void addVehicle(Vehicle vehicle) {
        fleet.put(vehicle.getId(), vehicle);
    }

    public Rental rent(String vehicleId, Customer customer, int days)
            throws VehicleNotAvailableException {

        if (days <= 0) {
            throw new IllegalArgumentException(
                    "Rental days must be greater than zero");
        }

        Vehicle vehicle = fleet.get(vehicleId);

        if (vehicle == null) {
            throw new VehicleNotAvailableException(
                    "Vehicle " + vehicleId + " not found");
        }

        if (!vehicle.isAvailable()) {
            throw new VehicleNotAvailableException(
                    vehicle + " is already rented");
        }

        Rental rental = new Rental(vehicle, customer, days);

        vehicle.markRented();
        customer.addRental(rental);

        return rental;
    }

    public void giveBack(String vehicleId)
            throws VehicleNotAvailableException {

        Vehicle vehicle = fleet.get(vehicleId);

        if (vehicle == null) {
            throw new VehicleNotAvailableException(
                    "Vehicle " + vehicleId + " not found");
        }

        if (vehicle.isAvailable()) {
            throw new VehicleNotAvailableException(
                    vehicle + " is not currently rented");
        }

        vehicle.markAvailable();
    }

    public void showAvailable() {
        System.out.println("Available vehicles:");

        for (Vehicle vehicle : fleet.values()) {
            if (vehicle.isAvailable()) {
                System.out.println("  " + vehicle);
            }
        }
    }
}

// 10. Main class
public class Activity1 {
    public static void main(String[] args) {
        RentalShop shop = new RentalShop();

        Car car = new Car("C1", "Swift", 1500);
        Bike bike = new Bike("B1", "Activa", 400);
        Truck truck = new Truck("T1", "Tata Ace", 2500);

        shop.addVehicle(car);
        shop.addVehicle(bike);
        shop.addVehicle(truck);

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");

        try {
            Rental r1 = shop.rent("B1", asha, 8);
            printRental(r1);

            Rental r2 = shop.rent("T1", ravi, 2);
            printRental(r2);

            // This fails because B1 is already rented.
            try {
                shop.rent("B1", ravi, 3);
            } catch (VehicleNotAvailableException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Return only the bike; the truck remains rented.
            shop.giveBack("B1");

            shop.showAvailable();

        } catch (VehicleNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printRental(Rental rental) {
        System.out.printf(
                "%s rented %s for %d days = %.2f%n",
                rental.getCustomer().getName(),
                rental.getVehicle(),
                rental.getDays(),
                rental.getTotalRent()
        );
    }
}
