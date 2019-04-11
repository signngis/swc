package com.atguigu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.bean.TCert;
import com.atguigu.bean.TMemberCert;
import com.atguigu.mapper.TCertMapper;
import com.atguigu.mapper.TMemberCertMapper;
import com.atguigu.service.CertService;

@Service
public class CertServiceImpl implements CertService {

	@Autowired
	TCertMapper certMapper;
	
	@Autowired
	TMemberCertMapper memberCertMapper;
	
	@Override
	public List<TCert> listCertsByAcctType(String accttype) {
		// TODO Auto-generated method stub
		
		//按照账户类型查出所有资质
		return certMapper.selectCertsByAcctytype(accttype);
	}

	@Override
	public void saveMemberCerts(List<TMemberCert> certs) {
		// TODO Auto-generated method stub
		for (TMemberCert tMemberCert : certs) {
			memberCertMapper.insertSelective(tMemberCert);
		}
	}

}
