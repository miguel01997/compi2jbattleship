/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AST;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author Eddy
 */
public class TablaDeSimbolos implements Serializable {
    Hashtable <String,Simbolo> tabla;
    
    TablaDeSimbolos tablaArriba;
    //TablaDeSimbolos tablaAbajo;
    
    static TablaDeSimbolos tablaTemYLabels;
    
    public static TablaDeSimbolos TBreakPoints;
    
    boolean Metodo=false;
    static int posSigG=0;
    
    static int Temporal=0;
    static int Etiqueta=0;
    
    public static boolean Debug;
    public static int NoBreakPoint;
    
    public TablaDeSimbolos()
    {
        tabla=new Hashtable();
        tablaArriba=null;
    }
    
    //Metodos para Insertar y Buscar BreakPoints  por linea para el Debug
    
    public static boolean InsertBreakPoint(int Linea)
    {
        boolean res=true;
        Simbolo s=null,sre=null;
        
        s=new Simbolo();
       
        String NombreBreak="BreakPoint_"+Integer.toString(Linea);
        s.Nombre=NombreBreak;
        s.BreakPoint=true;
        s.ActivoBP=true;
        
        if(TBreakPoints==null){TBreakPoints=new TablaDeSimbolos();}
        sre=TBreakPoints.tabla.put(NombreBreak, s);
        
        if(sre==null){res=false;} //No inserto el BreakPoint
        return res;
    }
    
    public static boolean BuscarBreakPoint(int Linea)
    {
        boolean res=false;
        Simbolo s=null;
        String NombreBreak="BreakPoint_"+Integer.toString(Linea);
        if(TBreakPoints!=null){
                                 s=TBreakPoints.tabla.get(NombreBreak);
                                 if(s!=null){if(s.ActivoBP){res=true;}
                                 //else{s.ActivoBP=true;}
                                 }
                               }
        return res;
    }
    
    public static boolean DisableBreaksPointsToLinea(int Linea)
    {
        boolean res=true;
        int i=0;
        Simbolo s=null;
        String NombreBreak="BreakPoint_";
        while(i<Linea)
        {
            NombreBreak="BreakPoint_"+Integer.toString(i);
            s=TBreakPoints.tabla.get(NombreBreak);
            if(s!=null){s.ActivoBP=false;}
        i++;
        }
        
        return res;
    }
    
    public static boolean DisableBreakPoint(int linea)
    {
        boolean res=false;
        int i=0;
        Simbolo s=null;
        String NombreBreak="BreakPoint_"+linea;
        s=TBreakPoints.tabla.get(NombreBreak);
        if(s!=null){if(s.BreakPoint){s.ActivoBP=false;res=true;}}
        return res;
    }
        
    public static boolean EnableBreakPoint(int linea)
    {
        boolean res=false;
        Simbolo s=null;
        String NombreBreak="BreakPoint_"+linea;
        s=TBreakPoints.tabla.get(NombreBreak);
        if(s!=null){if(s.BreakPoint){s.ActivoBP=true;res=true;}}
        return res;
    }
    
    public static boolean EnableBreakPoints()
    {   
        boolean res=true;
        int i=0;
        while(i<TBreakPoints.tabla.size())
        {
            ((Simbolo)TBreakPoints.tabla.values().toArray()[i]).ActivoBP=true;
            i++;
        }
        return res;
    }
    
    ///////////////////////////////////////////////////////


    public Simbolo BuscarV(String clave)
    {
        Simbolo res=null;
        res=tabla.get(clave);
        if(res==null){if(tablaArriba!=null)res=tablaArriba.BuscarV(clave);}
        if(res!=null)
        {
            if(!res.Variable){res=null;} 
        }
        return res;
    }

    public boolean InsertarV(String Nombre, String Tipo, String Metodo,LinkedList<Integer> Dim)
    {
        boolean res=false;
        Simbolo s=null;
        s=BuscarV(Nombre);
        if(s==null)
        {
            s=new Simbolo();
            s.Nombre=Nombre;
            s.Tipo=Tipo;
            s.Variable=true;
            s.Padre=Metodo;
            
            s.Dimenciones=Dim;

            int i;
            int size=s.TiposPrimitivos(Tipo);//tamaños del tipo de dato??
            if(Dim!=null)
            {
              for(i=0;i<Dim.size();i++)
              {
                 if(Dim.get(i)!=null)
                 {
                  size=size*Dim.get(i);
                }

              }
           }

           s.PosRel=MSizeMetodo(Metodo,size);
           s.Size=size;

            tabla.put(Nombre, s);
            res=true;
        }
        else{

                Errores.InsertarError(0,"ya declaro la variable");
                Errores.Cargar=true;
        }
        return res;
    }

