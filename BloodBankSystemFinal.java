import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

interface MedicalValidator {
    void validateEligibility() throws IneligibleDonorException;
}

class IneligibleDonorException extends Exception {
    public IneligibleDonorException(String message) {
        super(message);
    }
}

class Person {
    protected String name;
    protected String phoneNumber;

    public Person(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

class Donor extends Person implements MedicalValidator {
    private String bloodType;
    private int age;
    private double weight;

    public Donor(String name, String phoneNumber, String bloodType, int age, double weight) {
        super(name, phoneNumber);
        this.bloodType = bloodType.trim().toUpperCase();
        this.age = age;
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public void validateEligibility() throws IneligibleDonorException {
        if (age < 18 || age > 65) {
            throw new IneligibleDonorException("Donor age must be between 18 and 65 years.");
        }
        if (weight < 50) {
            throw new IneligibleDonorException("Donor weight must be at least 50 kg.");
        }
        if (!BloodBankSystemFinal.isValidBloodGroup(bloodType)) {
            throw new IneligibleDonorException("Please enter a valid blood group.");
        }
        if (name.trim().isEmpty() || phoneNumber.trim().isEmpty()) {
            throw new IneligibleDonorException("Name and phone number cannot be empty.");
        }
    }

    public String toFileString() {
        return name.replace(",", " ") + "," + phoneNumber.replace(",", " ") + "," + bloodType + "," + age + "," + weight;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Phone: " + phoneNumber + " | Blood Group: " + bloodType + " | Age: " + age + " | Weight: " + weight + " kg";
    }
}

public class BloodBankSystemFinal {
    private static final String[] BLOOD_GROUPS = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private static final int[] DEFAULT_STOCK = {12, 5, 8, 3, 4, 2, 20, 6};
    private static final String DONOR_FILE = "donors_database.txt";
    private static final String STOCK_FILE = "blood_stock.txt";

    private final int[] stockLevels = Arrays.copyOf(DEFAULT_STOCK, DEFAULT_STOCK.length);
    private final List<Donor> donorRegistry = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static boolean isValidBloodGroup(String group) {
        if (group == null) {
            return false;
        }
        for (String bloodGroup : BLOOD_GROUPS) {
            if (bloodGroup.equalsIgnoreCase(group.trim())) {
                return true;
            }
        }
        return false;
    }

    public static String[] findCompatibleBloodGroups(String target) {
        if (target == null) {
            return new String[0];
        }
        switch (target.trim().toUpperCase()) {
            case "O-":
                return new String[]{"O-"};
            case "O+":
                return new String[]{"O+", "O-"};
            case "A-":
                return new String[]{"A-", "O-"};
            case "A+":
                return new String[]{"A+", "A-", "O+", "O-"};
            case "B-":
                return new String[]{"B-", "O-"};
            case "B+":
                return new String[]{"B+", "B-", "O+", "O-"};
            case "AB-":
                return new String[]{"AB-", "A-", "B-", "O-"};
            case "AB+":
                return new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            default:
                return new String[0];
        }
    }

    public static int simulateEmergencySupplyCascade(int units, int levels) {
        if (units <= 0 || levels <= 0) {
            return 0;
        }
        if (levels == 1) {
            return units;
        }
        return units + simulateEmergencySupplyCascade(units / 2, levels - 1);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void showInventory() {
        System.out.println("\nBLOOD BANK INVENTORY");
        for (int i = 0; i < BLOOD_GROUPS.length; i++) {
            System.out.println(BLOOD_GROUPS[i] + " : " + stockLevels[i] + " units");
        }
        int total = 0;
        for (int stock : stockLevels) {
            total += stock;
        }
        System.out.println("Total available units: " + total);
    }

    private void findMatches() {
        System.out.print("Enter patient's blood group: ");
        String target = scanner.nextLine().trim().toUpperCase();
        if (!isValidBloodGroup(target)) {
            System.out.println("Invalid blood group. Use A+, A-, B+, B-, AB+, AB-, O+ or O-.");
            return;
        }
        String[] compatibleGroups = findCompatibleBloodGroups(target);
        boolean foundStock = false;
        System.out.println("Compatible blood stock for " + target + ":");
        for (String group : compatibleGroups) {
            for (int i = 0; i < BLOOD_GROUPS.length; i++) {
                if (BLOOD_GROUPS[i].equals(group)) {
                    System.out.println(group + " : " + stockLevels[i] + " units");
                    if (stockLevels[i] > 0) {
                        foundStock = true;
                    }
                    break;
                }
            }
        }
        List<Donor> matches = donorRegistry.stream()
                .filter(donor -> Arrays.asList(compatibleGroups).contains(donor.getBloodType()))
                .sorted(Comparator.comparing(Donor::getName))
                .collect(Collectors.toList());
        System.out.println("Matching registered donors:");
        if (matches.isEmpty()) {
            System.out.println("No registered donors match this blood group.");
        } else {
            matches.forEach(System.out::println);
        }
        if (!foundStock) {
            System.out.println("No compatible blood units are currently in stock.");
        }
    }

    private void registerDonor() {
        System.out.print("Enter donor name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter blood group: ");
        String bloodType = scanner.nextLine().trim();
        int age = readInt("Enter age: ");
        double weight = readDouble("Enter weight in kg: ");
        Donor donor = new Donor(name, phone, bloodType, age, weight);
        try {
            donor.validateEligibility();
            boolean duplicate = donorRegistry.stream().anyMatch(d -> d.getPhoneNumber().equals(phone));
            if (duplicate) {
                System.out.println("A donor with this phone number is already registered.");
                return;
            }
            donorRegistry.add(donor);
            saveDonorsToFile();
            System.out.println("Donor registered successfully.");
            System.out.println("Blood stock was not changed because registration alone does not confirm a donation.");
        } catch (IneligibleDonorException e) {
            System.out.println("Donor registration rejected: " + e.getMessage());
        }
    }

    private void showDonors() {
        System.out.println("\nREGISTERED DONORS");
        if (donorRegistry.isEmpty()) {
            System.out.println("No donors are registered.");
            return;
        }
        for (int i = 0; i < donorRegistry.size(); i++) {
            System.out.println((i + 1) + ". " + donorRegistry.get(i));
        }
        System.out.println("Total registered donors: " + donorRegistry.size());
    }

    private void updateStock() {
        System.out.println("Available blood groups: " + String.join(", ", BLOOD_GROUPS));
        System.out.print("Enter blood group to update: ");
        String group = scanner.nextLine().trim().toUpperCase();
        if (!isValidBloodGroup(group)) {
            System.out.println("Invalid blood group.");
            return;
        }
        int index = -1;
        for (int i = 0; i < BLOOD_GROUPS.length; i++) {
            if (BLOOD_GROUPS[i].equals(group)) {
                index = i;
                break;
            }
        }
        System.out.println("1. Add units\n2. Remove units");
        int action = readInt("Choose action: ");
        if (action != 1 && action != 2) {
            System.out.println("Invalid action.");
            return;
        }
        int units = readInt("Enter number of units: ");
        if (units <= 0) {
            System.out.println("Units must be greater than zero.");
            return;
        }
        if (action == 1) {
            stockLevels[index] += units;
            System.out.println("Stock updated successfully.");
        } else if (stockLevels[index] < units) {
            System.out.println("Not enough units in stock.");
        } else {
            stockLevels[index] -= units;
            System.out.println("Stock updated successfully.");
        }
        saveStockToFile();
    }

    private void showRareDonorPipeline() {
        System.out.println("\nRARE BLOOD GROUP DONOR PIPELINE");
        List<Donor> rareDonors = donorRegistry.stream()
                .filter(donor -> donor.getBloodType().equals("O-") || donor.getBloodType().equals("AB-"))
                .sorted(Comparator.comparing(Donor::getName))
                .collect(Collectors.toList());
        if (rareDonors.isEmpty()) {
            System.out.println("No O- or AB- donors are registered.");
            return;
        }
        rareDonors.stream().map(donor -> donor.getName().toUpperCase() + " | " + donor.getBloodType() + " | " + donor.getPhoneNumber())
                .forEach(System.out::println);
        System.out.println("Rare donor count: " + rareDonors.size());
    }

    private void saveDonorsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DONOR_FILE))) {
            for (Donor donor : donorRegistry) {
                writer.write(donor.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save donors: " + e.getMessage());
        }
    }

    private void loadDonorsFromFile() {
        File file = new File(DONOR_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1);
                if (parts.length != 5) {
                    continue;
                }
                try {
                    Donor donor = new Donor(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), Double.parseDouble(parts[4]));
                    donor.validateEligibility();
                    boolean duplicate = donorRegistry.stream().anyMatch(d -> d.getPhoneNumber().equals(donor.getPhoneNumber()));
                    if (!duplicate) {
                        donorRegistry.add(donor);
                    }
                } catch (NumberFormatException | IneligibleDonorException e) {
                    System.out.println("Skipped an invalid donor record in the data file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load donors: " + e.getMessage());
        }
    }

    private void saveStockToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_FILE))) {
            for (int i = 0; i < BLOOD_GROUPS.length; i++) {
                writer.write(BLOOD_GROUPS[i] + "," + stockLevels[i]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not save stock: " + e.getMessage());
        }
    }

    private void loadStockFromFile() {
        File file = new File(STOCK_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    continue;
                }
                try {
                    int stock = Integer.parseInt(parts[1].trim());
                    if (stock < 0) {
                        continue;
                    }
                    for (int i = 0; i < BLOOD_GROUPS.length; i++) {
                        if (BLOOD_GROUPS[i].equalsIgnoreCase(parts[0].trim())) {
                            stockLevels[i] = stock;
                            break;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipped an invalid stock record in the data file.");
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load stock: " + e.getMessage());
        }
    }

    private void showMenu() {
        System.out.println("\n===============================================");
        System.out.println("BLOOD BANK INVENTORY & DONOR MATCHER");
        System.out.println("===============================================");
        System.out.println("1. View blood inventory");
        System.out.println("2. Match patient with compatible blood and donors");
        System.out.println("3. Run emergency supply recursion");
        System.out.println("4. Register donor");
        System.out.println("5. View all donors");
        System.out.println("6. Find rare blood group donors using streams");
        System.out.println("7. Update blood stock");
        System.out.println("8. Save and exit");
    }

    public void run() {
        loadDonorsFromFile();
        loadStockFromFile();
        int choice;
        do {
            showMenu();
            choice = readInt("Enter your choice (1-8): ");
            switch (choice) {
                case 1:
                    showInventory();
                    break;
                case 2:
                    findMatches();
                    break;
                case 3:
                    int units = readInt("Enter starting number of units: ");
                    int levels = readInt("Enter number of recursion levels: ");
                    if (units < 0 || levels < 1) {
                        System.out.println("Units must not be negative and levels must be at least one.");
                    } else {
                        System.out.println("Projected total units: " + simulateEmergencySupplyCascade(units, levels));
                    }
                    break;
                case 4:
                    registerDonor();
                    break;
                case 5:
                    showDonors();
                    break;
                case 6:
                    showRareDonorPipeline();
                    break;
                case 7:
                    updateStock();
                    break;
                case 8:
                    saveDonorsToFile();
                    saveStockToFile();
                    System.out.println("Data saved. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice. Please choose a number from 1 to 8.");
                    continue;
            }
        } while (choice != 8);
        scanner.close();
    }

    public static void main(String[] args) {
        BloodBankSystemFinal system = new BloodBankSystemFinal();
        system.run();
    }
}
