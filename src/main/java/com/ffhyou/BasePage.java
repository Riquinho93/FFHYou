package com.ffhyou;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import com.ffhyou.model.User;
import com.ffhyou.view.CountryPage;
import com.ffhyou.view.HomePage;
import com.ffhyou.view.InstitutionPage;
import com.ffhyou.view.UserPage;



public class BasePage extends WebPage {
	
	
	private static final long serialVersionUID = 1L;

	public BasePage() {
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

		
		add(institutionPage());
		add(countryPage());
		add(homePage());
		add(userPage());
	}
	
	protected AjaxLink<InstitutionPage> institutionPage() {
		AjaxLink<InstitutionPage> ajaxLink = new AjaxLink<InstitutionPage>("institutionPage") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(InstitutionPage.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}

	private AjaxLink<CountryPage> countryPage() {
		AjaxLink<CountryPage> ajaxLink = new AjaxLink<CountryPage>("countryPage") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CountryPage.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}
	
	private AjaxLink<HomePage> homePage() {
		AjaxLink<HomePage> ajaxLink = new AjaxLink<HomePage>("homePage") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(HomePage.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}
	
	private AjaxLink<HomePage> userPage() {
		AjaxLink<HomePage> ajaxLink = new AjaxLink<HomePage>("userPage") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(UserPage.class);
			}
		};
		ajaxLink.setOutputMarkupId(true);
		add(ajaxLink);
		return ajaxLink;
	}


}
