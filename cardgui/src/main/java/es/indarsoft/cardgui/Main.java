package es.indarsoft.cardgui;

import org.eclipse.jface.window.*;
import org.eclipse.swt.widgets.*;

public class Main extends ApplicationWindow {

	public Main() {
		super(null);
	}

	protected Control createContents(Composite parent) {

		@SuppressWarnings("unused")
		MainComposite mc = new MainComposite(parent);
		return parent;
	}

	public static void main(String[] args) {
		Main mn = new Main();
		mn.setBlockOnOpen(true);
		mn.open();
		Display.getCurrent().dispose();
	}

}