package com.wakacommerce.profile.web.controller;

import java.beans.PropertyEditorSupport;

import com.wakacommerce.profile.core.domain.ChallengeQuestion;
import com.wakacommerce.profile.core.service.ChallengeQuestionService;

public class CustomChallengeQuestionEditor extends PropertyEditorSupport {
    
    private ChallengeQuestionService challengeQuestionService;
    
    public CustomChallengeQuestionEditor(ChallengeQuestionService challengeQuestionService) {
        this.challengeQuestionService = challengeQuestionService;
    }

    @Override
    public String getAsText() {
        ChallengeQuestion question = (ChallengeQuestion) getValue();
        if (question == null) {
            return null;
        } else {
            return question.getId().toString();
        }
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(challengeQuestionService.readChallengeQuestionById((Long.parseLong(text))));
    }

}
