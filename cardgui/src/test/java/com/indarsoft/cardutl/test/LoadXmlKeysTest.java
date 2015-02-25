package com.indarsoft.cardutl.test;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.indarsoft.cardutl.beans.BinKey;
import com.indarsoft.cardutl.beans.PinBlockKey;
import com.indarsoft.cardutl.xml.LoadXmlKeys;
import com.indarsoft.cardutl.xml.XmlKeyData;
import com.indarsoft.cryptocard.types.PinBlockFormatType;
import com.indarsoft.cryptocard.types.PinValidationType;
import com.indarsoft.utl.Utl;


public class LoadXmlKeysTest {

	
	static Logger log = Logger.getLogger( LoadXmlKeysTest.class.getName() );
	
	private static LoadXmlKeys 	lk  ;
	private static XmlKeyData 	xmlkeyData ;
	private static String 		separator = File.separator ;
	
	private PinBlockFormatType pinBlockFormatType0 = PinBlockFormatType.ISOFORMAT0 ;
	private PinBlockFormatType pinBlockFormatType3 = PinBlockFormatType.ISOFORMAT3;	
	private String binNumbera = "400000";
	private String binNumberb = "520000";
	private String binNumberc = "530000";

	@BeforeClass
	public static void setUpBeforeClass() {
		
		String xmlFile	= Utl.getPwd() + separator + "data" + separator + "config" + separator + "key.xml" ;
		lk = new LoadXmlKeys( xmlFile ) ;
		try {
			xmlkeyData = lk.loadXMLData() ;
		}
		catch ( Exception ex){
			System.out.println( "setUpBeforeClass LoadXmlKeys : " + xmlFile + " " + ex.getMessage() ) ;
			assertFalse( true) ;
		}
	}
	
	@Test
	public void testLoadXMLDataPinBlockFormatType0() {
			
		//Pinblock
		PinBlockKey pinBlockKey = xmlkeyData.getPinBlock( pinBlockFormatType0.toString() ) ;

		String ValidationType	= pinBlockKey.getPinBlockFormatType().toString() ;
		String keyValue 		= xmlkeyData.getPinBlock(ValidationType).getZpk().getKeyAsString() ;
		String keyExpected		= "0123456789ABCDEF0123456789ABCDEF";
		if ( keyValue.equals( keyExpected ) ){
			System.out.println("testLoadXMLDataPinBlockFormatType0 - OK : " + ValidationType + " keyExpected : " + keyExpected + " keyReturned : " + keyValue ) ;
			assertTrue( true) ;
		}else{
			System.out.println("testLoadXMLDataPinBlockFormatType0 - KO : " + ValidationType + " keyExpected : " + keyExpected + " keyReturned : " + keyValue ) ;
			assertFalse( true) ;
		}			
	}

	@Test
	public void testLoadXMLDataPinBlockFormatType3() {
			
		//Pinblock
		PinBlockKey pinBlockKey = xmlkeyData.getPinBlock(pinBlockFormatType3.toString()) ;

		String ValidationType	= pinBlockKey.getPinBlockFormatType().toString() ;
		String keyValue 		= xmlkeyData.getPinBlock(ValidationType).getZpk().getKeyAsString() ;
		String keyExpected		= "0123456789ABCDEF0123456789ABCDEF";
		if ( keyValue.equals( keyExpected ) ){
			System.out.println("testLoadXMLDataPinBlockFormatType3 - OK : " + ValidationType + " keyExpected : " + keyExpected + " keyReturned : " + keyValue ) ;
			assertTrue( true) ;
		}else{
			System.out.println("testLoadXMLDataPinBlockFormatType3 - KO : " + ValidationType + " keyExpected : " + keyExpected + " keyReturned : " + keyValue ) ;
			assertFalse( true) ;
		}			
	}
	
