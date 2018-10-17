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
public final static short JOINTARRAY=289;
public final static short DEFAULT=290;
public final static short UMINUS=293;
public final static short EMPTY=294;
public final static short VAR=295;
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
   15,   15,   15,   15,   30,   30,   30,   31,   32,   32,
   32,   29,   29,   33,   33,   17,   18,   21,   16,   34,
   34,   19,   19,   20,
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
    3,    3,    6,    6,    1,    1,    1,    3,    3,    0,
    1,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   15,   19,    0,   19,    0,    0,    0,    7,    8,
    6,    9,    0,    0,   12,   17,    0,    0,   18,   19,
   14,   10,    0,    4,    0,    0,    0,    0,    0,   11,
   13,    0,   23,    0,    0,    0,    0,    5,    0,    0,
    0,   28,   25,   22,   24,    0,   86,   76,    0,    0,
    0,    0,   98,    0,    0,    0,    0,   85,    0,    0,
    0,    0,    0,    0,   26,    0,   29,   38,   27,    0,
    0,   32,   33,   34,    0,    0,    0,   39,    0,    0,
    0,   57,   87,    0,    0,    0,    0,    0,   55,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   91,    0,   53,   30,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,    0,   35,   36,   37,    0,    0,    0,    0,    0,
   44,    0,    0,    0,    0,    0,   74,   75,    0,    0,
    0,   71,    0,   88,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,   77,    0,
    0,  104,    0,    0,    0,   89,    0,    0,    0,    0,
   96,    0,    0,    0,   43,   78,    0,    0,    0,   80,
    0,    0,   54,    0,    0,   99,    0,   79,   31,   84,
   83,    0,  100,   46,    0,   97,
};
final static short yydgoto[] = {                          3,
    4,    5,   77,   27,   44,   10,   16,   29,   45,   46,
   78,   56,   79,   80,   81,   82,   83,   84,   85,   86,
   87,   88,   99,  100,  142,  177,  178,   91,  189,   92,
   93,  113,  146,  206,
};
final static short yysindex[] = {                      -239,
 -247, -231,    0, -239,    0, -232, -234,    0, -233,  -75,
 -115,    0,    0, -221,    0,  287,  -52,  317,    0,    0,
    0,    0, -194, -118,    0,    0,   24,  -90,    0,    0,
    0,    0,  -89,    0,   44,   -5,  370,   49, -118,    0,
    0, -118,    0,  -85,   53,   61,   66,    0,    9, -118,
    9,    0,    0,    0,    0,    6,    0,    0,   72,   73,
  -22,  105,    0,   96,   78,   94,  104,    0,  106,  107,
  105,  105,   63,  -82,    0, -156,    0,    0,    0,   90,
  932,    0,    0,    0,   93,  108,  109,    0,   92,    0,
 -111,    0,    0,  105,  105,  105,   41,  932,    0,    0,
  130,   86,  105,  137,  140,  105, -105,  -11,  -11,  -94,
  522,    0,  -20,    0,    0,  105,  105,  105,  105,  105,
  105,  105,  105,  105,  105,  105,  105,  105,  105,  105,
    0,  105,    0,    0,    0,  105,  143,  577,  125,  613,
    0,  105,  144,   71,  932,   -3,    0,    0,  747,  145,
  147,    0,  -82,    0, 1020,  996,  -32,  -32,    7,    7,
   19,  -32,  -10,  -10,  -11,  -11,  -11,  -32,  -32,  328,
  932,  105,   46,  105,   46,  771,   65,    0,    0,  826,
  105,    0,  -79,  105,  105,    0,  -84,  105,  167,  168,
    0,  861,  -57,   46,    0,    0,  932,  172,  885,    0,
  105,  911,    0,  105,   46,    0,  -73,    0,    0,    0,
    0,  181,    0,    0,   46,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  223,    0,  112,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  171,    0,    0,    0,  204,    0,
    0,  204,    0,    0,    0,  205,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -56,    0,    0,    0,    0,
    0,  -55,    0,    0,    0,    0,    0,    0,    0,    0,
  -27,  -27,  -27,  -16,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  953,  494,
    0,    0,    0,  -27,  -56,  -27,   88,  192,    0,    0,
    0,    0,  -27,    0,    0,  -27,    0,  364,  406,    0,
    0,    0,    0,    0,    0,  -27,  -27,  -27,  -27,  -27,
  -27,  -27,  -27,  -27,  -27,  -27,  -27,  -27,  -27,  -27,
    0,  -27,    0,    0,    0,  -27,  114,    0,    0,    0,
    0,  -27,    0,  -27,   58,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  151,  -25,  439,  542,  622,  633,
 1058,   16, 1033, 1202,  430,  459,  468,  924, 1042,    0,
    4,  -21,  -56,  -27,  -56,    0,    0,    0,    0,    0,
  -27,    0,    0,  -27,  -27,    0,  157,  -27,    0,  218,
    0,    0,  -33,  -56,    0,    0,   83,    0,    0,    0,
  -27,    0,    0,  -18,  -56,    0,  135,    0,    0,    0,
    0,    0,    0,    0,  -56,    0,
};
final static short yygindex[] = {                         0,
    0,  257,   69,   80,   99,    0,   10,    0,  227,    0,
   21,    0,  -80,  -78, 1256,    0,    0,    0,    0,    0,
    0,    0, 1077, 1092,    0,    0,    0,    0,    0,  -53,
    0,    0,  102,    0,
};
final static int YYTABLESIZE=1491;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        101,
   36,   36,   42,  103,  128,   36,  101,   15,   74,  126,
  124,  101,  125,  131,  127,   70,  139,   96,   70,   93,
  112,    1,   42,  153,   18,  101,  128,   90,    6,    7,
    9,  126,   70,   70,  131,  131,  127,  182,   72,   37,
  181,   11,   12,  128,   40,   73,    2,   13,  126,  124,
   71,  125,  131,  127,   17,  128,   82,  101,  132,   82,
  126,  124,   40,  125,  131,  127,  130,   70,  129,   53,
   30,   55,  154,   82,   82,   82,   90,   82,   72,  132,
  132,   32,   34,   39,   26,   73,   26,   40,   42,  101,
   71,  101,  191,   49,  193,   72,   74,  132,   95,  186,
   97,   95,   73,   72,   50,   26,   51,   71,   82,  132,
   73,   94,   95,  207,   28,   71,   28,  103,   43,  114,
   48,   43,   33,   94,  213,  212,   94,   48,   52,   54,
   75,   52,   48,  104,  216,   28,   74,   72,   19,   20,
   21,   22,   23,  105,   73,  106,  107,   14,  115,   71,
   51,  133,  136,   74,   51,   51,   51,   51,   51,   51,
   51,   74,  102,   40,  137,  141,  134,  135,   52,  143,
  150,   51,   51,   51,   51,   51,  144,  147,   48,   57,
  148,  151,  172,  174,  179,   35,   38,  185,  184,  195,
   48,   69,   68,   52,   69,   74,  198,   52,   52,   52,
   52,   52,   52,   52,   51,  201,   51,  203,   69,   69,
  205,  181,  208,  214,   52,   52,   52,   52,   52,   50,
   50,  215,    1,  101,  101,  101,  101,  101,  101,    5,
  101,  101,  101,  101,   16,  101,  101,  101,  101,  101,
  101,  101,  101,   69,   21,   20,  101,   52,   50,   52,
  102,  101,   70,  101,   50,  122,  123,   50,   92,   45,
    8,  101,   19,   20,   21,   22,   23,   57,   47,   58,
   59,   60,   61,  190,   62,   63,   64,   65,   66,   67,
   68,    0,    0,    0,    0,   69,    0,  118,  119,    0,
   70,    0,   82,   82,  122,  123,   82,   82,   82,   82,
   76,    0,   19,   20,   21,   22,   23,   57,    0,   58,
   59,   60,   61,    0,   62,   63,   64,   65,   66,   67,
   68,    0,    0,  110,   57,   69,   58,    0,    0,    0,
   70,    0,   57,   64,   58,   66,   67,   68,    0,    0,
   76,   64,   69,   66,   67,   68,    0,    0,    0,   48,
   69,   48,   19,   20,   21,   22,   23,   76,   48,    0,
   48,   48,   48,   48,  128,   76,   57,   48,   58,  126,
  124,  101,  125,  131,  127,   64,    0,   66,   67,   68,
    0,    0,   48,    0,   69,  188,    0,  130,    0,  129,
   51,   51,    0,    0,   51,   51,   51,   51,    0,   76,
   72,   51,   51,    0,   72,   72,   72,   72,   72,    0,
   72,   25,    0,    0,    0,    0,    0,    0,  132,    0,
  187,   72,   72,   72,    0,   72,    0,   69,   69,    0,
    0,    0,    0,   52,   52,    0,    0,   52,   52,   52,
   52,   31,   73,    0,   52,   52,   73,   73,   73,   73,
   73,    0,   73,    0,    0,    0,   72,    0,    0,    0,
    0,    0,    0,   73,   73,   73,   60,   73,    0,    0,
   60,   60,   60,   60,   60,    0,   60,    0,    0,   67,
    0,    0,   67,    0,    0,    0,    0,   60,   60,   60,
    0,   60,    0,    0,   41,   61,   67,   67,   73,   61,
   61,   61,   61,   61,   62,   61,    0,    0,   62,   62,
   62,   62,   62,    0,   62,    0,   61,   61,   61,    0,
   61,    0,   60,    0,    0,   62,   62,   62,    0,   62,
   56,   67,    0,    0,   41,   56,   56,    0,   56,   56,
   56,    0,    0,   19,   20,   21,   22,   23,    0,    0,
    0,   61,   41,   56,    0,   56,    0,    0,  128,    0,
   62,    0,  152,  126,  124,   24,  125,  131,  127,    0,
    0,    0,    0,   19,   20,   21,   22,   23,    0,    0,
    0,  130,   68,  129,   56,   68,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   24,    0,    0,    0,   68,
   68,    0,    0,    0,  116,  117,    0,    0,  118,  119,
  120,  121,  132,  128,    0,  122,  123,  173,  126,  124,
    0,  125,  131,  127,    0,    0,   19,   20,   21,   22,
   23,    0,    0,    0,   68,    0,  130,    0,  129,    0,
   72,   72,    0,    0,   72,   72,   72,   72,   24,  128,
    0,   72,   72,  175,  126,  124,    0,  125,  131,  127,
    0,    0,   63,    0,    0,   63,    0,  132,    0,    0,
    0,    0,  130,   64,  129,    0,   64,    0,    0,   63,
   63,    0,   73,   73,    0,    0,   73,   73,   73,   73,
   64,   64,    0,   73,   73,    0,    0,    0,    0,    0,
    0,    0,    0,  132,    0,    0,   60,   60,    0,    0,
   60,   60,   60,   60,   63,   67,   67,   60,   60,    0,
    0,   67,   67,    0,    0,   64,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   61,   61,    0,    0,   61,
   61,   61,   61,    0,   62,   62,   61,   61,   62,   62,
   62,   62,    0,    0,    0,   62,   62,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,   56,    0,    0,   56,   56,   56,   56,    0,    0,
    0,   56,   56,  128,    0,    0,    0,    0,  126,  124,
  183,  125,  131,  127,    0,    0,    0,    0,  116,  117,
    0,    0,  118,  119,  120,  121,  130,  128,  129,  122,
  123,    0,  126,  124,    0,  125,  131,  127,   68,   68,
    0,    0,    0,    0,   68,   68,    0,    0,  194,    0,
  130,    0,  129,    0,    0,    0,    0,  132,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  116,  117,    0,    0,  118,  119,  120,
  121,  132,  128,    0,  122,  123,    0,  126,  124,    0,
  125,  131,  127,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  130,    0,  129,    0,  116,
  117,    0,    0,  118,  119,  120,  121,  128,   63,   63,
  122,  123,  126,  124,    0,  125,  131,  127,    0,   64,
   64,    0,    0,    0,    0,    0,  132,    0,  196,  204,
  130,  128,  129,    0,    0,  209,  126,  124,    0,  125,
  131,  127,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  130,    0,  129,  128,    0,    0,
    0,  132,  126,  124,    0,  125,  131,  127,    0,    0,
    0,    0,    0,    0,   66,    0,    0,   66,  128,    0,
  130,    0,  129,  126,  124,  132,  125,  131,  127,    0,
    0,   66,   66,    0,    0,    0,    0,    0,    0,   55,
    0,  130,    0,  129,   55,   55,    0,   55,   55,   55,
    0,  132,    0,  211,    0,    0,    0,    0,    0,    0,
    0,    0,   55,    0,   55,    0,   66,    0,    0,    0,
    0,    0,  132,  116,  117,    0,    0,  118,  119,  120,
  121,    0,  128,    0,  122,  123,    0,  126,  124,    0,
  125,  131,  127,   55,    0,    0,    0,  116,  117,    0,
    0,  118,  119,  120,  121,  130,  128,  129,  122,  123,
    0,  126,  124,    0,  125,  131,  127,    0,    0,    0,
    0,    0,    0,   58,    0,   58,   58,   58,    0,  130,
    0,  129,   65,    0,    0,   65,  132,    0,    0,    0,
   58,   58,   58,    0,   58,    0,    0,    0,   81,   65,
   65,   81,  116,  117,    0,    0,  118,  119,  120,  121,
  132,    0,    0,  122,  123,   81,   81,   81,    0,   81,
    0,    0,    0,    0,    0,   58,    0,    0,    0,    0,
    0,    0,   89,    0,   65,    0,    0,  116,  117,    0,
    0,  118,  119,  120,  121,    0,    0,   90,  122,  123,
   81,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  116,  117,    0,    0,  118,  119,  120,  121,    0,
    0,   89,  122,  123,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   90,  116,  117,    0,
    0,  118,  119,  120,  121,    0,    0,    0,  122,  123,
   66,   66,    0,    0,    0,    0,   66,   66,  116,  117,
    0,    0,  118,  119,  120,  121,    0,    0,    0,  122,
  123,    0,    0,    0,    0,    0,    0,    0,    0,   55,
   55,    0,    0,   55,   55,   55,   55,    0,    0,    0,
   55,   55,   59,    0,   59,   59,   59,    0,    0,   89,
    0,   89,    0,    0,    0,    0,    0,    0,    0,   59,
   59,   59,    0,   59,   90,    0,   90,    0,    0,    0,
   89,    0,  116,    0,    0,    0,  118,  119,  120,  121,
   89,   89,    0,  122,  123,   90,    0,    0,    0,    0,
    0,   89,    0,    0,   59,   90,   90,    0,    0,    0,
  118,  119,  120,  121,    0,    0,   90,  122,  123,   58,
   58,    0,    0,   58,   58,   58,   58,   98,   65,   65,
   58,   58,    0,    0,   65,   65,  108,  109,  111,    0,
    0,    0,    0,    0,   81,   81,    0,    0,   81,   81,
   81,   81,    0,    0,    0,   81,   81,    0,    0,  138,
    0,  140,    0,    0,    0,    0,    0,    0,  145,    0,
    0,  149,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  155,  156,  157,  158,  159,  160,  161,  162,  163,
  164,  165,  166,  167,  168,  169,    0,  170,    0,    0,
    0,  171,    0,    0,    0,    0,    0,  176,    0,  180,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  145,    0,  192,
    0,    0,    0,    0,    0,    0,  197,    0,    0,  199,
  200,    0,    0,  202,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  210,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   59,   59,
    0,    0,   59,   59,   59,   59,    0,    0,    0,   59,
   59,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   91,   91,   59,   59,   37,   91,   40,  123,   91,   42,
   43,   45,   45,   46,   47,   41,   95,   40,   44,   41,
   74,  261,   41,   44,   15,   59,   37,   44,  276,  261,
  263,   42,   58,   59,   46,   46,   47,   41,   33,   30,
   44,  276,  276,   37,   41,   40,  286,  123,   42,   43,
   45,   45,   46,   47,  276,   37,   41,   91,   91,   44,
   42,   43,   59,   45,   46,   47,   60,   93,   62,   49,
  123,   51,   93,   58,   59,   60,   93,   62,   33,   91,
   91,  276,   59,   40,   16,   40,   18,   93,   40,  123,
   45,  125,  173,   41,  175,   33,   91,   91,   41,  153,
  123,   44,   40,   33,   44,   37,   41,   45,   93,   91,
   40,   40,   40,  194,   16,   45,   18,   40,   39,  276,
   33,   42,   24,   41,  205,  204,   44,   40,  123,   50,
  125,  123,   45,   40,  215,   37,   91,   33,  257,  258,
  259,  260,  261,   40,   40,   40,   40,  263,   59,   45,
   37,   59,   61,   91,   41,   42,   43,   44,   45,   46,
   47,   91,   64,   93,  276,  125,   59,   59,  123,   40,
  276,   58,   59,   60,   61,   62,   91,   41,   91,  262,
   41,  276,   40,   59,   41,  276,  276,   41,   44,  125,
  276,   41,  275,   37,   44,   91,  276,   41,   42,   43,
   44,   45,   46,   47,   91,  290,   93,   41,   58,   59,
  268,   44,   41,  287,   58,   59,   60,   61,   62,  276,
  276,   41,    0,  257,  258,  259,  260,  261,  262,   59,
  264,  265,  266,  267,  123,  269,  270,  271,  272,  273,
  274,  275,  276,   93,   41,   41,  280,   91,  276,   93,
   59,  285,  278,  287,  276,  288,  289,  276,   41,  125,
    4,  295,  257,  258,  259,  260,  261,  262,   42,  264,
  265,  266,  267,  172,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,   -1,   -1,  280,   -1,  281,  282,   -1,
  285,   -1,  277,  278,  288,  289,  281,  282,  283,  284,
  295,   -1,  257,  258,  259,  260,  261,  262,   -1,  264,
  265,  266,  267,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,  261,  262,  280,  264,   -1,   -1,   -1,
  285,   -1,  262,  271,  264,  273,  274,  275,   -1,   -1,
  295,  271,  280,  273,  274,  275,   -1,   -1,   -1,  262,
  280,  264,  257,  258,  259,  260,  261,  295,  271,   -1,
  273,  274,  275,  276,   37,  295,  262,  280,  264,   42,
   43,  276,   45,   46,   47,  271,   -1,  273,  274,  275,
   -1,   -1,  295,   -1,  280,   58,   -1,   60,   -1,   62,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,  295,
   37,  288,  289,   -1,   41,   42,   43,   44,   45,   -1,
   47,  125,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   93,   58,   59,   60,   -1,   62,   -1,  277,  278,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,  125,   37,   -1,  288,  289,   41,   42,   43,   44,
   45,   -1,   47,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   58,   59,   60,   37,   62,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,   58,   59,   60,
   -1,   62,   -1,   -1,  125,   37,   58,   59,   93,   41,
   42,   43,   44,   45,   37,   47,   -1,   -1,   41,   42,
   43,   44,   45,   -1,   47,   -1,   58,   59,   60,   -1,
   62,   -1,   93,   -1,   -1,   58,   59,   60,   -1,   62,
   37,   93,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,  257,  258,  259,  260,  261,   -1,   -1,
   -1,   93,   59,   60,   -1,   62,   -1,   -1,   37,   -1,
   93,   -1,   41,   42,   43,  279,   45,   46,   47,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,   -1,   -1,
   -1,   60,   41,   62,   91,   44,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  279,   -1,   -1,   -1,   58,
   59,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   91,   37,   -1,  288,  289,   41,   42,   43,
   -1,   45,   46,   47,   -1,   -1,  257,  258,  259,  260,
  261,   -1,   -1,   -1,   93,   -1,   60,   -1,   62,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,  279,   37,
   -1,  288,  289,   41,   42,   43,   -1,   45,   46,   47,
   -1,   -1,   41,   -1,   -1,   44,   -1,   91,   -1,   -1,
   -1,   -1,   60,   41,   62,   -1,   44,   -1,   -1,   58,
   59,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   58,   59,   -1,  288,  289,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   91,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   93,  277,  278,  288,  289,   -1,
   -1,  283,  284,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,  277,  278,  288,  289,  281,  282,
  283,  284,   -1,   -1,   -1,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,  288,  289,   37,   -1,   -1,   -1,   -1,   42,   43,
   44,   45,   46,   47,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   60,   37,   62,  288,
  289,   -1,   42,   43,   -1,   45,   46,   47,  277,  278,
   -1,   -1,   -1,   -1,  283,  284,   -1,   -1,   58,   -1,
   60,   -1,   62,   -1,   -1,   -1,   -1,   91,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   91,   37,   -1,  288,  289,   -1,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   37,  277,  278,
  288,  289,   42,   43,   -1,   45,   46,   47,   -1,  277,
  278,   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,   59,
   60,   37,   62,   -1,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   60,   -1,   62,   37,   -1,   -1,
   -1,   91,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,   37,   -1,
   60,   -1,   62,   42,   43,   91,   45,   46,   47,   -1,
   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,   37,
   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,   47,
   -1,   91,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   62,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   91,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   37,   -1,  288,  289,   -1,   42,   43,   -1,
   45,   46,   47,   91,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   60,   37,   62,  288,  289,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   41,   -1,   43,   44,   45,   -1,   60,
   -1,   62,   41,   -1,   -1,   44,   91,   -1,   -1,   -1,
   58,   59,   60,   -1,   62,   -1,   -1,   -1,   41,   58,
   59,   44,  277,  278,   -1,   -1,  281,  282,  283,  284,
   91,   -1,   -1,  288,  289,   58,   59,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   56,   -1,   93,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   56,  288,  289,
   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   95,  288,  289,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   95,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,  288,  289,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,  288,
  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
  288,  289,   41,   -1,   43,   44,   45,   -1,   -1,  173,
   -1,  175,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   59,   60,   -1,   62,  173,   -1,  175,   -1,   -1,   -1,
  194,   -1,  277,   -1,   -1,   -1,  281,  282,  283,  284,
  204,  205,   -1,  288,  289,  194,   -1,   -1,   -1,   -1,
   -1,  215,   -1,   -1,   93,  204,  205,   -1,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,  215,  288,  289,  277,
  278,   -1,   -1,  281,  282,  283,  284,   62,  277,  278,
  288,  289,   -1,   -1,  283,  284,   71,   72,   73,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,  288,  289,   -1,   -1,   94,
   -1,   96,   -1,   -1,   -1,   -1,   -1,   -1,  103,   -1,
   -1,  106,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  116,  117,  118,  119,  120,  121,  122,  123,  124,
  125,  126,  127,  128,  129,  130,   -1,  132,   -1,   -1,
   -1,  136,   -1,   -1,   -1,   -1,   -1,  142,   -1,  144,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  172,   -1,  174,
   -1,   -1,   -1,   -1,   -1,   -1,  181,   -1,   -1,  184,
  185,   -1,   -1,  188,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  201,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,  288,
  289,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=295;
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
"SEALED","GUARDED","NEWSAMEARRAY","JOINTARRAY","DEFAULT","\"%%\"","\"++\"",
"UMINUS","EMPTY","VAR",
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
"Expr : Expr JOINTARRAY Expr",
"Expr : Expr '[' Expr ':' Expr ']'",
"Expr : Expr '[' Expr ']' DEFAULT Expr",
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

