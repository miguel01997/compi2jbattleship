package proyectocompiladores2r;
import java_cup.runtime.Symbol;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import AST.*;
%%
%line
%cup
%full
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
%class ScannerProyecto
%implements java_cup.runtime.Scanner
%function next_token
%type java_cup.runtime.Symbol

%eofval{

MostrarMensaje("Fin de Archivo");
return new Symbol(SymbolosProyecto.EOF);

%eofval}

%line

letra=[a-zA-Z]
digito=[0-9]
COMMENT_TEXT=([^/*\n]|[^*\n]"/"[^*\n]|[^/\n]"*"[^/\n]|"*"[^/\n]|"/"[^*\n])*
%%

<YYINITIAL>{letra}({letra}|{digito}|"_")*  {
				    String a= new String(yytext());			
				    Columna=a.length()+Columna;
				    if(a.compareTo("class")==0){return tok(SymbolosProyecto.CLASS,a);}
				    if(a.compareTo("private")==0){return tok(SymbolosProyecto.PRIVADO,a);}
				    if(a.compareTo("protected")==0){return tok(SymbolosProyecto.PROTEGIDO,a);}
				    if(a.compareTo("public")==0){return tok(SymbolosProyecto.PUBLICO,a);}
				    if(a.compareTo("import")==0){return tok(SymbolosProyecto.IMPORT,a);}
				    
				    
				    if(a.compareTo("if")==0)   {return tok(SymbolosProyecto.IF,a);    }
				    if(a.compareTo("else")==0) {return tok(SymbolosProyecto.ELSE,a);  }
				    if(a.compareTo("for")==0)  {return tok(SymbolosProyecto.FOR,a);   }
				    if(a.compareTo("while")==0){return tok(SymbolosProyecto.WHILE,a); }
				    if(a.compareTo("return")==0){return tok(SymbolosProyecto.RETURN,a); }

				    if(a.compareTo("switch")==0) {return tok(SymbolosProyecto.SWITCH,a);  }
				    if(a.compareTo("case")==0) {return tok(SymbolosProyecto.CASE,a);  }
                                    if(a.compareTo("break")==0) {return tok(SymbolosProyecto.BREAK,a);}
				    if(a.compareTo("default")==0) {return tok(SymbolosProyecto.DEFAULT,a);}


				    if(a.compareTo("int")==0)  {return tok(SymbolosProyecto.INT,a);  }
				    if(a.compareTo("string")==0) {return tok(SymbolosProyecto.STRING,a); }
				    if(a.compareTo("char")==0) {return tok(SymbolosProyecto.CHAR,a); }      
				    if(a.compareTo("float")==0) {return tok(SymbolosProyecto.FLOAT,a); }   
				    if(a.compareTo("boolean")==0) {return tok(SymbolosProyecto.BOOL,a); }
				    if(a.compareTo("void")==0) {return tok(SymbolosProyecto.VOID,a); }

				    if(a.compareTo("do")==0){return tok(SymbolosProyecto.DO,a);}		    

				     //if(a.compareTo("var")==0) {return tok(SymbolosProyecto.VAR,a); }
				    if(a.compareTo("this")==0) {return tok(SymbolosProyecto.ID,a); }
				    if(a.compareTo("extends")==0) {return tok(SymbolosProyecto.EXTENDS,a); }

                                    if(a.compareTo("true")==0)  {return tok(SymbolosProyecto.TRUE,a); }
				    if(a.compareTo("false")==0) {return tok(SymbolosProyecto.FALSE,a); }
				    if(a.compareTo("new")==0) {return tok(SymbolosProyecto.NEW,a); }

				    
				    return tok(SymbolosProyecto.ID,a);
				}
<YYINITIAL>{digito}({digito})*		{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.ENTERO,a);
				}


<YYINITIAL>"."			{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PUNTO,a);
				}
<YYINITIAL>","				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.COMA,a);
				}

<YYINITIAL>";"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PCOMA,a);
				}

<YYINITIAL>":"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.DPUNTOS,a);
				}
<YYINITIAL>"&"			{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.AP,a);
				}


<YYINITIAL>"["				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.CA,a);
				}

<YYINITIAL>"]"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.CC,a);
				}


<YYINITIAL>"{"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.LLA,a);
				}
<YYINITIAL>"}"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.LLC,a);
				}
<YYINITIAL>"("				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PA,a);
				}

<YYINITIAL>")"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PC,a);
				}

<YYINITIAL>"&&"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.AND,a);
				}

<YYINITIAL>"||"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.OR,a);
				}
<YYINITIAL>"!"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.NOT,a);
				}
<YYINITIAL>"=="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.IGUAL,a);
				}

<YYINITIAL>"!="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.NOIGUAL,a);
				}

<YYINITIAL>"<"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MENORQ,a);
				}

<YYINITIAL>">"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MAYORQ,a);
				}

<YYINITIAL>"<="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MENORIGUAL,a);
				}

<YYINITIAL>">="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MAYORIGUAL,a);
				}


<YYINITIAL>"="				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     
				     return tok(SymbolosProyecto.ASIGNA,a);
				}

<YYINITIAL>"+"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MAS,a);
				}

<YYINITIAL>"-"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MENOS,a);
				}


<YYINITIAL>"*"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.POR,a);
				}

<YYINITIAL>"/"				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.DIV,a);
				}

<YYINITIAL>"%"			{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MOD,a);
				}

<YYINITIAL>" "				{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				}

<YYINITIAL>\"([^\"-\"^\n-\n])*\"    {
				                        String a=new String(yytext());
				                        Columna=a.length()+Columna;
							a=a.substring(1, a.length()-1);
				
				                        return tok(SymbolosProyecto.CADENA,a);
				                     }
<YYINITIAL>"'"{letra}"'" 	   {
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     a=a.substring(1, a.length()-1);
				
				     return tok(SymbolosProyecto.CARACTER,a);
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
				   Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   //System.out.println("Error lexico no?");
				   return new Symbol(SymbolosProyecto.error); 
				}


