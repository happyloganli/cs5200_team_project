package game.model;

public class EquipmentSlot {
    private int equipSlotID;
    private String slotName;
	public EquipmentSlot(int equipSlotID, String slotName) {
		super();
		this.equipSlotID = equipSlotID;
		this.slotName = slotName;
	}
	public int getEquipSlotID() {
		return equipSlotID;
	}
	public void setEquipSlotID(int equipSlotID) {
		this.equipSlotID = equipSlotID;
	}
	public String getSlotName() {
		return slotName;
	}
	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}
    
}
