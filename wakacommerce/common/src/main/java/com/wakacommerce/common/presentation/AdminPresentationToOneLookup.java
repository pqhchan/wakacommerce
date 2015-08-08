package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.client.LookupType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationToOneLookup {

    /**
     * <p>Optional - 只有当要显示的属性不是"name"的时候，才需要指定</p>
     *
     * <p>指定关联实体的一个属性，这个属性会在前端展示主实体的时候，作为关联实体的代表显示在表单上
     *
     * @return 关联实体的某个属性
     */
    String lookupDisplayProperty() default "";

    /**
     * <p>Optional - only required if you need to specially handle crud operations for this
     * specific collection on the server</p>
     *
     * <p>Custom string values that will be passed to the server during Read and Inspect operations on the
     * entity lookup. This allows for the creation of a custom persistence handler to handle both
     * inspect and fetch phase operations. Presumably, one could use this to
     * somehow filter the list of records shown when the user interacts with the lookup widget in the
     * admin UI.</p>
     *
     * @return the custom string array to pass to the server during CRUD operations
     */
    String[] customCriteria() default {};

    /**
     * <p>Optional - only required if you want to make the field ignore caching</p>
     *
     * <p>Explicitly specify whether or not this field will use server-side
     * caching during inspection</p>
     *
     * @return whether or not this field uses caching
     */
    boolean useServerSideInspectionCache() default true;
    
    /**
     * <p>Optional - 只有当你想要用下拉框而不是标准的模式对话框的时候，才需要指定</p>
     *
     * @return
     */
    LookupType lookupType() default LookupType.STANDARD;
    
    
    /**
     * <p>Optional - by setting this value to true, the admin will identify
     * the properties that are inside the target of this to-one field. </p>
     * 
     * <p>Typically, this is done if you want to expose a certain field as an
     * AdminPresentationToOneLookup but also allow filtering on a property that
     * resides inside of the target of this lookup.</p>
     * 
     * @return whether or not to force population of the child properties
     */
    boolean forcePopulateChildProperties() default false;
    
    /**
     * <p>Optional - by setting this value to true, the admin will utilize a typeahead
     * based lookup to search for the associated entity. When the value is set to
     * false, the admin will utilize a model that can be used to
     * search to the server for the associated entity.</p>
     * 
     * @return whether or not to enable the typeahead lookup feature for this field
     */
    boolean enableTypeaheadLookup() default false;

}
