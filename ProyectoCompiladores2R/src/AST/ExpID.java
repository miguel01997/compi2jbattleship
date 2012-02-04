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
public class ExpID extends Exp{
    public String Variable;
    
    public LinkedList<Exp> Indices;

    public String NombreMetodo;
    public LinkedList<Exp> Parametros;

    public ExpID subExpID;
    
    public boolean Stack;
    public boolean Heap;
    
    public boolean Global;
    
    public boolean NEW;
    
    
    

    public ExpID(int Nolinea,ExpID subEx,String variable,LinkedList<Exp> indices,boolean metodo) //Variable o metodo con Linea
    {
       
        subExpID=subEx; 
        NoLinea=Nolinea;
        if(metodo)
        {
            NombreMetodo=variable;
            Parametros=indices;
            TablaDeSimbolos.InsertBreakPoint(NoLinea);
            TablaDeSimbolos.DisableBreakPoint(NoLinea);
        }
        else{
        Variable=variable;
        Indices=indices;
        
        }
       
    }
    
    public ExpID(ExpID subEx,String variable,LinkedList<Exp> indices,boolean metodo) //Variable o metodo
    {
        subExpID=subEx;
        if(metodo)
        {
            NombreMetodo=variable;
            Parametros=indices;
         
        }
        else{
        Variable=variable;
        Indices=indices;
        
        }
        
    }
    
    public boolean EjecutarParametros()
    {//Ejecuta las expreciones de los  parametros que lleva el metodo
        int i;
        boolean res=true;
        if(Parametros!=null)
        {
            for(i=0;i<Parametros.size()&&res;i++)
            {
                if(Parametros.get(i)!=null)
                {
                    res=Parametros.get(i).Ejecutar();
                    
                }
            }
        }
        return res;
    }

    public void EjecutarIndices()
    {//Ejecuta las exprecinoes en los indices
      
      int i;
      if(Indices!=null)
      {
	for(i=0;i<Indices.size();i++)
	{	
	    if(Indices.get(i)!=null)
	    {
		Indices.get(i).Ejecutar();
                
	    }
	}
      }
      
    }
    
    public int PosLinealArreglos(LinkedList<Integer> dim)
    {
        int res=0;
        int i,X;
        boolean bandera=true;
        if(dim.size()==Indices.size())
        {
        for(i=0;i<dim.size()&&bandera;i++)
        {
        X=Integer.parseInt(Indices.get(i).Valor);
        if(X>=0&&X<dim.get(i))
        {
            res=res*dim.get(i)+X;
        }
        else{bandera=false;}//Error en las dimesiones declaradas y las usadas
        }
        if(!bandera){res=0;}
        }
        return res;
    }

    public String NombreRealMetodo()
    {//Obtiene el nombre Real del Metodo  Ejemplo: Suma_int_int
        String res="";
        int i;
        if(Parametros!=null)
        {
        for(i=0;i<Parametros.size();i++)
        {
            res=res+"_"+Parametros.get(i).tipos.Tipos.get(0);
        }
        }
        res=NombreMetodo+"_"+res;
        return res;
    }
    
