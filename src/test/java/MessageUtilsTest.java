import org.junit.jupiter.api.Test;
import wtf.cmyk.toomanycolors.utils.MessageUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageUtilsTest {
    @Test
    void testColorHex() {
        String input = "#F20";
        assertEquals("#FF2200", MessageUtils.convertHex(input));
        input = "#FF2200";
        assertEquals("#FF2200", MessageUtils.convertHex(input));
    }
}
