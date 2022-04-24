package com.zensar.repository;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.zensar.entity.BlackListedTokensDocument;



public interface UserMongoRepo extends MongoRepository<BlackListedTokensDocument, Integer>{
	
	public BlackListedTokensDocument findByToken(String token);

}
