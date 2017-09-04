package $package;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for Function class.
 */
public class FunctionTest {
    /**
     * Unit test for hello method.
     */
    @Test
    public void testHello() throws Exception {
        final Function function = new Function();

        final String ret = function.hello("function", null);

        assertEquals("Hello, function!", ret);
    }
}
