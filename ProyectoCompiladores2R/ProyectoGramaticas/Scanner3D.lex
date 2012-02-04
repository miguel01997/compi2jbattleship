package proyectocompiladores2r;
import java_cup.runtime.Symbol;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import InterpreteTAC.*;
%%
%line
%cup
%full  // para que tenga los 255 caracteres del ascii
%state CCOMMENT
%{


private boolean Debug=false;
public int Columna=0;
public void MostrarMensaje(String mn)
{
	if(!Debug){System.out.println(mn);}
}

private Symbol tok(int k,Object value)
{
	return new Symbol(k,yyline,Columna,value);
}

private void ReporteErroesLexicos(String  val)
{

}

%}

%public
%class Scanner3D
%implements java_cup.runtime.Scanner
%function next_token
%type java_cup.runtime.Symbol

%eofval{

MostrarMensaje("Fin de Archivo");
return new Symbol(Symbolos3D.EOF);

%eofval}

%line

letra=[a-zA-Z]
digito=[0-9]
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
%%

<YYINITIAL>{letra}({letra}|{digito}|"_")*  {
				    String a= new String(yytext());			
				    Columna=a.length()+Columna;
				    
				    
				    
				    if(a.compareTo("if")==0)   {return tok(Symbolos3D.IF,a);    }
				    if(a.compareTo("return")==0){return tok(Symbolos3D.RETURN,a); }
				    if(a.compareTo("goto")==0){return tok(Symbolos3D.GOTO,a); }
				    

				    if(a.compareTo("int")==0)  {return tok(Symbolos3D.INT,a);  }
				    if(a.compareTo("float")==0)  {return tok(Symbolos3D.FLOAT,a);  }
				    
				    if(a.compareTo("void")==0)  {return tok(Symbolos3D.VOID,a);  }

				    if(a.compareTo("stack")==0)  {return tok(Symbolos3D.STACK,a);  }
				    if(a.compareTo("heap")==0)  {return tok(Symbolos3D.HEAP,a);  }

				     
				    //aqui iria un starwith o revisar el primier caracter 
				    if(a.contains("L")){return tok(Symbolos3D.LABEL,a);}

				    
				    return tok(Symbolos3D.ID,a);
				}
<YYINITIAL>{digito}({digito})*		{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.ENTERO,a);
				}


<YYINITIAL>"."			{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PUNTO,a);
				}
<YYINITIAL>","				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.COMA,a);
				}

<YYINITIAL>";"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PCOMA,a);
				}

<YYINITIAL>":"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.DPUNTOS,a);
				}


<YYINITIAL>"["				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.CA,a);
				}

<YYINITIAL>"]"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.CC,a);
				}


<YYINITIAL>"{"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.LLA,a);
				}
<YYINITIAL>"}"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.LLC,a);
				}
<YYINITIAL>"("				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PA,a);
				}

<YYINITIAL>")"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PC,a);
				}

<YYINITIAL>"=="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				      System.out.println("..."+a);
				     return tok(Symbolos3D.IGUAL,a);
				}

<YYINITIAL>"!="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.NOIGUAL,a);
				}

<YYINITIAL>"<"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MENORQ,a);
				}

<YYINITIAL>">"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MAYORQ,a);
				}

<YYINITIAL>"<="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MENORIGUAL,a);
				}

<YYINITIAL>">="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MAYORIGUAL,a);
				}


<YYINITIAL>"="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     
				     return tok(Symbolos3D.ASIGNA,a);
				}

<YYINITIAL>"+"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MAS,a);
				}

<YYINITIAL>"-"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MENOS,a);
				}


<YYINITIAL>"*"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.POR,a);
				}

<YYINITIAL>"/"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.DIV,a);
				}
<YYINITIAL>"%"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MOD,a);
				}

<YYINITIAL>" "				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				}

<YYINITIAL>"'"{letra}"'" 	   {
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     a=a.substring(1, a.length()-1);
				
				     return tok(Symbolos3D.CARACTER,a);
				   }
<YYINITIAL> "/*"			        {yybegin(CCOMMENT); } 
<CCOMMENT> .		 		       	{yybegin(CCOMMENT);}  
<CCOMMENT> "*/"					{yybegin(YYINITIAL);} 
						

<YYINITIAL>"//"({digito}|{letra}|.)*\r\n	{Columna=0;}

<YYINITIAL>"//"({digito}|{letra}|.)*\n		{Columna=0;}

<YYINITIAL>"//"({digito}|{letra}|.)*$		{Columna=0;}



\r\n				{
				 Columna=0;
				}

\t                              {Columna++;}

(\r|\n|\r\n)[\n\r\t" "]+	{Columna++;}

\n				{Columna=0;}


<YYINITIAL>.				{
				   Columna++;
				   //Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   
				   return new Symbol(Symbolos3D.error); 
				}


