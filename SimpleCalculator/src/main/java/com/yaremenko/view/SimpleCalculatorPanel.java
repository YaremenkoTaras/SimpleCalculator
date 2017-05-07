/**
 * 
 */
package com.yaremenko.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.yaremenko.controller.RPN;

/**
 * @author Taras
 *
 */
public class SimpleCalculatorPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2580941034262280164L;

	private Form<?> form = new Form<String>("calculatorForm");

	private final TextField<String> displayField = new TextField<>("displayField");

	private final TextArea<String> historyArea = new TextArea<>("historyArea");

	private final List<String> numbers = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

	private Symbol lastSymbol;

	private boolean dotPresent;

	private byte bracketCounter;

	public SimpleCalculatorPanel(String id) {
		super(id);

		lastSymbol = Symbol.DIGIT;
		dotPresent = false;
		bracketCounter = 0;

		displayField.setModel(Model.of("0"));
		displayField.setOutputMarkupId(true);

		historyArea.setModel(Model.of(""));
		historyArea.setOutputMarkupId(true);

		form.add(displayField);
		form.add(historyArea);
		
		initDigitButtons();
		initButtons();
		
		add(form);

	}

	@SuppressWarnings("serial")
	private void initDigitButtons() {
		for (final String number : numbers) {
			AjaxButton bttn = new AjaxButton(number) {
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					if (lastSymbol != Symbol.BRACKET_CLOSE) {
						String expression = displayField.getInput();
						if (expression.equals("0"))
							expression = number;
						else {
							expression += number;
						}
						lastSymbol = Symbol.DIGIT;
						displayField.setModelObject(expression);
						target.add(displayField);
					}
				};

			};
			form.add(bttn);
		}
	}

	@SuppressWarnings("serial")
	private void initButtons() {
		AjaxButton bttnDot = new AjaxButton("dot") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.DIGIT && !dotPresent) {
					lastSymbol = Symbol.DOT;
					String expression = displayField.getInput();
					expression += ".";
					dotPresent = true;
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};

		};
		
		AjaxButton bttnDel = new AjaxButton("del") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String expression = displayField.getInput();
				if (expression.length() > 1) {
					if (expression.charAt(expression.length() - 1) == '.')
						dotPresent = false;
					expression = expression.substring(0, expression.length() - 1);
					int i = expression.length();
					char c = expression.charAt(i - 1);
					switch (c) {
					case '(':
						lastSymbol = Symbol.BRACKET_OPEN;
						break;
					case ')':
						lastSymbol = Symbol.BRACKET_CLOSE;
						break;
					case '.':
						lastSymbol = Symbol.DOT;
						break;
					case '+':
					case '-':
					case '/':
					case '*':
					case '^':
						lastSymbol = Symbol.ACTION;
						break;
					default:
						lastSymbol = Symbol.DIGIT;
						break;
					}
				} else {
					expression = "0";
				}
				displayField.setModelObject(expression);
				target.add(displayField);
			};
		};

		final AjaxButton bttnAdd = new AjaxButton("add") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.DIGIT || lastSymbol == Symbol.BRACKET_CLOSE) {
					lastSymbol = Symbol.ACTION;
					dotPresent = false;
					String expression = displayField.getInput();
					expression += "+";
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};
		};
		
		AjaxButton bttnSub = new AjaxButton("sub") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.DIGIT || lastSymbol == Symbol.BRACKET_CLOSE) {
					lastSymbol = Symbol.ACTION;
					dotPresent = false;
					String expression = displayField.getInput();
					expression += "-";
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};
		};
		AjaxButton bttnDiv = new AjaxButton("div") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.DIGIT || lastSymbol == Symbol.BRACKET_CLOSE) {
					lastSymbol = Symbol.ACTION;
					dotPresent = false;
					String expression = displayField.getInput();
					expression += "/";
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};
		};
		AjaxButton bttnMul = new AjaxButton("mul") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.DIGIT || lastSymbol == Symbol.BRACKET_CLOSE) {
					lastSymbol = Symbol.ACTION;
					dotPresent = false;
					String expression = displayField.getInput();
					expression += "*";
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};
		};
		AjaxButton bttnPow = new AjaxButton("pow") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.DIGIT || lastSymbol == Symbol.BRACKET_CLOSE) {
					lastSymbol = Symbol.ACTION;
					dotPresent = false;
					String expression = displayField.getInput();
					expression += "^";
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};
		};
		
		AjaxButton bttnClear = new AjaxButton("clear") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				lastSymbol = Symbol.DIGIT;
				dotPresent = false;
				displayField.setModel(Model.of("0"));
				target.add(displayField);
			};
		};
		
		AjaxButton bttnClearHistory = new AjaxButton("clearHistory") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				historyArea.setModel(Model.of(""));
				target.add(historyArea);
			};
		};

		AjaxButton bttnBracketOpen = new AjaxButton("bracketOpen") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.ACTION) {
					lastSymbol = Symbol.BRACKET_OPEN;
					bracketCounter++;
					String expression = displayField.getInput();
					expression += "(";
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};
		};
		
		AjaxButton bttnBracketClose = new AjaxButton("bracketClose") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				if (lastSymbol == Symbol.DIGIT && bracketCounter > 0) {
					lastSymbol = Symbol.BRACKET_CLOSE;
					bracketCounter--;
					String expression = displayField.getInput();
					expression += ")";
					displayField.setModelObject(expression);
					target.add(displayField);
				}
			};
		};

		AjaxButton bttnResult = new AjaxButton("res") {
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String expression = displayField.getInput();
				if (bracketCounter > 0) {
					for (; bracketCounter > 0; bracketCounter--)
						expression += ")";
				}
				String history = historyArea.getInput();
				Double result = RPN.calculate(expression);

				expression += " = " + result;
				history += "\n" + expression;

				lastSymbol = Symbol.DIGIT;
				dotPresent = true;

				historyArea.setModelObject(history);
				displayField.setModelObject(result.toString());
				target.add(historyArea);
				target.add(displayField);
			};
		};

		form.add(bttnDot);
		form.add(bttnDel);
		form.add(bttnAdd);
		form.add(bttnSub);
		form.add(bttnMul);
		form.add(bttnDiv);
		form.add(bttnPow);
		form.add(bttnBracketOpen);
		form.add(bttnBracketClose);
		form.add(bttnResult);
		form.add(bttnClear);
		form.add(bttnClearHistory);
	}
	private enum Symbol {
		DIGIT, ACTION, BRACKET_OPEN, BRACKET_CLOSE, DOT
	}

}
