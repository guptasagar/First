@echo off
rem
rem This file is generated for the Confluence Project Configuratiobn
rem

echo Setting up Confluence  environment...


set CONFLUENCE_DIR=D:\Sagar Workspace\repos\confluence
set CCTECHUTILITY_LIB=D:\Sagar Workspace\All repos\CCTech Utility\bin
set CCTECHFRAMEWORK_LIB=D:\Sagar Workspace\All repos\repos\CCTechFramework\bin
set CONFLUENCESERVER_LIB=D:\Sagar Workspace\repos\confluence\bin

rem echo -----------------------------------------------------------------------------------------------
rem set PATH=%THIRD_PARTY_LIBS%;%PATH%
rem echo %PATH%
rem echo ------------------------------------------------------------------------------------------------

call "C:\Program Files (x86)\Microsoft Visual Studio 10.0\Common7\Tools\vsvars32.bat"
"C:\Program Files (x86)\Microsoft Visual Studio 10.0\Common7\IDE\devenv.exe"  .\depot\3DPrintTech\3DPrintTech.sln