    public boolean EjecutarFuncion()
    {
        int i;
        boolean res=true;
        Simbolo s=null;
        Simbolo s1=null;
        Simbolo cl=null;
        boolean bandera=true;
        int DireccionNuevoObjeto=0;
        int DireccionArgumento=0;
        if(TablaDeSimbolos.Debug)
        {
            if(Debug(tabla))
            {
                
            }
            else
            {
                Return=true;
                bandera=false;
               
                return true;
            }
        }
        // Atributo Metodo es Metodo Actual
        s1=tabla.BuscarM(Metodo); // esto seria reemplazado por SimboloMetodo que es el simbolo del metodo actual
        
        //Constructor 
        if(Variable==null&&NEW&&NombreMetodo!=null)    //Nuevo Objeto
        {
             s=tabla.BuscarM(NombreMetodo+"_"+NombreRealMetodo());
             if(s!=null)
             {
             cl=tabla.BuscarC(NombreMetodo); //Buscar clase, los constructores tiene el mismo nombre de la clase
             this.tipos.Add(NombreMetodo);   //el tipo es el nombre de la clase 
             this.Valor=Integer.toString(Memoria.ptrH); //el valor la direccion del puntero del Heap
             DireccionNuevoObjeto=Memoria.ptrH;
             Memoria.MovePtrH(cl.Size); //Mueve el puntero hacia una direccion vacia en este caso PtrH+size(clase)
                                        //para el siguiete objeto  
             }
             else{} //Error No existe Constructor
        }
        if(Variable==null&&NombreMetodo!=null&&subExpID==null) 
        {
            
            if ((this.NombreMetodo.equals("leer")) || (this.NombreMetodo.equals("escribir")) || (this.NombreMetodo.equals("salir")))
            {
            
            }
            else
            {}    
            
            //Buscar el Simbolos del los Metodos Globales, osea sin clase  
            s=tabla.BuscarM("_"+NombreRealMetodo()); 
            if(s==null)
            {
                //Busca el simbolo del metodo de la clase acutal  Ejemplo: clase1_metodo1_int_int
                s=tabla.BuscarM(Clase+"_"+NombreRealMetodo(),Clase); 
            }
          }
            if (Variable==null&&NombreMetodo!=null&&subExpID!=null)//hay sub Expreciones 
            {
              if(subExpID.tipos.Tipos.size()==1)
                {
                 String ClaseTipo=subExpID.tipos.Tipos.get(0);
                
                 //Busca el Simbolo del Metodo del tipo(Clase) anterior
                s=tabla.BuscarM(ClaseTipo+"_"+NombreRealMetodo(),ClaseTipo);
            }
            }
        
            if(s==null){} //Error no encuentra el simbolo
            else //Encuentra el simbolo
            {    
                //Ejecuta las expreciones de los parametros del metodo 
                res=EjecutarParametros();
                if(res)
                {
                if(s.Argumentos.size()==Parametros.size())
                {
                    /*Coloca los parametros en tamaño del metodo acutal Size(Metodo)+PosRel(De cada argumento)*/

                    for(i=0;i<s.Argumentos.size();i++)
                    {
                        if(Parametros.get(i).tipos.Buscar(s.Argumentos.get(i).Tipo))
                        {
                            
                            DireccionArgumento=s1.Size+s.Argumentos.get(i).PosRel;
                                                                 //Valor del resultado del Parametro
                            Memoria.SetPilaR(DireccionArgumento, Parametros.get(i).Valor);
                            
                        
                        }
                        
                    }
                    //La direccion al objeto en cuestion seria Size(metodoActual)+Size(MetodoALLamar)-2
                    if(Variable==null&&Metodo!=null&&subExpID!=null)
                    {    // buscar la varaible this del metodo a llamar.... en este caso s 
                            DireccionArgumento=s.Size+s1.Size-2;
                            Memoria.SetPilaR(DireccionArgumento,subExpID.Valor);
                    }
                    if(Variable==null&&Metodo!=null&&NEW) // si es un constructor coloca la direccion Nueva en la posicion Size(metodoActual)+Size(MetodoALLamar)-2
                    {
                            DireccionArgumento=s.Size+s1.Size-2;
                            Memoria.SetPilaR(DireccionArgumento,Integer.toString(DireccionNuevoObjeto));
                    }
                    
                }
                }
                // revisar los simbolos de los parametros con su tipo y colocarlos Size(medotoActual)+Simbolo.PosRel;
                
                
                
                // Mueve el apuntador el tamaño del metodo el tamaño del metodo acual 
                Memoria.MovePtrP(s1.Size);
                s.SubArbol.Ejecutar(); //Ejecuta el sub Arbol
                // Regresa el apuntado el tamñao del metodo el taño del metodo acutal
                Memoria.MovePtrP(0-s1.Size);
            }
        return res;
        

    }
    
    
    
