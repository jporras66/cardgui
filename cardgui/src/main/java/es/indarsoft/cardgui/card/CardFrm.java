package es.indarsoft.cardgui.card;

import java.io.File;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import es.indarsoft.cardgui.FrmControls;
import es.indarsoft.cryptocard.card.Card;
import es.indarsoft.cryptocard.card.CardException;
import es.indarsoft.cryptocard.types.PinBlockFormatType;
import es.indarsoft.cryptocard.types.PinValidationType;
import es.indarsoft.cardgui.FrmAlert;
import es.indarsoft.cardutl.beans.BinKey;
import es.indarsoft.cardutl.beans.PinBlockKey;
import es.indarsoft.cardutl.beans.ProcessedCardData;
import es.indarsoft.cardutl.card.ProcessCard;
import es.indarsoft.cardutl.xml.LoadXmlKeys;
import es.indarsoft.cardutl.xml.XmlKeyData;
import es.indarsoft.utl.Utl;
import org.eclipse.swt.graphics.Rectangle;

public class CardFrm extends Composite {

	private Text txtcardpanNumber;
	private Text txtcardExpDate;
	private Text txtcardServCode;
	private Button btnVisaPvv;
	private Button btnIbmOffset;
	private Button btnIsopinblock0;
	private Button btnIsopinblock3;
	private Button btnfind;
	private Button btnClear;
	//
	private static final String NA = "N/A";
	private static final String NF = "N/F";
	private Text txtkeyPvk;
	private Text txtkeydki;
	private Text txtkeyPinlength;
	private Text txtkeyCvk;
	private Text txtkeyDateformatCvv;
	private Text txtkeyCvk2;
	private Text txtkeyDateformatCvv2;
	private Text txtkeyZpk;
	private Text txtkeyPinblockFormat;
	private Button btnLoadKeys;
	//
	private Text txtrspin;
	private Text txtdrsdki;
	private Text txtrscvv;
	private Text txtrscvv2;
	private Text txtrsicvv;
	private Label lblCvv;
	private Label lblIcvv;
	private Text txtrsclearpinblock;
	private Label lblClearPinblock;
	private Text txtrsencryptedpinblock;
	private Label lblEncryptedPinblock;
	private Label lblrsmessage;
	//
	private Shell sh = this.getShell();
	private String xmlFile = "";
	private Card card = null;
	private XmlKeyData xmlkeyData = null;
	private Text txtcardPvv;
	private Text txtcarddki;
	private Label lblPvvoffset;

