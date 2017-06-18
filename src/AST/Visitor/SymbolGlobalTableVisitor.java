package AST.Visitor;

import AST.*;

public interface SymbolGlobalTableVisitor {
    // Display added for toy example language.  Not used in MiniJava AST
    public void visit(Program n);
    public void visit(MainClass n);
    public void visit(ClassDeclSimple n);
    public void visit(ClassDeclExtends n);
}
