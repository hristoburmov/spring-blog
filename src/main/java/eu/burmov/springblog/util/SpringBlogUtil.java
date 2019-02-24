package eu.burmov.springblog.util;

public class SpringBlogUtil {
	
	// Constructors
	private SpringBlogUtil() {}
	
	// Constants
	public static final int ITEMS_PER_ADMIN_PAGE = 5;
	
	public static final int ITEMS_PER_PAGE = 3;
	
	// Methods
	public static Integer parseId(String idString) {
		try {
			return Integer.parseInt(idString);
		} catch(Exception e) {
			return null;
		}
	}

}
