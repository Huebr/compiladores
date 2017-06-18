package SymbolTables;


import TypePack.ClassType;
import TypePack.Type;

import java.util.*;

public class ClassSymbolTable {
    public ClassType type;
    public String nomedaClasse, nomedoPai;
    public Map methodList, varList;

    public ClassSymbolTable(){
        nomedoPai="";
        nomedaClasse="";
        varList = new HashMap<String, TypePack.Type>();
        methodList = new HashMap<String, TypePack.Type>();
    }
    public void putVar(String name, Type t){
        if(!varList.containsKey(name)){
            varList.put(name, t);
        }else{
            System.out.println("Variavel \'" + name + "\' jah declarada em " + nomedaClasse + ".");
        }
    }
}