
package com.wakacommerce.core.workflow;

import java.util.List;

import com.wakacommerce.core.order.service.call.ActivityMessageDTO;


public interface ActivityMessages {

    List<ActivityMessageDTO> getActivityMessages();

    void setActivityMessages(List<ActivityMessageDTO> activityMessages);
}
