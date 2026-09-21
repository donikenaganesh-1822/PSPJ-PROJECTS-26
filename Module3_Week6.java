import java.util.Scanner;
public class Module3_Week6 {
    private static String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private static int[] stockLevels = {12, 5, 8, 3, 4, 2, 20, 6};
    public static String[] findCompatibleDonors(String target) {
        switch (target) {
            case "O+":  return new String[]{"O+", "O-"};
            case "O-":  return new String[]{"O-"};
            case "A+":  return new String[]{"A+", "A-", "O+", "O-"};
            case "A-":  return new String[]{"A-", "O-"};
            case "B+":  return new String[]{"B+", "B-", "O+", "O-"};
            case "B-":  return new String[]{"B-", "O-"};
            case "AB+": return new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            case "AB-": return new String[]{"A-", "B-", "AB-", "O-"};
            default:    return new String[]{};
        }
     } public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int commandInput;
        do {
            System.out.println("=======================================================");
            System.out.println(" FULLY COMPILED BLOOD BANK INVENTORY & DONOR SYSTEM ");
            System.out.println("=======================================================");
            System.out.println("1. Display Current Stock Inventory (Arrays)");
            System.out.println("2. Patient Request & Compatibility Matcher (Selection)");
            System.out.println("3. Run Recursive Allocation Math (Recursion)");
            System.out.println("4. Exit Application System Terminal");
            System.out.print("Provide system command option (1-4): ");
            commandInput = scanner.nextInt();
            switch (commandInput) {
                case 1:
                    System.out.println("\n[LIVE INVENTORY LEDGER]");
                    for (int i = 0; i < bloodGroups.length; i++) {
                        System.out.println("🔹 Group Type [" + bloodGroups[i] + "] : " + stockLevels[i] + " bags in stock.");
                    }
                    break;
                case 2:
                    System.out.print("\nEnter Patient Blood Group Required: ");
                    String requestedGroup = scanner.next().toUpperCase();
                    String[] matches = findCompatibleDonors(requestedGroup);

                    if (matches.length == 0) {
                        System.out.println(" Entry Error: Not a globally verified blood category type.");
                        break;
                    }
                    System.out.println("\n🔍 Scanning matching stock options for " + requestedGroup + ":");
                   for (String matchedType : matches) {
                        for (int i = 0; i < bloodGroups.length; i++) {
                            if (bloodGroups[i].equals(matchedType)) {
                                System.out.println(" -> Available Compatible Stock: Type " + bloodGroups[i] + " = " + stockLevels[i] + " units.");
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.println("[RECURSIVE SYSTEM CALCULATION]");
                    System.out.print("Enter current backup blood units to test cascade: ");
                    int initialUnits = scanner.nextInt();
                
                    int result = Module3_Week5.simulateEmergencySupplyCascade(initialUnits, 3);
                    System.out.println("Total projected cascading emergency stock holding: " + result + " units.");
                    break;
                case 4:
                    System.out.println("Terminating operational memory matrices. Shutdown completed smoothly.");
                    break;

                default:
                    System.out.println(" Input execution error. Retrying command cycle.");
            }
        } while (commandInput != 4);
        scanner.close();
    }
}
