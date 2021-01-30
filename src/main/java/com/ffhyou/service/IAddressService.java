package com.ffhyou.service;

import java.util.List;

import com.ffhyou.model.Address;
import com.googlecode.genericdao.search.Search;

public interface IAddressService {
	
	public void SalvarOuAlterar(Address address);

	public Address buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<Address> listar();
	
	public List<Address> search(Search search);


}
