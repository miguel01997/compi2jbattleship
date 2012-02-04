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
public class Else extends Instruccion{
    
    public String LabelTrue;
    public String LabelFalse;
    public String LabelSig;
 
    public Else(int Nolinea, LinkedList<Instruccion>Si)
    {
        SiguienteInstruccion=Si;
        NoLinea=Nolinea;
        TablaDeSimbolos.InsertBreakPoint(NoLinea);
        TablaDeSimbolos.DisableBreakPoint(NoLinea);
    }
    
    public Else(LinkedList<Instruccion>Si)
    {
        SiguienteInstruccion=Si;
    }
    
 
       
public boolean CargarTS()
{
    boolean res=true;
    TablaDeSimbolos nTabla=new TablaDeSimbolos();
    nTabla.tablaArriba=tabla;
    tabla=nTabla;
    int i;
    if(SiguienteInstruccion!=null)
    {
        for(i=0;i<SiguienteInstruccion.size()&&res;i++)
        {
            if(SiguienteInstruccion.get(i)!=null)
            {
                SiguienteInstruccion.get(i).tabla=tabla;
                
                
                
                SiguienteInstruccion.get(i).Metodo=Metodo;
                SiguienteInstruccion.get(i).SimboloMetodo=SimboloMetodo;
                
                SiguienteInstruccion.get(i).Clase=Clase;
                
                //SiguienteInstruccion.get(i).Clase1=this.Clase1;
                res=SiguienteInstruccion.get(i).CargarTS();
            }
        }
    }
    return res;
}

public boolean Ejecutar()
    {
        boolean res=true;
        boolean bandera=true;
        int i;
         if(TablaDeSimbolos.Debug)
        {
            if(Debug(tabla))
            {
                
            }
            else
            {
                Return=true;
                bandera=false;
                return true;
            }
        }
        if(bandera)
        {
        if(SiguienteInstruccion!=null)
        {
            for(i=0;i<SiguienteInstruccion.size()&&res&&bandera;i++)
            {
                if(SiguienteInstruccion.get(i)!=null)
                {
                    res=SiguienteInstruccion.get(i).Ejecutar();
                    if(this.SiguienteInstruccion.get(i).Break){bandera=false;}
                    if(this.SiguienteInstruccion.get(i).Return){bandera=false; Return=true;}
                }
            }
        }
        }
        return res;
    }

public String Generar3Direcciones()
    {
        String ret=""; 
        String Sintrucciones="";
        int i;
        if(this.SiguienteInstruccion!=null)
        {
            for(i=0;i<this.SiguienteInstruccion.size();i++)
            {
                Sintrucciones=Sintrucciones+this.SiguienteInstruccion.get(i).Generar3Direcciones();
            }
        }
        ret=Sintrucciones;
        return ret;
    }    

}
