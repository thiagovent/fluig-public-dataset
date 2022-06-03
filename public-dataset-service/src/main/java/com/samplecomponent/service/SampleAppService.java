package com.samplecomponent.service;

import java.util.List;

import javax.ejb.Remote;

import com.fluig.sdk.api.common.SDKException;
import com.samplecomponent.entity.SampleApp;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

@Remote
public interface SampleAppService {

	public static final String JNDI_NAME = "service/sample-app";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;
	
	long create(SampleApp entity) throws FDNCreateException;
	
	SampleApp get(long id);
	
	void update(SampleApp entity) throws FDNUpdateException;
	
	void delete(long id) throws FDNRemoveException;
	
	List<SampleApp> find(String text, int limit, int offset) throws SDKException;
	
	List<SampleApp> findByCategory(Long categoryId, int limit, int offset) throws SDKException;
}
