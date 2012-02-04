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
public class Declaracion extends Instruccion{
    
        
        public String Clase;
        public boolean ref;
        
        public boolean Atributo=false;
        public boolean Glogal=false;
        
        public String Modificador;
        public String Nombre;
        public String Tipo;

        public LinkedList<Integer> Dim;
        
        public Exp Dimensionar;
        public boolean DimVariable;
        
        public Declaracion SubDeclaracion;
        
        public Asignacion InicializarDeclaracion;
        
        public boolean Ref=false;
        
        public Declaracion(int Nolinea, String nombre, Exp dimensionar) //para declaracion tipo b=10; int a[b];
        {
            this.NoLinea = Nolinea;
            this.Nombre = nombre;

            this.DimVariable = true;

            this.Dimensionar = dimensionar;
        }

        // Este tipo de constructor es para un miembro de una clase
        public Declaracion(int Nolinea,String clase,String modificador,String nombre,String tipo,LinkedList<Integer> dim)
        {
            Clase=clase;
            Atributo=true;
            Modificador=modificador;
            Nombre=nombre;
            Tipo=tipo;
            Dim=dim;
            NoLinea=Nolinea;
            TablaDeSimbolos.InsertBreakPoint(NoLinea);
            TablaDeSimbolos.DisableBreakPoint(NoLinea);
        }
        
        
        public Declaracion(int Nolinea,String modificador,String nombre,String tipo,LinkedList<Integer> dim)
        {
            //Clase=clase;
            Atributo=true;
            Modificador=modificador;
            Nombre=nombre;
            Tipo=tipo;
            Dim=dim;
            NoLinea=Nolinea;
            TablaDeSimbolos.InsertBreakPoint(NoLinea);
            TablaDeSimbolos.DisableBreakPoint(NoLinea);
        }
        
        public Declaracion(int Nolinea,String clase,String modificador,String nombre,String tipo,LinkedList<Integer> dim,boolean ref)
        {
            Clase=clase;
            Modificador=modificador;
            Nombre=nombre;
            Tipo=tipo;
            Dim=dim;
            NoLinea=Nolinea;
            Ref=ref;
            TablaDeSimbolos.InsertBreakPoint(NoLinea);
            TablaDeSimbolos.DisableBreakPoint(NoLinea);
        }
        
        
        public Declaracion(String clase,String modificador,String nombre,String tipo,LinkedList<Integer> dim)
        {
            Clase=clase;
            Modificador=modificador;
            Nombre=nombre;
            Tipo=tipo;
            Dim=dim;
        }
        
        // para subDeclaracion
        public Declaracion(String nombre,LinkedList<Integer> dim)
        {
            Nombre=nombre;
            Dim=dim;
        }
        
        //para Globales
        public Declaracion(String nombre,String tipo,LinkedList<Integer> dim ,boolean ref)
        {
            Nombre=nombre;
            Tipo=tipo;
            Dim=dim;
            this.Ref=ref;
        }
        
        //Primero colocamos los atributos de Clase, Metodo(si es que existe) y Modificador(si es que existe)
        //en las subDeclaraciones (esta son en  int a,b,c -> b,c) 
        //tambien colocamos la tabla de simbolos del acutal ambito
        
