package $package;

import org.junit.Test;

import com.microsoft.azure.functions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


/**
 * Unit test for Function class.
 */
public class FunctionTest {
    /**
     * Unit test for hello method.
     */
    @Test
    public void testHttpTriggerJava() throws Exception {
        // Setup
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Azure");
        doReturn(queryParams).when(req).getQueryParameters();

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();


        final HttpResponseMessage.Builder builder = mock(HttpResponseMessage.Builder.class);
        doReturn(builder).when(req).createResponseBuilder(any(HttpStatus.class));
        doReturn(builder).when(builder).body(anyString());

        final HttpResponseMessage res = mock(HttpResponseMessage.class);
        doReturn(res).when(builder).build();

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Invoke
        final HttpResponseMessage ret = new Function().HttpTriggerJava(req, context);

        // Verify
        assertSame(res, ret);
    }
}
