package com.qmx.framework.nio.listener;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qmx.framework.nio.common.ClientCertificateUtil;
import com.qmx.framework.nio.common.HandleListener;
import com.qmx.framework.nio.common.MessageEvent;
import com.qmx.framework.nio.common.StringPrintWriter;


/**
 * 
 * @author qmx 2014-12-1 上午11:12:28
 * 
 */
public class TestAuthClientDefaultHandleListener implements HandleListener
{
	private List<String> testData = new ArrayList<String>();
	private String password = "123456";
	private String alias = "test";
	// private String certificatePath = "D:\\soft\\Java\\bin\\test.cer";
	private String keyStorePath = "D:\\soft\\Java\\bin\\test.keystore";
	private final Logger log = LoggerFactory
			.getLogger(TestAuthClientDefaultHandleListener.class);

	public TestAuthClientDefaultHandleListener()
	{
		for (int i = 0; i < 500; i++)
		{
			testData.add("client" + i);
		}
	}

	@Override
	public void conneced(MessageEvent event)
	{
		// TODO Auto-generated method stub
		ClientCertificateUtil certificateUtil = new ClientCertificateUtil(
				keyStorePath, alias, password);
		String res = certificateUtil.getCert("qmx", "123456");
		System.out.println("conneced");
		if (null != res)
		{
			event.write(res);
		}
	}

	@Override
	public void close(MessageEvent event)
	{
		// TODO Auto-generated method stub
		System.out.println("close");
	}

	static int aaa = 0;

	@Override
	public void read(MessageEvent event)
	{
		// TODO Auto-generated method stub
		// 判断确定类型后执行
		event.setMessageType("123");
		event.executMessageExecutor();
		System.out.println(event.getMessage().toString());
		if (testData.size() > 0)
		{
			String str = testData.remove(0);
			event.write(str);
		}

	}

	@Override
	public void write(MessageEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void exception(MessageEvent event, Exception e)
	{
		// TODO Auto-generated method stub
		StringPrintWriter stringWriter = new StringPrintWriter();
		e.printStackTrace(stringWriter);
		log.error("异常->{}\n{}", event.getChannel().getChannelName(),
				stringWriter.getString());
	}

	@Override
	public void accept(MessageEvent event)
	{
		// TODO Auto-generated method stub
		System.out.println("accept");
	}
}
