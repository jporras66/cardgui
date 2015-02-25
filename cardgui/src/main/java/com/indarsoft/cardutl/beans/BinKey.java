package com.indarsoft.cardutl.beans;

import com.indarsoft.cryptocard.symmetrickey.DesKey;
import com.indarsoft.cryptocard.symmetrickey.DesKeyException;
import com.indarsoft.cryptocard.symmetrickey.Pvk;
import com.indarsoft.cryptocard.types.PinValidationType;

public class BinKey {

	private String				binNumber ;
	private PinValidationType 	pinValidationType ;
	private Pvk					pvk ;
	private DesKey				cvvKey ;
	private String				cvvkeyDateFormat ;
	private DesKey				cvv2Key ;
	private String				cvv2keyDateFormat ;	
//
	private int 				pinLength;
//
	private String				pinValidationDataType;
	private int 				insertPosition ;
	private String 				decimalizationTable;
	private int 				panStartPosition;
	private int 				panEndPosition;
	private char 				panPadCharacter = 0x00;	
//	
	public BinKey (String binNumber) {
		this.binNumber = binNumber ;
	}
	
	public String getBinNumber() {
		return binNumber ;
	}
	
	public DesKey getCvv2Key(){
		return cvv2Key ; 
	}
	
	public String getCvv2KeyDateFormat ( )  {
		return cvv2keyDateFormat ;
	}

	public DesKey getCvvKey(){
		return cvvKey ; 
	}

	public String getCvvKeyDateFormat ( )  {
		return cvvkeyDateFormat ;
	}
	
	public String getDecimalizationTable ( )  {
		return decimalizationTable;
	}
	public int getpanEndPosition ( )  {
		return panEndPosition;
	}

	public char getPanPadCharacter ( )  {
		return panPadCharacter;
	}	
	public int getPanStartPosition ( )  {
		return panStartPosition;
	}	

	public int getPinLength ( )  {
		return pinLength;
	}
	public String getPinValidationDataType() {
		return pinValidationDataType;
	}
	
	public PinValidationType getPinValidationType ( ) {
		return pinValidationType ;
	}	
	public Pvk getPvk(){
		return pvk ; 
	}	
	
	public void setCvv( String cvvValue) throws DesKeyException {
		cvvKey = new DesKey( cvvValue );
	}	
	public void setCvv2( String cvvValue) throws DesKeyException {
		cvv2Key = new DesKey( cvvValue );
	}	
	
	public void setCvv2KeyDateFormat ( String dateFormat )  {
		cvv2keyDateFormat = dateFormat;
	}	
	public void setCvvKeyDateFormat ( String dateFormat )  {
		cvvkeyDateFormat = dateFormat;
	}
	
	public void setDecimalizationTable ( String decimalizationTable )  {
		this.decimalizationTable = decimalizationTable;
	}
	public void setPanEndPosition ( int panEndPosition )  {
		this.panEndPosition = panEndPosition;
	}

	public void setPanPadCharacter ( char panPadCharacter )  {
		this.panPadCharacter = panPadCharacter;
	}
	public void setPanStartPosition ( int panStartPosition )  {
		this.panStartPosition = panStartPosition;
	}
	
	public void setPinLength ( int pinLength )  {
		this.pinLength = pinLength;
	}
	public void setPinValidationDataType(String pinValidationDataType) {
		this.pinValidationDataType = pinValidationDataType;
	}	
	
	public void setPinValidationType (PinValidationType pinValidationType) {
		if (pinValidationType.equals( PinValidationType.VISA_PVV ) ){
			this.pinValidationType = PinValidationType.VISA_PVV ;
		}else
		if (pinValidationType.equals( PinValidationType.IBM_3624_OFFSET ) ){
			this.pinValidationType = PinValidationType.IBM_3624_OFFSET ; 
		}
	}

	public void setPvk( String pvkValue, int pvki) throws DesKeyException {
		pvk = new Pvk( pvkValue, pvki );
	}

	public int getInsertPosition() {
		return insertPosition;
	}

	public void setInsertPosition(int insertPosition) {
		this.insertPosition = insertPosition;
	}
}
