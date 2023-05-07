package com.example.InvatareInteractivaBackend.repository;

import com.example.InvatareInteractivaBackend.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "select * from invatare_interactiva_db.user u where u.username=?", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    User findUserById(Long id);


}
