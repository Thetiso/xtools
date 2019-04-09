package simple.framework.helper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import simple.framework.annotation.Action;
import simple.framework.bean.Handler;
import simple.framework.bean.Request;

public class ControllerHelper {

	private static final Map<Request, Handler> ACTION_MAP =
			new HashMap<Request, Handler>();
	
	static {
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if (CollectionUtils.isNotEmpty(controllerClassSet)) {
			for (Class<?> controllerClass : controllerClassSet) {
				Method[] methods = controllerClass.getDeclaredMethods();
				if (ArrayUtils.isNotEmpty(methods)) {
					for (Method method : methods) {
						if (method.isAnnotationPresent(Action.class)) {
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							if (mapping.matches("\\w+:/\\w*")) {
								String[] array = mapping.split(":");
								if (array != null && array.length == 2) {
									String requestMethod = array[0];
									String requestPath = array[1];
									Request request = new Request(requestMethod, requestPath);
									Handler handler = new Handler(controllerClass, method);
									ACTION_MAP.put(request, handler);
								}
							}
						}
					}
				}
			}
		}
	}	
	
	public static Handler getHandler(String requestMethod, String requestPath) {
		Request requst = new Request(requestMethod, requestPath);
		return ACTION_MAP.get(requst);
	}
}
