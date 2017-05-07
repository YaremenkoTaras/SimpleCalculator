/**
 * 
 */
package com.yaremenko.view;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Taras
 *
 */
@SuppressWarnings("serial")
public class HomePage extends WebPage {
	
	public HomePage() {
		add(new SimpleCalculatorPanel("panel"));
	}

}
