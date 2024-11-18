package game.model;

public class AttributeType {
    private int attributeTypeID;
    private String attributeName;
    private String description;
	public AttributeType(int attributeTypeID, String attributeName, String description) {
		super();
		this.attributeTypeID = attributeTypeID;
		this.attributeName = attributeName;
		this.description = description;
	}
	public int getAttributeTypeID() {
		return attributeTypeID;
	}
	public void setAttributeTypeID(int attributeTypeID) {
		this.attributeTypeID = attributeTypeID;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
}
