package SymbolTables;
import java.util.*;

public class GlobalSymbolTable{
    public Map myTable;

    public  GlobalSymbolTable(){
        myTable = new HashMap<String,ClassSymbolTable>();
    }

    public GlobalSymbolTable(Map newTable){
        this.myTable = newTable;
    }

    public ClassSymbolTable lookUp(String id){
        return (ClassSymbolTable) myTable.get(id);
    }
    public void enter(String id, ClassSymbolTable classTable){
        myTable.put(id,classTable);
    }
}