public class Module3_Week5 {

    public static int deductBloodUnits(int currentStock, int requestedUnits) {
        if (requestedUnits > currentStock) {
            System.out.println(" Alert: Insufficient inventory. Transaction denied.");
            return currentStock;
        }
        System.out.println("Allocation Authorized. Dispatching units...");
        return currentStock - requestedUnits;
    }
    public static int simulateEmergencySupplyCascade(int unitsAtRisk, int depthCount) {
        if (depthCount <= 1) {
            return unitsAtRisk;
        }
        return unitsAtRisk + simulateEmergencySupplyCascade(unitsAtRisk / 2, depthCount - 1);
    }
    public static void main(String[] args) {
        System.out.println("=== Testing Modular Functional Layouts ===");
        
        int stockLevel = 25;
        stockLevel = deductBloodUnits(stockLevel, 7);
        System.out.println("Updated Inventory Level: " + stockLevel + " units.");
        
        System.out.println("--- Recursive Blood Redistribution Projections ---");
        int totalCascadeProjection = simulateEmergencySupplyCascade(10, 3);
        System.out.println("Recursive System Multi-Tier Allocation Projection: " + totalCascadeProjection + " total bags.");
    }
}
