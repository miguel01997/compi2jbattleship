/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AST;

/**
 *
 * @author Eddy
 */
public class ExpU extends Exp{
    public Exp U;
    public String OP;
    
    public String LabelTrue;
    public String LabelFalse;
    public String LabelSig;
    
    public ExpU(Exp u, String oP)
    {
        U=u;
        OP=oP;  
    }
    
  
    
    public boolean CargarTS()
    {
        boolean res=true;
        if(U!=null)
        {
        U.Metodo=Metodo;
        U.tabla=tabla;
        
        //U.Clase1=Clase1;
        U.SimboloMetodo=SimboloMetodo;
        res=U.CargarTS();
        }
        return res;
    }

    
    
    public boolean Ejecutar()
    {
        boolean res=true;
        float CasteoUFloat=0;
        int   CasteoUEntero=0;
        
        res=U.Ejecutar();//ejecuta la sub-exprecion del operador Unario
        
        if(res)
        {//Operador Unario de -
        if(OP.equals("-"))
        {
            if(U.tipos.Buscar("int")||U.tipos.Buscar("float"))
            {
                if(U.tipos.Buscar("float")){
                    CasteoUFloat=Float.parseFloat(U.Valor);
                    CasteoUFloat=0-CasteoUFloat;
                    Valor=Float.toString(CasteoUFloat);
                    tipos.Add("float");
                } if(U.tipos.Buscar("int")){
                    CasteoUEntero=Integer.parseInt(U.Valor);
                    CasteoUEntero=0-CasteoUEntero;
                    Valor=Integer.toString(CasteoUEntero);
                    tipos.Add("int");
                }
             }
               
                else{res=false;} //Error: No es un numero 
        }
        //Negacion
        if(OP.equals("!"))
        {
            if(U.tipos.Buscar("int")||U.tipos.Buscar("float"))
            {
                if(U.tipos.Buscar("float")){
                    CasteoUFloat=Float.parseFloat(U.Valor);
                    if(CasteoUFloat>0){Valor="0"; tipos.Add("int");}
                    else{Valor="1"; tipos.Add("int");}
                } if(U.tipos.Buscar("int")){
                    CasteoUEntero=Integer.parseInt(U.Valor);
                    if(CasteoUEntero>0){Valor="0"; tipos.Add("int");}
                    else{Valor="1"; tipos.Add("int");}
                }
             }           
                else{res=false;} //Error: No es un numero 
         }
        }
        return res;
    }
    
    
    public String Generar3Direcciones()
    {
        String ret=""; 
        String temp1="";
        String ins="";
        String U1="";
        if(this.OP.equals("-"))
        {
            U1=U.Generar3Direcciones();
            temp1=TablaDeSimbolos.GenerarVariableTempora("int");
            ins=temp1+"=0 -"+this.U.Valor+";\n";  // el simbolo - es el operador en este caso - pero puede cambiar
            this.Valor=temp1;
            ret=U1+"\n"+ins;
        }
        if(this.OP.equals("!"))
        {//falta arreglara este opreador
            
            U.LabelFalse=LabelTrue;
            U.LabelTrue=LabelFalse;
            U1=U.Generar3Direcciones();
            if(U.tipos.Buscar("boolean"))
            {
            temp1=TablaDeSimbolos.GenerarVariableTempora("int");
            ins=temp1+"=0 -"+this.U.Valor+";\n";  // el simbolo - es el operador en este caso - pero puede cambiar            
            this.tipos.Add("boolean");
            ret=U1+"\n";
            }
            else{} //Error la exprecio no es de tipo booleano para continuar
            
        
        
        }
        if(this.OP.equals(">>"))
        {        
            U1=U.Generar3Direcciones();
            temp1=TablaDeSimbolos.GenerarVariableTempora("int");
            ins=temp1+"=0 -"+this.U.Valor+";\n";  // el simbolo - es el operador en este caso - pero puede cambiar
            this.Valor=temp1;
            ret=U1+"\n"+ins;
        }
        return ret;    
    }


}
