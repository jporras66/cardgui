package es.indarsoft.cardutl.card;

import org.apache.log4j.Logger;

import es.indarsoft.cardutl.beans.BinKey;
import es.indarsoft.cardutl.beans.PinBlockKey;
import es.indarsoft.cardutl.beans.ProcessedCardData;
import es.indarsoft.cryptocard.card.Card;
import es.indarsoft.cryptocard.cardverify.CardVerification;
import es.indarsoft.cryptocard.customerverify.Ibm3624Offset;
import es.indarsoft.cryptocard.customerverify.Pvv;
import es.indarsoft.cryptocard.pinblock.PinBlock;
import es.indarsoft.cryptocard.symmetrickey.Pvk;
import es.indarsoft.cryptocard.symmetrickey.Zpk;
import es.indarsoft.cryptocard.types.PinValidationType;
import es.indarsoft.cryptocard.utl.PinValidationData;

public class ProcessCard {

	static Logger log = Logger.getLogger( ProcessCard.class.getName() );
	public static final String NF = "N/F";

	public ProcessCard() {

	}

	public static ProcessedCardData doit(Card card, BinKey binkey,
			PinBlockKey pinBlockKey) {

		ProcessedCardData pcd = new ProcessedCardData(card);

		Pvk pvk = binkey.getPvk();
		int pvki = pvk.getPvki();

		if (pvki != card.getPvki()) {
			String errMSg = "panNumber : " + card.getPanNumber()+" pvki in Pvk " + pvk.getKeyAsString() + " is : "
					+ pvki + " does not match with pvki " + card.getPvki();
					
			pcd.setErrMsg(errMSg);
			log.warn(errMSg);
		}

		String pin = NF;
		String pvv_offset = NF;

		PinValidationType pinValidationType = card.getPinValidationType();

		if (pinValidationType != binkey.getPinValidationType()) {
			String errMSg = "panNumber : " + card.getPanNumber()+ " pinValidationType in card is " + pinValidationType
					+ " does not match pinValidationType in binkey " + binkey.getBinNumber() + "-" 
					+ binkey.getPinValidationType() ;
			pcd.setErrMsg(errMSg);
			log.warn(errMSg);
		}
		
		if (pinValidationType == PinValidationType.VISA_PVV ) {
			pvv_offset = card.getPvv();
			pcd.setPvv_offset(pvv_offset);
			pcd.setPinValType( pinValidationType.toString() );
			if ( pinValidationType == binkey.getPinValidationType() ){
				try {
					pin = Pvv.findPin(card, pvk);
					pcd.setPin(pin);
				} catch (Exception e) {
					String errMSg = "panNumber : " + card.getPanNumber()+" pvv pin process error : " + e.getMessage() ;
					pcd.setErrMsg(errMSg);
					log.warn(errMSg);
				}
			}else{
				String errMSg = "panNumber : " + card.getPanNumber()+" pin validation type : " +pinValidationType 
								+ " does not match bin : " + binkey.getPinValidationType();
				pcd.setErrMsg(errMSg);
				log.warn(errMSg);
			}
		}

		pcd.setPinValidationDataType( "N/A       "); 
		if (pinValidationType == PinValidationType.IBM_3624_OFFSET) {
			pvv_offset = card.getOffset();
			pcd.setPvv_offset(pvv_offset);
			pcd.setPinValType("IBM_OFFSET");
			pcd.setPinValidationDataType(binkey.getPinValidationDataType());
//			
			Ibm3624Offset ibm3624Offset = null;
			String dectable =  binkey.getDecimalizationTable();
//			
			if ( pinValidationType == binkey.getPinValidationType()  ){
				
				if ( binkey.getPinValidationDataType().equals("THALES8000") ){
					int sp = binkey.getPanStartPosition();
					int ep = binkey.getpanEndPosition();
					char pad = binkey.getPanPadCharacter();
					try {
						String pinValidationData = PinValidationData.build8000(card.getPanNumber(), sp, ep, pad);
						ibm3624Offset = new Ibm3624Offset(pvk,dectable );
						pin = ibm3624Offset.generatePin( pinValidationData, card.getOffset());
						pcd.setPin(pin);
					} catch (Exception e) {
						String errMSg = "panNumber : " + card.getPanNumber()+ " ibm3624Offset pin process error : "+e.getMessage() ;
						pcd.setErrMsg(errMSg);
						log.warn(errMSg);
					}
				}
				else
				if ( binkey.getPinValidationDataType().equals("THALES7000") ){
					try {
						String pinValidationData = PinValidationData.build7000(card.getPanNumber(), binkey.getInsertPosition());
						ibm3624Offset = new Ibm3624Offset(pvk,dectable );
						pin = ibm3624Offset.generatePin( pinValidationData, card.getOffset());
						pcd.setPin(pin);
					} catch (Exception e) {
						String errMSg = "panNumber : " + card.getPanNumber()+ " ibm3624Offset pin process error : "+e.getMessage() ;
						pcd.setErrMsg(errMSg);
						log.warn(errMSg);
					}
				}
				else{
					String errMSg = "panNumber : " + card.getPanNumber()+ " ibm3624Offset pin validation data type error : " + binkey.getPinValidationDataType() ;
					pcd.setErrMsg(errMSg);
					log.warn(errMSg);
				}

			}else{
				String errMSg = "panNumber : " + card.getPanNumber()+" pin validation type : " +pinValidationType 
						+ " does not match bin : " + binkey.getPinValidationType();
				pcd.setErrMsg(errMSg);
				log.warn(errMSg);
			}
		}
//
//
// Calculate cvv/cvc icvv/icvc & cvv2/cvc2
//
		CardVerification cvv = new CardVerification(binkey.getCvvKey(), card);
		try {
			pcd.setCvv(cvv.getCvv());
			pcd.setIcvv(cvv.getIcvv());
		} catch (Exception e) {
			String errMSg = "cvv/icvv process error : " + e.getMessage();
			pcd.setErrMsg(errMSg);
			log.warn(errMSg);
		}

		CardVerification cvv2 = new CardVerification(binkey.getCvv2Key(), card);
		try {
			pcd.setCvv2(cvv2.getCvv2());
		} catch (Exception e) {
			String errMSg = "cvv2 process error : " + e.getMessage();
			pcd.setErrMsg(errMSg);
			log.warn(errMSg);
		}
		//
		// Calculate PinBlock
		//

		pcd.setPinblockValType(pinBlockKey.getPinBlockFormatType().toString());
		if (!pin.equals(NF)) {
			Zpk zpk = pinBlockKey.getZpk();
			try {
				card.setPin(pin);
				PinBlock pinBlockIso = PinBlock.getEncrypted(card, zpk);
				pcd.setEncryptedPinblock(pinBlockIso
						.getEncryptedPinBlockasString());
				pcd.setClearPinblock(pinBlockIso.getClearPinBlockasString());
			} catch (Exception e) {
				String errMSg = "pinBlockKey process error : " + e.getMessage();
				pcd.setErrMsg(errMSg);
				log.warn(errMSg);
			}
		} else {
			pcd.setEncryptedPinblock(NF);
			pcd.setClearPinblock(NF);
		}
//
// Calculate track2
//
		pcd.setTrack2();

		return pcd;
	}
}
