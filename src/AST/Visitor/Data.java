package AST.Visitor;

import java.util.ArrayList;

/**
 * Created by Clóvis on 15/06/2017.
 */
public class Data{
    public TypePack.Type ttype;
    public String name;
    public ArrayList<Data> param;
    public ArrayList<Data> scope;

    public Data(){
        param = new ArrayList<Data>();
        scope = new ArrayList<Data>();
    }

    public boolean contains(Data d){
        for(Data a : scope){
            //System.out.println("\n" +d.name +  "===" + a.name +"||" +(a.named.name));
            if(a.name.equals(d.name))
                return true;
        }
        for(Data a : param){
            if(a.name.equals(d.name))
                return true;
        }
        return false;
    }

    public void add(Data toAdd, ArrayList<Data> array, String cname){
        if(!contains(toAdd))
            array.add(toAdd);
        else
            System.out.println("Variavel \'"+toAdd.name +"\' jah foi declarada!" +
                    " No metodo \'" + name + "\' na classe " + cname + ".");
    }

    public void print(){
        if(ttype != null) {

            System.out.print("[" + ttype.print() + "]" + name + ";");
        }
        else
            System.out.print("["+"]" + name + ";");
        if(param.size() > 0) {
            System.out.print("(");
            for (Data d : param) {
                d.print();
            }
            System.out.print(")");
        }
        if(scope.size() > 0) {
            System.out.print(" => {");
            for(Data d: scope){
                d.print();
            }
            System.out.print("}");
        }
    }
}