package AST;
import AST.Visitor.*;

public class ClassDeclExtends extends ClassDecl {
  public Identifier i;
  public Identifier j;
  public VarDeclList vl;  
  public MethodDeclList ml;
 
  public ClassDeclExtends(Identifier ai, Identifier aj, 
                  VarDeclList avl, MethodDeclList aml, int ln) {
    super(ln);
    i=ai; j=aj; vl=avl; ml=aml;
    i.type = new TypePack.ClassType(i.s,j.s);
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  public void accept(SymbolGlobalTableVisitor v) {v.visit(this);}
  public void accept(SymbolLocalTableVisitor v) {v.visit(this);}
}
