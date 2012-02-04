/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AST;

/**
 *
 * @author Eddy
 */
public class ExpBinaria extends Exp{
    public Exp Iz;
    public Exp De;
    public String OP;
    
    
    
    public ExpBinaria(Exp iz,Exp de,String oP)
    {
        Iz=iz;
        De=de;
        OP=oP;
    }
    

    public boolean CargarTS()
    {
        boolean res=true;
        if(Iz!=null)
        {
            Iz.Metodo=Metodo;
            Iz.tabla=tabla;
            Iz.SimboloMetodo=SimboloMetodo;
            
            Iz.Clase=Clase;
            
            res=Iz.CargarTS();
        }
        if(De!=null)
        {
            De.Metodo=Metodo;
            De.tabla=tabla;
            De.SimboloMetodo=SimboloMetodo;
            
            De.Clase=Clase;
            
            res=De.CargarTS();
        }
        return res;

    }

    public boolean Ejecutar()
    {
        boolean res = true;
    boolean bandera = false;

    int CasteoDeEntero = 0; int CasteoIzEntero = 0;
    float CasteoDeFloat = 0.0F; float CasteoIzFloat = 0.0F;

    boolean CIF = false; boolean CDF = false;

    if (this.Iz != null) res = this.Iz.Ejecutar();
    if ((this.De != null) && (res)) res = this.De.Ejecutar();

    if (this.OP.equals("+"))
    {
      if ((this.Iz.tipos.Buscar("char")) && (this.De.tipos.Buscar("char")))
      {
        this.Valor = (this.Iz.Valor + this.De.Valor);
        this.tipos.Add("char");
        bandera = true;
      }

    }

    if ((this.Iz.tipos.Equivalencia(this.De.tipos)) && ((this.Iz.tipos.Buscar("int")) || (this.De.tipos.Buscar("float"))))
    {
      if (this.Iz.tipos.Buscar("int")) CasteoIzEntero = Integer.parseInt(this.Iz.Valor);
      if (this.Iz.tipos.Buscar("float")) { CasteoIzFloat = Float.parseFloat(this.Iz.Valor); CIF = true;
      }
      if (this.De.tipos.Buscar("int")) CasteoDeEntero = Integer.parseInt(this.De.Valor);
      if (this.De.tipos.Buscar("float")) { CasteoDeFloat = Float.parseFloat(this.De.Valor); CDF = true;
      }

      if (this.OP.equals("+")) {
        if ((CIF) || (CDF)) { float ValorF = CasteoIzFloat + CasteoDeFloat;
          this.Valor = Float.toString(ValorF);
          this.tipos.Add("float");
        } else {
          int ValorI = CasteoIzEntero + CasteoDeEntero;
          this.Valor = Integer.toString(ValorI);
          this.tipos.Add("int");
        }
        bandera = true;
      }
      if (this.OP.equals("-")) {
        if ((CIF) || (CDF)) { float ValorF = CasteoIzFloat - CasteoDeFloat;
          this.Valor = Float.toString(ValorF);
          this.tipos.Add("float");
        } else {
          int ValorI = CasteoIzEntero - CasteoDeEntero;
          this.Valor = Integer.toString(ValorI);
          this.tipos.Add("int");
        }
        bandera = true;
      }
      if (this.OP.equals("*")) {
        if ((CIF) || (CDF)) { float ValorF = CasteoIzFloat * CasteoDeFloat;
          this.Valor = Float.toString(ValorF);
          this.tipos.Add("float");
        } else {
          int ValorI = CasteoIzEntero * CasteoDeEntero;
          this.Valor = Integer.toString(ValorI);
          this.tipos.Add("int");
        }
        bandera = true;
      }
      if (this.OP.equals("/"))
      {
        if ((CIF) || (CDF)) {
          if (CasteoDeFloat != 0.0F) { float ValorF = CasteoIzFloat / CasteoDeFloat;
            this.Valor = Float.toString(ValorF);
            this.tipos.Add("float");
          } else {
            res = false;
            Errores.InsertarError(this.NoLinea, "No se puede dividir entre 0");
          }
          bandera = true;
        }
        else if (CasteoDeEntero != 0) {
          int ValorI = CasteoIzEntero / CasteoDeEntero;
          this.Valor = Integer.toString(ValorI);
          this.tipos.Add("int");
        } else {
          res = false;
          Errores.InsertarError(this.NoLinea, "No se puede dividir entre 0");
        }

        bandera = true;
      }

      if (this.OP.equals("<")) {
        if ((CIF) || (CDF)) {
          if (CasteoIzFloat < CasteoDeFloat) { this.Valor = "1"; this.tipos.Add("int"); } else {
            this.Valor = "0"; this.tipos.Add("int");
          }
        }
        else if (CasteoIzEntero < CasteoDeEntero) { this.Valor = "1"; this.tipos.Add("int"); } else {
          this.Valor = "0"; this.tipos.Add("int");
        }
        bandera = true;
      }
      if (this.OP.equals(">")) {
        if ((CIF) || (CDF)) {
          if (CasteoIzFloat > CasteoDeFloat) { this.Valor = "1"; this.tipos.Add("int"); } else {
            this.Valor = "0"; this.tipos.Add("int");
          }
        }
        else if (CasteoIzEntero > CasteoDeEntero) { this.Valor = "1"; this.tipos.Add("int"); } else {
          this.Valor = "0"; this.tipos.Add("int");
        }
        bandera = true;
      }

      if (this.OP.equals("<=")) {
        if ((CIF) || (CDF)) {
          if (CasteoIzFloat <= CasteoDeFloat) { this.Valor = "1"; this.tipos.Add("int"); } else {
            this.Valor = "0"; this.tipos.Add("int");
          }
        }
        else if (CasteoIzEntero <= CasteoDeEntero) { this.Valor = "1"; this.tipos.Add("int"); } else {
          this.Valor = "0"; this.tipos.Add("int");
        }
        bandera = true;
      }
      if (this.OP.equals(">=")) {
        if ((CIF) || (CDF)) {
          if (CasteoIzFloat >= CasteoDeFloat) { this.Valor = "1"; this.tipos.Add("int"); } else {
            this.Valor = "0"; this.tipos.Add("int");
          }
        }
        else if (CasteoIzEntero >= CasteoDeEntero) { this.Valor = "1"; this.tipos.Add("int"); } else {
          this.Valor = "0"; this.tipos.Add("int");
        }
        bandera = true;
      }

      if (this.OP.equals("&&")) {
        if ((CIF) || (CDF)) {
          if ((CasteoIzFloat >= 1.0F) && (CasteoDeFloat >= 1.0F)) { this.Valor = "1"; this.tipos.Add("int"); } else {
            this.Valor = "0"; this.tipos.Add("int");
          }
        }
        else if ((CasteoIzEntero >= 1) && (CasteoDeEntero >= 1)) { this.Valor = "1"; this.tipos.Add("int"); } else {
          this.Valor = "0"; this.tipos.Add("int");
        }
        bandera = true;
      }
      if (this.OP.equals("||")) {
        if ((CIF) || (CDF)) {
          if ((CasteoIzFloat >= 1.0F) || (CasteoDeFloat >= 1.0F)) { this.Valor = "1"; this.tipos.Add("int"); } else {
            this.Valor = "0"; this.tipos.Add("int");
          }
        }
        else if ((CasteoIzEntero >= 1) || (CasteoDeEntero >= 1)) {
          this.Valor = "1";
          this.tipos.Add("int"); } else {
          this.Valor = "0"; this.tipos.Add("int");
        }
        bandera = true;
      }

    }

    if (this.OP.equals("==")) {
      if ((this.Iz.tipos.Equivalencia(this.De.tipos)) && (this.Iz.tipos.SearchPrimitivo()))
      {
        if (this.Iz.Valor.equals(this.De.Valor)) {
          this.Valor = "1"; this.tipos.Add("int");
          res = true;
        }
        else {
          this.Valor = "0"; this.tipos.Add("int");
        }
      }
      else {
        res = false;
        Errores.InsertarError(this.NoLinea, "No se pueden comparar los tipos" + this.Iz.tipos.Tipos() + "con " + this.De.tipos.Tipos());
      }

      bandera = true;
    }
    if (this.OP.equals("!=")) {
      if ((this.Iz.tipos.Equivalencia(this.De.tipos)) && (this.Iz.tipos.SearchPrimitivo()))
      {
        if (this.Iz.Valor.equals(this.De.Valor)) {
          this.Valor = "0"; this.tipos.Add("int");
          res = true;
        }
        else {
          this.Valor = "1"; this.tipos.Add("int");
        }
      }
      else {
        res = false;
        Errores.InsertarError(this.NoLinea, "No se pueden comparar los tipos" + this.Iz.tipos.Tipos() + "con " + this.De.tipos.Tipos());
      }
      bandera = true;
    }

    if (!bandera) { res = false; Errores.InsertarError(this.NoLinea, "No se pueden Operar bajo " + this.OP + " los Tipos: " + this.Iz.tipos.Tipos() + " y " + this.De.tipos.Tipos());
    }
    return res;
    }
    
