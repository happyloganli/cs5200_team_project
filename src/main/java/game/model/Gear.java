package game.model;

public class Gear extends Equipment {
    private EquipmentSlot equipSlot;
    private Integer defenseRating;
    private Integer magicDefenseRating;
	public Gear(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer vendorPrice, int itemLevel,
			int requiredLevel, EquipmentSlot equipSlot, Integer defenseRating, Integer magicDefenseRating) {
		super(itemID, itemName, maxStackSize, isSellable, vendorPrice, itemLevel, requiredLevel);
		this.equipSlot = equipSlot;
		this.defenseRating = defenseRating;
		this.magicDefenseRating = magicDefenseRating;
	}
	public EquipmentSlot getEquipSlot() {
		return equipSlot;
	}
	public void setEquipSlot(EquipmentSlot equipSlot) {
		this.equipSlot = equipSlot;
	}
	public Integer getDefenseRating() {
		return defenseRating;
	}
	public void setDefenseRating(Integer defenseRating) {
		this.defenseRating = defenseRating;
	}
	public Integer getMagicDefenseRating() {
		return magicDefenseRating;
	}
	public void setMagicDefenseRating(Integer magicDefenseRating) {
		this.magicDefenseRating = magicDefenseRating;
	}

    
}
