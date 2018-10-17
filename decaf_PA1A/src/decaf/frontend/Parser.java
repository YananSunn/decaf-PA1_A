//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short LESS_EQUAL=281;
public final static short GREATER_EQUAL=282;
public final static short EQUAL=283;
public final static short NOT_EQUAL=284;
public final static short SCOPY=285;
public final static short SEALED=286;
public final static short GUARDED=287;
public final static short NEWSAMEARRAY=288;
public final static short UMINUS=290;
public final static short EMPTY=291;
public final static short VAR=292;
public final static short intConstant=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    2,    2,    6,    6,    7,    7,    7,    9,
    9,   10,   10,    8,    8,   11,   12,   12,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   14,
   14,   14,   22,   22,   26,   27,   25,   25,   28,   28,
   23,   23,   23,   24,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   30,   30,   30,   31,   32,   32,   32,   29,   29,
   33,   33,   17,   18,   21,   16,   34,   34,   19,   19,
   20,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    8,    6,    2,    0,    2,    2,    0,    1,
    0,    3,    1,    7,    6,    3,    2,    0,    1,    2,
    6,    1,    1,    1,    2,    2,    2,    1,    1,    3,
    1,    0,    5,    3,    3,    4,    2,    0,    2,    0,
    2,    4,    2,    5,    1,    1,    1,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    2,    3,    3,    1,    4,    5,    6,    5,
    3,    1,    1,    1,    3,    3,    0,    1,    1,    0,
    3,    1,    5,    9,    1,    6,    2,    0,    2,    1,
    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   15,   19,    0,   19,    0,    0,    0,    7,    8,
    6,    9,    0,    0,   12,   17,    0,    0,   18,   19,
   14,   10,    0,    4,    0,    0,    0,    0,    0,   11,
   13,    0,   23,    0,    0,    0,    0,    5,    0,    0,
    0,   28,   25,   22,   24,    0,   83,   76,    0,    0,
    0,    0,   95,    0,    0,    0,    0,   82,    0,    0,
    0,    0,    0,    0,   26,    0,   29,   38,   27,    0,
    0,   32,   33,   34,    0,    0,    0,   39,    0,    0,
    0,   57,   84,    0,    0,    0,    0,    0,   55,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   88,    0,   53,   30,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   49,    0,
    0,   35,   36,   37,    0,    0,    0,    0,    0,   44,
    0,    0,    0,    0,    0,   74,   75,    0,    0,    0,
   71,    0,   85,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   81,    0,    0,    0,
    0,    0,    0,    0,    0,   47,   77,    0,    0,  101,
    0,    0,    0,   86,   52,    0,    0,   93,    0,    0,
    0,   43,   78,    0,    0,    0,   80,   54,    0,    0,
   96,    0,   79,   31,    0,   97,   46,    0,   94,
};
final static short yydgoto[] = {                          3,
    4,    5,   77,   27,   44,   10,   16,   29,   45,   46,
   78,   56,   79,   80,   81,   82,   83,   84,   85,   86,
   87,   88,   99,  100,  141,  175,  176,   91,  186,   92,
   93,  113,  145,  201,
};
final static short yysindex[] = {                      -239,
 -244, -228,    0, -239,    0, -211, -220,    0, -216,  -59,
 -117,    0,    0, -202,    0,  -74,  -48,  270,    0,    0,
    0,    0, -196,  161,    0,    0,   34,  -88,    0,    0,
    0,    0,  -87,    0,   57,   -9,  299,   60,  161,    0,
    0,  161,    0,  -83,   67,   65,   73,    0,  -22,  161,
  -22,    0,    0,    0,    0,    3,    0,    0,   76,   77,
  -19,  100,    0, -123,   79,   82,   89,    0,   92,   99,
  100,  100,   58,  -72,    0, -133,    0,    0,    0,   85,
  800,    0,    0,    0,   88,   91,   93,    0,   87,    0,
 -125,    0,    0,  100,  100,  100,   29,  800,    0,    0,
  127,   78,  100,  129,  131,  100, -108,  -15,  -15, -103,
  344,    0,  -24,    0,    0,  100,  100,  100,  100,  100,
  100,  100,  100,  100,  100,  100,  100,  100,    0, -119,
  100,    0,    0,    0,  100,  135,  475,  123,  501,    0,
  100,  153,   66,  800,   38,    0,    0,  525,  151,  160,
    0,  -72,    0,  843,  832,   -8,   -8,  -32,  -32,   24,
   24,  -15,  -15,  -15,   -8,   -8,    0,  551,  800,  100,
   32,  100,   32,  572,   81,    0,    0,  700,  100,    0,
  -69,  100,  100,    0,    0,  163,  164,    0,  779,  -52,
   32,    0,    0,  800,  168,  726,    0,    0,  100,   32,
    0,  -76,    0,    0,  179,    0,    0,   32,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  221,    0,  107,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  176,    0,    0,    0,  181,    0,
    0,  181,    0,    0,    0,  182,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0,    0,
  -31,  -31,  -31,  -20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  821,  449,
    0,    0,    0,  -31,  -58,  -31,   80,  185,    0,    0,
    0,    0,  -31,    0,    0,  -31,    0,  155,  368,    0,
    0,    0,    0,    0,    0,  -31,  -31,  -31,  -31,  -31,
  -31,  -31,  -31,  -31,  -31,  -31,  -31,  -31,    0,    0,
  -31,    0,    0,    0,  -31,  119,    0,    0,    0,    0,
  -31,    0,  -31,   44,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,   83,  324,  878,   37,  277,  856,
  866,  404,  413,  440,  886,  894,    0,    0,  -18,  -25,
  -58,  -31,  -58,    0,    0,    0,    0,    0,  -31,    0,
    0,  -31,  -31,    0,    0,    0,  205,    0,    0,  -33,
  -58,    0,    0,   45,    0,    0,    0,    0,  -23,  -58,
    0,  130,    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  252,   26,    7,   94,    0,   25,    0,  216,    0,
   56,    0, -146,  -78, 1080,    0,    0,    0,    0,    0,
    0,    0,  470,  826,    0,    0,    0,    0,    0,  -65,
    0,    0,   96,    0,
};
final static int YYTABLESIZE=1263;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         98,
   42,  100,   36,   36,  126,   15,   98,   36,  112,  124,
  122,   98,  123,  129,  125,   90,  138,   42,   74,  152,
   96,    1,   40,   87,  188,   98,  190,  128,  126,  127,
  129,    6,    7,  124,  122,   72,  123,  129,  125,   18,
   40,   26,   73,   26,  202,   43,    2,   71,   43,   69,
   25,    9,   69,  206,   37,   11,   54,   98,  131,   12,
  126,  209,   26,   13,   72,  124,   69,   69,  153,  129,
  125,   73,   87,   17,   30,  131,   71,   63,  180,   32,
   63,  179,  131,   40,   92,   91,  184,   92,   91,   98,
   72,   98,   34,   74,   63,   63,   39,   73,   72,   42,
   52,   69,   71,   97,   53,   73,   55,   49,   50,   28,
   71,   28,   48,   51,  131,   94,   95,   33,  103,   48,
  205,  104,   74,   70,   48,   52,   70,   75,  105,   63,
   28,  106,   72,   19,   20,   21,   22,   23,  107,   73,
   70,   70,  114,  115,   71,   14,  132,  135,   74,  133,
  136,  134,  101,  140,   52,   51,   74,  102,   40,   51,
   51,   51,   51,   51,   51,   51,  142,  149,  143,  146,
   48,  147,  150,  167,  170,   70,   51,   51,   51,   51,
   51,  172,   19,   20,   21,   22,   23,   35,   38,   57,
   74,   72,   48,  177,  182,   72,   72,   72,   72,   72,
  183,   72,   68,  198,   24,  192,  195,  179,  203,   51,
  207,   51,   72,   72,   72,  200,   72,   50,   50,  208,
    1,   21,   20,   98,   98,   98,   98,   98,   98,   16,
   98,   98,   98,   98,    5,   98,   98,   98,   98,   98,
   98,   98,   98,   99,   50,   89,   98,   72,  118,  119,
   50,   98,   50,   98,   45,    8,  130,   47,   98,   19,
   20,   21,   22,   23,   57,  187,   58,   59,   60,   61,
    0,   62,   63,   64,   65,   66,   67,   68,    0,    0,
  130,    0,   69,    0,    0,   69,   69,   70,   19,   20,
   21,   22,   23,   57,   76,   58,   59,   60,   61,    0,
   62,   63,   64,   65,   66,   67,   68,    0,    0,    0,
    0,   69,  130,   63,   63,    0,   70,   64,  110,   57,
   64,   58,    0,   76,    0,    0,    0,   57,   64,   58,
   66,   67,   68,    0,   64,   64,   64,   69,   66,   67,
   68,   48,    0,   48,    0,   69,    0,    0,    0,   76,
   48,    0,   48,   48,   48,   48,    0,   76,    0,   48,
   70,   57,    0,   58,   67,    0,    0,   67,    0,   64,
   64,   48,   66,   67,   68,    0,    0,    0,    0,   69,
  126,   67,   67,    0,  151,  124,  122,    0,  123,  129,
  125,   76,    0,    0,   31,   51,   51,    0,    0,   51,
   51,   51,   51,  128,   73,  127,    0,   51,   73,   73,
   73,   73,   73,    0,   73,    0,   67,   19,   20,   21,
   22,   23,    0,   41,    0,   73,   73,   73,    0,   73,
    0,   72,   72,    0,  131,   72,   72,   72,   72,    0,
   60,    0,    0,   72,   60,   60,   60,   60,   60,   61,
   60,    0,    0,   61,   61,   61,   61,   61,    0,   61,
   73,   60,   60,   60,    0,   60,    0,    0,    0,    0,
   61,   61,   61,    0,   61,    0,   62,    0,    0,    0,
   62,   62,   62,   62,   62,   56,   62,    0,    0,   41,
   56,   56,    0,   56,   56,   56,   60,   62,   62,   62,
    0,   62,    0,    0,    0,   61,    0,   41,   56,    0,
   56,  126,    0,    0,    0,  171,  124,  122,    0,  123,
  129,  125,    0,    0,    0,   89,   19,   20,   21,   22,
   23,    0,   62,    0,  128,    0,  127,  126,    0,   56,
    0,  173,  124,  122,    0,  123,  129,  125,   24,    0,
    0,    0,    0,   64,   64,   19,   20,   21,   22,   23,
  128,  126,  127,    0,   89,  131,  124,  122,  181,  123,
  129,  125,    0,    0,    0,    0,    0,   24,    0,    0,
    0,    0,    0,    0,  128,    0,  127,  126,    0,    0,
    0,  131,  124,  122,    0,  123,  129,  125,    0,    0,
   67,   67,    0,    0,    0,    0,   67,   67,  126,    0,
  128,    0,  127,  124,  122,  131,  123,  129,  125,    0,
  116,  117,    0,    0,  118,  119,  120,  121,    0,  191,
    0,  128,  130,  127,    0,    0,    0,    0,    0,    0,
   89,  131,   89,  185,   73,   73,    0,    0,   73,   73,
   73,   73,    0,    0,    0,    0,   73,    0,    0,    0,
   89,    0,  131,    0,    0,    0,    0,    0,   89,   89,
    0,    0,    0,    0,    0,    0,    0,   89,    0,    0,
   60,   60,    0,    0,   60,   60,   60,   60,    0,   61,
   61,    0,   60,   61,   61,   61,   61,    0,    0,    0,
    0,   61,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   62,   62,    0,    0,
   62,   62,   62,   62,    0,   56,   56,    0,   62,   56,
   56,   56,   56,    0,    0,    0,  126,   56,    0,    0,
    0,  124,  122,    0,  123,  129,  125,    0,    0,    0,
    0,  116,  117,    0,    0,  118,  119,  120,  121,  128,
    0,  127,  126,  130,    0,    0,  204,  124,  122,    0,
  123,  129,  125,    0,    0,    0,    0,  116,  117,    0,
    0,  118,  119,  120,  121,  128,    0,  127,    0,  130,
  131,    0,  193,    0,    0,    0,    0,    0,    0,    0,
    0,  116,  117,    0,    0,  118,  119,  120,  121,    0,
    0,    0,    0,  130,    0,  126,  131,    0,    0,    0,
  124,  122,    0,  123,  129,  125,    0,  116,  117,    0,
    0,  118,  119,  120,  121,    0,  126,  199,  128,  130,
  127,  124,  122,    0,  123,  129,  125,    0,  116,  117,
    0,    0,  118,  119,  120,  121,    0,   55,    0,  128,
  130,  127,   55,   55,    0,   55,   55,   55,  126,  131,
    0,    0,    0,  124,  122,    0,  123,  129,  125,  126,
   55,   90,   55,    0,  124,  122,    0,  123,  129,  125,
  131,  128,    0,  127,    0,    0,   58,    0,   58,   58,
   58,    0,  128,    0,  127,    0,   59,    0,   59,   59,
   59,   55,    0,   58,   58,   58,    0,   58,   68,    0,
   90,   68,  131,   59,   59,   59,   66,   59,    0,   66,
    0,    0,    0,  131,   65,   68,   68,   65,    0,    0,
    0,    0,    0,   66,   66,    0,    0,    0,   58,    0,
    0,   65,   65,    0,    0,    0,    0,    0,   59,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   68,    0,    0,    0,    0,    0,  116,  117,   66,    0,
  118,  119,  120,  121,    0,    0,   65,    0,  130,    0,
    0,    0,    0,    0,    0,    0,   90,    0,   90,    0,
    0,    0,  116,  117,    0,    0,  118,  119,  120,  121,
    0,    0,    0,    0,  130,    0,   90,    0,    0,    0,
    0,    0,    0,    0,   90,   90,    0,    0,    0,    0,
    0,    0,    0,   90,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  116,  117,    0,    0,  118,
  119,  120,  121,    0,    0,    0,    0,  130,    0,    0,
    0,    0,    0,    0,    0,    0,  116,  117,    0,    0,
  118,  119,  120,  121,    0,    0,    0,    0,  130,    0,
    0,    0,    0,    0,    0,    0,    0,   55,   55,    0,
    0,   55,   55,   55,   55,    0,    0,    0,  116,   55,
    0,    0,  118,  119,  120,  121,    0,    0,    0,    0,
  130,    0,    0,  118,  119,  120,  121,    0,    0,    0,
    0,  130,   58,   58,    0,    0,   58,   58,   58,   58,
    0,   98,   59,   59,    0,    0,   59,   59,   59,   59,
  108,  109,  111,    0,   68,   68,    0,    0,    0,    0,
   68,   68,   66,   66,    0,    0,    0,    0,   66,   66,
   65,   65,    0,  137,    0,  139,   65,   65,    0,    0,
    0,    0,  144,    0,    0,  148,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  154,  155,  156,  157,  158,
  159,  160,  161,  162,  163,  164,  165,  166,    0,    0,
  168,    0,    0,    0,  169,    0,    0,    0,    0,    0,
  174,    0,  178,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  144,
    0,  189,    0,    0,    0,    0,    0,    0,  194,    0,
    0,  196,  197,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   91,   91,   37,  123,   40,   91,   74,   42,
   43,   45,   45,   46,   47,   41,   95,   41,   91,   44,
   40,  261,   41,   44,  171,   59,  173,   60,   37,   62,
   46,  276,  261,   42,   43,   33,   45,   46,   47,   15,
   59,   16,   40,   18,  191,   39,  286,   45,   42,   41,
  125,  263,   44,  200,   30,  276,   50,   91,   91,  276,
   37,  208,   37,  123,   33,   42,   58,   59,   93,   46,
   47,   40,   93,  276,  123,   91,   45,   41,   41,  276,
   44,   44,   91,   93,   41,   41,  152,   44,   44,  123,
   33,  125,   59,   91,   58,   59,   40,   40,   33,   40,
  123,   93,   45,  123,   49,   40,   51,   41,   44,   16,
   45,   18,   33,   41,   91,   40,   40,   24,   40,   40,
  199,   40,   91,   41,   45,  123,   44,  125,   40,   93,
   37,   40,   33,  257,  258,  259,  260,  261,   40,   40,
   58,   59,  276,   59,   45,  263,   59,   61,   91,   59,
  276,   59,  276,  125,  123,   37,   91,   64,   93,   41,
   42,   43,   44,   45,   46,   47,   40,  276,   91,   41,
   91,   41,  276,  293,   40,   93,   58,   59,   60,   61,
   62,   59,  257,  258,  259,  260,  261,  276,  276,  262,
   91,   37,  276,   41,   44,   41,   42,   43,   44,   45,
   41,   47,  275,   41,  279,  125,  276,   44,   41,   91,
  287,   93,   58,   59,   60,  268,   62,  276,  276,   41,
    0,   41,   41,  257,  258,  259,  260,  261,  262,  123,
  264,  265,  266,  267,   59,  269,  270,  271,  272,  273,
  274,  275,  276,   59,  276,   41,  280,   93,  281,  282,
  276,  285,  276,  287,  125,    4,  289,   42,  292,  257,
  258,  259,  260,  261,  262,  170,  264,  265,  266,  267,
   -1,  269,  270,  271,  272,  273,  274,  275,   -1,   -1,
  289,   -1,  280,   -1,   -1,  277,  278,  285,  257,  258,
  259,  260,  261,  262,  292,  264,  265,  266,  267,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,
   -1,  280,  289,  277,  278,   -1,  285,   41,  261,  262,
   44,  264,   -1,  292,   -1,   -1,   -1,  262,  271,  264,
  273,  274,  275,   -1,   58,   59,  271,  280,  273,  274,
  275,  262,   -1,  264,   -1,  280,   -1,   -1,   -1,  292,
  271,   -1,  273,  274,  275,  276,   -1,  292,   -1,  280,
  278,  262,   -1,  264,   41,   -1,   -1,   44,   -1,   93,
  271,  292,  273,  274,  275,   -1,   -1,   -1,   -1,  280,
   37,   58,   59,   -1,   41,   42,   43,   -1,   45,   46,
   47,  292,   -1,   -1,  125,  277,  278,   -1,   -1,  281,
  282,  283,  284,   60,   37,   62,   -1,  289,   41,   42,
   43,   44,   45,   -1,   47,   -1,   93,  257,  258,  259,
  260,  261,   -1,  125,   -1,   58,   59,   60,   -1,   62,
   -1,  277,  278,   -1,   91,  281,  282,  283,  284,   -1,
   37,   -1,   -1,  289,   41,   42,   43,   44,   45,   37,
   47,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
   93,   58,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,
   58,   59,   60,   -1,   62,   -1,   37,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   37,   47,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   93,   58,   59,   60,
   -1,   62,   -1,   -1,   -1,   93,   -1,   59,   60,   -1,
   62,   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   56,  257,  258,  259,  260,
  261,   -1,   93,   -1,   60,   -1,   62,   37,   -1,   91,
   -1,   41,   42,   43,   -1,   45,   46,   47,  279,   -1,
   -1,   -1,   -1,  277,  278,  257,  258,  259,  260,  261,
   60,   37,   62,   -1,   95,   91,   42,   43,   44,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,  279,   -1,   -1,
   -1,   -1,   -1,   -1,   60,   -1,   62,   37,   -1,   -1,
   -1,   91,   42,   43,   -1,   45,   46,   47,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,   37,   -1,
   60,   -1,   62,   42,   43,   91,   45,   46,   47,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   58,
   -1,   60,  289,   62,   -1,   -1,   -1,   -1,   -1,   -1,
  171,   91,  173,   93,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,   -1,  289,   -1,   -1,   -1,
  191,   -1,   91,   -1,   -1,   -1,   -1,   -1,  199,  200,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  208,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,  277,
  278,   -1,  289,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,  277,  278,   -1,  289,  281,
  282,  283,  284,   -1,   -1,   -1,   37,  289,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   60,
   -1,   62,   37,  289,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   60,   -1,   62,   -1,  289,
   91,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,  289,   -1,   37,   91,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   37,   59,   60,  289,
   62,   42,   43,   -1,   45,   46,   47,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   37,   -1,   60,
  289,   62,   42,   43,   -1,   45,   46,   47,   37,   91,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   37,
   60,   56,   62,   -1,   42,   43,   -1,   45,   46,   47,
   91,   60,   -1,   62,   -1,   -1,   41,   -1,   43,   44,
   45,   -1,   60,   -1,   62,   -1,   41,   -1,   43,   44,
   45,   91,   -1,   58,   59,   60,   -1,   62,   41,   -1,
   95,   44,   91,   58,   59,   60,   41,   62,   -1,   44,
   -1,   -1,   -1,   91,   41,   58,   59,   44,   -1,   -1,
   -1,   -1,   -1,   58,   59,   -1,   -1,   -1,   93,   -1,
   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,   93,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   93,   -1,   -1,   -1,   -1,   -1,  277,  278,   93,   -1,
  281,  282,  283,  284,   -1,   -1,   93,   -1,  289,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  171,   -1,  173,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,   -1,   -1,   -1,  289,   -1,  191,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  199,  200,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  208,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,   -1,  289,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,  289,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,  277,  289,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,
  289,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,  289,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,   62,  277,  278,   -1,   -1,  281,  282,  283,  284,
   71,   72,   73,   -1,  277,  278,   -1,   -1,   -1,   -1,
  283,  284,  277,  278,   -1,   -1,   -1,   -1,  283,  284,
  277,  278,   -1,   94,   -1,   96,  283,  284,   -1,   -1,
   -1,   -1,  103,   -1,   -1,  106,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  116,  117,  118,  119,  120,
  121,  122,  123,  124,  125,  126,  127,  128,   -1,   -1,
  131,   -1,   -1,   -1,  135,   -1,   -1,   -1,   -1,   -1,
  141,   -1,  143,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  170,
   -1,  172,   -1,   -1,   -1,   -1,   -1,   -1,  179,   -1,
   -1,  182,  183,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=293;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL","SCOPY",
