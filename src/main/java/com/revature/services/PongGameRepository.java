package com.revature.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.models.PongGameRecord;

/*
 * Spring Data provies a number of helpful repository types, with this hierarchy:
 * 
 * 1) Repository - Basic Repository Structure
 * 2) CrudRepository - Repository with CRUD helper methods
 * 3) PagingAndSortingRepository - Repository with methods to help with 
 * 			pagination and sorting of results and queries
 * 4) JpaRepository - Repository built with fluent query language and 
 * 			full support of JPA features
 */
@Repository
public interface PongGameRepository extends JpaRepository<PongGameRecord, Integer>{

	// Uses fluent API to automatically implement method
	public List<PongGameRecord> findAllByWinner(String winner);
	
	// @Query - implements method using HQL query defined in @Query
	@Query(value = "SELECT p FROM PongGameRecord p WHERE p.scoreWinner < 7")
	public List<PongGameRecord> findAllGamesScoreLessThanSeven();
}










