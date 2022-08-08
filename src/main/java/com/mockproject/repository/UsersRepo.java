package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.Users;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long>{
	Users findByUsername(String username);
	Users findByUsernameAndIsDeletedAndEnabled(String username, Boolean isDeleted, Boolean Enable);
	List<Users> findByIsDeleted(Boolean isDeleted);
	
	@Modifying(clearAutomatically = true)
	@Query(value ="UPDATE users SET isDeleted = 1 where username = ?", nativeQuery = true)
	void deleteLogical(String username);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET email = ?1, hashPassword = ?2, fullname = ?3 where username = ?4", nativeQuery = true)
	void update(String email, String hashPassword, String fullname, String username);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET email = ?1, fullname = ?2 where username = ?3", nativeQuery = true)
	void updateNonPass(String email, String fullname, String username);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET fullname = ?1 where username = ?2", nativeQuery = true)
	void updateUserDetails(String fullname, String username);
	
	Users findByEmail(String email);
	Users findByResetPassword(String token);

	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE users SET enabled = true where id= ?", nativeQuery = true)
	void enabled(Long id);
	
	Users findByVerificationCode(String code);
}
