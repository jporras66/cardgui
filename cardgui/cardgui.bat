REM @ECHO OFF
REM 
REM
SET JRE_HOME="C:\Java\jdk1.8.0_92"
REM 
SET JRE=%JRE_HOME%
SET PATH=%JRE%\bin;%PATH%
SET CLASSPATH=%JRE_HOME%\jre\lib;.\data\config
REM
%JRE%\bin\java -jar cryptocardUtl-1.0.0.jar 
REM