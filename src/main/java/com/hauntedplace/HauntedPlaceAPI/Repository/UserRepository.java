package com.hauntedplace.HauntedPlaceAPI.Repository;

import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
