/*
 * Copyright 2017-2018 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example;

//import example.Building;
import io.micronaut.aop.InterceptedProxy;
import io.micronaut.context.BeanContext;
import io.micronaut.context.BeanResolutionContext;
import io.micronaut.context.exceptions.NoSuchBeanException;
import io.micronaut.context.scope.CustomScope;
import io.micronaut.core.annotation.AnnotationMetadata;
import io.micronaut.core.type.Argument;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.BeanFactory;
import io.micronaut.inject.BeanIdentifier;
import io.micronaut.inject.qualifiers.Qualifiers;
//import org.hibernate.Session;

import javax.inject.Provider;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Implementation of the {@link CurrentRoom} custom scope.
 *
 * @author graemerocher
 * @since 1.0
 */
@Singleton
public class CurrentRoomScope implements CustomScope<CurrentRoom> {

    private final BeanContext beanContext;
    private final Map<BeanIdentifier, Room> proxyBeans = new ConcurrentHashMap<>();

    /**
     * Default constructor.
     *
     * @param beanContext The bean context
     */
    protected CurrentRoomScope(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    @Override
    public Class<CurrentRoom> annotationType() {
        return CurrentRoom.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(BeanResolutionContext resolutionContext, BeanDefinition<T> beanDefinition, BeanIdentifier identifier, Provider<T> provider) {
        return (T) proxyBeans.computeIfAbsent(identifier, beanIdentifier -> {
            BeanResolutionContext.Segment segment = resolutionContext.getPath().currentSegment().orElseThrow(() ->
                    new IllegalStateException("@CurrentRoom used in invalid location")
            );
            Argument argument = segment.getArgument();
            AnnotationMetadata annotationMetadata = argument.getAnnotationMetadata();
            io.micronaut.context.Qualifier<Room> qualifier = annotationMetadata.getAnnotationNameByStereotype(Qualifier.class)
                    .map((Function<String, io.micronaut.context.Qualifier<Room>>) s -> Qualifiers.byAnnotation(annotationMetadata, s)).orElse(null);

            Optional<BeanDefinition<Room>> proxyBean = beanContext.getBeanDefinitions(Room.class, qualifier)
                    .stream()
                    .filter(BeanDefinition::isProxy)
                    .findFirst();

            if (proxyBean.isPresent()) {
                BeanDefinition<Room> beanDef = proxyBean.get();
                BeanFactory<Room> beanFactory = (BeanFactory<Room>) beanDef;
                Room sessionProxy = beanFactory.build(
                        resolutionContext,
                        beanContext,
                        beanDef
                );

                if (qualifier != null && sessionProxy instanceof InterceptedProxy) {
                    ((InterceptedProxy) sessionProxy).$withBeanQualifier(
                            qualifier
                    );
                }

                return sessionProxy;
            }
            throw new NoSuchBeanException(Room.class, qualifier);
        });

    }

    @Override
    public <T> Optional<T> remove(BeanIdentifier identifier) {
        return Optional.empty();
    }
}