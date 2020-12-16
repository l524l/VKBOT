package ru.mityushin.responder.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vk.api.sdk.objects.callback.messages.CallbackMessage;
import com.vk.api.sdk.objects.messages.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mityushin.responder.service.CallbackApiHandler;
import ru.mityushin.responder.service.CallbackService;

/**
 * Controller for VK API requests
 *
 * @author Dmitry Mityushin
 * @since 1.0
 */
@Controller
@RequestMapping(value = "/callbacks")
@RequiredArgsConstructor
public class CallbackController {
    private static final Logger LOG = LoggerFactory.getLogger(CallbackController.class);
    private final CallbackApiHandler callbackApiHandler;

    @PostMapping
    @ResponseBody
    public String handleCallback(@RequestBody String callbackDto) {
        Gson gson = new Gson();
        LOG.info("REQUEST:  " + callbackDto);
        callbackApiHandler.parse(callbackDto);
        return "ok";
    }
}
