package com.olx.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.olx.dto.Advertise;
import com.olx.exception.FromDateMissingException;
import com.olx.exception.InvalidAdvertiseIdException;
import com.olx.exception.InvalidAuthTokenException;
import com.olx.exception.InvalidCategoryIdException;
import com.olx.exception.InvalidPageIdException;
import com.olx.exception.InvalidStatusIdException;
import com.olx.exception.OnDateMissingException;
import com.olx.exception.ToDateMissingException;
import com.olx.exception.UserNameDoesNotExistException;
import com.olx.service.AdvertiseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/olx/advertise")
@CrossOrigin(origins = "*")
public class AdvertiseController {

	@Autowired
	AdvertiseService advertiseService;

	@ExceptionHandler(value = InvalidAuthTokenException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionAuthToken(InvalidAuthTokenException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidAdvertiseIdException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionInvalidAdvId(InvalidAdvertiseIdException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UserNameDoesNotExistException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionUserName(UserNameDoesNotExistException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = OnDateMissingException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionOnDate(OnDateMissingException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = FromDateMissingException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionFromDate(FromDateMissingException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ToDateMissingException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionToDate(ToDateMissingException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidPageIdException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionInvalidPageId(InvalidPageIdException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidCategoryIdException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionInvalidCatId(InvalidCategoryIdException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidStatusIdException.class) // Local Exception Handler
	public ResponseEntity<String> handleExceptionStatusId(InvalidStatusIdException exception) {
		return new ResponseEntity<String>(exception.toString(), HttpStatus.BAD_REQUEST);
	}

	// 8
	@PostMapping(value = "/postAd", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Post an Ad", notes = "This Rest API will return Posted Ad")
	public ResponseEntity<Advertise> createNewAdvertise(@RequestBody Advertise advertise,
			@RequestHeader("Authorization") String authToken) {
		Advertise advertises = this.advertiseService.createNewAdvertise(advertise, authToken);
		return new ResponseEntity<Advertise>(advertises, HttpStatus.OK);
	}

	// 9
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	@ApiOperation(value = "Updating a Avertise", notes = "This Rest API will return Updated Advertise")
	public ResponseEntity<Advertise> updateAdvertise(@RequestBody Advertise advertise,
			@RequestHeader("Authorization") String authToken, @PathVariable("id") int id) {
		return new ResponseEntity<Advertise>(advertiseService.updateAdvertise(advertise, id, authToken), HttpStatus.OK);

	}

	// 10
	@GetMapping(value = "/user/advertise", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Getting a List of Avertise", notes = "This Rest API will return List of Advertise")
	public ResponseEntity<List<Advertise>> getAllAdvertises(@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<List<Advertise>>(advertiseService.getAllAdvertises(authToken), HttpStatus.OK);
	}

	// 11
	@GetMapping(value = "/user/advertises/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Getting a List of a Avertise by Id", notes = "This Rest API will return List of Advertise by Id")
	public ResponseEntity<List<Advertise>> getAdvertiseAllById(
			@ApiParam(value = "Advertise", name = "id") @PathVariable("id") int id,
			@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<List<Advertise>>(advertiseService.getAdvertiseAllById(id, authToken), HttpStatus.OK);
	}

	// 12
	@DeleteMapping(value = "/user/advertise/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Getting a List of a Avertise by Id", notes = "This Rest API will return List of Advertise by Id")
	public ResponseEntity<Boolean> deleteAdvertiseById(
			@ApiParam(value = "Advertise", name = "id") @PathVariable("id") int id,
			@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<Boolean>(advertiseService.deleteAdvertiseById(id, authToken), HttpStatus.OK);
	}

	// 13
	@GetMapping(value = "/search/filtercriteria", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Getting a List of Avertise by FilterCriteria", notes = "This Rest API will return List of Advertise by Filter Criteria")
	public ResponseEntity<List<Advertise>> searchAdvertisesByFilterCriteria(
			@RequestParam(name = "searchText", required = false) String searchText,
			@RequestParam(name = "category", required = false) Integer categoryId,
			@RequestParam(name = "postedBy", required = false) String postedBy,
			@RequestParam(name = "dateCondition", required = false) String dateCondition,
			@RequestParam(name = "onDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate onDate,
			@RequestParam(name = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
			@RequestParam(name = "toDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
			@RequestParam(name = "sortedBy", required = false) String sortedBy,
			@RequestParam(name = "startIndex", defaultValue = "0") int startIndex,
			@RequestParam(name = "records", defaultValue = "10") int records) {
		return new ResponseEntity<List<Advertise>>(advertiseService.filterAdvertise(searchText, categoryId, postedBy,
				dateCondition, onDate, fromDate, toDate, sortedBy, startIndex, records), HttpStatus.OK);
	}

	// 14
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Getting a Avertise by Search Text", notes = "This Rest API will return a Advertise by Search Text")
	public ResponseEntity<List<Advertise>> SearchAdvByText(@RequestParam(name = "searchText") String searchText) {
		return new ResponseEntity<List<Advertise>>(advertiseService.SearchAdvertiseByText(searchText), HttpStatus.OK);
	}

	// 15
	@GetMapping(value = "/user/advertise/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Getting a List of a Avertise by Id", notes = "This Rest API will return List of Advertise by Id")
	public ResponseEntity<Advertise> getAdvertiseById(
			@ApiParam(value = "Advertise", name = "id") @PathVariable("id") int id,
			@RequestHeader("Authorization") String authToken) {
		return new ResponseEntity<Advertise>(advertiseService.getAdvertiseById(id, authToken), HttpStatus.OK);
	}

}
