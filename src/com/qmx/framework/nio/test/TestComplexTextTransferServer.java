package com.qmx.framework.nio.test;
import com.qmx.framework.nio.common.Channels;
import com.qmx.framework.nio.common.ComplexSplitChannelBuffer;
import com.qmx.framework.nio.common.ConfigResources;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.MessageFormatEnhanceStringToString;
import com.qmx.framework.nio.common.PointModel;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.Server;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.common.TransferDirection;
import com.qmx.framework.nio.common.TransferType;
import com.qmx.framework.nio.listener.TestServerComplexDefaultHandleListener;

public class TestComplexTextTransferServer
{
	private static final MessageFormatEnhanceStringToString enhanceStringToStringSYNCHRONIZED  = new MessageFormatEnhanceStringToString(TransferType.SYNCHRONIZED,TransferDirection.REQUEST);
	private static final MessageFormatEnhanceStringToString enhanceStringToStringASYNCHRONY  = new MessageFormatEnhanceStringToString(TransferType.ASYNCHRONY);
	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		ConfigResources config = new ConfigResources();
		config.setPort(10086);
		config.setPointModel(PointModel.SERVER);
		Server server = new Server(config.getPort());
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(10, 2, 3);
		selectorStrategy.setHandleListen(new TestServerComplexDefaultHandleListener());
		selectorStrategy.setBufferType(ComplexSplitChannelBuffer.class);
		selectorStrategy.setConfig(config);
		selectorStrategy.getMessageContext().setMessageFormat(enhanceStringToStringASYNCHRONY);
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
		//channels.broadcast("heheheheheheh");
		
		for(int i=0;i<500;i++)
		{
			channels.setMessageFormat(enhanceStringToStringSYNCHRONIZED);
			Object result = channels.sendSynchronizedMessage(channels.getFirstOrderChannelName(), "china"+i,4000);
			if(null != result)
			{
				System.out.println(new String((byte[])result));
			}else
			{
				System.out.println(result);
			}
			channels.setMessageFormat(enhanceStringToStringASYNCHRONY);
			channels.broadcast("china"+i);
			
		}
		while(true)
		{
			channels.setMessageFormat(enhanceStringToStringASYNCHRONY);
			channels.broadcast("hehehehehehe");
			try
			{
				Thread.sleep(5000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
