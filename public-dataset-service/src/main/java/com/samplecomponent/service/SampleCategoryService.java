package com.samplecomponent.service;

import java.util.List;

import javax.ejb.Remote;

import com.fluig.sdk.api.common.SDKException;
import com.samplecomponent.entity.SampleCategory;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

@Remote
public interface SampleCategoryService {

	public static final String JNDI_NAME = "service/sample-category";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;
	
	long create(SampleCategory entity) throws FDNCreateException;
	
	SampleCategory get(long id);
	
	void update(SampleCategory entity) throws FDNUpdateException;
	
	void delete(long id) throws FDNRemoveException;
	
	List<SampleCategory> find(String text, int limit, int offset) throws SDKException;
}
