/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author eddytrex
 */
public class Break extends Instruccion{
    
   //La sintrucción break no lleva mas atributos en su constructor que le No linea
   //Pero se le pasa por atributos heredados la teiqueta de la instrucción siguiente para hacer un salto incondiciónal
   public Break(int Nolinea) {NoLinea=Nolinea;  
        TablaDeSimbolos.InsertBreakPoint(NoLinea);
        TablaDeSimbolos.DisableBreakPoint(Nolinea);
   }
   
   public Break(){}
   
   
   
   public boolean Ejecutar(){
       boolean bandera=true;
       boolean res=true;
       if(TablaDeSimbolos.Debug)
       {
           if(Debug(tabla))
           {        
           }
           else
           {
               bandera=false;
                Return=true;
                return true;
           }
       }
       if(bandera)
       {
       this.Break=true; }
       return res; 
   }
   
   public boolean CargarTS(){boolean res=true; return res;}
   
   //Ya que se le pasa el  la etiqueta de la instrucción sigueinte (LabelSig) se traduce como goto etiquetasig;
   public String Generar3Direcciones()
   {
   String ret=""; 
   String ins1;
   ins1="goto "+this.LabelSig+";\n";   
   ret=ins1;
   return ret;
   }
    
}
