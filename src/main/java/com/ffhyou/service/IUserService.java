package com.ffhyou.service;

import java.util.List;

import com.ffhyou.model.User;
import com.googlecode.genericdao.search.Search;

public interface IUserService {
	
	public void SalvarOuAlterar(User user);

	public User buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<User> listar();
	
	public List<User> search(Search search);
}
