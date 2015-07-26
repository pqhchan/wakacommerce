
package com.wakacommerce.core.search.dao;

import java.util.List;

import com.wakacommerce.core.search.domain.SearchSynonym;

public interface SearchSynonymDao {
    public List<SearchSynonym> getAllSynonyms();
    public void createSynonym(SearchSynonym synonym);
    public void updateSynonym(SearchSynonym synonym);
    public void deleteSynonym(SearchSynonym synonym);
}
