package com.ffhyou.view;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.ffhyou.model.Country;
import com.ffhyou.model.Institution;
import com.ffhyou.model.User;
import com.ffhyou.service.CountryService;
import com.ffhyou.service.InstitutionService;
import com.ffhyou.service.UserService;


public class UserPanel extends Panel{
	
	
	private static final long serialVersionUID = 1L;
	
	private Form<User> form;
	private Country country;
	private List<Country> listCountries;
	private List<Institution> listInstitutions;
	
	@SpringBean(name = "userService")
	private UserService userService;
	
	@SpringBean(name = "countryService")
	private CountryService countryService;
	
	@SpringBean(name = "institutionService")
	private InstitutionService institutionService;
	
	public UserPanel(String id) {
		this(id, new User());
		
	}
	
	public UserPanel(String id, final User user) {
		super(id);
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);
		country = new Country();
		listCountries = countryService.listar();
		listInstitutions = institutionService.listar();
		form = new Form<User>("form", new CompoundPropertyModel<User>(user));
		final TextField<String> name = new TextField<String>("name");
		final TextField<String> email = new TextField<String>("email");
		final PasswordTextField password = new PasswordTextField("password");
		final PasswordTextField confirmarpassword = new PasswordTextField("confirmarPassword");
		// Select Country

		DropDownChoice<Country> countries = new DropDownChoice<Country>("country",
				new PropertyModel<Country>(user, "country"), listCountries, new ChoiceRenderer<Country>("name"));
		// Select Institution
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

		//form.add(name, email,countries, password, confirmarpassword);
		
		final RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sexo");
		radioGroupAtivo.setRequired(true);
		radioGroupAtivo
				.add(new Radio<Boolean>("sim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sim")));
		radioGroupAtivo
				.add(new Radio<Boolean>("nao", new Model<Boolean>(false)).add(new AttributeModifier("id", "nao")));
		form.add(radioGroupAtivo);


		AjaxButton button = new AjaxButton("submit") {

			private static final long serialVersionUID = 994698440577863113L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				target.add(name, email, password, confirmarpassword);
				executarAoSalvar(target, user);

				

				target.add(feedbackPanel);

		 	}
		};
		add(feedbackPanel);
		button.setOutputMarkupId(true);
		form.add(name, email,countries,institutions, password, confirmarpassword);
		form.add(button);
		add(form);
		voltar();

		
		
		
	}
	
	// Enviando os dados para o HomePage
	public void executarAoSalvar(AjaxRequestTarget target, User user) {

	}

	private void voltar() {
		AjaxLink<InstitutionPage> ajaxLink = new AjaxLink<InstitutionPage>("voltar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(InstitutionPage.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		form.add(ajaxLink);
	}



}
