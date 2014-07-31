package es.indarsoft.cardutl.batch;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;

import es.indarsoft.cardutl.beans.BinKey;
import es.indarsoft.cardutl.beans.PinBlockKey;
import es.indarsoft.cardutl.beans.ProcessedCardData;
import es.indarsoft.cardutl.card.ProcessCard;
import es.indarsoft.cardutl.file.CardFile;
import es.indarsoft.cardutl.xml.LoadXmlKeys;
import es.indarsoft.cardutl.xml.XmlKeyData;
import es.indarsoft.cryptocard.card.Card;
import es.indarsoft.cryptocard.card.CardException;
import es.indarsoft.cryptocard.types.PinValidationType;
import es.indarsoft.utl.Utl;

public class Main {

	static Logger log = Logger.getLogger( Main.class.getName() );
	  
	private static String BASEDIR ="" ;
	private static String separator = File.separator ; 
	public static final String NF = "N/F";
	private static String cardFileName ;
	private static String xmlKeyFileName ;
	@SuppressWarnings("unused")
	private static PinValidationType pinValidationType;
	private static String cardFile;
	private static String xmlFile;
	private static String outFile;
	
	private static LoadXmlKeys lk  ;
	private static XmlKeyData xmlkeyData ;
	
	private static ArrayList<Card> alc;
	private static BufferedWriter bro ;
	
