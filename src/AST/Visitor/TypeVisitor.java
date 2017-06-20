package AST.Visitor;

import AST.*;
import SymbolTables.ClassSymbolTable;
import TypePack.*;
import TypePack.Type;

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
        mname = "";
        for(int i = 0; i < n.vl.size(); i++){
            n.vl.get(i).accept(this);
        }
        for(int i = 0; i < n.ml.size(); i++){
            n.ml.get(i).accept(this);
        }
    }

    @Override
    public void visit(ClassDeclExtends n) {
        cname = n.i.s;
        cnamePai = n.j.s;
        mname = "";
        n.type = VerificarClass(new ClassType(n.j.s,""),n.line_number);
        for(int i = 0; i < n.vl.size(); i++){
            n.vl.get(i).accept(this);
        }
        for(int i = 0; i < n.ml.size(); i++){
            n.ml.get(i).accept(this);
        }
    }

    @Override
    public void visit(VarDecl n) {
        n.i.accept(this);
        n.i.accept(this);
        //System.out.println(n.i.s+""+n.i.type.print());
    }

    @Override
    public void visit(MethodDecl n) {
        mname = n.i.s;
        for(int i = 0; i < n.fl.size(); i++){
            n.fl.get(i).accept(this);
        }
        for(int i = 0; i < n.vl.size(); i++){
            n.vl.get(i).accept(this);
        }
        for(int i = 0; i < n.sl.size(); i++){
            n.sl.get(i).accept(this);
        }
        n.e.accept(this);
    }

    @Override
    public void visit(Formal n) {
        n.i.accept(this);
        n.i.accept(this);
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
        n.type=n.i.type;
        //TODO Deu merda.
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
        //TODO verifica var ta verificando se o nome ou se o tipo de argumento é um parametro.
        n.e.accept(this);
        n.e.accept(this);
        n.i.type = new MethodType(n.e.type);
        n.i.accept(this);
        for(int i = 0; i < n.el.size(); i++){
            n.el.get(i).accept(this);
        }
        n.type = ((MethodType) n.i.type).retorno;
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
            ((MethodType)n.type).retorno = VerificarMethod(((MethodType)n.type).classe,n.s);
        }
        else if(n.type instanceof ClassType){
            n.type = VerificarClass(n.type,n.line_number);
        } else{
            n.type = VerificarVar(n.s,n.line_number);
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
        n.i.type = new ClassType(n.i.s,"");
        n.i.accept(this);
        n.type=n.i.type;
    }

    @Override
    public void visit(Not n) {
        n.type = new TypePack.BooleanType();
        n.e.accept(this);
    }

    @Override
    public void visit(Identifier n) {
        if(n.type instanceof MethodType){
            ((MethodType)n.type).retorno = VerificarMethod(((MethodType)n.type).classe,n.s);
        }
        else if(n.type instanceof ClassType){
            n.type = VerificarClass(n.type,n.line_number);
        } else{
            n.type = VerificarVar(n.s,n.line_number);
        }
    }

    private TypePack.Type VerificarMethod(TypePack.Type tipo,String s) {
        if( tipo instanceof ClassType){
            if(BuildSymbolGlobalTableVisitor.global.lookUp(tipo.representation) == null){
                return new TypePack.UndefinedType();
            }
            else if(BuildSymbolGlobalTableVisitor.global.lookUp(tipo.representation).methodList.containsKey(s)){
                return (TypePack.Type) BuildSymbolGlobalTableVisitor.global.lookUp(tipo.representation).methodList.get(s);
            }
            else if(!(((ClassType) tipo).baseRepresentation.equals(""))){
                if(BuildSymbolGlobalTableVisitor.global.lookUp(((ClassType) tipo).baseRepresentation).methodList.containsKey(s)){
                    return (TypePack.Type) BuildSymbolGlobalTableVisitor.global.lookUp(((ClassType) tipo).baseRepresentation).methodList.get(s);
                }
                else return new TypePack.UndefinedType();
            }
            else return new TypePack.UndefinedType();
        }
        else return new TypePack.UndefinedType();
    }

    public TypePack.Type VerificarClass(TypePack.Type s,int n){
        if(BuildSymbolGlobalTableVisitor.global.lookUp(s.representation) == null) {
            System.out.println("Erro! classe \'" + s.representation + "\' não declarado na linha: " + n);
            return new TypePack.UndefinedType();
        }
        return BuildSymbolGlobalTableVisitor.global.lookUp(s.representation).type;
    }
    //TODO, caso a variavel esteja na tabela, deve-se passar o tipo para s. S deve ser o nódulo.
    //haha eu gosto de hamburger
    public TypePack.Type VerificarVar(String n , int ln) {
        if(mname.equals("main")){
            System.out.println("Não pode haver declaracao na main " + n + " " + ln);
        }
        TypePack.Type meutipo = TipoDaVariavel(n);
        if(meutipo instanceof UndefinedType)
            System.out.println("Erro! Variaver \'"+ n + "\' não declarada." + mname+ " " + cname + " " + ln);
        return meutipo;
    }

    public TypePack.Type TipoDaVariavel(String n){
        if(mname.equals("main")){
            return new TypePack.UndefinedType();
        }
        else{
            DataArray dataArray = (DataArray) BuildSymbolLocalTableVisitor.localTable.get(cname);
            ClassSymbolTable table = BuildSymbolGlobalTableVisitor.global.lookUp(cname);
            if(cnamePai.equals("")){
                if(dataArray.contem(mname,n)){
                    return dataArray.get(mname).get(n).ttype;
                }
                else if(table.varList.containsKey(n)){
                    return (Type) table.varList.get(n);
                }
                else {
                    return new TypePack.UndefinedType();
                }
            }
            else{
                if (dataArray.contem(mname, n)) {
                    return dataArray.get(mname).get(n).ttype;
                } else if (table.varList.containsKey(n)) {
                    return (Type) table.varList.get(n);
                }
                else if(BuildSymbolGlobalTableVisitor.global.lookUp(table.nomedoPai)!=null) {
                    ClassSymbolTable tablePai = BuildSymbolGlobalTableVisitor.global.lookUp(table.nomedoPai);
                    if (tablePai.varList.containsKey(n)) {
                        return (Type) tablePai.varList.get(n);
                    }
                }
                return new TypePack.UndefinedType();
            }
        }
    }
}
