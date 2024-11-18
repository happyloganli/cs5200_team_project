package game.model;

public class EquippedItem {
	private Chara character;
    private EquipmentSlot equipSlot;
    private Equipment equipment;
	public EquippedItem(Chara character, EquipmentSlot equipSlot, Equipment equipment) {
		super();
		this.character = character;
		this.equipSlot = equipSlot;
		this.equipment = equipment;
	}
	public Chara getCharacter() {
		return character;
	}
	public void setCharacter(Chara character) {
		this.character = character;
	}
	public EquipmentSlot getEquipSlot() {
		return equipSlot;
	}
	public void setEquipSlot(EquipmentSlot equipSlot) {
		this.equipSlot = equipSlot;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
    
}
