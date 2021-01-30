package com.ffhyou.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

import com.ffhyou.model.Institution;

public class InstitutionDao extends GenericDao<Institution, Serializable>{
	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<Institution> listar() {
		String hql = "select f from Institution f order by name";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Institution> institutionList = query.list();
		return institutionList;
	}
//	@Transactional(readOnly = true)
	public Institution alterar(Integer id) {
		String hql = "select f from Institution f";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		Institution institution = (Institution) query.uniqueResult();
		return institution;
	}
	
	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<Institution> listarSolicitacao() {
		String hql = "select f from Institution f where f.situacao = 1 order by nome";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Institution> countryList = query.list();
		return countryList;
	}


}
