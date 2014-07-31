package es.indarsoft.cardutl.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import es.indarsoft.cardutl.beans.BinKey;
import es.indarsoft.cardutl.beans.PinBlockKey;
import es.indarsoft.cryptocard.symmetrickey.DesKeyException;
import es.indarsoft.cryptocard.types.PinBlockFormatType;
import es.indarsoft.cryptocard.types.PinValidationType;

public class LoadXmlKeys {
	
	static Logger log = Logger.getLogger( LoadXmlKeys.class.getName() );
	
	private String 		fileName ; 
	private Document 	dom ;
	private Element 	docEle ;
	private BinKey 		binKey  ;
	private PinBlockKey pinBlockKey;
	private XmlKeyData 	xmlkeyData = new XmlKeyData() ;
	
	public LoadXmlKeys ( String xmlFileData ) {
		
		this.fileName = xmlFileData ;
	}
	

	public XmlKeyData loadXMLData()  {  	 
//		
		File data = new File(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbf.newDocumentBuilder();
			this.dom = dBuilder.parse(data) ;
		} catch (ParserConfigurationException  | SAXException  | IOException e) {
			log.error ( "dBuilder + dom :  "  + e.getMessage() ) ;
			return null ; 
		}
		
		this.docEle = dom.getDocumentElement();
		getRootElement(); 
		getPinBlockValidation() ;
		getBinNumber() ;		    
		return xmlkeyData ; // Return HashTable
	}

