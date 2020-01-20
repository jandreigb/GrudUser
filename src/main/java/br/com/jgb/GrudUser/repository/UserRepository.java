package br.com.jgb.GrudUser.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jgb.GrudUser.entity.User;

/**
 * Repositório de dados do usuário
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Busca pelo login
	 * 
	 * @param user
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login)")
	public List<User> findByLogin(@Param("login") String login);

	/**
	 * Busca pelo login desconsiderando o código do usuário
	 * 
	 * @param user
	 * @param id
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login) AND id != :id")
	public List<User> findByLogin(@Param("login") String login, @Param("id") Long id);

	/**
	 * Busca pelo login e senha
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	@Query("SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login) AND password = :password")
	public User findByLoginPassword(@Param("login") String login, @Param("password") String password);
}