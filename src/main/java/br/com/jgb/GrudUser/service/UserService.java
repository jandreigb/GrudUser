package br.com.jgb.GrudUser.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jgb.GrudUser.BadRequestException;
import br.com.jgb.GrudUser.entity.User;
import br.com.jgb.GrudUser.repository.UserRepository;

/**
 * Camada de serviço/negócio do usuário
 *
 */
@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Efetua login
	 */
	@Override
	public User logIn(@Valid User user) {
		return findByLoginPassword(user);
	}

	/**
	 * Lista todos os registros
	 */
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * Recupera usuário pelo código
	 */
	@Override
	public User findById(long id) {

		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new BadRequestException("Usuário não encontrado");
		}
	}

	/**
	 * Insere novo usuário
	 */
	@Override
	public User insert(User user) {

		// dados básicos
		basicValidation(user);

		// valida senhas
		passwordValidation(user);

		// não pode haver outro usuário com o mesmo login
		List<User> users = userRepository.findByLogin(user.getLogin());

		if (users != null && users.size() > 0) {
			throw new BadRequestException("Já existe registro para o login informado.");
		}

		// validações para senha

		user.setCreatedDate(new Date());
		user.setLogin(user.getLogin().toLowerCase());
		return userRepository.save(user);
	}

	/**
	 * Validação para as senhas
	 * 
	 * @param user
	 */
	private void passwordValidation(User user) {

		if (user.getNewPassword() == null || user.getNewPassword().trim().equals("")
				|| user.getNewPassword().trim().length() < 6) {
			throw new BadRequestException("A senha deve ter no mínimo 6 caracteres.");
		}

		if (user.getNewPasswordRepeat() == null || user.getNewPasswordRepeat().trim().equals("")) {
			throw new BadRequestException("A repetição da senha não foi informada.");
		}

		if (!user.getNewPasswordRepeat().equals(user.getNewPassword())) {
			throw new BadRequestException("A senha não confere com a repetição.");
		}

		// define a senha criptografada
		user.setPassword(generatePassword(user.getNewPassword()));
	}

	/**
	 * Gera senha criptografada
	 * 
	 * @param password
	 * @return
	 */
	private String generatePassword(String password) {

		// a lógica da hash vai aqui....
		return password.concat("NÃO SEGURO");
	}

	/**
	 * Validação básica
	 * 
	 * @param user
	 */
	private void basicValidation(User user) {

		if (user == null) {
			throw new BadRequestException("Dados do usuário não informados.");
		}

		if (user.getName() == null || user.getName().trim().equals("")) {
			throw new BadRequestException("O nome do usário é obrigatório.");
		}

		if (user.getLogin() == null || user.getLogin().trim().equals("")) {
			throw new BadRequestException("O login do usário é obrigatório.");
		}
	}

	/**
	 * Atualiza usuário existente
	 */
	@Override
	public User update(User user) {

		// dados básicos
		basicValidation(user);

		if (user.getId() == null) {
			throw new BadRequestException("Código do usuário não informado.");
		}

		// não pode haver outro usuário com o mesmo login
		List<User> users = userRepository.findByLogin(user.getLogin(), user.getId());

		if (users != null && users.size() > 0) {
			throw new BadRequestException("Já existe registro para o login informado.");
		}

		User oldUser = findById(user.getId());
		oldUser.setName(user.getName());
		oldUser.setLogin(user.getLogin().toLowerCase());
		return userRepository.save(oldUser);
	}

	/**
	 * Altera senha do usuário
	 */
	@Override
	public User changePassword(User user) {

		// recupera login atual
		User oldUser = findByLoginPassword(user);

		// valida novas senhas
		passwordValidation(user);

		oldUser.setPassword(user.getPassword());
		return userRepository.save(oldUser);
	}

	/**
	 * Recupera usuário pelo login e senha
	 * 
	 * @param user
	 * @return
	 */
	private User findByLoginPassword(User user) {

		if (user == null) {
			throw new BadRequestException("Dados do usuário não informados.");
		}

		if (user.getLogin() == null) {
			throw new BadRequestException("Login do usuário não informado.");
		}

		if (user.getPassword() == null) {
			throw new BadRequestException("Senha não informada.");
		}

		user.setPassword(generatePassword(user.getPassword()));
		User oldUser = userRepository.findByLoginPassword(user.getLogin(), user.getPassword());

		if (oldUser == null) {
			throw new BadRequestException("Login e senha inválidos.");
		}

		return oldUser;
	}

	/**
	 * Remove usuário
	 */
	@Override
	public void delete(long id) {

		User user = findById(id);
		userRepository.delete(user);
	}
}