package com.qmx.framework.nio.impl;

import com.qmx.framework.nio.common.AuthModel;
import com.qmx.framework.nio.common.CertificateInterface;

public class CertificateInterfaceImpl implements CertificateInterface
{

	@Override
	public boolean certificateUser(AuthModel authModel){
		// TODO Auto-generated method stub
		if (authModel.getUserName().equals("qmx")
				&& authModel.getPasswd().equals("123456"))
			return true;
		return false;
	}

}
