package com.indarsoft.cardgui;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class FrmControls {

	private static final String EMPTY = "";
	private ArrayList<Control> ctrl = new ArrayList<Control>();
	private Integer count = 0;

	public FrmControls(Composite c) {

		setIds(c);

	}

	private void setIds(Composite cmp) {

		Control[] children = cmp.getChildren();
		for (int j = 0; j < children.length; j++) {
			if (children[j] instanceof Composite) {
				setIds((Composite) children[j]);
			} else {
				Control d = children[j];
				d.setData(count);
				ctrl.add(d);
				// System.out.println("control ID/name : " + d.getData() +"-" +
				// d.toString());
				++count;
			}
		}
	}

	public void clearFields() {
		for (int j = 0; j < ctrl.size(); j++) {
			Control c = ctrl.get(j);
			String str = c.getClass().toString();
			// System.out.println("control class   : " + str ) ;
			if (str.equals("class org.eclipse.swt.widgets.Text")) {
				Text txt = (Text) c;
				txt.setText(EMPTY);
			}
		}
	}
}
