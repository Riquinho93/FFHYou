package com.ffhyou.view;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import com.ffhyou.Login;
import com.ffhyou.model.User;

public class HeadMainUserPage extends WebPage {
	
	public HeadMainUserPage() {
		User userName = (User) getSession().getAttribute("userName");
		if (userName == null) {
			setResponsePage(Login.class);
			return;
		}
		
		add(new Link<Void>("exit") {

			private static final long serialVersionUID = 1L;

			public void onClick() {
				getSession().invalidate();
				setResponsePage(HomePage.class);
			}
		});
		
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
