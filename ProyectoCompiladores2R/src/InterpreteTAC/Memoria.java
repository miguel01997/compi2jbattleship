/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package InterpreteTAC;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Eddy
 */
public class Memoria implements Serializable{
   public  int ptrP;
   public  int ptrH;
   
   
   public LinkedList <Integer> Stack=new LinkedList();
   public LinkedList <Integer> Heap=new LinkedList();
}
