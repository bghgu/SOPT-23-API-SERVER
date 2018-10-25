package org.sopt.server.utils;

import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.util.List;
import java.util.Optional;

/**
 * Created by ds on 2018-10-25.
 */

//class OptionalAwareObjectFactory extends DefaultObjectFactory {
//
//    public Object create(Class type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
//        if (Optional.class.isAssignableFrom(type)) {
//            return Optional.fromNullable(Iterables.getOnlyElement(constructorArgs));
//        } else {
//            return super.create(type, constructorArgTypes, constructorArgs);
//        }
//    }
//}