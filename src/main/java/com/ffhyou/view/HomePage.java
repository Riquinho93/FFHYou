package com.ffhyou.view;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.ffhyou.BasePage;
import com.ffhyou.Login;
import com.ffhyou.model.User;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends BasePage {
	
	private static final long serialVersionUID = 1L;

	public HomePage() {
		this(new User());
	}
	public HomePage(User user) {

		//setResponsePage(Login.class);

		// TODO Add your page's components here

    }
}
