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
public final static short UMINUS=287;
public final static short EMPTY=288;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    2,    2,    6,    6,    7,    7,    7,    9,
    9,   10,   10,    8,    8,   11,   12,   12,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   14,   14,
   14,   24,   24,   22,   22,   23,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   26,   26,   25,   25,   27,   27,   17,   18,
   21,   16,   28,   28,   19,   19,   20,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    8,    6,    2,    0,    2,    2,    0,    1,
    0,    3,    1,    7,    6,    3,    2,    0,    1,    2,
    6,    1,    1,    1,    2,    2,    2,    1,    3,    1,
    0,    2,    0,    2,    4,    5,    1,    1,    1,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    2,    3,    3,    1,    4,    5,
    6,    5,    1,    1,    1,    0,    3,    1,    5,    9,
    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   15,   19,    0,   19,    0,    0,    0,    7,    8,
    6,    9,    0,    0,   12,   17,    0,    0,   18,   19,
   14,   10,    0,    4,    0,    0,    0,    0,    0,   11,
   13,    0,   23,    0,    0,    0,    0,    5,    0,    0,
    0,   28,   25,   22,   24,    0,   74,   68,    0,    0,
    0,    0,   81,    0,    0,    0,    0,   73,    0,    0,
    0,    0,    0,   26,   29,   38,   27,    0,    0,   32,
   33,   34,    0,    0,    0,    0,    0,    0,   49,    0,
    0,    0,    0,   47,   48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   42,    0,   35,   36,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   66,   67,    0,    0,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   69,
    0,    0,   87,    0,    0,    0,   45,    0,    0,   79,
    0,    0,   70,    0,    0,    0,   72,   46,    0,    0,
   82,   71,   31,    0,   83,    0,   80,
};
final static short yydgoto[] = {                          3,
    4,    5,   75,   27,   44,   10,   16,   29,   45,   46,
   76,   56,   77,   78,   79,   80,   81,   82,   83,   84,
   85,   94,   95,   88,  168,   89,  134,  181,
};
final static short yysindex[] = {                      -253,
 -258, -234,    0, -253,    0, -232, -244,    0, -237,  -77,
 -114,    0,    0, -229,    0,  -80,  -68,  328,    0,    0,
    0,    0, -216,  -50,    0,    0,    7,  -89,    0,    0,
    0,    0,  -87,    0,   42,    5,  384,   74,  -50,    0,
    0,  -50,    0,  -85,   48,   60,   71,    0,   -8,  -50,
   -8,    0,    0,    0,    0,   -4,    0,    0,   78,   80,
   82,  526,    0, -174,   89,   90,   99,    0,  106,  112,
  526,  526,  501,    0,    0,    0,    0,   81,  535,    0,
    0,    0,   97,  102,  105,  114,    0, -118,    0,  526,
  526,  526,  535,    0,    0,  134,   75,  526,  135,  141,
  526,  -93,  -29,  -29,  -84,  351,    0,  526,  526,  526,
  526,  526,  526,  526,  526,  526,  526,  526,  526,  526,
    0,  526,    0,    0,    0,  526,  161,  378,  143,  402,
  162,  606,  535,   13,    0,    0,  413,  160,  164,    0,
  696,  578,    6,    6,  -32,  -32,   26,   26,  -29,  -29,
  -29,    6,    6,  434,  535,  526,   25,  526,   25,    0,
  445,  526,    0,  -62,  526,  526,    0,  175,  173,    0,
  469,  -47,    0,  535,  181,  502,    0,    0,  526,   25,
    0,    0,    0,  182,    0,   25,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  230,    0,  121,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  176,    0,    0,    0,  204,    0,
    0,  204,    0,    0,    0,  207,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -56,    0,    0,    0,    0,    0,    0,    0,    0,
  -12,  -12,  -12,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  557,   64,    0,    0,  -12,
  -58,  -12,  216,    0,    0,    0,    0,  -12,    0,    0,
  -12,    0,   91,  100,    0,    0,    0,  -12,  -12,  -12,
  -12,  -12,  -12,  -12,  -12,  -12,  -12,  -12,  -12,  -12,
    0,  -12,    0,    0,    0,  -12,   34,    0,    0,    0,
    0,  -12,   20,    0,    0,    0,    0,    0,    0,    0,
  113,  -19,   72,  424,  459,  491,  764,  804,  126,  153,
  292,  426,  751,    0,  -17,  -25,  -58,  -12,  -58,    0,
    0,  -12,    0,    0,  -12,  -12,    0,    0,  236,    0,
    0,  -33,    0,   59,    0,    0,    0,    0,   -3,  -58,
    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  274,   19,   49,  256,    0,    4,    0,  237,    0,
   18,    0, -136,  -71,  828,    0,    0,    0,    0,    0,
    0,  575,  737,    0,    0,    0,  132,    0,
};
final static int YYTABLESIZE=1088;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         84,
   41,   36,   86,   36,  118,   36,   84,    1,   15,  116,
  114,   84,  115,  121,  117,   76,  121,    6,   18,  129,
  170,   62,  172,   39,   62,   84,    7,  120,   72,  119,
    9,   11,    2,   37,   26,   73,   26,   41,   12,   62,
   71,   39,  118,  185,   25,   13,   17,  116,  114,  187,
  115,  121,  117,  163,   30,   26,  162,   72,  122,   32,
   78,  122,  118,   78,   73,   34,   53,  116,   55,   71,
   44,  121,  117,   62,   44,   44,   44,   44,   44,   44,
   44,   39,   19,   20,   21,   22,   23,   43,   49,   84,
   43,   84,   44,   44,   44,   44,  122,   40,   54,   77,
   48,   96,   77,   50,   40,   48,   48,  184,   48,   48,
   48,   51,   59,   42,   52,   59,  122,   90,   52,   91,
   74,   92,   40,   48,   44,   48,   44,   64,   98,   99,
   59,   64,   64,   64,   64,   64,   65,   64,  100,  107,
   65,   65,   65,   65,   65,  101,   65,   52,   14,   64,
   64,  102,   64,   61,   48,  123,   61,  127,   65,   65,
  124,   65,   52,  125,   59,  132,   52,   52,   52,   52,
   52,   61,   52,  131,  126,  135,   19,   20,   21,   22,
   23,  136,  138,   64,   52,   52,   35,   52,   38,   53,
   48,  139,   65,   53,   53,   53,   53,   53,   24,   53,
  156,  158,  160,  165,  166,   61,   19,   20,   21,   22,
   23,   53,   53,  175,   53,  178,  162,   43,   52,   43,
  180,  182,  186,   84,   84,   84,   84,   84,   84,    1,
   84,   84,   84,   84,    5,   84,   84,   84,   84,   84,
   84,   84,   84,   16,   21,   53,   84,   20,  110,  111,
   43,   84,   19,   20,   21,   22,   23,   57,   62,   58,
   59,   60,   61,   43,   62,   63,   64,   65,   66,   67,
   68,   28,   43,   28,   85,   69,   75,    8,   47,   33,
   70,   19,   20,   21,   22,   23,   57,  169,   58,   59,
   60,   61,   28,   62,   63,   64,   65,   66,   67,   68,
    0,    0,    0,    0,   69,    0,    0,    0,    0,   70,
   44,   44,    0,    0,   44,   44,   44,   44,    0,   97,
    0,    0,    0,    0,    0,    0,    0,    0,   54,    0,
    0,    0,   54,   54,   54,   54,   54,    0,   54,    0,
   48,   48,    0,    0,   48,   48,   48,   48,   59,   59,
   54,   54,    0,   54,   59,   59,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   64,   64,    0,
    0,   64,   64,   64,   64,    0,   65,   65,    0,    0,
   65,   65,   65,   65,   54,    0,    0,  118,    0,   61,
   61,  140,  116,  114,    0,  115,  121,  117,    0,    0,
    0,    0,   52,   52,    0,    0,   52,   52,   52,   52,
  120,    0,  119,    0,  118,    0,    0,    0,  157,  116,
  114,    0,  115,  121,  117,    0,    0,    0,    0,   53,
   53,    0,    0,   53,   53,   53,   53,  120,  118,  119,
    0,  122,  159,  116,  114,    0,  115,  121,  117,  118,
    0,    0,   31,    0,  116,  114,  164,  115,  121,  117,
    0,  120,    0,  119,   60,    0,   58,   60,  122,   58,
  118,    0,  120,    0,  119,  116,  114,    0,  115,  121,
  117,  118,   60,    0,   58,    0,  116,  114,    0,  115,
  121,  117,  122,  120,    0,  119,    0,    0,    0,   55,
    0,    0,   55,  122,  120,  118,  119,    0,   41,    0,
  116,  114,    0,  115,  121,  117,   60,   55,   58,    0,
    0,    0,    0,    0,  122,    0,  167,  179,  120,    0,
  119,   56,    0,   72,   56,  122,    0,  173,  118,    0,
   73,    0,  183,  116,  114,   71,  115,  121,  117,   56,
    0,   55,    0,    0,    0,    0,    0,    0,   72,  122,
    0,  120,    0,  119,    0,   73,    0,    0,   54,   54,
   71,  118,   54,   54,   54,   54,  116,  114,    0,  115,
  121,  117,    0,   56,   19,   20,   21,   22,   23,    0,
    0,    0,  122,   47,  120,    0,  119,    0,   47,   47,
    0,   47,   47,   47,    0,    0,   24,    0,    0,    0,
    0,    0,    0,    0,  118,    0,   47,    0,   47,  116,
  114,    0,  115,  121,  117,  122,    0,  108,  109,    0,
   86,  110,  111,  112,  113,    0,    0,  120,   72,  119,
   19,   20,   21,   22,   23,   73,    0,   47,    0,    0,
   71,    0,    0,    0,  108,  109,    0,    0,  110,  111,
  112,  113,   24,    0,    0,   86,    0,    0,  122,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  108,  109,
    0,    0,  110,  111,  112,  113,    0,    0,    0,  108,
  109,    0,    0,  110,  111,  112,  113,    0,   40,    0,
   60,   60,   58,   58,    0,    0,   60,   60,   58,   58,
  108,  109,    0,    0,  110,  111,  112,  113,    0,    0,
    0,  108,  109,    0,    0,  110,  111,  112,  113,    0,
    0,   86,  118,   86,    0,   55,   55,  116,  114,    0,
  115,  121,  117,    0,    0,  108,  109,    0,    0,  110,
  111,  112,  113,   86,   86,  120,    0,  119,    0,    0,
   86,  105,   57,    0,   58,    0,    0,   56,   56,    0,
    0,   64,    0,   66,   67,   68,    0,    0,  108,  109,
   69,    0,  110,  111,  112,  113,  122,   57,    0,   58,
    0,   57,   87,    0,   57,    0,   64,    0,   66,   67,
   68,    0,    0,    0,   50,   69,   50,   50,   50,   57,
    0,  108,  109,    0,    0,  110,  111,  112,  113,    0,
    0,    0,   50,   50,    0,   50,    0,   87,    0,    0,
    0,    0,    0,   47,   47,    0,    0,   47,   47,   47,
   47,    0,    0,   57,   51,    0,   51,   51,   51,    0,
    0,    0,    0,    0,  108,    0,   50,    0,  110,  111,
  112,  113,   51,   51,    0,   51,    0,   57,    0,   58,
    0,    0,    0,    0,    0,    0,   64,    0,   66,   67,
   68,    0,    0,    0,    0,   69,    0,    0,    0,   93,
    0,    0,    0,   87,    0,   87,   51,    0,  103,  104,
  106,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,   87,  128,    0,  130,
    0,    0,   87,    0,    0,  133,    0,    0,  137,    0,
    0,    0,    0,    0,    0,  141,  142,  143,  144,  145,
  146,  147,  148,  149,  150,  151,  152,  153,    0,  154,
    0,    0,    0,  155,    0,    0,    0,    0,    0,  161,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  110,  111,  112,  113,
    0,    0,    0,  133,    0,  171,    0,    0,    0,  174,
    0,    0,  176,  177,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   57,   57,    0,
    0,    0,    0,   57,   57,    0,    0,    0,    0,    0,
   50,   50,    0,    0,   50,   50,   50,   50,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,   51,    0,    0,   51,   51,   51,   51,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   91,   59,   91,   37,   91,   40,  261,  123,   42,
   43,   45,   45,   46,   47,   41,   46,  276,   15,   91,
  157,   41,  159,   41,   44,   59,  261,   60,   33,   62,
  263,  276,  286,   30,   16,   40,   18,   41,  276,   59,
   45,   59,   37,  180,  125,  123,  276,   42,   43,  186,
   45,   46,   47,   41,  123,   37,   44,   33,   91,  276,
   41,   91,   37,   44,   40,   59,   49,   42,   51,   45,
   37,   46,   47,   93,   41,   42,   43,   44,   45,   46,
   47,   40,  257,  258,  259,  260,  261,   39,   41,  123,
   42,  125,   59,   60,   61,   62,   91,   93,   50,   41,
   37,  276,   44,   44,   41,   42,   43,  179,   45,   46,
   47,   41,   41,   40,  123,   44,   91,   40,  123,   40,
  125,   40,   59,   60,   91,   62,   93,   37,   40,   40,
   59,   41,   42,   43,   44,   45,   37,   47,   40,   59,
   41,   42,   43,   44,   45,   40,   47,  123,  263,   59,
   60,   40,   62,   41,   91,   59,   44,  276,   59,   60,
   59,   62,   37,   59,   93,   91,   41,   42,   43,   44,
   45,   59,   47,   40,   61,   41,  257,  258,  259,  260,
  261,   41,  276,   93,   59,   60,  276,   62,  276,   37,
  276,  276,   93,   41,   42,   43,   44,   45,  279,   47,
   40,   59,   41,   44,   41,   93,  257,  258,  259,  260,
  261,   59,   60,  276,   62,   41,   44,  276,   93,  276,
  268,   41,   41,  257,  258,  259,  260,  261,  262,    0,
  264,  265,  266,  267,   59,  269,  270,  271,  272,  273,
  274,  275,  276,  123,   41,   93,  280,   41,  281,  282,
  276,  285,  257,  258,  259,  260,  261,  262,  278,  264,
  265,  266,  267,  276,  269,  270,  271,  272,  273,  274,
  275,   16,  276,   18,   59,  280,   41,    4,   42,   24,
  285,  257,  258,  259,  260,  261,  262,  156,  264,  265,
  266,  267,   37,  269,  270,  271,  272,  273,  274,  275,
   -1,   -1,   -1,   -1,  280,   -1,   -1,   -1,   -1,  285,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   64,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   37,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,  277,  278,
   59,   60,   -1,   62,  283,  284,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   93,   -1,   -1,   37,   -1,  277,
  278,   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   60,   -1,   62,   -1,   37,   -1,   -1,   -1,   41,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   60,   37,   62,
   -1,   91,   41,   42,   43,   -1,   45,   46,   47,   37,
   -1,   -1,  125,   -1,   42,   43,   44,   45,   46,   47,
   -1,   60,   -1,   62,   41,   -1,   41,   44,   91,   44,
   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   37,   59,   -1,   59,   -1,   42,   43,   -1,   45,
   46,   47,   91,   60,   -1,   62,   -1,   -1,   -1,   41,
   -1,   -1,   44,   91,   60,   37,   62,   -1,  125,   -1,
   42,   43,   -1,   45,   46,   47,   93,   59,   93,   -1,
   -1,   -1,   -1,   -1,   91,   -1,   93,   59,   60,   -1,
   62,   41,   -1,   33,   44,   91,   -1,   93,   37,   -1,
   40,   -1,   41,   42,   43,   45,   45,   46,   47,   59,
   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   33,   91,
   -1,   60,   -1,   62,   -1,   40,   -1,   -1,  277,  278,
   45,   37,  281,  282,  283,  284,   42,   43,   -1,   45,
   46,   47,   -1,   93,  257,  258,  259,  260,  261,   -1,
   -1,   -1,   91,   37,   60,   -1,   62,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,  279,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   -1,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   91,   -1,  277,  278,   -1,
   56,  281,  282,  283,  284,   -1,   -1,   60,   33,   62,
  257,  258,  259,  260,  261,   40,   -1,   91,   -1,   -1,
   45,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,  279,   -1,   -1,   91,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   93,   -1,
  277,  278,  277,  278,   -1,   -1,  283,  284,  283,  284,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,  157,   37,  159,   -1,  277,  278,   42,   43,   -1,
   45,   46,   47,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,  179,  180,   60,   -1,   62,   -1,   -1,
  186,  261,  262,   -1,  264,   -1,   -1,  277,  278,   -1,
   -1,  271,   -1,  273,  274,  275,   -1,   -1,  277,  278,
  280,   -1,  281,  282,  283,  284,   91,  262,   -1,  264,
   -1,   41,   56,   -1,   44,   -1,  271,   -1,  273,  274,
  275,   -1,   -1,   -1,   41,  280,   43,   44,   45,   59,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   59,   60,   -1,   62,   -1,   91,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   93,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   -1,   -1,  277,   -1,   93,   -1,  281,  282,
  283,  284,   59,   60,   -1,   62,   -1,  262,   -1,  264,
   -1,   -1,   -1,   -1,   -1,   -1,  271,   -1,  273,  274,
  275,   -1,   -1,   -1,   -1,  280,   -1,   -1,   -1,   62,
   -1,   -1,   -1,  157,   -1,  159,   93,   -1,   71,   72,
   73,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  179,  180,   90,   -1,   92,
   -1,   -1,  186,   -1,   -1,   98,   -1,   -1,  101,   -1,
   -1,   -1,   -1,   -1,   -1,  108,  109,  110,  111,  112,
  113,  114,  115,  116,  117,  118,  119,  120,   -1,  122,
   -1,   -1,   -1,  126,   -1,   -1,   -1,   -1,   -1,  132,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  281,  282,  283,  284,
   -1,   -1,   -1,  156,   -1,  158,   -1,   -1,   -1,  162,
   -1,   -1,  165,  166,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,  283,  284,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=288;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
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
"SEALED","UMINUS","EMPTY",
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
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
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

//#line 440 "Parser.y"
    
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
//#line 602 "Parser.java"
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
case 39:
//#line 216 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 40:
//#line 220 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 41:
//#line 224 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 231 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 44:
//#line 237 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 45:
//#line 244 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 46:
//#line 250 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 47:
//#line 259 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 50:
//#line 265 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 269 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 273 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 277 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 281 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 285 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 289 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 293 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 297 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 317 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 64:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 67:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 68:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 69:
//#line 343 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 70:
//#line 347 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 71:
//#line 351 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 72:
//#line 355 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 73:
//#line 361 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 74:
//#line 365 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 76:
//#line 372 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 77:
//#line 379 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 78:
//#line 383 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 79:
//#line 390 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 80:
//#line 396 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 81:
//#line 402 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 82:
//#line 408 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 83:
//#line 414 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 84:
//#line 418 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 85:
//#line 424 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 86:
//#line 428 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 87:
//#line 434 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1207 "Parser.java"
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
