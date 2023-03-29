package Domain.Adapters;

import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;

import java.util.List;

public interface MethodAdapter {

    public abstract String getMethodName();
    public abstract boolean getIsPublic();
    public abstract String getReturnType();
    public abstract List<String> getArgTypes();
    public abstract boolean isStatic();
    public abstract List<String> getInstructions();
    public abstract List<Integer> getInstOpCodes();
    public abstract boolean isGetter();
    public abstract boolean isSetter();
    public abstract boolean getIsAbstract();
    public abstract boolean getIsPrivate();
    public abstract String getSignature();
}
