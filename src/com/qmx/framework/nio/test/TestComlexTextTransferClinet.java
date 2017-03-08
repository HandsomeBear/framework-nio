package com.qmx.framework.nio.test;

import com.qmx.framework.nio.common.Client;
import com.qmx.framework.nio.common.ComplexSplitChannelBuffer;
import com.qmx.framework.nio.common.ConfigResources;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.MessageFormatEnhanceStringToString;
import com.qmx.framework.nio.common.PointModel;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.listener.TestClientComplexDefaultHandleListener;

public class TestComlexTextTransferClinet
{
	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		ConfigResources config = new ConfigResources();
		config.setIp("172.18.70.109");
		config.setPort(10086);
		config.setPointModel(PointModel.CLIENT);
		Client client = new Client(config.getIp(), config.getPort());
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(2, 2);
		selectorStrategy.setHandleListen(new TestClientComplexDefaultHandleListener());
		selectorStrategy.setBufferType(ComplexSplitChannelBuffer.class);
		selectorStrategy.setConfig(config);
		selectorStrategy.getMessageContext().setMessageFormat(new MessageFormatEnhanceStringToString());
		client.setSelectorStrategy(selectorStrategy);
		client.start();
		config.setConnection(client);
	}
}
