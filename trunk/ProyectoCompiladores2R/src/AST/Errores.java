/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AST;

import java.util.LinkedList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eddytrex
 */
public class Errores
{
  public static LinkedList<String> Errores;
  public static int NE;
  public static boolean Cargar=false;
  public static boolean Generar3D=false;

  public static void InicializarTablaDeErrores()
  {
    Errores = new LinkedList();
    NE = 1;
  }

  public static void ClearErrorrs()
  {
      Errores=new LinkedList();
      NE=1;
  
  }
  public static void InsertarError(int Nolinea, String Error)
  {
    String Ma = "<td>" + Integer.toString(NE) + "</td>" + "\n";
    String a = "<td>" + Integer.toString(Nolinea) + "</td>" + "\n";
    String b = "<td>" + Error + "</td>" + "\n";

    String c = "<tr>" + a + b + "</tr>" + "\n";

    Errores.add(c);
    NE += 1;
  }

  public static void publicarErrores(String NombreErrores)
  {
    FileWriter fw = null;
    try
    {
      String Encabezado = "</Head>\n <Title>Errores</Title> \n</Head>\n";
      String Salida = "";
      int i;
      for ( i= 0; i < Errores.size(); i++)
      {
        Salida = Salida + (String)Errores.get(i);
      }
      Salida = "<table border=\"1\">\n" + Salida + "\n" + "</table>";
      Salida = "<HTML>\n" + Encabezado + Salida + "</HTML>" + "\n";

      fw = new FileWriter(NombreErrores);

      BufferedWriter bw = new BufferedWriter(fw);

      for (i = 0; i < Salida.length(); i++)
      {
        if (Salida.charAt(i) != '\n')
        {
          bw.write(Salida.charAt(i));
        }
        else
        {
          bw.newLine();
        }
      }

      bw.close();
      fw.close();
    }
    catch (IOException ex)
    {
      Logger.getLogger(Errores.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        fw.close();
      } catch (IOException ex) {
        Logger.getLogger(Errores.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}