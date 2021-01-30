package com.ffhyou.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ffhyou.model.Address;

@Repository
public class AddressDao extends GenericDao<Address, Serializable>{
	
	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
	public List<Address> listar() {
		String hql = "select f from Address f order by street";
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		List<Address> addressList = query.list();
		return addressList;
	}
//	@Transactional(readOnly = true)
	public Address alterar(Integer id) {
		String hql = "select f from Address f";
		
		Query query = getSessionFactory().getCurrentSession().createQuery(hql);
		query.setParameter("id", id);
		Address address = (Address) query.uniqueResult();
		return address;
	}

}
