package com.tim10.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tim10.domain.DiscountListItem;
import com.tim10.service.DiscountService;

@RestController
@RequestMapping(value="/api/discounts")
public class DiscountController {
	
	@Autowired
	private DiscountService discountService;
	
	@RequestMapping(method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DiscountListItem>> getDiscounts(){
		return new ResponseEntity<List<DiscountListItem>>(discountService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DiscountListItem>> addDiscounts(@RequestBody List<DiscountListItem> discounts){
		return new ResponseEntity<List<DiscountListItem>>(discountService.saveAll(discounts), HttpStatus.CREATED);
	}	
}
