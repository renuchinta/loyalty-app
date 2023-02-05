package dev.danvega.jwt.repository;

import dev.danvega.jwt.model.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessUserRepository  extends JpaRepository<BusinessUser,Long> {

    BusinessUser findByUserNameAndPassword(String username, String password);

    Optional<BusinessUser> findByBusinessQRId(String businessQRId);
}
