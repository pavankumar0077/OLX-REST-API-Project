package com.olx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olx.dto.Advertise;
import com.olx.entity.AdvertiseEntity;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.exception.InvalidCategoryIdException;
import com.olx.exception.InvalidStatusIdException;
import com.olx.repository.AdvertiseRepo;
import com.olx.security.JwtUtil;


@Service
public class AdvertiseServiceImpl implements AdvertiseService {

	@Autowired
	EntityManager entityManager; // JPA

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AdvertiseRepo advertiseRepo;

	@Autowired
	LoginServiceDelegate loginServiceDelegate;
	
	@Autowired
	MasterServiceDelegate masterServiceDelegate;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Override
	public Advertise createNewAdvertise(Advertise advertiseDto, String authToken) {
	
		if(loginServiceDelegate.isTokenValid(authToken)) {
			
			int categoryId = advertiseDto.getCategoryId();
			int statusId = advertiseDto.getStatusId();
			String category = masterServiceDelegate.getCategoryDescription(categoryId);
			String status = masterServiceDelegate.getStatusName(statusId);
			authToken = authToken.substring(7);
			String username = jwtUtil.extractUsername(authToken);
			
			AdvertiseEntity advertiseEntity = convertDTOIntoEntity(advertiseDto);
			advertiseEntity.setUsername(username);
			advertiseEntity.setCategory(category);
			advertiseEntity.setStatus(status);;
			advertiseEntity.setCreatedDate(LocalDate.now());
			advertiseEntity.setModifiedDate(LocalDate.now());
			advertiseRepo.save(advertiseEntity);
			return convertEntityIntoDTO(advertiseEntity);
		}
		throw new InvalidAuthTokenException();
	}


//	@Override
//	public Advertise createNewAdvertise(Advertise advertise, String authToken) {
////		String cateName = masterServiceDelegate.getCategoryDescription(advertise.getCategory(), authToken);
////		String statusName = masterServiceDelegate.getStatusName(advertise.getStatusId(), authToken);
//		int categoryId = advertise.getCategory();
//		int statusId = advertise.getStatusId();
//		String category = masterServiceDelegate.getCategoryDescription(categoryId, authToken)
//		String status = masterServiceDelegate.getStatus(statusId);
//		if (cateName != null && statusName != null) {
//			advertise.setCategoryName(cateName);
//			advertise.setStatusName(statusName);
//			AdvertiseEntity advertiseEntity = advertiseRepo.save(convertDTOIntoEntity(advertise));
//			return convertEntityIntoDTO(advertiseEntity);
//		} else {
//			throw new InvalidAuthTokenException(authToken);
//		}
//	}

	@Override
	public Advertise updateAdvertise(Advertise advertise, int id, String token) {
		if (loginServiceDelegate.isTokenValid(token)) {
			Optional<AdvertiseEntity> advertiseEntityOptional = advertiseRepo.findById(id);
			if (advertiseEntityOptional.isPresent()) {
				AdvertiseEntity advertiseEntity = advertiseEntityOptional.get();
				Advertise updatedAdvertise = convertEntityIntoDTO(advertiseEntity);
				int categoryId = advertise.getCategoryId();
				int statusId = advertise.getStatusId();
				String category = masterServiceDelegate.getCategoryDescription(categoryId);
				String status = masterServiceDelegate.getStatusName(statusId);
				updatedAdvertise.setTitle(advertise.getTitle());
				updatedAdvertise.setCategory(category);
				updatedAdvertise.setDescription(advertise.getDescription());
				updatedAdvertise.setModifiedDate(LocalDate.now());
				updatedAdvertise.setPrice(advertise.getPrice());
				updatedAdvertise.setStatusId(statusId);
				return updatedAdvertise;
			} else
				throw new InvalidCategoryIdException();

		} else {
			throw new InvalidAuthTokenException();
		}
	}

