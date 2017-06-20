package AST.Visitor;

import AST.*;
import codegen.Codegen;

import java.io.IOException;
import java.util.Map;

/**
 * Created by LCC-07 on 13/06/2017.
 */
public class CodeGenVisitor implements Visitor {
    public Codegen code;
    public Map<String,String> vtable;
    public String cname,cnamePai,mname;
    public int labelid;

    public void visit(Display n){

    }
    public void visit(Program n) {
        try {
            code = new Codegen("out.s");
            labelid=1;
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
            cname=n.i.s;
            cnamePai="";
            int tamanhoatual;
            tamanhoatual=0;

            for ( int i = 0; i < n.ml.size(); i++ ) {
                code.genLabel(n.i.s+"$"+n.ml.get(i).i.s);
                prelude();
                code.genbin("subq","$"+vtable.get(n.i.s+"$"+n.ml.get(i).i.s+"$$"),"%rsp");
                n.ml.get(i).accept(this);
                epilogue();
            }
            //create constructor
            code.genLabel(n.i.s+"$"+n.i.s);
            prelude();
            code.gen("pushq %rdi");
            code.genbin("movq", "$"+vtable.get(cname+"$$"), "%rdi");
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
        mname=n.i.s;

        for ( int i = 0; i < n.sl.size(); i++ ) {
            n.sl.get(i).accept(this);
        }
        n.e.accept(this);
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
        for(int i = 0; i < n.sl.size(); i++){
            n.sl.get(i).accept(this);
        }
    }

    @Override
    public void visit(If n) {

        try {
            n.e.accept(this);
            int temp;
            temp=labelid++;
            code.genbin("cmpq","$1","%rax");
            code.gen("je "+"ElseLabel"+String.valueOf(temp));
            code.genLabel("IfLabel" + String.valueOf(temp));
            n.s1.accept(this);
            code.gen("jmp "+"DoneLabel"+String.valueOf(temp));
            code.genLabel("ElseLabel"+String.valueOf(temp));
            n.s2.accept(this);
            code.genLabel("DoneLabel"+String.valueOf(temp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(While n) {
        try {
            int temp;
            temp=labelid++;
            code.gen("jmp TestLabel"+String.valueOf(temp));
            code.genLabel("BodyLabel"+String.valueOf(temp));
            n.s.accept(this);
            code.genLabel("TestLabel"+String.valueOf(temp));
            n.e.accept(this);
            code.genbin("cmpq","$0","%rax");
            code.gen("je "+"BodyLabel"+String.valueOf(temp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Print n) {
        n.e.accept(this);
        try {
            code.gen("pushq %rdi");
            code.genbin("movq", "%rax","%rdi");
            code.gen("call put");
            code.gen("popq %rdi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(Assign n) {
        n.e.accept(this);
        try {
            code.genbin("movq", "%rax", Offsetvar(n.i.s) + "(%rbp)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(ArrayAssign n) {

    }

    @Override
    public void visit(And n) {
        try {
            int temp=labelid++;
            n.e1.accept(this);
            code.gen("cmpq $1,%rax");
            code.gen("movq $1,%rax");
            code.gen("je EndAndLabel"+String.valueOf(temp));
            n.e2.accept(this);
            code.gen("cmpq $1,%rax");
            code.gen("movq $1,%rax");
            code.gen("je EndAndLabel"+String.valueOf(temp));
            code.gen("movq $0,%rax");
            code.genLabel("EndAndLabel"+String.valueOf(temp));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(LessThan n) {
        try {
            int temp=labelid++;
            n.e1.accept(this);
            code.gen("pushq %rax");
            n.e2.accept(this);
            code.gen("popq %rdx");
            code.gen("cmpq %rdx,%rax");
            code.gen("movq $1,%rax");
            code.gen("jle EndLessLabel"+String.valueOf(temp));
            code.gen("movq $0,%rax");
            code.genLabel("EndLessLabel"+String.valueOf(temp));

        } catch (IOException e) {
            e.printStackTrace();
        }

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
            code.gen("pushq %rdi");
            for(int i=0;i<n.el.size();i++){
                n.el.get(i).accept(this);
                code.gen("pushq %rax");
            }
            n.e.accept(this);
            code.genbin("movq", "%rax", "%rdi");
            code.genbin("movq", "(%rdi)", "%rax");
            code.genbin("lea",vtable.get(n.e.type.representation+"$"+n.i.s)+"(%rax)","%rax");
            code.gen("movq %rax,%rcx");
            code.gen("call *(%rax)");
            for(int i=0;i<n.el.size();i++){
                code.gen("popq %rdx");
            }
            code.gen("popq %rdi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(IntegerLiteral n) {
        try {
            code.genbin("movq", "$"+String.valueOf(n.i),"%rax");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(True n) {
        try {
            code.genbin("movq","$0","%rax");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(False n) {
        try {
            code.genbin("movq","$1","%rax");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(IdentifierExp n) {
            try {
                    code.genbin("movq", Offsetvar(n.s) + "(%rbp)", "%rax");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            try {
                code.genbin("movq", Offsetvar(n.s) +"(%rbp)","%rax");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void setVtable(Map<String,String> vtable){
        this.vtable = vtable;
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
    public String Offsetvar(String s){
        if(vtable.get(cname+"$"+mname+"$"+s)!=null){
            return vtable.get(cname+"$"+mname+"$"+s);
        }
        else if(vtable.get(cname+"$$"+s)!=null){
            return vtable.get(cname+"$$"+s);
        }
        return "0";
    }

}

