package travel.src.com;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class ActivityTest {
    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = new Activity("Hiking", "Enjoy the scenic views", 50.0, 1);
    }

    @Test
    void getName() {
        Assertions.assertEquals("Hiking", activity.getName());
    }

    @Test
    void getDescription() {
        Assertions.assertEquals("Enjoy the scenic views", activity.getDescription());
    }

    @Test
    void getCost() {
        Assertions.assertEquals(50.0, activity.getCost());
    }

    @Test
    void getCapacity() {
        Assertions.assertEquals(1, activity.getCapacity());
    }

    @Test
    void getEnrolledPassengers() {
        Assertions.assertEquals(0, activity.getEnrolledPassengers());
    }

    @Test
    void isFull() {
        Assertions.assertFalse(activity.isFull());
        activity.enrollPassenger();
        Assertions.assertTrue(activity.isFull());
    }

    @Test
    void enrollPassenger() {
        Assertions.assertTrue(activity.enrollPassenger());
        Assertions.assertFalse(activity.enrollPassenger());
    }
}

class DestinationTest {
    private Destination destination;
    private Activity activity;

    @BeforeEach
    void setUp() {
        destination = new Destination("Vagator Beach");
        activity = new Activity("Scuba Diving", "Explore underwater world", 100.0, 5);
        destination.addActivity(activity);
    }

    @Test
    void getName() {
        Assertions.assertEquals("Vagator Beach", destination.getName());
    }

    @Test
    void getActivities() {
        List<Activity> activities = destination.getActivities();
        Assertions.assertEquals(1, activities.size());
        Assertions.assertEquals(activity, activities.get(0));
    }

    @Test
    void addActivity() {
        Activity newActivity = new Activity("Parasailing", "Fly high in the sky", 200.0, 3);
        destination.addActivity(newActivity);
        Assertions.assertEquals(2, destination.getActivities().size());
        Assertions.assertEquals(newActivity, destination.getActivities().get(1));
    }
}

class PassengerTest {
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = new Passenger("Alice", 1, PassengerType.PREMIUM);
    }

    @Test
    void getName() {
        Assertions.assertEquals("Alice", passenger.getName());
    }

    @Test
    void getPassengerNumber() {
        Assertions.assertEquals(1, passenger.getPassengerNumber());
    }

    @Test
    void getMoney() {
        Assertions.assertEquals(Double.POSITIVE_INFINITY, passenger.getMoney());
    }

    @Test
    void getPassengerType() {
        Assertions.assertEquals(PassengerType.PREMIUM, passenger.getPassengerType());
    }

    @Test
    void deductMoney() {
        passenger.deductMoney(50.0);
        Assertions.assertEquals(Double.POSITIVE_INFINITY - 50.0, passenger.getMoney());
    }

    @Test
    void getSignedActivities() {
        Assertions.assertTrue(passenger.getSignedActivities().isEmpty());
    }

    @Test
    void signUpForActivity() {
        Activity activity = new Activity("Hiking", "Enjoy the scenic views", 50.0, 1);
        passenger.signUpForActivity(activity, passenger);
        Assertions.assertEquals(1, passenger.getSignedActivities().size());
    }
}

class TravelPackageTest {
    private TravelPackage travelPackage;
    private Destination destination;
    private Activity activity;
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        travelPackage = new TravelPackage("Goa Package", 20);
        destination = new Destination("Vagator Beach");
        activity = new Activity("Scuba Diving", "Explore underwater world", 100.0, 5);
        passenger = new Passenger("Alice", 1, PassengerType.PREMIUM);

        destination.addActivity(activity);
        travelPackage.addDestination(destination);
        travelPackage.addPassenger(passenger);
    }

    @Test
    void getName() {
        Assertions.assertEquals("Goa Package", travelPackage.getName());
    }

    @Test
    void getPassengerCapacity() {
        Assertions.assertEquals(20, travelPackage.getPassengerCapacity());
    }

    @Test
    void getPassengers() {
        List<Passenger> passengers = travelPackage.getPassengers();
        Assertions.assertEquals(1, passengers.size());
        Assertions.assertEquals(passenger, passengers.get(0));
    }

    @Test
    void getItinerary() {
        List<Destination> itinerary = travelPackage.getItinerary();
        Assertions.assertEquals(1, itinerary.size());
        Assertions.assertEquals(destination, itinerary.get(0));
    }

    @Test
    void addDestination() {
        Destination newDestination = new Destination("DudhSagar Falls");
        travelPackage.addDestination(newDestination);
        Assertions.assertEquals(2, travelPackage.getItinerary().size());
        Assertions.assertEquals(newDestination, travelPackage.getItinerary().get(1));
    }

    @Test
    void addPassenger() {
        Passenger newPassenger = new Passenger("Bob", 2, PassengerType.GOLD);
        travelPackage.addPassenger(newPassenger);
        Assertions.assertEquals(2, travelPackage.getPassengers().size());
        Assertions.assertEquals(newPassenger, travelPackage.getPassengers().get(1));
    }
}




