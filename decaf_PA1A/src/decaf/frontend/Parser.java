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
public final static short UMINUS=288;
public final static short EMPTY=289;
public final static short VAR=290;
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
   30,   30,   29,   29,   31,   31,   17,   18,   21,   16,
   32,   32,   19,   19,   20,
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
    1,    1,    1,    0,    3,    1,    5,    9,    1,    6,
    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   15,   19,    0,   19,    0,    0,    0,    7,    8,
    6,    9,    0,    0,   12,   17,    0,    0,   18,   19,
   14,   10,    0,    4,    0,    0,    0,    0,    0,   11,
   13,    0,   23,    0,    0,    0,    0,    5,    0,    0,
    0,   28,   25,   22,   24,    0,   82,   76,    0,    0,
    0,    0,   89,    0,    0,    0,    0,   81,    0,    0,
    0,    0,    0,   26,    0,   29,   38,   27,    0,    0,
   32,   33,   34,    0,    0,    0,   39,    0,    0,    0,
   57,    0,    0,    0,    0,    0,   55,   56,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
   30,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   49,    0,   35,   36,   37,    0,
    0,    0,    0,    0,   44,    0,    0,    0,    0,    0,
   74,   75,    0,    0,    0,   71,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,   77,    0,
    0,   95,    0,    0,    0,   52,    0,    0,   87,    0,
    0,    0,   43,   78,    0,    0,    0,   80,   54,    0,
    0,   90,    0,   79,   31,    0,   91,   46,    0,   88,
};
final static short yydgoto[] = {                          3,
    4,    5,   76,   27,   44,   10,   16,   29,   45,   46,
   77,   56,   78,   79,   80,   81,   82,   83,   84,   85,
   86,   87,   97,   98,  136,  167,  168,   90,  177,   91,
  140,  192,
};
final static short yysindex[] = {                      -234,
 -256, -230,    0, -234,    0, -208, -216,    0, -215,  -56,
 -115,    0,    0, -204,    0,  -82,  -47,  271,    0,    0,
    0,    0, -191, -124,    0,    0,   30,  -87,    0,    0,
    0,    0,  -85,    0,   65,   14,  464,   68, -124,    0,
    0, -124,    0,  -74,   70,   71,   72,    0,  -11, -124,
  -11,    0,    0,    0,    0,    1,    0,    0,   76,   78,
  -37,   98,    0,  -54,   79,   80,   81,    0,   83,   85,
   98,   98,   55,    0, -148,    0,    0,    0,   82,  808,
    0,    0,    0,   97,  100,  101,    0,   96,    0, -146,
    0,   98,   98,   98,   15,  808,    0,    0,   99,   51,
   98,  103,  120,   98, -113,  -21,  -21, -112,  463,    0,
    0,   98,   98,   98,   98,   98,   98,   98,   98,   98,
   98,   98,   98,   98,    0,   98,    0,    0,    0,   98,
  125,  474,  112,  496,    0,   98,  131,   69,  808,   -9,
    0,    0,  524,  129,  133,    0,  866,  840,   36,   36,
  -32,  -32,    7,    7,  -21,  -21,  -21,   36,   36,  535,
  808,   98,   35,   98,   35,  559,   57,    0,    0,  614,
   98,    0,  -96,   98,   98,    0,  142,  140,    0,  723,
  -83,   35,    0,    0,  808,  145,  784,    0,    0,   98,
   35,    0, -100,    0,    0,  149,    0,    0,   35,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  200,    0,   89,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  155,    0,    0,    0,  167,    0,
    0,  167,    0,    0,    0,  174,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0,    0,
  -60,  -60,  -60,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  819,  436,    0,
    0,  -60,  -58,  -60,   77,  158,    0,    0,    0,    0,
  -60,    0,    0,  -60,    0,  151,  356,    0,    0,    0,
    0,  -60,  -60,  -60,  -60,  -60,  -60,  -60,  -60,  -60,
  -60,  -60,  -60,  -60,    0,  -60,    0,    0,    0,  -60,
  108,    0,    0,    0,    0,  -60,    0,  -60,   43,    0,
    0,    0,    0,    0,    0,    0,  -22,    4,    6,  516,
   88,  532,  874,  894,  365,  401,  410,  691,  695,    0,
   -8,  -25,  -58,  -60,  -58,    0,    0,    0,    0,    0,
  -60,    0,    0,  -60,  -60,    0,    0,  179,    0,    0,
  -33,  -58,    0,    0,   50,    0,    0,    0,    0,  -23,
  -58,    0,  105,    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  217,   40,   54,    5,    0,    9,    0,  181,    0,
   52,    0, -125,  -84, 1069,    0,    0,    0,    0,    0,
    0,    0,  302,  490,    0,    0,    0,    0,    0,    0,
   73,    0,
};
final static int YYTABLESIZE=1244;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         92,
   42,   94,   94,   36,  122,   36,   92,   15,  133,  120,
  118,   92,  119,  125,  121,   84,   36,   42,   69,    6,
   28,   69,   28,   18,  125,   92,    1,  124,   33,  123,
    7,  172,   40,   72,  171,   69,   69,  179,   37,  181,
   73,   28,   25,  122,   70,   71,   67,   70,  120,   67,
   40,    2,  125,  121,    9,   26,  193,   26,  126,   11,
   12,   70,   70,   67,   67,  197,   13,   72,  100,  126,
   69,   17,  122,  200,   73,   30,   26,  120,  118,   71,
  119,  125,  121,   86,   32,   95,   86,   72,   34,   92,
   85,   92,   43,   85,   73,   43,   70,  126,   67,   71,
   53,   72,   55,   54,   39,  196,   40,   42,   73,   48,
   49,   52,   51,   71,   50,   92,   48,   93,  101,  102,
  103,   48,  104,   52,  105,   74,  126,  110,   63,  131,
   72,   63,   19,   20,   21,   22,   23,   73,  137,  135,
  111,  138,   71,  141,   51,   63,   63,   14,   51,   51,
   51,   51,   51,   51,   51,  127,  130,   52,  128,  129,
  142,   40,  144,  145,  162,   51,   51,   51,   51,   51,
  164,  169,  174,  175,   19,   20,   21,   22,   23,  186,
   63,  183,  189,  171,  191,  194,  198,   72,   35,  199,
   38,   72,   72,   72,   72,   72,   24,   72,   51,    1,
   51,   48,   19,   20,   21,   22,   23,   21,   72,   72,
   72,   16,   72,    5,   20,   50,   93,   50,   50,   83,
    8,   99,   47,   92,   92,   92,   92,   92,   92,   45,
   92,   92,   92,   92,  178,   92,   92,   92,   92,   92,
   92,   92,   92,   72,    0,    0,   92,    0,  114,  115,
   50,   92,   50,   92,   69,   69,   92,   19,   20,   21,
   22,   23,   57,    0,   58,   59,   60,   61,    0,   62,
   63,   64,   65,   66,   67,   68,    0,    0,    0,    0,
   69,   70,   67,   67,    0,   70,    0,    0,   67,   67,
   75,   19,   20,   21,   22,   23,   57,    0,   58,   59,
   60,   61,    0,   62,   63,   64,   65,   66,   67,   68,
    0,    0,    0,    0,   69,  108,   57,    0,   58,   70,
    0,    0,    0,    0,   75,   64,    0,   66,   67,   68,
   57,    0,   58,    0,   69,    0,    0,    0,   48,   64,
   48,   66,   67,   68,   75,    0,    0,   48,   69,   48,
   48,   48,   48,    0,    0,    0,   48,   88,   75,   57,
    0,   58,    0,    0,   63,   63,   48,    0,   64,    0,
   66,   67,   68,    0,    0,    0,    0,   69,    0,    0,
    0,    0,    0,    0,   51,   51,    0,   75,   51,   51,
   51,   51,   73,    0,   88,   31,   73,   73,   73,   73,
   73,   60,   73,    0,    0,   60,   60,   60,   60,   60,
    0,   60,    0,   73,   73,   73,    0,   73,    0,    0,
    0,    0,   60,   60,   60,    0,   60,   72,   72,    0,
    0,   72,   72,   72,   72,    0,    0,   61,    0,    0,
    0,   61,   61,   61,   61,   61,   62,   61,   73,    0,
   62,   62,   62,   62,   62,    0,   62,   60,   61,   61,
   61,    0,   61,    0,   88,    0,   88,   62,   62,   62,
    0,   62,   56,    0,    0,    0,   41,   56,   56,    0,
   56,   56,   56,   88,    0,    0,    0,    0,    0,    0,
    0,   88,   88,   61,   41,   56,    0,   56,    0,  122,
   88,    0,   62,  146,  120,  118,    0,  119,  125,  121,
  122,    0,    0,    0,  163,  120,  118,    0,  119,  125,
  121,    0,  124,    0,  123,    0,   56,   19,   20,   21,
   22,   23,  122,  124,    0,  123,  165,  120,  118,    0,
  119,  125,  121,    0,    0,   89,    0,    0,    0,   24,
    0,    0,    0,  126,    0,  124,   68,  123,    0,   68,
  122,    0,    0,    0,  126,  120,  118,  173,  119,  125,
  121,  122,   64,   68,   68,   64,  120,  118,    0,  119,
  125,  121,   89,  124,    0,  123,  126,    0,   41,   64,
   64,    0,    0,    0,  124,  122,  123,    0,    0,    0,
  120,  118,    0,  119,  125,  121,    0,    0,   68,    0,
    0,    0,    0,    0,  126,    0,  182,    0,  124,    0,
  123,    0,    0,    0,   64,  126,    0,  176,    0,    0,
    0,    0,   73,   73,    0,    0,   73,   73,   73,   73,
    0,   60,   60,    0,    0,   60,   60,   60,   60,  126,
  122,    0,   89,    0,   89,  120,  118,    0,  119,  125,
  121,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   89,    0,  124,    0,  123,    0,   61,   61,   89,
   89,   61,   61,   61,   61,    0,   62,   62,   89,    0,
   62,   62,   62,   62,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  126,    0,  184,    0,    0,    0,
    0,    0,   56,   56,    0,    0,   56,   56,   56,   56,
   19,   20,   21,   22,   23,    0,    0,    0,    0,    0,
    0,   66,    0,    0,   66,   65,    0,    0,   65,  112,
  113,    0,   24,  114,  115,  116,  117,    0,   66,   66,
  112,  113,   65,   65,  114,  115,  116,  117,    0,  122,
    0,    0,    0,    0,  120,  118,    0,  119,  125,  121,
    0,    0,  112,  113,    0,    0,  114,  115,  116,  117,
    0,  190,  124,   66,  123,    0,    0,   65,    0,    0,
    0,    0,   68,   68,    0,    0,    0,    0,   68,   68,
  112,  113,    0,    0,  114,  115,  116,  117,   64,   64,
    0,  112,  113,  126,    0,  114,  115,  116,  117,    0,
  122,    0,    0,    0,  195,  120,  118,    0,  119,  125,
  121,    0,    0,    0,    0,  112,  113,    0,    0,  114,
  115,  116,  117,  124,  122,  123,    0,    0,    0,  120,
  118,    0,  119,  125,  121,   55,    0,    0,    0,    0,
   55,   55,    0,   55,   55,   55,    0,  124,    0,  123,
    0,    0,    0,    0,  126,    0,  122,    0,   55,    0,
   55,  120,  118,    0,  119,  125,  121,    0,    0,    0,
  112,  113,    0,    0,  114,  115,  116,  117,  126,  124,
    0,  123,  122,    0,    0,    0,    0,  120,  118,   55,
  119,  125,  121,    0,   58,    0,   58,   58,   58,    0,
    0,    0,    0,    0,    0,  124,    0,  123,    0,    0,
  126,   58,   58,   58,   59,   58,   59,   59,   59,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   59,   59,   59,    0,   59,  126,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   58,   66,   66,    0,
    0,   65,   65,   66,   66,    0,    0,   65,   65,    0,
    0,    0,    0,    0,    0,    0,   59,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  112,
  113,    0,    0,  114,  115,  116,  117,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  112,  113,    0,    0,  114,  115,  116,  117,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  112,  113,    0,    0,  114,  115,
  116,  117,    0,    0,    0,   55,   55,    0,    0,   55,
   55,   55,   55,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  112,    0,    0,    0,
  114,  115,  116,  117,    0,    0,    0,    0,    0,    0,
   96,    0,    0,    0,    0,    0,    0,    0,    0,  106,
  107,  109,    0,    0,    0,    0,  114,  115,  116,  117,
   58,   58,    0,    0,   58,   58,   58,   58,    0,    0,
  132,    0,  134,    0,    0,    0,    0,    0,    0,  139,
   59,   59,  143,    0,   59,   59,   59,   59,    0,    0,
  147,  148,  149,  150,  151,  152,  153,  154,  155,  156,
  157,  158,  159,    0,  160,    0,    0,    0,  161,    0,
    0,    0,    0,    0,  166,    0,  170,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  139,    0,  180,    0,    0,    0,    0,    0,    0,  185,
    0,    0,  187,  188,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   40,   91,   37,   91,   40,  123,   93,   42,
   43,   45,   45,   46,   47,   41,   91,   41,   41,  276,
   16,   44,   18,   15,   46,   59,  261,   60,   24,   62,
  261,   41,   41,   33,   44,   58,   59,  163,   30,  165,
   40,   37,  125,   37,   41,   45,   41,   44,   42,   44,
   59,  286,   46,   47,  263,   16,  182,   18,   91,  276,
  276,   58,   59,   58,   59,  191,  123,   33,   64,   91,
   93,  276,   37,  199,   40,  123,   37,   42,   43,   45,
   45,   46,   47,   41,  276,  123,   44,   33,   59,  123,
   41,  125,   39,   44,   40,   42,   93,   91,   93,   45,
   49,   33,   51,   50,   40,  190,   93,   40,   40,   33,
   41,  123,   41,   45,   44,   40,   40,   40,   40,   40,
   40,   45,   40,  123,   40,  125,   91,  276,   41,  276,
   33,   44,  257,  258,  259,  260,  261,   40,   40,  125,
   59,   91,   45,   41,   37,   58,   59,  263,   41,   42,
   43,   44,   45,   46,   47,   59,   61,  123,   59,   59,
   41,   93,  276,  276,   40,   58,   59,   60,   61,   62,
   59,   41,   44,   41,  257,  258,  259,  260,  261,  276,
   93,  125,   41,   44,  268,   41,  287,   37,  276,   41,
  276,   41,   42,   43,   44,   45,  279,   47,   91,    0,
   93,  276,  257,  258,  259,  260,  261,   41,   58,   59,
   60,  123,   62,   59,   41,  276,   59,  276,  276,   41,
    4,  276,   42,  257,  258,  259,  260,  261,  262,  125,
  264,  265,  266,  267,  162,  269,  270,  271,  272,  273,
  274,  275,  276,   93,   -1,   -1,  280,   -1,  281,  282,
  276,  285,  276,  287,  277,  278,  290,  257,  258,  259,
  260,  261,  262,   -1,  264,  265,  266,  267,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,   -1,
  280,  278,  277,  278,   -1,  285,   -1,   -1,  283,  284,
  290,  257,  258,  259,  260,  261,  262,   -1,  264,  265,
  266,  267,   -1,  269,  270,  271,  272,  273,  274,  275,
   -1,   -1,   -1,   -1,  280,  261,  262,   -1,  264,  285,
   -1,   -1,   -1,   -1,  290,  271,   -1,  273,  274,  275,
  262,   -1,  264,   -1,  280,   -1,   -1,   -1,  262,  271,
  264,  273,  274,  275,  290,   -1,   -1,  271,  280,  273,
  274,  275,  276,   -1,   -1,   -1,  280,   56,  290,  262,
   -1,  264,   -1,   -1,  277,  278,  290,   -1,  271,   -1,
  273,  274,  275,   -1,   -1,   -1,   -1,  280,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,  290,  281,  282,
  283,  284,   37,   -1,   93,  125,   41,   42,   43,   44,
   45,   37,   47,   -1,   -1,   41,   42,   43,   44,   45,
   -1,   47,   -1,   58,   59,   60,   -1,   62,   -1,   -1,
   -1,   -1,   58,   59,   60,   -1,   62,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   37,   -1,   -1,
   -1,   41,   42,   43,   44,   45,   37,   47,   93,   -1,
   41,   42,   43,   44,   45,   -1,   47,   93,   58,   59,
   60,   -1,   62,   -1,  163,   -1,  165,   58,   59,   60,
   -1,   62,   37,   -1,   -1,   -1,   41,   42,   43,   -1,
   45,   46,   47,  182,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  190,  191,   93,   59,   60,   -1,   62,   -1,   37,
  199,   -1,   93,   41,   42,   43,   -1,   45,   46,   47,
   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   60,   -1,   62,   -1,   91,  257,  258,  259,
  260,  261,   37,   60,   -1,   62,   41,   42,   43,   -1,
   45,   46,   47,   -1,   -1,   56,   -1,   -1,   -1,  279,
   -1,   -1,   -1,   91,   -1,   60,   41,   62,   -1,   44,
   37,   -1,   -1,   -1,   91,   42,   43,   44,   45,   46,
   47,   37,   41,   58,   59,   44,   42,   43,   -1,   45,
   46,   47,   93,   60,   -1,   62,   91,   -1,  125,   58,
   59,   -1,   -1,   -1,   60,   37,   62,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   93,   -1,
   -1,   -1,   -1,   -1,   91,   -1,   58,   -1,   60,   -1,
   62,   -1,   -1,   -1,   93,   91,   -1,   93,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   91,
   37,   -1,  163,   -1,  165,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  182,   -1,   60,   -1,   62,   -1,  277,  278,  190,
  191,  281,  282,  283,  284,   -1,  277,  278,  199,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   91,   -1,   93,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
  257,  258,  259,  260,  261,   -1,   -1,   -1,   -1,   -1,
   -1,   41,   -1,   -1,   44,   41,   -1,   -1,   44,  277,
  278,   -1,  279,  281,  282,  283,  284,   -1,   58,   59,
  277,  278,   58,   59,  281,  282,  283,  284,   -1,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,   59,   60,   93,   62,   -1,   -1,   93,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,  283,  284,
  277,  278,   -1,   -1,  281,  282,  283,  284,  277,  278,
   -1,  277,  278,   91,   -1,  281,  282,  283,  284,   -1,
   37,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   60,   37,   62,   -1,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   91,   -1,   37,   -1,   60,   -1,
   62,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   91,   60,
   -1,   62,   37,   -1,   -1,   -1,   -1,   42,   43,   91,
   45,   46,   47,   -1,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,   -1,
   91,   58,   59,   60,   41,   62,   43,   44,   45,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   58,   59,   60,   -1,   62,   91,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   93,  277,  278,   -1,
   -1,  277,  278,  283,  284,   -1,   -1,  283,  284,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,   -1,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   71,
   72,   73,   -1,   -1,   -1,   -1,  281,  282,  283,  284,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   92,   -1,   94,   -1,   -1,   -1,   -1,   -1,   -1,  101,
  277,  278,  104,   -1,  281,  282,  283,  284,   -1,   -1,
  112,  113,  114,  115,  116,  117,  118,  119,  120,  121,
  122,  123,  124,   -1,  126,   -1,   -1,   -1,  130,   -1,
   -1,   -1,   -1,   -1,  136,   -1,  138,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  162,   -1,  164,   -1,   -1,   -1,   -1,   -1,   -1,  171,
   -1,   -1,  174,  175,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=290;
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
"SEALED","GUARDED","UMINUS","EMPTY","VAR",
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
"Constant : LITERAL",
"Constant : NULL",
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

