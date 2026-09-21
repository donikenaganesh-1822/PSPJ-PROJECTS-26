import java.util.Scanner;

public class Module2_Week4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("---  MAIN BLOOD SYSTEM CONTROL PANEL ---");
            System.out.println("1. Register Live Donation");
            System.out.println("2. Simulate Emergency Matching Cycle");
            System.out.println("3. Run Diagnostics Loop");
            System.out.println("4. Shut Down Terminal Interface");
            System.out.print("Select Menu Reference Action (1-4): ");
            option = scanner.nextInt();
             if (option == 4) {
                System.out.println("Safely closing inventory interfaces. Goodbye!");
                break; 
            }
            switch (option) {
                case 1:
                    System.out.println("[Action Log]: Executing Donor Registration System Routine...");
                    break;
                case 2:
                    System.out.println("[Action Log]: Initializing Cross-Match Matrix Systems...");
                    break;
                case 3:
                    System.out.println("[Action Log]: Running structural countdown loops...");

                    for (int i = 3; i > 0; i--) {
                        if (i == 2) {
                            System.out.println("   Testing branch bypass (continue state)...");
                            continue;
                        }
                        System.out.println("   Diagnostic Pulse Check " + i + " OK.");
                    }
                    break;
                default:
                    System.out.println(" Unrecognized action index. Select alternative.");
            }
        } while (option != 4);
        
        scanner.close();
    }
}
