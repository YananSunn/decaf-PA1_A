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
public final static short UMINUS=292;
public final static short EMPTY=293;
public final static short VAR=294;
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
   15,   15,   15,   30,   30,   30,   31,   32,   32,   32,
   29,   29,   33,   33,   17,   18,   21,   16,   34,   34,
   19,   19,   20,
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
    3,    3,    6,    1,    1,    1,    3,    3,    0,    1,
    1,    0,    3,    1,    5,    9,    1,    6,    2,    0,
    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   15,   19,    0,   19,    0,    0,    0,    7,    8,
    6,    9,    0,    0,   12,   17,    0,    0,   18,   19,
   14,   10,    0,    4,    0,    0,    0,    0,    0,   11,
   13,    0,   23,    0,    0,    0,    0,    5,    0,    0,
    0,   28,   25,   22,   24,    0,   85,   76,    0,    0,
    0,    0,   97,    0,    0,    0,    0,   84,    0,    0,
    0,    0,    0,    0,   26,    0,   29,   38,   27,    0,
    0,   32,   33,   34,    0,    0,    0,   39,    0,    0,
    0,   57,   86,    0,    0,    0,    0,    0,   55,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   90,    0,   53,   30,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,    0,   35,   36,   37,    0,    0,    0,    0,    0,
   44,    0,    0,    0,    0,    0,   74,   75,    0,    0,
    0,   71,    0,   87,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,   77,    0,
    0,  103,    0,    0,    0,   88,   52,    0,    0,    0,
   95,    0,    0,    0,   43,   78,    0,    0,    0,    0,
    0,   54,    0,    0,   98,    0,   79,   31,   83,    0,
   99,   46,    0,   96,
};
final static short yydgoto[] = {                          3,
    4,    5,   77,   27,   44,   10,   16,   29,   45,   46,
   78,   56,   79,   80,   81,   82,   83,   84,   85,   86,
   87,   88,   99,  100,  142,  177,  178,   91,  189,   92,
   93,  113,  146,  205,
};
final static short yysindex[] = {                      -250,
 -249, -222,    0, -250,    0, -207, -216,    0, -203,  -48,
 -117,    0,    0, -199,    0,  -49,  -42,  197,    0,    0,
    0,    0, -193, -132,    0,    0,   25,  -83,    0,    0,
    0,    0,  -82,    0,   45,   -5,  241,   53, -132,    0,
    0, -132,    0,  -81,   55,   59,   64,    0,  -26, -132,
  -26,    0,    0,    0,    0,   -2,    0,    0,   67,   69,
  -22,   79,    0,  202,   70,   74,   75,    0,   77,   78,
   79,   79,   46,  -76,    0, -154,    0,    0,    0,   72,
  630,    0,    0,    0,   89,   94,  101,    0,  102,    0,
 -146,    0,    0,   79,   79,   79,    8,  630,    0,    0,
   96,   76,   79,  124,  132,   79,  -99,  -44,  -44,  -96,
  354,    0,  -23,    0,    0,   79,   79,   79,   79,   79,
   79,   79,   79,   79,   79,   79,   79,   79,   79,   79,
    0,   79,    0,    0,    0,   79,  141,  405,  123,  427,
    0,   79,  142,   54,  630,  -25,    0,    0,  448,  160,
  144,    0,  -76,    0,  810,  798,  -13,  -13,  878,  878,
  630,  630,   17,   17,  -44,  -44,  -44,  -13,  -13,  129,
  630,   79,   29,   79,   29,  469,   80,    0,    0,  491,
   79,    0,  -70,   79,   79,    0,    0,   79,  166,  172,
    0,  512,  -45,   29,    0,    0,  630,  178,  548, -244,
  604,    0,   79,   29,    0,  -52,    0,    0,    0,  209,
    0,    0,   29,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  251,    0,  130,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  207,    0,    0,    0,  233,    0,
    0,  233,    0,    0,    0,  236,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -55,    0,    0,    0,    0,    0,    0,    0,    0,
    3,    3,    3,  -21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  760,  155,
    0,    0,    0,    3,  -58,    3,   71,  221,    0,    0,
    0,    0,    3,    0,    0,    3,    0,  930,  939,    0,
    0,    0,    0,    0,    0,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    0,    3,    0,    0,    0,    3,   97,    0,    0,    0,
    0,    3,    0,    3,  -16,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    7,  120,  110,  482,   91,  382,
    9,  463, 1071, 1083,  966, 1009, 1063,  504,  621,    0,
  -24,  -28,  -58,    3,  -58,    0,    0,    0,    0,    0,
    3,    0,    0,    3,    3,    0,    0,    3,    0,  240,
    0,    0,  -33,  -58,    0,    0,    5,    0,    0,  900,
    0,    0,  -27,  -58,    0,  157,    0,    0,    0,    0,
    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  293,    4,   56,  339,    0,   22,    0,  269,    0,
    6,    0, -133,  -90, 1297,    0,    0,    0,    0,    0,
    0,    0,  827,  861,    0,    0,    0,    0,    0,  -71,
    0,    0,  140,    0,
};
final static int YYTABLESIZE=1485;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        100,
   42,  131,  112,  102,  139,   15,  100,   36,   36,   36,
    1,  100,   92,   42,   74,  182,   40,   96,  181,   26,
  153,   26,   89,  128,   94,  100,    6,   94,  126,  124,
   72,  125,  131,  127,   40,    2,   18,   73,    7,  191,
   26,  193,   71,  122,  123,   93,  132,   69,   93,   81,
   69,   37,   81,  128,   53,    9,   55,  100,  126,   11,
  206,   72,  131,  127,   69,   69,   81,   81,   73,  154,
  211,   89,   12,   71,   13,   25,   17,  132,   72,  214,
   30,  186,   32,   34,   39,   73,   72,   40,   74,  100,
   71,  100,   42,   73,   43,   49,   52,   43,   71,   69,
   97,   81,   50,   48,   51,   54,   94,  132,   95,  103,
   48,   72,  210,  104,  105,   48,  106,  107,   73,   74,
   52,  114,   75,   71,   19,   20,   21,   22,   23,  137,
  115,   63,  141,   51,   63,  143,   74,   51,   51,   51,
   51,   51,   51,   51,   74,   14,   40,  133,   63,   63,
   67,   52,  134,   67,   51,   51,   51,   51,   51,  135,
   70,   48,  136,   70,  147,  128,  144,   67,   67,   74,
  126,  124,  148,  125,  131,  127,  150,   70,   70,  151,
  172,  174,  179,   63,  185,   57,  188,   51,  130,   51,
  129,   56,   35,   38,   48,   41,   56,   56,   68,   56,
   56,   56,   67,  184,  195,  198,  202,   19,   20,   21,
   22,   23,   70,   41,   56,  181,   56,   50,  207,  132,
   50,  187,  204,  100,  100,  100,  100,  100,  100,   24,
  100,  100,  100,  100,  212,  100,  100,  100,  100,  100,
  100,  100,  100,  122,  123,   56,  100,   50,   50,  213,
    1,  100,   16,  100,   19,   20,   21,   22,   23,   57,
  100,   58,   59,   60,   61,    5,   62,   63,   64,   65,
   66,   67,   68,   21,  122,  123,   20,   69,   50,  101,
   91,   45,   70,   69,   69,   19,   20,   21,   22,   23,
   57,   76,   58,   59,   60,   61,    8,   62,   63,   64,
   65,   66,   67,   68,  122,  123,  110,   57,   69,   58,
   47,  190,    0,   70,    0,   57,   64,   58,   66,   67,
   68,   31,   76,    0,   64,   69,   66,   67,   68,    0,
    0,    0,   48,   69,   48,    0,    0,    0,    0,   76,
   57,   48,   58,   48,   48,   48,   48,   76,    0,   64,
   48,   66,   67,   68,   28,    0,   28,    0,   69,    0,
    0,    0,   33,    0,   48,   41,    0,   63,   63,    0,
    0,    0,   76,   51,   51,   28,    0,   51,   51,   51,
   51,    0,    0,    0,   51,   51,   67,   67,    0,    0,
  128,    0,   67,   67,  152,  126,  124,   70,  125,  131,
  127,    0,  102,    0,    0,  116,  117,    0,    0,  118,
  119,  120,  121,  130,    0,  129,  122,  123,    0,    0,
    0,    0,   64,    0,    0,   64,    0,    0,    0,    0,
    0,   56,   56,    0,    0,   56,   56,   56,   56,   64,
   64,  128,   56,   56,  132,  173,  126,  124,    0,  125,
  131,  127,    0,   19,   20,   21,   22,   23,   19,   20,
   21,   22,   23,  128,  130,    0,  129,  175,  126,  124,
    0,  125,  131,  127,   64,   24,    0,  101,    0,    0,
    0,    0,    0,    0,  128,    0,  130,    0,  129,  126,
  124,  183,  125,  131,  127,  132,    0,   19,   20,   21,
   22,   23,    0,   82,    0,  128,   82,  130,    0,  129,
  126,  124,    0,  125,  131,  127,    0,  132,    0,   24,
   82,   82,   68,    0,    0,   68,  194,  128,  130,    0,
  129,    0,  126,  124,    0,  125,  131,  127,  132,   68,
   68,    0,    0,    0,   66,    0,    0,   66,  128,    0,
  130,    0,  129,  126,  124,   82,  125,  131,  127,  132,
    0,   66,   66,    0,    0,    0,    0,    0,    0,    0,
  203,  130,    0,  129,   68,    0,    0,    0,    0,    0,
    0,  132,    0,  196,  128,    0,    0,    0,  208,  126,
  124,    0,  125,  131,  127,    0,   66,    0,    0,    0,
    0,    0,  132,    0,    0,    0,    0,  130,    0,  129,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  116,  117,    0,    0,  118,  119,  120,  121,  132,    0,
  128,  122,  123,    0,    0,  126,  124,    0,  125,  131,
  127,    0,    0,    0,    0,    0,    0,    0,   64,   64,
    0,   65,    0,  130,   65,  129,  128,    0,    0,    0,
    0,  126,  124,    0,  125,  131,  127,    0,   65,   65,
    0,  116,  117,    0,    0,  118,  119,  120,  121,  130,
    0,  129,  122,  123,  132,    0,  209,    0,    0,    0,
    0,    0,    0,  116,  117,    0,    0,  118,  119,  120,
  121,    0,    0,   65,  122,  123,    0,    0,    0,    0,
  132,    0,    0,    0,  116,  117,    0,    0,  118,  119,
  120,  121,    0,    0,    0,  122,  123,    0,    0,    0,
    0,    0,    0,    0,    0,  116,  117,    0,    0,  118,
  119,  120,  121,    0,    0,    0,  122,  123,   68,   68,
    0,    0,    0,    0,   68,   68,    0,  116,  117,    0,
    0,  118,  119,  120,  121,    0,    0,    0,  122,  123,
   66,   66,    0,    0,    0,    0,   66,   66,  116,  117,
    0,    0,  118,  119,  120,  121,   55,    0,    0,  122,
  123,   55,   55,    0,   55,   55,   55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   55,
    0,   55,    0,    0,  116,  117,    0,    0,  118,  119,
  120,  121,    0,    0,  128,  122,  123,    0,    0,  126,
  124,    0,  125,  131,  127,    0,  128,    0,    0,    0,
   55,  126,  124,    0,  125,  131,  127,  130,    0,  129,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  130,
    0,  129,    0,    0,    0,    0,    0,    0,    0,    0,
  116,  117,   89,    0,  118,  119,  120,  121,  132,    0,
    0,  122,  123,    0,    0,    0,    0,   65,   65,    0,
  132,    0,    0,   65,   65,    0,  116,  117,    0,    0,
  118,  119,  120,  121,  128,    0,   90,  122,  123,  126,
  124,   89,  125,  131,  127,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   80,  130,    0,  129,
   80,   80,   80,   80,   80,   80,   80,    0,    0,    0,
    0,    0,    0,    0,    0,   90,    0,   80,   80,   80,
    0,   80,    0,    0,    0,    0,   72,    0,  132,    0,
   72,   72,   72,   72,   72,   73,   72,    0,    0,   73,
   73,   73,   73,   73,    0,   73,    0,   72,   72,   72,
   80,   72,   80,    0,    0,    0,   73,   73,   73,   89,
   73,   89,   60,    0,    0,    0,   60,   60,   60,   60,
   60,    0,   60,    0,    0,    0,    0,    0,    0,    0,
   89,    0,   72,   60,   60,   60,    0,   60,    0,   89,
   89,   73,    0,   90,    0,   90,   55,   55,    0,   89,
   55,   55,   55,   55,    0,   61,    0,   55,   55,   61,
   61,   61,   61,   61,   90,   61,    0,    0,   60,    0,
    0,    0,    0,   90,   90,    0,   61,   61,   61,    0,
   61,    0,    0,   90,  116,    0,    0,    0,  118,  119,
  120,  121,    0,    0,    0,  122,  123,    0,    0,    0,
  118,  119,  120,  121,    0,    0,    0,  122,  123,   62,
    0,   61,    0,   62,   62,   62,   62,   62,    0,   62,
    0,   58,    0,   58,   58,   58,    0,    0,    0,    0,
   62,   62,   62,   59,   62,   59,   59,   59,   58,   58,
   58,    0,   58,    0,    0,    0,    0,    0,    0,    0,
   59,   59,   59,    0,   59,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   62,    0,    0,  118,  119,
    0,    0,    0,   58,    0,  122,  123,    0,    0,    0,
    0,    0,    0,    0,    0,   59,   80,   80,    0,    0,
   80,   80,   80,   80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   72,   72,    0,    0,
   72,   72,   72,   72,    0,   73,   73,    0,    0,   73,
   73,   73,   73,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   60,   60,    0,    0,   60,   60,   60,   60,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   61,   61,    0,    0,   61,
   61,   61,   61,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   62,
   62,    0,    0,   62,   62,   62,   62,   58,   58,    0,
    0,   58,   58,   58,   58,    0,    0,    0,   98,   59,
   59,    0,    0,   59,   59,   59,   59,  108,  109,  111,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  138,    0,  140,    0,    0,    0,    0,    0,    0,  145,
    0,    0,  149,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  155,  156,  157,  158,  159,  160,  161,  162,
  163,  164,  165,  166,  167,  168,  169,    0,  170,    0,
    0,    0,  171,    0,    0,    0,    0,    0,  176,    0,
  180,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  145,    0,
  192,    0,    0,    0,    0,    0,    0,  197,    0,    0,
  199,  200,    0,    0,  201,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   46,   74,   59,   95,  123,   40,   91,   91,   91,
  261,   45,   41,   41,   91,   41,   41,   40,   44,   16,
   44,   18,   44,   37,   41,   59,  276,   44,   42,   43,
   33,   45,   46,   47,   59,  286,   15,   40,  261,  173,
   37,  175,   45,  288,  289,   41,   91,   41,   44,   41,
   44,   30,   44,   37,   49,  263,   51,   91,   42,  276,
  194,   33,   46,   47,   58,   59,   58,   59,   40,   93,
  204,   93,  276,   45,  123,  125,  276,   91,   33,  213,
  123,  153,  276,   59,   40,   40,   33,   93,   91,  123,
   45,  125,   40,   40,   39,   41,  123,   42,   45,   93,
  123,   93,   44,   33,   41,   50,   40,   91,   40,   40,
   40,   33,  203,   40,   40,   45,   40,   40,   40,   91,
  123,  276,  125,   45,  257,  258,  259,  260,  261,  276,
   59,   41,  125,   37,   44,   40,   91,   41,   42,   43,
   44,   45,   46,   47,   91,  263,   93,   59,   58,   59,
   41,  123,   59,   44,   58,   59,   60,   61,   62,   59,
   41,   91,   61,   44,   41,   37,   91,   58,   59,   91,
   42,   43,   41,   45,   46,   47,  276,   58,   59,  276,
   40,   59,   41,   93,   41,  262,   58,   91,   60,   93,
   62,   37,  276,  276,  276,   41,   42,   43,  275,   45,
   46,   47,   93,   44,  125,  276,   41,  257,  258,  259,
  260,  261,   93,   59,   60,   44,   62,  276,   41,   91,
  276,   93,  268,  257,  258,  259,  260,  261,  262,  279,
  264,  265,  266,  267,  287,  269,  270,  271,  272,  273,
  274,  275,  276,  288,  289,   91,  280,  276,  276,   41,
    0,  285,  123,  287,  257,  258,  259,  260,  261,  262,
  294,  264,  265,  266,  267,   59,  269,  270,  271,  272,
  273,  274,  275,   41,  288,  289,   41,  280,  276,   59,
   41,  125,  285,  277,  278,  257,  258,  259,  260,  261,
  262,  294,  264,  265,  266,  267,    4,  269,  270,  271,
  272,  273,  274,  275,  288,  289,  261,  262,  280,  264,
   42,  172,   -1,  285,   -1,  262,  271,  264,  273,  274,
  275,  125,  294,   -1,  271,  280,  273,  274,  275,   -1,
   -1,   -1,  262,  280,  264,   -1,   -1,   -1,   -1,  294,
  262,  271,  264,  273,  274,  275,  276,  294,   -1,  271,
  280,  273,  274,  275,   16,   -1,   18,   -1,  280,   -1,
   -1,   -1,   24,   -1,  294,  125,   -1,  277,  278,   -1,
   -1,   -1,  294,  277,  278,   37,   -1,  281,  282,  283,
  284,   -1,   -1,   -1,  288,  289,  277,  278,   -1,   -1,
   37,   -1,  283,  284,   41,   42,   43,  278,   45,   46,
   47,   -1,   64,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   60,   -1,   62,  288,  289,   -1,   -1,
   -1,   -1,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   58,
   59,   37,  288,  289,   91,   41,   42,   43,   -1,   45,
   46,   47,   -1,  257,  258,  259,  260,  261,  257,  258,
  259,  260,  261,   37,   60,   -1,   62,   41,   42,   43,
   -1,   45,   46,   47,   93,  279,   -1,  276,   -1,   -1,
   -1,   -1,   -1,   -1,   37,   -1,   60,   -1,   62,   42,
   43,   44,   45,   46,   47,   91,   -1,  257,  258,  259,
  260,  261,   -1,   41,   -1,   37,   44,   60,   -1,   62,
   42,   43,   -1,   45,   46,   47,   -1,   91,   -1,  279,
   58,   59,   41,   -1,   -1,   44,   58,   37,   60,   -1,
   62,   -1,   42,   43,   -1,   45,   46,   47,   91,   58,
   59,   -1,   -1,   -1,   41,   -1,   -1,   44,   37,   -1,
   60,   -1,   62,   42,   43,   93,   45,   46,   47,   91,
   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   60,   -1,   62,   93,   -1,   -1,   -1,   -1,   -1,
   -1,   91,   -1,   93,   37,   -1,   -1,   -1,   41,   42,
   43,   -1,   45,   46,   47,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   91,   -1,   -1,   -1,   -1,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,  281,  282,  283,  284,   91,   -1,
   37,  288,  289,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   41,   -1,   60,   44,   62,   37,   -1,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   58,   59,
   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,   60,
   -1,   62,  288,  289,   91,   -1,   93,   -1,   -1,   -1,
   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,
  284,   -1,   -1,   93,  288,  289,   -1,   -1,   -1,   -1,
   91,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,  288,  289,  277,  278,
   -1,   -1,   -1,   -1,  283,  284,   -1,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,  288,  289,
  277,  278,   -1,   -1,   -1,   -1,  283,  284,  277,  278,
   -1,   -1,  281,  282,  283,  284,   37,   -1,   -1,  288,
  289,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,
   -1,   62,   -1,   -1,  277,  278,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   37,  288,  289,   -1,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   37,   -1,   -1,   -1,
   91,   42,   43,   -1,   45,   46,   47,   60,   -1,   62,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   56,   -1,  281,  282,  283,  284,   91,   -1,
   -1,  288,  289,   -1,   -1,   -1,   -1,  277,  278,   -1,
   91,   -1,   -1,  283,  284,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   37,   -1,   56,  288,  289,   42,
   43,   95,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   37,   60,   -1,   62,
   41,   42,   43,   44,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   95,   -1,   58,   59,   60,
   -1,   62,   -1,   -1,   -1,   -1,   37,   -1,   91,   -1,
   41,   42,   43,   44,   45,   37,   47,   -1,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,   58,   59,   60,
   91,   62,   93,   -1,   -1,   -1,   58,   59,   60,  173,
   62,  175,   37,   -1,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  194,   -1,   93,   58,   59,   60,   -1,   62,   -1,  203,
  204,   93,   -1,  173,   -1,  175,  277,  278,   -1,  213,
  281,  282,  283,  284,   -1,   37,   -1,  288,  289,   41,
   42,   43,   44,   45,  194,   47,   -1,   -1,   93,   -1,
   -1,   -1,   -1,  203,  204,   -1,   58,   59,   60,   -1,
   62,   -1,   -1,  213,  277,   -1,   -1,   -1,  281,  282,
  283,  284,   -1,   -1,   -1,  288,  289,   -1,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,  288,  289,   37,
   -1,   93,   -1,   41,   42,   43,   44,   45,   -1,   47,
   -1,   41,   -1,   43,   44,   45,   -1,   -1,   -1,   -1,
   58,   59,   60,   41,   62,   43,   44,   45,   58,   59,
   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   58,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,  281,  282,
   -1,   -1,   -1,   93,   -1,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   93,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,
  281,  282,  283,  284,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,  281,  282,  283,  284,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,  281,
  282,  283,  284,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,  281,  282,  283,  284,  277,  278,   -1,
   -1,  281,  282,  283,  284,   -1,   -1,   -1,   62,  277,
  278,   -1,   -1,  281,  282,  283,  284,   71,   72,   73,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   94,   -1,   96,   -1,   -1,   -1,   -1,   -1,   -1,  103,
   -1,   -1,  106,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  116,  117,  118,  119,  120,  121,  122,  123,
  124,  125,  126,  127,  128,  129,  130,   -1,  132,   -1,
   -1,   -1,  136,   -1,   -1,   -1,   -1,   -1,  142,   -1,
  144,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  172,   -1,
  174,   -1,   -1,   -1,   -1,   -1,   -1,  181,   -1,   -1,
  184,  185,   -1,   -1,  188,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=294;
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
"SEALED","GUARDED","NEWSAMEARRAY","JOINTARRAY","\"%%\"","\"++\"","UMINUS",
"EMPTY","VAR",
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

//#line 515 "Parser.y"
    
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
//#line 718 "Parser.java"
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
//#line 414 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 85:
//#line 418 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 87:
//#line 425 "Parser.y"
{
						yyval.expr = new Tree.ArrayConstant(val_peek(1).elist, val_peek(2).loc);
					}
break;
case 88:
//#line 431 "Parser.y"
{
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 89:
//#line 435 "Parser.y"
{
                        yyval = new SemValue();
                    }
break;
case 90:
//#line 439 "Parser.y"
{
                        yyval.elist = new ArrayList<Tree.Expr>();
                        yyval.elist.add(val_peek(0).expr);
                    }
break;
case 92:
//#line 447 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 93:
//#line 454 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 94:
//#line 458 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 95:
//#line 465 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 96:
//#line 471 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 97:
//#line 477 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 98:
//#line 483 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 99:
//#line 489 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 100:
//#line 493 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 101:
//#line 499 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 102:
//#line 503 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 103:
//#line 509 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1409 "Parser.java"
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
