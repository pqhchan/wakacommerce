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
     * <p>Optional - only required if you want to display a friendly name to the user</p>
     * 
     * <p>The friendly name to present to a user for this field in a GUI. If supporting i18N,
     * the friendly name may be a key to retrieve a localized friendly name using
     * the GWT support for i18N.</p>
     *
     * @return the friendly name
     */
    String friendlyName() default "";
    
    /**
     * <p>Optional - only required if you want to order the appearance of this field in the UI</p>
     *
     * <p>The order in which this field will appear in a GUI relative to other fields from the same class</p>
     * 
     * @return the display order
     */
    int order() default 99999;
    
    /**
     * <p>Optional - required only if you want to order the appearance of this field as it relates to other fields in a grid.
     * 
     * <p>Note that this field will only be relevant if {@link #prominent()} is also set to true.
     * 
     * @return
     */
    int gridOrder() default 9999;

    /**
     * <p>Optional - only required if you want to restrict the visibility of this field in the admin tool
     *
     * <p>Describes how the field is shown in admin GUI.
     *
     * @return whether or not to hide the form field.
     */
    VisibilityEnum visibility() default VisibilityEnum.VISIBLE_ALL;
    
    /**
     * <p>Optional - only required if you want to explicitly specify the field type. This
     * value is normally inferred by the system based on the field type in the entity class.
     *
     * <p>Explicity specify the type the GUI should consider this field
     * Specifying UNKNOWN will cause the system to make its best guess
     * 
     * @return the field type
     */
    SupportedFieldType fieldType() default SupportedFieldType.UNKNOWN;
    
    /**
     * <p>Optional - only required if you want to specify a grouping for this field
     *
     * <p>Specify a GUI grouping for this field. Fields in the same group 
     * will be visually grouped together in the GUI. If supporting I18N, this 
     * can also be a key to retrieve a localized String
     * 
     * @return the group for this field
     */
    String group() default "General";
    
    /**
     * <p>Optional - only required if you want to order the appearance of groups in the UI
     *
     * <p>Specify an order for this group. Groups will be sorted in the resulting
     * form in ascending order based on this parameter.
     * 
     * @return the order for this group
     */
    int groupOrder() default 99999;

    /**
     * <p>Optional - only required if you want the field to appear under a different tab
     * 
     * <p>Specify a GUI tab for this field
     * 
     * @return the tab for this field
     */
    String tab() default "General";

    /**
     * <p>Optional - only required if you want to order the appearance of the tabs in the UI
     * 
     * <p>Specify an order for this tab. Tabs will be sorted in 
     * ascending order based on this parameter.
     * 
     * @return the order for this tab
     */
    int tabOrder() default 100;

    /**
     * <p>Optional - only required if you want to give the user extra room to enter a value
     * for this field in the UI
     *
     * <p>If the field is a string, specify that the GUI
     * provide a text area
     * 
     * @return is a text area field
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
     * <p>Optional - only required if you want to explicitly control column width
     * for this field in a grid in the admin tool
     *
     * <p>Specify the column space this field will occupy in grid widgets.
     * This value can be an absolute integer or a percentage. A value
     * of "*" will make this field use up equally distributed space.
     * 
     * @return the space utilized in grids for this field
     */
    String columnWidth() default "*";
    
    /**
     * <p>Optional - only required for WAKA_ENUMERATION field types
     *
     * <p>For fields with a SupportedFieldType of WAKA_ENUMERATION,
     * you must specify the fully qualified class name of the Waka Enumeration here.
     * 
     * @return waka enumeration class name
     */
    String wakaEnumeration() default "";
    
    /**
     * <p>Optional - only required if you want to make the field immutable
     *
     * <p>Explicityly specify whether or not this field is mutable.
     * 
     * @return whether or not this field is read only
     */
    boolean readOnly() default false;
    
    /**
     * <p>Optional - only required if you want to provide validation for this field
     *
     * <p>Specify the validation to use for this field in the admin
     * 
     * @return the configuration for the validation
     */
    ValidationConfiguration[] validationConfigurations() default {};

    /**
     * <p>Optional - only required if you want to explicitly make a field required. This
     * setting is normally inferred by the JPA annotations on the field.
     *
     * <p>Specify whether you would like the admin to require this field,
     * even if it is not required by the ORM.
     *
     * @return the required override enumeration
     */
    RequiredOverride requiredOverride() default RequiredOverride.IGNORED;

    /**
     * <p>Optional - only required if you want to explicitly exclude this field from
     * dynamic management by the admin tool
     *
     * <p>Specify if this field should be excluded from inclusion in the
     * admin presentation layer
     *
     * @return whether or not the field should be excluded
     */
    boolean excluded() default false;
    
    /**
     * <p>Optional - only required if you want to provide a tooltip for the field
     *
     * <p>Helpful tooltip to be displayed when the admin user hovers over the field.
     * This can be localized by providing a key which will use the GWT
     * support for i18N.
     * 
     */
    String tooltip() default "";
    
    /**
     * <p>Optional - only required if you want to provide help text for this field
     *
     * <p>On the form for this entity, this will show a question
     * mark icon next to the field. When the user clicks on the icon, whatever
     * HTML that is specified in this helpText is shown in a popup.
     * <p>
     * For i18n support, this can also be a key to a localized version of the text
     * <p>
     * Reference implementation: http://www.smartclient.com/smartgwt/showcase/#form_details_hints
     * 
     */
    String helpText() default "";
    
    /**
     * <p>Optional - only required if you want to provide a hint for this field
     *
     * <p>Text to display immediately to the right of a form field. For instance, if the user needs
     * to put in a date, the hint could be the format the date needs to be in like 'MM/YYYY'.
     * 
     * <p>For i18n support, this can also be a key to a localized version of the text
     * 
     * <p>Reference implementation: http://www.smartclient.com/smartgwt/showcase/#form_details_hints
     */
    String hint() default "";

    /**
     * <p>Optional - propertyName , only required if you want hide the field based on this property's value</p>
     *
     * <p>If the property is defined and found to be set to false, in the AppConfiguraionService, 
     * then this field will be excluded in the admin presentation layer</p>
     *
     * @return name of the property 
     */
    String showIfProperty() default "";
    
    /**
     * <p>Optional - only required if the fieldType is SupportedFieldType.RULE_SIMPLE or SupportedFieldType.RULE_COMPLEX</p>
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
     * <p>Optional - only required if you want to display a default value to the user when adding a new entity</p>
     *
     * <p>The default value to present to a user for this field when adding a new entity.</p>
     *
     * <p>Default values on <code>Boolean</code> require {@code "true"} or {@code "false"}.</p>
     * <p>Default values on <code>Date</code> support the string {@code "today"} and strings with the
     * format of <b>{@code yyyy.MM.dd HH:mm:ss}</b> (e.g. {@code "2020.02.05 22:11:05"}).</p>
     *
     * @return the defaultValue set for the field.
     */
    String defaultValue() default "";
}
