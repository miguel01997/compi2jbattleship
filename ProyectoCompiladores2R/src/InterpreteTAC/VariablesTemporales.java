/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterpreteTAC;

import java.util.Hashtable;

/**
 *
 * @author eddytrex
 */
public class VariablesTemporales {
    public Hashtable <String,Temporal> temporales;
    
    public Temporal GetValorInt(String temp)
    {
        Temporal res=null;
        if(temporales.contains(temp))
        {
            res=temporales.get(temp);
        }
        return res;
    }
    public boolean  agregarTemporal(String nombre,String tipo)
    {
        boolean res=false;
        Temporal temp=new Temporal();
        temp.tipo=tipo;
        
        return res;
    }
    
}
