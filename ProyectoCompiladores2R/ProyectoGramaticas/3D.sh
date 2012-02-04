cd /media/cdisk/Users/Eddy/Util/JLexYCup/
java java_cup.Main -package proyectocompiladores2r -symbols Symbolos3D -parser Parser3D "/home/eddytrex/Desktop/Proyecto/Gramatica3Dir.cup"
cp Symbolos3D.java "/home/eddytrex/Desktop/Eddy C/Documents/NetBeansProjects/ProyectoCompiladores2R/src/proyectocompiladores2r/Symbolos3D.java"
cp Parser3D.java "/home/eddytrex/Desktop/Eddy C/Documents/NetBeansProjects/ProyectoCompiladores2R/src/proyectocompiladores2r/Parser3D.java"

cd /media/cdisk/Users/Eddy/Util/JLexYCup/
java JLex.Main "/home/eddytrex/Desktop/Proyecto/Scanner3D.lex"
cd "/home/eddytrex/Desktop/Proyecto/"
cp  Scanner3D.lex.java "/home/eddytrex/Desktop/Eddy C/Documents/NetBeansProjects/ProyectoCompiladores2R/src/proyectocompiladores2r/Scanner3D.java"
