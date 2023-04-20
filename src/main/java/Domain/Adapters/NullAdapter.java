package Domain.Adapters;

import java.io.IOException;
import java.util.List;

public class NullAdapter implements ClassAdapter{
    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public Boolean getIsPublic() {
        return null;
    }

    @Override
    public String getExtends() {
        return null;
    }

    @Override
    public List<String> getInterfaces() {
        return null;
    }

    @Override
    public List<MethodAdapter> getAllMethods() {
        return null;
    }

    @Override
    public List<FieldAdapter> getAllFields() {
        return null;
    }

    @Override
    public boolean matchesClassName(String name) {
        return false;
    }

    @Override
    public boolean getIsAbstract() {
        return false;
    }

    @Override
    public boolean getIsInterface() {
        return false;
    }

    @Override
    public boolean getIsEnum() {
        return false;
    }

    @Override
    public boolean removeField(String name) {
        return false;
    }

    @Override
    public boolean isAnObserver() {
        return false;
    }

    @Override
    public byte[] addField(String fieldName, int accessType, String datatype) throws IOException {
        return new byte[0];
    }

    @Override
    public Class constructClass(byte[] output) {
        return null;
    }
}
