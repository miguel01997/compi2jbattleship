package proyectocompiladores2r;
import java_cup.runtime.Symbol;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import AST.*;


public class ScannerProyecto implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 256;
	private final int YY_EOF = 257;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public ScannerProyecto (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public ScannerProyecto (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private ScannerProyecto () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int CCOMMENT = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0,
		53
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_END,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NOT_ACCEPT,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_END,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NOT_ACCEPT,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NOT_ACCEPT,
		/* 53 */ YY_NOT_ACCEPT
	};
	private int yy_cmap[] = unpackFromString(1,258,
"29:9,33,31,29:2,27,29:18,25,16,26,29:2,24,8,28,13,14,22,20,5,21,4,23,2:10,7" +
",6,18,17,19,29:2,1:26,9,29,10,30,3,29,1:26,11,15,12,29:130,0,32")[0];

	private int yy_rmap[] = unpackFromString(1,54,
"0,1,2,3,1:5,4,1:6,5,6,7,8,1:3,9,1:2,10,1:9,10:2,11,1:5,12,13,1,14,15,16:2,1" +
"7,18,19")[0];

	private int yy_nxt[][] = unpackFromString(20,34,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,45,16,17,18,19,20,21,22,23,24,25,49,44," +
"51,4:2,26,1,27,-1:35,2:3,-1:32,3,-1:39,28,-1:42,30,-1:33,31,-1:33,32,-1:33," +
"33,-1:38,34,48,-1:35,36,-1,36,-1:3,36,-1,36,-1:31,41,-1:27,36,-1,36,-1:3,37" +
",-1,36,-1:15,29,-1:41,43,-1:11,48:26,38,48:3,39,46,48,-1,50:25,35,50:3,-1:3" +
",50,-1,52,-1:60,40,-1:5,1,42:21,47,42:4,44,42:3,26,1,42");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

MostrarMensaje("Fin de Archivo");
return new Symbol(SymbolosProyecto.EOF);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{
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
					case -3:
						break;
					case 3:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.ENTERO,a);
				}
					case -4:
						break;
					case 4:
						{
				   Columna++;
				   Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   //System.out.println("Error lexico no?");
				   return new Symbol(SymbolosProyecto.error); 
				}
					case -5:
						break;
					case 5:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PUNTO,a);
				}
					case -6:
						break;
					case 6:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.COMA,a);
				}
					case -7:
						break;
					case 7:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PCOMA,a);
				}
					case -8:
						break;
					case 8:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.DPUNTOS,a);
				}
					case -9:
						break;
					case 9:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.AP,a);
				}
					case -10:
						break;
					case 10:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.CA,a);
				}
					case -11:
						break;
					case 11:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.CC,a);
				}
					case -12:
						break;
					case 12:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.LLA,a);
				}
					case -13:
						break;
					case 13:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.LLC,a);
				}
					case -14:
						break;
					case 14:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PA,a);
				}
					case -15:
						break;
					case 15:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.PC,a);
				}
					case -16:
						break;
					case 16:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.NOT,a);
				}
					case -17:
						break;
					case 17:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.ASIGNA,a);
				}
					case -18:
						break;
					case 18:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MENORQ,a);
				}
					case -19:
						break;
					case 19:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MAYORQ,a);
				}
					case -20:
						break;
					case 20:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MAS,a);
				}
					case -21:
						break;
					case 21:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MENOS,a);
				}
					case -22:
						break;
					case 22:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.POR,a);
				}
					case -23:
						break;
					case 23:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.DIV,a);
				}
					case -24:
						break;
					case 24:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MOD,a);
				}
					case -25:
						break;
					case 25:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				}
					case -26:
						break;
					case 26:
						{Columna=0;}
					case -27:
						break;
					case 27:
						{Columna++;}
					case -28:
						break;
					case 28:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.AND,a);
				}
					case -29:
						break;
					case 29:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.OR,a);
				}
					case -30:
						break;
					case 30:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.NOIGUAL,a);
				}
					case -31:
						break;
					case 31:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.IGUAL,a);
				}
					case -32:
						break;
					case 32:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MENORIGUAL,a);
				}
					case -33:
						break;
					case 33:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(SymbolosProyecto.MAYORIGUAL,a);
				}
					case -34:
						break;
					case 34:
						{yybegin(CCOMMENT); }
					case -35:
						break;
					case 35:
						{
				                        String a=new String(yytext());
				                        Columna=a.length()+Columna;
							a=a.substring(1, a.length()-1);
				                        return tok(SymbolosProyecto.CADENA,a);
				                     }
					case -36:
						break;
					case 36:
						{Columna++;}
					case -37:
						break;
					case 37:
						{
				 Columna=0;
				}
					case -38:
						break;
					case 38:
						{Columna=0;}
					case -39:
						break;
					case 39:
						{Columna=0;}
					case -40:
						break;
					case 40:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     a=a.substring(1, a.length()-1);
				     return tok(SymbolosProyecto.CARACTER,a);
				   }
					case -41:
						break;
					case 41:
						{Columna=0;}
					case -42:
						break;
					case 42:
						{yybegin(CCOMMENT);}
					case -43:
						break;
					case 43:
						{yybegin(YYINITIAL);}
					case -44:
						break;
					case 45:
						{
				   Columna++;
				   Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   //System.out.println("Error lexico no?");
				   return new Symbol(SymbolosProyecto.error); 
				}
					case -45:
						break;
					case 46:
						{Columna=0;}
					case -46:
						break;
					case 47:
						{yybegin(CCOMMENT);}
					case -47:
						break;
					case 49:
						{
				   Columna++;
				   Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   //System.out.println("Error lexico no?");
				   return new Symbol(SymbolosProyecto.error); 
				}
					case -48:
						break;
					case 51:
						{
				   Columna++;
				   Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   //System.out.println("Error lexico no?");
				   return new Symbol(SymbolosProyecto.error); 
				}
					case -49:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
