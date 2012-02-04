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
public class IF extends Instruccion {

    public Exp Condicion;
    public LinkedList<ElseIF> elseifs;
    public Else else1;
    
    public Exp ExpSwitch;    
    public boolean Switch;
    
    public String LabelTrue;
    public String LabelFalse;
    public String LabelSig;
    
    

    public TablaDeSimbolos tablaArriba;
    
    public IF(){}
    public IF(Exp condicion,LinkedList<Instruccion>Si,LinkedList<ElseIF> eifs,Else els)
    {
        Condicion=condicion;
        SiguienteInstruccion=Si;
        elseifs=eifs;
        else1=els;
    }
    
    public IF(int Nolinea,Exp Sw,LinkedList<Instruccion>Si,LinkedList<ElseIF> eifs,Else els,boolean sw)
    {
        if(sw=true)
        {
            this.NoLinea=Nolinea;
            this.ExpSwitch=Sw;
            this.SiguienteInstruccion=Si;
            this.elseifs=eifs;
            this.else1=els;
            this.Switch=true;
        }
        else
        {
            Condicion=Sw;
            SiguienteInstruccion=Si;
            elseifs=eifs;
            else1=els;
            NoLinea=Nolinea;
            this.Switch=false;
        }
    }
    
    public IF(int Nolinea,Exp condicion,LinkedList<Instruccion>Si,LinkedList<ElseIF> eifs,Else els)
    {
        Condicion=condicion;
        SiguienteInstruccion=Si;
        elseifs=eifs;
        else1=els;
        NoLinea=Nolinea;
        TablaDeSimbolos.InsertBreakPoint(NoLinea);
        TablaDeSimbolos.DisableBreakPoint(NoLinea);
    }
    

    
    public boolean  Ejecutar()
    {
        boolean ret=false;
        boolean bandera=true;
         if(TablaDeSimbolos.Debug)
        {
            if(Debug(tabla))
            {
             bandera=true;   
            }
            else
            {
                Return=true;
                bandera=false;
                return true;
            }
        }
        
        if(bandera){
        ret=Condicion.Ejecutar();
        int i;
        boolean Ejecuto=false;

        
        if(Float.parseFloat(Condicion.Valor)>=1)
        {
            if(SiguienteInstruccion!=null){
            for(i=0;i<this.SiguienteInstruccion.size()&&ret&&bandera;i++)
            {
                if(this.SiguienteInstruccion.get(i)!=null)
                {
                    ret=this.SiguienteInstruccion.get(i).Ejecutar();
                    if(this.SiguienteInstruccion.get(i).Break){bandera=false;}
                    if(this.SiguienteInstruccion.get(i).Return){bandera=false; Return=true;}
                    
                }
            }
            
            Ejecuto=true;
            }
            
        }
        if(elseifs!=null)
        {   
            for(i=0;i<this.elseifs.size()&&ret&&bandera;i++)
            {
                if(elseifs.get(i)!=null)
                {
                    ret=elseifs.get(i).Ejecutar();
                    if(Integer.parseInt(elseifs.get(i).Condicion.Valor)>=1)//Verifica si ya ejecuto para no ejecutar el Else
                    {Ejecuto=true;}
                    if(elseifs.get(i).Break){bandera=false;}
                    if(elseifs.get(i).Return){bandera=false; Return=true;}
                }
            }
        }
        if(!Ejecuto)//Else si no ejecuto nada
        {
           if(else1!=null){
           ret=else1.Ejecutar();
           }
        }
        
        }
        
        return ret;
    }
    
    

