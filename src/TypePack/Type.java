package TypePack;

/**
 * Created by LCC-002 on 14/06/2017.
 */
public abstract class Type {
    public String representation;

    public boolean sameType(Type otherType){
        return otherType.representation.equals(representation);
    }
    public boolean isAssingnable(Type otherType){
        return otherType.representation.equals(representation);
    }
    abstract public String print();

}
