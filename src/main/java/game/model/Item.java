package game.model;

public class Item {
    private int itemID;
    private String itemName;
    private int maxStackSize;
    private boolean isSellable;
    private Integer vendorPrice;
    private int itemLevel;
	public Item(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer vendorPrice, int itemLevel) {
		super();
		this.itemID = itemID;
		this.itemName = itemName;
		this.maxStackSize = maxStackSize;
		this.isSellable = isSellable;
		this.vendorPrice = vendorPrice;
		this.itemLevel = itemLevel;
	}
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getMaxStackSize() {
		return maxStackSize;
	}
	public void setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
	}
	public boolean isSellable() {
		return isSellable;
	}
	public void setSellable(boolean isSellable) {
		this.isSellable = isSellable;
	}
	public Integer getVendorPrice() {
		return vendorPrice;
	}
	public void setVendorPrice(Integer vendorPrice) {
		this.vendorPrice = vendorPrice;
	}
	public int getItemLevel() {
		return itemLevel;
	}
	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}
    
}
