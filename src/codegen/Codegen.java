package codegen;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LCC-07 on 13/06/2017.
 */
public class Codegen {
    FileOutputStream output;
    public Codegen(String file) throws IOException {
            output =new FileOutputStream(file);
            gen(".text\n\t\t.globl asm_main\nasm_main:");
    }
    public void gen(String text) throws IOException {
            output.write(("\t\t"+text+"\n").getBytes());
    }
    public void genbin(String op,String src,String dst) throws IOException {
        String text = "\t\t"+op +" "+src+","+dst+"\n";
        output.write(text.getBytes());
    }
    public void genLabel(String label) throws IOException {
        String text = label+":\n";
        output.write(text.getBytes());
    }
}
