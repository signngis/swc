package com.atguigu.service;

import java.util.List;

import com.atguigu.bean.TCert;
import com.atguigu.bean.TMemberCert;

public interface CertService {

	List<TCert> listCertsByAcctType(String accttype);

	//保存会员资质信息
	void saveMemberCerts(List<TMemberCert> certs);

}
