package com.qmx.framework.nio.test;

import java.io.UnsupportedEncodingException;

import com.qmx.framework.nio.common.ByteEncoderAndDecoderImpl;
import com.qmx.framework.nio.common.Client;
import com.qmx.framework.nio.common.DefaultAlgorithmImpl;
import com.qmx.framework.nio.common.EncoderAndDecoderFactory;
import com.qmx.framework.nio.common.LengthSplitChannelBuffer;
import com.qmx.framework.nio.common.MessageContext;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.MessageFormatBytesToBytes;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.listener.TestClientFileDefaultHandleListener;

public class TestFileTransferClinet
{
	public static void main(String[] args) throws UnsupportedEncodingException
	{
		MessageExecutor.register("123", new ScriptExecutor());
		Client client = new Client("172.18.70.109", 10086);
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(2, 10);
		selectorStrategy
				.setHandleListen(new TestClientFileDefaultHandleListener());
		selectorStrategy.setBufferType(LengthSplitChannelBuffer.class);
		MessageContext messageContext = new MessageContext();
		messageContext.setAlgorithm(new DefaultAlgorithmImpl());
		EncoderAndDecoderFactory encoderAndDecoderFactory = new EncoderAndDecoderFactory();
		encoderAndDecoderFactory
				.setEncoderAndDecoderType(ByteEncoderAndDecoderImpl.class);
		messageContext.setEncoderAndDecoderFactory(encoderAndDecoderFactory);
		messageContext.setMessageFormat(new MessageFormatBytesToBytes());
		selectorStrategy.setMessageContext(messageContext);
		client.setSelectorStrategy(selectorStrategy);
		client.start();
	}
}
