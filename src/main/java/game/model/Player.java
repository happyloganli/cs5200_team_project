package game.model;

public class Player {
    private String email;
    private String playerName;
    private boolean isActive;
    
	public Player(String email, String playerName, boolean isActive) {
		super();
		this.email = email;
		this.playerName = playerName;
		this.isActive = isActive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}


