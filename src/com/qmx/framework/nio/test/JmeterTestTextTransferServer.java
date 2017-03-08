package com.qmx.framework.nio.test;

import com.qmx.framework.nio.common.ConfigResources;
import com.qmx.framework.nio.common.LengthSplitChannelBuffer;
import com.qmx.framework.nio.common.PointModel;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.Server;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.listener.JmeterTestServerDefaultHandleListener;

public class JmeterTestTextTransferServer
{
	public static void main(String[] args)
	{
		ConfigResources config = new ConfigResources();
		config.setPort(10086);
		config.setPointModel(PointModel.SERVER);
		Server server = new Server(config.getPort());
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(50, 50);
		selectorStrategy.setHandleListen(new JmeterTestServerDefaultHandleListener());
		selectorStrategy.setBufferType(LengthSplitChannelBuffer.class);
		selectorStrategy.setConfig(config);
		server.setSelectorStrategy(selectorStrategy);
		server.start();
	}
}
