package springboot.exceptionhandler.enums;

import lombok.Getter;

/**
 * 返回状态
 *
 */
@Getter
public enum Status {
	OK(200, "成功"), UNKNOW_ERROR(-1, "未知错误");
	private Integer code;
	private String message;

	Status(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
