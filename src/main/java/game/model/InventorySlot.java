package game.model;

public class InventorySlot {
    private int inventorySlotID;

	public InventorySlot(int inventorySlotID) {
		super();
		this.inventorySlotID = inventorySlotID;
	}

	public int getInventorySlotID() {
		return inventorySlotID;
	}

	public void setInventorySlotID(int inventorySlotID) {
		this.inventorySlotID = inventorySlotID;
	}
    
}