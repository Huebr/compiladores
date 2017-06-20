import AST.Program;
import AST.Visitor.*;
import Parser.parser;
import Scanner.scanner;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by pedro-jorge on 19/06/2017.
 */
public class TestCodegen {
    public static void main(String [] args) {
        try {
            // create a scanner on the input file
            BuildSymbolGlobalTableVisitor visitorpass1 = new BuildSymbolGlobalTableVisitor();
            BuildSymbolLocalTableVisitor visitorpass2 = new BuildSymbolLocalTableVisitor();
            BuildThirdParseVisitor visitorpass4 = new BuildThirdParseVisitor();
            ComputeOffsetVisitor visitoroffeset = new ComputeOffsetVisitor();
            CodeGenVisitor visitorgen = new CodeGenVisitor();
            TypeVisitor visitorpass3 = new TypeVisitor();
            scanner s = new scanner(new BufferedReader(new InputStreamReader(System.in)));
            parser p = new parser(s);
            Symbol root;
            // replace p.parse() with p.debug_parse() in next line to see trace of
            // parser shift/reduce actions during parse
            root = p.parse();
            Program pro = (Program) root.value;
            pro.accept(visitorpass1);
            pro.accept(visitorpass2);
            pro.accept(visitorpass3);
            pro.accept(visitorpass4);
            pro.accept(visitoroffeset);
            visitorgen.setVtable(visitoroffeset.getvtable());
            pro.accept(visitorgen);
            System.out.print("\n");
            System.out.print("\nGlobal localTable completed\n\n");
            visitorpass1.print();
            System.out.print("\n");
            visitorpass2.print();
        } catch (Exception e) {
            // yuck: some kind of error in the compiler implementation
            // that we're not expecting (a bug!)
            System.err.println("Unexpected internal compiler error: " +
                    e.toString());
            // print out a stack dump
            e.printStackTrace();
        }
    }
}
