package com.revature.controllers;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.PongGameRecord;
import com.revature.services.PongGameService;

@RestController
@RequestMapping("pong-game-record")
@CrossOrigin(origins= {"*", "http://localhost:3000"})
public class PongGameController {
	
	PongGameService pongGameService;
	
	@Autowired
	public PongGameController(PongGameService pongGameService) {
		super();
		this.pongGameService = pongGameService;
	}
	
	@GetMapping("")
	public Page<PongGameRecord> getAllRecords(Pageable page) {
		return this.pongGameService.getAllRecords(page);
	}



	@PostMapping("")
	public PongGameRecord createRecord(@Valid @RequestBody PongGameRecord record) {
		PongGameRecord newRecord = pongGameService.createRecord(record);
		return newRecord;
	}
	
	@GetMapping("/{id}")
	public PongGameRecord getRecord(@PathVariable int id) {
		return pongGameService.getById(id);
	}
	
	@GetMapping(params = "winner")
	public List<PongGameRecord> getWinsByWinner(@PathParam(value="winner") String winner) {
		return pongGameService.getGamesWonBy(winner);
	}
	
	@PutMapping("/cheat/from/{from}/to/{to}")
	public List<PongGameRecord> cheatScores
				(@PathVariable int from, @PathVariable int to) {
		return pongGameService.stealPoints(from, to);
	}
	
	@GetMapping(path="/", params = {"fishy"})
	public List<PongGameRecord> findFishygames() {
		return pongGameService.getGamesWinnerLessThanSeven();
	}
}











