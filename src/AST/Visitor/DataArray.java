package AST.Visitor;

import java.util.ArrayList;

/**
 * Created by Clóvis on 15/06/2017.
 */
public class DataArray{
    public ArrayList<Data> array;
    public DataArray(){
        array = new ArrayList<Data>();
    }

    private boolean contemMesmoTipo(Data toAdd){
        for(Data d : array){
            if(d.name.equals(toAdd.name)){
                return d.ttype.sameType(toAdd.ttype);
            }
        }
        return false;
    }

    public Data get(String method){
        for(Data d : array){
            if(d.name.equals(method))
                return d;
        }
        return null;
    }

    public boolean contem(String method, String var){
        for(Data d : array){
            Data procurado = new Data();
            procurado.name = var;
            if(d.name.equals(method) && d.contains(procurado))
                return true;
        }
        return false;
    }

    public void adicionar(Data toAdd, String className){
        if(!contemMesmoTipo(toAdd))
            array.add(toAdd);
        else
            System.out.println("Jah existe um mehtodo \'" + toAdd.name + "\' do tipo "
                    + toAdd.ttype.print() +" na classe "+ className +".");
    }

    public void paraString(){
        for(Data d : array){
            System.out.print("      ");
            d.print();
            System.out.println();
        }
    }
}