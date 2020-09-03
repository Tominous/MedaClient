package de.felixeckert.medaclient.events;

import java.util.ArrayList;

public class Event {
	public Event call() {
		final ArrayList<EventData> dataList = EventManager.get(this.getClass());
		
		if (dataList != null) {
			for (EventData ed : dataList) {
				try {
					ed.target.invoke(ed.source, this);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return this;
	}
}
