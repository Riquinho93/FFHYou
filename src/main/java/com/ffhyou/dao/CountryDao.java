package com.ffhyou.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ffhyou.model.Country;


@Repository
public class CountryDao extends GenericDao<Country, Serializable>{
	
	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<Country> listar() {
		String hql = "select f from Country f order by name";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Country> countryList = query.list();
		return countryList;
	}
//	@Transactional(readOnly = true)
	public Country alterar(Integer id) {
		String hql = "select f from Country f";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		Country country = (Country) query.uniqueResult();
		return country;
	}
	
	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<Country> listarSolicitacao() {
		String hql = "select f from Country f where f.situacao = 1 order by nome";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Country> countryList = query.list();
		return countryList;
	}


}
