package business;

public class ApplicationMessage {
    public static final String MESSAGE = "MENSAJE";
    public static final String WARNING = "AVISO";
    public static final String ERROR = "ERROR";
    //
    private String code;
    private String text;
    private String level;
    private String cause;
    private String action;
    
	public ApplicationMessage ( ) {
	}
	
    public ApplicationMessage ( String code, String text, String level ) {
		this.code = code;
		this.text = text;
		this.level = level;
	}
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	//
	public void setMessage ( String code, String text, String level ) {
		this.code = code;
		this.text = text;
		this.level = level;
	}
}
