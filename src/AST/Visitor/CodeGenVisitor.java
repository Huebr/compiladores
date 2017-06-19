package AST.Visitor;

import AST.*;
import codegen.Codegen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LCC-07 on 13/06/2017.
 */
public class CodeGenVisitor implements Visitor {
    public Codegen code;
    public Map<String,String> vtable;


    public void visit(Display n){

    }
    public void visit(Program n) {
        try {
            code = new Codegen("out.s");
            vtable = new HashMap<String, String>();
            prelude();
            n.m.accept(this);
            epilogue();
            code.gen(".data");
            code.gen(n.m.i1.s+"$$:"+" .quad 0");
            for ( int i = 0; i < n.cl.size(); i++ ) {
                n.cl.get(i).accept(this);
            }
        } catch (IOException e) {


        }
    }

    public void visit(MainClass n) {
        n.s.accept(this);
    }

    public void visit(ClassDeclSimple n) {
        try {
            //create constructor
            int tamanhoatual;
            tamanhoatual=0;
            for(int i =0;i<n.vl.size();i++){
                if(n.vl.get(i).i.type instanceof TypePack.IntegerType) {
                    tamanhoatual+=8;
                    vtable.put(n.i.s+"$"+n.vl.get(i).i.s,String.valueOf(tamanhoatual));
                }
            }
            for ( int i = 0; i < n.ml.size(); i++ ) {
                code.genLabel(n.i.s+"$"+n.ml.get(i).i.s);
                vtable.put(n.i.s+"$"+n.ml.get(i).i.s,String.valueOf(8*(i+1)));
                prelude();
                n.ml.get(i).accept(this);
                epilogue();
            }
            //create constructor
            code.genLabel(n.i.s+"$"+n.i.s);
            prelude();
            code.gen("pushq %rdi");
            code.genbin("movq", String.valueOf(tamanhoatual+8), "%rdi");
            code.gen("call mjcalloc");
            code.gen("popq %rdi");
            code.genbin("leaq ", n.i.s + "$$", "%rdx");
            code.genbin("movq","%rdx","(%rax)");
            epilogue();

            code.gen(".data");
            code.gen(n.i.s+"$$:"+" .quad 0");
            for ( int i = 0; i < n.ml.size(); i++ ) {
                code.gen(".quad "+n.i.s+"$"+n.ml.get(i).i.s);
            }
            code.gen(".quad "+n.i.s+"$"+n.i.s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void visit(ClassDeclExtends n) {

    }


    public void visit(VarDecl n) {

    }


    public void visit(MethodDecl n) {
            for ( int i = 0; i < n.sl.size(); i++ ) {
                n.sl.get(i).accept(this);
            }
    }


    public void visit(Formal n) {

    }


    public void visit(IntArrayType n) {

    }


    public void visit(BooleanType n) {

    }

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
        n.e.accept(this);
        try {
            code.genbin("movq", "%rax","%rdi");
            code.gen("put");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            n.e1.accept(this);
            code.gen("pushq %rax");
            n.e2.accept(this);
            code.gen("popq %rdx");
            code.genbin("addq","%rdx","%rax");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void visit(Minus n) {
        try {
            n.e1.accept(this);
            code.gen("pushq %rax");
            n.e2.accept(this);
            code.gen("popq %rdx");
            code.genbin("subq","%rdx","%rax");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Times n) {
        try {
            n.e1.accept(this);
            code.gen("pushq %rax");
            n.e2.accept(this);
            code.gen("popq %rdx");
            code.genbin("imulq","%rdx","%rax");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(ArrayLookup n) {

    }

    @Override
    public void visit(ArrayLength n) {

    }

    @Override
    public void visit(Call n) {
        try {
            n.e.accept(this);
            code.gen("pushq %rdi");
            code.genbin("movq", "%rax", "%rdi");
            code.genbin("addq",String.valueOf(8),"%rax");
            code.gen("call *(%rax)");
            code.gen("popq %rdi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(IntegerLiteral n) {
        try {
            code.genbin("movq", String.valueOf(n.i),"%rax");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try {
            code.gen("call "+n.i.s+"$"+n.i.s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Not n) {

    }

    @Override
    public void visit(Identifier n) {

    }
    public void prelude() throws IOException {
        code.gen("pushq %rbp");
        code.gen("movq  %rsp,%rbp");
    }
    public void epilogue() throws IOException {
        code.gen("movq  %rbp,%rsp");
        code.gen("popq  %rbp");
        code.gen("ret");
    }

}
