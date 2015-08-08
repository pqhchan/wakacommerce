package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.enumeration.domain.DataDrivenEnumerationValueImpl;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationDataDrivenEnumeration {

    /**
     * <p>Optional - 只有当目标实体不是DataDrivenEnumerationValueImpl的时候才需要指定 
     */
    Class<?> optionListEntity() default DataDrivenEnumerationValueImpl.class;

    /**
     * <p>Optional - only required if it is desirable to filter the list of items returned from the query for the optionListEntity. This is useful if you
     * only want to present part of a table as options in the data driven enumeration. Note, when configuring for optionListEntity
     * equals DataDrivenEnumerationValueImpl, it is generally appropriate to denote:</p>
     *
     * <p>@OptionFilterParam(param="type.key", value="[the key value of the DataDrivenEnumerationImpl instance]", paramType=[your param type])</p>
     *
     * @return list of parameters with which to filter the option list
     */
    OptionFilterParam[] optionFilterParams() default {};

    /**
     * <p>Optional - only required if the optionListEntity is not DataDrivenEnumerationValueImpl.</p>
     *
     * <p>Specify the field in the target entity that contains the value that will be persisted into this annotated field.</p>
     *
     * @return the value field in the target entity
     */
    String optionValueFieldName() default "";

    /**
     * <p>Optional - only required if the optionListEntity is not DataDrivenEnumerationValueImpl.</p>
     *
     * <p>Specify the field in the target entity that contains the display value that will be shown to the user in the dropdown field</p>
     *
     * @return the display field in the target entity
     */
    String optionDisplayFieldName() default "";

    /**
     * <p>Optional - 是否允许用户在dropdown中编辑。如果设为true的话，用户就既可以输入自己的值也可以从选择一个存在的值。
     * 只有当optionListEntity没有指定为DataDrivenEnumerationValueImpl的时候才需要设置该值，
     * 因为DataDrivenEnumerationValueImpl中已经有这样的属性了（modifiable）
     */
    boolean optionCanEditValues() default false;
}
