package es.indarsoft.cardgui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class FrmAlert {

	public FrmAlert(Shell sh, String msg) {

		MessageBox messageBox = new MessageBox(sh, SWT.ICON_ERROR | SWT.ABORT);

		messageBox.setText("Error !!");
		messageBox.setMessage(msg);
		int buttonID = messageBox.open();
		switch (buttonID) {
		case SWT.YES:
			// saves changes ...
		case SWT.NO:
			// exits here ...
			break;
		case SWT.CANCEL:
			// does nothing ...
		}
		// System.out.println(buttonID);
	}

}
