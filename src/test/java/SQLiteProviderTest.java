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
        provider.close();
    }
}