	@Override
	public List<Advertise> getAllAdvertises(String authToken) {
		if (loginServiceDelegate.isTokenValid(authToken)) {
			List<AdvertiseEntity> advertiseEntities = advertiseRepo.findAll();
			List<Advertise> advertiseDTO = new ArrayList<Advertise>();
			Iterator<AdvertiseEntity> itrAdvertiseEntities = advertiseEntities.iterator();
			while (itrAdvertiseEntities.hasNext()) {
				Advertise advertise = convertEntityIntoDTO(itrAdvertiseEntities.next());
				advertiseDTO.add(advertise);
			}

			return advertiseDTO;
		} else
			throw new InvalidAuthTokenException();
	}

	@Override
	public Advertise getAdvertiseById(int id, String authToken) {
		if (loginServiceDelegate.isTokenValid(authToken)) {
			Optional<AdvertiseEntity> advertiseEntitiesOp = advertiseRepo.findById(id);
			Advertise advertiseDTO = null;
			if (advertiseEntitiesOp.isPresent()) {
				Advertise advertise = convertEntityIntoDTO(advertiseEntitiesOp.get());
				return advertise;
			}
			

		}
		throw new InvalidAuthTokenException();
	}

	@Override
	public Boolean deleteAdvertiseById(int id, String authtoken) {
		if (loginServiceDelegate.isTokenValid(authtoken)) {
			advertiseRepo.deleteById(id);
			return true;
		} else
			throw new InvalidAuthTokenException();
	}

