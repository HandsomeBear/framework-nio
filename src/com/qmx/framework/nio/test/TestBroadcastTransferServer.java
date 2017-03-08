package com.qmx.framework.nio.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.qmx.framework.nio.common.ByteEncoderAndDecoderImpl;
import com.qmx.framework.nio.common.Channels;
import com.qmx.framework.nio.common.DefaultAlgorithmImpl;
import com.qmx.framework.nio.common.EncoderAndDecoderFactory;
import com.qmx.framework.nio.common.LengthSplitChannelBuffer;
import com.qmx.framework.nio.common.MessageContext;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.MessageFormatBytesToBytes;
import com.qmx.framework.nio.common.MessageFormatStringToString;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.Server;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.common.StringEncoderAndDecoderImpl;
import com.qmx.framework.nio.listener.TestServerBroacasttHandleListener;

public class TestBroadcastTransferServer
{
	static int aaa;

	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		Server server = new Server(10086);
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(10, 2);
		selectorStrategy
				.setHandleListen(new TestServerBroacasttHandleListener());
		selectorStrategy.setBufferType(LengthSplitChannelBuffer.class);
		MessageContext messageContext = new MessageContext();
		messageContext.setAlgorithm(new DefaultAlgorithmImpl());
		EncoderAndDecoderFactory encoderAndDecoderFactory = new EncoderAndDecoderFactory();
		encoderAndDecoderFactory
				.setEncoderAndDecoderType(StringEncoderAndDecoderImpl.class);
		messageContext.setEncoderAndDecoderFactory(encoderAndDecoderFactory);
		messageContext.setMessageFormat(new MessageFormatStringToString());
		selectorStrategy.setMessageContext(messageContext);
		server.setSelectorStrategy(selectorStrategy);
		server.start();
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Channels channels = Channels.newChannel(null);
		for (int i = 0; i < 500; i++)
			channels.broadcast("hello aaa" + i);
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		encoderAndDecoderFactory
				.setEncoderAndDecoderType(ByteEncoderAndDecoderImpl.class);
		messageContext.setEncoderAndDecoderFactory(encoderAndDecoderFactory);
		messageContext.setMessageFormat(new MessageFormatBytesToBytes());
		channels.changeMessageContext(messageContext);
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream("d:\\Categories.xml");
			byte[] aa = new byte[8096];
			int temp = 0;
			while ((temp = inputStream.read(aa)) != -1)
			{
				aaa += temp;
				System.out.println("累计写" + aaa);
				byte[] sendMsg = Arrays.copyOfRange(aa, 0, temp);
				channels.broadcast(sendMsg);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				inputStream.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("写完 ");
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		encoderAndDecoderFactory
				.setEncoderAndDecoderType(StringEncoderAndDecoderImpl.class);
		messageContext.setEncoderAndDecoderFactory(encoderAndDecoderFactory);
		messageContext.setMessageFormat(new MessageFormatStringToString());
		channels.changeMessageContext(messageContext);
		for (int i = 0; i < 500; i++)
		{
			channels.broadcast("hello bbbbbb" + i);
		}
			
	}
}
