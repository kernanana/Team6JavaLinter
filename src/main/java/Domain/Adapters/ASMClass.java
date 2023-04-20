package Domain.Adapters;


import DataSource.ASMClassWriterFacade;
import DataSource.ConcreteClassLoader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ASMClass implements ClassAdapter {
    private final ClassNode node;

    public ASMClass(ClassNode node){
        this.node = node;
    }
    @Override
    public String getClassName() {
        return node.name;
    }

    @Override
    public Boolean getIsPublic() {
        return (node.access & Opcodes.ACC_PUBLIC) != 0;
    }

    @Override
    public String getExtends() {
        return node.superName;
    }

    @Override
    public List<String> getInterfaces() {
        return node.interfaces;
    }

    @Override
    public List<MethodAdapter> getAllMethods() {
        List<MethodAdapter> methods = new ArrayList<>();
        List<MethodNode> mNodes = node.methods;
        for (MethodNode mNode : mNodes) {
            MethodAdapter adapter = new ASMMethod(mNode);
            methods.add(adapter);
        }
        return methods;
    }

    @Override
    public List<FieldAdapter> getAllFields() {
        List<FieldAdapter> fields = new ArrayList<>();
        List<FieldNode> fNodes = node.fields;
        for (FieldNode fNode : fNodes) {
            FieldAdapter adapter = new ASMField(fNode);
            fields.add(adapter);
        }
        return fields;
    }

    @Override
    public boolean matchesClassName(String name) {
        return name.equals(this.getClassName());
    }

    @Override
    public boolean getIsAbstract() {
        return (this.node.access & Opcodes.ACC_ABSTRACT) != 0;
    }

    @Override
    public boolean getIsEnum() {
        return (this.node.access & Opcodes.ACC_ENUM) != 0;
    }

    @Override
    public boolean removeField(String name) {
        if(!node.fields.contains(name)){
            return false;
        }
        node.fields.remove(name);
        return true;
    }

    @Override
    public boolean isAnObserver() {
        return true;
    }

    @Override
    public byte[] addField(String fieldName, int accessType, String dataType) throws IOException {
        ASMClassWriterFacade writer = new ASMClassWriterFacade(this.getClassName());
        return writer.addField(fieldName,accessType);
    }

    @Override
    public Class constructClass(byte[] output) {
        ConcreteClassLoader mcl = new ConcreteClassLoader();
        Class c = mcl.defineClass(this.getClassName(), output);
        return c;
    }

    @Override
    public boolean getIsInterface() {
        return (this.node.access & Opcodes.ACC_INTERFACE) != 0;
    }



}
