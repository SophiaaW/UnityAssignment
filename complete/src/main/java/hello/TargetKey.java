package hello;

public class TargetKey {
	
	private Integer number;
    private String keyword;
    
    public TargetKey() {}
    
    public TargetKey(Integer number, String keyword) {
    	this.number = number;
    	this.keyword = keyword;
    }

	public long getNumber() {
		return number;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
