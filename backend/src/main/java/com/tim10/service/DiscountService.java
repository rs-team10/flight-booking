package com.tim10.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tim10.domain.DiscountListItem;
import com.tim10.repository.DiscountRepository;

@Service
public class DiscountService {
	
	@Autowired
	private DiscountRepository discountRepository;
	
	public List<DiscountListItem> findAll(){
		return discountRepository.findAll(sortByMinPointsAsc());
	}
	
	private Sort sortByMinPointsAsc() {
		return new Sort(Sort.Direction.ASC, "minPoints");
	}
	
	public List<DiscountListItem> saveAll(List<DiscountListItem> discounts){
		deleteAll();
		return discountRepository.saveAll(discounts);
	}
	
	private void deleteAll(){
		discountRepository.deleteAll();
	}
}
