package com.qmx.framework.nio.test;

import com.qmx.framework.nio.common.Channels;
import com.qmx.framework.nio.common.ConfigResources;
import com.qmx.framework.nio.common.DelimiterLimitChannelBuffer;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.MessageFormatDelimiterStringToString;
import com.qmx.framework.nio.common.PointModel;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.Server;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.listener.TestServerDelimiterDefaultHandleListener;

public class TestTextDelimiterTransferServer
{
	private final static MessageFormatDelimiterStringToString DELIMITER_STRING_TO_STRING = new MessageFormatDelimiterStringToString();
	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		ConfigResources config = new ConfigResources();
		config.setPort(10086);
		config.setPointModel(PointModel.SERVER);
		Server server = new Server(config.getPort());
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(10, 2);
		selectorStrategy.setHandleListen(new TestServerDelimiterDefaultHandleListener());
		selectorStrategy.setBufferType(DelimiterLimitChannelBuffer.class);
		selectorStrategy.setConfig(config);
		selectorStrategy.getMessageContext().setMessageFormat(DELIMITER_STRING_TO_STRING);
		server.setSelectorStrategy(selectorStrategy);
		server.start();
		try
		{
			Thread.sleep(8000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-------------");
		Channels channels = Channels.newChannel(null);
		while(true)
		{
			channels.broadcast("heheheheheheh");
			try
			{
				Thread.sleep(15000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
