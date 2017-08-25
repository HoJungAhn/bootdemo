package com.skcc.demo.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements ApplicationListener<BusinessEvent>, ApplicationContextAware{
	private ApplicationContext applicationContext;
	
	private List<Map<String, List<Method>>> methodList = new ArrayList<Map<String, List<Method>>>();
	
	@Override
	public void onApplicationEvent(BusinessEvent event) {
		
		for(Map<String, List<Method>> methods : methodList) {
			for(String beanName : methods.keySet().toArray(new String[methods.size()])) {				
				for(Method method : methods.get(beanName)) {
					method.setAccessible(true);
					try {
						method.invoke(applicationContext.getBean(beanName), event.getSource());
					} catch (BeansException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
				}
			}
		}
	}
	
	public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
	    final List<Method> methods = new ArrayList<Method>();
	    Class<?> klass = type;
	    while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
	        // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
	        final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));       
	        for (final Method method : allMethods) {
	            if (method.isAnnotationPresent(annotation)) {
	                Annotation annotInstance = method.getAnnotation(annotation);
	                // TODO process annotInstance
	                methods.add(method);
	            }
	        }
	        // move to the upper class in the hierarchy in search for more methods
	        klass = klass.getSuperclass();
	    }
	    return methods;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		for(String beanName : applicationContext.getBeanDefinitionNames()) {
			Map <String, List<Method>> methodMap = new HashMap<String, List<Method>>();
			methodMap.put(beanName, getMethodsAnnotatedWith(applicationContext.getBean(beanName).getClass(), BusinessEventListener.class));
			methodList.add(methodMap);
		}
		System.out.println(methodList);
		
	}
}
