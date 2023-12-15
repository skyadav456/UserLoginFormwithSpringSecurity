package com.sharad.springboot.repository;

import com.sharad.springboot.entiry.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
