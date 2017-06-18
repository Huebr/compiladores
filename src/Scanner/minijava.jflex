/*
 * JFlex specification for the lexical analyzer for a simple demo language.
 * Change this into the scanner for your implementation of MiniJava.
 * CSE 401/P501 Au11
 */


package Scanner;

import java_cup.runtime.*;
import Parser.sym;

%%

%public
%final
%class scanner
%unicode
%cup
%line
%column

/* Code copied into the generated scanner class.  */
/* Can be referenced in scanner action code. */
%{
  // Return new symbol objects with line and column numbers in the symbol 
  // left and right fields. This abuses the original idea of having left 
  // and right be character positions, but is   // is more useful and 
  // follows an example in the JFlex documentation.
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }

  // Return a readable representation of symbol s (aka token)
  public String symbolToString(Symbol s) {
    String rep;
    switch (s.sym) {
      case sym.CLASS: return "<CLASS>";
      case sym.PUBLIC: return "<PUBLIC>";
      case sym.STATIC: return "<STATIC>";
      case sym.VOID: return "<VOID>";
      case sym.MAIN: return "<MAIN>";
      case sym.LPAREN: return "<LPAREN>";
      case sym.STRING: return "<STRING>";
      case sym.LCOCHETE: return "<LCOCHETE>";
      case sym.RCOCHETE: return "<RCOCHETE>";
      case sym.RPAREN: return "<RPAREN>";
      case sym.LCHAVE: return "<LCHAVE>";
      case sym.RCHAVE: return "<RCHAVE>";
      case sym.EXTENDS: return "<EXTENDS>";
      case sym.SCOLON: return "<SCOLON>";
      case sym.VIRGULA: return "<VIRGULA>";
      case sym.RETURN: return "<RETURN>";
      case sym.INTEGER: return "<INTEGER>";
      case sym.BOOLEANO: return "<BOOLEANO>";
      case sym.IF: return "<IF>";
      case sym.ELSE: return "<ELSE>";
      case sym.WHILE: return "<WHILE>";
      case sym.SYSO: return "<SYSO>";
      case sym.ASSIGN: return "<ASSIGN>";
      case sym.AND: return "<AND>";
      case sym.LESS: return "<LESS>";
      case sym.PLUS: return "<PLUS>";
      case sym.MINUS: return "<MINUS>";
      case sym.MULTI: return "<MULTI>";
      case sym.DOT: return "<DOT>";
      case sym.LENGTH: return "<LENGTH>";
      case sym.INTEGER_LITERAL: return "INT(" + String.valueOf(s.value) + ")";
      case sym.TRUE: return "<TRUE>";
      case sym.FALSE: return "<FALSE>";
      case sym.THIS: return "<THIS>";
      case sym.NEW: return "<NEW>";
      case sym.NEGATION: return "<NEGATION>";
      case sym.IDENTIFIER: return "ID(" + String.valueOf(s.value) + ")";
      case sym.EOF: return "<EOF>";
      case sym.error: return "<ERROR>";
      default: return "<UNEXPECTED TOKEN " + s.toString() + ">";
    }
  }
%}

/* Helper definitions */
letter = [a-zA-Z]
digit = [0-9]
eol = [\r\n]
not_eol = [^\r\n]
white = {eol}|[ \t]
start_comment = "/*"
comment_contents = ([^*]|\*[^/])
end_comment = "*/"

%%

/* Token definitions */

/* reserved words */
/* (put here so that reserved words take precedence over identifiers) */
"class" { return symbol(sym.CLASS); }
"public" { return symbol(sym.PUBLIC); }
"static" { return symbol(sym.STATIC); }
"void" { return symbol(sym.VOID); }
"main" { return symbol(sym.MAIN); }
"String" { return symbol(sym.STRING); }
"class" { return symbol(sym.CLASS); }
"extends" { return symbol(sym.EXTENDS); }
"return" { return symbol(sym.RETURN); }
"int" { return symbol(sym.INTEGER); }
"boolean" { return symbol(sym.BOOLEANO); }
"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }
"while" { return symbol(sym.WHILE); }
"System.out.println" { return symbol(sym.SYSO); }
"length" { return symbol(sym.LENGTH); }
"true" { return symbol(sym.TRUE); }
"false" { return symbol(sym.FALSE); }
"this" { return symbol(sym.THIS); }
"new" { return symbol(sym.NEW); }

/* operators */
"+" { return symbol(sym.PLUS); }
"=" { return symbol(sym.ASSIGN); }
"&&" { return symbol(sym.AND); }
"<" { return symbol(sym.LESS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.MULTI); }
"!" { return symbol(sym.NEGATION); }

/* delimiters */
"(" { return symbol(sym.LPAREN); }
")" { return symbol(sym.RPAREN); }
";" { return symbol(sym.SCOLON); }
"[" { return symbol(sym.LCOCHETE); }
"]" { return symbol(sym.RCOCHETE); }
"{" { return symbol(sym.LCHAVE); }
"}" { return symbol(sym.RCHAVE); }
"," { return symbol(sym.VIRGULA); }
"." { return symbol(sym.DOT); }


/* identifiers */
{letter} ({letter}|{digit}|_)* { return symbol(sym.IDENTIFIER, yytext()); }
{digit}+ {return symbol(sym.INTEGER_LITERAL, new Integer(yytext()));}

/* whitespace */
{white}+ { /* ignore whitespace */ }

/* comments */
"//"{not_eol}*{eol} { /* do nothing */ }
{start_comment}{comment_contents}*{end_comment} { /* do nothing */ }

/* lexical errors (put last so other matches take precedence) */
. { System.err.println(
	"\nunexpected character in input: '" + yytext() + "' at line " +
	(yyline+1) + " column " + (yycolumn+1));
  }
