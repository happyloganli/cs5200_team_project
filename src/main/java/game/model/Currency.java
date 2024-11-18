package game.model;

import java.time.LocalDateTime;

public class Currency {
    private int currencyID;
    private String name;
    private Integer maxCap;
    private Integer weeklyCap;
    private LocalDateTime lastResetTime;
	public Currency(int currencyID, String name, Integer maxCap, Integer weeklyCap, LocalDateTime lastResetTime) {
		super();
		this.currencyID = currencyID;
		this.name = name;
		this.maxCap = maxCap;
		this.weeklyCap = weeklyCap;
		this.lastResetTime = lastResetTime;
	}
	public int getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMaxCap() {
		return maxCap;
	}
	public void setMaxCap(Integer maxCap) {
		this.maxCap = maxCap;
	}
	public Integer getWeeklyCap() {
		return weeklyCap;
	}
	public void setWeeklyCap(Integer weeklyCap) {
		this.weeklyCap = weeklyCap;
	}
	public LocalDateTime getLastResetTime() {
		return lastResetTime;
	}
	public void setLastResetTime(LocalDateTime lastResetTime) {
		this.lastResetTime = lastResetTime;
	}
    
}