    public Simbolo BuscarVG(String clave)
    {
        Simbolo res=null;
        res=tabla.get(clave);
        if(res==null){if(tablaArriba!=null)res=tablaArriba.BuscarVG(clave);}
        if(res!=null)
        {
            if(!res.Global){res=null;}
        }
        return res;
    }
    
    public boolean InsertarVG(String Nombre,String Tipo,LinkedList<Integer> Dim)
    {
        boolean res=false;
        Simbolo s=null;
        s=BuscarVG(Nombre);
        if(s==null)
        {
            s=new Simbolo();
            s.Nombre=Nombre;
            s.Tipo=Tipo;
            s.Dimenciones=Dim;
            s.Global=true;

            

            int i;
            int size=s.TiposPrimitivos(Tipo);//tamaños del tipo de dato??
            if(Dim!=null)
            {
                for(i=0;i<Dim.size();i++)
                {
                    if(Dim.get(i)!=null)
                    {
                    size=size*Dim.get(i);
                    }
                }
            }
            s.PosGlo=posSigG;
            posSigG=posSigG+size;
            
            s.Size=size;

            tabla.put(Nombre, s);
        }
        return res;
    }

    public Simbolo BuscarM(String clave)
    {
        Simbolo res=null;        
        res=tabla.get(clave);
        if(res==null)
        {
            if(tablaArriba!=null){ res=tablaArriba.BuscarM(clave);}
            
        }
        if(res!=null)
        {
            if(!res.Metodo){res=null;}
        }
        return res;
    }
    
    //Busca Metodo en una clase y en su padre si tiene;
    public Simbolo BuscarM(String clave,String clase)
    {
        Simbolo res=null;
        Simbolo cl;
        res=tabla.get(clave);
        if(res==null)
        {
            if(tablaArriba!=null)
            {res=tablaArriba.BuscarM(clave, clase);}
            else
            {
              cl=BuscarC(clase);
              String clave2=cl.Padre+ObtenerNombreMiembroClase(clave);
              if(!cl.Padre.equals("")){res=BuscarM(clave,cl.Padre);}
            }
        }
        if(res!=null)
        {
            if(!res.Metodo){res=null;}
            
        }
        return res;
    }
    
    public String ObtenerNombreMiembroClase(String clave)
    {
       String res="";
       res=clave.split("_", 2)[1];
       return res;
    }
    
    public void InsertarSubArbolM(String Clave,Instruccion SubArbol)
    {
        Simbolo res=null;
        res=tabla.get(Clave);
        if(res==null){if(tablaArriba!=null){tablaArriba.InsertarSubArbolM(Clave, SubArbol);}}
        if(res!=null)
        {
            if(res.Metodo){
                res.SubArbol=SubArbol;
                tabla.put(Clave, res);
            }
        }
                
    }

    public int MSizeMetodo(String Metodo,int size)
    {
        int posF=0;
        Simbolo s;
        s=tabla.get(Metodo);
        if(s==null){if(tablaArriba!=null){posF=tablaArriba.MSizeMetodo(Metodo,size);}}
        if(s!=null)
        {
            if(s.Metodo)
            {
                posF=s.PosRel;
                s.Size=s.Size+size;
                s.PosRel=s.PosRel+size;
                tabla.put(Metodo, s);
                
            }
        }
        return posF;
    }

