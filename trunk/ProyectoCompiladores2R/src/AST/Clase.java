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
public class Clase extends  Instruccion{
   public String NombreClase;
   public String Padre;
    
   public  LinkedList <Declaracion> Atributos;
   public  LinkedList <DeclaracionMetodos> Metodos;
   
   //en su constructor se tiene el nombre de la case como tambien el nombre de la clase padre de quien hereda 
   public Clase(String nombreClase,String padre)
   {
        NombreClase=nombreClase;
        Padre=padre;
        
   }
   
   //dado que solo los metodos generan codigo de 3 direcciónes (TAC) solo se recorren la lista de metodos
   public String Generar3Direcciones()
   {
       String ret=""; 
       String a="";
       int i;
       for(i=0;i<this.Metodos.size();i++)
       {
           a=this.Metodos.get(i).Generar3Direcciones();
           ret=ret+a;
       }
       return ret;
   }
   
   //Se crea el simbolo de la clase y se inserta en la tabla de simbolos 
   public boolean CargarTS()
   {
        boolean res=false;
        
        tabla.tablaArriba=null;
        if(!Padre.equals(""))
        {
        //Buscar Clase Padre
        res=tabla.InsertarC(NombreClase, Padre,"public");
        
        //Buscar SimboloClase y Colocarlo en Clase1;
        
        //Simbolo father=tabla.BuscarC(Padre);             
        //Clase1=tabla.BuscarC(NombreClase);        
        //Clase1.Father=father;
        
        }
        
        else
        {
            
            
        res=tabla.InsertarC(NombreClase,"", "public");
        
        //Buscar SimboloClase y Colocarlo en Clase1;
        //Clase1=tabla.BuscarC(NombreClase);
        }
        
        int i;
        
        //Se recore los Atributos de la clase  y se pasa el atributo del nombre de la clase a que pertenecen 
        // NombreClase a Clase
        //seria mas eficiente si se pasa el simbolo de  la clase para poder hacer cambios de forma mas facil 
        //y no tener que ir a buscar el simolo de la clase cada vez
        
        //pero en este caso se paso el Nombre de la clase 
        
        if(Atributos!=null&&res)
        {
        for(i=0;i<Atributos.size()&&res;i++)
        {
            if(Atributos.get(i)!=null){
                
                Atributos.get(i).Clase=NombreClase;
                
                //Atributos.get(i).Clase1=Clase1;
                
                Atributos.get(i).tabla=tabla;
                res=Atributos.get(i).CargarTS();
            }
        }
        }
        //Si hay Metodos se hace lo mismo con el nombre de la clase 
        // igual seria mas eficiente si se pasa el simbolo de  la clase para poder hacer cambios de forma mas facil 
        //y no tener que ir a buscar el simolo de la clase cada vez
        //
        //pero en este caso se paso el Nombre de la clase 
        if(Metodos!=null&&res)
        {
        for(i=0;i<Metodos.size()&&res;i++)
        {
            if(Metodos.get(i)!=null){
                Metodos.get(i).Clase=NombreClase;
                
                //Atributos.get(i).Clase=Clase;                
                Metodos.get(i).tabla=tabla;
                
                res=Metodos.get(i).CargarTS();
            }
        }
        }


        return res;
   }

}
