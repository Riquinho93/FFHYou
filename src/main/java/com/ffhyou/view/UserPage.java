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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.ffhyou.BasePage;
import com.ffhyou.model.Country;
import com.ffhyou.model.Institution;
import com.ffhyou.model.User;
import com.ffhyou.service.InstitutionService;
import com.ffhyou.service.UserService;
import com.googlecode.genericdao.search.Search;

public class UserPage extends BasePage {

	private static final long serialVersionUID = 1L;

	// private Form<Endereco> formEnd;
	private Form<User> form2;
	private List<User> listUser = new ArrayList<User>();
	private PageableListView<User> listView;
	private LoadableDetachableModel<List<User>> atualizarLista;
	private WebMarkupContainer listContainer = null;
	private ModalWindow modalWindow;
	private ModalWindow modalWindowDel;

	@SpringBean(name = "userService")
	private UserService userService;
	private User filtrar;

	public UserPage() {
		// TODO Auto-generated constructor stub
		listUser = userService.listar();

		add(container());
		add(filtrar());

		modalWindow = new ModalWindow("modalWindow");
		// Tamanho do Modal
		modalWindow.setInitialHeight(750);
		modalWindow.setInitialWidth(800);
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

				UserPanel userPanel = new UserPanel(modalWindow.getContentId()) {

					private static final long serialVersionUID = 277997013286385910L;

					public void executarAoSalvar(AjaxRequestTarget target, User user) {

						// userService.SalvarOuAlterar(user);
						listUser.add(user);
						// listUser = userService.listar();
						target.appendJavaScript("sucessCadastro();");
						target.add(listContainer);
						modalWindow.close(target);
					}

				};
				userPanel.setOutputMarkupId(true);
				add(userPanel);
				modalWindow.setContent(userPanel);
				modalWindow.show(target);
			};

		});

	}

	private WebMarkupContainer container() {

		listContainer = new WebMarkupContainer("theContainer");
		listContainer.setOutputMarkupId(true);

		atualizarLista = new LoadableDetachableModel<List<User>>() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<User> load() {
				return listUser;
			}
		};

		listView = new PageableListView<User>("listView", atualizarLista, 5) {

			private static final long serialVersionUID = -8503564664744203394L;

			@Override
			protected void populateItem(ListItem<User> item) {
				User user = item.getModelObject();
				item.add(new Label("name", user.getName()));
				item.add(editando(user));
				item.add(remover(user.getId()));
			}
		};
		add(listView);
		listView.setOutputMarkupId(true);
		listContainer.add(listView);

		add(new PagingNavigator("pag", listView));
		return listContainer;

	}

	public Form<User> filtrar() {
		filtrar = new User();
		form2 = new Form<User>("form2", new CompoundPropertyModel<User>(filtrar));
		TextField<String> name = new TextField<String>("name");

		name.setOutputMarkupId(true);

		form2.add(name);
		AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("filtrar", form2) {

			private static final long serialVersionUID = 8104552052869900594L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Search search = new Search(User.class);

				if (filtrar.getName() != null && !filtrar.getName().equals("")) {
					search.addFilterLike("name", "%" + filtrar.getName() + "%");
				}
				/*
				 * else if (filtrar.getCliente().getNome() != null &&
				 * !filtrar.getCliente().getNome().equals("")) { search.addFilterLike("cliente",
				 * "%" + filtrar.getCliente().getNome() + "%"); }
				 */

				listUser = userService.search(search);
				target.add(listContainer);
				super.onSubmit(target, form);
			}

		};
		form2.setOutputMarkupId(true);
		form2.add(ajaxSubmitLink).setOutputMarkupId(true);
		return form2;

	}
	
	// Editando
			AjaxLink<User> editando(final User user) {
				AjaxLink<User> editar = new AjaxLink<User>("update") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						UserPanel userPanel = new UserPanel(modalWindow.getContentId(), user) {

							private static final long serialVersionUID = 1L;

							public void executarAoSalvar(AjaxRequestTarget target, User user) {

								userService.SalvarOuAlterar(user);
								listUser = userService.listar();
								target.appendJavaScript("sucessCadastro();");
								target.add(listContainer);
								modalWindow.close(target);
							};
						};
						userPanel.setOutputMarkupId(true);
						modalWindow.setContent(userPanel);
						modalWindow.show(target);
					}
				};
				editar.setOutputMarkupId(true);
				return editar;
			}


	// Removendo
	public Component remover(final Integer index) {

		AjaxLink<User> button = new AjaxLink<User>("delete") {
			User answer = new User();

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				DeleteUserPanel deleteUserPanel = new DeleteUserPanel(modalWindowDel.getContentId(), answer) {

					private static final long serialVersionUID = 1L;

					public void executarAoExcluir(AjaxRequestTarget target, User user) {
						if (user.isAnswer() == true) {
							// enderecoService.excluir(index);
							userService.excluir(index);
							listUser = userService.listar();
							target.appendJavaScript("sucessDelet();");
							target.add(listContainer);
						}
						modalWindowDel.close(target);
					};
				};
				deleteUserPanel.setOutputMarkupId(true);
				modalWindowDel.setContent(deleteUserPanel);
				modalWindowDel.show(target);
			}
		};
		button.setOutputMarkupId(true);
		return button;
	}

}
