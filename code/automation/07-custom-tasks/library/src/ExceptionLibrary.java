import org.apache.commons.lang3.exception.ExceptionUtils;

class ExceptionLibrary {
    public static void printStackTrace(Throwable throwable) {
        System.out.println(ExceptionUtils.getStackTrace(throwable));
    }
}
