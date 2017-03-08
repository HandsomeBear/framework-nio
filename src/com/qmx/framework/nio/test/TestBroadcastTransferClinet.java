package com.qmx.framework.nio.test;

import com.qmx.framework.nio.common.Client;
import com.qmx.framework.nio.common.LengthSplitChannelBuffer;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.listener.TestClientBroacasttHandleListener;

public class TestBroadcastTransferClinet
{
	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		Client client = new Client("172.18.70.109", 10086);
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(2, 2);
		selectorStrategy.setHandleListen(new TestClientBroacasttHandleListener());
		selectorStrategy.setBufferType(LengthSplitChannelBuffer.class);
		client.setSelectorStrategy(selectorStrategy);
		client.start();
	}
}
