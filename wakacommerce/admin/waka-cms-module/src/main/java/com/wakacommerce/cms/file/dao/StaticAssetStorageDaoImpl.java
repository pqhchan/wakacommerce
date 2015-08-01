package com.wakacommerce.cms.file.dao;

import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.wakacommerce.cms.file.domain.StaticAssetStorage;
import com.wakacommerce.cms.file.domain.StaticAssetStorageImpl;
import com.wakacommerce.common.persistence.EntityConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("blStaticAssetStorageDao")
public class StaticAssetStorageDaoImpl implements StaticAssetStorageDao {

    @PersistenceContext(unitName = "blCMSStorage")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public StaticAssetStorage create() {
        return (StaticAssetStorage) entityConfiguration.createEntityInstance("com.wakacommerce.cms.file.domain.StaticAssetStorage");
    }

    @Override
    public Blob createBlob(MultipartFile uploadedFile) throws IOException {
        return createBlob(uploadedFile.getInputStream(), uploadedFile.getSize());
    }
    
    @Override
    public Blob createBlob(InputStream uploadedFileInputStream, long fileSize) throws IOException {
        InputStream inputStream = uploadedFileInputStream;
        //We'll work with Blob instances and streams so that the uploaded files are never read into memory
        return ((HibernateEntityManager) em).getSession().getLobHelper().createBlob(inputStream, fileSize);
    }

    @Override
    public StaticAssetStorage readStaticAssetStorageById(Long id) {
        return em.find(StaticAssetStorageImpl.class, id);
    }

    @Override
    public StaticAssetStorage readStaticAssetStorageByStaticAssetId(Long id) {
        Query query = em.createNamedQuery("BC_READ_STATIC_ASSET_STORAGE_BY_STATIC_ASSET_ID");
        query.setParameter("id", id);

        return (StaticAssetStorage) query.getSingleResult();
    }

    @Override
    public StaticAssetStorage save(StaticAssetStorage assetStorage) {
        if (em.contains(assetStorage)) {
            return em.merge(assetStorage);
        }
        em.persist(assetStorage);
        em.flush();
        return assetStorage;
    }

    @Override
    public void delete(StaticAssetStorage assetStorage) {
        if (!em.contains(assetStorage)) {
            assetStorage = readStaticAssetStorageById(assetStorage.getId());
        }
        em.remove(assetStorage);
    }
}