	@Override
	public List<Advertise> filterAdvertise(String searchText, Integer categoryId, String postedBy, String dateCondition,
			LocalDate onDate, LocalDate fromDate, LocalDate toDate, String sortedBy, int startIndex, int records) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertiseEntity> criteriaQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);
		Root<AdvertiseEntity> root = criteriaQuery.from(AdvertiseEntity.class);

		Predicate predicateTitle = criteriaBuilder.and();
		Predicate predicateDescription = criteriaBuilder.and();
		Predicate predicateSearchText = criteriaBuilder.and();
		Predicate predicateCategory = criteriaBuilder.and();
		Predicate predicateDateConditionEquals = criteriaBuilder.and();
		Predicate predicateDateConditionGreateThan = criteriaBuilder.and();
		Predicate predicateDateConditionLessThan = criteriaBuilder.and();
		Predicate predicateDateConditionBetweenFromDate = criteriaBuilder.and();
		Predicate predicatePostedBy = criteriaBuilder.and();
		Predicate predicateDateCondition = criteriaBuilder.and();
		Predicate predicateOrderBy = criteriaBuilder.and();
		Predicate predicateFinal = criteriaBuilder.and();

		if (searchText != null && !"".equalsIgnoreCase(searchText)) {
			predicateTitle = criteriaBuilder.like(root.get("title"), "%" + searchText + "%");
			predicateDescription = criteriaBuilder.like(root.get("description"), "%" + searchText + "%");
			predicateSearchText = criteriaBuilder.or(predicateTitle, predicateDescription);
		}

		if (postedBy != null && !"".equalsIgnoreCase(postedBy)) {
			predicatePostedBy = criteriaBuilder.equal(root.get("username"), postedBy);
		}

		if (dateCondition != null && dateCondition.contains("equal")) {
			predicateDateConditionEquals = criteriaBuilder.equal(root.get("createdDate"), onDate);
		}

		if (dateCondition != null && dateCondition.contains("greatethan")) {
			predicateDateConditionGreateThan = criteriaBuilder.greaterThan(root.get("createdDate"), fromDate);
		}

		if (dateCondition != null && dateCondition.contains("lessthan")) {
			predicateDateConditionLessThan = criteriaBuilder.greaterThan(root.get("createdDate"), onDate);
		}

		if (dateCondition != null && dateCondition.contains("between")) {
			predicateDateConditionBetweenFromDate = criteriaBuilder.between(root.get("createdDate"), fromDate, toDate);
		}

		predicateDateCondition = criteriaBuilder.and(predicateDateConditionEquals, predicateDateConditionGreateThan,
				predicateDateConditionLessThan, predicateDateConditionBetweenFromDate);

		if (categoryId != null) {
			predicateCategory = criteriaBuilder.equal(root.get("category"), categoryId);
		}

		predicateFinal = criteriaBuilder.and(predicateSearchText, predicateCategory, predicateDateCondition,
				predicatePostedBy);
		criteriaQuery.where(predicateFinal);
		if (sortedBy != null && !sortedBy.equalsIgnoreCase("")) {
			if (sortedBy == "title") {
				criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(root.get("title")));
			} else {
				criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")));
			}

		}
		TypedQuery<AdvertiseEntity> typedQuery = entityManager.createQuery(criteriaQuery);

		typedQuery.setFirstResult(startIndex);
		typedQuery.setMaxResults(records);
		List<AdvertiseEntity> advertiseEntityList = typedQuery.getResultList();
		return convertEntityListIntoDTOList(advertiseEntityList);
	}

	@Override
	public List<Advertise> SearchAdvertiseByText(String searchText) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<AdvertiseEntity> advertiseQuery = criteriaBuilder.createQuery(AdvertiseEntity.class);
		Root<AdvertiseEntity> advertiseRoot = advertiseQuery.from(AdvertiseEntity.class);

		Predicate predicateTitle = criteriaBuilder.and();
		Predicate predicateDescription = criteriaBuilder.and();
		Predicate predicateSearchText = criteriaBuilder.and();
		if (searchText != null && !searchText.equalsIgnoreCase("")) {
			predicateTitle = criteriaBuilder.like(advertiseRoot.get("title"), "%" + searchText + "%");
			predicateDescription = criteriaBuilder.like(advertiseRoot.get("description"), "%" + searchText + "%");
			predicateSearchText = criteriaBuilder.or(predicateTitle, predicateDescription);
			advertiseQuery.where(predicateSearchText);

			TypedQuery<AdvertiseEntity> typedQuery = entityManager.createQuery(advertiseQuery);

			List<AdvertiseEntity> advertiseEntityList = typedQuery.getResultList();
			return convertEntityListIntoDTOList(advertiseEntityList);

		} else
			throw new InvalidStatusIdException();
	}

	@Override
	public List<Advertise> getAdvertiseAllById(int id, String authToken) {
		if (loginServiceDelegate.isTokenValid(authToken)) {
			List<AdvertiseEntity> advertiseEntities = advertiseRepo.findAllById(id);
			List<Advertise> advertiseDTO = new ArrayList<Advertise>();
			Iterator<AdvertiseEntity> itrAdvertiseEntities = advertiseEntities.iterator();
			while (itrAdvertiseEntities.hasNext()) {
				Advertise advertise = convertEntityIntoDTO(itrAdvertiseEntities.next());
				advertiseDTO.add(advertise);
			}

			return advertiseDTO;
		} else
			throw new InvalidAuthTokenException();
	}

	private Advertise convertEntityIntoDTO(AdvertiseEntity advertiseEntity) {
		Advertise advertise = modelMapper.map(advertiseEntity, Advertise.class);
		return advertise;
	}

	private AdvertiseEntity convertDTOIntoEntity(Advertise advertise) {
		AdvertiseEntity advertiseEntity = modelMapper.map(advertise, AdvertiseEntity.class);
		return advertiseEntity;
	}

	private List<Advertise> convertEntityListIntoDTOList(List<AdvertiseEntity> advertiseEntityList) {
		List<Advertise> advertisesList = new ArrayList<>();
		for (AdvertiseEntity advertiseEntity : advertiseEntityList) {
			TypeMap<AdvertiseEntity, Advertise> typeMap = modelMapper.typeMap(AdvertiseEntity.class, Advertise.class);
			Advertise advertise = modelMapper.map(advertiseEntity, Advertise.class);
			advertisesList.add(advertise);
		}

		return advertisesList;
	}

}
