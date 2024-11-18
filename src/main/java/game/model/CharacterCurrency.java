package game.model;

public class CharacterCurrency {
    private Chara character; 
    private Currency currency;
    private int totalAmount;
    private int weeklyEarned;
	public CharacterCurrency(Chara character, Currency currency, int totalAmount, int weeklyEarned) {
		super();
		this.character = character;
		this.currency = currency;
		this.totalAmount = totalAmount;
		this.weeklyEarned = weeklyEarned;
	}
	public Chara getCharacter() {
		return character;
	}
	public void setCharacter(Chara character) {
		this.character = character;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getWeeklyEarned() {
		return weeklyEarned;
	}
	public void setWeeklyEarned(int weeklyEarned) {
		this.weeklyEarned = weeklyEarned;
	}
    
}
