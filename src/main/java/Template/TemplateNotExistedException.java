package Template;

@SuppressWarnings("serial")
public class TemplateNotExistedException extends RuntimeException {

	public TemplateNotExistedException(String message){
		super(message);
	}

	public TemplateNotExistedException(String message,Throwable cause){
		
		super(message,cause);
	}

	public TemplateNotExistedException(Throwable cause){
		
		super(cause);
	}
}
