package springboot.elasticsearch.web.base;

import lombok.Data;

/**
 * <p>
 * 统一接口返回类型
 * </p>
 *
 */
@Data
public class ApiResponse {
	private int code;
	private String message;
	private Object data;
	private boolean more;

	public ApiResponse(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ApiResponse() {
		this.code = Status.SUCCESS.getCode();
		this.message = Status.SUCCESS.getMsg();
	}

	public static ApiResponse ofMessage(int code, String message) {
		return new ApiResponse(code, message, null);
	}

	public static ApiResponse ofSuccess(Object data) {
		return new ApiResponse(Status.SUCCESS.getCode(), Status.SUCCESS.getMsg(), data);
	}

	public static ApiResponse ofStatus(Status status) {
		return new ApiResponse(status.getCode(), status.getMsg(), null);
	}

}