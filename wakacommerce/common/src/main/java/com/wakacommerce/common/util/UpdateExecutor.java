
package com.wakacommerce.common.util;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

/**
 *
 * @ hui
 */
public class UpdateExecutor {

    public static int executeUpdateQuery(EntityManager em, String template, Object[] params, Type[] types, List<Long> ids) {
        int response = 0;
        List<Long[]> runs = buildRuns(ids);
        for (Long[] run : runs) {
            String queryString = String.format(template, buildInClauseTemplate(run.length));
            SQLQuery query = em.unwrap(Session.class).createSQLQuery(queryString);
            int counter = 0;
            if (!ArrayUtils.isEmpty(params)) {
                for (Object param : params) {
                    query.setParameter(counter, param, types[counter]);
                    counter++;
                }
            }
            for (Long id : run) {
                query.setLong(counter, id);
                counter++;
            }
            response += query.executeUpdate();
        }
        return response;
    }

    private static String buildInClauseTemplate(int length) {
        String[] temp = new String[length];
        Arrays.fill(temp, "?");
        return StringUtils.join(temp, ",");
    }

    private static List<Long[]> buildRuns(List<Long> ids) {
        List<Long[]> runs = new ArrayList<Long[]>();
        Long[] all = ids.toArray(new Long[ids.size()]);
        int test = all.length;
        int pos = 0;
        boolean eof = false;
        while (!eof) {
            int arraySize;
            if (test < 800) {
                arraySize = test;
                eof = true;
            } else {
                arraySize = 800;
                test -= arraySize;
            }
            Long[] temp = new Long[arraySize];
            System.arraycopy(all, pos, temp, 0, arraySize);
            pos += arraySize;
            runs.add(temp);
        }
        return runs;
    }
}
