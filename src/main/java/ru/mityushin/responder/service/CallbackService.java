package ru.mityushin.responder.service;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.mityushin.responder.dto.CallbackDto;

/**
 * Service designed to handle callback requests from VK API
 *
 * @author Dmitry Mityushin
 * @since 1.0
 */
public interface CallbackService {
    /**
     * Returns response needed by VK API
     *
     * @param callbackDto deserialized callback from request
     * @return string for {@code ResponseEntity}
     */
    String handleCallback(CallbackDto callbackDto) throws ClientException, ApiException;
}