    public boolean InsertarMG(String Nombre,String tipo,LinkedList<Simbolo> parametros1)
    {
        boolean res=false;
        String  cNombre="";
        Simbolo This=new Simbolo();
        Simbolo Return=new Simbolo();
        Simbolo metodoGlobal=new Simbolo();
        int i;
        
        Return.Nombre="return";
        Return.Tipo="int";
        This.Size=2;
        This.Nombre="this";
        This.Tipo="int";        
        This.Size=2;
        parametros1.addFirst(Return);
        parametros1.add(This);
        
        metodoGlobal.Size=0;
        metodoGlobal.PosRel=0;
        for(i=0;i<parametros1.size();i++)
        {
            //cNombre=cNombre+"_"+parametros1.get(i).Tipo;
            metodoGlobal.Size=metodoGlobal.PosRel+ConjuntoTipos.SizePrimitivos(tipo);
            parametros1.get(i).PosRel=metodoGlobal.PosRel;
            metodoGlobal.PosRel=metodoGlobal.PosRel+metodoGlobal.Size;
        }
        for(i=1;i<parametros1.size()-1;i++)
        {
            cNombre=cNombre+"_"+parametros1.get(i).Tipo;
        }
        metodoGlobal.Nombre="_"+Nombre+"_"+cNombre;
        metodoGlobal.Tipo=tipo;
        metodoGlobal.Metodo=true;
        metodoGlobal.Argumentos=parametros1;
        
        
        Simbolo bs=BuscarM(metodoGlobal.Nombre);
        if(bs==null)
        {
            this.tabla.put(metodoGlobal.Nombre, metodoGlobal);
        }
        
        
        
        ///falta implementar tamaño de los parametros,+ this + return
        return res;    
    }
    public boolean InsertarM(String Nombre,String Clase,String Tipo, String Modificador,LinkedList<String> Parametros,LinkedList<Simbolo> parametros1)
    {
        boolean res=false;
        boolean claseres=false;
        Simbolo temp1=null;
        Simbolo s=null;
        if(Clase!=null)
        {
            if(!Clase.equals(""))
            {
            temp1=BuscarC(Clase);
            claseres=true;
            }
        }
        s=BuscarM(Nombre);
        if(s==null)
        {
            s=new Simbolo();
            if(Modificador!=null)
            {
            if(Modificador.equals("private")){s.privado=true;}
            if(Modificador.equals("public")){s.publico=true;}
            if(Modificador.equals("protected")){s.protegido=true;}
            }
            if(claseres)
            {
                s.Nombre=Nombre;
                s.Padre=Clase;
                s.Tipo=Tipo;
                s.Parametros=Parametros;
                s.Argumentos=parametros1;
                s.Metodo=true;
                tabla.put(Nombre, s);
            }
            else{
                s.Nombre=Nombre;
                s.Tipo=Tipo;
                s.Parametros=Parametros;
                s.Metodo=true;
                tabla.put(Nombre, s);
            }
        }

        return res;
    }


    public Simbolo BuscarA(String clave,String clase)
    {
        Simbolo res=null;
        Simbolo cl;
        res=tabla.get(clave);
        
        if(res==null){            
            if(tablaArriba!=null){ res=tablaArriba.BuscarA(clave,clase);}
            else
            {
                cl=BuscarC(clase);//Busca el Simbolo de la clase
                if(cl!=null)
                {
                String clave2=cl.Padre+"_"+ObtenerNombreMiembroClase(clave);
                if(cl!=null&&!cl.Padre.equals("")){
                    res=BuscarA(clave2,cl.Padre);
                }
                }
                
            }
        }
        
        if(res!=null)
        {
            if(!res.Atributo&&!res.Padre.equals(clase)){res=null;}
        }
        return res;
    }

    public Simbolo BuscarC(String clave)
    {
        Simbolo res=null;

        res=tabla.get(clave);
        if(res==null){if(tablaArriba!=null){res=tablaArriba.BuscarC(clave);}}
        if(res!=null)
        {
            if(!res.Clase){res=null;}
        }
        return res;
    }

    public boolean InsertarC(String Nombre, String superc,String Modificador)
    {
        boolean res=false;
        boolean clasep=false;
        Simbolo temp=tabla.get(Nombre);
        Simbolo temp2=null;
        Simbolo s;
        if(!superc.equals("")){
            clasep=true;
        temp2=tabla.get(superc);}
        if(temp==null)
        {
            s=new Simbolo();
            if(Modificador.equals("private")){s.privado=true;}
            if(Modificador.equals("public")){s.publico=true;}
            if(Modificador.equals("protected")){s.protegido=true;}
            if(clasep)
            {
                s.Nombre=Nombre;
                s.Father=temp2;
                s.Tipo=superc;
                s.Padre=superc;
                s.PosRel=temp2.PosRel;
                s.Size=temp2.Size;
                
            }
            else
            {
                s.Nombre=Nombre;
                s.Tipo="";
                s.Padre="";
                s.PosRel=0;
                s.Size=0;
            }
            s.Clase=true;
            tabla.put(Nombre, s);

            res=true;
        }
        else
        {
            Errores.InsertarError(0,"No se encuetra la clase Padre");
            Errores.Cargar=true;
        }
        //Error no clase repetida :D
        return res;
    }
    
    public int MSizeClase(String Clase,int size)
    {
        int posActual=0;
        Simbolo res=null;
        res=tabla.get(Clase);
        if(res==null){if(tablaArriba!=null){posActual=tablaArriba.MSizeClase(Clase,size);}}
        if(res!=null)
        {
            if(res.Clase)
            {
                posActual=res.PosRel;
                res.PosRel=res.PosRel+size;
                res.Size=res.Size+size;
                tabla.put(Clase, res);
                
            }
        }
        return posActual;
    }

