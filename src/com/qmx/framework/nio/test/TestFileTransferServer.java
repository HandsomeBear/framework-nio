package com.qmx.framework.nio.test;

import com.qmx.framework.nio.common.ByteEncoderAndDecoderImpl;
import com.qmx.framework.nio.common.DefaultAlgorithmImpl;
import com.qmx.framework.nio.common.EncoderAndDecoderFactory;
import com.qmx.framework.nio.common.LengthSplitChannelBuffer;
import com.qmx.framework.nio.common.MessageContext;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.MessageFormatBytesToBytes;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.Server;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.listener.TestServerFileDefaultHandleListener;

public class TestFileTransferServer
{
	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		Server server = new Server(10086);
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(2, 2);
		selectorStrategy
				.setHandleListen(new TestServerFileDefaultHandleListener());
		selectorStrategy.setBufferType(LengthSplitChannelBuffer.class);
		MessageContext messageContext = new MessageContext();
		messageContext.setAlgorithm(new DefaultAlgorithmImpl());
		EncoderAndDecoderFactory encoderAndDecoderFactory = new EncoderAndDecoderFactory();
		encoderAndDecoderFactory
				.setEncoderAndDecoderType(ByteEncoderAndDecoderImpl.class);
		messageContext.setEncoderAndDecoderFactory(encoderAndDecoderFactory);
		messageContext.setMessageFormat(new MessageFormatBytesToBytes());
		selectorStrategy.setMessageContext(messageContext);
		server.setSelectorStrategy(selectorStrategy);
		server.start();

	}
}
