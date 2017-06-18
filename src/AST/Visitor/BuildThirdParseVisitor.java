package AST.Visitor;

import AST.*;
import SymbolTables.ClassSymbolTable;
import TypePack.ClassType;

// Sample print visitor from MiniJava web site with small modifications for UW CSE.
// HP 10/11

public class BuildThirdParseVisitor implements Visitor {
  public String cname,mname;

  // Display added for toy example language.  Not used in regular MiniJava
  public void visit(Display n) {
    n.e.accept(this);
  }
  
  // MainClass m;
  // ClassDeclList cl;
  public void visit(Program n) {
    n.m.accept(this);
    for ( int i = 0; i < n.cl.size(); i++ ) {
        n.cl.get(i).accept(this);
    }
  }
  
  // Identifier i1,i2;
  // Statement s;
  public void visit(MainClass n) {
    cname = n.i1.s;
    mname = "main";
    n.s.accept(this);
  }

  // Identifier i;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclSimple n) {
    ClassSymbolTable ta = BuildSymbolGlobalTableVisitor.global.lookUp(n.i.s);
    for ( int i = 0; i < n.vl.size(); i++ ) {
      if(ta.varList.get(n.vl.get(i).i.s) instanceof ClassType){
        if(BuildSymbolGlobalTableVisitor.global.lookUp(((ClassType) ta.varList.get(n.vl.get(i).i.s)).representation) == null){
          System.out.println("Variavel declarada com classe "+((ClassType) ta.varList.get(n.vl.get(i).i.s)).representation+
                  " inexistente não existente na linha: " + n.vl.get(i).i.line_number);
        }
      }
    }
    cname = n.i.s;
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.get(i).accept(this);
    }
  }
 
  // Identifier i;
  // Identifier j;
  // VarDeclList vl;
  // MethodDeclList ml;
  public void visit(ClassDeclExtends n) {
    ClassSymbolTable ta = BuildSymbolGlobalTableVisitor.global.lookUp(n.i.s);
    verificarClasse(n.j.s,n.j.line_number);//verifica se classe estendida existe
    for ( int i = 0; i < n.vl.size(); i++ ) {
      if(ta.varList.get(n.vl.get(i).i.s) instanceof ClassType){
        if(BuildSymbolGlobalTableVisitor.global.lookUp(((ClassType) ta.varList.get(n.vl.get(i).i.s)).representation) == null){
          System.out.println("Variavel declarada com classe "+((ClassType) ta.varList.get(n.vl.get(i).i.s)).representation+
                  " inexistente não existente na linha: " + n.vl.get(i).i.line_number);
        }
      }
    }
    cname = n.i.s;
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.get(i).accept(this);
    }
  }

  private void verificarClasse(String s, int line_number) {

  }

  // TypePack t;
  // Identifier i;
  public void visit(VarDecl n) {
    n.t.accept(this);
    n.i.accept(this);
  }

  // TypePack t;
  // Identifier i;
  // FormalList fl;
  // VarDeclList vl;
  // StatementList sl;
  // Exp e;
  public void visit(MethodDecl n) {
    ClassSymbolTable ta = BuildSymbolGlobalTableVisitor.global.lookUp(cname);
    if(ta.methodList.get(n.i.s) instanceof ClassType){
        if(BuildSymbolGlobalTableVisitor.global.lookUp(((ClassType) ta.methodList.get(n.i.s)).representation) == null){
          System.out.println("Retorno declarado com classe "+((ClassType) ta.methodList.get(n.i.s)).representation+
                  " inexistente não existente na linha: " + n.i.line_number);
        }
    }
    n.i.accept(this);
    for ( int i = 0; i < n.fl.size(); i++ ) {
        n.fl.get(i).accept(this);
    }
    for ( int i = 0; i < n.vl.size(); i++ ) {
        n.vl.get(i).accept(this);
    }

    for ( int i = 0; i < n.sl.size(); i++ ) {
        mname = n.i.s;
        n.sl.get(i).accept(this);
    }
    n.e.accept(this);
  }

  // TypePack t;
  // Identifier i;
  public void visit(Formal n) {
    n.t.accept(this);
    n.i.accept(this);
  }

  public void visit(IntArrayType n) {
  }

  public void visit(BooleanType n) {

  }

  public void visit(IntegerType n) {

  }

  // String s;
  public void visit(IdentifierType n) {

  }

  // StatementList sl;
  public void visit(Block n) {
    for ( int i = 0; i < n.sl.size(); i++ ) {
        n.sl.get(i).accept(this);
    }
  }

  // Exp e;
  // Statement s1,s2;
  public void visit(If n) {
    n.e.accept(this);
    n.s1.accept(this);
    n.s2.accept(this);
  }

  // Exp e;
  // Statement s;
  public void visit(While n) {
    n.e.accept(this);
    n.s.accept(this);
  }

  // Exp e;
  public void visit(Print n) {
    n.e.accept(this);
  }
  
  // Identifier i;
  // Exp e;
  public void visit(Assign n) {
    n.i.accept(this);
    n.e.accept(this);
  }

  // Identifier i;
  // Exp e1,e2;
  public void visit(ArrayAssign n) {
    n.i.accept(this);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(And n) {
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(LessThan n) {
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(Plus n) {
    System.out.println(n.e2.type.print()+n.e2.line_number);

  }

  // Exp e1,e2;
  public void visit(Minus n) {
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(Times n) {
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(ArrayLookup n) {
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e;
  public void visit(ArrayLength n) {
    n.e.accept(this);
  }

  // Exp e;
  // Identifier i;
  // ExpList el;
  public void visit(Call n) {
    n.e.accept(this);

    n.i.accept(this);

    for ( int i = 0; i < n.el.size(); i++ ) {
        n.el.get(i).accept(this);
    }
  }

  // int i;
  public void visit(IntegerLiteral n) {
  }

  public void visit(True n) {

  }

  public void visit(False n) {

  }

  // String s;
  public void visit(IdentifierExp n) {
  }

  public void visit(This n) {

  }

  // Exp e;
  public void visit(NewArray n) {
    n.e.accept(this);
  }

  // Identifier i;
  public void visit(NewObject n){
            n.i.accept(this);
  }

  // Exp e;
  public void visit(Not n) {
    n.e.accept(this);
  }

  // String s;
  public void visit(Identifier n) {
  }
  private void VerificarMethod(String s,int n) {
    ClassSymbolTable ta = BuildSymbolGlobalTableVisitor.global.lookUp(cname);
    ClassSymbolTable tb = new ClassSymbolTable();
    if(BuildSymbolGlobalTableVisitor.global.lookUp(ta.nomedoPai)!=null){
      tb = BuildSymbolGlobalTableVisitor.global.lookUp(ta.nomedoPai);
    }
    if(!(ta.methodList.containsKey(s)||tb.methodList.containsKey(s)))
      System.out.println("Erro! metodo  \'"+s +"\' não declarado na linha: "+n + cname);;
  }
  public void VerificarClass(String s,int n){
    if(BuildSymbolGlobalTableVisitor.global.lookUp(s) == null)
      System.out.println("Erro! classe \'"+s +"\' não declarado na linha: "+n);
  }
}
