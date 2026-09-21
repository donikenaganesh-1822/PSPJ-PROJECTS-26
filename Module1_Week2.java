import java.util.Scanner;
public class Module1_Week2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Emergency Donor Registration Intake Shell ===");
        
        System.out.print("Enter Donor Full Name : ");
        String donorName = scanner.nextLine();
        
        System.out.print("Enter Blood Type : ");
        String bloodType = scanner.next();
        
        System.out.print("Enter Donor Age : ");
        int age = scanner.nextInt();
        
        System.out.print("Enter Weight in kg : ");
        double weight = scanner.nextDouble();
        
        System.out.println("\n--- Intake Verification Summary ---");
        System.out.println("Donor registered: " + donorName);
        System.out.println("Blood Group: " + bloodType);
        System.out.println("Age: " + age + " years old");
        System.out.println("Weight: " + weight + " kg");
        
        scanner.close();
    }
}

