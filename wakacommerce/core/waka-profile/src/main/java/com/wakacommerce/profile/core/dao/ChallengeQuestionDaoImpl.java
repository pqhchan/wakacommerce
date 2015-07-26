
package com.wakacommerce.profile.core.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.profile.core.domain.ChallengeQuestion;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("blChallengeQuestionDao")
public class ChallengeQuestionDaoImpl implements ChallengeQuestionDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<ChallengeQuestion> readChallengeQuestions() {
        Query query = em.createNamedQuery("BC_READ_CHALLENGE_QUESTIONS");
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public ChallengeQuestion readChallengeQuestionById(long challengeQuestionId) {
        Query query = em.createNamedQuery("BC_READ_CHALLENGE_QUESTION_BY_ID");
        query.setParameter("question_id", challengeQuestionId);
        List<ChallengeQuestion> challengeQuestions = query.getResultList();
        return challengeQuestions == null || challengeQuestions.isEmpty() ? null : challengeQuestions.get(0);
    }
    
    @Transactional("blTransactionManager")
    public ChallengeQuestion saveChallengeQuestion(ChallengeQuestion q) {
        return em.merge(q);
    }

}
