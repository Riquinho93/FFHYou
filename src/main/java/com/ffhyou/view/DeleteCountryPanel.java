package com.ffhyou.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.ffhyou.model.Country;

public class DeleteCountryPanel extends Panel{
	
	private static final long serialVersionUID = 1L;
	private Country country = new Country();

	public DeleteCountryPanel(String id, final Country answer) {
		super(id);
		this.country = answer;
		Form<Country> form = new Form<Country>("resposta");

		add(new Label("msg", "Do you really want to delete this Country?"));

		// Se a resposta == sim
		AjaxButton yesButton = new AjaxButton("sim") {

			private static final long serialVersionUID = 963978570032062983L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					country.setAnswer(true);
					executarAoExcluir(target, country);
				}
			}

		};

		// Se resposta == nao
		AjaxButton naoButton = new AjaxButton("nao") {

			private static final long serialVersionUID = -4614172469024292429L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					country.setAnswer(false);
					executarAoExcluir(target, country);
				}

			}
		};
		add(form);
		yesButton.setOutputMarkupId(true);
		naoButton.setOutputMarkupId(true);

		form.add(yesButton);
		form.add(naoButton);
	}

	public void executarAoExcluir(AjaxRequestTarget target, Country country) {

	}



}