    public boolean CargarTS()
    {
        
        boolean res=true;
        TablaDeSimbolos nTabla=new TablaDeSimbolos();

        nTabla.tablaArriba=tabla;
        tabla=nTabla;
        int i;
        if(this.Switch)
        {
            if(this.elseifs!=null)
            {
              for(i=0;i<this.elseifs.size()&&res;i++)
              {
                  this.elseifs.get(i).tabla=tabla.tablaArriba;
                  
                  this.elseifs.get(i).Metodo=this.Metodo;
                  this.elseifs.get(i).SimboloMetodo=this.SimboloMetodo;
                          
                  this.elseifs.get(i).Clase=Clase;
                  //this.elseifs.get(i).Clase1=Clase1;
                  this.elseifs.get(i).Switch=true;
                  this.elseifs.get(i).ExpSwitch=this.ExpSwitch;
                  
                  res=this.elseifs.get(i).CargarTS();
              
              }
              if(this.else1!=null)
              {
                  this.else1.tabla=tabla.tablaArriba;
                  this.else1.SimboloMetodo=this.SimboloMetodo;
                  this.else1.Metodo=this.Metodo;
                  
                  this.else1.Clase=this.Clase;                  
                  //this.else1.Clase1=Clase1;
                  
                  res=this.else1.CargarTS();
              
              }
                
            }
        
        }
        if(!this.Switch)
        {
        if(Condicion!=null)
        {
                Condicion.Metodo=Metodo;
                Condicion.tabla=tabla;
                
                Condicion.Clase=Clase;
                
                Condicion.CargarTS();
        }
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
                    
                    //SiguienteInstruccion.get(i).Clase1=Clase1;
                    
                    res=SiguienteInstruccion.get(i).CargarTS();
                }
            }
        }
        if(elseifs!=null&&res)
        {
            for(i=0;i<elseifs.size()&&res;i++)
            {
                if(elseifs.get(i)!=null)
                {
                    elseifs.get(i).tabla=tabla.tablaArriba;
                    
                    elseifs.get(i).Metodo=Metodo;
                    elseifs.get(i).SimboloMetodo=SimboloMetodo;
                    
                    elseifs.get(i).Clase=Clase;
                    
                    //elseifs.get(i).Clase1=Clase1;
                    
                    res=elseifs.get(i).CargarTS();

                }
            }
        }
        if(else1!=null&&res)
        {
                else1.tabla=tabla.tablaArriba;
                
                else1.Metodo=Metodo;
                else1.SimboloMetodo=SimboloMetodo;
                
                else1.Clase=Clase;
                
                //else1.Clase1=Clase1;
                
                res=else1.CargarTS();
        }
        }
        return res;
    }
    
    public String Generar3Direcciones()
    {
     String ret=""; 
     String CCondicion;
     String CSInstrucciones="";
     String instrucionElseIF="";
     String instruccionesElseIF="";
     String instruccionElseIFFS="";
     String ins1,ins2,ins3,ins0;
     String Celse;
     int i;

     if(this.Switch)
     {
         String nEtiquetaS=TablaDeSimbolos.GenerarEtiqueta();
         for(i=0;i<this.elseifs.size();i++)
         {
             if(this.elseifs.get(i)!=null)
             {
                 this.elseifs.get(i).LabelSig=nEtiquetaS;
                 
             } 
         }
     }
     else
     {
         //hay ye erevisar el tipo de las condiciones  tiene que ser boolean 
         if(this.elseifs!=null)
         {
          String nEtiquetaV=TablaDeSimbolos.GenerarEtiqueta();
          String nEtiquetaF=TablaDeSimbolos.GenerarEtiqueta();
          String nEtiquetaS=TablaDeSimbolos.GenerarEtiqueta();
          String nEtiquetaVS=TablaDeSimbolos.GenerarEtiqueta();
          String nEtiquetaFS=TablaDeSimbolos.GenerarEtiqueta();
          
          Condicion.LabelFalse=nEtiquetaF;
          Condicion.LabelTrue=nEtiquetaV;
          Condicion.LabelSig=nEtiquetaS;
          CCondicion=Condicion.Generar3Direcciones();
          if(Condicion.tipos.Buscar("boolean"))
          {
          //Generar la instruccion para el if y goto nEtiquetaS
          for(i=0;i<this.SiguienteInstruccion.size();i++)
          {
              
              CSInstrucciones=CSInstrucciones+this.SiguienteInstruccion.get(i).Generar3Direcciones();
          }          
          CSInstrucciones=CSInstrucciones+"goto "+nEtiquetaS+"\n";
          
          for(i=0;i<this.elseifs.size();i++)
          {
              if(this.elseifs.get(i)!=null)
              {
                  this.elseifs.get(i).LabelSig=nEtiquetaS;
                  this.elseifs.get(i).LabelTrue=nEtiquetaVS;
                  this.elseifs.get(i).LabelFalse=nEtiquetaFS;
                  
                  // Generar instruccion + nEtiquetaFS 
                  instrucionElseIF=this.elseifs.get(i).Generar3Direcciones();
                  instruccionElseIFFS=nEtiquetaFS+":\n";
                  instruccionesElseIF=instruccionesElseIF+instrucionElseIF+instruccionElseIFFS;
                  
                  
                 if(i<this.elseifs.size()-1)
                 {
                 nEtiquetaVS=TablaDeSimbolos.GenerarEtiqueta();
                 nEtiquetaFS=TablaDeSimbolos.GenerarEtiqueta();                  
                 }
              }
          } 
            //Genera la isntruccion para el else si existe(y si no colocar la etiqueta nEtiquetaFS tambien) y para nEtiquetaS
         if(this.else1!=null)
         {
             ins1=else1.Generar3Direcciones();
             ins2=nEtiquetaS+":\n";             
             ins3=CCondicion+nEtiquetaV+":\n"+CSInstrucciones+instruccionElseIFFS+ins2;
                //Colocar la etiqueta nEtiquetaFS pero creo que hay un error esta generando una etiqueta extra....
         }
         else
         {
                ins2=nEtiquetaFS+":\n"+nEtiquetaS+":\n";
                ins3=CCondicion+nEtiquetaV+":\n"+CSInstrucciones+instruccionElseIFFS+ins2;
                ret=ins3;
         }         
          
         }
          else{Errores.InsertarError(this.NoLinea,"ya expreción tiene que ser booleana ");}
         }
         else
         {
             String nEtiquetaF=TablaDeSimbolos.GenerarEtiqueta();
             String nEtiquetaV=TablaDeSimbolos.GenerarEtiqueta();
             String nEtiquetaS=TablaDeSimbolos.GenerarEtiqueta();
             
             Condicion.LabelFalse=nEtiquetaF;
             Condicion.LabelTrue=nEtiquetaV;
             
             CCondicion=Condicion.Generar3Direcciones();
             if(this.Condicion.tipos.Buscar("boolean"))
             {
                 
             if(this.SiguienteInstruccion!=null)
             {
                 for(i=0;i<this.SiguienteInstruccion.size();i++)
                 {
                     if(this.SiguienteInstruccion.get(i)!=null)
                     {
                         CSInstrucciones=CSInstrucciones+this.SiguienteInstruccion.get(i).Generar3Direcciones();
                     }
                 }             
             }
             if(this.else1==null)
             {
                ins1="\n "+CCondicion+"\n "+nEtiquetaV+":\n"+CSInstrucciones+"\n"+nEtiquetaF+":\n";
                ret=ins1;
             }
             else
             {
                Celse=else1.Generar3Direcciones();
                ins1="\n"+CCondicion+"\n "+nEtiquetaV+":\n"+CSInstrucciones+"\n goto "+nEtiquetaS+"; \n"+nEtiquetaF+":\n"+Celse+"\n"+nEtiquetaS+":\n";
                ret=ins1;
             }
             
             }
         }
     }         
     return ret;
    }

}
