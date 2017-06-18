package TypePack;

/**
 * Created by LCC-002 on 14/06/2017.
 */
public class ArrayType extends Type {
    public static String typeArray = "array";
    public int size;
    @Override
    public boolean sameType(Type other){
        if(other instanceof UndefinedType)return false;
        return other instanceof ArrayType;
    }

    @Override
    public boolean isAssingnable(Type other){
        return false;
    }

    @Override
    public String print() {
        return "int[]";
    }
}
