import org.junit.jupiter.api.Test;
import wtf.cmyk.toomanycolors.storage.SQLiteProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SQLiteProviderTest {
    private final SQLiteProvider provider = new SQLiteProvider();

    @Test
    void getterSetterTest() {
        provider.init();
        provider.setPlaceholder("TEST_UUID", "$5", "FF44FF");
        assertEquals("FF44FF", provider.getHexColor("TEST_UUID", "$5"));
        provider.setPlaceholder("TEST_UUID", "$5", "FA44FF");
        assertEquals("FA44FF", provider.getHexColor("TEST_UUID", "$5"));
        assertEquals(false, provider.hasPlaceholder("TEST_UUID", "$69"));
        assertEquals(true, provider.hasPlaceholder("TEST_UUID", "$5"));
        provider.setPlaceholder("TEST_UUID", "$2", "FF44FF");
        provider.setPlaceholder("TEST_UUID", "$4", "FF44FF");
        provider.setPlaceholder("TEST_UUID", "$9", "FF44FF");
        assertEquals(4, provider.getTotalPlaceholders("TEST_UUID"));
        provider.delPlaceholder("TEST_UUID", "$4");
        assertEquals(3, provider.getTotalPlaceholders("TEST_UUID"));
        provider.close();
    }
}
