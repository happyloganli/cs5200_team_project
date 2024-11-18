package game.model;

public class CharacterJob {
    private Job job;
    private Chara character;
    private int jobLevel;
    private int experience;

	public CharacterJob(Job job, Chara character, int jobLevel, int experience) {
		super();
		this.job = job;
		this.character = character;
		this.jobLevel = jobLevel;
		this.experience = experience;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Chara getCharacter() {
		return character;
	}

	public void setCharacter(Chara character) {
		this.character = character;
	}

	public int getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(int jobLevel) {
		this.jobLevel = jobLevel;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	
    
}
