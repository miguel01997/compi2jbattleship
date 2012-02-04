/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompiladores2r;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author eddytrex
 */
public class Bala {
    public int idBala;
    public double X;
    public double Y;
    public double alpha;
    public double velocidad;
    
    public double poder;
    Image jpg;
    
    public Bala(double x,double y,double Aplha)
    {
        X=25+x;
        Y=25+y;
        alpha=Aplha;        
        velocidad=2;
        try
        {
            jpg=ImageIO.read(new File("bala.png"));
        }
        catch(IOException e)
        {
        }
    }
    
    public void mover()
    {
            double inx=Math.cos(Math.toRadians(alpha))*this.velocidad;
            double iny=Math.sin(Math.toRadians(alpha))*this.velocidad;
            
            this.X=this.X+inx;
            this.Y=this.Y+iny;
            /*if(alpha>=0&&alpha<=90)  {this.X=this.X+inx; this.Y=this.Y-iny;}
            if(alpha>90&&alpha<=180){this.X=this.X-inx; this.Y=this.Y-iny;}
            if(alpha>180&&alpha<=270)  {this.X=this.X-inx; this.Y=this.Y+iny;}
            if(alpha>270&&alpha<=360)  {this.X=this.X+inx; this.Y=this.Y+iny;}*/

    }
    
    
    
}