"SEALED","GUARDED","NEWSAMEARRAY","\"%%\"","UMINUS","EMPTY","VAR","intConstant",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ClassDef : SEALED CLASS IDENTIFIER EXTENDS IDENTIFIER '{' FieldList '}'",
"ClassDef : SEALED CLASS IDENTIFIER '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : SCOPY '(' IDENTIFIER ',' Expr ')'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : GuardedStmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"GuardedStmt : IF '{' IfBranchList IfSubStmt '}'",
"GuardedStmt : IF '{' '}'",
"IfSubStmt : Expr ':' Stmt",
"IfBranch : Expr ':' Stmt GUARDED",
"IfBranchList : IfBranchList IfBranch",
"IfBranchList :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"LValue : VAR IDENTIFIER",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : Expr \"%%\" intConstant",
"Constant : LITERAL",
"Constant : NULL",
"Constant : ArrayConstant",
"ArrayConstant : '[' ConstantList ']'",
"ConstantList : ConstantList ',' Constant",
"ConstantList :",
"ConstantList : Constant",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 507 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 668 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 58 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 64 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 68 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 78 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 84 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 88 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 92 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 96 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 100 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 104 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 110 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 114 "Parser.y"
{
						yyval.cdef = new Tree.Sealed(val_peek(5).ident, val_peek(3).ident, val_peek(1).flist, val_peek(7).loc);
					}
