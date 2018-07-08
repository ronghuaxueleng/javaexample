package springboot.exceptionhandler.exception;

import lombok.Getter;

/**
 * 统一的 json 异常处理
 * <p>
 * 要继承 RuntimeException
 * </p>
 *
 */
@SuppressWarnings("serial")
@Getter
public class DemoJsonException extends RuntimeException {
	public Integer code;

	public DemoJsonException(Integer code, String message) {
		super(message);
		this.code = code;
	}
}
