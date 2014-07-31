package es.indarsoft.cardgui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import es.indarsoft.cardgui.card.CardFrm;

public class MainComposite extends Composite {

	protected CardFrm cardfrm;

	// protected LoadKeysFrm lkfrm ;
	// protected String panNumber = "" ;

	public MainComposite(Composite parent) {

		super(parent, SWT.NONE);
		parent.getShell().setText("CryptoCard GUI");

		cardfrm = new CardFrm(this);
		cardfrm.setLocation(0, 0);
		cardfrm.pack();
		//
		// lkfrm = new LoadKeysFrm(this);
		// lkfrm.setLocation(0, 125);
		// lkfrm.pack();
		//
		pack();
		// setIds(this);
		// clearFields() ;
		//
	}

	protected CardFrm getCardFrm() {
		return this.cardfrm;
	}

	/*
	 * protected void setPanNumber (String s){ this.panNumber = s ; }
	 */
}