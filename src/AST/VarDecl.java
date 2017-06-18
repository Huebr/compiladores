package AST;
import AST.Visitor.Visitor;

public class VarDecl extends ASTNode {
  public Type t;
  public Identifier i;
  
  public VarDecl(Type at, Identifier ai, int ln) {
    super(ln);
    t=at; i=ai;
    //t.type = i.s;
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
