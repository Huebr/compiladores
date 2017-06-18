package AST;
import AST.Visitor.Visitor;

public class IdentifierType extends Type {
  public String s;

  public IdentifierType(String as, int ln) {
    super(ln);
    s=as;
    type = new TypePack.ClassType(as,"");
  }

  public void accept(Visitor v) {
    v.visit(this);
  }
}
