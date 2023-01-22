package dev.danvega.jwt.repository;

import dev.danvega.jwt.model.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessUserRepository  extends JpaRepository<BusinessUser,Long> {

}
