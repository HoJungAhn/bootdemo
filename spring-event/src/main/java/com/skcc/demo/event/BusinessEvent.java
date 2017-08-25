package com.skcc.demo.event;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class BusinessEvent extends ApplicationEvent {
	/** event group ,일반적으로 업무 대분류 사용 , ex) user,activity,reward 등등 */
	private final String eventGroup;
	/** event name, ex) join_user */
	private final String eventName;

	private final LocalDateTime createdTime = LocalDateTime.now();

	/**
	 * Create a new BusinessEvent.
	 *
	 * @param eventGroup
	 *            event group ,일반적으로 업무 대분류 사용 , ex) user,activity,reward 등등
	 * @param eventName
	 *            event name, ex) join_user
	 * @param source
	 *            the Map on which the event initially occurred (never
	 *            {@code null})
	 */
	public BusinessEvent(String eventGroup, String eventName, Map source) {
		super(source);
		this.eventGroup = eventGroup;
		this.eventName = eventName;
	}

	public String getEventGroup() {
		return eventGroup;
	}

	public String getEventName() {
		return eventName;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	@Override
	public String toString() {
		return "BusinessEvent{" + "eventGroup='" + eventGroup + '\'' + ", eventName='" + eventName + '\''
				+ ", createdTime=" + createdTime + ", source=" + source + '}';
	}
}
