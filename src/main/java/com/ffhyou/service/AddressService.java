package com.ffhyou.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffhyou.dao.AddressDao;
import com.ffhyou.model.Address;
import com.googlecode.genericdao.search.Search;

@Service
public class AddressService implements IAddressService {

	private AddressDao addressDao;
	
	
	public void setAddressDao(AddressDao addressDao) {
		this.addressDao = addressDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(Address address) {
		addressDao.SalvarOuAlterar(address);

	}

	@Override
	@Transactional(readOnly = true)
	public Address buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return addressDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		addressDao.excluir(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Address> listar() {
		// TODO Auto-generated method stub
		return addressDao.listar();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Address> search(Search search) {
		// TODO Auto-generated method stub
		return addressDao.searchDao(search);
	}

}
