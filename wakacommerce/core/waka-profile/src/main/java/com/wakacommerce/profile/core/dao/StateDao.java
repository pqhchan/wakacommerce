
package com.wakacommerce.profile.core.dao;

import java.util.List;

import com.wakacommerce.profile.core.domain.State;

/**
 *
 * @ hui
 */
@Deprecated
public interface StateDao {

    public List<State> findStates();

    public List<State> findStates(String countryAbbreviation);

    public State findStateByAbbreviation(String abbreviation);

    public State create();
    
    public State save(State state);
    
}
