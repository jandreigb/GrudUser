package br.com.jgb.GrudUser.service;

import java.util.List;

import javax.validation.Valid;

import br.com.jgb.GrudUser.entity.User;

public interface IUserService {

	public List<User> findAll();

	public User findById(long id);

	public User insert(User user);

	public User update(User user);

	public void delete(long id);

	public User changePassword(User user);

	public User logIn(@Valid User user);
}
