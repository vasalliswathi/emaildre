package org.jsp.dre.util;

import java.util.Comparator;

import org.jsp.dre.dto.MatchingUser;

public class SortByAge implements Comparator<MatchingUser> {

	@Override
	public int compare(MatchingUser o1, MatchingUser o2) {
	
		if(o1.getAgeDifference()<o2.getAgeDifference()) {
			return -1;
		}
		else if(o1.getAgeDifference()>o2.getAgeDifference()) {
			return 1;
		}
		else if(o1.getAgeDifference()==o2.getAgeDifference()) {
			
			if(o1.getMatchingIntrestCount()<o2.getMatchingIntrestCount()) {
				return 1;
			}
			else if(o1.getMatchingIntrestCount()>o2.getMatchingIntrestCount()) {
				return -1;
			}
			return 0;
		}
		
	    return 0;
		
	}

}


























