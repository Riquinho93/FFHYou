package com.ffhyou.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import com.ffhyou.model.Country;
import com.ffhyou.model.Institution;

public class InstitutionPanel  extends Panel{
	
	
	private static final long serialVersionUID = 1L;
	
	private Form<Institution> form;
	
	public InstitutionPanel(String id) {
		this(id, new Institution());
		
	}
	
	public InstitutionPanel(String id, final Institution institution) {
		super(id);
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		form = new Form<Institution>("form", new CompoundPropertyModel<Institution>(institution));
		final TextField<String> name = new TextField<String>("name");

		name.setOutputMarkupId(true);

		name.setRequired(true);

		form.add(name);

		AjaxButton button = new AjaxButton("submit") {

			private static final long serialVersionUID = 994698440577863113L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				target.add(name);
				executarAoSalvar(target, institution);

				

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
	public void executarAoSalvar(AjaxRequestTarget target, Institution institution) {

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
