package com.ffhyou;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.ffhyou.model.Country;
import com.ffhyou.model.Institution;
import com.ffhyou.model.User;
import com.ffhyou.service.CountryService;
import com.ffhyou.service.InstitutionService;
import com.ffhyou.service.UserService;


public class Register extends WebPage {

	private Form<User> form;
	private User user;
	private Country country;
	private List<Country> listCountries;
	private List<Institution> listInstitutions;
	
	@SpringBean(name = "userService")
	private UserService userService;
	
	@SpringBean(name = "countryService")
	private CountryService countryService;
	
	@SpringBean(name = "institutionService")
	private InstitutionService institutionService;

	public Register() {

		user = new User();
		country = new Country();
		listCountries = countryService.listar();
		listInstitutions = institutionService.listar();
		form = new Form<User>("form", new CompoundPropertyModel<User>(user));
		TextField<String> name = new TextField<String>("name");
		final TextField<String> email = new TextField<String>("email");
		final PasswordTextField password = new PasswordTextField("password");
		final PasswordTextField confirmarpassword = new PasswordTextField("confirmarPassword");
		// Select Countty
		DropDownChoice<Country> countries = new DropDownChoice<Country>("country",
				new PropertyModel<Country>(user, "country"), listCountries, new ChoiceRenderer<Country>("name"));
		
		// Select Insitution
				DropDownChoice<Institution> institutions = new DropDownChoice<Institution>("institution",
						new PropertyModel<Institution>(user, "institution"), listInstitutions, new ChoiceRenderer<Institution>("name"));

		name.setOutputMarkupId(true);
		email.setOutputMarkupId(true);
		//countries.setOutputMarkupId(true);
		password.setOutputMarkupId(true);
		confirmarpassword.setOutputMarkupId(true);
		name.setRequired(true);
		email.setRequired(true);
		//countries.setRequired(true);
		password.setRequired(true);
		confirmarpassword.setRequired(true);
		user.setPerfil(user.getPerfil());

		form.add(name, email,countries, institutions,password, confirmarpassword);

		AjaxButton button = new AjaxButton("submit") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				User user = (User) form.getModelObject();
				user.setPerfil(com.ffhyou.model.Perfil.ADMIN);
				userService.SalvarOuAlterar(user);
				user = new User();
				form.clearInput();
				form.modelChanged();
				form.setDefaultModelObject(user);
				target.add(email, password,confirmarpassword);

				setResponsePage(Login.class);
			}

		};

		button.setOutputMarkupId(true);
		form.add(button);
		add(form);
		voltar();


	}

	private void voltar() {
		AjaxLink<Login> ajaxLink = new AjaxLink<Login>("voltar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(Login.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		form.add(ajaxLink);
	}

}
