/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterpreteTAC;

import proyectocompiladores2r.Mundo;
import proyectocompiladores2r.Tanque;

/**
 *
 * @author eddytrex
 */
public class Cuadruplo {
    public String Operador;
    public String OP1;
    public String OP2;
    public String Result;
    
    public int Op1Int;
    public int Op2Int;
    public int ResInt;
    
    public double Op1double;
    public double Op2double;
    public double Resdouble;
    
    public boolean Activo=true;
    
    boolean Op1int=false;
    boolean Op2int=false;
    boolean Resint=false;
    
    boolean Op1dou=false;
    boolean Op2dou=false;
    boolean Resdou=false;
    
    boolean OP1S=false;
    boolean OP2S=false;
    public void Convertir(String op)
    {
        
        
        if(op.equals("OP1"))
        {
        try{
         if(OP1==null)
         {
             System.out.println("//err");
         }
         Op1Int=Integer.parseInt(OP1);
         Op1int=true;
        }
        catch(NumberFormatException ex)
        {
        try{
        Op1double=Double.parseDouble(OP1);
        Op1dou=true;
        }
        catch(NumberFormatException ex1)
        {}
        }        
        if(!Op1int&&!Op1dou){OP1S=true;}
        }
        
        if(op.equals("OP2"))
        {
        try{
         Op2Int=Integer.parseInt(OP2);
         Op2int=true;
        }
        catch(NumberFormatException ex)
        {
           try{
           Op2double=Double.parseDouble(OP2);
           Op2dou=true;
           }
           catch(NumberFormatException ex1)
           {}
        }       
        if(!Op2int&&!Op2dou){OP2S=true;}
        }
        if(op.equals("RES"))
        {
        try{
        ResInt=Integer.parseInt(Result);
        Resint=true;
        }
        catch(NumberFormatException ex)
        {
        
        try{
        Resdouble=Double.parseDouble(Result);
        Resdou=true;
        }
        catch(NumberFormatException ex1)
        {}
        }
        }
        
    }
    
    public Cuadruplo(String op1,String op2,String operador,String res)
    {
        OP1=op1;
        OP2=op2;
        Result=res;
        
        
        
        Operador=operador;
        
    }
    
