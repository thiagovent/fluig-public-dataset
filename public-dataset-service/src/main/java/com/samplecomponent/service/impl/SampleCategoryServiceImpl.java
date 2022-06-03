package com.samplecomponent.service.impl;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.SecurityService;
import com.fluig.sdk.service.UserService;
import com.samplecomponent.dao.SampleCategoryDAO;
import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.service.SampleCategoryService;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

import javax.ejb.*;
import java.util.List;
import java.util.Optional;

@Remote
@Stateless(name = SampleCategoryService.JNDI_NAME, mappedName = SampleCategoryService.JNDI_NAME)
public class SampleCategoryServiceImpl implements SampleCategoryService {

	@EJB
	private SampleCategoryDAO dao;

	@EJB(lookup = SecurityService.JNDI_REMOTE_NAME)
	private SecurityService securityService;
	
	@EJB(lookup = UserService.JNDI_REMOTE_NAME)
	private UserService userService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(SampleCategory category) throws FDNCreateException {
		try {
			/**
			 * check permission if needed 
			 */
			category.setTenantId(securityService.getCurrentTenantId());
			Optional<SampleCategory> categoryOptional = Optional.ofNullable(dao.create(category));
			return (categoryOptional.isPresent() ? categoryOptional.get().getId() : null);
		} catch (FDNCreateException | SDKException e) {
            throw new FDNCreateException(e.getMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SampleCategory get(long id) {
		Optional<SampleCategory> sampleCategory = Optional.ofNullable(dao.find(id));
		return (sampleCategory.isPresent() ? sampleCategory.get() : null);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void update(SampleCategory sampleCategory) throws FDNUpdateException {
		/**
		 * check permission if needed
		 */
		Optional<SampleCategory> sampleCategory1 = Optional.ofNullable(dao.find(sampleCategory.getId()));
		if (!sampleCategory1.isPresent())
			throw new FDNUpdateException("No Category found for ID: " + sampleCategory.getId());
		sampleCategory.setTenantId(sampleCategory1.get().getTenantId());
		dao.edit(sampleCategory);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void delete(long id) throws FDNRemoveException {
		/**
		 * check permission if needed
		 */
		Optional<SampleCategory> sampleCategory = Optional.ofNullable(dao.find(id));
		if (!sampleCategory.isPresent())
			throw new FDNRemoveException("No Category found for ID: " + id);
		dao.remove(sampleCategory.get());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleCategory> find(String text, int limit, int offset) throws SDKException {
		return dao.findCategories(securityService.getCurrentTenantId(), text, limit, offset);
	}

}
