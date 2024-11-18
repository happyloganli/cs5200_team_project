package game.model;

public class Chara {
    private int characterID;
    private String firstName;
    private String lastName;
    private Player player; 
    private int HP;
    private int MP;
	public Chara(int characterID, String firstName, String lastName, Player player, int hP, int mP) {
		super();
		this.characterID = characterID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.player = player;
		HP = hP;
		MP = mP;
	}
	public int getCharacterID() {
		return characterID;
	}
	public void setCharacterID(int characterID) {
		this.characterID = characterID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getHP() {
		return HP;
	}
	public void setHP(int hP) {
		HP = hP;
	}
	public int getMP() {
		return MP;
	}
	public void setMP(int mP) {
		MP = mP;
	}
    
}
