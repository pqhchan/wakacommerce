--
-- This file is responsible for creating the content structure which includes page-types (e.g. template
-- mappings) and structured-content-types (e.g. ad placement among other things).
--

--
-- Load Currencies:  Your site will need currencies defined in order to use price lists.
-- Currencies need to be defined before locale if they are using a currency code.
-- i18n standard abbreviations should be used.
--
INSERT INTO BLC_CURRENCY(CURRENCY_CODE, FRIENDLY_NAME, DEFAULT_FLAG) VALUES('USD', 'US Dollar', TRUE);

--
-- Load Locales:  Your site must have at least one Locale with DEFAULT_FLAG set to TRUE
-- You can have as many locales as you like. Currency can be set  to null if none have
-- been defined. i18n standard abbreviations should be used.
--
INSERT INTO BLC_LOCALE (LOCALE_CODE, DEFAULT_FLAG, FRIENDLY_NAME, CURRENCY_CODE) VALUES ('en_US', TRUE, 'English US', 'USD');
INSERT INTO BLC_LOCALE (LOCALE_CODE, DEFAULT_FLAG, FRIENDLY_NAME, CURRENCY_CODE) VALUES ('en', FALSE, 'English', 'USD');

--
-- The following items create page templates.    
-- In the example below, there is only one page templates "basic" providing a header, footer, and sidebar around the content
-- The full path to the template is .../WEB_INF/jsp/templates/content/default.html
INSERT INTO BLC_PAGE_TMPLT (PAGE_TMPLT_ID, TMPLT_NAME, TMPLT_DESCR, TMPLT_PATH) VALUES (1, 'Basic Template', 'This template provides a basic layout with header and footer surrounding the content and title.', '/content/default') ;


--
-- Field groups define a list of dynamic fields.    Field groups can be associated with page
-- templates or structured content types.    The field group below defines two fields named
-- "body", which is a rich text edit field, and "title", which is a string field.
-- This will tell the Broadleaf admin how to generate the view for a given template
--
INSERT INTO BLC_FLD_GROUP(FLD_GROUP_ID, NAME, INIT_COLLAPSED_FLAG) VALUES (1, 'Content', FALSE);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (3, 'title', '标题', 'STRING', FALSE, null, null, null, '*', FALSE, NULL, FALSE, 1, 0);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (2, 'body', '内容', 'HTML', FALSE, null, null, null, '*', FALSE, NULL, FALSE, 1, 1);

--
-- Map both templates to the field groups
--
INSERT INTO BLC_PGTMPLT_FLDGRP_XREF(PG_TMPLT_FLD_GRP_ID, PAGE_TMPLT_ID, FLD_GROUP_ID, GROUP_ORDER) VALUES (1, 1,1,0);

--  
-- NONE template
-- 
-- note id 2 is in i18n file
INSERT INTO BLC_PAGE_TMPLT (PAGE_TMPLT_ID, TMPLT_NAME, TMPLT_DESCR, TMPLT_PATH) VALUES (-3, 'NONE', 'Outputs the body field.', 'NONE') ;
INSERT INTO BLC_FLD_GROUP(FLD_GROUP_ID, NAME, INIT_COLLAPSED_FLAG) VALUES (-3, 'None', FALSE);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (-1, 'body', 'File Contents', 'HTML', FALSE, null, null, null, '*', FALSE, NULL, FALSE, -3, 0);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (-2, 'plainText', 'Plain Text', 'BOOLEAN', FALSE, null, null, null, '*', FALSE, NULL, FALSE, -3, 1);

INSERT INTO BLC_PGTMPLT_FLDGRP_XREF(PG_TMPLT_FLD_GRP_ID,PAGE_TMPLT_ID, FLD_GROUP_ID, GROUP_ORDER) VALUES (2, -3, -3 ,0);

-----------------------------------------------------------------------------------------------------------------------------------
-- Structured Content Step 1:   Create Structured Content Field Groups
-----------------------------------------------------------------------------------------------------------------------------------
-- Create Ad Fields - Defining the ad field group as a group of two fields: Image URL and Target URL
INSERT INTO BLC_FLD_GROUP(FLD_GROUP_ID, NAME, INIT_COLLAPSED_FLAG) VALUES (4, 'Ad Fields', FALSE);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (7, 'imageUrl', '图片URL', 'STRING', FALSE, null, null, 150, '*', FALSE, NULL, FALSE, 4, 0);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (8, 'targetUrl', '目标URL', 'STRING', FALSE, null, null, 150, '*', FALSE, NULL, FALSE, 4, 1);

