package com.ffhyou.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffhyou.dao.CountryDao;
import com.ffhyou.model.Country;
import com.googlecode.genericdao.search.Search;

@Service
public class CountryService implements ICountryService {

	private CountryDao countryDao;
	
	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(Country country) {
		countryDao.SalvarOuAlterar(country);

	}

	@Override
	@Transactional(readOnly = true)
	public Country buscarPorId(Integer id) {
		
		return countryDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		countryDao.excluir(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Country> listar() {
		
		return countryDao.listar();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Country> search(Search search) {
		
		return countryDao.searchDao(search);
	}

}
