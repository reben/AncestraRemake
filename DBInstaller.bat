@echo off

title AncestraRemake DB Installer

rem Configurations about Mysql
set host=127.0.0.1
set port=3306
set user=root
set pass=PasswordHere!

rem misc configurations
color 0C

goto menu

:instalador
set /P host=host [%host%]: 
set /P port=port [%port%]: 
set /P user=user [%user%]: 
set /P pass=pass[%pass%]: 
echo.

:DBTables
set /P base_realm=Ancestra_Realm[%base_realm%] 
set /P base_game=Ancestra_Game[%base_game%]

:menu
echo MySQL Config
echo -------------------
echo Host: %host%
echo port: %port%
echo usser: %user%
echo Password: %pass%
echo.
echo.
echo Menu : Choose
echo -------------------
echo.
echo 1 - Install the realm database
echo 2 - Install the game database
echo 3 - Install updates from Realm!
echo 4 - Install updates from Game!
echo 5 - Change the DB autentification settings
echo 6 - Change the DB Table settings


pause>nul