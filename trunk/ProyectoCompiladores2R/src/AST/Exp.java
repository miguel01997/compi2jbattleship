/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AST;

/**
 *
 * @author Eddy
 */
public class Exp extends Instruccion {
    public String Operador;
    
    public String Valor;
    public String temp;
    
    public String Dir;
    
    public String LabelTrue;
    public String LabelFalse;
    public String LabelSig;

    public int Direccion;
    
 

    public boolean CargarTS()
    {
        boolean res=true;
        return res;
    }
    
    public String Generar3Direcciones(){String ret=""; return ret;}

}
