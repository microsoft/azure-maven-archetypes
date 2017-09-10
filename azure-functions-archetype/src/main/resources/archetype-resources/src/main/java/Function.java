package $package;

import com.microsoft.azure.serverless.functions.annotation.*;
import com.microsoft.azure.serverless.functions.ExecutionContext;

/**
 * Hello function with HTTP Trigger.
 */
public class Function {
    @FunctionName("hello")
    public String hello(@HttpTrigger(name = "req", methods = {"get", "post"}, authLevel = AuthorizationLevel.ANONYMOUS) String req,
                        ExecutionContext context) {
        return String.format("Hello, %s!", req);
    }
}
