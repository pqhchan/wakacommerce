
package com.wakacommerce.openadmin.server.security.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.util.dao.TypedQueryBuilder;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * 
 *jfischer
 *
 */
@Repository("blAdminUserDao")
public class AdminUserDaoImpl implements AdminUserDao {

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public void deleteAdminUser(AdminUser user) {
        if (!em.contains(user)) {
            user = em.find(entityConfiguration.lookupEntityClass("com.wakacommerce.openadmin.server.security.domain.AdminUser", AdminUser.class), user.getId());
        }
        em.remove(user);
    }

    public AdminUser readAdminUserById(Long id) {
        return em.find(entityConfiguration.lookupEntityClass("com.wakacommerce.openadmin.server.security.domain.AdminUser", AdminUser.class), id);
    }
    
    @Override
    public List<AdminUser> readAdminUsersByIds(Set<Long> ids) {
        TypedQueryBuilder<AdminUser> tqb = new TypedQueryBuilder<AdminUser>(AdminUser.class, "au");

        if (ids != null && !ids.isEmpty()) {
            tqb.addRestriction("au.id", "in", ids);
        }

        TypedQuery<AdminUser> query = tqb.toQuery(em);
        return query.getResultList();
    }

    public AdminUser saveAdminUser(AdminUser user) {
        if (em.contains(user) || user.getId() != null) {
            return em.merge(user);
        } else {
            em.persist(user);
            return user;
        }
    }

    public AdminUser readAdminUserByUserName(String userName) {
        TypedQuery<AdminUser> query = em.createNamedQuery("BC_READ_ADMIN_USER_BY_USERNAME", AdminUser.class);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        query.setParameter("userName", userName);
        List<AdminUser> users = query.getResultList();
        if (users != null && !users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public List<AdminUser> readAllAdminUsers() {
        TypedQuery<AdminUser> query = em.createNamedQuery("BC_READ_ALL_ADMIN_USERS", AdminUser.class);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        return query.getResultList();
    }

    @Override
    public List<AdminUser> readAdminUserByEmail(String emailAddress) {
        TypedQuery<AdminUser> query = em.createNamedQuery("BC_READ_ADMIN_USER_BY_EMAIL", AdminUser.class);
        query.setParameter("email", emailAddress);
        return query.getResultList();
    }
}
