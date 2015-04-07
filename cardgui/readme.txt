1. 	Cryptography for VISA/MASTERCAD card (and others).

	PIN (PVV/IBM OFFSET), CVV, CVV2, ICVV, PINBLOCK (clear or encrypted)
	
2. Verify your JDK	(version 1.7.0_65 or above) . Open a Command Line Window

	C:>echo %JRE_HOME%
	"C:\Program Files\Java\jre7"
	
	C:>java -version
	java version "1.7.0_72"
	Java(TM) SE Runtime Environment (build 1.7.0_72-b14)
	Java HotSpot(TM) Client VM (build 24.72-b04, mixed mode, sharing)
		
	Edit cardgui.bat and cardutl.bat and update variable : JRE_HOME
		
3. 	Edit key file .\data\config\key.xml 
		
		ZPK Zone Pin Key. Pin block validation can be ISOFORMAT0 or ISOFORMAT3.
		
		Per BIN number adds :
		- Add CVKs pair, one for CVV/ICVV (or CVC/ICVC) and another for CVV2 ( or CVC2 ).
		- One PVK for every BIN number.
			Pin validation Type can be Visa PVV or IBM_3624_OFFSET.
			For IBM_3624_OFFSET type pin validation data type can be (THALES700 or THALES800)
				- THALES7000 Pin Validation Data is calculated as follows: 
					* Refer to Thales 7000 manual - 9.4 IBM PIN Offset (command code value 'DE' )
					* - Computes Account Number : Takes the 12 right-most digits of the account number, excluding check digit.
					* - Inserts the last 5 digits of the account number (previous data) in a given position <INSERT_POSITION>
					* - Returns this data
				- THALES8000 Pin Validation Data is calculated as follows: 
					* Refer to Thales HSM 8000 Host Command Reference Manual - Generate an IBM PIN Offset (command code value 'DE' )
					* - Takes characters from Pan Number starting at position <PAN_START_POSITION> and ending at <PAN_END_POSITION> ( 1 <= sp < ep <= 15 ) 
					* - Add pad character <PAN_PAD_CHARACTER>, until a 16 characters length is completed.
					* - Returns this data
		
4. 	Batch utility

	- Create your own file with cards to be processed ( use .\data\cards.txt as a template )
		- All fields are mandatory. 
		- Lines that contains an '#' character, are considered as a comment.
	
	- Open a cmd window and execute :
		.\cardutl.bat <cards_filename> <key_filename> 
	- Once finished, go to .\data and check .out file (and .\log\cryptocardutl.log for warnings and errors).

5. 	GUI utility 
	
 	- Edit cardgui.bat and point JRE_HOME variable to your JRE 
	- Execute cardgui.bat

	

				