 /* Secci�n de declaraciones de JFlex */
package scanner;
import java_cup.runtime.*;
import parser.sym;
%%
%caseless 
%ignorecase
%line
%ignorecase
%public
%class ScannerABC
%cup
%{
 
 /* C�digo personalizado */
 
 // Se agreg� una propiedad para verificar si existen tokens pendientes
 private boolean _existenTokens = false;
 
 public boolean existenTokens(){
 return this._existenTokens;
 }

private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
}

private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
}

private Symbol symbol(String value, int type, int line) {
    return new Symbol(type, yyline, yycolumn, (Object)value);
}
 
%}
 
 /* Al utilizar esta instrucci�n, se le indica a JFlex que devuelva objetos del tipo TokenPersonalizado */
%type Symbol
 
%init{
 /* C�digo que se ejecutar� en el constructor de la clase */
%init}
 
%eof{
 
 /* C�digo a ejecutar al finalizar el an�lisis, en este caso cambiaremos el valor de una variable bandera */
 this._existenTokens = false;
 
%eof}
 
/* Inicio de Expresiones regulares */
 
 Space = " "
 NewLine = \n|\r|\r\n
 
NewLine         =       \n|\r|\r\n
InputChar       =       [^\n\r]
SpaceChar       =       [\ | \t]*
LineChar        =       \n | \r | \r | \n  

   
Alpha           =       [A-Za-z_] 
Digit           =       [0-9]
AlphaNumeric    =       {Alpha}|{Digit}
Identifier      =       {Alpha}({AlphaNumeric})*
Number          =       ({Digit})+
WhiteSpace      =       ([\ \n\r\t\f])+ 
Zero            =       0
Integer         =       [1-9][0-9]* | {Zero}
Float1          =       [0-9]+ \. [0-9]+

Exponent        =       [E] [\+ \-]? [0-9]+
ScienNot        =       {Integer}|{Float1} {Exponent} 
Float1          =       [0-9]+ \. [0-9]+ {Exponent}?
Float2          =       \. [0-9]+ {Exponent}?
Float3          =       [0-9]+ \. {Exponent}? 
Float           =       ( {Float1} | {Float2} | {Float3} )


BooleanOp       =       (AND)|(OR)|(NOT)|(XOR)|(\>=)|(\>)|(\<=)|(\<)|
                        (\<>)

BlockComment    =       \( \* ([^\}]|{NewLine})* \* \) | \{ ([^\}]|{NewLine})* \}
LineComment     =       \/ \/ (.)*

ReservedWords   =       (BYTE)|(DOWNTO)|(FILE)|
                        (FORWARD)|(GOTO)|(IN)|(INLINE)|(LABEL) |(NIL)|
                        (PACKED)|(REAL)|(RECORD)|
                        (REPEAT)|(SET)|
                        (TYPE)|(WITH)

R_Program       =       (PROGRAM)

R_Array         =       (ARRAY)

R_Begin         =       (BEGIN)

R_Boolean       =       (BOOLEAN)

R_Case          =       (CASE)

R_Char          =       (CHAR)

R_Const         =       (CONST)

R_Do            =       (DO)

R_Else          =       (ELSE)

R_End           =       (END)

R_False         =       (FALSE)

R_For           =       (FOR)

R_Function      =       (FUNCTION)

R_If            =       (IF)

R_Int           =       (INT)

R_LongInt       =       (LONGINT)

R_Of            =       (OF)

R_Procedure     =       (PROCEDURE)

R_Read          =       (READ)

R_Repeat        =       (REPEAT)

R_ShortInt      =       (SHORTINT)

R_String        =       (STRING)

R_Then          =       (THEN)

R_To            =       (TO)

R_True          =       (TRUE)

R_Until         =       (UNTIL)

R_Var           =       (VAR)

R_While         =       (WHILE)

R_Write         =       (WRITE)

O_Equal         =       (\=)

o_OpenP         =       (\()

O_CloseP        =       (\))

O_Equal         =       (\=)

O_Comma         =       (\,)

O_Semi          =       (\;)

O_Colon         =       (\:)


Operators       =       (\,)|(\;) |
                        (\[)|(\])|(\.)|(\>>)|(\<<)|(\<<=)|(\>>=)


ArithmeticOp    =       (\++) | (\--) | (\+) | (\*)| (\/) |(MOD) |(DIV)
                        |(\+=)|(\-=)|(\*=)| (\/=)


MinusOp         =       (\-)


OpenParenthesis =       (\() 


CloseParenthesis =      (\)) 


