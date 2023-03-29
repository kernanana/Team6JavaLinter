package Domain.Adapters;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.FieldNode;


public class ASMField implements FieldAdapter{
    private final FieldNode node;
    public ASMField(FieldNode node){
        this.node = node;
    }

    @Override
    public String getFieldName() {
        return node.name;
    }

    @Override
    public boolean getIsPublic() {
        return (node.access & Opcodes.ACC_PUBLIC) != 0;
    }

    @Override
    public String getType() {
        System.out.println(Type.getObjectType(node.desc).getClassName());
        return Type.getObjectType(node.desc).getClassName();
    }

    @Override
    public boolean getIsStatic() {
        return  (node.access & Opcodes.ACC_STATIC) != 0;
    }

    @Override
    public boolean getIsProtected() {
        return (node.access & Opcodes.ACC_PROTECTED) != 0;
    }

    @Override
    public  boolean getIsFinal(){
        return (node.access & Opcodes.ACC_FINAL) != 0;
    }

    @Override
    public boolean isList() {
        return getType().contains(".List;");
    }

    @Override
    public String getCollectionType(){
        int startIndex = node.signature.indexOf("<L");
        int endIndex = node.signature.indexOf(";>");
        return node.signature.substring(startIndex + 2, endIndex);
    }
}
