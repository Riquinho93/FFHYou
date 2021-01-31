package com.ffhyou;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.ffhyou.model.Perfil;
import com.ffhyou.model.User;
import com.ffhyou.service.AlertFeedback;
import com.ffhyou.service.UserService;
import com.ffhyou.view.HomePage;
import com.ffhyou.view.MainUserPage;
import com.googlecode.genericdao.search.Search;

public class Login extends WebPage{
	
	private static final long serialVersionUID = 1L;
	private Form<User> formularioLogin;
	private User filtrarUsuario;
	@SpringBean(name = "userService")
	private UserService userService;

	
	public Login() {
		
		final AlertFeedback alertFeedback = new AlertFeedback("feedbackMessage");

		add(register());

		filtrarUsuario = new User();
		final TextField<String> email = new TextField<String>("email");
		final PasswordTextField password = new PasswordTextField("password");
		email.setRequired(true);
		password.setRequired(true);
		email.setOutputMarkupId(true);
		password.setOutputMarkupId(true);
		
		formularioLogin = new Form<User>("formularioLogin", new CompoundPropertyModel<User>(filtrarUsuario)) {

			private static final long serialVersionUID = -5095534494215850537L;

			@Override
			protected void onSubmit() {
				super.onSubmit();
				Search search = new Search(User.class);

				search.addFilterEqual("email", email.getModelObject());
				search.addFilterEqual("password", password.getModelObject());

				List<User> lista = userService.search(search);

				if (lista != null && !lista.isEmpty()) {
					User user = lista.get(0);
					Integer id = user.getId();
					User userPerfil =  userService.buscarPorId(id);
					
					getSession().setAttribute("userName", lista.get(0));
					userPerfil.getPerfil();
					/*if(user.getPerfil().equals(user.getPerfil().ADMIN)) {
						setResponsePage(new TelaPrincipal(usuario));
					}else {
						setResponsePage(new EmployeePage(funcionario));
					}*/
					//setResponsePage(new HomePage(user));
					if(user.getPerfil().equals(Perfil.ADMIN)) {
						setResponsePage(new HomePage(user));	
					}else {
						setResponsePage(new MainUserPage(user));
					}
					
				} else {

					alertFeedback.error("Login Incorreto");
//					errorLogin.setVisible(true);
				} 

			}

		};
		formularioLogin.add(email, password).setOutputMarkupId(true);
		add(alertFeedback);
		add(formularioLogin);
		
		
	}
	
	private AjaxLink<Register> register() {
		AjaxLink<Register> ajaxLink = new AjaxLink<Register>("register") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(Register.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

}
