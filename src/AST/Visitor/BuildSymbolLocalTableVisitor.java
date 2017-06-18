package AST.Visitor;

import AST.*;

import java.util.HashMap;
import java.util.Map;
// Sample print visitor from MiniJava web site with small modifications for UW CSE.
// HP 10/11

public class BuildSymbolLocalTableVisitor implements SymbolLocalTableVisitor {

    public static Map localTable = new HashMap<String,DataArray>();
    private String cname = "";
    private Data method = null;
    /*public BuildSymbolLocalTableVisitor(GlobalSymbolTable newGlobal){
        global = newGlobal;
    }*/

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        //n.m.accept(this);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.get(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        for (int i = 0; i < n.ml.size(); i++) {
            cname = n.i.s;
            n.ml.get(i).accept(this);
        }

    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        for (int i = 0; i < n.ml.size(); i++) {
            cname = n.i.s;
            n.ml.get(i).accept(this);
        }
    }

    // TypePack t;
    // Identifier i;
    public void visit(VarDecl n) {
    }

    // TypePack t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n) {
        Data d = new Data();
        d.name = n.i.s;
        if(n.t instanceof IntArrayType){
            d.ttype = new TypePack.ArrayType();
        }
        else if(n.t instanceof BooleanType){
            d.ttype = new TypePack.BooleanType();
        }
        else if(n.t instanceof IntegerType){
            d.ttype = new TypePack.IntegerType();
        }
        else if(n.t instanceof IdentifierType){
            d.ttype = new TypePack.ClassType(((IdentifierType) n.t).s, "");
        }
        for ( int i = 0; i < n.fl.size(); i++ ) {
            Data toAdd = new Data();
            toAdd.name = n.fl.get(i).i.s;
            if(n.fl.get(i).t instanceof IntArrayType){
                toAdd.ttype = new TypePack.ArrayType();
            }
            else if(n.fl.get(i).t instanceof BooleanType){
                toAdd.ttype = new TypePack.BooleanType();
            }
            else if(n.fl.get(i).t instanceof IntegerType){
                toAdd.ttype = new TypePack.IntegerType();
            }
            else if(n.fl.get(i).t instanceof IdentifierType){
                toAdd.ttype = new TypePack.ClassType(((IdentifierType) n.fl.get(i).t).s, "");
            }
            d.add(toAdd, d.param,cname);
        }
        for ( int i = 0; i < n.vl.size(); i++ ) {
            Data toAdd = new Data();
            toAdd.name = n.vl.get(i).i.s;
            if( n.vl.get(i).t instanceof IntArrayType){
                toAdd.ttype = new TypePack.ArrayType();
            }
            else if( n.vl.get(i).t instanceof BooleanType){
                toAdd.ttype = new TypePack.BooleanType();
            }
            else if( n.vl.get(i).t instanceof IntegerType){
                toAdd.ttype = new TypePack.IntegerType();
            }
            else if( n.vl.get(i).t instanceof IdentifierType){
                toAdd.ttype = new TypePack.ClassType(((IdentifierType)  n.vl.get(i).t).s, "");
            }
            d.add(toAdd, d.scope,cname);
        }
        for ( int i = 0; i < n.sl.size(); i++ ) {
            method = d;
            //n.sl.get(i).accept(this);
            d = method;
            method = null;
        }
        if(localTable.isEmpty() || !localTable.containsKey(cname)){
            DataArray data = new DataArray();
            data.array.add(d);
            localTable.put(cname, data);
        }else{
            DataArray tableArray = ((DataArray) localTable.get(cname));
            tableArray.adicionar(d, cname);
        }

        cname = "";
    }

    // TypePack t;
    // Identifier i;
    public void visit(Formal n) {

    }

    public void print(){
        for(Object s: localTable.keySet()){
            System.out.println((String)s+" ::: ");
          ((DataArray) localTable.get((String)s)).paraString();
        }
    }



}
