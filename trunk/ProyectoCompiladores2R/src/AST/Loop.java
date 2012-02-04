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
public class Loop extends Instruccion {
    public boolean FOR;
    public boolean WHILE;
    public boolean doWhile;
    

    public Asignacion iniFor;
    public Asignacion incFor;
    
    public Exp Condicion;

    public Loop(int Nolinea,Asignacion iF,Exp con,Asignacion inc,LinkedList<Instruccion> sig) //For con Linea
    {
        iniFor=iF;
        Condicion=con;
        incFor=inc;
        FOR=true;
        SiguienteInstruccion=sig;
        NoLinea=Nolinea;
        TablaDeSimbolos.InsertBreakPoint(NoLinea);
        TablaDeSimbolos.DisableBreakPoint(NoLinea);
    }
    
    public Loop(Asignacion iF,Exp con,Asignacion inc,LinkedList<Instruccion> sig)  //For
    {
        iniFor=iF;
        Condicion=con;
        incFor=inc;
        FOR=true;
        SiguienteInstruccion=sig;
    }

    public Loop(Exp con,LinkedList<Instruccion> sig)//While
    {
        Condicion=con;
        WHILE=true;
        SiguienteInstruccion=sig;
    }
    
    public Loop(int line,Exp con,LinkedList<Instruccion>sig,boolean Do)
    {
        this.NoLinea=line;
        Condicion=con;
        doWhile=true;
        SiguienteInstruccion=sig;
    }
    
