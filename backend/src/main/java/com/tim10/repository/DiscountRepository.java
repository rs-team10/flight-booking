package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.DiscountListItem;

public interface DiscountRepository  extends JpaRepository<DiscountListItem, Long> {

}
