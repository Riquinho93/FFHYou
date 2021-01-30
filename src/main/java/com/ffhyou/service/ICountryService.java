package com.ffhyou.service;

import java.util.List;

import com.ffhyou.model.Country;
import com.googlecode.genericdao.search.Search;

public interface ICountryService {
	
	public void SalvarOuAlterar(Country country);

	public Country buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<Country> listar();
	
	public List<Country> search(Search search);

}
