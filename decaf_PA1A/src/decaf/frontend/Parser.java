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
    0,    0,    0,    0,    0,    0,    0,    0,    0,   49,
    0,   35,   36,   37,    0,    0,    0,    0,    0,   44,
    0,    0,    0,    0,    0,   74,   75,    0,    0,    0,
   71,    0,   85,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   47,   77,    0,    0,  101,
    0,    0,    0,   86,   52,    0,    0,   93,    0,    0,
    0,   43,   78,    0,    0,    0,    0,   54,    0,    0,
   96,    0,   79,   31,    0,   97,   46,    0,   94,
};
final static short yydgoto[] = {                          3,
    4,    5,   77,   27,   44,   10,   16,   29,   45,   46,
   78,   56,   79,   80,   81,   82,   83,   84,   85,   86,
   87,   88,   99,  100,  141,  175,  176,   91,  186,   92,
   93,  113,  145,  201,
};
final static short yysindex[] = {                      -241,
 -243, -215,    0, -241,    0, -207, -214,    0, -213,  -62,
 -117,    0,    0, -206,    0,  -75,  -30,  291,    0,    0,
    0,    0, -174,  -85,    0,    0,   46,  -87,    0,    0,
    0,    0,  -83,    0,   70,   19,  412,   74,  -85,    0,
    0,  -85,    0,  -82,   75,   73,   78,    0,   -1,  -85,
   -1,    0,    0,    0,    0,    3,    0,    0,   84,   89,
  -19,  100,    0, -107,   90,   92,   94,    0,   97,   98,
  100,  100,   58,  -72,    0, -161,    0,    0,    0,   82,
  538,    0,    0,    0,   83,   88,   99,    0,   87,    0,
 -137,    0,    0,  100,  100,  100,   42,  538,    0,    0,
  103,   77,  100,  129,  146,  100,  -88,  -44,  -44,  -81,
  344,    0,  -13,    0,    0,  100,  100,  100,  100,  100,
  100,  100,  100,  100,  100,  100,  100,  100,  100,    0,
  100,    0,    0,    0,  100,  159,  368,  147,  380,    0,
  100,  164,   66,  538,   10,    0,    0,  404,  163,  167,
    0,  -72,    0,  691,  593,   -8,   -8,  -32,  -32,  538,
   27,   27,  -44,  -44,  -44,   -8,   -8,  430,  538,  100,
   32,  100,   32,  442,   86,    0,    0,  464,  100,    0,
  -67,  100,  100,    0,    0,  172,  175,    0,  485,  -47,
   32,    0,    0,  538,  181,  517,  -65,    0,  100,   32,
    0,  -71,    0,    0,  189,    0,    0,   32,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  235,    0,  122,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  196,    0,    0,    0,  207,    0,
    0,  207,    0,    0,    0,  216,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -56,    0,    0,    0,    0,    0,    0,    0,    0,
  -18,  -18,  -18,  -12,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  550,  155,
    0,    0,    0,  -18,  -58,  -18,   80,  212,    0,    0,
    0,    0,  -18,    0,    0,  -18,    0,  799,  841,    0,
    0,    0,    0,    0,    0,  -18,  -18,  -18,  -18,  -18,
  -18,  -18,  -18,  -18,  -18,  -18,  -18,  -18,  -18,    0,
  -18,    0,    0,    0,  -18,  119,    0,    0,    0,    0,
  -18,    0,  -18,   34,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    8,   38,  530,  636,  325,  424,  -17,
  716,  923,  865,  894,  903,  752,  945,    0,   12,  -25,
  -58,  -18,  -58,    0,    0,    0,    0,    0,  -18,    0,
    0,  -18,  -18,    0,    0,    0,  225,    0,    0,  -33,
  -58,    0,    0,   44,    0,    0,  744,    0,  -23,  -58,
    0,  154,    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  277,    7,   18,   71,    0,   25,    0,  240,    0,
   35,    0,  -64,  -78, 1121,    0,    0,    0,    0,    0,
    0,    0,  860,  893,    0,    0,    0,    0,    0,  -52,
    0,    0,  114,    0,
};
final static int YYTABLESIZE=1304;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         98,
   42,  130,  100,   36,  127,   15,   98,   36,   36,  125,
  123,   98,  124,  130,  126,   90,  138,   42,   74,    1,
   96,  112,   26,   81,   26,   98,   81,  129,  127,  128,
  152,   87,    6,  125,  123,   72,  124,  130,  126,   18,
   81,   81,   73,   26,    2,    7,  131,   71,   69,   25,
  180,   69,   40,  179,   37,    9,   43,   98,  131,   43,
   13,   11,   12,  127,   72,   69,   69,   54,  125,   17,
   40,   73,  130,  126,   92,   81,   71,   92,   70,  153,
   87,   70,  131,   53,   91,   55,   28,   91,   28,   98,
   72,   98,   30,   74,   33,   70,   70,   73,   72,  184,
   69,   32,   71,   97,   34,   73,  188,   28,  190,   39,
   71,   40,   48,   42,  114,   49,   50,  131,   51,   48,
  205,   52,   74,   94,   48,   52,  202,   75,   95,  103,
   70,  104,   72,  105,  102,  206,  106,  107,  136,   73,
  115,  132,  142,  209,   71,   14,  133,  135,   74,   19,
   20,   21,   22,   23,   52,   51,   74,  134,   40,   51,
   51,   51,   51,   51,   51,   51,  140,  143,  101,  146,
   48,   19,   20,   21,   22,   23,   51,   51,   51,   51,
   51,   19,   20,   21,   22,   23,  147,  149,   35,   57,
   74,   56,   38,   48,  150,   41,   56,   56,  170,   56,
   56,   56,   68,   24,  177,  172,  182,  183,  195,   51,
  192,   51,  198,   41,   56,  207,   56,   50,  179,   50,
  200,  203,  122,   98,   98,   98,   98,   98,   98,  208,
   98,   98,   98,   98,    1,   98,   98,   98,   98,   98,
   98,   98,   98,  122,   16,   56,   98,   21,  118,  119,
   50,   98,   50,   98,    5,  122,   20,   50,   98,   19,
   20,   21,   22,   23,   57,   89,   58,   59,   60,   61,
   99,   62,   63,   64,   65,   66,   67,   68,   45,  122,
    8,   47,   69,  187,   69,   69,    0,   70,   19,   20,
   21,   22,   23,   57,   76,   58,   59,   60,   61,    0,
   62,   63,   64,   65,   66,   67,   68,    0,    0,    0,
    0,   69,    0,    0,  122,   70,   70,    0,  110,   57,
    0,   58,    0,   76,    0,    0,    0,   57,   64,   58,
   66,   67,   68,    0,    0,    0,   64,   69,   66,   67,
   68,   48,    0,   48,    0,   69,    0,    0,    0,   76,
   48,    0,   48,   48,   48,   48,    0,   76,    0,   48,
    0,   57,    0,   58,    0,   63,    0,    0,   63,    0,
   64,   48,   66,   67,   68,    0,    0,    0,    0,   69,
  127,    0,   63,   63,  151,  125,  123,    0,  124,  130,
  126,   76,    0,    0,    0,   51,   51,    0,    0,   51,
   51,   51,   51,  129,  127,  128,   51,    0,  171,  125,
  123,    0,  124,  130,  126,   31,  127,   63,    0,    0,
  173,  125,  123,    0,  124,  130,  126,  129,    0,  128,
    0,   56,   56,    0,  131,   56,   56,   56,   56,  129,
  127,  128,   56,    0,    0,  125,  123,  181,  124,  130,
  126,    0,    0,    0,    0,    0,    0,    0,  131,    0,
    0,    0,    0,  129,   64,  128,  127,   64,    0,    0,
  131,  125,  123,    0,  124,  130,  126,    0,  127,    0,
    0,   64,   64,  125,  123,    0,  124,  130,  126,  129,
    0,  128,    0,    0,  131,    0,    0,    0,    0,  191,
  127,  129,    0,  128,    0,  125,  123,    0,  124,  130,
  126,    0,    0,    0,    0,    0,   64,    0,    0,    0,
  131,  127,  185,  129,    0,  128,  125,  123,    0,  124,
  130,  126,  131,    0,    0,    0,   41,    0,    0,    0,
    0,    0,    0,  199,  129,    0,  128,   19,   20,   21,
   22,   23,    0,  127,  131,    0,  193,  204,  125,  123,
    0,  124,  130,  126,    0,    0,    0,    0,    0,   24,
   67,    0,    0,   67,  127,  131,  129,    0,  128,  125,
  123,    0,  124,  130,  126,    0,   55,   67,   67,    0,
    0,   55,   55,    0,   55,   55,   55,  129,    0,  128,
    0,   63,   63,    0,    0,    0,    0,  131,    0,   55,
    0,   55,    0,    0,    0,    0,    0,    0,    0,    0,
  116,  117,   67,    0,  118,  119,  120,  121,  131,  127,
    0,  122,    0,    0,  125,  123,    0,  124,  130,  126,
   55,    0,    0,    0,  116,  117,    0,    0,  118,  119,
  120,  121,  129,    0,  128,  122,  116,  117,    0,    0,
  118,  119,  120,  121,    0,    0,    0,  122,   19,   20,
   21,   22,   23,    0,    0,    0,   68,    0,    0,   68,
  116,  117,    0,  131,  118,  119,  120,  121,    0,    0,
   24,  122,    0,   68,   68,    0,    0,    0,    0,    0,
   64,   64,    0,    0,    0,    0,  116,  117,    0,    0,
  118,  119,  120,  121,    0,    0,    0,  122,  116,  117,
    0,    0,  118,  119,  120,  121,    0,  127,   68,  122,
    0,    0,  125,  123,    0,  124,  130,  126,    0,    0,
  116,  117,    0,    0,  118,  119,  120,  121,    0,    0,
  129,  122,  128,    0,    0,    0,   58,    0,   58,   58,
   58,  116,  117,    0,    0,  118,  119,  120,  121,    0,
    0,    0,  122,   58,   58,   58,    0,   58,    0,    0,
   80,  131,    0,    0,   80,   80,   80,   80,   80,   80,
   80,    0,   66,  116,  117,   66,    0,  118,  119,  120,
  121,   80,   80,   80,  122,   80,   67,   67,   58,   66,
   66,    0,   67,   67,  116,  117,    0,    0,  118,  119,
  120,  121,    0,    0,    0,  122,   55,   55,    0,    0,
   55,   55,   55,   55,   80,   72,   80,   55,    0,   72,
   72,   72,   72,   72,   66,   72,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   72,   72,   72,    0,
   72,    0,    0,    0,    0,    0,    0,    0,    0,  116,
    0,    0,    0,  118,  119,  120,  121,   73,    0,    0,
  122,   73,   73,   73,   73,   73,    0,   73,    0,    0,
    0,   72,    0,    0,    0,    0,    0,    0,   73,   73,
   73,   60,   73,    0,    0,   60,   60,   60,   60,   60,
    0,   60,   68,   68,    0,   89,    0,    0,   68,   68,
    0,    0,   60,   60,   60,    0,   60,    0,    0,    0,
   61,    0,    0,   73,   61,   61,   61,   61,   61,   62,
   61,    0,    0,   62,   62,   62,   62,   62,   90,   62,
    0,   61,   61,   61,   89,   61,    0,   60,    0,    0,
   62,   62,   62,   59,   62,   59,   59,   59,    0,    0,
    0,  118,  119,  120,  121,    0,    0,    0,  122,    0,
   59,   59,   59,    0,   59,   65,   61,   90,   65,    0,
    0,    0,   58,   58,    0,   62,   58,   58,   58,   58,
    0,    0,   65,   65,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   59,    0,    0,    0,    0,
   80,   80,    0,    0,   80,   80,   80,   80,   66,   66,
   89,    0,   89,    0,   66,   66,    0,   65,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   89,    0,    0,    0,    0,    0,    0,    0,   89,   89,
    0,    0,    0,   90,    0,   90,    0,   89,    0,    0,
    0,    0,    0,    0,    0,   72,   72,    0,    0,   72,
   72,   72,   72,   90,    0,    0,    0,    0,    0,    0,
    0,   90,   90,    0,    0,    0,    0,    0,    0,    0,
   90,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,   73,    0,
    0,   73,   73,   73,   73,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   60,   60,    0,    0,   60,   60,   60,   60,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   61,   61,    0,    0,   61,   61,   61,   61,    0,   62,
   62,    0,   98,   62,   62,   62,   62,    0,    0,    0,
    0,  108,  109,  111,    0,    0,    0,    0,    0,   59,
   59,    0,    0,   59,   59,   59,   59,    0,    0,    0,
    0,    0,    0,    0,  137,    0,  139,    0,    0,    0,
    0,   65,   65,  144,    0,    0,  148,   65,   65,    0,
    0,    0,    0,    0,    0,    0,  154,  155,  156,  157,
  158,  159,  160,  161,  162,  163,  164,  165,  166,  167,
    0,  168,    0,    0,    0,  169,    0,    0,    0,    0,
    0,  174,    0,  178,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  144,    0,  189,    0,    0,    0,    0,    0,    0,  194,
    0,    0,  196,  197,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   46,   59,   91,   37,  123,   40,   91,   91,   42,
   43,   45,   45,   46,   47,   41,   95,   41,   91,  261,
   40,   74,   16,   41,   18,   59,   44,   60,   37,   62,
   44,   44,  276,   42,   43,   33,   45,   46,   47,   15,
   58,   59,   40,   37,  286,  261,   91,   45,   41,  125,
   41,   44,   41,   44,   30,  263,   39,   91,   91,   42,
  123,  276,  276,   37,   33,   58,   59,   50,   42,  276,
   59,   40,   46,   47,   41,   93,   45,   44,   41,   93,
   93,   44,   91,   49,   41,   51,   16,   44,   18,  123,
   33,  125,  123,   91,   24,   58,   59,   40,   33,  152,
   93,  276,   45,  123,   59,   40,  171,   37,  173,   40,
   45,   93,   33,   40,  276,   41,   44,   91,   41,   40,
  199,  123,   91,   40,   45,  123,  191,  125,   40,   40,
   93,   40,   33,   40,   64,  200,   40,   40,  276,   40,
   59,   59,   40,  208,   45,  263,   59,   61,   91,  257,
  258,  259,  260,  261,  123,   37,   91,   59,   93,   41,
   42,   43,   44,   45,   46,   47,  125,   91,  276,   41,
   91,  257,  258,  259,  260,  261,   58,   59,   60,   61,
   62,  257,  258,  259,  260,  261,   41,  276,  276,  262,
   91,   37,  276,  276,  276,   41,   42,   43,   40,   45,
   46,   47,  275,  279,   41,   59,   44,   41,  276,   91,
  125,   93,   41,   59,   60,  287,   62,  276,   44,  276,
  268,   41,  288,  257,  258,  259,  260,  261,  262,   41,
  264,  265,  266,  267,    0,  269,  270,  271,  272,  273,
  274,  275,  276,  288,  123,   91,  280,   41,  281,  282,
  276,  285,  276,  287,   59,  288,   41,  276,  292,  257,
  258,  259,  260,  261,  262,   41,  264,  265,  266,  267,
   59,  269,  270,  271,  272,  273,  274,  275,  125,  288,
    4,   42,  280,  170,  277,  278,   -1,  285,  257,  258,
  259,  260,  261,  262,  292,  264,  265,  266,  267,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,
   -1,  280,   -1,   -1,  288,  278,  285,   -1,  261,  262,
   -1,  264,   -1,  292,   -1,   -1,   -1,  262,  271,  264,
  273,  274,  275,   -1,   -1,   -1,  271,  280,  273,  274,
  275,  262,   -1,  264,   -1,  280,   -1,   -1,   -1,  292,
  271,   -1,  273,  274,  275,  276,   -1,  292,   -1,  280,
   -1,  262,   -1,  264,   -1,   41,   -1,   -1,   44,   -1,
  271,  292,  273,  274,  275,   -1,   -1,   -1,   -1,  280,
   37,   -1,   58,   59,   41,   42,   43,   -1,   45,   46,
   47,  292,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   60,   37,   62,  288,   -1,   41,   42,
   43,   -1,   45,   46,   47,  125,   37,   93,   -1,   -1,
   41,   42,   43,   -1,   45,   46,   47,   60,   -1,   62,
   -1,  277,  278,   -1,   91,  281,  282,  283,  284,   60,
   37,   62,  288,   -1,   -1,   42,   43,   44,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   60,   41,   62,   37,   44,   -1,   -1,
   91,   42,   43,   -1,   45,   46,   47,   -1,   37,   -1,
   -1,   58,   59,   42,   43,   -1,   45,   46,   47,   60,
   -1,   62,   -1,   -1,   91,   -1,   -1,   -1,   -1,   58,
   37,   60,   -1,   62,   -1,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   91,   37,   93,   60,   -1,   62,   42,   43,   -1,   45,
   46,   47,   91,   -1,   -1,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,  257,  258,  259,
  260,  261,   -1,   37,   91,   -1,   93,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,  279,
   41,   -1,   -1,   44,   37,   91,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   -1,   37,   58,   59,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   60,   -1,   62,
   -1,  277,  278,   -1,   -1,   -1,   -1,   91,   -1,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   93,   -1,  281,  282,  283,  284,   91,   37,
   -1,  288,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   91,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   60,   -1,   62,  288,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,  288,  257,  258,
  259,  260,  261,   -1,   -1,   -1,   41,   -1,   -1,   44,
  277,  278,   -1,   91,  281,  282,  283,  284,   -1,   -1,
  279,  288,   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,  288,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   37,   93,  288,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   60,  288,   62,   -1,   -1,   -1,   41,   -1,   43,   44,
   45,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,  288,   58,   59,   60,   -1,   62,   -1,   -1,
   37,   91,   -1,   -1,   41,   42,   43,   44,   45,   46,
   47,   -1,   41,  277,  278,   44,   -1,  281,  282,  283,
  284,   58,   59,   60,  288,   62,  277,  278,   93,   58,
   59,   -1,  283,  284,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,  288,  277,  278,   -1,   -1,
  281,  282,  283,  284,   91,   37,   93,  288,   -1,   41,
   42,   43,   44,   45,   93,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
   -1,   -1,   -1,  281,  282,  283,  284,   37,   -1,   -1,
  288,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,
   60,   37,   62,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,  277,  278,   -1,   56,   -1,   -1,  283,  284,
   -1,   -1,   58,   59,   60,   -1,   62,   -1,   -1,   -1,
   37,   -1,   -1,   93,   41,   42,   43,   44,   45,   37,
   47,   -1,   -1,   41,   42,   43,   44,   45,   56,   47,
   -1,   58,   59,   60,   95,   62,   -1,   93,   -1,   -1,
   58,   59,   60,   41,   62,   43,   44,   45,   -1,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,  288,   -1,
   58,   59,   60,   -1,   62,   41,   93,   95,   44,   -1,
   -1,   -1,  277,  278,   -1,   93,  281,  282,  283,  284,
   -1,   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,  277,  278,
  171,   -1,  173,   -1,  283,  284,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  191,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  199,  200,
   -1,   -1,   -1,  171,   -1,  173,   -1,  208,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,  191,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  199,  200,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  208,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,  277,
  278,   -1,   62,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,   71,   72,   73,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   94,   -1,   96,   -1,   -1,   -1,
   -1,  277,  278,  103,   -1,   -1,  106,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  116,  117,  118,  119,
  120,  121,  122,  123,  124,  125,  126,  127,  128,  129,
   -1,  131,   -1,   -1,   -1,  135,   -1,   -1,   -1,   -1,
   -1,  141,   -1,  143,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  170,   -1,  172,   -1,   -1,   -1,   -1,   -1,   -1,  179,
   -1,   -1,  182,  183,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=292;
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
"SEALED","GUARDED","NEWSAMEARRAY","\"%%\"","UMINUS","EMPTY","VAR",
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
"Expr : Expr NEWSAMEARRAY Expr",
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

//#line 506 "Parser.y"
    
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
//#line 675 "Parser.java"
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
                		yyval.expr = new Tree.NewSameArray(val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
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
                    }
break;
case 88:
//#line 430 "Parser.y"
{
                        yyval.elist = new ArrayList<Tree.Expr>();
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 90:
//#line 438 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 91:
//#line 445 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 92:
//#line 449 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 93:
//#line 456 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 94:
//#line 462 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 95:
//#line 468 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 96:
//#line 474 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 97:
//#line 480 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 98:
//#line 484 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 99:
//#line 490 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 100:
//#line 494 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 101:
//#line 500 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1354 "Parser.java"
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
