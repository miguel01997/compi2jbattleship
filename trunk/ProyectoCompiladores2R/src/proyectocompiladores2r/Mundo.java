/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompiladores2r;

import InterpreteTAC.GrafoCuadruplos;
import InterpreteTAC.NodoCuadruplos;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author eddytrex
 */
public class Mundo extends JPanel implements Runnable {
    
    public LinkedList<Tanque> lTanques=new LinkedList();
    
    public Hashtable<String,Tanque>ListaTanqes=new Hashtable();
    public LinkedList<Thread>lth=new LinkedList();
    
    public LinkedList<Integer> lg=new LinkedList();
    Graphics mundo1;
    Image imagen1;
    
    public int DY;
    public int DX;
    
    
    public String salida="";
    
    @Override
    public void run() {
        int i;               
        for(i=0;i<this.lTanques.size();i++)
        {
            lg.add(i);
        }
        for(i=0;i<this.lTanques.size();i++)
        {
            Thread a=new Thread(this.lTanques.get(i));
            lth.add(a);  
        }
        while(true)
        {
            synchronized (this){
                
                    
                    Actualizar();                     
                    this.ControlparedesTanque();
                    this.ActualizarBalas();
                    this.QuitarBalas();
                    this.QuivarVida();
                    this.ColicionTanques();
                    this.Imprimir();
                    this.Ver();
               
               for(i=0;i<this.lTanques.size();i++)
               {
                    Thread a=new Thread(this.lTanques.get(i));
                    lth.add(a);  
                }
            }
            try
            {
                Thread.sleep(20);
                
            }catch(InterruptedException e)
            {                
            }
            for(i=0;i<this.lTanques.size();i++)
            {
            Thread a=new Thread(this.lTanques.get(i));
            lth.add(a);  
            }    
        }
    }
    
    
    public void ControlparedesTanque()
    {
        int i;
        Tanque temp1;
        if(this.lTanques!=null)
        {
        for(i=0;i<this.lTanques.size();i++)
        {
            temp1=this.lTanques.get(i);            
            if(temp1.X<0)  {        
                temp1.X=0;      
                NodoCuadruplos tempc=temp1.Programa.BuscarFuncion("Chocar");
                if(tempc!=null){tempc.Ejecutar(temp1.Programa,temp1);}}
            if(temp1.X>(DX-60)) {   
                temp1.X=DX-70;
                NodoCuadruplos tempc=temp1.Programa.BuscarFuncion("Chocar");
            if(tempc!=null){tempc.Ejecutar(temp1.Programa,temp1);}}
                if(temp1.Y<0)  {        
                temp1.Y=0;      
                NodoCuadruplos tempc=temp1.Programa.BuscarFuncion("Chocar");                                                    
                if(tempc!=null){tempc.Ejecutar(temp1.Programa,temp1);}
            }
            
            if(temp1.Y>(DY-60)) {   
                temp1.Y=DY-70;  
                NodoCuadruplos tempc=temp1.Programa.BuscarFuncion("Chocar");
                if(tempc!=null){tempc.Ejecutar(temp1.Programa,temp1);}
            }      
            
            
            if(!temp1.Activo){if(lg.size()>1){lg.remove(i);}}
        }
        }
    }
    
    
    public void Actualizar()
    {
        int i,j;        
        for(i=0;i<this.lTanques.size();i++)
        {

                        if(this.lTanques.get(i).Activo)
                        {
                                                        
                            //lth.get(i).start();
                            
                            this.lTanques.get(i).EXEC();
                            
                        }
                      //Thread tanque=new Thread(this.lTanques.get(i));
                      //tanque.start();                      
        } 
    }
    public  Mundo(int dx,int dy)
    {
            DX=dx;
            DY=dy;
    }
    public void repaint(Graphics g)
    {
        imagen1=createImage(DX,DY);
        mundo1=imagen1.getGraphics();
        mundo1.setColor(Color.BLUE);
        mundo1.fillRect(0, 0, DX, DY);
        int i,j;
        Tanque tanqueTemporal=null;
        for(i=0;i<this.lTanques.size();i++)
        {
            tanqueTemporal=this.lTanques.get(i);
            
            BufferedImage imagen= new BufferedImage(tanqueTemporal.jpg.getWidth(null),tanqueTemporal.jpg.getHeight(null),BufferedImage.TRANSLUCENT);
            Graphics2D g2d=imagen.createGraphics();
            g2d.drawImage(tanqueTemporal.jpg,null,null);
            AffineTransform transfomrada=new AffineTransform();
            transfomrada.rotate(Math.toRadians(tanqueTemporal.alpha),60/2 ,60/2);
            AffineTransformOp op = new AffineTransformOp(transfomrada,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage buff = op.filter(imagen,null);
            
            mundo1.setColor(Color.yellow);
            mundo1.drawString(tanqueTemporal.nombre,(int)tanqueTemporal.X,(int)tanqueTemporal.Y);
            mundo1.drawString(Integer.toString(tanqueTemporal.Vida),(int)tanqueTemporal.X+60,(int)tanqueTemporal.Y+75);
            mundo1.drawString(Integer.toString(tanqueTemporal.Energia),(int)tanqueTemporal.X+0,(int)tanqueTemporal.Y+75);            
            
            if(lg.size()==1)
            {
             mundo1.setColor(Color.GREEN);
             Font fontAnterior=mundo1.getFont();
             mundo1.setFont(new Font("Serif", Font.BOLD, 20));
             mundo1.drawString("El ganador es "+this.lTanques.get(lg.getFirst()).nombre,this.DX/2-130,DY/2);
             mundo1.setFont(fontAnterior);             
            }
            mundo1.drawImage(buff,(int)tanqueTemporal.X,(int)tanqueTemporal.Y,null);
        }
        Color c=mundo1.getColor();
        mundo1.setColor(Color.GREEN);
        mundo1.drawString(this.salida,0,10);
        mundo1.setColor(c);
        
        Bala tempBala=null;
        Tanque tb;
        for(i=0;i<this.lTanques.size();i++)
        {
            tb=this.lTanques.get(i);
            for(j=0;j<tb.balas.size();j++)
            {
                if(tb.balas.get(j)!=null)
                {
                tempBala=tb.balas.get(j);
                tempBala.jpg.setAccelerationPriority(TOP_ALIGNMENT);
                mundo1.drawImage(tempBala.jpg,(int) tempBala.X,(int)tempBala.Y, null);
                }
            }
        }
        g.drawImage(imagen1, 0, 0, null);
        
        
        repaint();
    }
    
    public void paint(Graphics g)
    {
        repaint(g);        
    }   
    
    public void agregarTanque(Tanque t)
    {
        this.lTanques.add(t);
        this.ListaTanqes.put(t.nombre, t);
    }
    
    public void Imprimir()
    {
        int i;
        for(i=0;i<this.lTanques.size();i++)
        {            
            this.salida=this.lTanques.get(i).nombre+"::"+this.lTanques.get(i).slaidaT;   
        }
    }
    
    public void Ver()
    {
        int i,j;
        int xv=0,yv=0;
        Tanque t1,t2;
        double tx1,tx2,ty1,ty2,yrev;
        
        if(this.lTanques!=null)
        {
            for(i=0;i<this.lTanques.size();i++)
            {
                t1=this.lTanques.get(i);
                
                tx1=t1.X;
                ty1=t1.Y;
                for(j=0;j<this.lTanques.size();j++)
                {
                    t2=this.lTanques.get(j);
                    if(t1!=t2)
                    {
                       tx2=t2.X;
                       ty2=t2.Y;                   
                       if(t1.alpha==90)
                       {
                           if(ty2>=ty1)
                           {
                           if(Math.abs(tx2-tx1)<=60)
                           {
                               //t1.Programa.BuscarFuncion("Detectado").Ejecutar(t1.Programa, t1);
                           }
                           }
                       }
                       if(t1.alpha==270)
                       {
                       
                           if(ty2<=ty1)
                           {
                               if(Math.abs(tx2-tx1)<=60)
                               {
                                 //  t1.Programa.BuscarFuncion("Detectado").Ejecutar(t1.Programa, t1);
                               }
                           }
                       }
                       if((t1.alpha>=0&&t1.alpha<90)||(t1.alpha>270&&t1.alpha<360)){ 
                        if(tx2>=tx1)   
                        {
                            yrev=Math.tan(Math.toRadians(t1.alpha))*tx2-Math.tan(Math.toRadians(t1.alpha))*tx1+ty1;                       
                            if(Math.abs(yrev-ty2)<=60)
                            {
                                t1.Programa.BuscarFuncion("Detectado").Ejecutar(t1.Programa, t1);
                            }
                        }
                       }
                       if((t1.alpha>90&&t1.alpha<270))
                       {
                           if(tx2<=tx1)
                           {
                               yrev=Math.tan(Math.toRadians(t1.alpha))*tx2-Math.tan(Math.toRadians(t1.alpha))*tx1+ty1;                       
                               if(Math.abs(yrev-ty2)<=60)
                               {
                                   t1.Programa.BuscarFuncion("Detectado").Ejecutar(t1.Programa, t1);
                               }
                           }
                       }
                    }
                }
            }
            
            
            
        }
    
    }
    
    public void ColicionTanques()
    {
        int i,j;
        Tanque t1,t2;
        double r,dx,dy;
        if(this.lTanques!=null)
        {
            for(i=0;i<this.lTanques.size();i++)
            {
                t1=this.lTanques.get(i);
                for(j=0;j<this.lTanques.size();j++)
                {
                    t2=this.lTanques.get(j);
                    if(t2!=t1)
                    {                    
                     r=56;
                     dx=t1.X-t2.X;
                     dy=t1.Y-t2.Y;
                     if(r*r>=(dx*dx)+(dy*dy))
                     {
                         t1.X=t1.X+Math.cos(Math.toRadians(180+t1.alpha))*10;
                         t1.Y=t1.Y+Math.sin(Math.toRadians(180+t1.alpha))*10;
                         t2.X=t2.X+Math.cos(Math.toRadians(180+t2.alpha))*10;
                         t2.Y=t2.Y+Math.sin(Math.toRadians(180+t2.alpha))*10;
                         NodoCuadruplos tempc=t1.Programa.BuscarFuncion("Chocar");
                         if(tempc!=null){tempc.Ejecutar(t1.Programa,t1);}
                         
                         NodoCuadruplos tempc2=t2.Programa.BuscarFuncion("Chocar");
                         if(tempc!=null){tempc2.Ejecutar(t2.Programa,t2);}
                         
                         
                         break;
                         
                     }
                    }
                }
                
            }
        }
    
    }
    
    public void ActualizarBalas()
    {
           int i,j;
           if(this.lTanques!=null)
           {
           for(i=0;i<this.lTanques.size();i++)
           {               
               if(this.lTanques.get(i).balas!=null)
               {
               for(j=0;j<this.lTanques.get(i).balas.size();j++)
               {
                   this.lTanques.get(i).balas.get(j).mover();
               }
               }
           }
           }
    }
    
    public void QuivarVida()
    {
        int i,j,k;
        LinkedList<Bala> BT;
        Bala bl;
        Tanque tT,tT1;
        if(this.lTanques!=null)
           {
           for(i=0;i<this.lTanques.size();i++)
           {
               tT1=lTanques.get(i);
               BT=tT1.balas;
               if(BT!=null)
               {
               for(j=0;j<BT.size();j++)
               {
                bl=BT.get(j);
                for(k=0;k<this.lTanques.size();k++)
                {
                  tT=this.lTanques.get(k);
                  if(tT1!=tT)
                  {
                   if(bl.X>tT.X&&bl.X<tT.X+60)
                   {
                       if(bl.Y>tT.Y&&bl.Y<tT.Y+60)
                       {
                           tT.DisminuirVida((int)bl.poder);
                           tT1.balas.remove(bl);                           
                       }
                   }
                   
                  }
                  
                }
               }
               }
               lTanques.get(i).balas=BT;
           }
           }
    }
    
   
    public void QuitarBalas()
    {
        int i,j;
        LinkedList<Bala> BT;
        Bala bl;
           if(this.lTanques!=null)
           {
           for(i=0;i<this.lTanques.size();i++)
           {
               BT=lTanques.get(i).balas;
               if(BT!=null)
               {
               for(j=0;j<BT.size();j++)
               {
                   bl=BT.get(j);
                   if(bl.X<0&&bl.X>=this.DX)
                   {
                       BT.remove(j);
                   }
                   if(bl.Y<0&&bl.Y>=this.DY)
                   {
                       BT.remove(j);
                   }
               }
               }
               lTanques.get(i).balas=BT;
           }
           }
    }
    
}