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
        Message message = gson.fromJson("{\"date\":1608123144,\"from_id\":446899878,\"id\":549,\"out\":0,\"peer_id\":446899878,\"text\":\"ываыва\",\"conversation_message_id\":315,\"fwd_messages\":[],\"important\":false,\"random_id\":0,\"attachments\":[],\"is_hidden\":false}", Message.class);


        LOG.info("REQUEST:  " + callbackDto);
        callbackApiHandler.parse(callbackDto);
        return "d70aff5a";
    }
}
