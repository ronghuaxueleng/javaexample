package springboot.exceptionhandler.enums;

import lombok.Getter;

/**
 * 状态码
 *
 */
@Getter
public enum Code {
	SUCCESS(200);
	private Integer code;

	Code(Integer code) {
		this.code = code;
	}

}
