package game.model;

public class Inventory {
    private Chara character;
    private InventorySlot inventorySlot;
    private ItemStack itemStack;
	public Inventory(Chara character, InventorySlot inventorySlot, ItemStack itemStack) {
		super();
		this.character = character;
		this.inventorySlot = inventorySlot;
		this.itemStack = itemStack;
	}
	public Chara getCharacter() {
		return character;
	}
	public void setCharacter(Chara character) {
		this.character = character;
	}
	public InventorySlot getInventorySlot() {
		return inventorySlot;
	}
	public void setInventorySlot(InventorySlot inventorySlot) {
		this.inventorySlot = inventorySlot;
	}
	public ItemStack getItemStack() {
		return itemStack;
	}
	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}
    
}