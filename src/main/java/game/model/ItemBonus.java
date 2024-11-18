package game.model;

import java.math.BigDecimal;

public class ItemBonus {
    private Item item;
    private AttributeType attributeType;
    private Integer bonusVal;
    private BigDecimal foodBonusVal;
    private Integer foodBonusCap;
	public ItemBonus(Item item, AttributeType attributeType, Integer bonusVal, BigDecimal foodBonusVal,
			Integer foodBonusCap) {
		super();
		this.item = item;
		this.attributeType = attributeType;
		this.bonusVal = bonusVal;
		this.foodBonusVal = foodBonusVal;
		this.foodBonusCap = foodBonusCap;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public AttributeType getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	public Integer getBonusVal() {
		return bonusVal;
	}
	public void setBonusVal(Integer bonusVal) {
		this.bonusVal = bonusVal;
	}
	public BigDecimal getFoodBonusVal() {
		return foodBonusVal;
	}
	public void setFoodBonusVal(BigDecimal foodBonusVal) {
		this.foodBonusVal = foodBonusVal;
	}
	public Integer getFoodBonusCap() {
		return foodBonusCap;
	}
	public void setFoodBonusCap(Integer foodBonusCap) {
		this.foodBonusCap = foodBonusCap;
	}
    
}
