package org.openfootie.api.sample.match.dao.sjon;

import java.util.Set;

import org.openfootie.api.sample.match.Match;
import org.openfootie.api.sample.match.dao.MatchDao;
import org.sjon.db.SjonTable;

public class MatchDaoImpl implements MatchDao {
	
	public MatchDaoImpl(String sjonFile) throws Exception {
		
		SjonTable matchData = new SjonTable(sjonFile);
		
		
		
	}

	@Override
	public Set<Match> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
