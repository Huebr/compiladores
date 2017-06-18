package AST;
import AST.Visitor.*;

public class ClassDeclSimple extends ClassDecl {
  public Identifier i;
  public VarDeclList vl;  
  public MethodDeclList ml;
 
  public ClassDeclSimple(Identifier ai, VarDeclList avl, MethodDeclList aml, int ln) {
    super(ln);
    i=ai; vl=avl; ml=aml;
    i.type = new TypePack.ClassType(i.s, "");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  public void accept(SymbolGlobalTableVisitor v) {v.visit(this);}
  public void accept(SymbolLocalTableVisitor v) {v.visit(this);}

}
