package springboot.elasticsearch.web.base;

import lombok.Getter;

/**
 * <p>
 * 通用状态码
 * </p>
 *
 */
@Getter
public enum Status {
	SUCCESS(200, "OK"), BAD_REQUEST(400, "Bad Request"), NOT_FOUND(404, "Not Found"), INTERNAL_SERVER_ERROR(500, "Unknown Internal Error");

	private int code;
	private String msg;

	Status(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