	private void getRootElement( ){

		Element elementroot = (Element) dom.getDocumentElement();
		elementroot.normalize();
		
		NamedNodeMap rootatr = elementroot.getAttributes();

		for (int i = 0; i < rootatr.getLength(); i++) {
			Node theAttribute = rootatr.item(i);	
			if (theAttribute.getNodeName() == "GROUP_NAME"){
				@SuppressWarnings("unused")
				String name = theAttribute.getNodeValue() ;  
			}
		}		
	} 

	
	private void getPinBlockValidation()  {

		NodeList nl = this.docEle.getElementsByTagName("PINBLOCK_VALIDATION");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Element el = (Element)nl.item(i);
				
				getPinBlockValidationElements(el) ;
				
				if ( pinBlockKey == null || pinBlockKey.getPinBlockFormatType() == null || pinBlockKey.getZpk() == null ) {
					log.error ( "getPinBlockValidationElements "   ) ;
				}else{
					xmlkeyData.putPinBlock ( pinBlockKey );
				}
			}
		}
	}

	private void getPinBlockValidationElements(Element el)  {
		
		String pinblockValidationTypeStr 	= el.getAttribute("VALIDATION_TYPE");
		String zpkValue 		= getTextValue(el,"ZPK");
		PinBlockFormatType 		pinBlockFormatType = null ;

		try {
			if ( pinblockValidationTypeStr.equals( PinBlockFormatType.ISOFORMAT0.toString()  )){
				pinBlockFormatType = PinBlockFormatType.ISOFORMAT0 ;
				pinBlockKey = new PinBlockKey (zpkValue, pinBlockFormatType); 
			}else{
				if ( pinblockValidationTypeStr.equals( PinBlockFormatType.ISOFORMAT3.toString() ) ){
					pinBlockFormatType = PinBlockFormatType.ISOFORMAT3 ;
					pinBlockKey = new PinBlockKey (zpkValue, pinBlockFormatType); 
				}else{
					log.error ( "getPinBlockValidationElements not found pinBlockFormatType "  ) ;
				}
			}

		}
		catch ( DesKeyException e ){
			log.error ( "DesKeyException getPinBlockValidationElements : " + e.getMessage()  ) ; 
		}
		
	}

	private void getBinNumber() {

		NodeList nl = this.docEle.getElementsByTagName("BIN");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				Element 	el 	= (Element)nl.item(i);
				binKey = new BinKey( el.getAttribute("NUMBER")  );
				
				getPinValidation(el ) ;
				getCvvValidation(el ) ;
				
				xmlkeyData.putBinKey( binKey );
		
			}
		}
	}
	
	private void getPinValidation(Element aElement ) {
//
		String pinValidationTypeStr="";
		String pinValidationDataTypeStr ="" ;
		String pvkValue="";
		int pvki = 0 ;	
//
		int pingLength= 0;
		String decimalizationTable="";
		int panStartPosition = 0;
		int panEndPosition = 0;
		char panPadCharacter = 0x00;
//		
		NodeList nl = aElement.getElementsByTagName("PIN_VALIDATION");
		for(int i = 0 ; i < nl.getLength();i++) {
				
			Element el = (Element)nl.item(i);
				
			pinValidationTypeStr 	=  el.getAttribute("VALIDATION_TYPE");
			pinValidationDataTypeStr=  el.getAttribute("PINVALIDATIONDATA_TYPE");
			pvkValue 				=  getTextValue(el,"PVK");

			
			if (pinValidationTypeStr.equals( PinValidationType.IBM_3624_OFFSET.toString() )){

				pingLength 	= Integer.parseInt( getTextValue(el,"PIN_LENGTH") );
				binKey.setPinLength( pingLength ) ;
				decimalizationTable = getTextValue(el,"DECIMALIZATION_TABLE");
				binKey.setDecimalizationTable( decimalizationTable ) ;
				
				if ( pinValidationDataTypeStr.equals ( "THALES8000") ) {

					panStartPosition		= Integer.parseInt( getTextValue(el,"PAN_START_POSITION") ) ;
					panEndPosition			= Integer.parseInt( getTextValue(el,"PAN_END_POSITION") ) ;
					panPadCharacter			= getTextValue(el,"PAN_PAD_CHARACTER").charAt(0) ;
					binKey.setPinValidationDataType(pinValidationDataTypeStr);
					binKey.setPanStartPosition( panStartPosition );
					binKey.setPanEndPosition( panEndPosition );
					binKey.setPanPadCharacter ( panPadCharacter );
				}else
				if ( pinValidationDataTypeStr.equals ( "THALES7000") ) {
					binKey.setPinValidationDataType(pinValidationDataTypeStr);
					int insertPosition = Integer.parseInt( getTextValue(el,"INSERT_POSITION") );
					binKey.setInsertPosition(insertPosition);

				}else{
					log.error ( "pinValidationDataType not found for IBM_3624_OFFSET "  ) ;
				}
				
			}
			
			//log.error ( "getPinBlockValidationElements not found pinBlockFormatType "  ) ;
			if ( el.getNodeType() == Node.ELEMENT_NODE ) {
					
				Element ael = (Element) el;	
				NodeList anl = ael.getElementsByTagName("PVK");
	
					for (int k = 0; k < anl.getLength();k++){
						Element bel = (Element)anl.item(k);
						pvki	= Integer.parseInt( bel.getAttribute("PVKI") ) ;
					}
			}
			
			try{
				//String str = PinValidationType.VISA_PVV.toString() ;
				if ( pinValidationTypeStr.equals(  PinValidationType.VISA_PVV.toString() )){
					binKey.setPinValidationType( PinValidationType.VISA_PVV ) ;
				}
				if ( pinValidationTypeStr.equals( PinValidationType.IBM_3624_OFFSET.toString() ) ){
					binKey.setPinValidationType( PinValidationType.IBM_3624_OFFSET ) ;
				}
				
				binKey.setPvk( pvkValue, pvki) ;
				
			}
			catch ( DesKeyException e ){
				log.error ( "setPinValidationType + setPvk : " + e.getMessage()  ) ; 
			}			
		}
	}

	private void getCvvValidation(Element aElement  ) {
//
		String validationType="";
		String dateFormat="";
		String cvkValue = "";
//		
		NodeList nl = aElement.getElementsByTagName("CVK");
		for(int i = 0 ; i < nl.getLength();i++) {
				
			Element el = (Element)nl.item(i);
				
			validationType 	= el.getAttribute("TYPE");
			dateFormat	 	= el.getAttribute("DATEFORMAT");
			cvkValue 		= el.getTextContent() ;
			
			try {
				if ( validationType.equals("CVV")){
					binKey.setCvv(cvkValue) ;
					binKey.setCvvKeyDateFormat(dateFormat) ;
				}else
				if ( validationType.equals("CVV2")){
					binKey.setCvv2(cvkValue) ;	
					binKey.setCvv2KeyDateFormat(dateFormat) ;
				}
			}
			catch ( DesKeyException e ){
				log.error ( "getCvvValidation : " + e.getMessage()  ) ; 
			}
		}
	}
	
	/**
	 * I take a es.indarsoft.cardutl.xml element and the tag name, look for the tag and get
	 * the text content 
	 * i.e for <employee><name>John</name></employee> org.inds.cardutl.xml snippet if
	 * the Element points to employee node and tagName is name I will return John  
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	@SuppressWarnings("unused")
	private int getIntValue(Element ele, String tagName) {
		
		//in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele,tagName));
	}
}	