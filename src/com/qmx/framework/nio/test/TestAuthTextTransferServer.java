package com.qmx.framework.nio.test;

import com.qmx.framework.nio.common.CertificateModel;
import com.qmx.framework.nio.common.Channels;
import com.qmx.framework.nio.common.ConfigResources;
import com.qmx.framework.nio.common.LengthSplitChannelBuffer;
import com.qmx.framework.nio.common.MessageExecutor;
import com.qmx.framework.nio.common.PointModel;
import com.qmx.framework.nio.common.ScheduledCheckValid;
import com.qmx.framework.nio.common.ScriptExecutor;
import com.qmx.framework.nio.common.SelectorStrategy;
import com.qmx.framework.nio.common.Server;
import com.qmx.framework.nio.common.SingleSelectorStrategy;
import com.qmx.framework.nio.impl.CertificateInterfaceImpl;
import com.qmx.framework.nio.listener.TestAuthServerDefaultHandleListener;

/**
 * 单向服务端认证
 * 
 * @author qmx 2015-1-14 上午10:26:22
 * 
 */
public class TestAuthTextTransferServer
{
	private static String certificatePath = "D:\\soft\\Java\\bin\\test.cer";

	public static void main(String[] args)
	{
		MessageExecutor.register("123", new ScriptExecutor());
		ConfigResources config = new ConfigResources();
		config.setPort(10086);
		config.setPointModel(PointModel.SERVER);
		config.setCertificateAuth(true);
		CertificateModel certificateModel = new CertificateModel();
		certificateModel.setCertificatePath(certificatePath);
		config.setCertificateModel(certificateModel);
		config.setCertificateInterface(new CertificateInterfaceImpl());
		Server server = new Server(config.getPort());
		ScheduledCheckValid scheduledCheckValid = new ScheduledCheckValid();
		scheduledCheckValid.setInitialDelay(2000);
		scheduledCheckValid.setScheduledDelay(1000*30);
		scheduledCheckValid.setMaxAcceptWaitCertificateTime(1000*60*2);
		SelectorStrategy selectorStrategy = new SingleSelectorStrategy(10, 2);
		selectorStrategy
				.setHandleListen(new TestAuthServerDefaultHandleListener());
		selectorStrategy.setBufferType(LengthSplitChannelBuffer.class);
		selectorStrategy.setConfig(config);
		selectorStrategy.setScheduledCheckValid(scheduledCheckValid);
		server.setSelectorStrategy(selectorStrategy);
		server.start();
		int count = 0;
		Channels channels = Channels.newChannel(null);
		while (true)
		{
			try
			{
				Thread.sleep(9000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("-------------");
			channels.broadcast("heheheheheheh" + (count++));
		}

	}
}