//#line 519 "Parser.y"
    
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
//#line 722 "Parser.java"
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
//#line 59 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 65 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 69 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 79 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 85 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 97 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 105 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 111 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 115 "Parser.y"
{
						yyval.cdef = new Tree.Sealed(val_peek(5).ident, val_peek(3).ident, val_peek(1).flist, val_peek(7).loc);
					}
break;
case 14:
//#line 119 "Parser.y"
{
						yyval.cdef = new Tree.Sealed(val_peek(3).ident, null, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 15:
//#line 125 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 16:
//#line 129 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 17:
//#line 135 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 18:
//#line 139 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 19:
//#line 143 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 21:
//#line 151 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 22:
//#line 158 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 23:
//#line 162 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 24:
//#line 169 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 173 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 26:
//#line 179 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 27:
//#line 185 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 28:
//#line 189 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 29:
//#line 196 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 30:
//#line 201 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 31:
//#line 207 "Parser.y"
{
                		yyval.stmt = new Tree.SCopyExpr(val_peek(3).ident, val_peek(1).expr, val_peek(5).loc);
                	}
break;
case 40:
//#line 221 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 41:
//#line 225 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 42:
//#line 229 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 235 "Parser.y"
{
                        yyval.stmt = new Tree.Guarded(val_peek(2).slist, val_peek(1).stmt, val_peek(4).loc);
                    }
break;
case 44:
//#line 239 "Parser.y"
{
                        yyval.stmt = new Tree.Guarded(null, null, val_peek(2).loc);
                    }
break;
case 45:
//#line 244 "Parser.y"
{
                        yyval.stmt = new Tree.IfSubStmt(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
                    }
break;
case 46:
//#line 249 "Parser.y"
{
                        yyval.stmt = new Tree.IfSubStmt(val_peek(3).expr, val_peek(1).stmt, val_peek(3).loc);
                    }
break;
case 47:
//#line 254 "Parser.y"
{
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 48:
//#line 258 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.slist = new ArrayList<Tree>();
                    }
break;
case 50:
//#line 266 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 51:
//#line 272 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 52:
//#line 279 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 53:
//#line 284 "Parser.y"
{
                		yyval.lvalue = new Tree.Var(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 54:
//#line 290 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 55:
//#line 299 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 58:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 353 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 357 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 72:
//#line 361 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 365 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 74:
//#line 369 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 373 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 76:
//#line 377 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 77:
//#line 383 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 78:
//#line 387 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 79:
//#line 391 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 80:
//#line 395 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 81:
//#line 400 "Parser.y"
{
                		yyval.expr = new Tree.NewSameArray(val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 82:
//#line 404 "Parser.y"
{
                		yyval.expr = new Tree.JointArray(val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 83:
//#line 408 "Parser.y"
{
                		yyval.expr = new Tree.AccessArray(val_peek(5).expr, val_peek(3).expr, val_peek(1).expr, val_peek(5).loc);
                	}
break;
case 84:
//#line 412 "Parser.y"
{
                		yyval.expr = new Tree.DefaultArray(val_peek(5).expr, val_peek(3).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 85:
//#line 418 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 86:
//#line 422 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 88:
//#line 429 "Parser.y"
{
						yyval.expr = new Tree.ArrayConstant(val_peek(1).elist, val_peek(2).loc);
					}
break;
case 89:
//#line 435 "Parser.y"
{
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 90:
//#line 439 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 91:
//#line 443 "Parser.y"
{
                        yyval.elist = new ArrayList<Tree.Expr>();
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 93:
//#line 451 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 94:
//#line 458 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 95:
//#line 462 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 96:
//#line 469 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 97:
//#line 475 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 98:
//#line 481 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 99:
//#line 487 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 100:
//#line 493 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 101:
//#line 497 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 102:
//#line 503 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 103:
//#line 507 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 104:
//#line 513 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1419 "Parser.java"
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