	@Test
	public void testLoadXMLBin40000() {
		
		//BinKey
		BinKey 	binKey  			= xmlkeyData.getBinKey( binNumbera );
		PinValidationType pinValidationType 	= binKey.getPinValidationType() ;
		String pvkValue 			= binKey.getPvk().getKeyAsString() ;
		int pvki 					= binKey.getPvk().getPvki() ;
		
		if ( pinValidationType.equals( PinValidationType.VISA_PVV) ){
			System.out.printf("testLoadXMLBin40000 OK - binNumber %s pinValidationType %s pvkValue %s pvki %d \n", binNumbera, pinValidationType , pvkValue, pvki);
			assertTrue( true) ;
		}else{
			System.out.printf("testLoadXMLBin40000 KO - binNumber %s pinValidationType %s pvkValue %d pvki %d \n", binNumbera, pinValidationType , pvkValue, pvki);
			assertFalse( true) ;
		}	
		
	}

	@Test
	public void testLoadXMLBin52000() {
		
		//BinKey
		BinKey 	binKey  			= xmlkeyData.getBinKey( binNumberb );
		PinValidationType pinValidationType 	= binKey.getPinValidationType() ;
		String pvkValue 			= binKey.getPvk().getKeyAsString() ;
		int pvki 					= binKey.getPvk().getPvki() ;
		
		
		if ( pinValidationType.equals(PinValidationType.IBM_3624_OFFSET) ){
			System.out.printf ("testLoadXMLBin52000 OK - binNumber %s pinValidationType %s pvkValue %s pvki %d \n", binNumberb, pinValidationType , pvkValue, pvki);
			System.out.println("testLoadXMLBin52000 OK - pingLength-decimalizationTable-panStartPosition-panEndPosition-panPadCharacter : " + binKey.getPinValidationDataType() + "-"+ binKey.getPinLength() + "-" + binKey.getDecimalizationTable() + "-" + binKey.getPanStartPosition() + "-" + binKey.getpanEndPosition() + "-" + binKey.getPanPadCharacter()  ) ;
			assertTrue( true) ;
		}else{
			System.out.printf ("testLoadXMLBin52000 KO - binNumber %s pinValidationType %s pvkValue %d pvki %d \n", binNumberb, pinValidationType , pvkValue, pvki);
			System.out.println("testLoadXMLBin52000 KO - pingLength-decimalizationTable-panStartPosition-panEndPosition-panPadCharacter : " + binKey.getPinValidationDataType() + "-"+ binKey.getPinLength() + "-" + binKey.getDecimalizationTable() + "-" + binKey.getPanStartPosition() + "-" + binKey.getpanEndPosition() + "-" + binKey.getPanPadCharacter()  ) ;
			assertFalse( true) ;
		}	
		
	}	
	
	@Test
	public void testLoadXMLBin53000() {
		
		//BinKey
		BinKey 	binKey  			= xmlkeyData.getBinKey( binNumberc );
		PinValidationType pinValidationType 	= binKey.getPinValidationType() ;
		String pvkValue 			= binKey.getPvk().getKeyAsString() ;
		int pvki 					= binKey.getPvk().getPvki() ;
		
		
		if ( pinValidationType.equals(PinValidationType.IBM_3624_OFFSET) ){
			System.out.printf ("testLoadXMLBin53000 OK - binNumber %s pinValidationType %s pvkValue %s pvki %d \n", binNumberc, pinValidationType , pvkValue, pvki);
			System.out.println("testLoadXMLBin53000 OK - pingLength-decimalizationTable-panStartPosition-panEndPosition-panPadCharacter : " + binKey.getPinValidationDataType() + "-"+ binKey.getPinLength() + "-" + binKey.getDecimalizationTable() + "-" + binKey.getPanStartPosition() + "-" + binKey.getpanEndPosition() + "-" + binKey.getPanPadCharacter()  ) ;
			assertTrue( true) ;
		}else{
			System.out.printf ("testLoadXMLBin53000 KO - binNumber %s pinValidationType %s pvkValue %d pvki %d \n", binNumberc, pinValidationType , pvkValue, pvki);
			System.out.println("testLoadXMLBin53000 KO - pingLength-decimalizationTable-panStartPosition-panEndPosition-panPadCharacter : " + binKey.getPinValidationDataType() + "-"+ binKey.getPinLength() + "-" + binKey.getDecimalizationTable() + "-" + binKey.getPanStartPosition() + "-" + binKey.getpanEndPosition() + "-" + binKey.getPanPadCharacter()  ) ;
			assertFalse( true) ;
		}	
		
	}
}
