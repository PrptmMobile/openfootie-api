package org.openfootie.api.domain.sample;

public enum Position {
	
	GK,D,M,F;
	
	public static Position fromString(String str) {
		switch(str) {
			case "G":
				return GK;
			case "D":
				return D;
			case "M":
				return M;
			case "F":
				return F;
			default:
				return null;
		}
	}
}
