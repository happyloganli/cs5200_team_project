package game.model;

public class ItemStack {
    private int itemStackID;
    private InventorySlot inventorySlot;
    private Item item;
    private int quantity;
	public ItemStack(int itemStackID, InventorySlot inventorySlot, Item item, int quantity) {
		super();
		this.itemStackID = itemStackID;
		this.inventorySlot = inventorySlot;
		this.item = item;
		this.quantity = quantity;
	}
	public int getItemStackID() {
		return itemStackID;
	}
	public void setItemStackID(int itemStackID) {
		this.itemStackID = itemStackID;
	}
	public InventorySlot getInventorySlot() {
		return inventorySlot;
	}
	public void setInventorySlot(InventorySlot inventorySlot) {
		this.inventorySlot = inventorySlot;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
}
