package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.PriceListItem;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {

}
