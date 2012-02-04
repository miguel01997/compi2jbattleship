cd /media/cdisk/Users/Eddy/Util/JLexYCup/
java java_cup.Main -package proyectocompiladores2r -symbols SymbolosProyecto -parser ParserP "/home/eddytrex/Desktop/Proyecto/GramaticaProyecto.cup"
cp SymbolosProyecto.java "/home/eddytrex/Desktop/Eddy C/Documents/NetBeansProjects/ProyectoCompiladores2R/src/proyectocompiladores2r/SymbolosProyecto.java"
cp ParserP.java "/home/eddytrex/Desktop/Eddy C/Documents/NetBeansProjects/ProyectoCompiladores2R/src/proyectocompiladores2r/ParserP.java"

cd /media/cdisk/Users/Eddy/Util/JLexYCup/
java JLex.Main "/home/eddytrex/Desktop/Proyecto/ScannerProyecto.lex"
cd "/home/eddytrex/Desktop/Proyecto/"
cp  ScannerProyecto.lex.java "/home/eddytrex/Desktop/Eddy C/Documents/NetBeansProjects/ProyectoCompiladores2R/src/proyectocompiladores2r/ScannerProyecto.java"
