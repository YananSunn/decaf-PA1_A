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
public final static short UMINUS=286;
public final static short EMPTY=287;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   14,   14,   14,   24,
   24,   22,   22,   23,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   26,   26,   25,   25,   27,   27,   17,   18,   21,   16,
   28,   28,   19,   19,   20,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    6,    1,
    1,    1,    2,    2,    2,    1,    3,    1,    0,    2,
    0,    2,    4,    5,    1,    1,    1,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    2,    2,    3,    3,    1,    4,    5,    6,    5,
    1,    1,    1,    0,    3,    1,    5,    9,    1,    6,
    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   72,   66,    0,    0,    0,
    0,   79,    0,    0,    0,    0,   71,    0,    0,    0,
    0,    0,   24,   27,   36,   25,    0,    0,   30,   31,
   32,    0,    0,    0,    0,    0,    0,   47,    0,    0,
    0,    0,   45,   46,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   28,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   40,
    0,   33,   34,   35,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   64,   65,    0,    0,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   67,    0,
    0,   85,    0,    0,    0,   43,    0,    0,   77,    0,
    0,   68,    0,    0,    0,   70,   44,    0,    0,   80,
   69,   29,    0,   81,    0,   78,
};
final static short yydgoto[] = {                          2,
    3,    4,   64,   20,   33,    8,   11,   22,   34,   35,
   65,   45,   66,   67,   68,   69,   70,   71,   72,   73,
   74,   83,   84,   77,  157,   78,  123,  170,
};
final static short yysindex[] = {                      -253,
 -258,    0, -253,    0, -244,    0, -229,  -91,    0,    0,
  -80,    0,    0,    0,    0, -228,  -50,    0,    0,   -2,
  -89,    0,    0,  -87,    0,   28,  -20,   32,  -50,    0,
  -50,    0,  -85,   41,   44,   48,    0,  -24,  -50,  -24,
    0,    0,    0,    0,   -4,    0,    0,   51,   63,   68,
  526,    0, -174,   74,   75,   77,    0,   78,   80,  526,
  526,  501,    0,    0,    0,    0,   45,  535,    0,    0,
    0,   53,   70,   71,   61,    0, -137,    0,  526,  526,
  526,  535,    0,    0,  106,   49,  526,  108,  111,  526,
 -120,  -29,  -29, -118,  351,    0,  526,  526,  526,  526,
  526,  526,  526,  526,  526,  526,  526,  526,  526,    0,
  526,    0,    0,    0,  526,  121,  378,  105,  402,  125,
  606,  535,  -21,    0,    0,  413,  130,  134,    0,  696,
  578,    7,    7,  -32,  -32,    9,    9,  -29,  -29,  -29,
    7,    7,  434,  535,  526,   25,  526,   25,    0,  445,
  526,    0, -100,  526,  526,    0,  141,  139,    0,  469,
  -76,    0,  535,  160,  502,    0,    0,  526,   25,    0,
    0,    0,  161,    0,   25,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  203,    0,   81,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  146,    0,    0,  173,    0,
  173,    0,    0,    0,  175,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -58,    0,    0,    0,    0,    0,
  -56,    0,    0,    0,    0,    0,    0,    0,    0,  -59,
  -59,  -59,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  557,   64,    0,    0,  -59,  -58,
  -59,  162,    0,    0,    0,    0,  -59,    0,    0,  -59,
    0,   91,  100,    0,    0,    0,  -59,  -59,  -59,  -59,
  -59,  -59,  -59,  -59,  -59,  -59,  -59,  -59,  -59,    0,
  -59,    0,    0,    0,  -59,   34,    0,    0,    0,    0,
  -59,  -10,    0,    0,    0,    0,    0,    0,    0,  113,
  -19,   72,  424,  459,  491,  764,  804,  126,  153,  292,
  426,  751,    0,  -17,  -25,  -58,  -59,  -58,    0,    0,
  -59,    0,    0,  -59,  -59,    0,    0,  181,    0,    0,
  -33,    0,   20,    0,    0,    0,    0,   -3,  -58,    0,
    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  220,  219,    4,   10,    0,    0,    0,  204,    0,
   29,    0, -109,  -71,  814,    0,    0,    0,    0,    0,
    0,  441,  506,    0,    0,    0,   99,    0,
};
final static int YYTABLESIZE=1088;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         82,
   39,   27,   84,   27,  107,   27,   82,    1,  118,  105,
  103,   82,  104,  110,  106,   74,  110,    5,    7,  152,
   21,   60,  151,   37,   60,   82,   24,  109,   61,  108,
   76,   10,   32,   76,   32,   62,  159,   39,  161,   60,
   60,   37,   43,  107,   18,  107,    9,   23,  105,  103,
  105,  104,  110,  106,  110,  106,   25,   61,  111,  174,
   75,  111,   86,   75,   62,  176,   42,   29,   44,   60,
   42,   31,   30,   60,   42,   42,   42,   42,   42,   42,
   42,   38,   12,   13,   14,   15,   16,   39,   40,   82,
   79,   82,   42,   42,   42,   42,  173,  111,   41,  111,
   46,   85,   80,   96,   38,   46,   46,   81,   46,   46,
   46,  112,   57,   87,   88,   57,   89,   90,   41,   91,
   63,  115,   38,   46,   42,   46,   42,   62,  113,  114,
   57,   62,   62,   62,   62,   62,   63,   62,  116,  121,
   63,   63,   63,   63,   63,  120,   63,   41,  124,   62,
   62,  125,   62,   59,   46,  127,   59,  128,   63,   63,
  145,   63,   50,  147,   57,  149,   50,   50,   50,   50,
   50,   59,   50,  154,  155,  164,   12,   13,   14,   15,
   16,  167,  151,   62,   50,   50,   26,   50,   28,   51,
   37,  169,   63,   51,   51,   51,   51,   51,   17,   51,
  171,  175,    1,   14,    5,   59,   12,   13,   14,   15,
   16,   51,   51,   19,   51,   18,   41,   41,   50,   41,
   83,   73,    6,   82,   82,   82,   82,   82,   82,   19,
   82,   82,   82,   82,   36,   82,   82,   82,   82,   82,
   82,   82,   82,  158,    0,   51,   82,    0,   99,  100,
   41,   82,   12,   13,   14,   15,   16,   46,   60,   47,
   48,   49,   50,    0,   51,   52,   53,   54,   55,   56,
   57,    0,   41,    0,    0,   58,    0,    0,    0,    0,
   59,   12,   13,   14,   15,   16,   46,    0,   47,   48,
   49,   50,    0,   51,   52,   53,   54,   55,   56,   57,
    0,    0,    0,    0,   58,    0,    0,    0,    0,   59,
   42,   42,    0,    0,   42,   42,   42,   42,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   52,    0,
    0,    0,   52,   52,   52,   52,   52,    0,   52,    0,
   46,   46,    0,    0,   46,   46,   46,   46,   57,   57,
   52,   52,    0,   52,   57,   57,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   62,   62,    0,
    0,   62,   62,   62,   62,    0,   63,   63,    0,    0,
   63,   63,   63,   63,   52,    0,    0,  107,    0,   59,
   59,  129,  105,  103,    0,  104,  110,  106,    0,    0,
    0,    0,   50,   50,    0,    0,   50,   50,   50,   50,
  109,    0,  108,    0,  107,    0,    0,    0,  146,  105,
  103,    0,  104,  110,  106,    0,    0,    0,    0,   51,
   51,    0,    0,   51,   51,   51,   51,  109,  107,  108,
    0,  111,  148,  105,  103,    0,  104,  110,  106,  107,
    0,    0,    0,    0,  105,  103,  153,  104,  110,  106,
    0,  109,    0,  108,   58,    0,   56,   58,  111,   56,
  107,    0,  109,    0,  108,  105,  103,    0,  104,  110,
  106,  107,   58,    0,   56,   75,  105,  103,    0,  104,
  110,  106,  111,  109,    0,  108,    0,    0,    0,   53,
    0,    0,   53,  111,  109,  107,  108,    0,    0,    0,
  105,  103,    0,  104,  110,  106,   58,   53,   56,    0,
   75,    0,    0,    0,  111,    0,  156,  168,  109,    0,
  108,   54,    0,   61,   54,  111,    0,  162,  107,    0,
   62,    0,  172,  105,  103,   60,  104,  110,  106,   54,
   76,   53,    0,    0,    0,    0,    0,    0,   61,  111,
    0,  109,    0,  108,    0,   62,    0,    0,   52,   52,
   60,  107,   52,   52,   52,   52,  105,  103,    0,  104,
  110,  106,    0,   54,    0,   76,   75,    0,   75,    0,
    0,    0,  111,   45,  109,    0,  108,    0,   45,   45,
    0,   45,   45,   45,    0,    0,    0,    0,   75,   75,
    0,    0,    0,    0,  107,   75,   45,    0,   45,  105,
  103,    0,  104,  110,  106,  111,    0,   97,   98,    0,
    0,   99,  100,  101,  102,    0,    0,  109,   61,  108,
    0,    0,    0,    0,    0,   62,    0,   45,    0,    0,
   60,   76,    0,   76,   97,   98,    0,    0,   99,  100,
  101,  102,    0,    0,    0,    0,    0,    0,  111,    0,
    0,    0,    0,   76,   76,    0,    0,    0,   97,   98,
   76,    0,   99,  100,  101,  102,    0,    0,    0,   97,
   98,    0,    0,   99,  100,  101,  102,    0,   30,    0,
   58,   58,   56,   56,    0,    0,   58,   58,   56,   56,
   97,   98,    0,    0,   99,  100,  101,  102,    0,    0,
    0,   97,   98,    0,    0,   99,  100,  101,  102,    0,
    0,    0,  107,    0,    0,   53,   53,  105,  103,    0,
  104,  110,  106,    0,    0,   97,   98,    0,    0,   99,
  100,  101,  102,    0,    0,  109,    0,  108,    0,    0,
    0,   94,   46,    0,   47,    0,    0,   54,   54,    0,
    0,   53,    0,   55,   56,   57,    0,    0,   97,   98,
   58,    0,   99,  100,  101,  102,  111,   46,    0,   47,
    0,   55,    0,    0,   55,    0,   53,    0,   55,   56,
   57,    0,    0,    0,   48,   58,   48,   48,   48,   55,
    0,   97,   98,    0,    0,   99,  100,  101,  102,    0,
    0,    0,   48,   48,    0,   48,    0,    0,    0,    0,
    0,    0,    0,   45,   45,    0,    0,   45,   45,   45,
   45,    0,    0,   55,   49,    0,   49,   49,   49,    0,
    0,    0,    0,    0,   97,    0,   48,    0,   99,  100,
  101,  102,   49,   49,   82,   49,    0,   46,    0,   47,
    0,    0,    0,   92,   93,   95,   53,    0,   55,   56,
   57,    0,    0,    0,    0,   58,    0,    0,    0,    0,
    0,    0,  117,    0,  119,    0,   49,    0,    0,    0,
  122,    0,    0,  126,    0,    0,    0,    0,    0,    0,
  130,  131,  132,  133,  134,  135,  136,  137,  138,  139,
  140,  141,  142,    0,  143,    0,    0,    0,  144,    0,
    0,    0,    0,    0,  150,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  122,    0,
  160,    0,    0,    0,  163,    0,    0,  165,  166,    0,
    0,    0,    0,    0,    0,    0,   99,  100,  101,  102,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,   55,    0,
    0,    0,    0,   55,   55,    0,    0,    0,    0,    0,
   48,   48,    0,    0,   48,   48,   48,   48,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,   49,    0,    0,   49,   49,   49,   49,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   91,   59,   91,   37,   91,   40,  261,   80,   42,
   43,   45,   45,   46,   47,   41,   46,  276,  263,   41,
   11,   41,   44,   41,   44,   59,   17,   60,   33,   62,
   41,  123,   29,   44,   31,   40,  146,   41,  148,   59,
   45,   59,   39,   37,  125,   37,  276,  276,   42,   43,
   42,   45,   46,   47,   46,   47,   59,   33,   91,  169,
   41,   91,   53,   44,   40,  175,   38,   40,   40,   45,
   37,   40,   93,   93,   41,   42,   43,   44,   45,   46,
   47,   41,  257,  258,  259,  260,  261,   44,   41,  123,
   40,  125,   59,   60,   61,   62,  168,   91,  123,   91,
   37,  276,   40,   59,   41,   42,   43,   40,   45,   46,
   47,   59,   41,   40,   40,   44,   40,   40,  123,   40,
  125,   61,   59,   60,   91,   62,   93,   37,   59,   59,
   59,   41,   42,   43,   44,   45,   37,   47,  276,   91,
   41,   42,   43,   44,   45,   40,   47,  123,   41,   59,
   60,   41,   62,   41,   91,  276,   44,  276,   59,   60,
   40,   62,   37,   59,   93,   41,   41,   42,   43,   44,
   45,   59,   47,   44,   41,  276,  257,  258,  259,  260,
  261,   41,   44,   93,   59,   60,  276,   62,  276,   37,
  276,  268,   93,   41,   42,   43,   44,   45,  279,   47,
   41,   41,    0,  123,   59,   93,  257,  258,  259,  260,
  261,   59,   60,   41,   62,   41,  276,  276,   93,  276,
   59,   41,    3,  257,  258,  259,  260,  261,  262,   11,
  264,  265,  266,  267,   31,  269,  270,  271,  272,  273,
  274,  275,  276,  145,   -1,   93,  280,   -1,  281,  282,
  276,  285,  257,  258,  259,  260,  261,  262,  278,  264,
  265,  266,  267,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,  276,   -1,   -1,  280,   -1,   -1,   -1,   -1,
  285,  257,  258,  259,  260,  261,  262,   -1,  264,  265,
  266,  267,   -1,  269,  270,  271,  272,  273,  274,  275,
   -1,   -1,   -1,   -1,  280,   -1,   -1,   -1,   -1,  285,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
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
   -1,   -1,   -1,   -1,   42,   43,   44,   45,   46,   47,
   -1,   60,   -1,   62,   41,   -1,   41,   44,   91,   44,
   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   37,   59,   -1,   59,   45,   42,   43,   -1,   45,
   46,   47,   91,   60,   -1,   62,   -1,   -1,   -1,   41,
   -1,   -1,   44,   91,   60,   37,   62,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   93,   59,   93,   -1,
   80,   -1,   -1,   -1,   91,   -1,   93,   59,   60,   -1,
   62,   41,   -1,   33,   44,   91,   -1,   93,   37,   -1,
   40,   -1,   41,   42,   43,   45,   45,   46,   47,   59,
   45,   93,   -1,   -1,   -1,   -1,   -1,   -1,   33,   91,
   -1,   60,   -1,   62,   -1,   40,   -1,   -1,  277,  278,
   45,   37,  281,  282,  283,  284,   42,   43,   -1,   45,
   46,   47,   -1,   93,   -1,   80,  146,   -1,  148,   -1,
   -1,   -1,   91,   37,   60,   -1,   62,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,  168,  169,
   -1,   -1,   -1,   -1,   37,  175,   60,   -1,   62,   42,
   43,   -1,   45,   46,   47,   91,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   60,   33,   62,
   -1,   -1,   -1,   -1,   -1,   40,   -1,   91,   -1,   -1,
   45,  146,   -1,  148,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,  168,  169,   -1,   -1,   -1,  277,  278,
  175,   -1,  281,  282,  283,  284,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   93,   -1,
  277,  278,  277,  278,   -1,   -1,  283,  284,  283,  284,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   37,   -1,   -1,  277,  278,   42,   43,   -1,
   45,   46,   47,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   60,   -1,   62,   -1,   -1,
   -1,  261,  262,   -1,  264,   -1,   -1,  277,  278,   -1,
   -1,  271,   -1,  273,  274,  275,   -1,   -1,  277,  278,
  280,   -1,  281,  282,  283,  284,   91,  262,   -1,  264,
   -1,   41,   -1,   -1,   44,   -1,  271,   -1,  273,  274,
  275,   -1,   -1,   -1,   41,  280,   43,   44,   45,   59,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   93,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   -1,   -1,  277,   -1,   93,   -1,  281,  282,
  283,  284,   59,   60,   51,   62,   -1,  262,   -1,  264,
   -1,   -1,   -1,   60,   61,   62,  271,   -1,  273,  274,
  275,   -1,   -1,   -1,   -1,  280,   -1,   -1,   -1,   -1,
   -1,   -1,   79,   -1,   81,   -1,   93,   -1,   -1,   -1,
   87,   -1,   -1,   90,   -1,   -1,   -1,   -1,   -1,   -1,
   97,   98,   99,  100,  101,  102,  103,  104,  105,  106,
  107,  108,  109,   -1,  111,   -1,   -1,   -1,  115,   -1,
   -1,   -1,   -1,   -1,  121,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  145,   -1,
  147,   -1,   -1,   -1,  151,   -1,   -1,  154,  155,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  281,  282,  283,  284,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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
