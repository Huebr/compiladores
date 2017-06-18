package AST.Visitor;

import AST.*;
import SymbolTables.ClassSymbolTable;
import TypePack.ClassType;
import TypePack.UndefinedType;

import java.util.ArrayList;

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
    for ( int i = 0; i < n.vl.size(); i++ ) {
      if(n.vl.get(i).i.type instanceof TypePack.UndefinedType){
        System.out.println("Erro, variável \'"+n.vl.get(i).i.s +" "+n.vl.get(i).i.type.print() +"\' declarada com tipo inexistente na linha " + n.vl.get(i).line_number);
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
    //VerificarClass(n.j.s,n.j.line_number);//verifica se classe estendida existe
    if(!(n.type instanceof UndefinedType)){
      for ( int i = 0; i < n.ml.size(); i++ ) {
        if(BuildSymbolGlobalTableVisitor.global.lookUp(n.j.s).methodList.containsKey(n.ml.get(i).i.s)){
            compara(n.ml.get(i).fl,((DataArray)BuildSymbolLocalTableVisitor.localTable.get(n.j.s)).get(n.ml.get(i).i.s).param);
            if(!(n.ml.get(i).t.type.sameType((TypePack.Type)BuildSymbolGlobalTableVisitor.global.lookUp(n.j.s).methodList.get(n.ml.get(i).i.s)))){
              System.out.println("Erro, classe filha possue retorno diferente da pai."+ n.ml.get(i).line_number);
            }
        }
      }
      if(verificaLoop(n.i.s,n.j.s)){
         System.out.println("Classe possui loop de heranca diretamente ou indiretamente"+n.line_number);
      }
    }
    for ( int i = 0; i < n.vl.size(); i++ ) {
      if(n.vl.get(i).i.type instanceof UndefinedType){
        System.out.println("Erro, variável \'"+n.vl.get(i).i.type.print() +"\' declarada com tipo inexistente na linha " + n.vl.get(i).line_number);
      }
      /*if(ta.varList.get(n.vl.get(i).i.s) instanceof ClassType){
        if(BuildSymbolGlobalTableVisitor.global.lookUp(((ClassType) ta.varList.get(n.vl.get(i).i.s)).representation) == null){
          System.out.println("Variavel declarada com classe "+((ClassType) ta.varList.get(n.vl.get(i).i.s)).representation+
                  " inexistente não existente na linha: " + n.vl.get(i).i.line_number);
        }
      }*/
    }
    cname = n.i.s;
    for ( int i = 0; i < n.ml.size(); i++ ) {
        n.ml.get(i).accept(this);
    }
  }

  private void compara(FormalList list, ArrayList<Data> param) {
    if(list.size() == param.size()){
      for(int i = 0; i < param.size(); i++){
        if(!list.get(i).i.type.sameType(param.get(i).ttype)) {
          System.out.println("Parametro " + (i+1) + " esperava " + param.get(i).ttype.print() + ", mas recebeu " + list.get(i).i.type.print() +" " + list.line_number);
        }
      }
    } else
        System.out.println("Quantidade de parâmetros diferentes no método pai e filho na linha " + list.line_number);
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
    if(!(n.e.type instanceof TypePack.BooleanType)){
      System.out.println("Operação de if esperando booleano, encontrado " + n.e.type.print() +" na linha "+ n.line_number);
    }
    n.s1.accept(this);
    n.s2.accept(this);
  }

  // Exp e;
  // Statement s;
  public void visit(While n) {
    if(!(n.e.type instanceof TypePack.BooleanType)){
      System.out.println("Operação de while esperando booleano, encontrado " + n.e.type.print() +" na linha "+ n.line_number);
    }
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
    if(!n.i.type.sameType(n.e.type)){
      System.out.println("Erro em atribuição, se esperava um " + n.i.type.print() + ", mas foi recebido um " + n.e.type.print() + " na linha " + n.line_number);
    }
    n.i.accept(this);
    n.e.accept(this);
  }

  // Identifier i;
  // Exp e1,e2;
  public void visit(ArrayAssign n) {
    n.i.accept(this);
    if(!(n.i.type instanceof TypePack.ArrayType)){
      System.out.println("Erro em atribuição de array, se esperava um int[], mas foi recebido um " + n.i.type.print() + " na linha " + n.line_number);
    }
    if(!(n.e2.type instanceof TypePack.IntegerType)){
      System.out.println("Erro em atribuição de array, se esperava um inteiro, mas foi recebido um " + n.e2.type.print() + " na linha " + n.line_number);
    }
    if(!(n.e1.type instanceof TypePack.IntegerType)){
      System.out.println("Erro em atribuição de array, se esperava um inteiro, mas foi recebido um " + n.e1.type.print() + " na linha " + n.line_number);
    }
    n.i.accept(this);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(And n) {
    if(!(n.e1.type instanceof TypePack.BooleanType))
      System.out.println("Operação de And esperando booleano, encontrado " + n.e1.type.print() +" na linha "+ n.line_number);
    if(!(n.e2.type instanceof TypePack.BooleanType))
      System.out.println("Operação de And esperando booleano, encontrado " + n.e2.type.print() +" na linha "+ n.line_number);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(LessThan n) {
    if(!(n.e1.type instanceof TypePack.IntegerType))
      System.out.println("Operação de < esperando inteiro, encontrado " + n.e1.type.print() +" na linha "+ n.line_number);
    if(!(n.e2.type instanceof TypePack.IntegerType))
      System.out.println("Operação de < esperando inteiro, encontrado " + n.e2.type.print() +" na linha "+ n.line_number);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(Plus n) {
    if(!(n.e1.type instanceof TypePack.IntegerType))
      System.out.println("Operação de soma esperando inteiro, encontrado " + n.e1.type.print() +" na linha "+ n.line_number);
    if(!(n.e2.type instanceof TypePack.IntegerType))
      System.out.println("Operação de soma esperando inteiro, encontrado " + n.e2.type.print() +" na linha "+ n.line_number);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(Minus n) {
    if(!(n.e1.type instanceof TypePack.IntegerType))
      System.out.println("Operação de subtração esperando inteiro, encontrado " + n.e1.type.print() +" na linha "+ n.line_number);
    if(!(n.e2.type instanceof TypePack.IntegerType))
      System.out.println("Operação de subtração esperando inteiro, encontrado " + n.e2.type.print() +" na linha "+ n.line_number);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(Times n) {
    if(!(n.e1.type instanceof TypePack.IntegerType))
      System.out.println("Operação de multiplicação esperando inteiro, encontrado " + n.e1.type.print() +" na linha "+ n.line_number);
    if(!(n.e2.type instanceof TypePack.IntegerType))
      System.out.println("Operação de multiplicação esperando inteiro, encontrado " + n.e2.type.print() +" na linha "+ n.line_number);
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e1,e2;
  public void visit(ArrayLookup n) {
    if(!(n.e1.type instanceof TypePack.ArrayType)){
      System.out.println("Array lookup esperando array, encontrado " + n.e1.type.print() +" na linha "+ n.line_number);
    }
    if(!(n.e2.type instanceof TypePack.IntegerType)){
      System.out.println("O index do array esperando inteiro, encontrado " + n.e2.type.print() +" na linha "+ n.line_number);
    }
    n.e1.accept(this);
    n.e2.accept(this);
  }

  // Exp e;
  public void visit(ArrayLength n) {
    if(!(n.e.type instanceof TypePack.ArrayType)){
      System.out.println("Operação length esperando array, encontrado " + n.e.type.print() +" na linha "+ n.line_number);
    }
    n.e.accept(this);
  }

  // Exp e;
  // Identifier i;
  // ExpList el;
  public void visit(Call n) {
    if(!(n.e.type instanceof TypePack.ClassType)){
      System.out.println("Operação de chamada esperando Objeto, encontrado " + n.e.type.print() +" na linha "+ n.line_number);
    }
    if(n.type instanceof TypePack.UndefinedType){
      System.out.println("Metodo invalido, retorno do metodo "+n.i.s+" " + n.type.print()+" "+n.e.type.print() +" na linha "+ n.line_number);
    }
    else{
      if(!(n.el.size() == ((DataArray)BuildSymbolLocalTableVisitor.localTable.get(n.e.type.representation)).get(n.i.s).param.size())){
        System.out.println("Quantidade de parâmetros diferentes no método " + n.i.s + " na linha " + n.line_number);
      }
      else{
        paramIguais(n.el,((DataArray)BuildSymbolLocalTableVisitor.localTable.get(n.e.type.representation)).get(n.i.s).param);
      }
    }
    n.e.accept(this);

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
    if(!(n.e.type instanceof TypePack.IntegerType)){
      System.out.println("Operação new Array esperando inteiro, encontrado " + n.e.type.print() +" na linha "+ n.line_number);
    }
    n.e.accept(this);
  }

  // Identifier i;
  public void visit(NewObject n){
    if(!(n.i.type instanceof TypePack.ClassType)){
      System.out.println("Operação new Object esperando Object, encontrado " + n.i.type.print() +" na linha "+ n.line_number);
    }
  }

  // Exp e;
  public void visit(Not n) {
    if(!(n.e.type instanceof TypePack.BooleanType)){
      System.out.println("Operação NOT esperando boolean, encontrado " + n.e.type.print() +" na linha "+ n.line_number);
    }
    n.e.accept(this);
  }
  void paramIguais (ExpList list, ArrayList<Data> param){
    for(int i = 0; i < param.size(); i++){
      if(!list.get(i).type.sameType(param.get(i).ttype)) {
          System.out.println("Parametro " + (i+1) + " esperava " + param.get(i).ttype.print() + ", mas recebeu " + list.get(i).type.print()+" " + list.line_number);
      }
    }
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
  public TypePack.Type VerificarClass(String s,int n){
    if(BuildSymbolGlobalTableVisitor.global.lookUp(s) == null) {
      System.out.println("Erro! classe \'" + s + "\' não declarado na linha: " + n);
      return new TypePack.UndefinedType();
    }
    return BuildSymbolGlobalTableVisitor.global.lookUp(s).type;
  }
  public boolean verificaLoop(String filho,String Pai){
      if(Pai.equals(""))return false;
      if(filho.equals(Pai)){
         return true;
      }
      else{
         if(BuildSymbolGlobalTableVisitor.global.lookUp(Pai)!=null){
           return verificaLoop(filho,BuildSymbolGlobalTableVisitor.global.lookUp(Pai).nomedoPai);
         }
      }
      return false;
  }
}

