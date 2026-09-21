import java.util.Scanner;

public class Module2_Week3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Medical Eligibility & Compatibility Dashboard ===");
        
        System.out.print("Enter Donor Age: ");
        int age = scanner.nextInt();
        
        if (age >= 18) {
            if (age <= 65) {
                System.out.print("Enter Weight (kg): ");
                double weight = scanner.nextDouble();
                
                if (weight >= 50.0) {
                    System.out.println(" Status: Medically Approved for Donation.");
                } else {
                    System.out.println(" Status: Rejected. Weight must be at least 50kg.");
                }
            } else {
                System.out.println(" Status: Rejected. Age exceeds maximum limit of 65.");
            }
        } else {
            System.out.println(" Status: Rejected. Donor must be an adult (18+).");
        }
        
        System.out.print("\nEnter Patient Blood Type to check compatibility rules: ");
        String bType = scanner.next().toUpperCase();
        
        System.out.print("Compatible donors for " + bType + ": ");
        switch (bType) {
            case "O-":
                System.out.println("Only O- (Universal Donor standard).");
                break;
            case "O+":
                System.out.println("O+, O-");
                break;
            case "A+":
                System.out.println("A+, A-, O+, O-");
                break;
            case "AB+":
                System.out.println("Universal Recipient (Can accept all blood types: A, B, AB, O)");
                break;
            default:
                System.out.println("Other standard types (B+, B-, A-, AB- match based on antibody checks).");
                break;
        }
        scanner.close();
    }
}