break;
case 14:
//#line 118 "Parser.y"
{
						yyval.cdef = new Tree.Sealed(val_peek(3).ident, null, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 15:
//#line 124 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 16:
//#line 128 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 17:
//#line 134 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 18:
//#line 138 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 19:
//#line 142 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 21:
//#line 150 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 22:
//#line 157 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 23:
//#line 161 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 24:
//#line 168 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 172 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 26:
//#line 178 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 27:
//#line 184 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 28:
//#line 188 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 29:
//#line 195 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 30:
//#line 200 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 31:
//#line 206 "Parser.y"
{
                		yyval.stmt = new Tree.SCopyExpr(val_peek(3).ident, val_peek(1).expr, val_peek(5).loc);
                	}
break;
case 40:
//#line 220 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 41:
//#line 224 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 42:
//#line 228 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 234 "Parser.y"
{
                        yyval.stmt = new Tree.Guarded(val_peek(2).slist, val_peek(1).stmt, val_peek(4).loc);
                    }
break;
case 44:
//#line 238 "Parser.y"
{
                        yyval.stmt = new Tree.Guarded(null, null, val_peek(2).loc);
                    }
break;
case 45:
//#line 243 "Parser.y"
{
                        yyval.stmt = new Tree.IfSubStmt(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
                    }
break;
case 46:
//#line 248 "Parser.y"
{
                        yyval.stmt = new Tree.IfSubStmt(val_peek(3).expr, val_peek(1).stmt, val_peek(3).loc);
                    }
break;
case 47:
//#line 253 "Parser.y"
{
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 48:
//#line 257 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.slist = new ArrayList<Tree>();
                    }
break;
case 50:
//#line 265 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 51:
//#line 271 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 52:
//#line 278 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 53:
//#line 283 "Parser.y"
{
                		yyval.lvalue = new Tree.Var(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 54:
//#line 289 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 55:
//#line 298 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 58:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 308 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 348 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 352 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 356 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 72:
//#line 360 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 364 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 74:
//#line 368 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 372 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 76:
//#line 376 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 77:
//#line 382 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 78:
//#line 386 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 79:
//#line 390 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 80:
//#line 394 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 81:
//#line 399 "Parser.y"
{
                		yyval.expr = new NewSameArray(val_peek(2).expr, val_peek(0).stmt, val_peek(1).loc);
                	}
break;
case 82:
//#line 405 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 83:
//#line 409 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 85:
//#line 416 "Parser.y"
{
						yyval.expr = new Tree.ArrayConstant(val_peek(1).elist, val_peek(2).loc);
					}
break;
case 86:
//#line 422 "Parser.y"
{
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 87:
//#line 426 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.elist = new ArrayList<Tree.Expr>();
                    }
break;
case 88:
//#line 431 "Parser.y"
{
                        yyval.elist = new ArrayList<Tree.Expr>();
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 90:
//#line 439 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 91:
//#line 446 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 92:
//#line 450 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 93:
//#line 457 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 94:
//#line 463 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 95:
//#line 469 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 96:
//#line 475 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 97:
//#line 481 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 98:
//#line 485 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 99:
//#line 491 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 100:
//#line 495 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 101:
//#line 501 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1348 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
