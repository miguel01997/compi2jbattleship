package proyectocompiladores2r;
import java_cup.runtime.Symbol;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import InterpreteTAC.*;


public class Scanner3D implements java_cup.runtime.Scanner {
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

	public Scanner3D (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Scanner3D (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Scanner3D () {
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
		46
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
		/* 33 */ YY_END,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NOT_ACCEPT,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_END,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NOT_ACCEPT,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NOT_ACCEPT,
		/* 46 */ YY_NOT_ACCEPT
	};
	private int yy_cmap[] = unpackFromString(1,258,
"25:9,29,27,25:2,26,25:18,23,15,25:3,22,25,24,12,13,20,18,5,19,4,21,2:10,7,6" +
",16,14,17,25:2,1:26,8,25,9,25,3,25,1:26,10,25,11,25:130,0,28")[0];

	private int yy_rmap[] = unpackFromString(1,47,
"0,1,2,3,1:11,4,5,6,1:3,7,1:2,8,1:6,8:2,9,1:5,10,11,1,12,13,14,15,16")[0];

	private int yy_nxt[][] = unpackFromString(17,30,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,40,16,17,18,19,20,21,22,23,44,4,39,24,1" +
",25,-1:31,2:3,-1:28,3,-1:41,26,-1:29,28,-1:29,29,-1:35,30,43,-1:31,31,-1:2," +
"31:2,-1,31,-1:27,36,-1:25,31,-1:2,31,32,-1,31,-1:14,27,-1:36,38,-1:9,43:25," +
"33,34,41,43,-1,45,-1:52,35,-1:5,1,37:19,42,37:5,39,24,1,37");

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
return new Symbol(Symbolos3D.EOF);
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
					case -3:
						break;
					case 3:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.ENTERO,a);
				}
					case -4:
						break;
					case 4:
						{
				   Columna++;
				   //Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   return new Symbol(Symbolos3D.error); 
				}
					case -5:
						break;
					case 5:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PUNTO,a);
				}
					case -6:
						break;
					case 6:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.COMA,a);
				}
					case -7:
						break;
					case 7:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PCOMA,a);
				}
					case -8:
						break;
					case 8:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.DPUNTOS,a);
				}
					case -9:
						break;
					case 9:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.CA,a);
				}
					case -10:
						break;
					case 10:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.CC,a);
				}
					case -11:
						break;
					case 11:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.LLA,a);
				}
					case -12:
						break;
					case 12:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.LLC,a);
				}
					case -13:
						break;
					case 13:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PA,a);
				}
					case -14:
						break;
					case 14:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.PC,a);
				}
					case -15:
						break;
					case 15:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.ASIGNA,a);
				}
					case -16:
						break;
					case 16:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MENORQ,a);
				}
					case -17:
						break;
					case 17:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MAYORQ,a);
				}
					case -18:
						break;
					case 18:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MAS,a);
				}
					case -19:
						break;
					case 19:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MENOS,a);
				}
					case -20:
						break;
					case 20:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.POR,a);
				}
					case -21:
						break;
					case 21:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.DIV,a);
				}
					case -22:
						break;
					case 22:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MOD,a);
				}
					case -23:
						break;
					case 23:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				}
					case -24:
						break;
					case 24:
						{Columna=0;}
					case -25:
						break;
					case 25:
						{Columna++;}
					case -26:
						break;
					case 26:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				      System.out.println("..."+a);
				     return tok(Symbolos3D.IGUAL,a);
				}
					case -27:
						break;
					case 27:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.NOIGUAL,a);
				}
					case -28:
						break;
					case 28:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MENORIGUAL,a);
				}
					case -29:
						break;
					case 29:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     return tok(Symbolos3D.MAYORIGUAL,a);
				}
					case -30:
						break;
					case 30:
						{yybegin(CCOMMENT); }
					case -31:
						break;
					case 31:
						{Columna++;}
					case -32:
						break;
					case 32:
						{
				 Columna=0;
				}
					case -33:
						break;
					case 33:
						{Columna=0;}
					case -34:
						break;
					case 34:
						{Columna=0;}
					case -35:
						break;
					case 35:
						{
				     String a=new String(yytext());
				     Columna=a.length()+Columna;
				     a=a.substring(1, a.length()-1);
				     return tok(Symbolos3D.CARACTER,a);
				   }
					case -36:
						break;
					case 36:
						{Columna=0;}
					case -37:
						break;
					case 37:
						{yybegin(CCOMMENT);}
					case -38:
						break;
					case 38:
						{yybegin(YYINITIAL);}
					case -39:
						break;
					case 40:
						{
				   Columna++;
				   //Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   return new Symbol(Symbolos3D.error); 
				}
					case -40:
						break;
					case 41:
						{Columna=0;}
					case -41:
						break;
					case 42:
						{yybegin(CCOMMENT);}
					case -42:
						break;
					case 44:
						{
				   Columna++;
				   //Errores.InsertarError(yyline,"Error: Probocado por el simbolo"+ yytext());
				   return new Symbol(Symbolos3D.error); 
				}
					case -43:
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
