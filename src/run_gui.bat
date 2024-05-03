@echo off
javac -d ..\bin -cp . gui\*.java util\*.java algorithm\*.java
java -cp ..\bin gui.MainGUI