    public boolean EjecutarCuadruplo(GrafoCuadruplos G,Tanque T)
    {
        boolean res=false;
        boolean banderaOP1=false;
        boolean banderaOP2=false;
        boolean banderaSalto=false;
        int val1=0;
        int val2=0;
        int val3=0;
        double val1f=0;
        double val2f=0;
        double val3f=0;
        
        if(Operador.equals("GOTO"))
        {
            NodoCuadruplos temp=G.BuscarEtiqueta(Result); 
            if(temp!=null)
            {
            temp.Ejecutar(G,T);
            res=true;           
            }
        }
        
        if(Operador.equals("CALL"))
        {
           
            NodoCuadruplos temp=G.BuscarEtiqueta(Result); 
            if(temp!=null)
            {temp.Ejecutar(G,T);}
            else
            {
             if(Result.equals("Disparar")){
                 if(G.getStackValor(2)>0){
                 T.disparar(G.getStackValor(G.Tenteros.get("ptr")+2));
             }
             }
             if(Result.equals("Mover"))
             {                 
                 if(G.getStackValor(0)>0)
                 {
                 T.MoverDelante(G.getStackValor(G.Tenteros.get("ptr")+2));
                 }
                 if(T.Activo)
                 {
                     G.setStackValor(6, (int)T.X);
                     G.setStackValor(8, (int)T.Y);
                 }
             }
             if(Result.equals("MoverAtraz")){
                 
                 if(G.getStackValor(0)>0)
                 {
                 T.MoverAtras(G.getStackValor(G.Tenteros.get("ptr")+2));
                 }
                 if(T.Activo)
                 {
                     G.setStackValor(6, (int)T.X);
                     G.setStackValor(8, (int)T.Y);
                 }
             }
             if(Result.equals("Girar")){
                 if(G.getStackValor(0)>0)
                 {
                 T.Girar(G.getStackValor(G.Tenteros.get("ptr")+2));
                 }
                 if(T.Activo)
                 {
                     G.setStackValor(4,(int)T.alpha);
                     G.setStackValor(6, (int)T.X);
                     G.setStackValor(8, (int)T.Y);
                 }
             }
             if(Result.equals("Imprimir")){
                 
                 
                 T.Imprimir(G.getStackValor(G.Tenteros.get("ptr")+2));
                 
             }
            }
            //res=true;           
        }
        
        if(Operador.equals("STACK"))
        {
            if(!G.Tenteros.containsKey(Result)){this.Convertir("Res");val3=Op1Int;}
            else{val3=G.Tenteros.get(Result);}
            
            
            
            if(G.Tenteros.containsKey(OP1)){val1=G.Tenteros.get(OP1);banderaOP1=false;}
            else
            {
                if(G.Tflotantes.containsKey(OP1)){val1f=G.Tflotantes.get(OP1);banderaOP1=true;}
                else
                {
                    this.Convertir("OP1");
                    if(this.Op1int){val1=Op1Int;banderaOP1=false;}
                    if(this.Op1dou){val1f=Op1double;banderaOP1=true;}
                }                
            }          
           
            if(!banderaOP1){G.Stack[val3]=val1;}
            else{G.Stack[val3]=(int)val1f;}
               
        }
        if(Operador.equals("STACKV"))
        {
            if(!G.Tenteros.containsKey(OP1)){this.Convertir("OP1");val1=Op1Int;banderaOP1=false;}
            else{val1=G.Tenteros.get(OP1);banderaOP1=false;}
            
            if(G.Tenteros.containsKey(Result)){banderaOP1=false;}
            if(G.Tflotantes.containsKey(Result)){banderaOP1=true;}
            
            if(!banderaOP1)
            {
                G.Tenteros.put(Result,G.Stack[val1]);
            }
            else{
                G.Tflotantes.put(Result,(double)G.Stack[val1]);
            }            
        }      
        
        if(Operador.equals("HEAP"))
        {
            if(!G.Tenteros.containsKey(Result)){this.Convertir("Res");val3=Op1Int;}
            else{val3=G.Tenteros.get(Result);}
            
            if(G.Tenteros.containsKey(OP1)){val1=G.Tenteros.get(OP1);banderaOP1=false;}
            else
            {
                if(G.Tflotantes.containsKey(OP1)){val1f=G.Tflotantes.get(OP1);banderaOP1=true;}
                else
                {
                    this.Convertir("OP1");
                    if(this.Op1int){val1=Op1Int;banderaOP1=false;}
                    if(this.Op1dou){val1f=Op1double;banderaOP1=true;}
                }                
            }          
               
            if(!banderaOP1){G.Heap[val3]=val1;}
            else{G.Heap[val3]=(int)val1f;}               
        }
        if(Operador.equals("HEAPV"))
        {
            if(!G.Tenteros.containsKey(OP1)){this.Convertir("OP1");val1=Op1Int;banderaOP1=false;}
            else{val1=G.Tenteros.get(OP1);banderaOP1=false;}
            
            if(G.Tenteros.containsKey(Result)){banderaOP1=false;}
            if(G.Tflotantes.containsKey(Result)){banderaOP1=true;}
            
            if(!banderaOP1)
            {
                if(val1>=1000)
                {
                   System.out.println("joder");
                }
                else{
                G.Tenteros.put(Result,G.Heap[val1]);
                }
            }
            else{
                G.Tflotantes.put(Result,(double)G.Heap[val1]);
            }            
        }
        if(Operador.equals("="))
        {            
            if(G.Tenteros.containsKey(OP1)){val1=G.Tenteros.get(OP1);banderaOP1=false;}
            else
            {
                if(G.Tflotantes.containsKey(OP1)){val1f=G.Tflotantes.get(OP1);banderaOP1=true;}
                else
                {
                    this.Convertir("OP1");
                    if(this.Op1int){val1=Op1Int;banderaOP1=false;}
                    if(this.Op1dou){val1f=Op1double;banderaOP1=true;}
                }                
            }
            if(!banderaOP1){G.Tenteros.put(Result,val1);}
            else           {G.Tflotantes.put(Result,val1f);}                
        }
        if(Operador.equals("<")||Operador.equals("<=")||Operador.equals(">")||Operador.equals(">=")||Operador.equals("==")||Operador.equals("!="))
        {
            if(G.Tenteros.containsKey(OP1)){
                val1=G.Tenteros.get(OP1);banderaOP1=false;
            }
            else
            {
                if(G.Tflotantes.containsKey(OP1)){val1f=G.Tflotantes.get(OP1);banderaOP1=true;}
                else
                {
                    this.Convertir("OP1");
                    if(this.Op1int){val1=Op1Int;banderaOP1=false;}
                    if(this.Op1dou){val1f=Op1double;banderaOP1=true;}
                }                
            }          
            if(G.Tenteros.containsKey(OP2)){val2=G.Tenteros.get(OP2);banderaOP1=false;}
            else
            {
                if(G.Tflotantes.containsKey(OP2)){val2f=G.Tflotantes.get(OP2);banderaOP1=true;}
                else
                {
                    this.Convertir("OP2");
                    if(this.Op2int){val2=Op2Int;banderaOP2=false;}
                    if(this.Op2dou){val2f=Op2double;banderaOP2=true;}
                }                
            }          
            
            if(!banderaOP1&&!banderaOP2)
            {
                if(Operador.equals("<")){if(val1<val2){banderaSalto=true;}}
                if(Operador.equals("<=")){if(val1<=val2){banderaSalto=true;}}
                if(Operador.equals(">")){if(val1>val2){banderaSalto=true;}}
                if(Operador.equals(">=")){if(val1>=val2){banderaSalto=true;}
                }
                if(Operador.equals("==")){if(val1==val2){banderaSalto=true;}}
                if(Operador.equals("!=")){if(val1!=val2){banderaSalto=true;}}
            }
            if(banderaOP1&&banderaOP2)
            {
                if(Operador.equals("<")){if(val1f<val2f){banderaSalto=true;}}
                if(Operador.equals("<=")){if(val1f<=val2f){banderaSalto=true;}}
                if(Operador.equals(">")){if(val1f>val2f){banderaSalto=true;}}
                if(Operador.equals(">=")){if(val1f>=val2f){banderaSalto=true;}}
                if(Operador.equals("==")){if(val1f==val2f){banderaSalto=true;}}
                if(Operador.equals("!=")){if(val1f!=val2f){banderaSalto=true;}}
            }
        
            if(banderaSalto){
                NodoCuadruplos temp =G.BuscarEtiqueta(Result); 
                if(temp!=null){
                    temp.Ejecutar(G,T); 
                    res=true;
                }
            }
        }
        
        if(Operador.equals("+")||Operador.equals("-")||Operador.equals("*")||Operador.equals("/")||Operador.equals("%"))
        {
            if(G.Tenteros.containsKey(Result))
            {
                if(!G.Tenteros.containsKey(OP1)){this.Convertir("OP1");val1=Op1Int;}
                else{val1=G.Tenteros.get(OP1);}
               
                if(!G.Tenteros.containsKey(OP2)){this.Convertir("OP2");val2=Op2Int;}
                else{val2=G.Tenteros.get(OP2);}
                
                if(Operador.equals("+")){val3=val1+val2;}
                if(Operador.equals("-")){val3=val1-val2;}
                if(Operador.equals("*")){val3=val1*val2;}
                if(Operador.equals("/")){val3=val1/val2;}
                if(Operador.equals("%")){val3=val1%val2;}
                
                G.Tenteros.put(Result, val3);
            }
            if(G.Tflotantes.containsKey(Result))
            {
                if(!G.Tflotantes.containsKey(OP1)){this.Convertir("OP1");val1f=Op1double;}
                else{val1f=G.Tflotantes.get(OP1);}
               
                if(!G.Tflotantes.containsKey(OP2)){this.Convertir("OP2");val2f=Op2double;}
                else{val2f=G.Tflotantes.get(OP2);}
                
                if(Operador.equals("+")){val3f=val1f+val2f;}
                if(Operador.equals("-")){val3f=val1f-val2f;}
                if(Operador.equals("*")){val3f=val1f*val2f;}
                if(Operador.equals("/")){val3f=val1f/val2f;}
                if(Operador.equals("%")){val3f=val1f%val2f;}
                
                G.Tflotantes.put(Result, val3f);                
            }
        }                        
        if(Operador.equals(";")){}                   //no hacer nada
        return res;
    }
    
