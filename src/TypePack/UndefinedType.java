package TypePack;

/**
 * Created by Clóvis on 16/06/2017.
 */
public class UndefinedType extends Type{
    @Override
    public String print() {
        return "undefined";
    }

    @Override
    public boolean sameType(Type otherType) {
        return false;
    }
}