    public void EjecutarID()
    {
        Simbolo s=null;
        int Linealizado=0;
        //Simbolo mas a la Izquierda de la cadena de Id
        if(Variable!=null&&NombreMetodo==null&&subExpID==null)
        {   
            // Busca Variables en el Metodo Acutal
            s=tabla.BuscarV(Variable);
            Simbolo temp=null;
            if(s==null)
            {//Busca en Atributos del Objeto Actual
                s=tabla.BuscarA(Clase+"_"+Variable, Clase);
            }
            if(s==null)
            {//Busca en Variables Globales
                s=tabla.BuscarVG(Variable);
            }
            if(s==null){} //Error: No hay un simbolo
            else
            {
                if(s.Variable){    //el valor esta en la direccion relativa de simbolo;
                                    if(s.Dimenciones!=null&&Indices!=null)
                                    {
                                        EjecutarIndices();
                                        Linealizado=PosLinealArreglos(s.Dimenciones);
                                    }
                                    Direccion=s.PosRel+Linealizado*ConjuntoTipos.SizePrimitivos(s.Tipo);
                                    tipos.Add(s.Tipo);
                                    Valor=Memoria.GetPilaR(Direccion);
                                    Stack=true;

                                }
                if(s.Atributo){     
                                    // El valor esta en la direccion del objeto acual mas la posicion relativa;
                                    Simbolo sT=tabla.BuscarM(Metodo);
                                    
                                    //int DireccionObjetoAcutal=Integer.parseInt(Memoria.GetPilaR(sT.Size-2));
                                    int DireccionObjetoAcutal=tabla.BuscarV("this").PosRel;
                                    if(s.Dimenciones!=null&&Indices!=null)
                                    {
                                        EjecutarIndices();
                                        Linealizado=PosLinealArreglos(s.Dimenciones);
                                    }
                                    Direccion=DireccionObjetoAcutal+s.PosRel+Linealizado;
                                    tipos.Add(s.Tipo);
                                    Valor=Memoria.GetHeap(Direccion);
                                    Heap=true;
                                }
                if(s.Global){       //El Valor esta en la posicion global en la Pila 
                                    if(s.Dimenciones!=null&&Indices!=null)
                                    {
                                        EjecutarIndices();
                                        Linealizado=PosLinealArreglos(s.Dimenciones);
                                    }
                                    Direccion=s.PosGlo+Linealizado;
                                    tipos.Add(s.Tipo);
                                    Valor=Memoria.GetPilaG(Direccion);
                                    Stack=true;
                                    Global=true;
                                }
            }
        }
        
        //Atributo el objeto
        if(Variable!=null&&NombreMetodo==null&&subExpID!=null)
        {
            String ClaseTemp="";
            if(subExpID.tipos.Tipos.size()==1)
            {
                ClaseTemp=subExpID.tipos.Tipos.getFirst();
                // Nombre Real del Atributo en la tabla de Simbolos Ejemplo: clase1_atributo1
                s=tabla.BuscarA(ClaseTemp+"_"+Variable, ClaseTemp);

            if(s!=null)
            {
                if(s.Primitivo()){
                    // El valor de la posicion en la subExp + el valor relativo en la tabla de simbolos
                    if(s.Dimenciones!=null&&Indices!=null)
                                    {
                                        EjecutarIndices();
                                        Linealizado=PosLinealArreglos(s.Dimenciones);
                                    }
                    Direccion=Integer.parseInt(subExpID.Valor)+s.PosRel+Linealizado;
                    tipos.Add(s.Tipo);
                    Valor=Memoria.GetHeap(Direccion);
                    Heap=true;
                }
                else{
                    //El Valor de la posicion de la subExp + el valor relativo de la tabla de simbolos
                    if(s.Dimenciones!=null&&Indices!=null)
                                    {
                                        EjecutarIndices();
                                        Linealizado=PosLinealArreglos(s.Dimenciones);
                                    }
                    Direccion=Integer.parseInt(subExpID.Valor)+s.PosRel+Linealizado;
                    tipos.Add(s.Tipo);
                    Valor=Memoria.GetHeap(Direccion);
                    Heap=true;
                }
            }
            }
        }

    }


    public boolean Ejecutar()
    {
        boolean res=true;
        if(subExpID!=null)
        {
            //Hasta llegar al Elemento mas a la Izquierda
            subExpID.Ejecutar();
        }
        else
        {
            // Si son Atributos o Variables Locales o Globales
            if(Variable!=null){EjecutarID();}             
            // Si son Metodos o Procedimientos
            if(NombreMetodo!=null){EjecutarFuncion();} 
        }
        return res;
    }
    

    public boolean CargarTS()
    {
        boolean res=true;
        int i;
        if(Indices!=null)
        {
            for(i=0;i<Indices.size();i++)
            {
                if(Indices.get(i)!=null)
                {
                
                Indices.get(i).tabla=tabla;
                Indices.get(i).SimboloMetodo=SimboloMetodo;
                Indices.get(i).Clase=Clase;

                //Indices.get(i).Clase1=Clase1;
                Indices.get(i).Metodo=Metodo;
                
                res=Indices.get(i).CargarTS();
                }
            }
        }
        if(Parametros!=null&&res)
        {
            for(i=0;i<Parametros.size();i++)
            {
                if(Parametros.get(i)!=null)
                {
                Parametros.get(i).tabla=tabla;
                Parametros.get(i).Metodo=Metodo;
                
                Parametros.get(i).Clase=Clase;
                //Parametros.get(i).Clase1=Clase1;
                
                Parametros.get(i).SimboloMetodo=SimboloMetodo;
                
                res=Parametros.get(i).CargarTS();
                }
            }
        }
        if(subExpID!=null&&res)
        {
            subExpID.Metodo=Metodo;
            subExpID.tabla=tabla;
            
            //subExpID.Clase1=Clase1;
            subExpID.SimboloMetodo=SimboloMetodo;
            
            subExpID.CargarTS();
        }
        return res;
    }
    