    public boolean Optimizar()
    {
        boolean res=false;
        
        if(Operador.equals("+")||Operador.equals("-")||Operador.equals("*")||Operador.equals("/")||Operador.equals("%"))
        {
        this.Convertir("OP1");
        this.Convertir("OP2");
        this.Convertir("Res");
         if(this.Op1int&&this.Op2int)   
                        { 
                            int valtem=0;
                            if(Operador.equals("+")){valtem=this.Op1Int+this.Op2Int;}
                            if(Operador.equals("-")){valtem=this.Op1Int-this.Op2Int;}
                            if(Operador.equals("*")){valtem=this.Op1Int*this.Op2Int;}
                            if(Operador.equals("/")){valtem=this.Op1Int/this.Op2Int;}
                            if(Operador.equals("%")){valtem=this.Op1Int%this.Op2Int;}
                            this.Operador="=";
                            this.OP1=String.valueOf(valtem);
                            res=true;
                        }  
         if(this.Op1int&&OP2S)
         {
             if(this.Op1Int==0&&this.Operador.equals("+")){this.Operador="="; this.Op1int=true; this.OP1=this.OP2;res=true;}
             if(this.Op1Int==0&&this.Operador.equals("-")){this.Operador="="; this.Op1int=true; this.OP1=this.OP2;res=true;}
             if(this.Op1Int==1&&this.Operador.equals("*")){this.Operador="="; this.Op1int=true; this.OP1=this.OP2;res=true;}
         }  //cambiar Cuadruplo
         if(this.Op2int&&OP1S){
             if(this.Op2Int==0&&this.Operador.equals("+")){this.Operador="="; this.Op1int=true; this.OP1=this.OP1;res=true;}
             if(this.Op2Int==0&&this.Operador.equals("-")){this.Operador="="; this.Op1int=true; this.OP1=this.OP1;res=true;}
             if(this.Op2Int==1&&this.Operador.equals("*")){this.Operador="="; this.Op1int=true; this.OP1=this.OP1;res=true;}
         }  //Cmabiar Cuadruplo
         if(this.OP1S&&this.OP2S){
             if(this.OP1.equals(OP2)){
                    if(this.Operador.equals("+")){this.Operador="*";this.OP1="2";this.OP1S=true;res=true;}
                    if(this.Operador.equals("-")){this.Operador="=";this.OP1="0";this.OP1S=true;res=true;}
                   // if(this.Operador.equals("*")){}
                    if(this.Operador.equals("/")){this.Operador="=";this.OP1="1";this.OP1S=true;res=true;}
                    
             }
         }
        }
        return res;
    }
    
