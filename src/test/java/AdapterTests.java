import DataSource.DefaultDataLoader;
import Domain.ASMProjectDataManager;
import Domain.Adapters.ClassAdapter;
import Domain.ProjectDataManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdapterTests {
    @Test
    public void testLoadsBytes(){
        DefaultDataLoader loader = new DefaultDataLoader();
        byte[] bytes = loader.loadFileBytes("./src/test/resources/AbstractClass.class");
        Assertions.assertNotNull(bytes);
    }
    @Test
    public void testCreatesAdapter() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/AbstractClass.class");
        Assertions.assertEquals("ASMPracticeCode/AbstractClass", adapter.getClassName());
    }
    @Test
    public void testClassAdapterHasAllInfo() {
        ProjectDataManager manager = new ASMProjectDataManager(new DefaultDataLoader());
        ClassAdapter adapter = manager.createClassAdapter("./src/test/resources/AbstractClass.class");
        Assertions.assertEquals("ASMPracticeCode/AbstractClass", adapter.getClassName());
        Assertions.assertTrue(adapter.getIsPublic());
        Assertions.assertEquals(1,adapter.getAllFields().size());
        Assertions.assertEquals(4, adapter.getAllMethods().size());
        Assertions.assertTrue(adapter.matchesClassName("ASMPracticeCode/AbstractClass"));
    }
}
