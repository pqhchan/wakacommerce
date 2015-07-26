
package com.wakacommerce.profile.core.service;

import java.util.List;

import com.wakacommerce.profile.core.domain.State;

/**
 * @deprecated - use {@link com.wakacommerce.profile.core.service.CountrySubdivisionService} instead.
 */
@Deprecated
public interface StateService {

    public List<State> findStates();

    public List<State> findStates(String countryAbbreviation);

    public State findStateByAbbreviation(String abbreviation);
    
    public State save(State state);
    
}
