package com.revature.services;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.PongGameRecord;

@Service
public class PongGameService {

	@Autowired
	EntityManager entityManager;
	
	PongGameRepository pongGameRepository;
	
	@Autowired
	public PongGameService(PongGameRepository pongGameRepository) {
		super();
		this.pongGameRepository = pongGameRepository;
	}

	public PongGameRecord createRecord(PongGameRecord record) {
		// Business Logic
		// Ensuring the user has the privileges to create this thing
		// Ensuring that the values passed are valid
		return pongGameRepository.save(record);
	}

	@Transactional(propagation = Propagation.NEVER)
	public PongGameRecord getById(int id) {
		return pongGameRepository.findById(id)
				.orElseThrow(() -> 
					new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<PongGameRecord> stealPoints(int id, int otherId) {
		
		Session session = entityManager.unwrap(Session.class);
		System.out.println(session.getTransaction());
		// Retrieve both records
		PongGameRecord fromRecord = getById(id);
		PongGameRecord toRecord = getById(otherId);
		
		// Update values
		fromRecord.setScoreWinner(fromRecord.getScoreWinner()-1);
		toRecord.setScoreWinner(toRecord.getScoreWinner()+1);
		
		// Create a list to return them with
		List<PongGameRecord> records = Arrays
							.asList(new PongGameRecord[]{fromRecord, toRecord});
		
		// Update the records
		update(fromRecord);
		update(toRecord);
		
		return records;		
	}
	
	public PongGameRecord update(PongGameRecord record) {
		return pongGameRepository.save(record);
	}
	
	public List<PongGameRecord> getGamesWonBy(String winner) {
		return pongGameRepository.findAllByWinner(winner);
	}
	
	public List<PongGameRecord> getGamesWinnerLessThanSeven() {
		return pongGameRepository.findAllGamesScoreLessThanSeven();
	}

	public Page<PongGameRecord> getAllRecords(Pageable pageable) {
		return pongGameRepository.findAll(pageable);
	}
}
