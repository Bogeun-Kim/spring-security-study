package com.example.springsecurity.repository;

import com.example.springsecurity.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<CustomerRepository, Long> {

    // 이메일을 기반으로 고객 기록이 존재하지 않는 시나리오에서 null 값을 받기 위해 설정
    Optional<Customer> findByEmail(String email);

}
