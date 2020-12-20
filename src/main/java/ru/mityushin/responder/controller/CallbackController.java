package ru.mityushin.responder.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mityushin.responder.config.VKGroupActor;
import ru.mityushin.responder.service.CallbackApiHandler;

@Controller
@RequestMapping(value = "/callbacks")
@RequiredArgsConstructor
public class CallbackController {
    private static final Logger LOG = LoggerFactory.getLogger(CallbackController.class);
    private final CallbackApiHandler callbackApiHandler;
    private final VKGroupActor vkGroupActor;

    @PostMapping
    @ResponseBody
    public String handleCallback(@RequestBody String callbackDto) {
        Gson gson = new Gson();
        LOG.info("REQUEST:  " + callbackDto);
        JsonObject json = gson.fromJson(callbackDto, JsonObject.class);
        String type = json.get("type").getAsString();
        if (type.equalsIgnoreCase("confirmation")) {
            return vkGroupActor.getConfirmation();
        }else {
            callbackApiHandler.parse(callbackDto);
            return "ok";
        }
    }
}
