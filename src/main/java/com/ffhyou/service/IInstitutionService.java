package com.ffhyou.service;

import java.util.List;

import com.ffhyou.model.Institution;
import com.googlecode.genericdao.search.Search;

public interface IInstitutionService {
	public void SalvarOuAlterar(Institution institution);

	public Institution buscarPorId(Integer id);

	public void excluir(Integer id);

	public List<Institution> listar();
	
	public List<Institution> search(Search search);

}
