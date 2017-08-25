package com.skcc.demo.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Created by alcava00 on 2017. 5. 18..
 *
 * <pre>
 *     sample code
 *    ----------- Event publish -----------
 *     @Autowired
 *     private org.springframework.context.ApplicationEventPublisher publisher;
 *
 *     Map userMap=new HashMap();
 *     .. ..
 *     BusinessEvent event=new BusinessEvent("user","join_user",userMap);
 *     publisher.publishEvent(event);
 *
 *    ----------- Event subscribe -----------
 *    @BusinessEventListener("user.join_member")
 *    public void welcome(Map map){
 *      System.out.println("hello2 >>>>>>>>>>>>>>>>>" +map.toString());
 *    }
 * </pre>
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessEventListener {
	/**
	 * 수신할 routingkey , ${BusinessEvent.eventGroup}.${BusinessEvent.eventName}
	 * 형식
	 */
	String value() default "";

	/** Number of concurrent consumers ,optional ,default 1 */
	int concurrentConsumers() default 1;

	/**
	 * The consumer uses a Thread Pool Executor with a fixed number of threads.
	 * This setting allows you to set that number of threads
	 */
	int threadPoolSize() default 10;

	/** Enables the quality of service on the RabbitMQConsumer side */
	boolean prefetchEnabled() default false;

	/*
	 * The maximum number of messages that the server will deliver, 0 if
	 * unlimited.
	 **/
	int prefetchCount() default 0;

}