        public boolean CargarTS()
        {
            boolean res = false;
            if (this.SubDeclaracion != null)
            {
                this.SubDeclaracion.tabla = this.tabla;

                this.SubDeclaracion.Clase = this.Clase;
                
                //this.SubDeclaracion.Clase1=Clase1;
                
                this.SubDeclaracion.Tipo = this.Tipo;
                this.SubDeclaracion.Modificador = this.Modificador;
                this.SubDeclaracion.Metodo = this.Metodo;

                res = this.SubDeclaracion.CargarTS();
            }
            //OJO
            //el Atributo DimVariable significa variable en tiempo de ejecución
            //el atributo no nos sirve para el momento de generación de codigo de 3Direcciones 
            //por lo tanto no debe de ser tomando en cuenta
            //
            if (!this.DimVariable)  
            {
                if (this.Atributo)
                {
                if (this.Atributo)
                    { 
                    res = this.tabla.InsertarA(this.Clase + "_" + this.Nombre, this.Clase, this.Tipo, this.Modificador, this.Dim);
                    }
                }
                else if (!this.Metodo.equals("")&&!this.Glogal)
                {
                    res = this.tabla.InsertarV(this.Nombre, this.Tipo, this.Metodo, this.Dim);
                    if(res&&Ref)
                    {
                        Simbolo Refe=this.tabla.BuscarV(this.Nombre);
                        Refe.Referencia=true;
                    }
                }
                 else if(this.Glogal){
                 this.tabla.InsertarVG(this.Nombre, this.Tipo, this.Dim);
                }

                if (this.InicializarDeclaracion != null)
                {
                    this.InicializarDeclaracion.tabla = this.tabla;

                    this.InicializarDeclaracion.Metodo = this.Metodo;
                    this.InicializarDeclaracion.SimboloMetodo = this.SimboloMetodo;
                    
                    //this.InicializarDeclaracion.Clase1=Clase1;

                    res = this.InicializarDeclaracion.CargarTS();
                }
            }
            else
            {
                if (this.Dimensionar != null)
                {
                    this.Dimensionar.tabla = this.tabla;
                    
                    this.Dimensionar.SimboloMetodo = this.SimboloMetodo;
                    this.Dimensionar.Metodo = this.Metodo;
                    
                    //this.Dimensionar.Clase1=this.Clase1;
                    
                    res = this.Dimensionar.CargarTS();
                }
                if (this.InicializarDeclaracion != null)
                {
                    this.InicializarDeclaracion.tabla = this.tabla;

                    this.InicializarDeclaracion.Metodo = this.Metodo;
                    this.InicializarDeclaracion.SimboloMetodo = this.SimboloMetodo;
                    
                    //this.InicializarDeclaracion.Clase1=Clase1;

                    res = this.InicializarDeclaracion.CargarTS();
                }
            }
            return res;
        }
        
        
        
        public boolean Ejecutar()
        {
            boolean res = true;
            if (this.SubDeclaracion != null)
            {
                this.SubDeclaracion.Ejecutar();
            }
            if (!this.DimVariable)
            {
                if (this.InicializarDeclaracion != null)
                {
                res = this.InicializarDeclaracion.Ejecutar();
                }
            }
           else
           {
            boolean res1 = true;
            res1 = this.Dimensionar.Ejecutar();
            if (res1)
            {
                if (this.Dimensionar.tipos.Buscar("int"))
                {
                LinkedList dim = new LinkedList();
                dim.add(Integer.valueOf(Integer.parseInt(this.Dimensionar.Valor)));
                this.Dim = dim;
                if (!this.Metodo.equals(""))
                {
                    res = this.tabla.InsertarV(this.Nombre, this.Tipo, this.Metodo, dim);
                    if (!res)
                    {
                    Errores.InsertarError(this.NoLinea, "La Variable " + this.Nombre + " ya esta declarada");
                    }
                }
                }
              else
              {
                Errores.InsertarError(this.NoLinea, "La Variable No tiene dimecinoes Enteras");
              }
            }
            else
            {
             Errores.InsertarError(this.NoLinea, "Error: al querer Declarar la variable: "+this.Nombre);
            }
            }

         return res;
      }
       // dado que la instrucción de declaración de variables puden inicializarse se recorre la subExpreciones y si tienen 
       // una expresión inicializadora e.g. int a=10+10; se genera su codigo de 3 direcciónes 
      public String Generar3Direcciones(){
          String ret=""; 
          String sub="";
          String Asi="";
          if(this.SubDeclaracion!=null)
          {
              sub=this.SubDeclaracion.Generar3Direcciones();
          }
          if(this.InicializarDeclaracion!=null)
          {
              Asi=this.InicializarDeclaracion.Generar3Direcciones();
          }
          ret=sub+Asi;
          return ret;
      
      } 
}
