
package com.wakacommerce.core.search.domain;

public interface SearchSynonym {
    public String getTerm();
    public void setTerm(String term);
    public String[] getSynonyms();
    public void setSynonyms(String[] synonyms);
}
