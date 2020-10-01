import org.apache.commons.lang3.exception.ExceptionUtils;

class PrintException {
    public static void main(String[]args){
        System.out.println(ExceptionUtils.getStackTrace(new IllegalStateException()));
        System.out.println("Just printed a stacktrace, I'm fine actually");
    }
}
