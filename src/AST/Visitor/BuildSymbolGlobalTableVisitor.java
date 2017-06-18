package AST.Visitor;

import AST.*;
import SymbolTables.ClassSymbolTable;
import SymbolTables.GlobalSymbolTable;
import TypePack.ClassType;
import TypePack.VoidType;

import java.util.Map;
// Sample print visitor from MiniJava web site with small modifications for UW CSE.
// HP 10/11

public class BuildSymbolGlobalTableVisitor implements SymbolGlobalTableVisitor {

    public static GlobalSymbolTable global  = new GlobalSymbolTable();

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n) {
        n.m.accept(this);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.get(i).accept(this);
        }
    }

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n) {
        ClassSymbolTable table = new ClassSymbolTable();
        table.methodList.put("main", new VoidType());
        table.nomedoPai="";
        table.nomedaClasse = n.i1.s;
        table.type = new ClassType(n.i1.s,"");
        global.enter(n.i1.s, table);
        //n.i2.accept(this);
        //n.s.accept(this);
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n) {
        ClassSymbolTable table = new ClassSymbolTable();
        table.nomedaClasse = n.i.s;
        table.nomedoPai = "";
        table.type = new ClassType(n.i.s, "");
        for (int i = 0; i < n.vl.size(); i++) {
            if(n.vl.get(i).t instanceof IntArrayType){
                table.putVar(n.vl.get(i).i.s, new TypePack.ArrayType());
            }
            else if(n.vl.get(i).t instanceof BooleanType){
                table.putVar(n.vl.get(i).i.s, new TypePack.BooleanType());
            }
            else if(n.vl.get(i).t instanceof IntegerType){
                table.putVar(n.vl.get(i).i.s, new TypePack.IntegerType());
            }
            else if(n.vl.get(i).t instanceof IdentifierType){
                table.putVar(n.vl.get(i).i.s, new TypePack.ClassType(((IdentifierType) n.vl.get(i).t).s,""));
            }
        }
        for (int i = 0; i < n.ml.size(); i++) {
            if(n.ml.get(i).t instanceof IntArrayType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.ArrayType());
            }
            else if(n.ml.get(i).t instanceof BooleanType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.BooleanType());
            }
            else if(n.ml.get(i).t instanceof IntegerType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.IntegerType());
            }
            else if(n.ml.get(i).t instanceof IdentifierType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.ClassType(((IdentifierType) n.ml.get(i).t).s,""));
            }
        }
        global.enter(n.i.s, table);
    }

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n) {
        ClassSymbolTable table = new ClassSymbolTable();
        table.nomedaClasse = n.i.s;
        table.nomedoPai = n.j.s;
        table.type = new ClassType(n.i.s, n.j.s);
        for (int i = 0; i < n.vl.size(); i++) {
            if(n.vl.get(i).t instanceof IntArrayType){
                table.putVar(n.vl.get(i).i.s, new TypePack.ArrayType());
            }
            else if(n.vl.get(i).t instanceof BooleanType){
                table.putVar(n.vl.get(i).i.s, new TypePack.BooleanType());
            }
            else if(n.vl.get(i).t instanceof IntegerType){
                table.putVar(n.vl.get(i).i.s, new TypePack.IntegerType());
            }
            else if(n.vl.get(i).t instanceof IdentifierType){
                table.putVar(n.vl.get(i).i.s, new TypePack.ClassType(((IdentifierType) n.vl.get(i).t).s,n.j.s));
            }
        }
        for (int i = 0; i < n.ml.size(); i++) {
            if(n.ml.get(i).t instanceof IntArrayType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.ArrayType());
            }
            else if(n.ml.get(i).t instanceof BooleanType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.BooleanType());
            }
            else if(n.ml.get(i).t instanceof IntegerType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.IntegerType());
            }
            else if(n.ml.get(i).t instanceof IdentifierType){
                table.methodList.put(n.ml.get(i).i.s, new TypePack.ClassType(((IdentifierType) n.ml.get(i).t).s,n.j.s));
            }
        }
        global.enter(n.i.s, table);
    }
    public void print(){
        for(Object s:global.myTable.keySet()){
            System.out.print((String)s+" : ");
            System.out.println(global.lookUp((String)s).nomedoPai);
            ClassSymbolTable c = global.lookUp((String)s);
            Map hm = c.methodList;
            for (Object s1: hm.keySet()) {
                TypePack.Type t = (TypePack.Type)hm.get((String)s1);
                System.out.print( t.print() +" " + s1 +" ;");
            }
            if(!hm.keySet().isEmpty())
                System.out.println();
            hm = c.varList;
            for (Object s1: hm.keySet()) {
                TypePack.Type t = (TypePack.Type)hm.get((String)s1);
                System.out.print( t.print() +" " + s1 +" ;");
            }
            if(!hm.keySet().isEmpty())
                System.out.println();
        }
    }

}