final static short YYFINAL=2;
final static short YYMAXTOKEN=287;
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
"UMINUS","EMPTY",
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

//#line 432 "Parser.y"
    
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
//#line 596 "Parser.java"
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
//#line 113 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 117 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 123 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 127 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 131 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 139 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 146 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 150 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 157 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 161 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 167 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 173 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 177 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 184 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 189 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 29:
//#line 195 "Parser.y"
{
                		yyval.stmt = new Tree.SCopyExpr(val_peek(3).ident, val_peek(1).expr, val_peek(5).loc);
                	}
break;
case 37:
//#line 208 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 38:
//#line 212 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 39:
//#line 216 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 223 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 229 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 43:
//#line 236 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 44:
//#line 242 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 45:
//#line 251 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 48:
//#line 257 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 49:
//#line 261 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 50:
//#line 265 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 269 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 273 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 277 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 281 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 285 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 289 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 293 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 297 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 309 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 62:
//#line 313 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 65:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 66:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 67:
//#line 335 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 68:
//#line 339 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 69:
//#line 343 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 70:
//#line 347 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 71:
//#line 353 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 72:
//#line 357 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 74:
//#line 364 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 75:
//#line 371 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 76:
//#line 375 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 77:
//#line 382 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 78:
//#line 388 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 79:
//#line 394 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 80:
//#line 400 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 81:
//#line 406 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 82:
//#line 410 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 83:
//#line 416 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 84:
//#line 420 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 85:
//#line 426 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1189 "Parser.java"
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
