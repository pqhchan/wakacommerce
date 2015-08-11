
  
(function($, BLCAdmin) {

    BLCAdmin.menuItem = {

        addOnChangeTriggers : function($form) {
            $form.find("#fields\\'type\\'\\.value").on('change', function() {
                BLCAdmin.menuItem.initializeMenuItemFormFields($form);
            });
        },

        /**
         * Show or hide certain Menu Item fields based on the currently selected Menu Item Type
         */
        initializeMenuItemFormFields : function($form) {

            var menuItemType = $("#fields\\'type\\'\\.value", $form).val()

            // Initialize relevant fields
            var $actionUrl        = $form.find('#field-actionUrl');
            var $image            = $form.find('#field-image');
            var $altText          = $form.find('#field-altText');
            var $linkedMenu       = $form.find('#field-linkedMenu');
            var $linkedCategory   = $form.find('#field-linkedCategory');
            var $linkedProduct    = $form.find('#field-linkedProduct');
            var $linkedPage       = $form.find('#field-linkedPage');
            var $customHtml       = $form.find('#field-customHtml');

            // Hide everything
            $actionUrl.addClass('hidden');
            $image.addClass('hidden');
            $altText.addClass('hidden');
            $linkedMenu.addClass('hidden');
            $linkedPage.addClass('hidden');
            $customHtml.addClass('hidden');

            switch (menuItemType) {
                case "CATEGORY":
                    $actionUrl.removeClass('hidden');
                    break;
                case "SUBMENU":
                    $actionUrl.removeClass('hidden');
                    $linkedMenu.removeClass('hidden');
                    break;
                case "PRODUCT":
                    $actionUrl.removeClass('hidden');
                    break;
                case "PAGE":
                    $linkedPage.removeClass('hidden');
                    break;
                case "LINK":
                    $actionUrl.removeClass('hidden');
                    $image.removeClass('hidden');
                    $altText.removeClass('hidden');
                    break;
                case "CUSTOM":
                    $customHtml.removeClass('hidden');
                    break;
            }
        }
    };

    BLCAdmin.addInitializationHandler(function($container) {
        var $form = $container.closest('form.menu-item-form');
        if ($form) {
            BLCAdmin.menuItem.addOnChangeTriggers($form);
            BLCAdmin.menuItem.initializeMenuItemFormFields($form);
        }
    });


})(jQuery, BLCAdmin);
