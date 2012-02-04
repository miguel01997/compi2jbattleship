/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AST;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Eddy
 */
public class ConjuntoTipos implements Serializable{
    LinkedList<String> Tipos;
    public ConjuntoTipos()
    {
        Tipos=new LinkedList();
    }
    //Busca un tipo en el conjunto de tipos
    public boolean Buscar(String Tipo){boolean res=false;
                                        int i;
                                        for(i=0;i<Tipos.size();i++)
                                        {
                                            if(Tipos.get(i).equals(Tipo))
                                            {
                                                res=true;
                                            }
                                        }
                                        return res;}
    //Agrega un tipo a al conjunto de tipo
    public void Add(String Tipo)
    {
        boolean resBuscar=Buscar(Tipo);
        if(!resBuscar)
        {
            Tipos.add(Tipo);
        }
    }
    // dado un string verifica si es prmitivo o no
    public boolean IsPrimitivos(String tipop)
    {
        boolean res=false;
        if(tipop.equals("int")){res=true;}
        if(tipop.equals("float")){res=true;}
        if(tipop.equals("char")){res=true;}
        if(tipop.equals("boolean")){res=true;}
        // Falta string que no es primitivo si no un objeto que esta en el heap
        return res;
    }
    //Busca en el conjunto de tipos si es primitivo (int float char boolean)
    public boolean SearchPrimitivo()
    { 
        boolean res=false;
        
            int i;
            for(i=0;i<Tipos.size();i++)
            {
                if(IsPrimitivos(Tipos.get(i))){res=true;}
                else{res=false;}
            }     
        return res;
    }
    //  devuelve el tamaño que cada tipo de dato primitivo ocupa en memoria el resultado esta en Bits
    public static int SizePrimitivos(String tipop)
            {
                int res=2;
                if(tipop.equals("int")){res=2;}
                if(tipop.equals("float")){res=4;}
                if(tipop.equals("char")){res=1;}
                if(tipop.equals("boolean")){res=1;}
                return res;
            }
    //Verifica si un conjunto tipo es esquivalente a otro conjunto de tipos 
    //esto es si un un Tipo esta en el otro conjunto es equivalente
    public boolean  Equivalencia(ConjuntoTipos OtroTipo)
    {
        boolean res=true; 
        int i;
        if(Tipos.size()==OtroTipo.Tipos.size())
        {
            for(i=0;i<Tipos.size()&&res;i++)
            {
                res=OtroTipo.Buscar(Tipos.get(i));
            }
        }
        return res;
    }
    
     public String Tipos()
  {
    String res = "";

    for (int i = 0; i < this.Tipos.size(); i++)
    {
      res = res + (String)this.Tipos.get(i);
    }
    return res;
  }

}
