package AST.Visitor;

import AST.*;

public interface SymbolLocalTableVisitor {
    public void visit(Program n);
    public void visit(MainClass n);
    public void visit(ClassDeclSimple n);
    public void visit(ClassDeclExtends n);
    public void visit(VarDecl n);
    public void visit(MethodDecl n);
    public void visit(Formal n);
}