package AST.Visitor;

import AST.*;
import SymbolTables.ClassSymbolTable;
import TypePack.*;

/**
 * Created by Clóvis on 16/06/2017.
 */
public class TypeVisitor implements Visitor {
    public String cnamePai;
    public String cname;
    public String mname;

    @Override
    public void visit(Display n) {
    }

    @Override
    public void visit(Program n) {
        n.m.accept(this);
        for(int i = 0; i < n.cl.size(); i ++){
            n.cl.get(i).accept(this);
        }
    }

    @Override
    public void visit(MainClass n) {
        cname = n.i1.s;
        mname = "main";
        cnamePai = "";
        n.s.accept(this);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        cname = n.i.s;
        cnamePai = "";
        for(int i = 0; i < n.ml.size(); i++){
            n.ml.get(i).accept(this);
        }
    }

    @Override
    public void visit(ClassDeclExtends n) {
        cname = n.i.s;
        cnamePai = n.j.s;
        for(int i = 0; i < n.ml.size(); i++){
            n.ml.get(i).accept(this);
        }
    }

    @Override
    public void visit(VarDecl n) {

    }

    @Override
    public void visit(MethodDecl n) {
        mname = n.i.s;
        for(int i = 0; i < n.sl.size(); i++){
            n.sl.get(i).accept(this);
        }
    }

    @Override
    public void visit(Formal n) {

    }

    @Override
    public void visit(IntArrayType n) {

    }

    @Override
    public void visit(AST.BooleanType n) {

    }

    @Override
    public void visit(AST.IntegerType n) {

    }

    @Override
    public void visit(IdentifierType n) {

    }

    @Override // Statment
    public void visit(Block n) {
        for(int i = 0; i < n.sl.size(); i++){
            n.sl.get(i).accept(this);
        }
    }

    @Override // Statment
    public void visit(If n) {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    }

    @Override // Statment
    public void visit(While n) {
        n.e.accept(this);
        n.s.accept(this);
    }

    @Override // Statment
    public void visit(Print n) {
        n.e.accept(this);
    }

    @Override // Statment
    public void visit(Assign n) {
        n.i.accept(this);
        n.e.accept(this);
        //TODO Pode dar merda.
    }

    @Override // Statment
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        n.e1.accept(this);
        n.e2.accept(this);
        //TODO Pode dar merda[2].
    }

    @Override // Exp
    public void visit(And n) {
        n.type = new TypePack.BooleanType();
        n.e1.accept(this);
        n.e2.accept(this);
    }
    @Override // Exp
    public void visit(LessThan n) {
        n.type = new TypePack.BooleanType();
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override // Exp
    public void visit(Plus n) {
        n.type = new TypePack.IntegerType();
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override // Exp
    public void visit(Minus n) {
        n.type = new TypePack.IntegerType();
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override // Exp
    public void visit(Times n) {
        n.type = new TypePack.IntegerType();
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override // Exp
    public void visit(ArrayLookup n) {
        n.type = new TypePack.IntegerType();

        n.e1.accept(this);
        n.e2.accept(this);
        //TODO pode dar merda[3].
    }

    @Override// Exp
    public void visit(ArrayLength n) {
        n.type = new TypePack.IntegerType();
        n.e.accept(this);
    }

    @Override//Exp
    public void visit(Call n) {
        n.i.type = new MethodType();
        n.i.accept(this);
        n.e.accept(this);
        for(int i = 0; i < n.el.size(); i++){
            n.el.get(i).accept(this);
        }
        n.type = n.i.type;
    }

    @Override
    public void visit(IntegerLiteral n) {
        n.type = new TypePack.IntegerType();
    }

    @Override
    public void visit(True n) {
        n.type = new TypePack.BooleanType();
    }

    @Override
    public void visit(False n) {
        n.type = new TypePack.BooleanType();
    }

    @Override
    public void visit(IdentifierExp n) {
        if(n.type instanceof MethodType){
        }
        else if(n.type instanceof ClassType){
            VerificarClass(n.s,n.line_number);
        } else{
            VerificarVar(n.s,n.line_number);
        }
    }

    @Override
    public void visit(This n) {
        n.type = new ClassType(cname,cnamePai);
    }

    @Override
    public void visit(NewArray n) {
        n.type = new ArrayType();
        n.e.accept(this);
    }

    @Override
    public void visit(NewObject n) {
        n.type = new ClassType(n.i.s, "");
        n.i.type = new ClassType(n.i.s,"");
        n.i.accept(this);
    }

    @Override
    public void visit(Not n) {
        n.type = new TypePack.BooleanType();
        n.e.accept(this);
    }

    @Override
    public void visit(Identifier n) {
        if(n.type instanceof MethodType){
        }
        else if(n.type instanceof ClassType){
            VerificarClass(n.s,n.line_number);
        } else{
            VerificarVar(n.s,n.line_number);
        }
    }

    private void VerificarMethod(String s,int n) {
        ClassSymbolTable ta = BuildSymbolGlobalTableVisitor.global.lookUp(cname);
        ClassSymbolTable tb = new ClassSymbolTable();
        if(BuildSymbolGlobalTableVisitor.global.lookUp(ta.nomedoPai)!=null){
            tb = BuildSymbolGlobalTableVisitor.global.lookUp(ta.nomedoPai);
        }
        if(!(ta.methodList.containsKey(s)||tb.methodList.containsKey(s)))
            System.out.println("Erro! metodo  \'"+s +"\' não declarado na linha: "+n + cname);;
    }

    public void VerificarClass(String s,int n){
        if(BuildSymbolGlobalTableVisitor.global.lookUp(s) == null)
            System.out.println("Erro! classe \'"+s +"\' não declarado na linha: "+n);
    }
    //TODO, caso a variavel esteja na tabela, deve-se passar o tipo para s. S deve ser o nódulo.
    public void VerificarVar(String n , int ln) {
        //ArrayAssign n = ;
        if(mname == "main"){
            System.out.println("Não pode haver declaracao na main " + n + " " + ln);
        } else{
            DataArray dataArray = (DataArray) BuildSymbolLocalTableVisitor.localTable.get(cname);
            ClassSymbolTable table = BuildSymbolGlobalTableVisitor.global.lookUp(cname);
            if(cnamePai.equals("")){
                if(!(table.varList.containsKey(n) || dataArray.contem(mname,n)))
                    System.out.println("Erro! Variaver \'"+ n + "\' não declarada." + mname+ " " + cname + " " + ln);
            }
            else{
                ClassSymbolTable tablePai = BuildSymbolGlobalTableVisitor.global.lookUp(table.nomedoPai);
                if(!(table.varList.containsKey(n) || dataArray.contem(mname,n)|| tablePai.varList.containsKey(n)))
                    System.out.println("Erro! Variaver \'"+ n + "\' não declarada." + mname + " " + cname+ " " + ln);
            }

        }
    }
}
