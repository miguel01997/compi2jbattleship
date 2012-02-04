/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

/**
 *
 * @author eddytrex
 */
public class ExpLiteral extends Exp{
    
    public ExpLiteral(String valor,String tipo)
    {
        Valor=valor;
        tipos.Add(tipo);
        //if(tipo.equals("int")){tipos.Add("float");}
    }
    
    public boolean Debug()
    {
        boolean res=true;
        return res;
    }
    public boolean CargarTS(){return true;}
    public boolean Ejecutar(){return true;}
    
    
    
    public String Generar3Direcciones()
    {
        String ret=""; 
        if(this.tipos.Tipos.get(0).equals("int")){ret="";}
        if(this.tipos.Buscar("boolean")&&Valor.equals("1")){ret="if  1==1 goto "+this.LabelTrue+";\n"+"goto "+this.LabelFalse+"\n";}
        if(this.tipos.Buscar("boolean")&&Valor.equals("0")){ret="if 1==0 goto "+this.LabelTrue+";\n"+"goto "+this.LabelFalse+"\n";}
        if(this.tipos.Buscar("char")){char tempChar=this.Valor.charAt(1); int tempint=(int)tempChar;  this.Valor=Integer.toString(tempint);} 
        
        //cambiar el valor a codigo asccii
        
        //Dependiendo el tipo se crean objetos como por ejemplo un String o ¿float?:/, si no varios enteros :D con el valor aplicar mascaras 
        return ret;
    }
}
