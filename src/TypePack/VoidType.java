package TypePack;

/**
 * Created by LCC-002 on 14/06/2017.
 */
public class VoidType extends Type{
    public VoidType(){
        representation = "void";
    }

    @Override
    public boolean sameType(Type other){
        if(other instanceof UndefinedType)return false;
        return other instanceof VoidType;
    }
    @Override
    public String print() {
        return "void";
    }
}
