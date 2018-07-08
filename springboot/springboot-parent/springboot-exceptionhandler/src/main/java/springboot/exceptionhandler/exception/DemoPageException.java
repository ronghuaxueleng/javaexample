package springboot.exceptionhandler.exception;

import lombok.Getter;

/**
 * 统一的页面异常处理
 * <p>
 * 要继承 RuntimeException
 * </p>
 */
@SuppressWarnings("serial")
@Getter
public class DemoPageException extends RuntimeException {
	public Integer code;

	public DemoPageException(Integer code, String message) {
		super(message);
		this.code = code;
	}
}
