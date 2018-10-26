package org.sopt.server.model;

import lombok.*;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;

/**
 * Created by ds on 2018-10-23.
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultRes<T> {

    private int statusCode;

    private String responseMessage;

    private T responseData;

    public DefaultRes(final int statusCode, final String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.responseData = null;
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage) {
        return res(statusCode, responseMessage, null);
    }

    public static<T> DefaultRes<T> res(final int statusCode, final String responseMessage, final T t) {
        return DefaultRes.<T>builder()
                .responseData(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }

    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
}
