package TypePack;

import AST.Visitor.BuildSymbolGlobalTableVisitor;
import SymbolTables.ClassSymbolTable;

/**
 * Created by LCC-002 on 14/06/2017.
 */
public class ClassType extends Type{
    public String baseRepresentation = "";

    public ClassType(String rep, String bRep){
        representation = rep;
        baseRepresentation = bRep;
    }
    @Override
    public boolean sameType(Type other){
        if(other instanceof UndefinedType)return false;
        if(other instanceof ClassType){
            return representation.equals(other.representation);
        }
        return false;
    }

    public boolean isAssingnable(Type other){
        if(other instanceof ClassType){
            if(sameType(other))
                return true;
            if(!baseRepresentation.equals("")) {
                ClassSymbolTable mytable = BuildSymbolGlobalTableVisitor.global.lookUp(baseRepresentation);
                if (mytable.type.isAssingnable(other)) // Sobe do tipo atual até o pai máximo na hierarquia;
                    return true;
                if (other.isAssingnable(this)) // Sobe do other até o pai máximo da hierarquia;
                    return true;
            }
        }
        return false;
    }

    @Override
    public String print() {
        return representation;
    }

}
