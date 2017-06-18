package AST;
import AST.Visitor.*;


public abstract class ClassDecl extends ASTNode{
  public ClassDecl(int ln) {
    super(ln);

  }
  public abstract void accept(Visitor v);
  public abstract void accept(SymbolGlobalTableVisitor v);
  public abstract void accept(SymbolLocalTableVisitor v);
}
