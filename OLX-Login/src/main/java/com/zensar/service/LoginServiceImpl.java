package com.zensar.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.zensar.dto.User;
import com.zensar.entity.BlackListedTokensDocument;
import com.zensar.entity.UserEntity;
import com.zensar.exception.InvalidAuthTokenException;
import com.zensar.exception.InvalidCredentialsException;
import com.zensar.repository.UserMongoRepo;
import com.zensar.repository.UserRepo;
import com.zensar.security.JwtUtil;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserMongoRepo userMongoRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtil jwtUtil;

//	@Autowired
	UserDetails userDetails;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public String authenticate(User user) {
		try {
			this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

		}

		catch (AuthenticationException ex) {
			throw new InvalidCredentialsException(ex.toString());
		}

		String jwt = jwtUtil.generateToken(user.getUserName());
		return jwt;
	}

//	@Override
//	public boolean logout(String authToken) {
//		UserDocument userDocument = userMongoRepo.findByToken(authToken);
//		if (userDocument != null) {
//			return false;
//		}
//		UserDocument newToken = new UserDocument(authToken, LocalDate.now());
//		userDocument.save(newToken);
//		return true;
//
//	}

	@Override
	public boolean logout(String authToken) {
		String token = authToken.substring(7);
		BlackListedTokensDocument bts = userMongoRepo.findByToken(token);
		if (bts == null) {
			BlackListedTokensDocument newBts = new BlackListedTokensDocument(token, LocalDate.now());
			userMongoRepo.save(newBts);
			return true;
		}

		throw new InvalidAuthTokenException();

	}

	@Override
	public User registerUser(User user) {
		UserEntity userEntity = convertDTOIntoEntity(user);
		userEntity = userRepo.save(userEntity);
		return convertEntityIntoDTO(userEntity);
	}

	// get a user
	@Override
	public User getUserById(int id) {
		UserEntity uEntity = userRepo.getById(id);
		return convertEntityIntoDTO(uEntity);

	}

	@Override
	public boolean validateToken(String authToken) {
		try {
			authToken = authToken.substring(7);
			String username = jwtUtil.extractUsername(authToken);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			return jwtUtil.validateToken(authToken, userDetails);
		} catch (Exception e) {
			return false;
		}
	}

	private UserEntity convertDTOIntoEntity(User user) {
		TypeMap<User, UserEntity> tMap = modelMapper.typeMap(User.class, UserEntity.class);
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
		return userEntity;
	}

//	private List<Advertise> convertEntityListIntoDTOList(List<AdvertiseEntity> advertiseEntityList) {
//		// return new StockEntity(stock.getId(), stock.getName(), stock.getMarket(),
//		// stock.getPrice());
//		List<Advertise> advertisesList = new ArrayList<>();
//		for(AdvertiseEntity advertiseEntity : advertiseEntityList)
//		{
//		TypeMap<AdvertiseEntity, Advertise> typeMap = modelMapper.typeMap(AdvertiseEntity.class, Advertise.class);
//		Advertise advertise = modelMapper.map(advertiseEntity, Advertise.class);
//		advertisesList.add(advertise);
//		}
//
//		return advertisesList;

	private List<User> convertEntityListIntoDTOList(List<UserEntity> userEntityList) {
		List<User> userList = new ArrayList<>();
		for (UserEntity userEntity : userEntityList) {
			TypeMap<UserEntity, User> typeMap = modelMapper.typeMap(UserEntity.class, User.class);
			User user = modelMapper.map(userEntity, User.class);
			userList.add(user);
		}
		return userList;

	}

	private List<UserEntity> convertDTOListIntoEntityList(List<User> userList) {
		List<UserEntity> userEntityList = new ArrayList<>();
		for (User user : userList) {
			TypeMap<User, UserEntity> typeMap = modelMapper.typeMap(User.class, UserEntity.class);
			UserEntity userEntity = modelMapper.map(user, UserEntity.class);
			userList.add(user);
		}
		return userEntityList;

	}

	private User convertEntityIntoDTO(UserEntity userEntity) {
		TypeMap<UserEntity, User> tMap = modelMapper.typeMap(UserEntity.class, User.class);
		User user = modelMapper.map(userEntity, User.class);
		return user;
	}

//	@Override
//	public User getUser(String authToken) {
//		UserEntity userentity = userRepo.findByFirstName(authToken);
//		return convertEntityIntoDTO(userentity);
//	}

	@Override
	public List<User> getUser(String authToken) {
		authToken = authToken.substring(7);
		String username = jwtUtil.extractUsername(authToken);
		List<UserEntity> userentity = userRepo.findByUserName(username);
		return convertEntityListIntoDTOList(userentity);
	}

//  @Override
//  public User getUser() {
//	List<UserEntity> userEntityList = userRepo.findAll();
//	List<User> userDtoList = new ArrayList<User>();
//	for (UserEntity userEntity : userEntityList) {
//	    User user = convertEntityIntoDTO(userEntity);
//	    userDtoList.add(user);
//	}
//	return getUser();
//  }

}
