(function($, BLCAdmin) {
    
    BLCAdmin.product = {
            
        refreshSkusGrid : function($container, listGridUrl) {
            BLC.ajax({
                url : listGridUrl,
                type : "GET"
            }, function(data) {
                BLCAdmin.listGrid.replaceRelatedListGrid($(data));
            });
        }

    };
    
})(jQuery, BLCAdmin);

$(document).ready(function() {
    
    $('body').on('click', 'button.generate-skus', function() {
        var $container = $(this).closest('div.listgrid-container');
        
        BLC.ajax({
            url : $(this).data('actionurl'),
            type : "GET"
        }, function(data) {
            var alertType = data.error ? 'alert' : '';
            
            BLCAdmin.listGrid.showAlert($container, data.message, {
                alertType: alertType,
                clearOtherAlerts: true
            });
            
            if (data.skusGenerated > 0) {
                BLCAdmin.product.refreshSkusGrid($container, data.listGridUrl);
            }
        });
        
        return false;
    });

    $('body').on('change', "input[name=\"fields['defaultCategory'].value\"]", function(event, fields) {
        var $fieldBox = $(event.target).closest('.field-box');
        var $prefix = $fieldBox.find('input.generated-url-prefix');

        if (!$prefix.length) {
            $prefix = $fieldBox.append($('<input>', {
                'type'  : "hidden",
                'class' : "generated-url-prefix"
            })).find('input.generated-url-prefix');
        }
        
        $prefix.val(fields['url']);
    });
});
