package game.model;

public class Consumable extends Item {
    private String description;

	public Consumable(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer vendorPrice,
			int itemLevel, String description) {
		super(itemID, itemName, maxStackSize, isSellable, vendorPrice, itemLevel);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
