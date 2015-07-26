
package com.wakacommerce.profile.core.dao;

import javax.persistence.OptimisticLockException;

import com.wakacommerce.profile.core.domain.IdGeneration;

public interface IdGenerationDao {

    public IdGeneration findNextId(String idType) throws OptimisticLockException, Exception;

    public IdGeneration findNextId(String idType, Long batchSize) throws OptimisticLockException, Exception;

}
