package Domain;

import DataSource.DefaultDataLoader;
import Domain.Adapters.ASMClass;
import Domain.Adapters.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

public class ASMProjectDataManager extends ProjectDataManager{
    public ASMProjectDataManager(DefaultDataLoader dataLoader)   {
        super(dataLoader);
    }

    @Override
    public ClassAdapter createClassAdapter(String path) {
        byte[] bytes = dataLoader.loadFileBytes(path);
        ClassReader reader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        reader.accept(classNode, ClassReader.EXPAND_FRAMES);
        return new ASMClass(classNode);
    }
}
