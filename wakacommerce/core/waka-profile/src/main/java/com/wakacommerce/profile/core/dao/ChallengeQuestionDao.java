package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.ChallengeQuestion;

public interface ChallengeQuestionDao {

    public List<ChallengeQuestion> readChallengeQuestions();
    public ChallengeQuestion readChallengeQuestionById(long challengeQuestionId);
    public ChallengeQuestion saveChallengeQuestion(ChallengeQuestion q);

}
