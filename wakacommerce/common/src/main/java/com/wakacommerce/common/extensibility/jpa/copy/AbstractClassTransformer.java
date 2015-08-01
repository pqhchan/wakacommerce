
package com.wakacommerce.common.extensibility.jpa.copy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;

/**
 * This provides a useful mechanism to pre-load/initialize classes that are required by a child class during class transformation, 
 * but that may not have been loaded or initialized by the JVM.
 * 
 *  
 *
 */
public abstract class AbstractClassTransformer implements InitializingBean {

	protected static final Set<String> alreadyLoadedClasses = new HashSet<String>();
	protected List<String> preLoadClassNamePatterns = new ArrayList<String>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (preLoadClassNamePatterns != null && ! preLoadClassNamePatterns.isEmpty()) {
			synchronized (alreadyLoadedClasses) {
				for (String className : preLoadClassNamePatterns) {
					try {
						if (!alreadyLoadedClasses.contains(className)) {
							Class.forName(className);
							alreadyLoadedClasses.add(className);
						}
					} catch (ClassNotFoundException e) {
						throw new RuntimeException("Unable to force load class with name " + className + " in the DirectCopyClassTransformer", e);
					}
				}
			}
		}
	}
	
	/**
	 * Fully qualified list of class names to pre-load
	 * 
	 * @param fullyQualifiedClassNames
	 */
	public void setPreLoadClassNamePatterns(List<String> fullyQualifiedClassNames) {
    	this.preLoadClassNamePatterns = fullyQualifiedClassNames;
    }
}