    public Loop(int Nolinea, Exp con,LinkedList<Instruccion> sig)//While con Linea
    {
        Condicion=con;
        WHILE=true;
        SiguienteInstruccion=sig;
        NoLinea=Nolinea;
        TablaDeSimbolos.InsertBreakPoint(NoLinea);
        TablaDeSimbolos.DisableBreakPoint(NoLinea);
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
                bandera=false;
               
                return true;
            }
        }
        if(bandera)
        {
        if(FOR&&iniFor!=null&&Condicion!=null)
        {
            res=iniFor.Ejecutar();
            res=Condicion.Ejecutar();
            int i;
            if(Condicion.tipos.Buscar("int")||Condicion.tipos.Buscar("float"))
            {
            while(res&&Float.parseFloat(Condicion.Valor)>=1&&bandera)
            {
                if(SiguienteInstruccion!=null)
                {
                    for(i=0;i<SiguienteInstruccion.size()&&res&&bandera;i++)
                    {
                        if(SiguienteInstruccion!=null)
                        {
                            res=SiguienteInstruccion.get(i).Ejecutar();
                            if(this.SiguienteInstruccion.get(i).Break){bandera=false;}
                            if(this.SiguienteInstruccion.get(i).Return){bandera=false; Return=true;}
                        }
                    }
                    if(incFor!=null)
                    {
                    res=incFor.Ejecutar();
                    }
                    res=Condicion.Ejecutar();
                }
            }
            }
            else
            {
                res=false;
                 Errores.InsertarError(this.NoLinea, "la condicion tiene que ser de tipo booleano y no  " + this.Condicion.tipos.Tipos());
            }
        }
        if(WHILE&&Condicion!=null)
        {
            res=Condicion.Ejecutar();
            int i;
            if(Condicion.tipos.Buscar("int")||Condicion.tipos.Buscar("float"))
            {
            while(res&&Float.parseFloat(Condicion.Valor)>=1&&bandera)
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
                res=Condicion.Ejecutar();
            }      
            }
            else
            {
                res=false;
                 Errores.InsertarError(this.NoLinea, "la condicion tiene que ser de tipo booleano y no  " + this.Condicion.tipos.Tipos());
            }
        }
        }
        return res;
    }

    public boolean CargarTS()
    {
        boolean res=true;
        TablaDeSimbolos nTabla=new TablaDeSimbolos();
        nTabla.tablaArriba=tabla;
        tabla=nTabla;

        if(Condicion!=null)
        {
            Condicion.Metodo=Metodo;
            Condicion.tabla=tabla;
            
            Condicion.Clase=Clase;
            
            Condicion.SimboloMetodo=SimboloMetodo;
            Condicion.CargarTS();
        }
            if(FOR)
            {
                iniFor.SimboloMetodo=SimboloMetodo;
                iniFor.tabla=tabla;
                
                iniFor.Clase=Clase;
                
                iniFor.Metodo=Metodo;
                
                res=iniFor.CargarTS();
                if(res&&incFor!=null)
                {
                incFor.SimboloMetodo=SimboloMetodo;
                incFor.tabla=tabla;
                incFor.Metodo=Metodo;
                incFor.Clase=Clase;
                
                res=incFor.CargarTS();
                }
            }
            if(WHILE){}
        int i;
        if(res&&SiguienteInstruccion!=null)
        {
            for(i=0;i<SiguienteInstruccion.size()&&res;i++)
            {
                if(SiguienteInstruccion.get(i)!=null)
                {
                    SiguienteInstruccion.get(i).tabla=tabla;
                    
                    SiguienteInstruccion.get(i).Metodo=Metodo;
                    SiguienteInstruccion.get(i).SimboloMetodo=SimboloMetodo;
                    
                    SiguienteInstruccion.get(i).Clase=Clase;
                    
                    SiguienteInstruccion.get(i).Clase1=Clase1;
                    
                    res=SiguienteInstruccion.get(i).CargarTS();
                }
            }
        }
        return res;
    }
    
    public String Generar3Direcciones()
    {
     String ret=""; 
     ret=Generar3DireccionesWhile();
     return ret;
    }
    
    
    public String Generar3DireccionesFor()
    {
        String ret="";
        return ret;
    }
    
    public String Generar3DireccionesWhile()
    {
        String ret="";
        String nEtiquetaC;
        String nEtiquetaV;
        String nEtiquetaF;
        String nEtiquetaS;
        String ins1,ins2,ins3,ins4,ins5,ins6,ins7;
        String instruccionA="";
        int i;
        if(this.doWhile)
        {
            nEtiquetaF=TablaDeSimbolos.GenerarEtiqueta();
            ins1=nEtiquetaF+":\n";
            for(i=0;i<this.SiguienteInstruccion.size();i++)
            {
                instruccionA=instruccionA+this.SiguienteInstruccion.get(i).Generar3Direcciones();
            }
            Condicion.LabelFalse=nEtiquetaF;
            Condicion.LabelTrue=TablaDeSimbolos.GenerarEtiqueta();
            ins2=Condicion.Generar3Direcciones();
            if(Condicion.tipos.Buscar("boolean"))
            {
                ins3=Condicion.LabelTrue+":\n";
                ret=ins1+instruccionA+ins2+ins3;
            }
            else
            {
                  Errores.InsertarError(this.NoLinea,"la  condición tiene que ser booleana");
            }
        }
        if(this.WHILE)
        {
            nEtiquetaC=TablaDeSimbolos.GenerarEtiqueta();
            nEtiquetaV=TablaDeSimbolos.GenerarEtiqueta();
            nEtiquetaF=TablaDeSimbolos.GenerarEtiqueta();
            
            ins1=nEtiquetaC+":\n";
            Condicion.LabelFalse=nEtiquetaF;
            Condicion.LabelTrue=nEtiquetaV;
            
            
            ins2=this.Condicion.Generar3Direcciones();
            
            if(this.Condicion.tipos.Buscar("boolean"))
            {
            for(i=0;i<this.SiguienteInstruccion.size();i++)
            {
            
                this.SiguienteInstruccion.get(i).LabelSig=nEtiquetaF;
                instruccionA=instruccionA+this.SiguienteInstruccion.get(i).Generar3Direcciones();
            }
            
            ins3="goto "+nEtiquetaC+";\n";
            ins4=nEtiquetaF+":\n";
            
            ins5="\n"+ins1+ins2+Condicion.LabelTrue+": \n"+instruccionA+ins3+ins4;
            ret=ins5;
            }
        }
        if(this.FOR)
        {
            
                ins1="";
                ins4="";
                nEtiquetaC=TablaDeSimbolos.GenerarEtiqueta();
                nEtiquetaF=TablaDeSimbolos.GenerarEtiqueta();
                nEtiquetaV=TablaDeSimbolos.GenerarEtiqueta();
                if(this.iniFor!=null)
                {
                    ins1=iniFor.Generar3Direcciones();
                }
                ins2=nEtiquetaC+":\n";
                
                this.Condicion.LabelFalse=nEtiquetaF;
                this.Condicion.LabelTrue=nEtiquetaV;
                
                ins3=this.Condicion.Generar3Direcciones();
                
                if(this.Condicion.tipos.Buscar("boolean"))
                {
                for(i=0;i<this.SiguienteInstruccion.size();i++)
                {
                    this.SiguienteInstruccion.get(i).LabelSig=nEtiquetaF;
                    instruccionA=instruccionA+this.SiguienteInstruccion.get(i).Generar3Direcciones();                
                }
                
                if(this.incFor!=null)
                {
                    ins4=this.incFor.Generar3Direcciones();                
                }
                ins5="goto "+nEtiquetaC+";\n";
                
                ins6=nEtiquetaF+": \n";
                ins7="\n"+ins1+ins2+ins3+"\n"+nEtiquetaV+":\n"+instruccionA+"\n"+ins4+ins5+"\n"+ins6;        
                ret=ins7;
                }
                
        }
        
        return ret;
    }
    
}


