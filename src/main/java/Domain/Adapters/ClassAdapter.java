package Domain.Adapters;

import java.io.IOException;
import java.util.List;

public interface ClassAdapter {
    public abstract String getClassName();
    public abstract Boolean getIsPublic();
    public abstract String getExtends();
    public abstract List<String> getInterfaces();
    public abstract List<MethodAdapter> getAllMethods();
    public abstract List<FieldAdapter> getAllFields();
    public abstract boolean matchesClassName(String name);
    public abstract boolean getIsAbstract();
    public abstract boolean getIsInterface();
    public abstract boolean getIsEnum();
    public abstract boolean removeField(String name);

    public abstract boolean isAnObserver();

    public abstract byte[] addField(String fieldName, int accessType, String datatype) throws IOException;

    Class constructClass(byte[] output);

}
