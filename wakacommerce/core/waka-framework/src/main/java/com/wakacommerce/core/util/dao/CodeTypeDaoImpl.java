
package com.wakacommerce.core.util.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.util.domain.CodeType;
import com.wakacommerce.core.util.domain.CodeTypeImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository("blCodeTypeDao")
public class CodeTypeDaoImpl implements CodeTypeDao {

    @PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public CodeType create() {
        return ((CodeType) entityConfiguration.createEntityInstance(CodeType.class.getName()));
    }

    @SuppressWarnings("unchecked")
    public List<CodeType> readAllCodeTypes() {
        Query query = em.createNamedQuery("BC_READ_ALL_CODE_TYPES");
        return query.getResultList();
    }

    public void delete(CodeType codeType) {
        if (!em.contains(codeType)) {
            codeType = (CodeType) em.find(CodeTypeImpl.class, codeType.getId());
        }
        em.remove(codeType);
    }

    public CodeType readCodeTypeById(Long codeTypeId) {
        return (CodeType) em.find(entityConfiguration.lookupEntityClass(CodeType.class.getName()), codeTypeId);
    }

    @SuppressWarnings("unchecked")
    public List<CodeType> readCodeTypeByKey(String key) {
        Query query = em.createNamedQuery("BC_READ_CODE_TYPE_BY_KEY");
        query.setParameter("key", key);
        List<CodeType> result = query.getResultList();
        return result;
    }

    public CodeType save(CodeType codeType) {
        if(codeType.getId()==null) {
            em.persist(codeType);
        }else {
            codeType = em.merge(codeType);
        }
        return codeType;
    }

}
