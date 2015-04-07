package com.indarsoft.cardutl.xml;

import java.util.Hashtable;

import com.indarsoft.cardutl.beans.BinKey;
import com.indarsoft.cardutl.beans.PinBlockKey;

public class XmlKeyData {
	// Hashtable de PinBlocks Keys
	private Hashtable<String, PinBlockKey> 	ahpinBlockKey 	= new Hashtable<String, PinBlockKey>(500) ;
	// Hashtable de BinNumber Keys
	private Hashtable<String, BinKey> 		ahBinKey 		= new Hashtable<String, BinKey>(500) ; 
	
	public XmlKeyData() {

	}

	public void putPinBlock (PinBlockKey pinBlockKey ) {
		String pinBlockFormatType = pinBlockKey.getPinBlockFormatType().toString()  ;
		ahpinBlockKey.put ( pinBlockFormatType  , pinBlockKey ) ;
	}
	
	public PinBlockKey getPinBlock( String pinBlockFormatType ){
		return (PinBlockKey)  ahpinBlockKey.get ( pinBlockFormatType ) ;
	}

	public void putBinKey(BinKey binKey ){
		ahBinKey.put ( binKey.getBinNumber() , binKey ) ;
	}

	public BinKey getBinKey( String binNumber ){
		BinKey b = ahBinKey.get ( binNumber ) ;
		//return (BinKey)  ahBinKey.get ( binNumber ) ;
		return b ;
	}
	
	
}
