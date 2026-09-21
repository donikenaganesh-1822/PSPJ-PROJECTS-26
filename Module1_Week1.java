public class Module1_Week1 {
    public static void main(String[] args) {
        System.out.println("--- Blood Bank Inventory Init ---");
      
        int stockA_Positive = 15;
        int stockB_Positive = 8;
        int stockO_Negative = 3; 
        
        int totalUnits = stockA_Positive + stockB_Positive + stockO_Negative;
        
        System.out.println("A+ Units: " + stockA_Positive);
        System.out.println("B+ Units: " + stockB_Positive);
        System.out.println("O- Units: " + stockO_Negative);
        System.out.println("Total Initial Stock in Bank: " + totalUnits);
    }
}
