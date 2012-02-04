/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterpreteTAC;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author eddytrex
 */
public class GrafoCuadruplos {
  public String NombrePrograma;
  public Hashtable<String,NodoCuadruplos>  G=new Hashtable();
  
  
  
  public  Hashtable<String,Integer> Tenteros=new Hashtable();
  public  Hashtable<String,Double> Tflotantes=new Hashtable();
  
  public  int Stack[];
  public  int Heap[];
  
  
  
  public void IniciarlizarValores(int e,int v,int xi,int yi)
  {
        this.setStackValor(0, e);
        this.setStackValor(2, v);
        this.setStackValor(6, xi);
        this.setStackValor(8, yi);
  }
  
  public void setStackValor(int i,int valor)
  {
        Stack[i]=valor;
  }
  public void setHeapValor(int i,int valor)
  {
        Heap[i]=valor;
  }
  public int getStackValor(int i)
  {
      int res=0;
      res=Stack[i];
      return res;
  }
  
  public int getHeapValor(int i)
  {
      int res=0;
      res=Heap[i];
      return res;
  }
  
  public void DECTI(String a)
  { 
        int t=0;
        Tenteros.put(a, t);
  }
  
  public void DECTF(String a)
  {
        double t=0;
        Tflotantes.put(a, t);
  }
  
  public void DECstack(String  size)
  {
      int a=10000;
      a=Integer.parseInt(size);
        Stack=new int[a];        
  }
  
  public void DECheap(String  size)
  {
        int a=10000;
        a=Integer.parseInt(size);
        Heap=new int[a];
  }
  
  public void add(NodoCuadruplos a)
  {
      if(a.BProc){G.put(a.Procedimiento,a);}
      else{G.put(a.Label, a);}
  }
  public NodoCuadruplos BuscarEtiqueta(String Label)
  {
      NodoCuadruplos res=null;
      if(G.containsKey(Label))
      {
          res=G.get(Label);
      }
      return res;
  }
  
  public void ConectarNodos()
  {
      NodoCuadruplos temporal=null;
      int i=0,j=0;
      for(i=0;i<G.size();i++)
      {
          temporal=(NodoCuadruplos) G.values().toArray()[i];
          if(temporal!=null)
          {
          
              temporal.ConectarNodos(this);
              
              for(j=0;j<temporal.NameL.size();j++)
              {
                  NodoCuadruplos temp2=this.BuscarEtiqueta(temporal.NameL.get(j));
                  temporal.NNodoCuadruplos.add(temp2);                  
              }
          }
          
      
      }  
  }
  public NodoCuadruplos BuscarFuncion(String Buscar)
  {
      NodoCuadruplos res=null,temp=null;
      int i;
        for(i=0;i<this.G.size();i++)
        {
            temp=(NodoCuadruplos)this.G.values().toArray()[i];
            if(temp.BProc&&temp.Procedimiento.contains(Buscar))
            {
                res=temp;
            }
        }
      return res;
  }
  
  public void OptimizarSB()
  {
      NodoCuadruplos res=null,temp=null;
      int i;
        for(i=0;i<this.G.size();i++)
        {
            temp=(NodoCuadruplos)this.G.values().toArray()[i];
            if(temp.BProc)
            {
            temp.OptimizarSC(1);
            }
        }
      
  }
  
  public String RegenerarTAC()
  { 
      String res="",resI="",resF="";
      String ss="",sh="";
      NodoCuadruplos temp;
      int i;
        for(i=0;i<this.G.size();i++)
        {
            temp=(NodoCuadruplos)this.G.values().toArray()[i];
            if(temp.BProc)
            {
            res=res+temp.RegenerarTAC(1);
            }
        }
        for(i=0;i<this.Tenteros.size();i++)
        {
            if(i==0)
            {resI=(String)this.Tenteros.keySet().toArray()[i];}
            else
            {resI=resI+","+(String)this.Tenteros.keySet().toArray()[i];}
        }    
        if(this.Tflotantes.size()>0)
        {
         for(i=0;i<this.Tflotantes.size();i++)
        {
            if(i==0)
            {resF=(String)this.Tflotantes.keySet().toArray()[i];}
            else
            {resF=resF+","+(String)this.Tflotantes.keySet().toArray()[i];}
        }
         resF="float "+resF+";\n";
        }
        ss="int stack["+this.Stack.length+"];\n";
        sh="int heap["+this.Heap.length+"];\n";
        resI="int "+resI+";\n";
        res=resI+resF+ss+sh+res;
      return res;
  }
  
  public void EscribirArchivo(String NombreDireccion,String Salida)
    {
        FileWriter fw = null;
        try {
            fw = new FileWriter(NombreDireccion+"_OP.c");
            fw.write(""+Salida);
            fw.close();
        } catch (IOException ex) {
            
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                //Logger.getLogger(Optimizacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
