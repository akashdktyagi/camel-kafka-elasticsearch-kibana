@echo off
cd /d c:\kafka\bin\windows\
start zookeeper-server-start.bat c:\kafka\config\zookeeper.properties
timeout /t 5
start kafka-server-start.bat c:\kafka\config\server.properties
timeout /t 5

