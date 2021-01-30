package com.ffhyou.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStreamWriter;

import com.ffhyou.BasePage;
import com.ffhyou.model.Country;
import com.ffhyou.model.Institution;
import com.ffhyou.model.User;
import com.ffhyou.service.CountryService;
import com.googlecode.genericdao.search.Search;

public class CountryPage extends BasePage {

	private static final long serialVersionUID = 1L;

	private Form<Country> form2;
	private List<Country> listCountry = new ArrayList<Country>();
	private PageableListView<Country> listView;
	private LoadableDetachableModel<List<Country>> atualizarLista;
	private WebMarkupContainer listContainer = null;
	private ModalWindow modalWindow;
	private ModalWindow modalWindowDel;
	@SpringBean(name = "countryService")
	private CountryService countryService;

	private Country filtrar;

	public CountryPage() {

		listCountry = countryService.listar();

		add(filtrar());

		// Chamando a listView
		add(container());

		modalWindow = new ModalWindow("modalWindow");
		// Tamanho do Modal
		modalWindow.setInitialHeight(350);
		modalWindow.setInitialWidth(350);
		modalWindow.setOutputMarkupId(true);
		add(modalWindow);

		// Modal Window do delete
		modalWindowDel = new ModalWindow("modalWindowDel");
		// Tamanho
		modalWindowDel.setInitialHeight(250);
		modalWindowDel.setInitialWidth(350);
		modalWindowDel.setOutputMarkupId(true);
		add(modalWindowDel);
		// Criando o botao para o Modal
		add(new AjaxLink<String>("viewLink") {

			private static final long serialVersionUID = -7766269695313736383L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				CountryPanel countryPanel = new CountryPanel(modalWindow.getContentId()) {

					private static final long serialVersionUID = 277997013286385910L;

					public void executarAoSalvar(AjaxRequestTarget target, Country country) {

						countryService.SalvarOuAlterar(country);
						listCountry.add(country);
						listCountry = countryService.listar();
						target.appendJavaScript("sucessCadastro();");
						target.add(listContainer);
						modalWindow.close(target);
					}

				};
				countryPanel.setOutputMarkupId(true);
				add(countryPanel);
				modalWindow.setContent(countryPanel);
				modalWindow.show(target);
			};

		});

	}

	private WebMarkupContainer container() {

		listContainer = new WebMarkupContainer("theContainer");
		listContainer.setOutputMarkupId(true);

		atualizarLista = new LoadableDetachableModel<List<Country>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Country> load() {
				return listCountry;
			}
		};

		listView = new PageableListView<Country>("listView", atualizarLista, 5) {

			private static final long serialVersionUID = -8503564664744203394L;

			@Override
			protected void populateItem(ListItem<Country> item) {
				Country country = item.getModelObject();
				item.add(new Label("name", country.getName()));
				item.add(editando(country));
				item.add(remover(country.getId()));
			}
		};
		add(listView);
		listView.setOutputMarkupId(true);
		listContainer.add(listView);

		add(new PagingNavigator("pag", listView));
		return listContainer;

	}

	public Form<Country> filtrar() {
		filtrar = new Country();
		form2 = new Form<Country>("form2", new CompoundPropertyModel<Country>(filtrar));
		TextField<String> name = new TextField<String>("name");

		name.setOutputMarkupId(true);

		form2.add(name);
		AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("filtrar", form2) {

			private static final long serialVersionUID = 8104552052869900594L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Search search = new Search(Country.class);

				if (filtrar.getName() != null && !filtrar.getName().equals("")) {
					search.addFilterLike("name", "%" + filtrar.getName() + "%");
				}
				/*
				 * else if (filtrar.getCliente().getNome() != null &&
				 * !filtrar.getCliente().getNome().equals("")) { search.addFilterLike("cliente",
				 * "%" + filtrar.getCliente().getNome() + "%"); }
				 */

				listCountry = countryService.search(search);
				target.add(listContainer);
				super.onSubmit(target, form);
			}

		};
		form2.setOutputMarkupId(true);
		form2.add(ajaxSubmitLink).setOutputMarkupId(true);
		return form2;

	}
	
	// Editando
		AjaxLink<Country> editando(final Country country) {
			AjaxLink<Country> editar = new AjaxLink<Country>("update") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					CountryPanel countryPanel = new CountryPanel(modalWindow.getContentId(), country) {

						private static final long serialVersionUID = 1L;

						public void executarAoSalvar(AjaxRequestTarget target, Country country) {

							countryService.SalvarOuAlterar(country);
							listCountry = countryService.listar();
							target.appendJavaScript("sucessCadastro();");
							target.add(listContainer);
							modalWindow.close(target);
						};
					};
					countryPanel.setOutputMarkupId(true);
					modalWindow.setContent(countryPanel);
					modalWindow.show(target);
				}
			};
			editar.setOutputMarkupId(true);
			return editar;
		}


	// Removendo
	public Component remover(final Integer index) {

		AjaxLink<Country> button = new AjaxLink<Country>("delete") {
			Country answer = new Country();

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				DeleteCountryPanel deleteCountryPanel = new DeleteCountryPanel(modalWindowDel.getContentId(), answer) {

					private static final long serialVersionUID = 1L;

					public void executarAoExcluir(AjaxRequestTarget target, Country country) {
						if (country.isAnswer() == true) {

							countryService.excluir(index);
							listCountry = countryService.listar();
							target.appendJavaScript("sucessDelet();");
							target.add(listContainer);
						}
						modalWindowDel.close(target);
					};
				};
				deleteCountryPanel.setOutputMarkupId(true);
				modalWindowDel.setContent(deleteCountryPanel);
				modalWindowDel.show(target);
			}
		};
		button.setOutputMarkupId(true);
		return button;
	}

}
