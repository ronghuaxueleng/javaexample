package springboot.exceptionhandler.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import springboot.exceptionhandler.enums.Code;
import springboot.exceptionhandler.enums.Status;
import springboot.exceptionhandler.exception.DemoJsonException;

/**
 * 统一返回的 json 对象
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
	private Integer code;
	private String message;
	private T data;

	public R(Status status) {
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static R success(Integer code, String message, Object data) {
		return new R(code, message, data);
	}

	@SuppressWarnings("rawtypes")
	public static R success() {
		return new R(Status.OK);
	}

	@SuppressWarnings("rawtypes")
	public static R success(String message) {
		return success(message, null);
	}

	@SuppressWarnings("rawtypes")
	public static R success(String message, Object data) {
		return success(Code.SUCCESS.getCode(), message, data);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static R error(Integer code, String message, Object data) {
		return new R(code, message, data);
	}

	@SuppressWarnings("rawtypes")
	public static R error(Integer code, String message) {
		return error(code, message, null);
	}

	@SuppressWarnings("rawtypes")
	public static R error(DemoJsonException exception) {
		return error(exception.getCode(), exception.getMessage());
	}
}
