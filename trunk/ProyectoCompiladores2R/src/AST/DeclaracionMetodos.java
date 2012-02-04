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
public class DeclaracionMetodos extends Instruccion {
    
    public String Clase;
    public String Modificador;
    public String Nombre;
    
    public String Tipo;

    public String NombreR;
    
    

    public LinkedList<Declaracion> Parametros;

    public DeclaracionMetodos(String clase,String mo,String nombre,String tipo,LinkedList<Declaracion> Pa,LinkedList<Instruccion>Sig)
    {

        Clase=clase;
        Modificador=mo;
        Nombre=nombre;
        Tipo=tipo;
        Parametros=Pa;
        SiguienteInstruccion=Sig;
    }
    
    public DeclaracionMetodos(String mo,String nombre,String tipo,LinkedList<Declaracion> Pa,LinkedList<Instruccion>Sig)
    {

        //Clase=clase;
        Modificador=mo;
        Nombre=nombre;
        Tipo=tipo;
        Parametros=Pa;
        SiguienteInstruccion=Sig;
    }
    public DeclaracionMetodos(String nombre,String tipo,LinkedList<Declaracion> Pa,LinkedList<Instruccion>Sig)           
    {
        Nombre=nombre;
        Tipo=tipo;
        Parametros=Pa;
        SiguienteInstruccion=Sig;
    
    }

    private LinkedList<String> ExtraerTiposParametros()
    {
            LinkedList<String>res=null;
            int i;
            if(Parametros!=null)
            {
                res=new LinkedList();
                for(i=0;i<Parametros.size();i++)
                {
                    res.add(Parametros.get(i).Tipo);
                }
            
            }
            return res;
    }
    
    public String ParteNombre()
    {
        String res="";
        int i;
        if(Parametros!=null)
        {
        for(i=0;i<Parametros.size();i++)
        {
            res=res+"_"+Parametros.get(i).Tipo;
        }
        }
        return res;
    }
    
    

    public boolean CargarTS()
    {
        boolean res=true;

        LinkedList<String> tiposParametros=ExtraerTiposParametros();
        LinkedList<Simbolo> Sparametros=new LinkedList();
        
        String N=ParteNombre();
        if(Clase!=null){
        NombreR=Clase+"_"+Nombre+"_"+N;}
        else{NombreR="_"+Nombre+"_"+N;}
        Simbolo sb=tabla.BuscarM(NombreR);
        Simbolo simboloMetodo;
        if(sb!=null)
        {
            
                Errores.InsertarError(0,"ya Existe el Metodo");
                Errores.Cargar=true;
        }
        else
        {

            
            tabla.InsertarM(NombreR, Clase, Tipo, Modificador,tiposParametros,Sparametros);
            simboloMetodo=tabla.BuscarM(NombreR);
            this.SimboloMetodo=simboloMetodo;
            
            Metodo=NombreR;
        
        //Crea una nueva tabla de simbolos y coloca la tabla actual como la tabla de arriba y la tabla nueva la pasa hacia las
        //su siguiente instruccion
        TablaDeSimbolos nTabla=new TablaDeSimbolos();
        nTabla.tablaArriba=tabla;
        tabla=nTabla;
        int i;
        Simbolo s1=new Simbolo();
        
        //Insertar por un simbolo(variable) llamada return  del tipo s1.Tipos
        Simbolo return1=new Simbolo();
        
        tabla.InsertarV("return", Tipo, Metodo, null);
        
        Sparametros.add(tabla.BuscarV("return"));
        
        //esta linea es equivalente a la anterior se comentaria
        //tabla.MSizeMetodo(Metodo, s1.TiposPrimitivos(Tipo));  
        
        
        //en la Posicon 0 esta el return
        if(Parametros!=null)
        {
            for(i=0;i<Parametros.size()&&res;i++)
            {
                if(Parametros.get(i)!=null)
                {
                    Parametros.get(i).Metodo=NombreR;
                    
                    Parametros.get(i).Clase=Clase;
                    
                    //Parametros.get(i).Clase1=Clase1;
                    
                    Parametros.get(i).SimboloMetodo=simboloMetodo;
                    
                    Parametros.get(i).tabla=tabla;
                    
                    res=Parametros.get(i).CargarTS();
                    Sparametros.add(tabla.BuscarV(Parametros.get(i).Nombre));

                }
            }
        }
        if(SiguienteInstruccion!=null&&res)
        {
              for(i=0;i<SiguienteInstruccion.size()&&res;i++)
              {
                    SiguienteInstruccion.get(i).tabla=tabla;
                    
                    SiguienteInstruccion.get(i).Metodo=NombreR;
                    SiguienteInstruccion.get(i).SimboloMetodo=simboloMetodo;
                    
                    //SiguienteInstruccion.get(i).Clase1=Clase1;
                    
                    
                    SiguienteInstruccion.get(i).Clase=Clase;
                    res=SiguienteInstruccion.get(i).CargarTS();
              }
              
              tabla.InsertarV("this",Clase, Metodo, null);//Direccion del Objeto Actual.   
              Sparametros.add(tabla.BuscarV("this"));
              
              //esta linea es equivalente a la anterior se comentaria
              //tabla.MSizeMetodo(Metodo, 2);//Direccion del Objeto Actual.   
        }
        if(res)
        {
            Instruccion subArbol;
            subArbol=new Instruccion();
            subArbol.SiguienteInstruccion=SiguienteInstruccion;
            tabla.InsertarSubArbolM(NombreR, subArbol);
        }
    }
        return res;
    }
    
    
    
    public String Generar3Direcciones(){
        String ret=""; 
        String ins="";
        int i;
        this.SimboloMetodo.LabelFin=TablaDeSimbolos.GenerarEtiqueta();        
        for(i=0;i<this.SiguienteInstruccion.size();i++)
        {
            ins=ins+this.SiguienteInstruccion.get(i).Generar3Direcciones();      
        }      
        
        if(this.SimboloMetodo.Nombre.contains("main"))
        {
            Simbolo cl=tabla.BuscarC(SimboloMetodo.Padre);
            
            String ins1="ptr="+this.tabla.posSigG+";\n";
            String ins2="ptk="+cl.Size+";\n";
            ret="void "+this.SimboloMetodo.Nombre+"(){\n"+ins1+ins2+ins+"\n"+this.SimboloMetodo.LabelFin+":\n ; \n "+"}\n";
        }
        
        else{
        ret="void "+this.SimboloMetodo.Nombre+"(){\n"+ins+"\n"+this.SimboloMetodo.LabelFin+":\n ; \n "+"}\n";
        }
        
        
        return ret;    
    }
    
}
