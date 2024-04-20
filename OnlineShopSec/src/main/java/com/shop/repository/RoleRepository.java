package com.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.model.Role;
import com.shop.model.User;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
