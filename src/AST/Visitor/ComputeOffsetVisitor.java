package AST.Visitor;

import AST.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pedro on 20/06/2017.
 */
public class ComputeOffsetVisitor implements Visitor {
    public Map<String,String> vtable;
    public String cname,cnamePai,mname;


    public Map<String,String> getvtable(){
        return this.vtable;
    }

    public void visit(Display n){

    }
    public void visit(Program n) {
            vtable = new HashMap<String, String>();
            for ( int i = 0; i < n.cl.size(); i++ ) {
                n.cl.get(i).accept(this);
            }
    }

    public void visit(MainClass n) {

    }

    public void visit(ClassDeclSimple n) {
            cname=n.i.s;
            cnamePai="";
            int tamanhoatual;
            tamanhoatual=0;
            for(int i =0;i<n.vl.size();i++){
                if(n.vl.get(i).i.type instanceof TypePack.IntegerType) {
                    tamanhoatual+=8;
                    vtable.put(n.i.s+"$$"+n.vl.get(i).i.s,String.valueOf(tamanhoatual));
                }
            }
            for ( int i = 0; i < n.ml.size(); i++ ) {
                vtable.put(n.i.s+"$"+n.ml.get(i).i.s,String.valueOf(8*(i+1)));
                n.ml.get(i).accept(this);
            }
            vtable.put(n.i.s+"$$",String.valueOf(tamanhoatual+8));
    }

    public void visit(ClassDeclExtends n) {

    }


    public void visit(VarDecl n) {

    }


    public void visit(MethodDecl n) {
        int tamanhoatual;
        tamanhoatual=8;
        mname=n.i.s;
        for(int i =0;i<n.fl.size();i++){
            if(n.fl.get(i).i.type instanceof TypePack.IntegerType) {
                tamanhoatual+=8;
                vtable.put(cname+"$"+mname+"$"+n.fl.get(i).i.s,String.valueOf(tamanhoatual));
            }
        }
        tamanhoatual=0;
        for(int i =0;i<n.vl.size();i++){
            if(n.vl.get(i).i.type instanceof TypePack.IntegerType) {
                tamanhoatual-=8;
                vtable.put(cname+"$"+mname+"$"+n.vl.get(i).i.s,String.valueOf(tamanhoatual));
            }
        }
        vtable.put(cname + "$" + mname + "$$", String.valueOf((-tamanhoatual)));
    }

    @Override
    public void visit(Formal n) {

    }

    @Override
    public void visit(IntArrayType n) {

    }

    @Override
    public void visit(BooleanType n) {

    }

    @Override
    public void visit(IntegerType n) {

    }

    @Override
    public void visit(IdentifierType n) {

    }

    @Override
    public void visit(Block n) {

    }

    @Override
    public void visit(If n) {

    }

    @Override
    public void visit(While n) {

    }

    @Override
    public void visit(Print n) {

    }

    @Override
    public void visit(Assign n) {

    }

    @Override
    public void visit(ArrayAssign n) {

    }

    @Override
    public void visit(And n) {

    }

    @Override
    public void visit(LessThan n) {

    }

    @Override
    public void visit(Plus n) {

    }

    @Override
    public void visit(Minus n) {

    }

    @Override
    public void visit(Times n) {

    }

    @Override
    public void visit(ArrayLookup n) {

    }

    @Override
    public void visit(ArrayLength n) {

    }

    @Override
    public void visit(Call n) {

    }

    @Override
    public void visit(IntegerLiteral n) {

    }

    @Override
    public void visit(True n) {

    }

    @Override
    public void visit(False n) {

    }

    @Override
    public void visit(IdentifierExp n) {

    }

    @Override
    public void visit(This n) {

    }

    @Override
    public void visit(NewArray n) {

    }

    @Override
    public void visit(NewObject n) {

    }

    @Override
    public void visit(Not n) {

    }

    @Override
    public void visit(Identifier n) {

    }
}
