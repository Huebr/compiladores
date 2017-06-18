package TypePack;

/**
 * Created by LCC-002 on 14/06/2017.
 */
public class VoidType extends Type{
    public VoidType(){
        representation = "void";
    }

    @Override
    public String print() {
        return "void";
    }
}
