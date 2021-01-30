package com.ffhyou.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffhyou.dao.InstitutionDao;
import com.ffhyou.model.Institution;
import com.googlecode.genericdao.search.Search;

@Service
public class InstitutionService implements IInstitutionService {
	
	private InstitutionDao institutionDao;

	public void setInstitutionDao(InstitutionDao institutionDao) {
		this.institutionDao = institutionDao;
	}

	@Override
	@Transactional
	public void SalvarOuAlterar(Institution institution) {
		institutionDao.SalvarOuAlterar(institution);

	}

	@Override
	@Transactional(readOnly = true)
	public Institution buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return institutionDao.buscarPorId(id);
	}

	@Override
	@Transactional
	public void excluir(Integer id) {
		institutionDao.excluir(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Institution> listar() {
		// TODO Auto-generated method stub
		return institutionDao.listar();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Institution> search(Search search) {
		// TODO Auto-generated method stub
		return institutionDao.searchDao(search);
	}

}