	public static void main( String[] args ) {

		Main cu = new Main();
		cu.checkUsage ( args ) ;
//		
		BASEDIR = Utl.getPwd();
		cardFile = BASEDIR + separator + "data" + separator + cardFileName ;
		xmlFile	 = BASEDIR + separator + "data" + separator + "config" + separator + xmlKeyFileName ;	
		outFile = cardFile + ".out" ;
		
		System.out.println("starting ....") ;
		
		try {
			bro = new BufferedWriter(new FileWriter( outFile ) );	
			cu.writeHeader() ;
		}catch ( IOException ex){
			doExit ( "BufferedWriter : "+ ex.getMessage() ) ;
		}
/*
 *  Load Keys in ./data/config DIR
 */
		lk = new LoadXmlKeys(xmlFile) ;
		try {
			xmlkeyData = lk.loadXMLData() ;
		}
		catch ( Exception ex){
			doExit (  "LoadXmlKeys : "+ ex.getMessage() ) ;
		}
/*
 * Load Card's txt ./data DIR and process every card 		
 */
		alc = cu.loadCardFile( cardFile ) ;
		for (int i=0; i< alc.size(); i++){
			Card a = alc.get( i ) ;
			cu.processCard( a ) ;
		}
//
        try {
			bro.close();
		} catch (IOException ex) {
			doExit ( "loadCardFile : " + ex.getMessage() ) ;
		}
        
		System.out.println("finishing ....") ;
    	System.exit(0);
	}

/* --------------------------------------------------------------------------------------------------------- */


	
	private ArrayList<Card> loadCardFile (String fileName){
	
		ArrayList<Card> alc = null ;
		CardFile u = new CardFile( fileName ) ;
		try{
			alc = u.readArrayListCard() ;
		}
		catch( IOException ex){
			doExit ( ex.getMessage() ) ;
		}
		return alc ;
	}

	
	private void processCard ( Card card ) {
		
		BinKey binkey 			= xmlkeyData.getBinKey( card.getPanNumber().substring( 0, 6) ) ;
		
		String str = card.getPinBlockFormatType().toString() ;
		PinBlockKey pinBlockKey = xmlkeyData.getPinBlock( str ) ;
		
		ProcessedCardData pcd = ProcessCard.doit(card, binkey, pinBlockKey) ;
//
// Adjust detailed line for output printing
//		
		String pinvalue = pcd.getPin() ;
		if ( pinvalue.equals( NF ) ){
			pinvalue = pinvalue + "\t";
		}
		
		String track2value = pcd.getTrack2() ;
		if ( track2value.length() < 32 ){
			track2value = track2value + "    "; // adds 4 blanks
		}		
//		
		String detailedline = "\t"+ pcd.getPanNumber() + "\t" + pcd.getServiceCode() + "\t\t" +  pcd.getExpirationDate() + "\t"+ pcd.getDki() + "\t" + pcd.getPinValType() + "\t" +pcd.getPinValidationDataType() +"\t"+ pcd.getPvv_offset() + "\t" + pinvalue  ;
		detailedline = detailedline + "\t" + pcd.getCvv() + "\t\t" + pcd.getIcvv() + "\t\t" + pcd.getCvv2() ;
		detailedline = detailedline + "\t\t" + track2value  ;
		detailedline = detailedline + "\t"   + pcd.getPinblockValType() + "\t" + pcd.getClearPinblock() + "\t"+  pcd.getEncryptedPinblock() +"\n";
				
		writeline (detailedline) ;
//
//		Process all dki's 
//
		/*
		 * 	if the issuer (BIN) uses the PIN Verification Service (PVS) for
			some, but not for all issued cards, the PIN Verification Data field
			(both PVKI and PVV) should be zero-filled on those cards not
			using the PVS. If the issuer does not use the PVS for any cards in
			a card range, the zero-fill requirement is not needed.
			Refer to Chapter 6 for more information on the PVKI and PVV.
		 *			
		 */		
		String newpinvalue = pcd.getPin() ;
		if ( newpinvalue.equals( NF ) ){ 
			for ( int k = 1; k  < 6 ; k ++){
				try {
					card.setPvki( k );
					pcd = ProcessCard.doit(card, binkey, pinBlockKey) ;
//
					String computedPin = pcd.getPin() ;
					if ( ! computedPin.equals( NF  ) ){
						String dummy = "\t\t\t\t\t\t\t\t\t\t"+ pcd.getDki()  + "\t\t" +"\t\t\t\t" + pcd.getPvv_offset() + "\t\t\t\t"+pcd.getPinValidationDataType() + "\t" + computedPin  ;
						dummy = dummy + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + pcd.getClearPinblock() + "\t"+  pcd.getEncryptedPinblock() +"\n";
						writeline (dummy) ;
					}					
//					
					
				} catch (CardException e) {
					log.warn( "Process all dki's " + e.getMessage()  ) ; 
				}
			}
		} 		
	}

	
	private void checkUsage( String[] a ){
		
        String osName = System.getProperty("os.name").toLowerCase() ;
		String cmdExt ="";
        
        if ( osName.contains("windows") ){
        	cmdExt = "bat" ;
        }else{
        	cmdExt = "sh"  ;
        }
        
        if ( a.length != 2 ){ 
        	System.out.println("Usage is    : cardutl." + cmdExt + " <cards_filename> <key_filename> ") ;
        	System.out.println("Example     : cardutl." + cmdExt + " cards.txt key.xml ") ;
        	System.out.println("GoodBye ..  :-)") ;
        	System.exit(-1);
        	//cardFileName 	= "cards.txt";
        	//xmlKeyFileName	= "key.xml";
        }else{
        	cardFileName 	= a[0] ;
        	xmlKeyFileName	= a[1] ;
        }
	}

	private void writeHeader () throws IOException {
		bro.write("#\n") ;
		bro.write("#\n")  ;
		bro.write("#	Card Number 		Service	ExpDate	Dki	PinValType	PinValDat	Pvv/	Pin		cvv		icvv	cvv2	Track2								PinBlock	Clear 				Encrypted \n") ;
		bro.write("#						Code 	(YYMM)								Offset			cvc		icvc	cvc2										Type		Pinblock			Pinblock  \n") ;
		bro.write("#\n")  ;		
	}
	
	private void writeline ( String line ) {
		
		try{
			bro.write(  line );
		}
		catch ( IOException ex){
			String errMsg = "exit(-1) : " + ex.getMessage();
			doExit( errMsg );
		}
	}
	
	private static void doExit ( String errMsg ){
		
		log.debug( "exit(-1) : " + errMsg ) ;
    	System.exit(-1);
	}
	
}
