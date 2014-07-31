package es.indarsoft.cardutl.beans;

import es.indarsoft.cryptocard.symmetrickey.DesKeyException;
import es.indarsoft.cryptocard.symmetrickey.Zpk;
import es.indarsoft.cryptocard.types.PinBlockFormatType;

public class PinBlockKey  {

	private Zpk zpk ;
	private PinBlockFormatType pinBlockFormatType ;
	
	public PinBlockKey (String zpkValue , PinBlockFormatType pinBlockFormatType ) throws DesKeyException {
		
		/*try {
			this.zpk = new Zpk(zpkValue);
			this.pinBlockFormatType = pinBlockFormatType ;
		}
		catch ( Exception ex){
			System.out.println( ex.getMessage() ) ;
			throw new Exception(ex.getMessage() );
		}*/
		this.zpk = new Zpk(zpkValue);
		this.pinBlockFormatType = pinBlockFormatType ;
	}
	
	public Zpk getZpk(){
		return zpk ; 
	}
	
	public PinBlockFormatType getPinBlockFormatType(){
		return pinBlockFormatType ;
	}	
}
