package com.nighthawk.spring_portfolio.mvc.usr;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  UsrRoleJpaRepository extends JpaRepository<UsrRole, Long> {
    UsrRole findByName(String name);
}

