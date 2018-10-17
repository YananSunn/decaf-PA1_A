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
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    2,    2,    6,    6,    7,    7,    7,    9,
    9,   10,   10,    8,    8,   11,   12,   12,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   13,   14,
   14,   14,   22,   22,   26,   27,   25,   25,   28,   28,
   23,   23,   24,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   15,
   15,   15,   15,   15,   15,   15,   15,   15,   15,   30,
   30,   29,   29,   31,   31,   17,   18,   21,   16,   32,
   32,   19,   19,   20,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    8,    6,    2,    0,    2,    2,    0,    1,
    0,    3,    1,    7,    6,    3,    2,    0,    1,    2,
    6,    1,    1,    1,    2,    2,    2,    1,    1,    3,
    1,    0,    5,    3,    3,    4,    2,    0,    2,    0,
    2,    4,    5,    1,    1,    1,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    2,    3,    3,    1,    4,    5,    6,    5,    1,
    1,    1,    0,    3,    1,    5,    9,    1,    6,    2,
    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   15,   19,    0,   19,    0,    0,    0,    7,    8,
    6,    9,    0,    0,   12,   17,    0,    0,   18,   19,
   14,   10,    0,    4,    0,    0,    0,    0,    0,   11,
   13,    0,   23,    0,    0,    0,    0,    5,    0,    0,
    0,   28,   25,   22,   24,    0,   81,   75,    0,    0,
    0,    0,   88,    0,    0,    0,    0,   80,    0,    0,
    0,    0,    0,   26,   29,   38,   27,    0,    0,   32,
   33,   34,    0,    0,    0,   39,    0,    0,    0,   56,
    0,    0,    0,    0,    0,   54,   55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   30,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   49,    0,   35,   36,   37,    0,    0,    0,
    0,    0,   44,    0,    0,    0,    0,    0,   73,   74,
    0,    0,    0,   70,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   47,   76,    0,    0,   94,
    0,    0,    0,   52,    0,    0,   86,    0,    0,    0,
   43,   77,    0,    0,    0,   79,   53,    0,    0,   89,
    0,   78,   31,    0,   90,   46,    0,   87,
};
final static short yydgoto[] = {                          3,
    4,    5,   75,   27,   44,   10,   16,   29,   45,   46,
   76,   56,   77,   78,   79,   80,   81,   82,   83,   84,
   85,   86,   96,   97,  134,  165,  166,   89,  175,   90,
  138,  190,
};
final static short yysindex[] = {                      -240,
 -249, -225,    0, -240,    0, -211, -219,    0, -207,  -53,
 -106,    0,    0, -205,    0,  -78,  -48,  -49,    0,    0,
    0,    0, -187, -173,    0,    0,   32,  -88,    0,    0,
    0,    0,  -87,    0,   60,    9,  156,   65, -173,    0,
    0, -173,    0,  -85,   73,   62,   74,    0,   18, -173,
   18,    0,    0,    0,    0,   -2,    0,    0,   91,   96,
  -22,  641,    0,  105,  100,  123,  125,    0,  126,  127,
  641,  641,  409,    0,    0,    0,    0,   83,  543,    0,
    0,    0,  113,  115,  116,    0,  124,    0, -100,    0,
  641,  641,  641,   52,  543,    0,    0,  144,   95,  641,
  146,  149,  641,  -83,  -26,  -26,  -82,  383,    0,  641,
  641,  641,  641,  641,  641,  641,  641,  641,  641,  641,
  641,  641,    0,  641,    0,    0,    0,  641,  163,  410,
  147,  421,    0,  641,  164,  613,  543,  -19,    0,    0,
  279,  172,  166,    0,  702,  585,    8,    8,  -32,  -32,
    2,    2,  -26,  -26,  -26,    8,    8,  442,  543,  641,
   27,  641,   27,  453,   97,    0,    0,  477,  641,    0,
  -56,  641,  641,    0,  180,  179,    0,  505,  -24,   27,
    0,    0,  543,  194,  532,    0,    0,  641,   27,    0,
  -42,    0,    0,  205,    0,    0,   27,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  251,    0,  138,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  207,    0,    0,    0,  233,    0,
    0,  233,    0,    0,    0,  235,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -57,    0,    0,    0,    0,    0,    0,    0,    0,
    3,    3,    3,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  564,  359,    0,    0,
    3,  -58,    3,  501,  221,    0,    0,    0,    0,    3,
    0,    0,    3,    0,   66,   75,    0,    0,    0,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    0,    3,    0,    0,    0,    3,   36,    0,
    0,    0,    0,    3,    0,    3,  -12,    0,    0,    0,
    0,    0,    0,    0,  450,  -25,  434,  712,  839,  850,
  792,  814,  102,  111,  155,  760,  837,    0,  -18,   -1,
  -58,    3,  -58,    0,    0,    0,    0,    0,    3,    0,
    0,    3,    3,    0,    0,  241,    0,    0,  -33,  -58,
    0,    0,   17,    0,    0,    0,    0,    1,  -58,    0,
  165,    0,    0,    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  291,   19,   24,  114,    0,   -6,    0,  261,    0,
   13,    0,  148,  -84, 1031,    0,    0,    0,    0,    0,
    0,    0,  738,  773,    0,    0,    0,    0,    0,    0,
  145,    0,
};
final static int YYTABLESIZE=1204;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         91,
   42,   93,   36,   36,  120,   36,   91,  131,   18,  118,
  116,   91,  117,  123,  119,   69,   15,   93,   69,  123,
    1,  170,   40,   37,  169,   91,    6,  122,   85,  121,
   72,   85,   69,   69,   26,    7,   26,   73,  120,   83,
   40,   42,   71,  118,  120,    2,   25,  123,  119,  118,
  116,    9,  117,  123,  119,   26,   11,   84,  124,   72,
   84,   53,   43,   55,  124,   43,   73,   69,   12,   13,
   17,   71,   51,   54,   30,   31,   51,   51,   51,   51,
   51,   51,   51,   19,   20,   21,   22,   23,   32,   91,
   34,   91,  124,   51,   51,   51,   51,   51,  124,   39,
   94,   40,   71,  194,   42,   50,   71,   71,   71,   71,
   71,   72,   71,   49,   51,   72,   72,   72,   72,   72,
   52,   72,   74,   71,   71,   71,   51,   71,   51,   28,
   91,   28,   72,   72,   72,   92,   72,   33,   59,  100,
   52,  109,   59,   59,   59,   59,   59,   60,   59,   52,
   28,   60,   60,   60,   60,   60,   14,   60,   71,   59,
   59,   59,  101,   59,  102,  103,  104,   72,   60,   60,
   60,  125,   60,  126,  127,  129,  133,   99,   19,   20,
   21,   22,   23,  135,  128,  136,  139,   35,   38,  140,
   48,   61,  142,  143,   59,   61,   61,   61,   61,   61,
   24,   61,  160,   60,  167,  162,  173,   19,   20,   21,
   22,   23,   61,   61,   61,  172,   61,   50,   50,  184,
  187,  181,  169,   91,   91,   91,   91,   91,   91,   24,
   91,   91,   91,   91,  192,   91,   91,   91,   91,   91,
   91,   91,   91,  189,  196,  197,   91,   61,  112,  113,
    1,   91,   69,   91,   19,   20,   21,   22,   23,   57,
   16,   58,   59,   60,   61,    5,   62,   63,   64,   65,
   66,   67,   68,   21,   50,   20,   50,   69,   50,   92,
   41,   82,   70,   19,   20,   21,   22,   23,   57,   45,
   58,   59,   60,   61,    8,   62,   63,   64,   65,   66,
   67,   68,   47,    0,  176,    0,   69,    0,  177,    0,
  179,   70,   51,   51,    0,  120,   51,   51,   51,   51,
  118,  116,  171,  117,  123,  119,    0,  191,    0,    0,
    0,    0,    0,    0,    0,    0,  195,    0,  122,    0,
  121,    0,   71,   71,  198,    0,   71,   71,   71,   71,
    0,   72,   72,    0,    0,   72,   72,   72,   72,    0,
    0,   19,   20,   21,   22,   23,    0,    0,    0,  124,
    0,    0,    0,    0,    0,    0,    0,    0,   59,   59,
   98,    0,   59,   59,   59,   59,    0,   60,   60,    0,
    0,   60,   60,   60,   60,   55,    0,    0,    0,   41,
   55,   55,    0,   55,   55,   55,    0,    0,    0,    0,
    0,    0,   19,   20,   21,   22,   23,   41,   55,  120,
   55,    0,    0,  144,  118,  116,    0,  117,  123,  119,
    0,   61,   61,    0,   24,   61,   61,   61,   61,    0,
    0,   72,  122,    0,  121,    0,  120,    0,   73,   55,
  161,  118,  116,   71,  117,  123,  119,  120,    0,    0,
    0,  163,  118,  116,    0,  117,  123,  119,    0,  122,
    0,  121,    0,  124,   66,    0,    0,   66,  120,    0,
  122,    0,  121,  118,  116,    0,  117,  123,  119,  120,
   68,   66,   66,   68,  118,  116,    0,  117,  123,  119,
  124,  122,    0,  121,    0,    0,    0,   68,   68,    0,
  180,  124,  122,  120,  121,    0,    0,    0,  118,  116,
    0,  117,  123,  119,    0,    0,   66,    0,    0,    0,
    0,    0,  124,   48,  174,    0,  122,    0,  121,    0,
   48,  120,   68,  124,    0,   48,  118,  116,    0,  117,
  123,  119,    0,    0,    0,  110,  111,    0,    0,  112,
  113,  114,  115,  188,  122,    0,  121,  124,  120,  182,
    0,    0,  193,  118,  116,    0,  117,  123,  119,  120,
    0,    0,    0,    0,  118,  116,    0,  117,  123,  119,
    0,  122,    0,  121,    0,  124,    0,    0,    0,    0,
   54,    0,  122,    0,  121,   54,   54,    0,   54,   54,
   54,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  120,  124,   54,    0,   54,  118,  116,    0,  117,
  123,  119,    0,  124,    0,   55,   55,    0,    0,   55,
   55,   55,   55,    0,  122,   72,  121,    0,    0,    0,
    0,    0,   73,    0,   54,    0,    0,   71,    0,  110,
  111,    0,    0,  112,  113,  114,  115,    0,    0,  107,
   57,    0,   58,   72,    0,  124,    0,    0,    0,   64,
   73,   66,   67,   68,    0,   71,  110,  111,   69,    0,
  112,  113,  114,  115,    0,    0,    0,  110,  111,    0,
    0,  112,  113,  114,  115,   40,    0,    0,    0,    0,
   66,   66,    0,    0,    0,    0,   66,   66,  110,  111,
    0,    0,  112,  113,  114,  115,   68,   68,    0,  110,
  111,    0,    0,  112,  113,  114,  115,    0,  120,    0,
    0,    0,    0,  118,  116,    0,  117,  123,  119,    0,
    0,    0,   67,  110,  111,   67,    0,  112,  113,  114,
  115,  122,   48,  121,   48,    0,    0,    0,    0,   67,
   67,   48,    0,   48,   48,   48,   48,    0,    0,    0,
   48,  110,  111,    0,    0,  112,  113,  114,  115,    0,
    0,    0,  124,   87,    0,    0,    0,    0,    0,    0,
   65,    0,    0,   65,   67,    0,    0,    0,  110,  111,
    0,    0,  112,  113,  114,  115,    0,   65,   65,  110,
  111,    0,    0,  112,  113,  114,  115,    0,   88,   87,
    0,    0,   57,    0,   57,   57,   57,    0,    0,    0,
   54,   54,    0,    0,   54,   54,   54,   54,    0,   57,
   57,   57,   65,   57,   58,    0,   58,   58,   58,    0,
    0,  110,    0,    0,   88,  112,  113,  114,  115,    0,
    0,   58,   58,   58,   57,   58,   58,   64,    0,   62,
   64,    0,   62,   64,   57,   66,   67,   68,    0,    0,
   63,    0,   69,   63,   64,   64,   62,   62,   87,    0,
   87,    0,   57,    0,   58,    0,   58,   63,   63,    0,
    0,   64,    0,   66,   67,   68,    0,   87,    0,    0,
   69,    0,    0,    0,    0,   87,   87,    0,    0,   64,
    0,   62,    0,   88,   87,   88,    0,    0,    0,    0,
    0,    0,   63,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   88,    0,    0,    0,    0,    0,    0,    0,
   88,   88,    0,    0,    0,    0,    0,    0,    0,   88,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  112,  113,  114,  115,    0,    0,   67,   67,
    0,    0,    0,    0,   67,   67,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,   65,    0,    0,
    0,    0,   65,   65,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   57,   57,
    0,    0,   57,   57,   57,   57,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   58,   58,   95,    0,   58,   58,   58,   58,    0,    0,
    0,  105,  106,  108,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   64,   64,   62,   62,    0,    0,   64,
   64,  130,    0,  132,    0,    0,   63,   63,    0,    0,
  137,    0,    0,  141,    0,    0,    0,    0,    0,    0,
  145,  146,  147,  148,  149,  150,  151,  152,  153,  154,
  155,  156,  157,    0,  158,    0,    0,    0,  159,    0,
    0,    0,    0,    0,  164,    0,  168,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  137,    0,  178,    0,    0,    0,    0,    0,    0,  183,
    0,    0,  185,  186,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   91,   91,   37,   91,   40,   92,   15,   42,
   43,   45,   45,   46,   47,   41,  123,   40,   44,   46,
  261,   41,   41,   30,   44,   59,  276,   60,   41,   62,
   33,   44,   58,   59,   16,  261,   18,   40,   37,   41,
   59,   41,   45,   42,   37,  286,  125,   46,   47,   42,
   43,  263,   45,   46,   47,   37,  276,   41,   91,   33,
   44,   49,   39,   51,   91,   42,   40,   93,  276,  123,
  276,   45,   37,   50,  123,  125,   41,   42,   43,   44,
   45,   46,   47,  257,  258,  259,  260,  261,  276,  123,
   59,  125,   91,   58,   59,   60,   61,   62,   91,   40,
  123,   93,   37,  188,   40,   44,   41,   42,   43,   44,
   45,   37,   47,   41,   41,   41,   42,   43,   44,   45,
  123,   47,  125,   58,   59,   60,   91,   62,   93,   16,
   40,   18,   58,   59,   60,   40,   62,   24,   37,   40,
  123,   59,   41,   42,   43,   44,   45,   37,   47,  123,
   37,   41,   42,   43,   44,   45,  263,   47,   93,   58,
   59,   60,   40,   62,   40,   40,   40,   93,   58,   59,
   60,   59,   62,   59,   59,  276,  125,   64,  257,  258,
  259,  260,  261,   40,   61,   91,   41,  276,  276,   41,
  276,   37,  276,  276,   93,   41,   42,   43,   44,   45,
  279,   47,   40,   93,   41,   59,   41,  257,  258,  259,
  260,  261,   58,   59,   60,   44,   62,  276,  276,  276,
   41,  125,   44,  257,  258,  259,  260,  261,  262,  279,
  264,  265,  266,  267,   41,  269,  270,  271,  272,  273,
  274,  275,  276,  268,  287,   41,  280,   93,  281,  282,
    0,  285,  278,  287,  257,  258,  259,  260,  261,  262,
  123,  264,  265,  266,  267,   59,  269,  270,  271,  272,
  273,  274,  275,   41,  276,   41,  276,  280,  276,   59,
  125,   41,  285,  257,  258,  259,  260,  261,  262,  125,
  264,  265,  266,  267,    4,  269,  270,  271,  272,  273,
  274,  275,   42,   -1,  160,   -1,  280,   -1,  161,   -1,
  163,  285,  277,  278,   -1,   37,  281,  282,  283,  284,
   42,   43,   44,   45,   46,   47,   -1,  180,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  189,   -1,   60,   -1,
   62,   -1,  277,  278,  197,   -1,  281,  282,  283,  284,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,  257,  258,  259,  260,  261,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
  276,   -1,  281,  282,  283,  284,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,   59,   60,   37,
   62,   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,
   -1,  277,  278,   -1,  279,  281,  282,  283,  284,   -1,
   -1,   33,   60,   -1,   62,   -1,   37,   -1,   40,   91,
   41,   42,   43,   45,   45,   46,   47,   37,   -1,   -1,
   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,   60,
   -1,   62,   -1,   91,   41,   -1,   -1,   44,   37,   -1,
   60,   -1,   62,   42,   43,   -1,   45,   46,   47,   37,
   41,   58,   59,   44,   42,   43,   -1,   45,   46,   47,
   91,   60,   -1,   62,   -1,   -1,   -1,   58,   59,   -1,
   58,   91,   60,   37,   62,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   91,   33,   93,   -1,   60,   -1,   62,   -1,
   40,   37,   93,   91,   -1,   45,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   59,   60,   -1,   62,   91,   37,   93,
   -1,   -1,   41,   42,   43,   -1,   45,   46,   47,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   60,   -1,   62,   -1,   91,   -1,   -1,   -1,   -1,
   37,   -1,   60,   -1,   62,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   37,   91,   60,   -1,   62,   42,   43,   -1,   45,
   46,   47,   -1,   91,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   60,   33,   62,   -1,   -1,   -1,
   -1,   -1,   40,   -1,   91,   -1,   -1,   45,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   -1,  261,
  262,   -1,  264,   33,   -1,   91,   -1,   -1,   -1,  271,
   40,  273,  274,  275,   -1,   45,  277,  278,  280,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   93,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  277,  278,
   -1,   -1,  281,  282,  283,  284,  277,  278,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   37,   -1,
   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   41,  277,  278,   44,   -1,  281,  282,  283,
  284,   60,  262,   62,  264,   -1,   -1,   -1,   -1,   58,
   59,  271,   -1,  273,  274,  275,  276,   -1,   -1,   -1,
  280,  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,
   -1,   -1,   91,   56,   -1,   -1,   -1,   -1,   -1,   -1,
   41,   -1,   -1,   44,   93,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   58,   59,  277,
  278,   -1,   -1,  281,  282,  283,  284,   -1,   56,   92,
   -1,   -1,   41,   -1,   43,   44,   45,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   -1,   58,
   59,   60,   93,   62,   41,   -1,   43,   44,   45,   -1,
   -1,  277,   -1,   -1,   92,  281,  282,  283,  284,   -1,
   -1,   58,   59,   60,  262,   62,  264,   41,   -1,   41,
   44,   -1,   44,  271,   93,  273,  274,  275,   -1,   -1,
   41,   -1,  280,   44,   58,   59,   58,   59,  161,   -1,
  163,   -1,  262,   -1,  264,   -1,   93,   58,   59,   -1,
   -1,  271,   -1,  273,  274,  275,   -1,  180,   -1,   -1,
  280,   -1,   -1,   -1,   -1,  188,  189,   -1,   -1,   93,
   -1,   93,   -1,  161,  197,  163,   -1,   -1,   -1,   -1,
   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  180,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  188,  189,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  197,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,  277,  278,
   -1,   -1,   -1,   -1,  283,  284,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,  281,  282,  283,  284,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   62,   -1,  281,  282,  283,  284,   -1,   -1,
   -1,   71,   72,   73,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,  277,  278,   -1,   -1,  283,
  284,   91,   -1,   93,   -1,   -1,  277,  278,   -1,   -1,
  100,   -1,   -1,  103,   -1,   -1,   -1,   -1,   -1,   -1,
  110,  111,  112,  113,  114,  115,  116,  117,  118,  119,
  120,  121,  122,   -1,  124,   -1,   -1,   -1,  128,   -1,
   -1,   -1,   -1,   -1,  134,   -1,  136,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  160,   -1,  162,   -1,   -1,   -1,   -1,   -1,   -1,  169,
   -1,   -1,  172,  173,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=289;
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
"SEALED","GUARDED","UMINUS","EMPTY",
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

//#line 471 "Parser.y"
    
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
//#line 641 "Parser.java"
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
//#line 281 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 54:
//#line 290 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 57:
//#line 296 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 300 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 304 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 308 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 312 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 316 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 320 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 64:
//#line 324 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 328 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 332 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 67:
//#line 336 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 68:
//#line 340 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 69:
//#line 344 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 70:
//#line 348 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 71:
//#line 352 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 72:
//#line 356 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 73:
//#line 360 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 74:
//#line 364 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 75:
//#line 368 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 76:
//#line 374 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 77:
//#line 378 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 78:
//#line 382 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 79:
//#line 386 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 80:
//#line 392 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 81:
//#line 396 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 83:
//#line 403 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 84:
//#line 410 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 85:
//#line 414 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 86:
//#line 421 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 87:
//#line 427 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 88:
//#line 433 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 89:
//#line 439 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 90:
//#line 445 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 91:
//#line 449 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 92:
//#line 455 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 93:
//#line 459 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 94:
//#line 465 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1283 "Parser.java"
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
