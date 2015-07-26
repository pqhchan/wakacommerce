
package com.wakacommerce.openadmin.server.util;

import java.util.LinkedHashMap;

import com.wakacommerce.openadmin.dto.ClassTree;

/**
 *Elbert Bautista (elbertbautista)
 *
 * Utility class to convert the Polymorphic ClassTree into a Map
 */
public class PolymorphicEntityMapUtil {

    public LinkedHashMap<String, String> convertClassTreeToMap(ClassTree polymorphicEntityTree) {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(polymorphicEntityTree.getRight()/2);
        buildPolymorphicEntityMap(polymorphicEntityTree, map);
        return map;
    }

    protected void buildPolymorphicEntityMap(ClassTree entity, LinkedHashMap<String, String> map) {
        String friendlyName = entity.getFriendlyName();
        if (friendlyName != null && !friendlyName.equals("")) {

            //TODO: fix this for i18N
            //check if the friendly name is an i18N key
            //String val = BLCMain.getMessageManager().getString(friendlyName);
            //if (val != null) {
            //    friendlyName = val;
            //}

        }
        map.put(entity.getFullyQualifiedClassname(), friendlyName!=null?friendlyName:entity.getName());
        for (ClassTree child : entity.getChildren()) {
            buildPolymorphicEntityMap(child, map);
        }
    }

}