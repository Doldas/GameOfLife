package utils.reflection;
/**
 * Utility class for object creations by reflection
 * @author anton lekedal(doldas)
 */
public class ObjectCreatorUtil {
	/**
	 * Creates an object instance by its class-name
	 * @param <T>
	 * @param className
	 * @param args constructor arguments
	 * @return the object instance or null if the object creation fails.
	 */
    @SuppressWarnings("unchecked")
	public static <T> T createObject(String className,Object[] args) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?>[] params = new Class[args.length];
            for(int i = 0; i<args.length; i++) {
                params[i] = args[i].getClass();
            }
            return (T) clazz.getConstructor(params).newInstance(args);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Creates an object instance by its class-name
     * @param <T>
     * @param className
     * @param arg A single argument
     * @return the object instance or null if the object creation fails.
     */
    @SuppressWarnings("unchecked")
	public static <T> T createObject(String className,Object arg) {
           try {
                Class<?> clazz = Class.forName(className);
                return (T) clazz.getConstructor(arg.getClass()).newInstance(arg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }
    /**
     * Creates an object instance by its class-name
     * @param <T>
     * @param className
     * @return the object instance or null if the object creation fails.
     */
    @SuppressWarnings("unchecked")
	public static <T> T createObject(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (T) clazz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
