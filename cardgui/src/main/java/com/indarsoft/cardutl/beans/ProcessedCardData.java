package com.indarsoft.cardutl.beans;

import com.indarsoft.cryptocard.card.Card;
import com.indarsoft.cryptocard.types.PinValidationType;

public class ProcessedCardData {

	private static final String NF = "N/F";
	private String 	panNumber;
	private String 	expirationDate;
	private String 	serviceCode;
	private int 	dki;
	private String 	pvv_offset;
	private String 	pinValidationType;
	private String 	pinValidationDataType;


	private String 	pin;
	private String 	cvv;
	private String 	cvv2;
	private String 	icvv;
	private String 	pinblockFormatType;
	private String 	clearPinblock;
	private String 	encryptedPinblock;
	private char   	cardBrand ;	
	private String  track2;
	private String 	errMsg;

	public ProcessedCardData ( Card card ){
		
		panNumber 		= card.getPanNumber() ;
		expirationDate	= card.getExpirationDate();
		serviceCode		= card.getServiceCode();
		dki 			= card.getPvki() ;
		pinValidationType	= card.getPinValidationType().toString();
		pinblockFormatType 	= card.getPinBlockFormatType().toString();
		cardBrand 		= card.getCardBrand(); 
		pvv_offset 		= NF ;
		pin				= NF ;
		cvv				= NF ;
		cvv2			= NF ;
		icvv			= NF ;
		clearPinblock	 	= NF ;
		encryptedPinblock 	= NF ;
		track2 			= NF ;
		errMsg				= "" ;  	
	}

	private String computeTrack2(){
		 
		String track2separator = "";
		if ( this.cardBrand == 'V' ){
			track2separator = "=" ;
		}else {
			if (this.cardBrand == 'M' ){
				track2separator = "D" ;
			}else {
				track2separator = "D" ;
			}
		}
	
		String track2 = panNumber + track2separator ;
		
		if ( expirationDate  == null ){
			track2 = track2 + "????";
		}else{
			track2 = track2 + expirationDate ;
		}
		
		if ( serviceCode  == null ){
			track2 = track2 + "???";
		}else{
			track2 = track2 + serviceCode ;
		}
		
		Integer z = new Integer ( this.dki  );
		track2 = track2 + z.toString() ;
//
// Discretionary data
//		
		if ( pinValidationType != null  ){
			if ( pinValidationType.equals( PinValidationType.VISA_PVV.toString() ) ){
				 track2 = track2 + this.pvv_offset ;
			}else
			if ( pinValidationType.equals( PinValidationType.IBM_3624_OFFSET.toString() ) ){
				track2 = track2 + this.pvv_offset ;	
			}
		}else {
			track2 = track2 + "?????";
		}
		
		if ( cvv != null  ){
			track2 = track2 + cvv;
		}
		
//
//		
		return track2;
	}

	public String getClearPinblock() {
		return clearPinblock;
	}

	public String getCvv() {
		return cvv;
	}

	public String getCvv2() {
		return cvv2;
	}

	public int getDki() {
		return dki;
	}

	public String getEncryptedPinblock() {
		return encryptedPinblock;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public String getIcvv() {
		return icvv;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public String getPin() {
		return pin;
	}

	public String getPinblockValType() {
		return pinblockFormatType;
	}

	public String getPinValidationDataType() {
		return pinValidationDataType;
	}
	
	public String getPinValType() {
		return pinValidationType;
	}

	public String getPvv_offset() {
		return pvv_offset;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getTrack2() {
		return track2;
	}

	public void setClearPinblock(String clearPinblock) {
		this.clearPinblock = clearPinblock;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public void setDki(int dki) {
		this.dki = dki;
	}

	public void setEncryptedPinblock(String encryptedPinblock) {
		this.encryptedPinblock = encryptedPinblock;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setIcvv(String icvv) {
		this.icvv = icvv;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setPinblockValType(String pinblockValType) {
		this.pinblockFormatType = pinblockValType;
	}

	public void setPinValidationDataType(String pinValidationDataType) {
		this.pinValidationDataType = pinValidationDataType;
	}
	
	public void setPinValType(String pinValType) {
		this.pinValidationType = pinValType;
	}

	public void setPvv_offset(String pvv_offset) {
		this.pvv_offset = pvv_offset;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public void setTrack2( ) {
		this.track2 = computeTrack2() ;
	}





}