    public boolean InsertarA(String Nombre,String Clase,String Tipo,String Modificador,LinkedList<Integer> Dim)
    {

            boolean res=false;
            Simbolo s=BuscarA(Nombre,Clase);
            Simbolo sc=BuscarC(Clase);
            if(s==null&&sc!=null)
            {
                s=new Simbolo();
                s.Atributo=true;
                s.Nombre=Nombre;
                s.Padre=Clase;
                s.Tipo=Tipo;
                s.Father=sc;
                if(Modificador.equals("public")){s.publico=true;}
                if(Modificador.equals("private")){s.privado=true;}
                if(Modificador.equals("protected")){s.protegido=true;}
               
                

                
                s.Dimenciones=Dim;
                
                int i;
                int size=s.TiposPrimitivos(Tipo);//tamaños del tipo de dato??
                if(Dim!=null)
                {
                for(i=0;i<Dim.size();i++)
                {
                    if(Dim.get(i)!=null)
                    {
                    size=size*Dim.get(i);
                    }
                }
                }

                //s.PosRel=this.MSizeClase(Clase,size);
                s.Size=size;
                s.PosRel=this.MSizeClase(Clase,s.Size);

                tabla.put(Nombre, s);
                res=true;
            }
            else
            {
                if(s!=null){
                Errores.InsertarError(0,"ya Existe el Atributo");
                Errores.Cargar=true;}
                if(sc!=null){
                Errores.InsertarError(0,"ya Existe la Calse");
                Errores.Cargar=true;}
            }
            return res;
    }
    
    static public boolean InsertarTemporal(String Nombre,String Tipo)
    {
        boolean res=true;
        Simbolo VariableTemporal;
        Simbolo res1;
        if(tablaTemYLabels==null)
        {
            tablaTemYLabels=new TablaDeSimbolos();
        }
        VariableTemporal=new Simbolo();
        VariableTemporal.Tipo=Tipo;
        VariableTemporal.Nombre=Nombre;
        VariableTemporal.Temporal=true;
        res1=tablaTemYLabels.tabla.put(Nombre, VariableTemporal);
        //if(res1==null){res=false;}
        
        return res;
    }
    
    static public String GenerarVariableTempora(String tipo)  //Inserta una variable temporal en la tablaTempYLabels
    {
        String res="";
        boolean res1=true;
        Temporal++;
        res="T"+Integer.toString(Temporal);
        res1=InsertarTemporal(res,tipo);
        if(!res1){Temporal--;res="";}//Si no pudo insertar el temporal. regresa el contado, poco probable
        return res;
    }
    
   static public boolean InsertarEtiqueta(String Nombre)
    {
       boolean res=true;
       Simbolo Label=null;
       Simbolo res1;
       if(tablaTemYLabels==null)
       {tablaTemYLabels=new TablaDeSimbolos();}
       Label=new Simbolo();
       Label.Nombre=Nombre;
       Label.Etiqueta=true;
       res1=tablaTemYLabels.tabla.put(Nombre, Label);
      // if(res1==null){res=false;}        
       return res;
    }
   static public String GenerarEtiqueta()
   {
       String res="";
       boolean res1=true;
       Etiqueta++;
       res="L"+Integer.toString(Etiqueta);
       res1=InsertarEtiqueta(res);
       if(!res1){Etiqueta--; res="";}
       return res;
   }

   static public String GenerarCadenaTemporales(int sizeStack,int sizeHeap)
   {
       String res="";
       String res1="";
       String res2="";
       int i;
       int b=0;
       Simbolo temp1;
       if(TablaDeSimbolos.tablaTemYLabels!=null)
       {
           TablaDeSimbolos.InsertarTemporal("ptr","int");
           TablaDeSimbolos.InsertarTemporal("ptk","int");
           
       for(i=0;i<TablaDeSimbolos.tablaTemYLabels.tabla.size();i++)
       {
           temp1=((Simbolo)TablaDeSimbolos.tablaTemYLabels.tabla.values().toArray()[i]);
           if(temp1.Temporal&&temp1.Tipo.equals("int"))
           {
               if(b==0){res1=temp1.Nombre;b=1;}
               else{
               res1=res1+","+temp1.Nombre;}
           
           /*if(i<TablaDeSimbolos.tablaTemYLabels.tabla.size()-1)
           {
               res1=res1+",";
           }*/
           }
       }       
       res1="int "+res1+";\n";
       }
       res=res1+"int stack["+Integer.toString(sizeStack)+"];\n"+"int heap["+Integer.toString(sizeHeap)+"];\n";
       return res;
   }
}


