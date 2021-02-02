package com.ffhyou.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.ffhyou.Login;
import com.ffhyou.model.User;
import com.ffhyou.service.UserService;
import com.googlecode.genericdao.search.Search;

public class MainUserPage extends WebPage {
	
	private static final long serialVersionUID = 1L;
	
	private Form<User> form2;
	private List<User> listUser = new ArrayList<User>();
	private PageableListView<User> listView;
	private LoadableDetachableModel<List<User>> atualizarLista;
	private WebMarkupContainer listContainer = null;
	

	@SpringBean(name = "userService")
	private UserService userService;
	private User filtrar;
	private User user2;
	
	public MainUserPage() {
		this(new User());
	}

	public MainUserPage(User user) {
		User userName = (User) getSession().getAttribute("userName");
		if (userName == null) {
			setResponsePage(Login.class);
			return;
		}
		user2=user;
		listUser = userService.listar();
		add(container());
		add(filtrar());
		
		add(new Link<Void>("exit") {

			private static final long serialVersionUID = 1L;

			public void onClick() {
				getSession().invalidate();
				setResponsePage(HomePage.class);
			}
		});
		
		add(mainUserPage());
		add(profilePage());

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
				item.add(new Label("country", user.getCountry().getName()));
				item.add(new Label("institution", user.getInstitution().getName()));
				item.add(new Label("email", user.getEmail()));
				
			}
		};
		add(listView);
		listView.setOutputMarkupId(true);
		listContainer.add(listView);

		add(new PagingNavigator("pag", listView));
		return listContainer;

	}

	
	private AjaxLink<MainUserPage> mainUserPage() {
		AjaxLink<MainUserPage> ajaxLink = new AjaxLink<MainUserPage>("mainUserPage") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(MainUserPage.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}
	private AjaxLink<ProfilePage> profilePage() {
		AjaxLink<ProfilePage> ajaxLink = new AjaxLink<ProfilePage>("profilePage") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(new ProfilePage(user2));
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

	public Form<User> filtrar() {
		filtrar = new User();
		form2 = new Form<User>("form2", new CompoundPropertyModel<User>(filtrar));
	final TextField<String> name = new TextField<String>("name");
	
		name.setOutputMarkupId(true);

		form2.add(name);
		AjaxSubmitLink ajaxSubmitLink = new AjaxSubmitLink("filtrar", form2) {

			private static final long serialVersionUID = 8104552052869900594L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Search search = new Search(User.class);
				/*listUser  =  userService.listarCountry(country);
				for(int i=0; i < listUser.size(); i++) {
					System.out.println(listUser.get(i).getName());
				}*/
				//User userAux;
				//List<User> listUserAux = new ArrayList<User>();
				
				List<User> listUserAux = new ArrayList<User>();
				System.out.println("QUANTIDADE " + listUser.size());
				if(listUser.size()==0) {
					listUser = userService.listar();
					System.out.println("QUANTIDADE2 " + listUser.size());
					target.add(listContainer);
					
				}else {
					for(int i=0;i<listUser.size();i++) {
						User userAux = (User) listUser.get(i);
						if (userAux.getCountry().getName().equals(name.getValue())) {
							listUserAux.add(userAux);
						}
						System.out.println("NOME "+ userAux.getCountry().getName());
						System.out.println("SIZE "+ listUser.size());
						System.out.println("Name "+ name.getValue());
						System.out.println("SIZE2 "+ listUserAux.size());
					}	
				}
				
				
				//if (filtrar.getCountry().getClass().getName() != null && !filtrar.getCountry().getClass().getName().equals("")) {
					//search.addFilterLike("country", "country.name" + filtrar.getCountry().getName() + "%");
				//}
				/*
				 * else if (filtrar.getCliente().getNome() != null &&
				 * !filtrar.getCliente().getNome().equals("")) { search.addFilterLike("cliente",
				 * "%" + filtrar.getCliente().getNome() + "%"); }
				 */

				listUser = listUserAux;
				target.add(listContainer);
				//listUser = userService.listar();
				super.onSubmit(target, form);
			}

		};
		form2.setOutputMarkupId(true);
		form2.add(ajaxSubmitLink).setOutputMarkupId(true);
		return form2;

	}


}
