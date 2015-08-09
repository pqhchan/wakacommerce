
package com.wakacommerce.common.sandbox;

import com.google.common.collect.BiMap;

import java.util.List;

import javax.persistence.EntityManager;

/**
 *
 * @ hui
 */
public interface SandBoxHelper {

    List<Long> mergeCloneIds(Class<?> type, Long... originalIds);

    BiMap<Long, Long> getSandBoxToOriginalMap(Class<?> type, Long... ids);

    Long getSandBoxVersionId(Class<?> linkedObjectType, Long requestedParent);

    Long getCascadedProductionStateId(Class<?> linkedObjectType, Long requestedParent);

    //Long getSandBoxVersionId(EntityManager entityManager, Class<?> linkedObjectType, Long requestedParent, Boolean includeSandBoxInheritance);

    //Long getCascadedProductionStateId(EntityManager em, Class<?> linkedObjectType, Long requestedParent,
                                      //Boolean includeSandBoxInheritance);

    //Long getCombinedSandBoxVersionId(Class<?> linkedObjectType, Long requestedParent);

    //Long getCombinedSandBoxVersionId(Class<?> linkedObjectType, Long requestedParent, Boolean includeSandBoxInheritance);

    OriginalIdResponse getOriginalId(Class<?> type, Long id);

    OriginalIdResponse getProductionOriginalId(Class<?> type, Long id);

    Long getOriginalId(Object test);

//    /**
//     * Setup basic required fields for sandbox support
//     *
//     * @param clone the entity instance to setup
//     * @param em the Entity Manager
//     */
//    void setupSandBoxState(Object clone, EntityManager em);

//    /**
//     * Archive an object so that it is no longer recognized
//     * by the sandbox support
//     *
//     * @param start the object to archive
//     * @param em the Entity Manager
//     */
//    void archiveObject(Object start, EntityManager em);

//    /**
//     * Retrieve the field names related to sandbox support
//     *
//     * @return the sandbox support fields
//     */
//    String[] getSandBoxDiscriminatorFieldList();

    boolean isSandBoxable(String className);

    boolean isPromote();

    boolean isReject();

    void optionallyIncludeDeletedItemsInQueriesAndCollections(Runnable runnable, boolean includeDeleted);

    Long getProductionRecordIdIfApplicable(EntityManager em, Object startFieldValue);

    public class OriginalIdResponse {

        private boolean recordFound = false;
        private Long originalId;

        public boolean isRecordFound() {
            return recordFound;
        }

        public void setRecordFound(boolean recordFound) {
            this.recordFound = recordFound;
        }

        public Long getOriginalId() {
            return originalId;
        }

        public void setOriginalId(Long originalId) {
            this.originalId = originalId;
        }
    }
}
