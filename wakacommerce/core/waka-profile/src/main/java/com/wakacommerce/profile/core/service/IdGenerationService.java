
package com.wakacommerce.profile.core.service;

public interface IdGenerationService {

    public Long findNextId(String idType);

    public Long findNextId(String idType, Long batchSize);

}
