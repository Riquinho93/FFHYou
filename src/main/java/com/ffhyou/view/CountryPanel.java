package com.ffhyou.view;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import com.ffhyou.model.Country;

public class CountryPanel extends Panel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Form<Country> form;

	public CountryPanel(String id) {
		this(id, new Country());
	}

	public CountryPanel(String id, final Country country) {
		super(id);

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		form = new Form<Country>("form", new CompoundPropertyModel<Country>(country));
		final TextField<String> name = new TextField<String>("name");

		name.setOutputMarkupId(true);

		name.setRequired(true);

		form.add(name);

		AjaxButton button = new AjaxButton("submit") {

			private static final long serialVersionUID = 994698440577863113L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				executarAoSalvar(target, country);
				target.add(name);
				

				target.add(feedbackPanel);

		 	}
		};
		add(feedbackPanel);
		button.setOutputMarkupId(true);
		form.add(name);
		form.add(button);
		add(form);
		voltar();

	}

	// Enviando os dados para o HomePage
	public void executarAoSalvar(AjaxRequestTarget target, Country country) {

	}

	private void voltar() {
		AjaxLink<CountryPage> ajaxLink = new AjaxLink<CountryPage>("voltar") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CountryPage.class);
			}
		};

		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		form.add(ajaxLink);
	}

}
