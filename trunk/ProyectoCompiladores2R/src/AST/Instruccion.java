/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AST;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eddy
 */
public class Instruccion implements Serializable {
    public LinkedList<Instruccion> SiguienteInstruccion;
    public String Metodo;
    
    public String Clase="";
    public Simbolo Clase1;
    
    public Simbolo SimboloMetodo;
    
    public ConjuntoTipos tipos=new ConjuntoTipos();
    public TablaDeSimbolos tabla;
    
    public TablaDeSimbolos tablaBP; //Tabla de Breakpoints
    
    public String LabelSig;
    public String LabelTrue;
    public String LabelFalse;
    
    public int NoLinea;

    public boolean Return;
    public boolean Break;
    
    public String TraduccionAc;
   
    public Instruccion()
    {
        SiguienteInstruccion=new  LinkedList();
    }
    
   public boolean Debug(TablaDeSimbolos Actual)
    {
        boolean ret=true;
        int res=0;
        boolean bandera=true;
        boolean bandear2=true;
        String entrada="";
        if(TablaDeSimbolos.BuscarBreakPoint(NoLinea))
        {
            
            
            
//            Debug.TS=Actual;
            System.out.print("BreakPoint in Linea:"+NoLinea);
  //          ret=Debug.ComandosDespuesDebug(NoLinea);
            
            //if(!ret)
            //System.out.println("LLego acá "+Actual.BuscarV("a").Size+"---");
        }
            
        
        
        return ret;
    }
   
   public boolean Ejecutar(){
       boolean ret=true;
       boolean bandera=true;
       int i=0;
       for(i=0;i<SiguienteInstruccion.size()&&ret&&bandera;i++)
       {
           if(SiguienteInstruccion.get(i)!=null)
           {
           ret=SiguienteInstruccion.get(i).Ejecutar();
           if(this.SiguienteInstruccion.get(i).Return){bandera=false;}
           
           }
       }
       return ret;
   }
   
   public boolean CargarTS(){
                                boolean res=true;
                                int i;
                                
                                if(SiguienteInstruccion==null){res=false;}
                                if(SiguienteInstruccion!=null)
                                {
                                    for(i=0;i<SiguienteInstruccion.size()&&res;i++)
                                    {
                                        SiguienteInstruccion.get(i).tabla=tabla;
                                        res=SiguienteInstruccion.get(i).CargarTS();
                                    }
                                }
                                return res;
                             }
   
   public String Generar3Direcciones()
   {
    String ret=""; 
    String a="";
    int i;
    if(this.SiguienteInstruccion!=null)
    {
        for(i=0;i<this.SiguienteInstruccion.size();i++)
        {
            a=this.SiguienteInstruccion.get(i).Generar3Direcciones();
            ret=ret+a;
        }
    }
    return ret;
   }
   
   
   public String EncabezadoAG="";
   public String CuerpoAG="";
   public void Recorer()
   {
        //String ret="";
        String a="";
        String b="";
       int i;
       if(this.SiguienteInstruccion!=null)
       {
            for(i=0;i<this.SiguienteInstruccion.size();i++)
            {
                this.SiguienteInstruccion.get(i).Recorer();
                
                
                a=this.SiguienteInstruccion.get(i).getClass().getCanonicalName();
                
                EncabezadoAG=EncabezadoAG+"{ node[label=\""+a+"\"]\""+this+"\n";
                
                b=this.SiguienteInstruccion.get(i).CuerpoAG;
                CuerpoAG=b+"\""+this+"\" -> \""+this.SiguienteInstruccion.get(i)+"\";\n";
                
                if(this.getClass().getCanonicalName().equals("Clase"))
                {
                 
                }
                
            }
            
       }
       
   }
   
   public void CrearA()
   {      
       this.Recorer();
       String dot = "digraph G {\n\n node [fillcolor=\"#D7DFE8\", shape=circle, style=\"filled\"];\n"+this.EncabezadoAG+this.CuerpoAG+"\n}\n";
       try {
            
            File f=new File("/home/eddytrex/Desktop/proyecto2java.dot");
            f.delete();
            FileWriter fr=new FileWriter(f);
            BufferedWriter bw=new BufferedWriter(fr);
            PrintWriter out = new PrintWriter(bw);
            out.println(dot);
            out.close();
            bw.close();
            fr.close();
        }
        catch(Exception e) {
            System.out.println("Error de escritura: "+e.toString());
        } 
   
   }

}
