/*
 * This file is part of Carbon, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2016, Jamie Mansfield <https://www.jamierocks.uk/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package uk.jamierocks.mana.carbon.service.exception;

import static com.google.common.base.Preconditions.checkNotNull;

import uk.jamierocks.mana.carbon.Carbon;

import java.util.Optional;

/**
 * Provides static access to the methods defined in {@link ExceptionService}.
 * In failure to retrieve the exception service, it uses a fallback.
 *
 * @author Jamie Mansfield
 * @since 1.1.0
 */
public final class ExceptionReporter {

    /**
     * The exception service to use when the service is unable to be fetched from the registry.
     */
    private static final ExceptionService FALLBACK = new SimpleExceptionService();

    /**
     * Handles reporting an {@link Exception}, with the default message.
     *
     * @param throwable The exception
     * @see ExceptionService#report(Throwable)
     * @since 1.1.0
     */
    public static void report(Throwable throwable) {
        report("Carbon has experienced an error!", throwable);
    }

    /**
     * Handles reporting an {@link Exception}, with a message.
     *
     * @param message The message
     * @param throwable The exception
     * @see ExceptionService#report(String, Throwable)
     * @since 1.1.0
     */
    public static void report(String message, Throwable throwable) {
        checkNotNull(message, "message is null!");

        final Optional<ExceptionService> exceptionService =
                Carbon.getCarbon().getServiceRegistry().provide(ExceptionService.class);
        if (exceptionService.isPresent()) {
            exceptionService.get().report(message, throwable);
        } else {
            FALLBACK.report(message, throwable);
        }
    }
}
