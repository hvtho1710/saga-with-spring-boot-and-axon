package com.progressivecoder.ordermanagement.orderservice.repository;

import com.progressivecoder.ordermanagement.orderservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Order;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}
