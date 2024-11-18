package game.model;

public class JobSpecificEquipment {
    private Equipment equipment;
    private Job job;
	public JobSpecificEquipment(Equipment equipment, Job job) {
		super();
		this.equipment = equipment;
		this.job = job;
	}
	public Equipment getEquipment() {
		return equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
    
}
