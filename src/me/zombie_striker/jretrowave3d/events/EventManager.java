package me.zombie_striker.jretrowave3d.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager {

	public static List<Listener> listeners = new ArrayList<>();

	public static void registerEvents(Listener listener){
		listeners.add(listener);
	}
	public static void call(Event event){
		for(Listener listener : listeners){
			for(Method method : listener.getClass().getMethods()){
				if(method.getParameterTypes().length==1&&(method.getParameterTypes()[0].isInstance(event))){
					try {
						method.invoke(listener,event);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
