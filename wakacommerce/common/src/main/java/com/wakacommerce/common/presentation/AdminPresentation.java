package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentation {
    
    /**
     * <p>Optional - 需要给用户显示更友好的名称时可以指定该值
     */
    String friendlyName() default "";
    
    /**
     * <p>Optional - 需要指定该字段在表单的分组里的顺序时可以指定该值
     */
    int order() default 99999;
    
    /**
     * <p>Optional - 需要指定该字段在表格里的顺序时可以指定该值
     * 
     * <p>只有当{@link #prominent()}设置成true的时候，该值才会被参考
     */
    int gridOrder() default 9999;

    /**
     * <p>Optional - 只有当你想要限制该段的可显示性的时候才需要指定该值
     */
    VisibilityEnum visibility() default VisibilityEnum.VISIBLE_ALL;
    
    /**
     * <p>Optional - 只有当你想显式的指定该字段的类型的时候，才需要设置该值。
     * 
     * <p>默认的UNKNOWN，会让系统自行分析
     * 
     * @return
     */
    SupportedFieldType fieldType() default SupportedFieldType.UNKNOWN;
    
    /**
     * <p>Optional - 分组名
     */
    String group() default "General";
    
    /**
     * <p>Optional - 分组排序
     */
    int groupOrder() default 99999;

    /**
     * <p>Optional - 标签页名称
     */
    String tab() default "General";

    /**
     * <p>Optional - 标签页顺序
     */
    int tabOrder() default 100;

    /**
     * <p>Optional - 需要给用户更大的输入空间时，可以指定该值
     */
    boolean largeEntry() default false;
    
    /**
     * <p>Optional - only required if you want this field to appear as one of the default
     * columns in a grid in the admin tool
     *
     * <p>Provide a hint to the GUI about the prominence of this field.
     * For example, prominent fields will show up as a column in any
     * list grid in the admin that displays this entity.
     * 
     * @return whether or not this is a prominent field
     */
    boolean prominent() default false;
    
    /**
     * <p>Optional - 只有当你想显式的控制列宽的时候，才需要指定
     *
     * <p>可以是整数或百分比, "*"表示对表格等分
     * 
     * @return
     */
    String columnWidth() default "*";
    
    /**
     * <p>Optional - 只有当字段类型是WAKA_ENUMERATION时，才需要指定
     *
     * <p>对于类型WAKA_ENUMERATION的字段, 必须在这里指定枚举类的完全限定名
     * 
     * @return
     */
    String wakaEnumeration() default "";
    
    /**
     * <p>Optional - 是否是只读字段（不可修改）
     */
    boolean readOnly() default false;
    
    /**
     * <p>Optional - 对该字段的验证
     */
    ValidationConfiguration[] validationConfigurations() default {};

    /**
     * <p>Optional - 显式地指定该字段是必须的，通常系统可以根据JPA来推导
     */
    RequiredOverride requiredOverride() default RequiredOverride.IGNORED;

    /**
     * <p>Optional - 是否排除该字段
     */
    boolean excluded() default false;
    
    /**
     * <p>Optional - 只有当你想为该字段提供tooltip的时候，才需要指定
     *
     * <p>如果设置了该值，那么当鼠标经过表单字段时，这里设置的文本就会显示出来
     * 
     */
    String tooltip() default "";
    
    /**
     * <p>Optional - 只有当你想为该字段提供help的时候，才需要指定
     *
     * <p>如果设置了该值，那么在表单字段的旁边就会显示一个问号。当用户点击该问号时，这里设置的文本就会显示出来，支持html
     */
    String helpText() default "";
    
    /**
     * <p>Optional - 只有当你想为该字段提供hint的时候，才需要指定
     *
     * <p>hint文本会直接显示在表单字段的右边。例如，假设某个字段需要用户输入一个日期, 那么就可以在该
     * 字段的旁边放上一个格式文本，比方说'MM/YYYY'，来提示用户输入正确的值，这个提示文本就是hint
     * 
     */
    String hint() default "";

    /**
     * <p>Optional - 只有当字段类型是RULE_SIMPLE或RULE_COMPLEX时，才需要指定该值</p>
     *
     * <p>Identifies the type for which this rule builder is targeted. See <tt>RuleIdentifier</tt> for a list of
     * identifier types supported out-of-the-box.
     * 
     * <p>Note - one of the main uses of this value is to help identify
     * the proper <tt>RuleBuilderService</tt> instance to generate the correct field value options for this rule builder.</p>
     *
     * @return The identifier value that denotes what type of rule builder this is - especially influences the fields that are available in the UI
     */
    String ruleIdentifier() default "";
    
    /**
     * <p>Optional - 新建记录时，如果想要给该字段显示一个默认值，可以指定该值
     * 
     * <p>Default values on <code>Boolean</code> require {@code "true"} or {@code "false"}.</p>
     * <p>Default values on <code>Date</code> support the string {@code "today"} and strings with the
     * format of <b>{@code yyyy.MM.dd HH:mm:ss}</b> (e.g. {@code "2020.02.05 22:11:05"}).</p>
     *
     * @return the defaultValue set for the field.
     */
    String defaultValue() default "";
}
