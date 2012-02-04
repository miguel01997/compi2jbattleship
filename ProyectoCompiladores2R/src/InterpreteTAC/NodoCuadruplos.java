/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterpreteTAC;

import java.util.Hashtable;
import java.util.LinkedList;
import proyectocompiladores2r.Tanque;

/**
 *
 * @author eddytrex
 */
public class NodoCuadruplos {
    public String Label;
    public String Procedimiento;
    public boolean BProc;
    public LinkedList<Cuadruplo> lCuadruplo;
    
    
    public LinkedList<NodoCuadruplos> LNodoCuadruplos;
    
    
    public Hashtable<String,String> Reemplazo;
    
    public NodoCuadruplos NSigueinte1=null;
    public NodoCuadruplos NSiguiente2=null;
    
    
    public LinkedList<NodoCuadruplos> NNodoCuadruplos;
    
    public LinkedList<String> NameL;
    
    public String  OptimizarSC(int bandera)
    {
        String res="";
        String resL="";
        String resf="";
        NodoCuadruplos temp=null;
        int i,j,k;
        if(BProc)
        {          
            if(this.LNodoCuadruplos!=null)
            {      
                if(this.LNodoCuadruplos.size()>0)
                {
                 resf=this.LNodoCuadruplos.get(0).OptimizarSC(bandera);
                }
            }
        }
        else
        {
            boolean b=false,b2=false;
            Cuadruplo tC1,tC2;
            
             if(this.lCuadruplo!=null)
             {
                for(i=0;i<this.lCuadruplo.size();i++)
                {            
                    tC1=this.lCuadruplo.get(i);                    
                    for(j=i;j<this.lCuadruplo.size();j++)
                    {
                        tC2=this.lCuadruplo.get(j);
                        if(tC2!=tC1)
                        {
                            tC2.Optimizar();
                            tC1.Optimizar();
                           
                            if(tC2.ModificarValor(tC1)){b=true;}
                            
                            
                            if(tC1.Equivalencia(tC2)&&!b)
                            {
                                tC2.SubstituirOP(tC1);
                            }
                            
                        } 
                    }                    
                   
                }
                if(bandera==1){}
                else{}
            }
            if(!b){
                if(this.NSigueinte1!=null)
                {
                 this.NSigueinte1.OptimizarSC(0);
                }
            }
        }
        return res;
    }

    
    
    
    public String  RegenerarTAC(int bandera)
    {
        String res="";
        String resL="";
        String resf="";
        NodoCuadruplos temp=null;
        int i;
        if(BProc)
        {
            
            if(this.LNodoCuadruplos!=null)
            {      
                if(this.LNodoCuadruplos.size()>0)
                {
                    resf=this.LNodoCuadruplos.get(0).RegenerarTAC(bandera);
                    resf="void "+this.Procedimiento+"()\n{\n"+resf+"\n}\n";
                    res=resf;
                }
            }
        }
        else
        {
            boolean b=false,b2=false;
            if(this.lCuadruplo!=null)
            {
            for(i=0;i<this.lCuadruplo.size()&&!b;i++)
            {            
                resL=resL+this.lCuadruplo.get(i).RegenerarTAC();
                //b=this.lCuadruplo.get(i).EjecutarCuadruplo(G,T);               
            }
            if(bandera==1){
                resL=resL;
            }
            else{resL=this.Label+":\n"+resL;}
            res=resL;
            }
            else{res=this.Label+":\n";}
            if(!b){
                if(this.NSigueinte1!=null)
                {
                 res=res+this.NSigueinte1.RegenerarTAC(0);
                }
            }
        }
        return res;
    }
    
    public NodoCuadruplos Ejecutar(GrafoCuadruplos G,Tanque T)
    {
        NodoCuadruplos res=null;
        NodoCuadruplos temp=null;
        int i;
        if(BProc)
        {
            
            if(this.LNodoCuadruplos!=null)
            {      
                if(this.LNodoCuadruplos.size()>0)
                {
                    this.LNodoCuadruplos.get(0).Ejecutar(G,T);
                }
            }
        }
        else
        {
            boolean b=false,b2=false;
            if(this.lCuadruplo!=null)
            {
            for(i=0;i<this.lCuadruplo.size()&&!b;i++)
            {            
                b=this.lCuadruplo.get(i).EjecutarCuadruplo(G,T);               
            }
            }
            if(!b){
                if(this.NSigueinte1!=null)
                {
                this.NSigueinte1.Ejecutar(G,T);
                }
            }
            res=this;
        }
        return res;
    }
    
    public boolean ConectarNodos(GrafoCuadruplos G)
    {
        NameL=new LinkedList();
        
        boolean res=true;
        int i;        
        if(BProc)
        {
            if(this.LNodoCuadruplos!=null)
            {
                for(i=0;i<this.LNodoCuadruplos.size()-1;i++)
                {
                    this.LNodoCuadruplos.get(i).NSigueinte1=this.LNodoCuadruplos.get(i+1);
                }
               
            }
        }
        if(!BProc)
        {
                String OperadorT="";
                Cuadruplo cuadruploT=null;
                NodoCuadruplos nTemporal=null;
                if(this.lCuadruplo!=null)
                {
                for(i=0;i<this.lCuadruplo.size();i++)
                {
                    cuadruploT=lCuadruplo.get(i);
                    OperadorT=cuadruploT.Operador;
                    if(OperadorT.equals("GOTO"))
                    {
                        nTemporal=G.BuscarEtiqueta(cuadruploT.Result);
                        if(nTemporal!=null)
                        {
                            this.NSigueinte1=nTemporal;
                        }                        
                    }
                    if(OperadorT.equals("<")||OperadorT.equals("<=")||OperadorT.equals(">")||OperadorT.equals(">=")||OperadorT.equals("==")||OperadorT.equals("!="))
                    {
                        nTemporal=G.BuscarEtiqueta(cuadruploT.Result);
                        if(nTemporal!=null)
                        {
                           this.NSiguiente2=nTemporal;
                        }
                    }                    
                }
                }
        }
        
        return res;
    }
    
    
   
    
    public NodoCuadruplos(LinkedList<Cuadruplo> L, String label,boolean Bproc)
    {
        LNodoCuadruplos=new LinkedList();
        lCuadruplo=new LinkedList();
        if(Bproc)
        {
            BProc=Bproc;
            Procedimiento=label;
        }
        else
        {
            BProc=Bproc;
            Label=label;
        }
        lCuadruplo=L;
    }
    
    public NodoCuadruplos(LinkedList<Cuadruplo> L,boolean Bproc)
    {
        LNodoCuadruplos=new LinkedList();
        lCuadruplo=new LinkedList();
        if(Bproc)
        {
            BProc=Bproc;            
        }
        else
        {
            BProc=Bproc;            
        }
        lCuadruplo=L;
    }
    
    public NodoCuadruplos(LinkedList<NodoCuadruplos> L, String label)
    {
        LNodoCuadruplos=new LinkedList();
        lCuadruplo=new LinkedList();
        this.BProc=true;
        this.Procedimiento=label;
        LNodoCuadruplos=L;
    }
    public NodoCuadruplos(LinkedList<Cuadruplo> L )
    {
        lCuadruplo=L;
        L.addFirst(null);
    }
}