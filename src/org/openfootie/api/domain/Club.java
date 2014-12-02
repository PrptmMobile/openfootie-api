package org.openfootie.api.domain;

public class Club implements Rankable {

	private String name;
	private String nationName;
	private String nativeName;
	private String fullName;
	private double squadValue;
	
	public Club(String name, String nationName) {
		this.name = name;
		this.nationName = nationName;
	}

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public double getSquadValue() {
		return squadValue;
	}

	public void setSquadValue(double squadValue) {
		this.squadValue = squadValue;
	}

	public String getName() {
		return name;
	}

	public String getNationName() {
		return nationName;
	}
	
	public String getCountryName() {
		return this.nationName;
	}
	
	@Override
	public boolean equals(Object club) {
		
		if (!(club instanceof Club)) {
			return false;
		}
		
		return ((Club) club).name.equals(this.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}
