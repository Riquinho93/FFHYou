package com.ffhyou.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.markup.html.form.TextField;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ffhyou.model.User;


@Repository
public class UserDao extends GenericDao<User, Serializable>{
	
	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<User> listar() {
		String hql = "select f from User f order by name";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<User> userList = query.list();
		return userList;
	}
//	@Transactional(readOnly = true)
	public User alterar(Integer id) {
		String hql = "select c from User u join fetch c.institution i join fetch c.country c " + "where c.id = :id";

		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		User user = (User) query.uniqueResult();
		return user;
	}
	
	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<User> listarCountry(String name) {
		String hql = "select u from User u join fetch u.country c join fetch u.address "+"where u.country.name="+name;
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<User> userList = query.list();
		return userList;
	}


}
