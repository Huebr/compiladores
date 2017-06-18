package AST;
import AST.Visitor.Visitor;
import TypePack.ArrayType;

public class IntArrayType extends Type {
  public IntArrayType(int ln) {
    super(ln);
    type = new TypePack.ArrayType();
    type.representation = ArrayType.typeArray;
  }
  public void accept(Visitor v) {
    v.visit(this);
  }
}
