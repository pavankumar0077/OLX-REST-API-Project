package com.zensar.entity;

import java.time.LocalDate;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "blacklisted_tokens")
public class BlackListedTokensDocument {

//	@Id
//	private int id;
	private String token;
	private LocalDate createdDate;

	

}