    public boolean Equivalencia(Cuadruplo a1)
    {
        boolean res=false;
        if(a1.OP1!=null&&this.OP1!=null)
        if(a1.OP1.equals(this.OP1))
        {
            if(a1.OP2!=null&&this.OP2!=null){
            if(a1.OP2.equals(this.OP2))
            {
                if(a1.Operador.equals(this.Operador))
                {
                res=true;
                }
            }}
        }
        return res;
    }
    public boolean Utilza(Cuadruplo a1)
    {
        boolean res=false;
        if(a1.Result!=null)
        {
        if(!a1.Operador.equals("GOTO")&&!a1.Operador.equals("CALL")&&!a1.Operador.equals("STACKV")&&!a1.Operador.equals("HEAPV"))
        {
            if(this.OP1!=null){if(a1.Result.equals(OP1)){res=true;}}
            if(this.OP2!=null){if(a1.Result.equals(OP2)){res=true;}}
            if(this.Result!=null){if(a1.Result.equals(Result)){res=true;}}
        }
        else{
            res=true;
        }
        }
        
        return res;
    }
    public boolean ModificarValor(Cuadruplo a1)
    {
        boolean res=false;
        if(a1.OP1!=null&&a1.OP2!=null)
        {
         if(this.Result!=null){
         if(this.Result.equals(a1.OP1)){res=true;}
         if(this.Result.equals(a1.OP2)){res=true;}}
        
        }
        return res;
    }
    //Falta
    public boolean VerificarOperador(String a)
    {
        boolean res=false;
        if(this.OP1.equals(a)){res=true;}
        if(this.OP2.equals(a)){res=true;}
        
        return res;
    }
    
    public void SubstituirOP(Cuadruplo a1)
    {
        this.Operador="=";
        this.OP2="";
        this.OP1=a1.Result;
    }
    
    public String RegenerarTAC()
    {
        String res="";
        if(this.Operador.equals("+")||this.Operador.equals("-")||this.Operador.equals("*")||this.Operador.equals("/")||this.Operador.equals("%"))
        {
            
            res=this.Result+"="+this.OP1+this.Operador+this.OP2+";\n";
        }
        if(this.Operador.equals("<")||this.Operador.equals("<=")||this.Operador.equals(">")||this.Operador.equals(">=")||this.Operador.equals("==")||this.Operador.equals("!="))
        {
            res="if ("+this.OP1+this.Operador+this.OP2+") goto "+this.Result+";\n";
        }
        if(this.Operador.equals("="))
        {
            res=this.Result+"="+this.OP1+";\n";
        }
        if(this.Operador.equals("GOTO"))
        {
            res="goto "+this.Result+";\n";
        }
        if(this.Operador.equals("STACK"))
        {
            res="stack["+this.Result+"]="+this.OP1+";\n";
        }
        if(this.Operador.equals("STACKV"))
        {
            res=this.Result+"=stack["+this.OP1+"];\n";
        }
        if(this.Operador.equals("HEAP"))
        {
            res="heap["+this.Result+"]="+this.OP1+";\n";
        }
        if(this.Operador.equals("HEAPV"))
        {
            res=this.Result+"=heap["+this.OP1+"];\n";
        }
        if(this.Operador.equals(";"))
        {
            res=";\n";
        }
        if(this.Operador.equals("CALL"))
        {
            res=this.Result+"();\n";
        }
        return res;
    }
}
