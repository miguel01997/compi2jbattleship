/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AST;

import java.util.LinkedList;

/**
 *
 * @author Eddy
 */
public class Asignacion extends Instruccion {
    public ExpID Variable;
    public boolean New;
    public Exp   Expresion;
    
    

    
    
    public Asignacion(ExpID v,Exp e,boolean n)
    {
        Variable=v;
        Expresion=e;
        New=n;
    }
    //una Instrucción de Asignación cuenta con una Variable(ExpID) y una Expresión (
    //opreación,llamda a una fusión, un literal (e.g. 1,1.5,"hola",'hola',true,flase))
    //el parametro n debe no hace nada xD xD 
    public Asignacion(int Nolinea,ExpID v,Exp e,boolean n)
    {
        Variable=v;
        Expresion=e;
        New=n;
        NoLinea=Nolinea;
        TablaDeSimbolos.InsertBreakPoint(NoLinea);
        TablaDeSimbolos.DisableBreakPoint(NoLinea);
    }
    
    public boolean Ejecutar()
    {
        boolean res=true;
        int DireccionSoH;//Stack o Heap;
        boolean bandera=true;
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
        res=Variable.Ejecutar();
        if(res){
        res=Expresion.Ejecutar();
        }
        if(res)
        {
         if(Expresion.tipos.SearchPrimitivo())
         {
             DireccionSoH=Variable.Direccion;
             if(Variable.Stack)
             {
                 if(Variable.Global)
                 {
                 Memoria.SetPilaG(DireccionSoH, Expresion.Valor); //Variable Global. 
                 }
                 else
                 {
                 Memoria.SetPilaR(DireccionSoH, Expresion.Valor);  //Variable Local.
                 }
             }
             if(Variable.Heap)
             {
                 Memoria.SetHeap(DireccionSoH, Expresion.Valor); // Atributos en los objetos. 
             }
         }
         else // Si hubo un error en la subExpresion de Variable o Expresion
         {
              Errores.InsertarError(this.NoLinea, "Error no se puede Assignar: " + this.Variable.Variable + " De Tipo " + this.Variable.tipos.Tipos() + " la exprecion de tipo " + this.Expresion.tipos.Tipos());
              res = false;
             
         }
        }
        }
        return res;
    }

    //Pasar los atributos de la tabla de simbolos 
    //Clase y Metodo al que pertenecen tanto a la Expresión como a la Variable 
    public boolean CargarTS()
    {
        boolean res=true;
        if(Expresion!=null)
        {
            
            Expresion.tabla=tabla;
            
            Expresion.Clase=Clase;
            
            Expresion.SimboloMetodo=SimboloMetodo;
            Expresion.Metodo=Metodo;
            
            Expresion.CargarTS();
        }
        if(Variable!=null)
        {
             
             Variable.tabla=tabla;
             
             Variable.Clase=Clase;
             
             Variable.SimboloMetodo=SimboloMetodo;
             Variable.Metodo=Metodo;
             
             Variable.CargarTS();
        }
        return res;
    }
    
    
    //Generar Three Adress Code Para la Instrucción asignación 
    public String Generar3Direcciones()
    {
        
        String ret=""; 
        String CExp;
        String CVar;
        String ins1,ins2,ins3;
        CExp=this.Expresion.Generar3Direcciones();
        CVar=this.Variable.Generar3Direcciones();
        if(this.Expresion.tipos.Equivalencia(Variable.tipos))
        {
             if(Variable.Stack)
             {
                 if(this.Variable.tipos.SearchPrimitivo())
                 {
                 if(this.Variable.tipos.Buscar("int"))
                  {
                    ins1=Memoria.GetPilaRTS(Variable.Dir)+"="+this.Expresion.Valor+";\n";
                    ret=CExp+CVar+ins1;
                  }
                 }
                 else
                 {
                    ins1=Memoria.GetHeapRTS(Variable.Dir)+"="+this.Expresion.Valor+";\n";
                    ret=CExp+CVar+ins1;
                 }
             }             
             if(Variable.Heap)
             {
                 if(this.Variable.tipos.SearchPrimitivo())
                 {
                 if(this.Variable.tipos.Buscar("int"))
                 {
                     ins1=Memoria.GetHeapRTS(Variable.Dir)+"="+this.Expresion.Valor+";\n";
                     ret=CExp+CVar+ins1;
                 }
                 }
                 else
                 {
                     ins1=Memoria.GetHeapRTS(Variable.Dir)+"="+this.Expresion.Valor+";\n";
                     ret=CExp+CVar+ins1;
                 }
             }        
        }
        else
        {
            Errores.InsertarError(this.NoLinea,"las Expresiónes no son del mismo tipo");
            Errores.Generar3D=true;
        }
        return ret;
    }

}
