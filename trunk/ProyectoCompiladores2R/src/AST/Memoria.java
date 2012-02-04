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
public class Memoria implements Serializable{
    static int ptrP;
    static int ptrH;

    static public LinkedList <String> Stack;
    static public LinkedList <String> Heap;
    
    public static String GetPilaRT(int prel)
    {
        String res="stack["+Integer.toString(prel)+"]";
        return res;
    }
    
    public static String GetHeapRT(int prel)
    {
        String res="heap["+Integer.toString(prel)+"]";
        return res;
    }
    
    public static String GetPilaRTS(String var)
    {
        String res="stack["+var+"]";
        return res;
    }
    
    public static String GetHeapRTS(String var)
    {
        String res="heap["+var+"]";
        return res;
    }
    
    public static void Memoria(int sP,int sH)
   {
    ptrP=0;
    ptrH=0;
    Stack=new LinkedList();
    Heap=new LinkedList();

    
    int i=0;
    
    for(i=0;i<sP;i++)
    {
        Stack.add("");
    }
    
    for(i=0;i<sH;i++)
    {
        Heap.add("");
    }
    
    for(i=0;i<sP;i++)
    {
    Stack.set(i, "");
    }
    
    for(i=0;i<sH;i++)
    {
    Heap.set(i, "");
    }
    i=0;
   }

   public static  boolean MovePtrP(int p)
    {
        boolean res=false;
        int posR=ptrP+p;
        if(posR<Stack.size()&&posR>0)
        {
            ptrP=posR;
            res=true;
        }
        return res;
    }

    public static  boolean MovePtrH(int p)
    {
            boolean res=false;
        int posR=ptrH+p;
        if(posR<Heap.size()&&posR>0)
        {
            ptrH=posR;
            res=true;
        }
        return res;


    }

   public static boolean SetHeap(int Pos,String Valor)
   {
      boolean res=false;
      Heap.set(Pos, Valor);
      return res;
   }

   public static String GetHeap(int Pos)
    {
        String res=null;
        res=Heap.get(Pos);
        
        return res;
   }

   public static boolean SetPilaR(int pos,String Valor)
   {
     boolean res=false;
     int posR=ptrP+pos;

     if(posR<Stack.size())
     {
         Stack.set(posR, Valor);
         res=true;
     }
     return res;
   }

   public static String GetPilaR(int pos)
   {
       String res="";
       int posR=ptrP+pos;
       if(posR<Stack.size())
       {
            res=Stack.get(posR);
       }
       return res;
   }


   public static String GetPilaG(int pos)
   {
       String res="";
       int posR=pos;
       if(posR<Stack.size())
       {

        res=Stack.get(posR);
       }
       return res;
   }

   public static boolean SetPilaG(int pos, String Valor)
   {
        boolean res=false;
        int posR=pos;
        if(pos<Stack.size())
        {
            Stack.set(posR, Valor);
            res=true;
        }
        return res;
   }

    
   

}
