package com.nhn.pinpoint.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;

import com.nhn.pinpoint.bootstrap.context.TraceContext;
import com.nhn.pinpoint.bootstrap.interceptor.ByteCodeMethodDescriptorSupport;
import com.nhn.pinpoint.bootstrap.interceptor.Interceptor;
import com.nhn.pinpoint.bootstrap.interceptor.MethodDescriptor;
import com.nhn.pinpoint.bootstrap.interceptor.TraceContextSupport;
import com.nhn.pinpoint.bootstrap.logging.PLoggerFactory;
import com.nhn.pinpoint.profiler.logging.Slf4jLoggerBinder;

public class BaseInterceptorTest {

	Interceptor interceptor;
	MethodDescriptor descriptor;

	public void setInterceptor(Interceptor interceptor) {
		this.interceptor = interceptor;
	}
	
	public void setMethodDescriptor(MethodDescriptor methodDescriptor) {
		this.descriptor = methodDescriptor;
	}
	
	@BeforeClass
	public static void before() {
		PLoggerFactory.initialize(new Slf4jLoggerBinder());
	}

	@Before
	public void beforeEach() {
		if (interceptor == null) {
			Assert.fail("set the interceptor first.");
		}

		if (interceptor instanceof TraceContextSupport) {
			// sampler

			// trace context
			TraceContext traceContext = new MockTraceContextFactory().create();
			((TraceContextSupport) interceptor).setTraceContext(traceContext);
		}
		
		if (interceptor instanceof ByteCodeMethodDescriptorSupport) {
			if (descriptor != null) {
				((ByteCodeMethodDescriptorSupport)interceptor).setMethodDescriptor(descriptor);
			}
		}
	}

}