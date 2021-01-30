package com.ffhyou.service;

import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffhyou.dao.UserDao;
import com.ffhyou.model.User;
import com.googlecode.genericdao.search.Search;

@Service
public class UserService implements IUserService {
	
	private UserDao userDao;
	

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(User user) {
		userDao.SalvarOuAlterar(user);

	}

	@Override
	@Transactional(readOnly = true)
	public User buscarPorId(Integer id) {
		
		return userDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		userDao.excluir(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<User> listar() {
		// TODO Auto-generated method stub
		return userDao.listar();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> search(Search search) {
		// TODO Auto-generated method stub
		return userDao.searchDao(search);
	}
	
	
	
	@Transactional(readOnly = true)
	public List<User> listarCountry(String string) {
		// TODO Auto-generated method stub
		return userDao.listarCountry(string);
	}
}
