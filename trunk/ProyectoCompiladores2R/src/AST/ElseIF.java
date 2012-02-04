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
public class ElseIF extends Instruccion {
    public Exp Condicion;
    
    public Exp ExpSwitch;    
    public boolean Switch;
    
    public boolean Do = false;
    
    public String LabelFalse;
    public  String LabelTrue;
    public  String LabelSig;
    
    public ElseIF(int Nolinea,Exp con,LinkedList<Instruccion> sig)
    {
        Condicion=con;
        SiguienteInstruccion=sig;
        NoLinea=Nolinea;
        TablaDeSimbolos.InsertBreakPoint(NoLinea);
        TablaDeSimbolos.DisableBreakPoint(NoLinea);
    }
    
    public ElseIF(Exp con,LinkedList<Instruccion> sig)
    {
        Condicion=con;
        SiguienteInstruccion=sig;
    }
    

    
    public boolean CargarTS()
    {
        boolean res=true;
        
        if(this.Switch)
        {
            ExpBinaria Temp=new ExpBinaria(this.ExpSwitch,this.Condicion,"==");
            Condicion=Temp;
        }
        Condicion.tabla=tabla;
        
        Condicion.Metodo=Metodo;
        Condicion.SimboloMetodo=SimboloMetodo;
        
        Condicion.Clase=Clase;
        
        //Condicion.Clase1=Clase1;
        
        res=Condicion.CargarTS();
        
        
        TablaDeSimbolos nTabla=new TablaDeSimbolos();
        nTabla.tablaArriba=tabla;
        tabla=nTabla;
        int i;
        
        if(SiguienteInstruccion!=null)
        {
            for(i=0;i<SiguienteInstruccion.size()&&res;i++)
            {
                SiguienteInstruccion.get(i).tabla=tabla;
                
                
                
                SiguienteInstruccion.get(i).Metodo=Metodo;
                SiguienteInstruccion.get(i).SimboloMetodo=SimboloMetodo;
                
                SiguienteInstruccion.get(i).Clase=Clase;
                
                //SiguienteInstruccion.get(i).Clase1=this.Clase1;
                
                res=SiguienteInstruccion.get(i).CargarTS();
            }
        }
        return res;
    }

    public boolean Ejecutar()
    {
        boolean res=true;
        boolean bandera=true;
         if(TablaDeSimbolos.Debug)
        {
            if(Debug(tabla))
            {
                
            }
            else
            {
                Return=true;
                return true;
            }
        }
        if(bandera)
        {
        if(Condicion!=null)
        {
            res=Condicion.Ejecutar();
        }
        if((Integer.parseInt(Condicion.Valor)>=1||Do)&&res)
        {
            int i;
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
        }
        return res;
    }
    
    public String Generar3Direcciones()
    {
        String ret=""; 
        int i;
        String ins1,ins2="",ins3,ins4="",ins5;
        String instruccionA="";
        String EtiquetaF=this.LabelFalse;
        String EtiquetaV=this.LabelTrue;
        
        this.Condicion.LabelFalse=EtiquetaF;
        this.Condicion.LabelTrue=EtiquetaV;
        
        ins1=this.Condicion.Generar3Direcciones();
        
        if(this.Condicion.tipos.Buscar("boolean"))
        {
        ins2=EtiquetaV+":\n";
        
        for(i=0;i<this.SiguienteInstruccion.size();i++)
        {
            instruccionA=instruccionA+this.SiguienteInstruccion.get(i).Generar3Direcciones();        
        }
        ins4="goto "+this.LabelSig+"\n";
        }
        else
        {
                Errores.InsertarError(this.NoLinea,"la condición tiene que booleano");       
        }
        ret=ins1+ins2+instruccionA+ins4;
        return ret;    
    }
}
