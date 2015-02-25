package com.indarsoft.cardgui.keys;

import java.io.File;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import com.indarsoft.cardutl.xml.LoadXmlKeys;
import com.indarsoft.cardutl.xml.XmlKeyData;
import com.indarsoft.utl.Utl;

public class LoadKeysFrm extends Composite {

	private static final String NA = "N/A";
	private static final String EMPTY = "";
	private Text txtPvk;
	private Text txtDki;
	private Text txtPinlength;
	private Text txtCvk;
	private Text txtDateformatCvv;
	private Text txtCvk2;
	private Text txtDateformatCvv2;
	private Text txtZpk;
	private Text txtPinblockFormat;
	private Button btnLoadKeys;
	//
	@SuppressWarnings("unused")
	private Shell sh = this.getShell();

	//

	public LoadKeysFrm(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		Group group = new Group(this, SWT.SHADOW_ETCHED_IN);
		group.setText("Load Keys");
		Label lblPvk = new Label(group, SWT.NONE);
		lblPvk.setText("Pvk");
		lblPvk.setLocation(20, 20);
		lblPvk.pack();

		Label lblDki = new Label(group, SWT.NONE);
		lblDki.setBounds(301, 20, 19, 15);
		lblDki.setText("Dki");

		Label lblPinlength = new Label(group, SWT.NONE);
		lblPinlength.setBounds(332, 20, 63, 15);
		lblPinlength.setText("Pin Length");

		txtPvk = new Text(group, SWT.BORDER);
		txtPvk.setEditable(false);
		txtPvk.setBounds(20, 41, 275, 21);

		txtDki = new Text(group, SWT.BORDER);
		txtDki.setEditable(false);
		txtDki.setBounds(301, 41, 36, 21);

		txtPinlength = new Text(group, SWT.BORDER);
		txtPinlength.setEditable(false);
		txtPinlength.setBounds(343, 41, 36, 21);
		group.pack();

		Label lblNewLabel = new Label(group, SWT.NONE);
		lblNewLabel.setBounds(20, 80, 169, 15);
		lblNewLabel.setText("Cvk for cvv/icvv or cvc/icvc");

		Label lblDateFormat = new Label(group, SWT.NONE);
		lblDateFormat.setBounds(301, 80, 78, 15);
		lblDateFormat.setText("Date Format");

		txtCvk = new Text(group, SWT.BORDER);
		txtCvk.setEditable(false);
		txtCvk.setBounds(20, 101, 275, 21);

		txtDateformatCvv = new Text(group, SWT.BORDER);
		txtDateformatCvv.setEditable(false);
		txtDateformatCvv.setBounds(301, 101, 76, 21);

		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setBounds(21, 141, 112, 15);
		lblNewLabel_1.setText("Cvk for cvv2 or cvc2");

		Label label = new Label(group, SWT.NONE);
		label.setText("Date Format");
		label.setBounds(301, 141, 78, 15);

		txtCvk2 = new Text(group, SWT.BORDER);
		txtCvk2.setEditable(false);
		txtCvk2.setBounds(20, 162, 275, 21);

		txtDateformatCvv2 = new Text(group, SWT.BORDER);
		txtDateformatCvv2.setEditable(false);
		txtDateformatCvv2.setBounds(301, 162, 76, 21);

		Label lblZpkForPinblock = new Label(group, SWT.NONE);
		lblZpkForPinblock.setBounds(20, 201, 113, 15);
		lblZpkForPinblock.setText("Zpk for Pinblock");

		txtZpk = new Text(group, SWT.BORDER);
		txtZpk.setEditable(false);
		txtZpk.setBounds(20, 222, 275, 21);

		txtPinblockFormat = new Text(group, SWT.BORDER);
		txtPinblockFormat.setEditable(false);
		txtPinblockFormat.setBounds(20, 248, 275, 21);

		btnLoadKeys = new Button(group, SWT.PUSH);
		btnLoadKeys.setToolTipText("load keys");
		btnLoadKeys.setImage(SWTResourceManager.getImage(LoadKeysFrm.class,
				"/images/key_add.png"));
		btnLoadKeys.setSize(28, 26);
		btnLoadKeys.setLocation(385, 39);
		btnLoadKeys.pack();
		//
		// this.binNumber = panNumber.substring( 0 , 6 );
		// this.pinBlockFormatType = pinBlockFType ;
		//
		initializeData(EMPTY);
		//
		btnLoadKeys.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {

				FileDialog dlg = new FileDialog(new Shell(), SWT.OPEN);
				String fileName = dlg.open();
				String dir = Utl.getPwd();
				dir = dir + File.separator + "data" + File.separator + "config";
				dlg.setFilterPath(dir);
				if (fileName != null) {
					System.out.println(fileName);
					//loadData("523236", "ISOFORMAT0", fileName);
				}
			}
		});
	}

	//

	public void initializeData(String str) {

		txtPvk.setText(str);
		txtDki.setText(str);
		txtPinlength.setText(str);
		txtCvk.setText(str);
		txtDateformatCvv.setText(str);
		txtCvk2.setText(str);
		txtDateformatCvv2.setText(str);
		txtZpk.setText(str);
		txtPinblockFormat.setText(str);
	}

	/*
	 * Load Keys from XML com.indarsoft.cardutl.file in ./data/config DIR
	 */

	public void loadData(String binNumber, String pinBlockFormatType,
			String xmlFile) {

		XmlKeyData xmlkeyData = loadKeys(xmlFile);
		fillData(binNumber, pinBlockFormatType, xmlkeyData);
	}

	/*
	 * Load Keys from XML com.indarsoft.cardutl.file in ./data/config DIR
	 */

	private XmlKeyData loadKeys(String xmlFile) {

		LoadXmlKeys lk = new LoadXmlKeys(xmlFile);
		XmlKeyData xmlkeyData = null;
		try {
			xmlkeyData = lk.loadXMLData();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return xmlkeyData;
	}

	private void fillData(String binNumber, String pinBlockFormatType,
			XmlKeyData xmlkeyData) {

		//
		initializeData(NA);
		//
		String str = xmlkeyData.getBinKey(binNumber).getPvk().getKeyAsString();
		txtPvk.setText(str);
		//
		str = Integer.toString(xmlkeyData.getBinKey(binNumber).getPvk()
				.getPvki());
		txtDki.setText(str);
		//
		str = Integer.toString(xmlkeyData.getBinKey(binNumber).getPinLength());
		if (!str.equals("0")) {
			txtPinlength.setText(str);
		}
		//
		str = xmlkeyData.getBinKey(binNumber).getCvvKey().getKeyAsString();
		txtCvk.setText(str);
		str = xmlkeyData.getBinKey(binNumber).getCvvKeyDateFormat();
		txtDateformatCvv.setText(str);
		//
		str = xmlkeyData.getBinKey(binNumber).getCvv2Key().getKeyAsString();
		txtCvk2.setText(str);
		str = xmlkeyData.getBinKey(binNumber).getCvv2KeyDateFormat();
		txtDateformatCvv2.setText(str);
		//
		str = xmlkeyData.getPinBlock(pinBlockFormatType).getZpk()
				.getKeyAsString();
		txtZpk.setText(str);
		str = xmlkeyData.getPinBlock(pinBlockFormatType)
				.getPinBlockFormatType().toString();
		txtPinblockFormat.setText(str);
	}
}