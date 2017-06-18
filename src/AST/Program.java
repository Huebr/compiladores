package AST;
import AST.Visitor.*;

public class Program extends ASTNode {
  public MainClass m;
  public ClassDeclList cl;

  public Program(MainClass am, ClassDeclList acl, int ln) {
    super(ln);
    m=am; cl=acl; 
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
  public void accept(SymbolGlobalTableVisitor v) {v.visit(this);}
  public void accept(SymbolLocalTableVisitor v) {v.visit(this);}
}
