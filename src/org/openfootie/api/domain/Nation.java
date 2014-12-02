package org.openfootie.api.domain;

public class Nation implements Rankable {
	
	private String name;
	private String fullName;
	private String confederation;
	
	public Nation (String name, String confederation) {
		this.name = name;
		this.confederation = confederation;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public String getConfederation() {
		return confederation;
	}
	
	@Override
	public boolean equals(Object nation) {
		
		if (!(nation instanceof Nation)) {
			return false;
		}
		
		return ((Nation) nation).name.equals(this.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
