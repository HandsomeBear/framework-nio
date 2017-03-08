package com.qmx.framework.nio.test;

import com.qmx.framework.nio.common.HybridLengthSplitChannelBuffer;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.Server;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.listener.TestServerHybridDefaultHandleListener;

public class TestHybridTransferServer
{
	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		Server server = new Server(10086);
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(10, 2);
		selectorStrategy
				.setHandleListen(new TestServerHybridDefaultHandleListener());
		selectorStrategy.setBufferType(HybridLengthSplitChannelBuffer.class);
		server.setSelectorStrategy(selectorStrategy);
		server.start();
	}
}