Symbols         =       (\@) | (\#) | (\%) | (\$)
                        | (\^) | (\&)  
                        | (\º) | (\á) | (\!) | (\¿)
                        | (\¡) | (\*)

InvalidSymbols  =       (\@) | (\%) | (\$)
                        | (\^) | (\&)
                        | (\º) | (\á) | (\!) | (\¿)
                        | (\¡)



String          =       \" ([^\"] |{NewLine})* \" 
Char            =       \" ([^\"] |{NewLine}) \"

NumericChar     =       \# {Number}

Null            =       \0
//EOF             =       <<EOF>>



//Errors
FloatError1     =       [0-9]+ \. 
FloatError2     =       (\. {Number})

ExponentError        =       [E] [\+ \-]? Float1
ScienNotError        =       {Integer}|{Float1} [\E] {Digit}  [\.] \{Digit} ]



IdentifierError =       ({Digit}|{Char} | {String} | {ScienNot} | {Float1} | 
                        {NumericChar} | {Symbols})+

                        {Alpha}+ | {Symbols}(Idenfitifer)

//IntegerError    =       {Digit}+
StringError     =       \" ([^\"] |{NewLine})* 

InvalidCharacter =      ({Alpha})*({InvalidSymbols})+({AlphaNumeric})*

CommentError    =       \( \* ([^\*)]|{NewLine})* | \{ ([^\}]|{NewLine})*

NoMatch         =       (.)




 
%%
{SpaceChar} {
    //Espacios y tabuladores
}


{CommentError} {
    Symbol t = new Symbol(sym.ERROR_COMMENT, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.ERROR_COMMENT, yyline);
    System.err.println("Lexical error in line " + yyline + ": " + "Error de comentario. Revise si faltan caracteres de cierre");
    this._existenTokens = true;
    return t;
    //return symbol(sym.ERROR_COMMENT);
}

{InvalidCharacter} {
    //Token t = new Token(yytext(), Types.ERROR_INVALID_CHARACTER, yyline);
    Symbol t = new Symbol(sym.ERROR_INVALID_CHARACTER, yyline, yycolumn, (Object)yytext());
    System.err.println("Lexical error in line " + yyline + ": " + "Caracter invalido ( " + (Object)yytext() + " )" );
    this._existenTokens = true;
    //return new Symbol(sym.ERROR_INVALID_CHARACTER);
    return t; 
}


{IdentifierError} {
    Symbol t = new Symbol(sym.ERROR_IDENTIFIER, yyline, yycolumn, (Object)yytext());
    System.err.println("Lexical error in line " + yyline + ": " + "Identificador erroneo ( " + (Object)yytext() + " )");
    this._existenTokens = true;
    //return symbol(sym.ERROR_IDENTIFIER);
    return t;
}

