package com.ffhyou.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import com.ffhyou.model.Institution;

public class DeleteInstitutionPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private Institution institution = new Institution();

	public DeleteInstitutionPanel(String id, final Institution answer) {
		super(id);
		this.institution = answer;
		Form<Institution> form = new Form<Institution>("resposta");

		add(new Label("msg", "Do you really want to delete this user?"));

		// Se a resposta == sim
		AjaxButton yesButton = new AjaxButton("sim") {

			private static final long serialVersionUID = 963978570032062983L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					institution.setAnswer(true);
					executarAoExcluir(target, institution);
				}
			}

		};

		// Se resposta == nao
		AjaxButton naoButton = new AjaxButton("nao") {

			private static final long serialVersionUID = -4614172469024292429L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (target != null) {
					institution.setAnswer(false);
					executarAoExcluir(target, institution);
				}

			}
		};
		add(form);
		yesButton.setOutputMarkupId(true);
		naoButton.setOutputMarkupId(true);

		form.add(yesButton);
		form.add(naoButton);
	}

	public void executarAoExcluir(AjaxRequestTarget target, Institution institution) {

	}

}
