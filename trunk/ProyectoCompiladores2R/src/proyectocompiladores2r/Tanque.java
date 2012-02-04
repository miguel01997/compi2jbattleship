/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompiladores2r;

import InterpreteTAC.GrafoCuadruplos;
import InterpreteTAC.NodoCuadruplos;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eddytrex
 */
public class Tanque implements Runnable{    
    public String nombre;
    public double X;      //pos x
    public double Y;      //pos y
    public double alpha; //angoulo
    public int Energia;
    public int Vida;
    public Image jpg;   
    
    public String slaidaT;
    public boolean Activo;    
    
    public GrafoCuadruplos Programa;
    
    public Thread tt;
    public LinkedList<Bala> balas=new LinkedList();
    
    public Tanque(String Nombre,Image imagen,GrafoCuadruplos g)
    {
        this.Energia=100;
        this.Vida=100;
        this.Programa=g;
        Random xr=new Random();
        Random yr=new Random();
        X=xr.nextDouble()*500;
        Y=yr.nextDouble()*500;
        nombre=Nombre;
        
        jpg=imagen;
        this.Activo=true;
        g.IniciarlizarValores(this.Vida,this.Energia,(int)X,(int)Y);
       
        tt=new Thread(this);
    }    
    
    public Tanque(String Nombre,Image imagen,double x,double y,double Alpha,GrafoCuadruplos g)
    {
        this.Energia=100;
        this.Vida=100;
        Random xr=new Random();
        Random yr=new Random();
        X=x;
        Y=y;
        alpha=Alpha;
        nombre=Nombre;        
        jpg=imagen;
        this.Activo=true;   
        this.Programa=g;
        g.IniciarlizarValores(this.Vida,this.Energia,(int)X,(int)Y);
        tt=new Thread(this);
    }    
    
    
    public void AddBala(Bala b)
    {   
        balas.add(b);
    }
    
    public void MoverDelante(double s) 
    {
        int i;
        if(this.Activo)
        {
            for(i=0;i<s;i++)
            {
            double inx=Math.cos(Math.toRadians(alpha))*1;
            double iny=Math.sin(Math.toRadians(alpha))*1;
            this.X=this.X+inx;
            this.Y=this.Y+iny;
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tanque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            /*if(alpha>=0&&alpha<=90)  {this.X=this.X+inx; this.Y=this.Y-iny;}
            if(alpha>90&&alpha<=180){this.X=this.X-inx; this.Y=this.Y-iny;}
            if(alpha>180&&alpha<=270)  {this.X=this.X-inx; this.Y=this.Y+iny;}
            if(alpha>270&&alpha<=360)  {this.X=this.X+inx; this.Y=this.Y+iny;}*/
        }    
    }
    
    public void MoverAtras(double s)
    {
        if(this.Activo)
        {
            double inx=Math.cos(Math.toRadians(alpha))*s;
            double iny=Math.sin(Math.toRadians(alpha))*s;
            this.X=this.X+inx;
            this.Y=this.Y+iny;
            try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Tanque.class.getName()).log(Level.SEVERE, null, ex);
                }
            /*if(alpha>=0&&alpha<=90)    {this.X=this.X-inx; this.Y=this.Y-iny;}
            if(alpha>90&&alpha<=180)   {this.X=this.X+inx; this.Y=this.Y-iny;}
            if(alpha>180&&alpha<=270)  {this.X=this.X+inx; this.Y=this.Y+iny;}
            if(alpha>270&&alpha<=360)  {this.X=this.X-inx; this.Y=this.Y+iny;}*/
        }    
    }
    
    public void Imprimir(int a)
    {
        this.slaidaT=Integer.toString(a);
    }
    public void Girar(double beta )
    {
        if(this.Activo)
        {
            this.alpha=this.alpha+beta;
            if(this.alpha>=360){this.alpha=this.alpha-360;}
            if(this.alpha<0){this.alpha=this.alpha+360;}
            
        }
    }
    
    public void disparar(double energia)
    {
        if(this.Activo)
        {
            Bala bl=new Bala(X,Y,this.alpha);
            bl.poder=energia;        
            this.AddBala(bl);            
            this.Energia=this.Energia-(int)energia;
            Programa.setStackValor(2, Energia);
        }
    }
    
    public void DisminuirVida(int enr)
    {
        if(this.Activo)
        {
            this.Vida=this.Vida-enr;
            Programa.setStackValor(0, this.Vida);
            if(Programa.getStackValor(0)<=0)                
            {this.Activo=false;}
        }
    }
    
    
    public void Printf(String s)
    {
        this.slaidaT=this.nombre+"_"+s;
    }
    
    public boolean actualizar()
    {
        //if(this.Energia<=0){this.Activo=false; return false;}
        //if(this.Energia>0){this.Activo=true; return true;}
        return true;
    }
    
    public void InsertarAlgulo()
    {
        
        Programa.setStackValor(4, (int)this.alpha);
    }
    public void InsertarPosicionT()
    {
        Programa.setStackValor(6,(int)this.X);
        Programa.setStackValor(8,(int)this.Y);
    }   
    
    public void EXEC()
    {
        if(this.Programa.getStackValor(0)>0)
            {    
            //NodoCuadruplos Ejecutar = this.Programa.BuscarEtiqueta("RobotdeEjemplo_main_").Ejecutar(Programa, this);     
            this.Programa.BuscarFuncion("main").Ejecutar(Programa, this);
            }
    }

    @Override
    public void run() {               
            if(this.Programa.getStackValor(0)>0)
            {    
            //NodoCuadruplos Ejecutar = this.Programa.BuscarEtiqueta("RobotdeEjemplo_main_").Ejecutar(Programa, this);     
                this.Programa.BuscarFuncion("main").Ejecutar(Programa, this);
            }
         }
    
    
           
}
