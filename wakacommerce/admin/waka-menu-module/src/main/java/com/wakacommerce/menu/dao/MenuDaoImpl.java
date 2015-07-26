
package com.wakacommerce.menu.dao;

import org.hibernate.ejb.QueryHints;
import org.springframework.stereotype.Repository;

import com.wakacommerce.common.util.dao.TypedQueryBuilder;
import com.wakacommerce.menu.domain.Menu;
import com.wakacommerce.menu.domain.MenuImpl;
import com.wakacommerce.menu.domain.MenuItem;
import com.wakacommerce.menu.domain.MenuItemImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository("blMenuDao")
public class MenuDaoImpl implements MenuDao {
    
    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Override
    public List<Menu> readAllMenus() {
        TypedQuery<Menu> q = new TypedQueryBuilder<Menu>(Menu.class, "m")
                .toQuery(em);
        return q.getResultList();
    }

    @Override
    public List<MenuItem> readAllMenuItems() {
        TypedQuery<MenuItem> q = new TypedQueryBuilder<MenuItem>(MenuItem.class, "mi")
                .toQuery(em);
        return q.getResultList();
    }

    @Override
    public Menu readMenuById(Long menuId) {
        return em.find(MenuImpl.class, menuId);
    }

    @Override
    public MenuItem readMenuItemById(Long menuItemId) {
        return em.find(MenuItemImpl.class, menuItemId);
    }

    @Override
    public Menu readMenuByName(String menuName) {
        TypedQuery<Menu> query = em.createNamedQuery("BC_READ_MENU_BY_NAME", Menu.class);
        query.setParameter("menuName", menuName);
        query.setHint(QueryHints.HINT_CACHEABLE, true);
        query.setHint(QueryHints.HINT_CACHE_REGION, "query.Cms");

        List<Menu> results = query.getResultList();
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Menu saveMenu(Menu menu) {
        return em.merge(menu);
    }

    @Override
    public MenuItem saveMenuItem(MenuItem menuItem) {
        return em.merge(menuItem);
    }

}
