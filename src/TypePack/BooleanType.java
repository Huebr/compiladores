package TypePack;

/**
 * Created by Clóvis on 16/06/2017.
 */
public class BooleanType extends TypePack.Type {
    @Override
    public boolean sameType(Type other){
        if(other instanceof UndefinedType)return false;
        return other instanceof BooleanType;
    }
    @Override
    public String print() {
        return "boolean";
    }
}
