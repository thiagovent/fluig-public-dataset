package com.samplecomponent.dao;

import com.samplecomponent.entity.SampleApp;
import com.totvs.technology.foundation.common.AbstractDAO;
import com.totvs.technology.foundation.common.exception.FDNRuntimeException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Aqui a sugestão é herdar a classe abstrata AbstractDAO e passar a entidade
 * SampleApp como sendo o objeto genérico desse DAO. Além disso, alguns serviços
 * de CRUD já são herdados.
 */
@Stateless(name = "dao/SampleApp", mappedName = "dao/SampleApp")
public class SampleAppDAO extends AbstractDAO<SampleApp> {

	/**
	 * Construtor para gerencia a entidade {@link SampleApp}
	 */
	public SampleAppDAO() {
		super(SampleApp.class);
	}

	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return this.em;
	}

	@Override
	// Obrigatório utilizar o DataSource correto: AppDS
	@PersistenceContext(unitName = "AppDS")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleApp> findApps(Long tenantId, String text, int limit, int offset) {
		try {
			TypedQuery<SampleApp> query = getEntityManager().createNamedQuery(SampleApp.FIND_BY_NAME_DEV,
					SampleApp.class);
			query.setParameter("tenantId", tenantId);
			query.setParameter("text", "%" + text.toLowerCase() + "%");
			query.setFirstResult(offset);
			query.setMaxResults(limit);

			return query.getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FDNRuntimeException(e.getMessage(), e.getCause()); 
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleApp> findAppsByCategoryId(Long tenantId, Long categoryId, int limit, int offset) {
		try {
			TypedQuery<SampleApp> query = getEntityManager().createNamedQuery(SampleApp.FIND_BY_CATEGORY,
					SampleApp.class);
			query.setParameter("tenantId", tenantId);
			query.setParameter("categoryId", categoryId);
			query.setFirstResult(offset);
			query.setMaxResults(limit);

			return query.getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new FDNRuntimeException(e.getMessage(), e.getCause());
		}
	}

}
