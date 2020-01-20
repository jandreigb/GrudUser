package br.com.jgb.GrudUser.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.jgb.GrudUser.entity.User;
import br.com.jgb.GrudUser.service.IUserService;

/**
 * Recursos para o gereanciamento de usuários
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * Autenticação do usuário retornando token de acesso
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/log-in", method = RequestMethod.POST)
	public ResponseEntity<User> autenticate(@Valid @RequestBody User user) {

		return new ResponseEntity<User>(userService.logIn(user), HttpStatus.OK);
	}

	/**
	 * Listagem de todos
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<User> listAll() {
		return userService.findAll();
	}

	/**
	 * Recupera pelo código
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getById(@PathVariable(value = "id") long id) {

		User user = userService.findById(id);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Cria novo usuário
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> insert(@Valid @RequestBody User user) {

		return new ResponseEntity<User>(userService.insert(user), HttpStatus.OK);
	}

	/**
	 * Atualiza usuário
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable(value = "id") long id, @Valid @RequestBody User user) {

		user.setId(id);
		return new ResponseEntity<User>(userService.update(user), HttpStatus.OK);
	}

	/**
	 * Atualiza usuário
	 * 
	 * @param id
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/change-password", method = RequestMethod.PUT)
	public ResponseEntity<User> changePassword(@Valid @RequestBody User user) {

		return new ResponseEntity<User>(userService.changePassword(user), HttpStatus.OK);
	}

	/**
	 * Remove usuário
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable(value = "id") long id) {

		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}