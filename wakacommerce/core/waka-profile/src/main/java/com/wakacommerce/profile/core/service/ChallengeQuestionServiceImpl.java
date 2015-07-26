
package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.profile.core.dao.ChallengeQuestionDao;
import com.wakacommerce.profile.core.domain.ChallengeQuestion;

import java.util.List;

import javax.annotation.Resource;

@Service("blChallengeQuestionService")
public class ChallengeQuestionServiceImpl implements ChallengeQuestionService {

    @Resource(name="blChallengeQuestionDao")
    protected ChallengeQuestionDao challengeQuestionDao;

    @Override
    @Transactional("blTransactionManager")
    public List<ChallengeQuestion> readChallengeQuestions() {
        return challengeQuestionDao.readChallengeQuestions();
    }

    @Override
    @Transactional("blTransactionManager")
    public ChallengeQuestion readChallengeQuestionById(long challengeQuestionId) {
        return challengeQuestionDao.readChallengeQuestionById(challengeQuestionId);
    }
    
}
