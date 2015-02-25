package com.indarsoft.cardutl.file;

import org.apache.log4j.Logger;

import com.indarsoft.cryptocard.card.Card;
import com.indarsoft.cryptocard.card.CardException;
import com.indarsoft.cryptocard.types.PinBlockFormatType;
import com.indarsoft.cryptocard.types.PinValidationType;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class CardFile {

	static Logger log = Logger.getLogger( CardFile.class.getName() );
	private String fileName ; 
	
	public CardFile ( String fileName ){
		
		this.fileName 		= fileName ;
	}
	
	public ArrayList<String> readArrayListRecord() throws  IOException {
		
		BufferedReader bri = new BufferedReader(new FileReader( this.fileName ) );
		
        String line;
        ArrayList<String> als = new ArrayList<String>() ;
        
        while((line = bri.readLine()) != null) {
        	als.add( line ) ;
        }
        bri.close();
       
        return als ;
	}

	public ArrayList<Card> readArrayListCard ( ) throws  IOException  {
		
	
        ArrayList<String> als 		= readArrayListRecord ( ) ;
        ArrayList<Card>   alCard	= new ArrayList<Card>() ;   

        for (int i=0; i<als.size(); i++){
        	int lineLength = als.get( i ).length() ;
        	if (! ( als.get( i ).contains("#") || lineLength <= 1 )  ){
        		try{
            		Card card = parseCard( als.get( i ) );
            		if ( card != null) {
            			alCard.add( card );
            		}
        		}
        		catch ( CardException e){
    				log.debug( e.getMessage() ) ;
    			}
        	}   	
        }
        return alCard ;
	}
	
/* ----------------------------------------------------------------------------------------------- */
	
	private Card parseCard ( String record ) throws CardException {
	
		String[] atr = new String[3] ; 
		Card card 			= null ;
		if ( ! record.contains("#")  ){
			
		
			String dummy		= record.replaceAll("\\t+", " ").replaceAll("\\s+" , " ") ;
			atr = dummy.split("\\s");
					
			try{
				card = new Card( atr[1] ) ;
				card.setServiceCode( atr[2] );
				card.setExpirationDate ( atr[3] );
				card.setPvki( Integer.parseInt( ( atr[4] ) ) );

				String pinValidationType = atr[5]; 
				if ( pinValidationType.equals( "VISA_PVV" ) ) {
					card.setPinValidationType( PinValidationType.VISA_PVV  );
					card.setPvv (  atr[6]  );
				}else 
				if ( pinValidationType.equals( "IBM_OFFSET" ) ) {
					card.setPinValidationType( PinValidationType.IBM_3624_OFFSET  );
					card.setOffset (  atr[6]  );
				}else {
					log.debug( "pinValidationType error in record : " + record ) ;
					log.debug( "pinValidationType valid values are  : VISA_PVV or IBM_OFFSET" ) ;
					return null ; 
				}
//
				String pinblockFormatType = atr[7]; 
				if ( pinblockFormatType.equals( "ISOFORMAT0" ) ) {
					card.setPinBlockFormatType( PinBlockFormatType.ISOFORMAT0  );
				}else 
				if ( pinblockFormatType.equals( "ISOFORMAT3" ) ) {
					card.setPinBlockFormatType( PinBlockFormatType.ISOFORMAT3  );
				}else {
					log.debug( "pinblockFormatType error in record : " + record ) ;
					log.debug( "pinblockFormatType valid values are  : ISOFORMAT0 or ISOFORMAT3" ) ;
					return null ; 
				}
				
			}
			catch ( CardException e){
				String errMsg = atr[1] + " - " +  e.getMessage() ;
				log.debug( errMsg ) ;
				return null ;
			}
		
		}
		
        return card ;
	}	
	
}
