package game.model;

public class Equipment extends Item {
    private int requiredLevel;

	public Equipment(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer vendorPrice,
			int itemLevel, int requiredLevel) {
		super(itemID, itemName, maxStackSize, isSellable, vendorPrice, itemLevel);
		this.requiredLevel = requiredLevel;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}
    
}
