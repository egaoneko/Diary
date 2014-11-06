/**
Copyright 2004 Juan Heyns. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY JUAN HEYNS ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JUAN HEYNS OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of Juan Heyns.
*/
package org.jdatepicker;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFormattedTextField;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;

/**
 * Created 18 April 2010
 * Updated 10 August 2012
 * 
 * @author Juan Heyns
 */
public abstract class JDateComponentFactory<T> {

	/**
	 * Create a factory which will construct widgets with the provided date
	 * model, date formatter, i18nStrings or colors.
	 * 
	 */
	public JDateComponentFactory() {
	}
	
	/**
	 * Create a DateModel initialised to today, based on the clazz type.
	 * 
	 * @return
	 */
	public abstract DateModel<T> createModel();

	/**
	 * Create a DateModel based on the type of the value.
	 * 
	 * @param value
	 * @return
	 */
	public abstract DateModel<T> createModel(T value);

	/**
	 * Create with factory dateModel, i18nStrings and dateFormatter.
	 * 
	 * @return
	 */
	public JDatePicker createJDatePicker() {
		DateModel<T> model = createModel();
		return new JDatePickerImpl(new JDatePanelImpl(model));
	}
	
	/**
	 * Create by specifying the initial value of the widget.
	 * 
	 * @param value
	 * @return
	 */
	public JDatePicker createJDatePicker(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Value may not be null.");
		}
		DateModel<T> model = createModel(value);
		return new JDatePickerImpl(new JDatePanelImpl(model));
	}
	
	/**
	 * Create with factory dateModel, i18nStrings and dateFormatter.
	 * 
	 * @return
	 */
	public JDatePanel createJDatePanel() {
		DateModel<T> model = createModel();
		return new JDatePanelImpl(model);
	}
	
	/**
	 * Create by specifying the initial value of the widget.
	 * 
	 * @param value
	 * @return
	 */
	public JDatePanel createJDatePanel(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Value may not be null.");
		}
		DateModel<T> model = createModel(value);
		return new JDatePanelImpl(model);
	}
	
}
