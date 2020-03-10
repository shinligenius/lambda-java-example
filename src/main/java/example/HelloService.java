package example;

import com.amazonaws.services.lambda.invoke.LambdaFunction;

public interface HelloService {

	@LambdaFunction(functionName = "helloTest")
	Response hello(final Request request);
}
