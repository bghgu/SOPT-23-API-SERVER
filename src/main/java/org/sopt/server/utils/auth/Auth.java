package org.sopt.server.utils.auth;

import java.lang.annotation.*;

/**
 * Created by ds on 2018-10-23.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Auth {
}