-- Create HTML Fields - Defining the message field group as a single field called messageText
INSERT INTO BLC_FLD_GROUP(FLD_GROUP_ID, NAME, INIT_COLLAPSED_FLAG) VALUES (5, 'HTML Fields', FALSE);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (10, 'htmlContent', 'HTML内容', 'HTML', FALSE, null, null, null, '*', FALSE, NULL, FALSE, 5, 0);

-- Create Message Fields - Defining the message field group as a single field called messageText
INSERT INTO BLC_FLD_GROUP(FLD_GROUP_ID, NAME, INIT_COLLAPSED_FLAG) VALUES (6, 'Message Fields', FALSE);
INSERT INTO BLC_FLD_DEF(FLD_DEF_ID, NAME, FRIENDLY_NAME, FLD_TYPE, HIDDEN_FLAG, VLDTN_REGEX, VLDTN_ERROR_MSSG_KEY, MAX_LENGTH, COLUMN_WIDTH, TEXT_AREA_FLAG, ENUM_ID, ALLOW_MULTIPLES, FLD_GROUP_ID, FLD_ORDER) VALUES (9, 'messageText', '消息文本', 'STRING', FALSE, null, null, 150, '*', FALSE, NULL, FALSE, 6, 0);

-----------------------------------------------------------------------------------------------------------------------------------
-- Structured Content Step 2:   Create Templates.     The examples below create field templates for Ads, Messages, and HTML blocks.
-----------------------------------------------------------------------------------------------------------------------------------
INSERT INTO BLC_SC_FLD_TMPLT(SC_FLD_TMPLT_ID, NAME) VALUES(1, 'Ad Template');
INSERT INTO BLC_SC_FLD_TMPLT(SC_FLD_TMPLT_ID, NAME) VALUES(2, 'HTML Template');
INSERT INTO BLC_SC_FLD_TMPLT(SC_FLD_TMPLT_ID, NAME) VALUES(3, 'Message Template');


-----------------------------------------------------------------------------------------------------------------------------------
-- Structured Content Step 3:   Add Field Groups to Templates
-----------------------------------------------------------------------------------------------------------------------------------
INSERT INTO BLC_SC_FLDGRP_XREF(SC_FLD_TMPLT_ID, FLD_GROUP_ID, GROUP_ORDER) VALUES (1,4,0);
INSERT INTO BLC_SC_FLDGRP_XREF(SC_FLD_TMPLT_ID, FLD_GROUP_ID, GROUP_ORDER) VALUES (2,5,0);
INSERT INTO BLC_SC_FLDGRP_XREF(SC_FLD_TMPLT_ID, FLD_GROUP_ID, GROUP_ORDER) VALUES (3,6,0);

-----------------------------------------------------------------------------------------------------------------------------------
-- Structured Content Step 4:   Create Types (These represent areas on a page or general types:  e.g 'Homepage Banner Ad')
-----------------------------------------------------------------------------------------------------------------------------------
INSERT INTO BLC_SC_TYPE (SC_TYPE_ID, NAME, DESCRIPTION, SC_FLD_TMPLT_ID) VALUES (1, 'Homepage Banner Ad', "首页横幅广告", 1);
INSERT INTO BLC_SC_TYPE (SC_TYPE_ID, NAME, DESCRIPTION, SC_FLD_TMPLT_ID) VALUES (2, 'Homepage Middle Promo Snippet', "首页中部促销", 2);
INSERT INTO BLC_SC_TYPE (SC_TYPE_ID, NAME, DESCRIPTION, SC_FLD_TMPLT_ID) VALUES (3, 'Homepage Featured Products Title', "首页推荐产品标题", 3);
INSERT INTO BLC_SC_TYPE (SC_TYPE_ID, NAME, DESCRIPTION, SC_FLD_TMPLT_ID) VALUES (4, 'Right Hand Side Banner Ad', "右栏横幅广告", 1);

