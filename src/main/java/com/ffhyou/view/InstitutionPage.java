package com.ffhyou.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.ffhyou.model.Institution;
import com.ffhyou.model.User;
import com.ffhyou.service.InstitutionService;
import com.googlecode.genericdao.search.Search;


import net.sf.jasperreports.engine.JRException;

public class InstitutionPage extends BasePage {

	private static final long serialVersionUID = 1L;

	// private Form<Endereco> formEnd;
	private Form<Institution> form2;
	private List<Institution> listInstitution = new ArrayList<Institution>();
	private PageableListView<Institution> listView;
	private LoadableDetachableModel<List<Institution>> atualizarLista;
	private WebMarkupContainer listContainer = null;
	private ModalWindow modalWindow;
	private ModalWindow modalWindowDel;
	@SpringBean(name = "institutionService")
	private InstitutionService institutionService;
	private Institution filtrar;

	public InstitutionPage() {
		// TODO Auto-generated constructor stub

		listInstitution = institutionService.listar();
		// add(gerarExcel(listInstitution));
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

				InstitutionPanel institutionPanel = new InstitutionPanel(modalWindow.getContentId()) {

					private static final long serialVersionUID = 277997013286385910L;

					public void executarAoSalvar(AjaxRequestTarget target, Institution institution) {

						institutionService.SalvarOuAlterar(institution);
						listInstitution.add(institution);
						listInstitution = institutionService.listar();
						target.appendJavaScript("sucessCadastro();");
						target.add(listContainer);
						modalWindow.close(target);
					}

				};
				institutionPanel.setOutputMarkupId(true);
				add(institutionPanel);
				modalWindow.setContent(institutionPanel);
				modalWindow.show(target);
			};

		});

	}

	/*
	 * add(new AjaxLink<String>("viewLink") {
	 * 
	 * private static final long serialVersionUID = -7766269695313736383L;
	 * 
	 * @Override public void onClick(AjaxRequestTarget target) {
	 * 
	 * InstitutionPanel institutionPanel = new
	 * InstitutionPanel(modalWindow.getContentId()) {
	 * 
	 * private static final long serialVersionUID = 277997013286385910L;
	 * 
	 * public void executarAoSalvar(AjaxRequestTarget target, Institution
	 * institution) {
	 * 
	 * institutionService.SalvarOuAlterar(institution);
	 * listInstitution.add(institution); listInstitution =
	 * institutionService.listar(); target.appendJavaScript("sucessCadastro();");
	 * target.add(listContainer); modalWindow.close(target); };
	 * 
	 * }; institutionPanel.setOutputMarkupId(true); add(institutionPanel);
	 * modalWindow.setContent(institutionPanel); modalWindow.show(target); };
	 * 
	 * });
	 * 
	 */

	private WebMarkupContainer container() {

		listContainer = new WebMarkupContainer("theContainer");
		listContainer.setOutputMarkupId(true);

		atualizarLista = new LoadableDetachableModel<List<Institution>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Institution> load() {
				return listInstitution;
			}
		};

		listView = new PageableListView<Institution>("listView", atualizarLista, 5) {

			private static final long serialVersionUID = -8503564664744203394L;

			@Override
			protected void populateItem(ListItem<Institution> item) {
				Institution institution = item.getModelObject();
				item.add(new Label("name", institution.getName()));
				item.add(editando(institution));
				item.add(remover(institution.getId()));
			}
		};
		add(listView);
		listView.setOutputMarkupId(true);
		listContainer.add(listView);

		add(new PagingNavigator("pag", listView));
		return listContainer;

	}

	public Form<Institution> filtrar() {
		filtrar = new Institution();
		form2 = new Form<Institution>("form2", new CompoundPropertyModel<Institution>(filtrar));
		TextField<String> name = new TextField<String>("name");

		name.setOutputMarkupId(true);

		form2.add(name);
		AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("filtrar", form2) {

			private static final long serialVersionUID = 8104552052869900594L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Search search = new Search(Institution.class);

				if (filtrar.getName() != null && !filtrar.getName().equals("")) {
					search.addFilterLike("name", "%" + filtrar.getName() + "%");
				}
				/*
				 * else if (filtrar.getCliente().getNome() != null &&
				 * !filtrar.getCliente().getNome().equals("")) { search.addFilterLike("cliente",
				 * "%" + filtrar.getCliente().getNome() + "%"); }
				 */

				listInstitution = institutionService.search(search);
				target.add(listContainer);
				super.onSubmit(target, form);
			}

		};
		form2.setOutputMarkupId(true);
		form2.add(ajaxSubmitLink).setOutputMarkupId(true);
		return form2;

	}

	// Editando
	AjaxLink<Institution> editando(final Institution institution) {
		AjaxLink<Institution> editar = new AjaxLink<Institution>("update") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				InstitutionPanel institutionPanel = new InstitutionPanel(modalWindow.getContentId(), institution) {

					private static final long serialVersionUID = 1L;

					public void executarAoSalvar(AjaxRequestTarget target, Institution institution) {

						institutionService.SalvarOuAlterar(institution);
						listInstitution = institutionService.listar();
						target.appendJavaScript("sucessCadastro();");
						target.add(listContainer);
						modalWindow.close(target);
					};
				};
				institutionPanel.setOutputMarkupId(true);
				modalWindow.setContent(institutionPanel);
				modalWindow.show(target);
			}
		};
		editar.setOutputMarkupId(true);
		return editar;
	}

	// Removendo
	public Component remover(final Integer index) {

		AjaxLink<Institution> button = new AjaxLink<Institution>("delete") {
			Institution answer = new Institution();

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				DeleteInstitutionPanel deleteInstitutionPanel = new DeleteInstitutionPanel(
						modalWindowDel.getContentId(), answer) {

					private static final long serialVersionUID = 1L;

					public void executarAoExcluir(AjaxRequestTarget target, Institution institution) {
						if (institution.isAnswer() == true) {

							institutionService.excluir(index);
							listInstitution = institutionService.listar();
							target.appendJavaScript("sucessDelet();");
							target.add(listContainer);
						}
						modalWindowDel.close(target);
					};
				};
				deleteInstitutionPanel.setOutputMarkupId(true);
				modalWindowDel.setContent(deleteInstitutionPanel);
				modalWindowDel.show(target);
			}
		};
		button.setOutputMarkupId(true);
		return button;
	}

	/*
	 * Gerar file do excel public Link<?> gerarExcel(final List<Institution>
	 * institution) {
	 * 
	 * final RelatorioExcel relatorio = new RelatorioExcel();
	 * 
	 * Link<ByteArrayOutputStream> button = new Link<ByteArrayOutputStream>("excel")
	 * {
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @Override public void onClick() {
	 * 
	 * final ByteArrayOutputStream bytes = relatorio.gerarRelatorio(func); if (bytes
	 * != null) { AbstractResourceStreamWriter Stream = new
	 * AbstractResourceStreamWriter() {
	 * 
	 * private static final long serialVersionUID = 1L;
	 * 
	 * @Override public void write(OutputStream output) throws IOException {
	 * output.write(bytes.toByteArray()); output.close(); }
	 * 
	 * };
	 * 
	 * ResourceStreamRequestHandler handler = new
	 * ResourceStreamRequestHandler(Stream);
	 * handler.setContentDisposition(ContentDisposition.ATTACHMENT); // nome do
	 * excel handler.setFileName("Employees.xlsx");
	 * getRequestCycle().scheduleRequestHandlerAfterCurrent(handler); }
	 * 
	 * } }; button.setOutputMarkupId(true); add(button); return button; }
	 */

}
