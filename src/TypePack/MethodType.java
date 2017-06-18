package TypePack;

/**
 * Created by Clóvis on 16/06/2017.
 */
public class MethodType extends Type {
    public TypePack.Type classe;
    public TypePack.Type retorno;
    public MethodType(TypePack.Type t){
        classe = t;
    }
    @Override
    public String print() {
        return  "methodType";
    }
}
