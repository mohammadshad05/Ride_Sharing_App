import java.util.*;

// User class representing Riders and Drivers
class User {
    String name;
    String userType; // Rider or Driver

    public User(String name, String userType) {
        this.name = name;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return name + " (" + userType + ")";
    }
}

// Ride class representing a Ride request
class Ride {
    static int idCounter = 1;
    int rideId;
    User rider;
    User driver;
    String pickup;
    String drop;
    boolean isCompleted;

    public Ride(User rider, String pickup, String drop) {
        this.rideId = idCounter++;
        this.rider = rider;
        this.pickup = pickup;
        this.drop = drop;
        this.isCompleted = false;
    }

    public void assignDriver(User driver) {
        this.driver = driver;
    }

    public void completeRide() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Ride " + rideId + ": " + rider.name + " from " + pickup + " to " + drop +
                (driver != null ? " | Driver: " + driver.name : " | Waiting for driver");
    }
}

// RideSharingApp to manage rides
class RideSharingApp {
    List<User> users = new ArrayList<>();
    List<Ride> rides = new ArrayList<>();

    public void registerUser(String name, String userType) {
        users.add(new User(name, userType));
    }

    public void requestRide(String riderName, String pickup, String drop) {
        for (User user : users) {
            if (user.name.equals(riderName) && user.userType.equals("Rider")) {
                Ride newRide = new Ride(user, pickup, drop);
                rides.add(newRide);
                System.out.println("Ride requested: " + newRide);
                return;
            }
        }
        System.out.println("Rider not found!");
    }

    public void assignDriver(String driverName, int rideId) {
        for (User user : users) {
            if (user.name.equals(driverName) && user.userType.equals("Driver")) {
                for (Ride ride : rides) {
                    if (ride.rideId == rideId && ride.driver == null) {
                        ride.assignDriver(user);
                        System.out.println("Driver assigned: " + ride);
                        return;
                    }
                }
            }
        }
        System.out.println("Driver not found or ride already assigned!");
    }

    public void completeRide(int rideId) {
        for (Ride ride : rides) {
            if (ride.rideId == rideId && ride.driver != null && !ride.isCompleted) {
                ride.completeRide();
                System.out.println("Ride completed: " + ride);
                return;
            }
        }
        System.out.println("Invalid ride completion request!");
    }

    public void showAvailableRides() {
        for (Ride ride : rides) {
            if (ride.driver == null) {
                System.out.println(ride);
            }
        }
    }
}

// Main class to test functionality
public class Main {
    public static void main(String[] args) {
        RideSharingApp app = new RideSharingApp();

        // Register users
        app.registerUser("Alice", "Rider");
        app.registerUser("Bob", "Driver");
        app.registerUser("Charlie", "Rider");
        app.registerUser("David", "Driver");

        // Request rides
        app.requestRide("Alice", "Point A", "Point B");
        app.requestRide("Charlie", "Point C", "Point D");

        // Show available rides
        System.out.println("Available rides:");
        app.showAvailableRides();

        // Assign drivers
        app.assignDriver("Bob", 1);
        app.assignDriver("David", 2);

        // Complete a ride
        app.completeRide(1);
    }
}
