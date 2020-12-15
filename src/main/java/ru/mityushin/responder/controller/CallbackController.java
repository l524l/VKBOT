package ru.mityushin.responder.controller;

import lombok.RequiredArgsConstructor;
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
    private final CallbackService callbackService;

    @PostMapping
    @ResponseBody
    public String handleCallback(@RequestBody String callbackDto) {
        CallbackApiHandler callbackApiHandler = new CallbackApiHandler(vkMessageSenderService);
        callbackApiHandler.parse(callbackDto);
        return "ok";
    }
}
