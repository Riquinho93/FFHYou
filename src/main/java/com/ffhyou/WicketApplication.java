package com.ffhyou;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import com.ffhyou.view.HomePage;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.ffhyou.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	
	@Override
	public void init()
	{
		super.init();

		// add your configuration here
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		
		
	}
	
}
