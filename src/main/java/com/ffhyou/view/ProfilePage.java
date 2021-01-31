package com.ffhyou.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

import com.ffhyou.model.User;

public class ProfilePage extends WebPage{
	
	
	private static final long serialVersionUID = 1L;

	public ProfilePage(User user) {
		
		add(new Label("name", user.getName()));
		add(new Label("institution", user.getInstitution().getName()));
		add(new Label("id", user.getId()));
		add(new Label("email", user.getEmail()));
		add(new Label("name2", user.getName()));
		add(new Label("country", user.getCountry().getName()));
		add(new Label("country2", user.getCountry().getName()));
		
		
		add(mainUserPage());
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

}
