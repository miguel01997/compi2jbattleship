/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author eddytrex
 */
public class Return extends Instruccion{
    
    public Exp Expresion;
    
    public Return(int Nolinea,Exp exp)
    {
        Expresion=exp;
        NoLinea=Nolinea;
    }
    
    public Return(Exp exp)
    {
        Expresion=exp;
    }
    
    public boolean Debug()
    {
        boolean res=true;
        return res;
    }
    
    public boolean Ejecutar(){boolean res=true; 
                              res=Expresion.Ejecutar(); 
                              if(res&&Expresion.tipos.Equivalencia(SimboloMetodo.tipos)){
                                  
                                    Memoria.SetPilaR(tabla.BuscarV("return").PosRel, Expresion.Valor);
                                  //  Memoria.SetPilaR(0, Expresion.Valor);
                                    Return=true;
                              }
                              else{res=false;}//Error: el tipo declarado no Coincide con el de la exprecion
                              return res;
                             }
    
    
    
    public boolean CargarTS(){
                               boolean res=true;
                               Expresion.SimboloMetodo=SimboloMetodo;
                               Expresion.tabla=tabla;
                               Expresion.Metodo=Metodo;
                               
                               Expresion.Clase1=Clase1;
                               
                               res=Expresion.CargarTS();
                               return res;
                             }
    
    public String Generar3Direcciones(){
        String ret=""; 
        String ins1="",ins2="",ins3="";
        String temp1;
        
        String trExp=Expresion.Generar3Direcciones();
        
        
        Simbolo sRet=tabla.BuscarV("return");        
        if(sRet!=null) {
        if(this.Expresion.tipos.Tipos.getFirst().equals(this.SimboloMetodo.Tipo))
        {
        temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                
        ins1=temp1+"=ptr"+"+"+Integer.toString(sRet.PosRel)+";\n";  
        ins2=Memoria.GetPilaRTS(temp1)+"="+this.Expresion.Valor+";\n";
        ins3="goto "+this.SimboloMetodo.LabelFin+";\n";
        }
        else{Errores.InsertarError(this.NoLinea,"para la expresión de return no conicide con la declarada ");}
        }
        else
        {
            ins3="goto "+this.SimboloMetodo.LabelFin+";\n";
        }
        
        ret=trExp+"\n"+ins1+ins2+ins3;
        
        return ret;
    }
    
}