   public String Generar3Direcciones()
   {
       //flata asignarle el ret
       String ret=""; 
       String CIz="";
       String CDe="";
       String temp1,temp2,temp3;
       String ins0,ins1,ins2,ins3,ins4;
       //se necesita que tanto le operador Iz y De sean del mismo tipo ademas de entero o flotante, se trasaldaran las etiqueta de falso,
       //verdadero y siguiente a los 2 operandos tipo que devuelve Booleano
       if(this.OP.equals("<"))
       {
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
               temp1=TablaDeSimbolos.GenerarVariableTempora("int");
               ins0=temp1+"="+"0"+";\n";
               ins1="if ("+Iz.Valor+"<"+De.Valor+") goto "+LabelTrue+";\n";               
               ins2=" goto "+LabelFalse+";\n";
               this.tipos.Add("boolean");
               ret=CIz+CDe+ins0+ins1+ins2;
               
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       if(this.OP.equals(">")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
               temp1=TablaDeSimbolos.GenerarVariableTempora("int");
               ins0=temp1+"="+"0"+";\n";
               ins1="if ("+Iz.Valor+">"+De.Valor+") goto "+LabelTrue+";\n";               
               ins2=" goto "+LabelFalse+";\n";   
               this.tipos.Add("boolean");
               ret=CIz+CDe+ins0+ins1+ins2;
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       if(this.OP.equals("<=")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
               temp1=TablaDeSimbolos.GenerarVariableTempora("int");
               ins0=temp1+"="+"0"+";\n";
               ins1="if ("+Iz.Valor+"<="+De.Valor+") goto "+LabelTrue+";\n";               
               ins2="goto "+LabelFalse+";\n";   
               this.tipos.Add("boolean");
               ret=CIz+CDe+ins0+ins1+ins2;
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       if(this.OP.equals(">=")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
               temp1=TablaDeSimbolos.GenerarVariableTempora("int");
               ins0=temp1+"="+"0"+";\n";
               ins1="if ("+Iz.Valor+">="+De.Valor+") goto "+LabelTrue+";\n";               
               ins2="goto "+LabelFalse+";\n";
               this.tipos.Add("boolean");
               ret=CIz+CDe+ins0+ins1+ins2;
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       
       //se necesita que tanto le operador Iz y De sean del mismo tipo
       if(this.OP.equals("==")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&Iz.tipos.SearchPrimitivo())
           {
               temp1=TablaDeSimbolos.GenerarVariableTempora("int");
               ins0=temp1+"="+"0"+";\n";
               ins1="if ("+Iz.Valor+"=="+De.Valor+") goto "+LabelTrue+";\n";               
               ins2="goto "+LabelFalse+";\n";      
               this.tipos.Add("boolean");
               ret=CIz+CDe+ins0+ins1+ins2;
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       if(this.OP.equals("!=")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&Iz.tipos.SearchPrimitivo())
           {
               temp1=TablaDeSimbolos.GenerarVariableTempora("int");
               ins0=temp1+"="+"0"+";\n";
               ins1="if ("+Iz.Valor+"!="+De.Valor+") goto "+LabelTrue+";\n";               
               ins2="goto "+LabelFalse+";\n";
               ret=CIz+CDe+ins0+ins1+ins2;
               this.tipos.Add("boolean");
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       
       //se necesita que los operadores Iz como De sean booleanos se trasaladar la etiqueta se falso,verdadero y siguiente a los 2 operandos
       if(this.OP.equals("&&")){
            String nEtiqueta1=TablaDeSimbolos.GenerarEtiqueta();
            String nEtiqueta2=TablaDeSimbolos.GenerarEtiqueta();
            ins2="";
            temp1=TablaDeSimbolos.GenerarVariableTempora("int");
            ins1=temp1+"= "+"0;\n";
            Iz.LabelTrue=nEtiqueta1;
            Iz.LabelFalse=LabelFalse;
       
            De.LabelTrue=LabelTrue;
            De.LabelFalse=LabelFalse;
       
            CIz=Iz.Generar3Direcciones();
            CDe=De.Generar3Direcciones();
       
            if(Iz.tipos.Buscar("boolean")&&De.tipos.Buscar("boolean"))
            {
                ins2=CIz+nEtiqueta1+":\n"+CDe;
       
            }    
            ret=ins1+ins2;
            this.tipos.Add("boolean");
       }
       if(this.OP.equals("||")){
            String nEtiqueta1=TablaDeSimbolos.GenerarEtiqueta();
            String nEtiqueta2=TablaDeSimbolos.GenerarEtiqueta();
            ins2="";
            temp1=TablaDeSimbolos.GenerarVariableTempora("int");
            ins1=temp1+"=0;\n";
       
            Iz.LabelTrue=LabelTrue;
            Iz.LabelFalse=nEtiqueta1;
       
            De.LabelTrue=LabelTrue;
            De.LabelFalse=LabelFalse;
       
            CIz=Iz.Generar3Direcciones();
            CDe=De.Generar3Direcciones();
       
            if(Iz.tipos.Buscar("boolean")&&De.tipos.Buscar("boolean"))
            {
                ins2=CIz+nEtiqueta1+":\n"+CDe;
            }
            ret=ins1+ins2;
            this.tipos.Add("boolean");
       }
       
       // OJO falta que  las combinaciones float int , int float casteen el int a float esa seria otra instruccion
       //Se necesita que los peradores sean enteros o flotantes  en el caso de + puede ser string o char
       if(this.OP.equals("+"))
       {
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("int"))   
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                
                ins1=temp1+"="+Iz.Valor+"+"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("int");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"+"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"+"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("int"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"+"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       if(this.OP.equals("-")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("int"))   
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                
                ins1=temp1+"="+Iz.Valor+"-"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("int");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"-"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"-"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("int"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"-"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       if(this.OP.equals("*")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("int"))   
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                
                ins1=temp1+"="+Iz.Valor+"*"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("int");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"*"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"*"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("int"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"*"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
               
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       
       //Se necesita que que le operador derecho no sea 0, ademas de del comentario anterior
       if(this.OP.equals("/")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("int"))   
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                
                ins1=temp1+"="+Iz.Valor+"/"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"/"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"/"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("int"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"/"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
               
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
       if(this.OP.equals("%")){
           CIz=this.Iz.Generar3Direcciones();
           CDe=this.De.Generar3Direcciones();
           if(Iz.tipos.Equivalencia(De.tipos)&&(Iz.tipos.Buscar("int")||Iz.tipos.Buscar("float")))
           {
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("int"))   
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                
                ins1=temp1+"="+Iz.Valor+"%"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("int");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"%"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("int")&&De.tipos.Buscar("float"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"%"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
            if(Iz.tipos.Buscar("float")&&De.tipos.Buscar("int"))
            {
                temp1=TablaDeSimbolos.GenerarVariableTempora("float");
                
                ins1=temp1+"="+Iz.Valor+"%"+De.Valor+";\n";
                ret=CIz+CDe+ins1;
                this.Valor=temp1;
                this.tipos.Add("float");
            }
               
           }
           else{}//Error los tipos Iz.tipo no es compatible con De.tipos
       }
   
           
       return ret;
   }
}