{NumericChar} {
    Symbol t = new Symbol(sym.NUMERIC_CHAR_LITERAL, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.NUMERIC_CHAR_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

{Char} {
    Symbol t = new Symbol(sym.CHAR_LITERAL, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.CHAR_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

{String} {
    Symbol t = new Symbol(sym.STRING_LITERAL, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.STRING_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

{StringError} { 
    Symbol t = new Symbol(sym.ERROR_STRING, yyline, yycolumn, (Object)yytext());
    System.err.println("Lexical error in line " + yyline + ": " + "Error de string ( " + (Object)yytext() + " )" );
    this._existenTokens = true;
    return t;
}


{BooleanOp} {
    Symbol t = new Symbol(sym.BOOLEAN_OPERATOR, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{ReservedWords} {
    Symbol t = new Symbol(sym.RESERVED, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.RESERVED, yyline);
    this._existenTokens = true;
    return t;
}

{Integer} {
    Symbol t = new Symbol(sym.INTEGER_NUMERIC_LITERAL, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.INTEGER_NUMERIC_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}


{OpenParenthesis} {
    Symbol t = new Symbol(sym.OPEN_PARENTHESIS, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.OPEN_PARENTHESIS, yyline);
    this._existenTokens = true;
    return t;
}


{CloseParenthesis} {
    Symbol t = new Symbol(sym.CLOSE_PARENTHESIS, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.CLOSE_PARENTHESIS, yyline);
    this._existenTokens = true;
    return t;
}

/*
R_Program
R_Array
R_Begin
R_Boolean
R_Case
R_If
R_Int
*/


{R_Char } {
    Symbol t = new Symbol(sym.CHAR, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Do} {
    Symbol t = new Symbol(sym.DO, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_False} {
    Symbol t = new Symbol(sym.FALSE, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_For} {
    Symbol t = new Symbol(sym.FOR, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Function} {
    Symbol t = new Symbol(sym.FUNCTION, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Read} {
    Symbol t = new Symbol(sym.READ, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Procedure} {
    Symbol t = new Symbol(sym.PROCEDURE, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Of} {
    Symbol t = new Symbol(sym.OF, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Repeat} {
    Symbol t = new Symbol(sym.REPEAT, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

/*{R_ShortInt} {
    Symbol t = new Symbol(sym.SHORTINT, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}*/

{R_String} {
    Symbol t = new Symbol(sym.STRING, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}


{R_Then} {
    Symbol t = new Symbol(sym.THEN, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_To} {
    Symbol t = new Symbol(sym.TO, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_True} {
    Symbol t = new Symbol(sym.TRUE, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Until} {
    Symbol t = new Symbol(sym.UNTIL, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Var} {
    Symbol t = new Symbol(sym.VAR, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_While} {
    Symbol t = new Symbol(sym.WHILE, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Write} {
    Symbol t = new Symbol(sym.WRITE, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Else} {
    Symbol t = new Symbol(sym.ELSE, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Then} {
    Symbol t = new Symbol(sym.THEN, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}


{R_If} {
    Symbol t = new Symbol(sym.IF, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_Int} {
    Symbol t = new Symbol(sym.INT, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}


{R_Begin} {
    Symbol t = new Symbol(sym.BEGIN, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{O_Equal} {
    Symbol t = new Symbol(sym.EQUAL, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}


{O_Comma} {
    Symbol t = new Symbol(sym.COMMA, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{O_Semi} {
    Symbol t = new Symbol(sym.SEMI, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{O_Colon} {
    Symbol t = new Symbol(sym.COLON, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{R_End} {
    Symbol t = new Symbol(sym.END, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}



{ScienNotError} {
    Symbol t = new Symbol(sym.ERROR_FLOATING_POINT, yyline, yycolumn, (Object)yytext());
    System.err.println("Lexical error in line " + yyline + ": " + "Notacion cientifica incorrecta ( " + (Object)yytext() + " )" );
    this._existenTokens = true;
    return t;
}

{ScienNot} {
    Symbol t = new Symbol(sym.SCIENTIFIC_NOTATION_NUMERIC_LITERAL, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.SCIENTIFIC_NOTATION_NUMERIC_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

 {R_Program} {
    Symbol t = new Symbol(sym.PROGRAM, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

 {R_Var} {
    Symbol t = new Symbol(sym.VAR, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

 {R_Const} {
    Symbol t = new Symbol(sym.CONST, yyline, yycolumn, (Object)yytext());
    this._existenTokens = true;
    return t;
}

{Identifier} {
    Symbol t = new Symbol(sym.IDENTIFIER, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.IDENTIFIER, yyline);
    if (yytext().length()>127){
        t = new Symbol(sym.ERROR_INVALID_LENGTH, yyline, yycolumn, (Object)yytext());
        System.err.println("Lexical error in line " + yyline + ": " + "Longitud invalida ( " + (Object)yytext() + " )" );
    }
     this._existenTokens = true;
     return t;
}
 

{FloatError1} {
    Symbol t = new Symbol(sym.ERROR_FLOATING_POINT, yyline, yycolumn, (Object)yytext());
    System.err.println("Lexical error in line " + yyline + ": " + "Error en numero racional ( " + (Object)yytext() + " )" );
    this._existenTokens = true;
    return t;
}

{FloatError2} {
    Symbol t = new Symbol(sym.ERROR_FLOATING_POINT, yyline, yycolumn, (Object)yytext());
    System.err.println("Lexical error in line " + yyline + ": " + "Error en numero racional ( " + (Object)yytext() + " )" );
    this._existenTokens = true;
    return t;
}

{Float} {
    Symbol t = new Symbol(sym.FLOATING_POINT_NUMERIC_LITERAL, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.FLOATING_POINT_NUMERIC_LITERAL, yyline);
    this._existenTokens = true;
    return t;
}

/*{EOF} {
 Token t = new Token(yytext(), Types.EOF);
 this._existenTokens = true;
 return t;
}*/

{Operators} {
    Symbol t = new Symbol(sym.OPERATOR, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.OPERATOR, yyline);
    this._existenTokens = true;
    return t;
}

{ArithmeticOp} {
    Symbol t = new Symbol(sym.ARITHMETICOP, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.ARITHMETICOP, yyline);
    this._existenTokens = true;
    return t;
}

{MinusOp} {
    Symbol t = new Symbol(sym.MINUSOP, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.ARITHMETICOP, yyline);
    this._existenTokens = true;
    return t;
}



/*{IntegerError} {
    Symbol t = new Symbol(sym.ERROR_INTEGER, yyline, yycolumn, (Object)yytext());
    //Token t = new Token(yytext(), Types.ERROR_INTEGER, yyline);
    this._existenTokens = true;
    return t;
}*/
 

{BlockComment} {
 // Comentario de bloque 1
}
{LineComment} {
 // Comentario de bloque 1
}
{Space} {
 // Ignorar cuando se ingrese un espacio
}
 
{NewLine} {
 /*Token t = new Token("Enter", Types.IDENTIFIER);
 this._existenTokens = true;
 return t;*/
}

{NoMatch} {
    Symbol t = new Symbol(sym.ERROR_INVALID_CHARACTER, yyline, yycolumn, (Object)yytext());
    System.err.println("Lexical error in line " + yyline + ": " + "Caracter invalido ( " + (Object)yytext() + " )" );
    this._existenTokens = true;
    return t;
}