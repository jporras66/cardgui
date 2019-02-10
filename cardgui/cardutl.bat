@ECHO OFF
REM 
REM Parameters :
REM     <cards_filename>  	data 			directory
REM     <key_filename> 		data\config 	directory
REM
REM
SET JRE_HOME="C:\Java\jdk1.8.0_92"
REM 
SET JRE=%JRE_HOME%
SET PATH=%JRE%\bin;%PATH%
SET CLASSPATH=%JRE_HOME%\jre\lib;.\data\config
REM
REM 	Usage is    : cardutl.bat <cards_filename> <key_filename>
REM 
REM
REM %JRE%\bin\java -cp  cryptocardUtl-1.0.0.jar es.indarsoft.cardutl.batch.Main %1 %2
%JRE%\bin\java -cp  cryptocardUtl-1.0.0.jar es.indarsoft.cardutl.batch.Main cards.txt key.xml
REM