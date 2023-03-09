package Domain.Adapters;


public interface FieldAdapter {
    public abstract String getFieldName();
    public abstract boolean getIsPublic();
    public abstract boolean getIsFinal();
    public abstract String getType();
    public abstract boolean getIsStatic();
    public abstract boolean getIsProtected();
}
