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
public class Simbolo implements Serializable{
    String Nombre;
    String Tipo;
    String Padre;

    Simbolo Father;
    
    boolean Metodo;
    boolean Variable;
    boolean Parametro;
    
    boolean Atributo;
    boolean Clase;
    boolean Global;
    
    boolean BreakPoint;
    boolean ActivoBP;

    public Instruccion SubArbol;

    boolean privado=false;
    boolean publico=false;
    boolean protegido=false;


    public int PosRel=0;
    public int PosGlo=0;

    int Size=0;

    ConjuntoTipos tipos;

    public DeclaracionMetodos metodo;

    public boolean Etiqueta;
    public boolean Temporal;
    public boolean GC3DM;

    
    public String EtiquetaFin;

    LinkedList <String> Parametros;
    public LinkedList <Simbolo> Argumentos;

    LinkedList <Integer> Dimenciones;

    int PRA;
    
    String LabelFin;

    boolean Referencia=false;
    
    public int TiposPrimitivos(String tipop)
    {
        this.Tipo=tipop;
        this.Size=2;
        if(tipop.equals("int")){this.Size=2;}
        if(tipop.equals("float")){this.Size=4;}
        if(tipop.equals("char")){this.Size=1;}
        if(tipop.equals("boolean")){this.Size=1;}
        
        if(tipop.equals("void")){this.Size=2;}

        return this.Size;
    }

    public boolean Primitivo()
    {
        boolean res=false;
        if(Tipo.equals("int")||Tipo.equals("float")||Tipo.equals("char")||Tipo.equals("boolean")||Tipo.equals("string"))
        {
            res=true;
        }
        return res;
    }

    public Simbolo()
    {}
    
    public Simbolo(String n,String Tipo,boolean V)
    {
        this.Nombre=n;
        this.Tipo=Tipo;
        this.Variable=V;
        this.Size=ConjuntoTipos.SizePrimitivos(Tipo);
    }
}
