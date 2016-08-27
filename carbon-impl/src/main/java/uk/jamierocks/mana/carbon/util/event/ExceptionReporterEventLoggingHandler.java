/*
 * Copyright 2016 Jamie Mansfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.jamierocks.mana.carbon.util.event;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import uk.jamierocks.mana.carbon.service.exception.ExceptionReporter;
import uk.jamierocks.mana.carbon.service.exception.ExceptionService;

import java.lang.reflect.Method;

/**
 * The implementation of {@link SubscriberExceptionHandler} for Carbon's {@link ExceptionService}.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
public final class ExceptionReporterEventLoggingHandler implements SubscriberExceptionHandler {

    private static String message(SubscriberExceptionContext context) {
        Method method = context.getSubscriberMethod();
        return "Exception thrown by subscriber method "
                + method.getName() + '(' + method.getParameterTypes()[0].getName() + ')'
                + " on subscriber " + context.getSubscriber()
                + " when dispatching event: " + context.getEvent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        ExceptionReporter.report(message(context), exception);
    }

}
