package hello;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Project {

    private Integer id;
    private String projectName;
    private String creationDate;
    private String expiryDate;
    private boolean enabled;
    private List<String> targetCountries;
    private double projectCost;
    private String projectUrl;
    private List<TargetKey> targetKeys;

    public Project() {}
    
    public Project(Integer id, String projectName, String creationDate, String expiryDate, boolean enabled, List<String> targetCountries, double projectCost, String projectUrl, List<TargetKey> targetKeys) {
        this.id = id;
        this.projectName = projectName;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.enabled = enabled;
        this.targetCountries = targetCountries;
        this.projectCost = projectCost;
        this.projectUrl = projectUrl;
        this.targetKeys = targetKeys;
    }
    
	public Integer getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public List<String> getTargetCountries() {
		return targetCountries;
	}

	public double getProjectCost() {
		return projectCost;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

	public List<TargetKey> getTargetKeys() {
		return targetKeys;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setTargetCountries(List<String> targetCountries) {
		this.targetCountries = targetCountries;
	}

	public void setProjectCost(double projectCost) {
		this.projectCost = projectCost;
	}

	public void setProjectUrl(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	public void setTargetKeys(List<TargetKey> targetKeys) {
		this.targetKeys = targetKeys;
	}
}