//#line 476 "Parser.y"
    
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
//#line 651 "Parser.java"
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
//#line 55 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 61 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 65 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 75 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 81 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 85 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 97 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 107 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 111 "Parser.y"
{
						yyval.cdef = new Tree.Sealed(val_peek(5).ident, val_peek(3).ident, val_peek(1).flist, val_peek(7).loc);
					}
break;
case 14:
//#line 115 "Parser.y"
{
						yyval.cdef = new Tree.Sealed(val_peek(3).ident, null, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 15:
//#line 121 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 16:
//#line 125 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 17:
//#line 131 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 18:
//#line 135 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 19:
//#line 139 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 21:
//#line 147 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 22:
//#line 154 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 23:
//#line 158 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 24:
//#line 165 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 169 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 26:
//#line 175 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 27:
//#line 181 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 28:
//#line 185 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 29:
//#line 192 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 30:
//#line 197 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 31:
//#line 203 "Parser.y"
{
                		yyval.stmt = new Tree.SCopyExpr(val_peek(3).ident, val_peek(1).expr, val_peek(5).loc);
                	}
break;
case 40:
//#line 217 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 41:
//#line 221 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 42:
//#line 225 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 231 "Parser.y"
{
                        yyval.stmt = new Tree.Guarded(val_peek(2).slist, val_peek(1).stmt, val_peek(4).loc);
                    }
break;
case 44:
//#line 235 "Parser.y"
{
                        yyval.stmt = new Tree.Guarded(null, null, val_peek(2).loc);
                    }
break;
case 45:
//#line 240 "Parser.y"
{
                        yyval.stmt = new Tree.IfSubStmt(val_peek(2).expr, val_peek(0).stmt, val_peek(2).loc);
                    }
break;
case 46:
//#line 245 "Parser.y"
{
                        yyval.stmt = new Tree.IfSubStmt(val_peek(3).expr, val_peek(1).stmt, val_peek(3).loc);
                    }
break;
case 47:
//#line 250 "Parser.y"
{
                        yyval.slist.add(val_peek(0).stmt);
                    }
break;
case 48:
//#line 254 "Parser.y"
{
                        yyval = new SemValue();
                        yyval.slist = new ArrayList<Tree>();
                    }
break;
case 50:
//#line 262 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 51:
//#line 268 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 52:
//#line 275 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 53:
//#line 280 "Parser.y"
{
                		yyval.lvalue = new Tree.Var(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 54:
//#line 286 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 55:
//#line 295 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 58:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 71:
//#line 353 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 72:
//#line 357 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 361 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 74:
//#line 365 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 369 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 76:
//#line 373 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 77:
//#line 379 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 78:
//#line 383 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 79:
//#line 387 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 80:
//#line 391 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 81:
//#line 397 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 82:
//#line 401 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 84:
//#line 408 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 85:
//#line 415 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 86:
//#line 419 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 87:
//#line 426 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 88:
//#line 432 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 89:
//#line 438 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 90:
//#line 444 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 91:
//#line 450 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 92:
//#line 454 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 93:
//#line 460 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 94:
//#line 464 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 95:
//#line 470 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1299 "Parser.java"
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
