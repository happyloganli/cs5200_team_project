package game.model;

public class Job {
    private int jobID;
    private String description;
    private String jobName;
    private String jobCategory;
    
	public Job(int jobID, String description, String jobName, String jobCategory) {
		super();
		this.jobID = jobID;
		this.description = description;
		this.jobName = jobName;
		this.jobCategory = jobCategory;
	}

	public int getJobID() {
		return jobID;
	}

	public void setJobID(int jobID) {
		this.jobID = jobID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}
    
}