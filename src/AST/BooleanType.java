package AST;
import AST.Visitor.Visitor;

public class BooleanType extends Type {
  public BooleanType(int ln) {
    super(ln);
    type = new TypePack.BooleanType();
  }
  public void accept(Visitor v) {
    v.visit(this);
  }
}
