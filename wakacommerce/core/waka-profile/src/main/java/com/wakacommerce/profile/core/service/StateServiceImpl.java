
package com.wakacommerce.profile.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.profile.core.dao.StateDao;
import com.wakacommerce.profile.core.domain.State;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @ hui
 */
@Deprecated
@Service("blStateService")
public class StateServiceImpl implements StateService {

    @Resource(name="blStateDao")
    protected StateDao stateDao;

    public List<State> findStates() {
        return stateDao.findStates();
    }

    public List<State> findStates(String countryAbbreviation) {
        return stateDao.findStates(countryAbbreviation);
    }

    public State findStateByAbbreviation(String abbreviation) {
        return stateDao.findStateByAbbreviation(abbreviation);
    }

    @Transactional("blTransactionManager")
    public State save(State state) {
        return stateDao.save(state);
    }
}