	//
	public CardFrm(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(null);
		Group groupcard = new Group(this, SWT.SHADOW_ETCHED_IN);
		groupcard.setBounds(10, 10, 294, 165);
		// group.setBounds(0, 0, 295, 150);

		groupcard.setText("Card Data");
		groupcard.setLayout(null);

		Label lblCard = new Label(groupcard, SWT.NONE);
		lblCard.setFont(SWTResourceManager.getFont("Cantarell", 10, SWT.NORMAL));
		lblCard.setBounds(new Rectangle(0, 0, 0, 16));
		lblCard.setBounds(12, 25, 91, 17);
		lblCard.setText("PAN Number");

		Label lblExpdate = new Label(groupcard, SWT.NONE);
		lblExpdate.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblExpdate.setBounds(new Rectangle(0, 0, 0, 16));
		lblExpdate.setBounds(141, 25, 56, 17);
		lblExpdate.setText("ExpDate");

		Label lblService = new Label(groupcard, SWT.NONE);
		lblService.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblService.setBounds(new Rectangle(0, 0, 0, 16));
		lblService.setBounds(204, 25, 65, 17);
		lblService.setText("ServCode");

		txtcardpanNumber = new Text(groupcard, SWT.BORDER);
		txtcardpanNumber.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtcardpanNumber.setBounds(12, 46, 135, 21);

		txtcardExpDate = new Text(groupcard, SWT.BORDER);
		txtcardExpDate.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtcardExpDate.setBounds(152, 46, 46, 21);

		txtcardServCode = new Text(groupcard, SWT.BORDER);
		txtcardServCode.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtcardServCode.setBounds(204, 46, 46, 21);

		Group pinValidationType = new Group(groupcard, SWT.SHADOW_ETCHED_IN);
		pinValidationType.setBounds(9, 68, 244, 68);

		btnVisaPvv = new Button(pinValidationType, SWT.RADIO);
		btnVisaPvv.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		btnVisaPvv.setSelection(true);
		btnVisaPvv.setBounds(10, 15, 90, 17);
		btnVisaPvv.setText("Visa PVV");

		btnIbmOffset = new Button(pinValidationType, SWT.RADIO);
		btnIbmOffset.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		btnIbmOffset.setBounds(130, 15, 104, 17);
		btnIbmOffset.setText("IBM Offset");

		Label lblDki_2 = new Label(pinValidationType, SWT.NONE);
		lblDki_2.setText("dki");
		lblDki_2.setFont(SWTResourceManager
				.getFont("Cantarell", 10, SWT.NORMAL));
		lblDki_2.setBounds(new Rectangle(0, 0, 0, 16));
		lblDki_2.setBounds(20, 40, 32, 17);

		txtcarddki = new Text(pinValidationType, SWT.BORDER);
		txtcarddki.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtcarddki.setBounds(51, 38, 34, 21);

		lblPvvoffset = new Label(pinValidationType, SWT.NONE);
		lblPvvoffset.setText("pvv/offset");
		lblPvvoffset.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblPvvoffset.setBounds(new Rectangle(0, 0, 0, 16));
		lblPvvoffset.setBounds(108, 38, 58, 17);

		txtcardPvv = new Text(pinValidationType, SWT.BORDER);
		txtcardPvv.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtcardPvv.setBounds(172, 38, 62, 21);

		Group pinBlockFormatType = new Group(groupcard, SWT.NONE);
		pinBlockFormatType.setBounds(9, 130, 244, 35);

		btnIsopinblock0 = new Button(pinBlockFormatType, SWT.RADIO);
		btnIsopinblock0.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		btnIsopinblock0.setSelection(true);
		btnIsopinblock0.setText("IsoPinblock0");
		btnIsopinblock0.setBounds(3, 15, 108, 17);

		btnIsopinblock3 = new Button(pinBlockFormatType, SWT.RADIO);
		btnIsopinblock3.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		btnIsopinblock3.setText("IsoPinblock3");
		btnIsopinblock3.setBounds(130, 15, 108, 17);

		btnfind = new Button(groupcard, SWT.NONE);
		btnfind.setBounds(260, 46, 28, 25);
		btnfind.setSelection(true);
		btnfind.setToolTipText("find");
		btnfind.setImage(SWTResourceManager.getImage(CardFrm.class, "/images/find_obj.gif"));

		btnClear = new Button(groupcard, SWT.NONE);
		btnClear.setBounds(260, 76, 28, 25);
		btnClear.setToolTipText("clear");
		btnClear.setImage(SWTResourceManager.getImage(CardFrm.class, "/images/clear.gif"));
		groupcard.pack();
		//
		Group groupkey = new Group(this, SWT.SHADOW_ETCHED_IN);
		groupkey.setBounds(10, 180, 470, 280);
		groupkey.setText("Load Keys");
		Label lblPvk = new Label(groupkey, SWT.NONE);
		lblPvk.setFont(SWTResourceManager.getFont("Cantarell", 10, SWT.NORMAL));
		lblPvk.setSize(23, 17);
		lblPvk.setText("Pvk");
		lblPvk.setLocation(20, 20);
		lblPvk.pack();

		Label lblDki = new Label(groupkey, SWT.NONE);
		lblDki.setFont(SWTResourceManager.getFont("Cantarell", 10, SWT.NORMAL));
		lblDki.setBounds(301, 20, 25, 17);
		lblDki.setText("dki");

		Label lblPinlength = new Label(groupkey, SWT.NONE);
		lblPinlength.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblPinlength.setBounds(332, 20, 81, 17);
		lblPinlength.setText("Pin Length");

		txtkeyPvk = new Text(groupkey, SWT.BORDER);
		txtkeyPvk.setFont(SWTResourceManager
				.getFont("Cantarell", 9, SWT.NORMAL));
		txtkeyPvk.setEditable(false);
		txtkeyPvk.setBounds(20, 41, 275, 21);

		txtkeydki = new Text(groupkey, SWT.BORDER);
		txtkeydki.setFont(SWTResourceManager
				.getFont("Cantarell", 9, SWT.NORMAL));
		txtkeydki.setEditable(false);
		txtkeydki.setBounds(301, 41, 36, 21);

		txtkeyPinlength = new Text(groupkey, SWT.BORDER);
		txtkeyPinlength.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtkeyPinlength.setEditable(false);
		txtkeyPinlength.setBounds(350, 41, 36, 21);

		Label lblNewLabel = new Label(groupkey, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblNewLabel.setBounds(20, 80, 203, 17);
		lblNewLabel.setText("Cvk for cvv/icvv or cvc/icvc");

		Label lblDateFormat = new Label(groupkey, SWT.NONE);
		lblDateFormat.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblDateFormat.setBounds(301, 80, 85, 17);
		lblDateFormat.setText("Date Format");

		txtkeyCvk = new Text(groupkey, SWT.BORDER);
		txtkeyCvk.setFont(SWTResourceManager
				.getFont("Cantarell", 9, SWT.NORMAL));
		txtkeyCvk.setEditable(false);
		txtkeyCvk.setBounds(20, 101, 275, 21);

		txtkeyDateformatCvv = new Text(groupkey, SWT.BORDER);
		txtkeyDateformatCvv.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtkeyDateformatCvv.setEditable(false);
		txtkeyDateformatCvv.setBounds(301, 101, 76, 21);

		Label lblNewLabel_1 = new Label(groupkey, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblNewLabel_1.setBounds(21, 141, 167, 17);
		lblNewLabel_1.setText("Cvk for cvv2 or cvc2");

		Label label = new Label(groupkey, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("Cantarell", 10, SWT.NORMAL));
		label.setText("Date Format");
		label.setBounds(301, 141, 85, 17);

		txtkeyCvk2 = new Text(groupkey, SWT.BORDER);
		txtkeyCvk2.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtkeyCvk2.setEditable(false);
		txtkeyCvk2.setBounds(20, 162, 275, 21);

		txtkeyDateformatCvv2 = new Text(groupkey, SWT.BORDER);
		txtkeyDateformatCvv2.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtkeyDateformatCvv2.setEditable(false);
		txtkeyDateformatCvv2.setBounds(301, 162, 76, 21);

		Label lblZpkForPinblock = new Label(groupkey, SWT.NONE);
		lblZpkForPinblock.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblZpkForPinblock.setBounds(20, 201, 113, 17);
		lblZpkForPinblock.setText("Zpk for Pinblock");

		txtkeyZpk = new Text(groupkey, SWT.BORDER);
		txtkeyZpk.setFont(SWTResourceManager
				.getFont("Cantarell", 9, SWT.NORMAL));
		txtkeyZpk.setEditable(false);
		txtkeyZpk.setBounds(20, 222, 275, 21);

		txtkeyPinblockFormat = new Text(groupkey, SWT.BORDER);
		txtkeyPinblockFormat.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtkeyPinblockFormat.setEditable(false);
		txtkeyPinblockFormat.setBounds(20, 248, 275, 21);

		btnLoadKeys = new Button(groupkey, SWT.PUSH);
		btnLoadKeys.setToolTipText("load keys");
		btnLoadKeys.setImage(SWTResourceManager.getImage(CardFrm.class, "/images/key_add.png"));
		btnLoadKeys.setSize(28, 26);
		btnLoadKeys.setLocation(418, 41);
		btnLoadKeys.pack();

		Group result = new Group(this, SWT.NONE);
		result.setText("Card Result");
		result.setBounds(311, 10, 305, 163);

		Label lblPin = new Label(result, SWT.NONE);
		lblPin.setFont(SWTResourceManager.getFont("Cantarell", 10, SWT.NORMAL));
		lblPin.setBounds(new Rectangle(0, 0, 0, 16));
		lblPin.setBounds(12, 25, 32, 17);
		lblPin.setText("pin");

		txtrspin = new Text(result, SWT.BORDER);
		txtrspin.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		txtrspin.setBounds(12, 46, 68, 21);

		Label lblDki_1 = new Label(result, SWT.NONE);
		lblDki_1.setFont(SWTResourceManager
				.getFont("Cantarell", 10, SWT.NORMAL));
		lblDki_1.setBounds(new Rectangle(0, 0, 0, 16));
		lblDki_1.setBounds(87, 25, 32, 17);
		lblDki_1.setText("dki");

		txtdrsdki = new Text(result, SWT.BORDER);
		txtdrsdki.setFont(SWTResourceManager
				.getFont("Cantarell", 9, SWT.NORMAL));
		txtdrsdki.setBounds(86, 46, 33, 21);

		Label lblCvvcvc = new Label(result, SWT.NONE);
		lblCvvcvc.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblCvvcvc.setBounds(new Rectangle(0, 0, 0, 16));
		lblCvvcvc.setBounds(125, 25, 32, 17);
		lblCvvcvc.setText("cvv");

		txtrscvv = new Text(result, SWT.BORDER);
		txtrscvv.setFont(SWTResourceManager.getFont("Cantarell", 9, SWT.NORMAL));
		txtrscvv.setBounds(125, 46, 43, 21);

		lblCvv = new Label(result, SWT.NONE);
		lblCvv.setFont(SWTResourceManager.getFont("Cantarell", 10, SWT.NORMAL));
		lblCvv.setBounds(new Rectangle(0, 0, 0, 16));
		lblCvv.setBounds(174, 25, 32, 17);
		lblCvv.setText("cvv2");

		txtrscvv2 = new Text(result, SWT.BORDER);
		txtrscvv2.setFont(SWTResourceManager
				.getFont("Cantarell", 9, SWT.NORMAL));
		txtrscvv2.setBounds(174, 46, 43, 21);

		lblIcvv = new Label(result, SWT.NONE);
		lblIcvv.setFont(SWTResourceManager.getFont("Cantarell", 10, SWT.NORMAL));
		lblIcvv.setBounds(new Rectangle(0, 0, 0, 16));
		lblIcvv.setBounds(223, 25, 32, 17);
		lblIcvv.setText("icvv\r\n");

		txtrsicvv = new Text(result, SWT.BORDER);
		txtrsicvv.setFont(SWTResourceManager
				.getFont("Cantarell", 9, SWT.NORMAL));
		txtrsicvv.setBounds(223, 46, 43, 21);

		lblClearPinblock = new Label(result, SWT.NONE);
		lblClearPinblock.setFont(SWTResourceManager.getFont("Cantarell", 10,
				SWT.NORMAL));
		lblClearPinblock.setBounds(12, 86, 105, 17);
		lblClearPinblock.setText("Clear Pinblock");

		txtrsclearpinblock = new Text(result, SWT.BORDER);
		txtrsclearpinblock.setFont(SWTResourceManager.getFont("Cantarell", 9,
				SWT.NORMAL));
		txtrsclearpinblock.setBounds(12, 107, 135, 21);

		lblEncryptedPinblock = new Label(result, SWT.NONE);
		lblEncryptedPinblock.setFont(SWTResourceManager.getFont("Cantarell",
				10, SWT.NORMAL));
		lblEncryptedPinblock.setBounds(160, 86, 135, 17);
		lblEncryptedPinblock.setText("Encrypted Pinblock");

		txtrsencryptedpinblock = new Text(result, SWT.BORDER);
		txtrsencryptedpinblock.setFont(SWTResourceManager.getFont("Cantarell",
				9, SWT.NORMAL));
		txtrsencryptedpinblock.setBounds(160, 107, 135, 21);

		lblrsmessage = new Label(result, SWT.NONE);
		lblrsmessage.setBounds(12, 138, 283, 15);
		//
		btnVisaPvv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (xmlkeyData != null) {
					String msg = validateCard();
					if (!msg.equals("")) {
						@SuppressWarnings("unused")
						FrmAlert alert = new FrmAlert(sh, msg);
					} else {
						fillDataKeys(card.getPanNumber().substring(0, 6), card
								.getPinBlockFormatType().toString(), xmlkeyData);
					}
				}
			}
		});
		//
		btnIsopinblock0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (xmlkeyData != null) {
					String msg = validateCard();
					if (!msg.equals("")) {
						@SuppressWarnings("unused")
						FrmAlert alert = new FrmAlert(sh, msg);
					} else {
						fillDataKeys(card.getPanNumber().substring(0, 6), card
								.getPinBlockFormatType().toString(), xmlkeyData);
					}
				}
			}
		});
		//
		btnfind.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {

				String msg = validateCard();
				if (!msg.equals("")) {
					@SuppressWarnings("unused")
					FrmAlert alert = new FrmAlert(sh, msg);
				} else if (xmlkeyData == null) {
					@SuppressWarnings("unused")
					FrmAlert alert = new FrmAlert(sh, "Please load key file !!");
				} else {
					computeResult();
				}
			}

		});
		//
		btnClear.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {

				clearScreen();
			}

		});
		//
		btnLoadKeys.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent event) {

				String msg = validateCard();
				if (!msg.equals("")) {
					@SuppressWarnings("unused")
					FrmAlert alert = new FrmAlert(sh, msg);
				} else {
					FileDialog dlg = new FileDialog(new Shell(), SWT.OPEN);
					String fileName = dlg.open();
					String dir = Utl.getPwd();
					dir = dir + File.separator + "data" + File.separator
							+ "config";
					dlg.setFilterPath(dir);
					if (fileName != null) {
						xmlFile = fileName;
						xmlkeyData = loadKeys(xmlFile);
						fillDataKeys(card.getPanNumber().substring(0, 6), card
								.getPinBlockFormatType().toString(), xmlkeyData);
					}
				}
			}
		});
		//
	}

	private String validateCard() {

		String str = "";
		try {

			card = new Card(txtcardpanNumber.getText());
			card.setExpirationDate(txtcardExpDate.getText());
			card.setServiceCode(txtcardServCode.getText());

			if (txtcarddki.getText().equals("")) {
				str = "please inform card dki value !!";
				return str;
			} else {
				int z = Integer.parseInt(txtcarddki.getText());
				card.setPvki(z);
			}

			if (btnVisaPvv.getSelection()) {
				card.setPinValidationType(PinValidationType.VISA_PVV);
				card.setPvv(txtcardPvv.getText());

			}
			if (btnIbmOffset.getSelection()) {
				card.setPinValidationType(PinValidationType.IBM_3624_OFFSET);
				card.setOffset(txtcardPvv.getText());
			}

			if (btnIsopinblock0.getSelection()) {
				card.setPinBlockFormatType(PinBlockFormatType.ISOFORMAT0);
			}
			if (btnIsopinblock3.getSelection()) {
				card.setPinBlockFormatType(PinBlockFormatType.ISOFORMAT3);
			}

		} catch (Exception e) {
			str = e.getMessage();
			return str;
		}

		return "";
	}

	private void clearScreen() {

		FrmControls frmcnt = new FrmControls(this.getParent());
		frmcnt.clearFields();
		btnVisaPvv.setSelection(true);
		btnIbmOffset.setSelection(false);
		btnIsopinblock0.setSelection(true);
		btnIsopinblock3.setSelection(false);
		lblrsmessage.setText("");
		xmlkeyData = null;
	}

	/*
	 * Load Keys from XML es.indarsoft.cardutl.file in ./data/config DIR
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

	private void fillDataKeys(String binNumber, String pinBlockFormatType,
			XmlKeyData xmlkeyData) {

		//
		initializeData(NA);
		//
		String str = "";
		if (xmlkeyData.getBinKey(binNumber).getPinValidationType()
				.equals(PinValidationType.VISA_PVV)
				&& btnVisaPvv.getSelection()) {
			str = xmlkeyData.getBinKey(binNumber).getPvk().getKeyAsString();
			txtkeyPvk.setText(str);
			str = Integer.toString(xmlkeyData.getBinKey(binNumber).getPvk()
					.getPvki());
			txtkeydki.setText(str);
			str = Integer.toString(xmlkeyData.getBinKey(binNumber)
					.getPinLength());
			if (!str.equals("0")) {
				txtkeyPinlength.setText(str);
			}
		}
		if (xmlkeyData.getBinKey(binNumber).getPinValidationType()
				.equals(PinValidationType.IBM_3624_OFFSET)
				&& btnIbmOffset.getSelection()) {
			str = xmlkeyData.getBinKey(binNumber).getPvk().getKeyAsString();
			txtkeyPvk.setText(str);
			str = Integer.toString(xmlkeyData.getBinKey(binNumber).getPvk()
					.getPvki());
			txtkeydki.setText(str);
			str = Integer.toString(xmlkeyData.getBinKey(binNumber)
					.getPinLength());
			if (!str.equals("0")) {
				txtkeyPinlength.setText(str);
			}
		}
		//
		str = xmlkeyData.getBinKey(binNumber).getCvvKey().getKeyAsString();
		txtkeyCvk.setText(str);
		str = xmlkeyData.getBinKey(binNumber).getCvvKeyDateFormat();
		txtkeyDateformatCvv.setText(str);
		//
		str = xmlkeyData.getBinKey(binNumber).getCvv2Key().getKeyAsString();
		txtkeyCvk2.setText(str);
		str = xmlkeyData.getBinKey(binNumber).getCvv2KeyDateFormat();
		txtkeyDateformatCvv2.setText(str);
		//
		str = xmlkeyData.getPinBlock(pinBlockFormatType).getZpk()
				.getKeyAsString();
		txtkeyZpk.setText(str);
		str = xmlkeyData.getPinBlock(pinBlockFormatType)
				.getPinBlockFormatType().toString();
		txtkeyPinblockFormat.setText(str);
	}

	private void initializeData(String str) {

		txtkeyPvk.setText(str);
		txtkeydki.setText(str);
		txtkeyPinlength.setText(str);
		txtkeyCvk.setText(str);
		txtkeyDateformatCvv.setText(str);
		txtkeyCvk2.setText(str);
		txtkeyDateformatCvv2.setText(str);
		txtkeyZpk.setText(str);
		txtkeyPinblockFormat.setText(str);
		//
		txtrspin.setText("");
		txtdrsdki.setText("");
		txtrscvv.setText("");
		txtrscvv2.setText("");
		txtrsicvv.setText("");
		txtrsclearpinblock.setText("");
		txtrsencryptedpinblock.setText("");
		lblrsmessage.setText("");
	}

	private void computeResult() {

		BinKey binkey = xmlkeyData.getBinKey(card.getPanNumber()
				.substring(0, 6));
		String str = card.getPinBlockFormatType().toString();
		PinBlockKey pinBlockKey = xmlkeyData.getPinBlock(str);
		ProcessedCardData pcd = null;

		pcd = ProcessCard.doit(card, binkey, pinBlockKey);
		//
		txtrspin.setText(pcd.getPin());
		String w = Integer.toString(pcd.getDki());
		txtdrsdki.setText(w);
		txtrscvv.setText(pcd.getCvv());
		txtrscvv2.setText(pcd.getCvv2());
		txtrsicvv.setText(pcd.getIcvv());
		txtrsclearpinblock.setText(pcd.getClearPinblock());
		txtrsencryptedpinblock.setText(pcd.getEncryptedPinblock());
		//
		// if pin N/F try all dki's until
		//
		String returnedpinvalue = pcd.getPin();
		if (returnedpinvalue.equals(NF)) {
			for (int k = 1; k < 6; k++) {
				try {
					card.setPvki(k);
					pcd = ProcessCard.doit(card, binkey, pinBlockKey);
					String triedPin = pcd.getPin();
					if (!triedPin.equals(NF)) {
						txtrspin.setText(pcd.getPin());
						w = Integer.toString(pcd.getDki());
						txtdrsdki.setText(w);
						txtrsclearpinblock.setText(pcd.getClearPinblock());
						txtrsencryptedpinblock.setText(pcd
								.getEncryptedPinblock());
						lblrsmessage.setText("Cannot find pin for dki  "
								+ txtcarddki.getText()
								+ " but found pin for dki  " + w + " !! ");
						break;
					}
				} catch (CardException e) {
					// log.warn( "Process all dki's " + e.getMessage() ) ;
				}
			}
		}
	}
}
