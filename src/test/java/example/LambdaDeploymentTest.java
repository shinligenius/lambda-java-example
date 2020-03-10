package example;

import java.io.FileInputStream;
import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.services.lambda.model.UpdateFunctionCodeRequest;
import com.amazonaws.services.lambda.model.UpdateFunctionConfigurationRequest;
import com.amazonaws.util.IOUtils;

public class LambdaDeploymentTest {

	private final AWSLambda lambda = AWSLambdaClientBuilder.defaultClient();

	@Ignore
	@Test
	public void testUpdateFunctionConfiguration() {
		lambda.updateFunctionConfiguration(new UpdateFunctionConfigurationRequest().withFunctionName("helloTest")
				.withHandler("example.HelloRequestHandler"));
	}

	@Ignore
	@Test
	public void testUpdateFunctionCode() {
		try (final FileInputStream fileInputStream = new FileInputStream("target/lambda-java-example-1.0.0.jar")) {
			final byte[] bytes = IOUtils.toByteArray(fileInputStream);
			lambda.updateFunctionCode(new UpdateFunctionCodeRequest().withFunctionName("helloTest").withPublish(true)
					.withZipFile(ByteBuffer.wrap(bytes)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Ignore
	@Test
	public void testInvokeRemoteFunction() {
		final Request request = new Request();
		request.setFirstName("Genius");
		request.setLastName("Chou");
		
		final HelloService service = LambdaInvokerFactory.builder().lambdaClient(this.lambda).build(HelloService.class);
		Assert.assertEquals("Hello Genius Chou.", service.hello(request).getGreetings());
	}
}
