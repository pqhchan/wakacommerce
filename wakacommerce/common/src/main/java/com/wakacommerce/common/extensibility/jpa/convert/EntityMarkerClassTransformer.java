
package com.wakacommerce.common.extensibility.jpa.convert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.common.extensibility.jpa.MergePersistenceUnitManager;
import com.wakacommerce.common.extensibility.jpa.copy.AbstractClassTransformer;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyIgnorePattern;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;

import javax.annotation.Resource;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 *
 * @ hui
 */
public class EntityMarkerClassTransformer extends AbstractClassTransformer implements BroadleafClassTransformer {
    protected static final Log LOG = LogFactory.getLog(EntityMarkerClassTransformer.class);
    
    protected HashSet<String> transformedEntityClassNames = new HashSet<String>();
    
    protected HashSet<String> transformedNonEntityClassNames = new HashSet<String>();
    
    @Resource(name = "blDirectCopyIgnorePatterns")
    protected List<DirectCopyIgnorePattern> ignorePatterns = new ArrayList<DirectCopyIgnorePattern>();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // Lambdas and anonymous methods in Java 8 do not have a class name defined and so no transformation should be done
        if (className == null) {
            return null;
        }

        String convertedClassName = className.replace('/', '.');
        
        if (isIgnored(convertedClassName)) {
            return null;
        }

        try {
            ClassFile classFile = new ClassFile(new DataInputStream(new ByteArrayInputStream(classfileBuffer)));
            List<?> attributes = classFile.getAttributes();
            Iterator<?> itr = attributes.iterator();
            while (itr.hasNext()) {
                Object object = itr.next();
                if (AnnotationsAttribute.class.isAssignableFrom(object.getClass())) {
                    boolean containsTypeLevelAnnotation = containsTypeLevelPersistenceAnnotation(((AnnotationsAttribute) object).getAnnotations());
                    if (containsTypeLevelAnnotation) {
                        LOG.debug("Marking " + convertedClassName + " as transformed");
                        transformedEntityClassNames.add(convertedClassName);
                    } else {
                        LOG.debug("Marking " + convertedClassName + " as picked up by the transformer but not detected as an entity");
                        transformedNonEntityClassNames.add(convertedClassName);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(e);
            throw new IllegalClassFormatException("Unable to mark " + convertedClassName + " as transformed.");
        }
        
        // We don't need to transform anything, so we'll return null
        return null;
    }

    protected boolean containsTypeLevelPersistenceAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation.getTypeName().equals(Entity.class.getName())
                    || annotation.getTypeName().equals(Embeddable.class.getName())
                    || annotation.getTypeName().equals(MappedSuperclass.class.getName())) {
                return true;
            }
        }
        return false;
    }
    
    protected boolean isIgnored(String convertedClassName) {
        boolean isValidPattern = true;
        List<DirectCopyIgnorePattern> matchedPatterns = new ArrayList<DirectCopyIgnorePattern>();
        for (DirectCopyIgnorePattern pattern : ignorePatterns) {
            boolean isPatternMatch = false;
            for (String patternString : pattern.getPatterns()) {
                isPatternMatch = convertedClassName.matches(patternString);
                if (isPatternMatch) {
                    break;
                }
            }
            if (isPatternMatch) {
                matchedPatterns.add(pattern);
            }
            isValidPattern = !(isPatternMatch && pattern.getTemplateTokenPatterns() == null);
            if (!isValidPattern) {
                break;
            }
        }
        
        return !isValidPattern;
    }

    @Override
    public void compileJPAProperties(Properties props, Object key) throws Exception {
        // When performing the check that this class transformer does, JPA properties do not need modificiation
    }

    public HashSet<String> getTransformedEntityClassNames() {
        return transformedEntityClassNames;
    }

    public HashSet<String> getTransformedNonEntityClassNames() {
        return transformedNonEntityClassNames;
    }

    public List<DirectCopyIgnorePattern> getIgnorePatterns() {
        return ignorePatterns;
    }

    public void setIgnorePatterns(List<DirectCopyIgnorePattern> ignorePatterns) {
        this.ignorePatterns = ignorePatterns;
    }

}
