package game.model;

public class Weapon extends Equipment {
    private EquipmentSlot equipSlot; 
    private Integer damageDone;
    private Boolean autoAttack;
    private Integer attackDelay;
	public Weapon(int itemID, String itemName, int maxStackSize, boolean isSellable, Integer vendorPrice, int itemLevel,
			int requiredLevel, EquipmentSlot equipSlot, Integer damageDone, Boolean autoAttack, Integer attackDelay) {
		super(itemID, itemName, maxStackSize, isSellable, vendorPrice, itemLevel, requiredLevel);
		this.equipSlot = equipSlot;
		this.damageDone = damageDone;
		this.autoAttack = autoAttack;
		this.attackDelay = attackDelay;
	}
	public EquipmentSlot getEquipSlot() {
		return equipSlot;
	}
	public void setEquipSlot(EquipmentSlot equipSlot) {
		this.equipSlot = equipSlot;
	}
	public Integer getDamageDone() {
		return damageDone;
	}
	public void setDamageDone(Integer damageDone) {
		this.damageDone = damageDone;
	}
	public Boolean getAutoAttack() {
		return autoAttack;
	}
	public void setAutoAttack(Boolean autoAttack) {
		this.autoAttack = autoAttack;
	}
	public Integer getAttackDelay() {
		return attackDelay;
	}
	public void setAttackDelay(Integer attackDelay) {
		this.attackDelay = attackDelay;
	}
    
}