    public String Generar3Direcciones()
    {
        String ret=""; 
        String CSub="";
        Simbolo sVariable=null;
        Simbolo sAtributo=null;
        String temp,temp1="",temp2,temp3,temp4,temp5,temp6,temp7="",temp8="",temp9,temp10,temp11;
        String tempthis1,tempthis,tempthisV="",tempR="",tempPRV="",tempRV="";
        
        String ins01="",ins0="",ins1="",ins2="",ins3="",ins4="",ins5="",ins6="",ins7="",ins8="",ins9="",ins10="",ins11="",ins12="",ins13="",ins14,ins15;
        if((this.NombreMetodo==null)&&!this.Variable.equals(""))
        {
            if(this.subExpID!=null)
            {
                CSub=subExpID.Generar3Direcciones();
                if(!subExpID.tipos.SearchPrimitivo())
                {
                   sAtributo=tabla.BuscarA(subExpID.tipos.Tipos.getFirst()+"_"+Variable,subExpID.tipos.Tipos.getFirst());
                   if(sAtributo!=null)
                   {
                       if(sAtributo.Primitivo())
                       {
//                           String ins1,ins2,ins3;
                           //String temp1,temp2,temp3;
                           temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                           ins1=temp1+"="+subExpID.Valor+"+"+Integer.toString(sAtributo.PosRel)+";\n";
                           
                           temp2=TablaDeSimbolos.GenerarVariableTempora("int");                           
                           ins2=temp2+"="+Memoria.GetHeapRTS(temp1)+";\n";
                           
                           ret=CSub+ins1+ins2;
                           
                           this.Dir=temp1;
                           this.Valor=temp2;
                           this.tipos.Add(sAtributo.Tipo);
                           this.Heap=true;
                       }
                       else{
//                           String ins1,ins2,ins3;
                          // String temp1,temp2,temp3;
                           
                           temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                           ins1=temp1+"="+subExpID.Valor+Integer.toString(sAtributo.PosRel)+";\n";
                           
                           temp2=TablaDeSimbolos.GenerarVariableTempora("int");
                           ins2=temp2+"="+Memoria.GetHeapRTS(temp1)+";\n";
                           
                           ret=CSub+ins1+ins2;
                           this.Dir=temp1;
                           this.Valor=temp2;
                           this.tipos.Add(sAtributo.Tipo);
                           this.Heap=true;
                       }
                   }               
                }               
            }
            if(this.subExpID==null)
            {            
                sVariable=tabla.BuscarV(Variable);
                if(sVariable!=null)
                {
                    if(sVariable.Primitivo())
                    {
                    if(sVariable.Tipo.equals("int")||sVariable.Tipo.equals("char")||sVariable.Tipo.equals("boolean"))
                    {
//                        String ins1="",ins2="";
//                        String temp1="",temp2;
                        
                        
                        temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                        temp2=TablaDeSimbolos.GenerarVariableTempora("int");
                        
                        ins1=temp1+"="+"ptr+"+Integer.toString(sVariable.PosRel)+";\n";
                        
                        ins2=temp2+"="+Memoria.GetPilaRTS(temp1)+";\n";
                        
                        ret=ins1+ins2;                     
                    
                        
                        this.Valor=temp2;
                        
                        
                        this.tipos.Add(sVariable.Tipo);
                        
                        this.Dir=temp1;
                        
                        if(sVariable.Referencia)
                        {
                          temp3=TablaDeSimbolos.GenerarVariableTempora("int");
                          ins3=temp3+"="+Memoria.GetPilaRTS(temp2)+";\n";
                          this.Valor=temp3;
                          this.Dir=temp2;
                          ret=ins1+ins2+ins3;
                        }
                        this.Stack=true;
                    }
                    }
                    else
                    {
                        temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                        this.Valor=TablaDeSimbolos.GenerarVariableTempora("int");
                        
                        ins1=temp1+"=ptr+"+Integer.toString(sVariable.PosRel)+";\n";
                        ret=ins1+this.Valor+"="+Memoria.GetPilaRTS(temp1)+";\n";
                        
                        this.Dir=temp1;                       
                        this.Stack=true;
                        
                        this.tipos.Add(sVariable.Tipo);
                    }
                }
                if(sVariable==null)
                {
                    sAtributo=tabla.BuscarA(Clase+"_"+this.Variable, Clase);
                    if(sAtributo!=null)
                    {
//                       String ins1,ins2,ins3;
//                       String temp1,temp2;
                       temp=TablaDeSimbolos.GenerarVariableTempora("int");
                       ins1=temp+"="+Memoria.GetPilaRT(tabla.BuscarV("this").PosRel)+";\n";
                       
                       temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                       ins2=temp1+"="+temp+"+"+sAtributo.PosRel+";\n";                       
                       
                       
                       this.Valor=TablaDeSimbolos.GenerarVariableTempora("int");
                       
                       ins3=this.Valor+"="+Memoria.GetHeapRTS(temp1)+";\n";                       
                       ret=ins1+ins2+ins3;                                                                   
                       
                       this.Dir=temp1;                       
                       this.Heap=true;
                       
                       this.tipos.Add(sAtributo.Tipo);       
                    }
                    else//Busco en los Globales
                    {
                        sVariable=tabla.BuscarVG(this.Variable);
                        if(sVariable!=null)
                        {
                        temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                        ins1=temp1+"="+Memoria.GetPilaRTS(Integer.toString(sVariable.PosGlo))+";\n";
                        ret=ins1;
                        this.Valor=temp1;
                        this.Dir=Integer.toString(sVariable.PosGlo);
                        this.Stack=true;
                        this.tipos.Add(sVariable.Tipo);
                        }
                    }
                    
                }
                
            }
             if(this.Indices!=null)
                {
                    if(this.Indices.size()>0)
                    {
                        String temp1i="",temp2i,temp3i,temp4i,temp5i;
                        String ins1i="",ins2i="",ins3i="",ins4i,ins5i;
                        String a;
                        int in;
                        if((sAtributo!=null&&sVariable==null))
                        {
                            if(sAtributo.Dimenciones.size()==this.Indices.size())
                            {
                                if(this.Indices.get(0)!=null)
                                {
                                    ins1i=this.Indices.get(0).Generar3Direcciones();
                                    temp1i=this.Indices.get(0).Valor;
                                }
                                for(in=0;in<this.Indices.size()-1;in++)
                                {
                                    if(this.Indices.get(in)!=null)
                                    {
                                           ins1i=ins1i=this.Indices.get(in+1).Generar3Direcciones();
                                           
                                           temp2i=TablaDeSimbolos.GenerarVariableTempora("int");
                                           
                                           ins2i=temp2i+"="+temp1i+"*"+sAtributo.Dimenciones.get(in+1)+";\n";                                           
                                           ins3i=ins3i+ins2i;
                                           
                                           temp1i=TablaDeSimbolos.GenerarVariableTempora("int");
                                           ins4i=temp1i+"="+temp2i+"+"+this.Indices.get(in+1).Valor+";\n";
                                           ins3i=ins3i+ins4i;
                                           
                                    }
                                }
                                temp3i=TablaDeSimbolos.GenerarVariableTempora("int");
                                ins5i=temp3i+"="+temp1+"+"+temp1i+";\n";
                                temp4i=TablaDeSimbolos.GenerarVariableTempora("int") ;
                                //Falta Componer el Valor
                                //
                                this.Valor=temp4i;
                                String sf="";
                                if(this.Stack)
                                {   
                                 sf=temp4i+"="+Memoria.GetPilaRTS(temp3i)+";\n";
                                }
                                if(this.Heap)
                                {
                                 sf=temp4i+"="+Memoria.GetHeapRTS(temp3i)+";\n";
                                }
                                this.Dir=temp3i;
                                ret="\n"+ret+ins3i+ins5i+sf;
                            }
                            else{} //Errror 
                        }
                        else if (sAtributo==null&&sVariable!=null)
                        {
                            if(sVariable.Dimenciones.size()==this.Indices.size())
                            {
                                if(this.Indices.get(0)!=null)
                                {
                                    ins1i=this.Indices.get(0).Generar3Direcciones();
                                    temp1i=this.Indices.get(0).Valor;
                                }
                                for(in=0;in<this.Indices.size()-1;in++)
                                {
                                    if(this.Indices.get(in)!=null)
                                    {
                                           ins1i=ins1i=this.Indices.get(in+1).Generar3Direcciones();
                                           
                                           temp2i=TablaDeSimbolos.GenerarVariableTempora("int");
                                           
                                           ins2i=temp2i+"="+temp1i+"*"+sVariable.Dimenciones.get(in+1)+";\n";                                           
                                           ins3i=ins3i+ins2i;
                                           
                                           temp1i=TablaDeSimbolos.GenerarVariableTempora("int");
                                           ins4i=temp1i+"="+temp2i+"+"+this.Indices.get(in+1).Valor+";\n";
                                           ins3i=ins3i+ins4i;
                                           
                                    }
                                }
                                temp3i=TablaDeSimbolos.GenerarVariableTempora("int");
                                ins5i=temp3i+"="+temp1+"+"+temp1i+";\n";
                                
                                temp4i=TablaDeSimbolos.GenerarVariableTempora("int") ;
                                //Falta Componer el Valor
                                //
                                
                                this.Valor=temp4i;
                                String sf=temp4i+"="+Memoria.GetPilaRTS(temp3i)+";\n";
                                this.Dir=temp3i;
                                ret="\n"+ret+ins3i+ins5i+sf;
                            }
                            else{} //Errror 
                        }
                        
                        
                    }
                }            
        }
        else{
        if(!this.NombreMetodo.equals("")&&(this.Variable==null))
        {
            String CSubM="";
            String CodigoParametros="";
            String nMetodo="";
            Simbolo SimboloM=null;
            int i=0;
            if(this.Parametros!=null)
            {
            for(i=0;i<this.Parametros.size();i++)
            {
                 CodigoParametros=CodigoParametros+this.Parametros.get(i).Generar3Direcciones();
            }
            }
            if(this.subExpID!=null)
            {
                CSubM=this.subExpID.Generar3Direcciones();                
                nMetodo=subExpID.tipos.Tipos.getFirst()+"_"+this.NombreRealMetodo();
                //es casi igual al anetriror solo que this ya tiene un valor en subExpID.Valor
                SimboloM=tabla.BuscarM(nMetodo);
                if(SimboloM!=null)
                {
                    //String ins1,ins2,ins3,ins4,ins5,ins6,ins7,ins8,ins9,ins10,ins11,ins12,ins13,ins14,ins15;
//                    String temp1,temp2,temp3,temp4,temp5,temp6,temp7,temp8,temp9,temp10,temp11;
                    
                    temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                    
                    //Posicion para colocar los parametros despues del metodo actual
                    
                    ins1=temp1+"= ptr +"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    CodigoParametros=CodigoParametros+ins1;
                    for(i=0;i<this.Parametros.size();i++)
                    {  
                        if(SimboloM.Argumentos.get(i+1).Referencia)
                        {
                        
                        }
                        temp2=TablaDeSimbolos.GenerarVariableTempora("int");
                        ins2=temp2+"="+temp1+"+"+SimboloM.Argumentos.get(i+1).PosRel+";\n";
                        
                        if(SimboloM.Argumentos.get(i+1).Referencia&&this.Parametros.get(i).getClass().getCanonicalName().equals("AST.ExpID"))
                        {
                            ins3=Memoria.GetPilaRTS(temp2)+"="+this.Parametros.get(i).Dir+";\n";
                        }
                        else
                        {
                        ins3=Memoria.GetPilaRTS(temp2)+"="+this.Parametros.get(i).Valor+";\n";
                        }
                        CodigoParametros=CodigoParametros+ins2+ins3;                        
                    }                                                                        
                
                   temp4=TablaDeSimbolos.GenerarVariableTempora("int");
                   ins5=temp4+"="+temp1+"+"+SimboloM.Argumentos.get(i+1).PosRel+";\n";
                   
                   temp5=TablaDeSimbolos.GenerarVariableTempora("int");
                   ins6=temp5+"= ptr +"+Integer.toString(tabla.BuscarV("this").PosRel)+";\n";
                   
                   temp6=TablaDeSimbolos.GenerarVariableTempora("int");
                   ins7=temp6+"="+Memoria.GetPilaRTS(temp5)+";\n";
                   
                   ins8=Memoria.GetPilaRTS(temp5)+"="+temp6+";\n";
                                            
                   ins9="ptr = ptr+"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                   ins10=nMetodo+"();\n";
                   ins11="ptr = ptr-"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                   
                   temp7=TablaDeSimbolos.GenerarVariableTempora("int");
                   ins12=temp7+"= ptr +"+Integer.toString(this.SimboloMetodo.Size) +";\n";
                   
                   temp8=TablaDeSimbolos.GenerarVariableTempora("int");
                   
                   
                   //cambiar por la posicion relativa del return 
                   ins13=temp8+"="+temp7+"+"+"0"+";\n";
                   
                   temp9=TablaDeSimbolos.GenerarVariableTempora("int");
                   ins14=temp9+"="+Memoria.GetPilaRTS(temp8) +";\n";
                   
                   ret=CSubM+"\n"+CodigoParametros+"\n"+ins5+ins6+ins7+ins8+"\n"+ins9+ins10+ins11+"\n"+ins12+ins13+ins14;
                           
                   this.Valor=temp9;
                   this.Dir=temp8;
                   
                   this.Stack=true;
                   
                   this.tipos.Add(SimboloM.Tipo);
                }
                else{} //Metodo No Encontrado
            }
            else
            {
                if(this.NEW)
                {                
                 nMetodo=this.NombreMetodo+"_"+this.NombreRealMetodo();
                 SimboloM=tabla.BuscarM(nMetodo);
                 Simbolo SimboloC=tabla.BuscarC(this.NombreMetodo);
                 if(SimboloC!=null)
                 {
                 tempthisV=TablaDeSimbolos.GenerarVariableTempora("int");
                 ins0=tempthisV+"=ptk;\n";
                 ins01="ptk=ptk+"+Integer.toString(SimboloC.Size)+";\n";
                 }
                 if(SimboloM!=null)
                 {
                    temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                    ins1=temp1+"= ptr +"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    CodigoParametros=CodigoParametros+ins1;
                    if(this.Parametros!=null)
                    {
                    //ins1=temp1+"= ptr +"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    
                    for(i=0;i<this.Parametros.size();i++)
                    {
                    
                    temp2=TablaDeSimbolos.GenerarVariableTempora("int");
                    
                    //t(n+1)=tn+posicionParametro i
                    ins2=temp2+"="+temp1+"+"+SimboloM.Argumentos.get(i+1).PosRel+";\n";                    
                    //ins2=temp2+"="+temp1+"+"+ConjuntoTipos.SizePrimitivos(Parametros.get(i).tipos.Tipos.getFirst())+";\n";                    
                    
                    
                    //Pila[t(n+1)]=tn-1  valor de la expresion
                    if(SimboloM.Argumentos.get(i+1).Referencia&&this.Parametros.get(i).getClass().getCanonicalName().equals("ExpID"))
                    {
                     ins3=Memoria.GetPilaRTS(temp2)+"="+this.Parametros.get(i).Dir+";\n";
                    }
                    else{
                    ins3=Memoria.GetPilaRTS(temp2)+"="+this.Parametros.get(i).Valor+";\n";
                    }
                    
                    
                    CodigoParametros=CodigoParametros+ins2+ins3;
                        
                    }
                    }
                    
                    tempthis=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempthis=tn+posiscionthis  del siguienteMetodo
                    ins1=tempthis+"="+temp1+"+"+SimboloM.Argumentos.get(i+1).PosRel+";\n";                   
                    
                    
                    //tempthis1=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempthis1=ptr+posicionthis del Meotod Actual
                    //ins2=tempthis1+"=ptr + "+Integer.toString(tabla.BuscarV("this").PosRel)+";\n";                   
                  
          
                    //tempthisV=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempVthis=Pila[tempthis1]
                    //ins3=tempthisV+"="+Memoria.GetPilaRTS(tempthis1)+";\n";
                    
                    //Pila[tempthis]=tempVthis
                    ins4=Memoria.GetPilaRTS(tempthis)+"="+tempthisV+";\n";
                    
                    //ptr=ptr+sizeMetodoActual()
                    ins5="ptr=ptr+"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    
                    //llamada al medoto metodo
                    ins10=nMetodo+"();\n";
                    
                    
                    //ptr=ptr-sizeMetodoActual()
                    ins6="ptr=ptr-"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    
                    
                    tempR=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempR=ptr+sizemetodoActual()    
                    ins7=tempR+"=ptr+"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    
                    /*                   
                    tempPRV=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempPRV=tempR+posicionVariableReturn 
                    ins8=tempPRV+"="+tempR+"+0;\n";
                            
                    tempRV=TablaDeSimbolos.GenerarVariableTempora("int");
                   //tempRV=PIla[tempR]
                    ins9=tempRV+"="+Memoria.GetPilaRTS(tempPRV)+";\n";
                    */
                    ret="\n"+ins0+ins01+CodigoParametros+ins1+"\n"+ins2+ins3+ins4+"\n"+ins5+ins10+ins6+"\n"+ins7+ins8+ins9;
                    this.Valor=tempthisV;
                    this.Dir=tempPRV;
                    
                    this.Heap=true;
                    
                    this.tipos.Add(SimboloC.Nombre);
                 
                 }               
                }
                else{
                temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                boolean bMG=false;
                nMetodo=this.Clase+"_"+this.NombreRealMetodo();
                SimboloM=tabla.BuscarM(nMetodo);
                if(SimboloM==null){bMG=true;SimboloM=tabla.BuscarM("_"+this.NombreRealMetodo());}
                if(SimboloM!=null)
                {
//                    String ins1,ins2,ins3,ins4,ins5,ins6,ins7,ins8,ins9,ins10;
//                    String temp1,temp2,temp3,tempthis1,tempthis,tempthisV,tempR,tempPRV,tempRV;
                    
                    temp1=TablaDeSimbolos.GenerarVariableTempora("int");
                    
                    //tn=ptr+sizeMetdoAcutal; para el metodo siguiente
                    ins1=temp1+"= ptr +"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    CodigoParametros=CodigoParametros+ins1;
                    for(i=0;i<this.Parametros.size();i++)
                    {
                    
                    temp2=TablaDeSimbolos.GenerarVariableTempora("int");
                    
                    //t(n+1)=tn+posicionParametro i
                    ins2=temp2+"="+temp1+"+"+SimboloM.Argumentos.get(i+1).PosRel+";\n";                    
                    //ins2=temp2+"="+temp1+"+"+ConjuntoTipos.SizePrimitivos(Parametros.get(i).tipos.Tipos.getFirst())+";\n";                    
                    
                    
                    //Pila[t(n+1)]=tn-1  valor de la expresion
                    if(SimboloM.Argumentos.get(i+1).Referencia&&this.Parametros.get(i).getClass().getCanonicalName().equals("ExpID"))
                    {
                     ins3=Memoria.GetPilaRTS(temp2)+"="+this.Parametros.get(i).Dir+";\n";
                    }
                    else{
                    ins3=Memoria.GetPilaRTS(temp2)+"="+this.Parametros.get(i).Valor+";\n";
                    }
                    
                    
                    CodigoParametros=CodigoParametros+ins2+ins3;
                        
                    }
                    if(!bMG)  {
                    tempthis=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempthis=tn+posiscionthis  del siguienteMetodo
                    ins1=tempthis+"="+temp1+"+"+SimboloM.Argumentos.get(i+1).PosRel+";\n";                   
                    
                    
                    tempthis1=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempthis1=ptr+posicionthis del Meotod Actual
                    ins2=tempthis1+"=ptr + "+Integer.toString(tabla.BuscarV("this").PosRel)+";\n";                   
                  
          
                    tempthisV=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempVthis=Pila[tempthis1]
                    ins3=tempthisV+"="+Memoria.GetPilaRTS(tempthis1)+";\n";
                    
                    //Pila[tempthis]=tempVthis
                    ins4=Memoria.GetPilaRTS(tempthis)+"="+tempthisV+";\n";
                    }
                    //ptr=ptr+sizeMetodoActual()
                    ins5="ptr=ptr+"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    
                    //llamada al medoto metodo
                    if(!bMG)
                    {
                    ins10=nMetodo+"();\n";
                    }
                    else
                    {
                     ins1="";ins2="";ins3="";ins4="";
                    ins10=this.NombreMetodo+"();\n";
                    }
                    
                    
                    //ptr=ptr-sizeMetodoActual()
                    ins6="ptr=ptr-"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    
                    if(!SimboloM.Tipo.equals("void")){
                    
                    tempR=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempR=ptr+sizemetodoActual()    
                    ins7=tempR+"=ptr+"+Integer.toString(this.SimboloMetodo.Size)+";\n";
                    
                                       
                    tempPRV=TablaDeSimbolos.GenerarVariableTempora("int");
                    //tempPRV=tempR+posicionVariableReturn 
                    ins8=tempPRV+"="+tempR+"+0;\n";
                            
                    tempRV=TablaDeSimbolos.GenerarVariableTempora("int");
                   //tempRV=PIla[tempR]
                    ins9=tempRV+"="+Memoria.GetPilaRTS(tempPRV)+";\n";
                    }
                    ret="\n"+CodigoParametros+ins1+"\n"+ins2+ins3+ins4+"\n"+ins5+ins10+ins6+"\n"+ins7+ins8+ins9;
                    this.Valor=tempRV;
                    this.Dir=tempPRV;
                    
                    this.Stack=true;
                    
                    this.tipos.Add(SimboloM.Tipo);
                }
                else{}//Metodo No Encontrado
            }
            }
        }
        }
            
        return ret;
    }
    
}
