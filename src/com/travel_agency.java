//package travel.src;
package travel.src.com;  

import java.util.*;

class Activity {
    private String name;
    private String description;
    private int enrolledPassengers;
    private double cost;
    private int capacity;

    public Activity(String name, String description, double cost, int capacity) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.capacity = capacity;
        this.enrolledPassengers = 0;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getEnrolledPassengers() {
        return enrolledPassengers;
    }

    public boolean isFull() {
        return enrolledPassengers >= capacity;
    }

    public boolean enrollPassenger() {
        if (!isFull()) {
            enrolledPassengers++;
            return true;
        }
        return false;
    }
}

class Destination {
    private String name;
    private List<Activity> activities;

    public Destination(String name) {
        this.name = name;
        this.activities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
}

class Passenger {
    private String name;
    private int passengerNumber;
    private double money;
    private List<Activity> signedActivities;
    private PassengerType type;

    public Passenger(String name, int passengerNumber, PassengerType type) {
        this.name = name;
        this.passengerNumber = passengerNumber;
        this.type = type;
        this.money = type == PassengerType.STANDARD ? 10 : (type == PassengerType.GOLD ? 1000 : Double.POSITIVE_INFINITY);
        //this.money = 1000; // Initial balance
        this.signedActivities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerNumber() {
        return passengerNumber;
    }

    public double getMoney() {
        return money;
    }
    
    public PassengerType getPassengerType() {
        return type;
    }

    public void deductMoney(double amount) {
        money -= amount;
    }

    public List<Activity> getSignedActivities() {
        return signedActivities;
    }

    public void signUpForActivity(Activity activity, Passenger p) {
        signedActivities.add(activity);
        //deductMoney(activity.getCost());
        if(p.type==PassengerType.STANDARD){
            deductMoney(activity.getCost());
        
        }
        else if(p.type==PassengerType.GOLD){
            deductMoney(activity.getCost()*0.9);
        }
    }
    
    public void signUp(Activity a, Passenger p){
        if(!a.isFull() && p.getMoney()>=a.getCost()){
            p.signUpForActivity(a, p);
            a.enrollPassenger();
            System.out.println(p.getName() + " signed up for " + a.getName());
        }
        else{
            if(p.getMoney()<a.getCost()){
                System.out.println(p.getName() + " tried signing up for " +a.getName() + ", but has insufficient balance for the same.");
            }
            else{
                System.out.println(p.getName() + " tried signing up for " + a.getName() + ", but no spot left for this activity.");
            }
        }
    }
}

enum PassengerType {
    STANDARD, GOLD, PREMIUM
}

class TravelPackage {
    private String name;
    private int passengerCapacity;
    private List<Passenger> passengers;
    private List<Destination> itinerary;

    public TravelPackage(String name, int passengerCapacity) {
        this.name = name;
        this.passengerCapacity = passengerCapacity;
        this.passengers = new ArrayList<>();
        this.itinerary = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public List<Destination> getItinerary() {
        return itinerary;
    }

    public void addDestination(Destination destination) {
        itinerary.add(destination);
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public void printItinerary() {
        System.out.println("==================================");
        System.out.println("Travel Package Itinerary: " + name);
        for (Destination destination : itinerary) {
            System.out.println("Destination: " + destination.getName());
            for (Activity activity : destination.getActivities()) {
                System.out.println("Activity: " + activity.getName() + ", Cost: " + activity.getCost() + ", Capacity: " + activity.getCapacity() + ", Description: " + activity.getDescription());
            }
        }
        System.out.println("==================================");
    }

    public void printPassengerList() {
        System.out.println("==================================");
        System.out.println("Passenger List of Travel Package: " + name);
        System.out.println("Passenger Capacity: " + passengerCapacity);
        System.out.println("Number of Passengers Enrolled: " + passengers.size());
        for (Passenger passenger : passengers) {
            System.out.println("Passenger: " + passenger.getName() + ", Passenger Number: " + passenger.getPassengerNumber());
        }
        System.out.println("==================================");
    }
    
    //print passenger details
    public void printPassengerDetails(TravelPackage t) {
        System.out.println("==================================");
        System.out.println("Passenger details:");
        List <Passenger> passengers = t.getPassengers();
        for(Passenger p: passengers){
            System.out.println("Passenger Name: " + p.getName() + ", Passenger No: " + p.getPassengerNumber() + (p.getPassengerType()!=PassengerType.PREMIUM ? ", Balance: " + p.getMoney() : "") + ", Type: " + p.getPassengerType());
            List <Activity> activities = p.getSignedActivities();
            System.out.println("Signed Activities of Passenger " + p.getName() + " are: " );
            for(Activity a: activities){
                System.out.println(a.getName() + ", cost: " + a.getCost());
            }
            System.out.println("--------");
        }
    }
    
    //print Activity details
    public void printActivityDetails(TravelPackage t){
        System.out.println("==================================");
        System.out.println("Activities details:");
        List <Destination> destinations = t.getItinerary();
        for(Destination d: destinations){
            List <Activity> activities = d.getActivities();
            for(Activity a: activities){
                System.out.println("Activity: " + a.getName() + ", Cost: " + a.getCost() + ", Capacity: " + a.getCapacity() + ", Description: " + a.getDescription());
                System.out.println("Empty spaces in " + a.getName() + " activity: " + (a.getCapacity()-a.getEnrolledPassengers()));
            }
        }
    }
}

public class travel_agency {
    public static void main(String[] args) {
        
        // Creating activities
        Activity activity1 = new Activity("Hiking", "Enjoy the scenic views", 50.0, 1);
        Activity activity2 = new Activity("Scuba Diving", "Explore underwater world", 100.0, 5);
        Activity activity3 = new Activity("Boating", "Enjoy speed boating in ocean", 70.0,6);
        Activity activity4 = new Activity("Paragliding", "Glide like a bird", 150.0, 10);
        Activity activity5 = new Activity("Banana Ride", "Sail in water and try to swim", 40.0, 8);
        
        // Creating destinations and adding activities
        Destination destination1 = new Destination("Vagator Beach");
        destination1.addActivity(activity2);
        destination1.addActivity(activity3);
        destination1.addActivity(activity4);
        destination1.addActivity(activity5);
        Destination destination2 = new Destination("DudhSagar Falls");
        destination2.addActivity(activity1);

        // Creating travel package and adding destinations
        TravelPackage travelPackage = new TravelPackage("Goa Package", 20);
        travelPackage.addDestination(destination1);
        travelPackage.addDestination(destination2);

        // Q1 ===  Printing itinerary
        travelPackage.printItinerary();

        //creating passengers
        Passenger p1 = new Passenger("Alice", 1, PassengerType.PREMIUM);
        Passenger p2 = new Passenger("Bob", 2, PassengerType.GOLD);
        Passenger p3 = new Passenger("Prince", 3, PassengerType.STANDARD);

        //signing up passengers for TravelPackage
        travelPackage.addPassenger(p1);
        travelPackage.addPassenger(p2);
        travelPackage.addPassenger(p3);
        
        // Q2 ===  Printing passenger list in travelPackage
        travelPackage.printPassengerList();
        
        //sign up passengers for activities
        p1.signUp(activity1, p1);
        p1.signUp(activity2, p1);
        p1.signUp(activity3, p1);
        p1.signUp(activity4, p1);
        p1.signUp(activity5, p1);
        p2.signUp(activity1, p2);
        p3.signUp(activity5, p3);
        
        //Q3 ===  print passenger details 
        travelPackage.printPassengerDetails(travelPackage);

        //Q4 === print activities details with empty spaces
        travelPackage.printActivityDetails(travelPackage);
    }
}
