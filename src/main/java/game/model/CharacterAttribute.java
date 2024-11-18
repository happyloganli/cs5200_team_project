package game.model;

public class CharacterAttribute {
    private Chara character;
    private AttributeType attributeType;
    private int value;
	public CharacterAttribute(Chara character, AttributeType attributeType, int value) {
		super();
		this.character = character;
		this.attributeType = attributeType;
		this.value = value;
	}
	public Chara getCharacter() {
		return character;
	}
	public void setCharacter(Chara character) {
		this.character = character;
	}
	public AttributeType getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
    
}
