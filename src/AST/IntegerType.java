package AST;
import AST.Visitor.Visitor;
//import TypePack.*;


public class IntegerType extends Type {
  public IntegerType(int ln) {
    super(ln);
    type = new TypePack.IntegerType();
  }
  public void accept(Visitor v) {
    v.visit(this);
  }
}
