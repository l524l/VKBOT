package ru.mityushin.responder.commandsmodule;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;
import ru.mityushin.responder.service.VkMessageSenderService;

/**
 * @author Arthur Kupriyanov
 */
public class Help extends Command {

    public Help(VkMessageSenderService messageSenderService, String name) {
        super(messageSenderService, name);
    }

    @Override
    public void exec(Message message) {
        String s = "Список команд:\n" +
                ".move XYtoXY - перемещает шашку в соответствии с заданными координатами\n" +
                ".reset - сбрасывает игру\n" +
                ".remove XY - удаляет побитые шашки может принимать несколько пармаетров(Remove XY;XY;XY ...)\n" +
                ".help - вызвать это меню";
        message.setText(s);
        try {
            messageSenderService.send(message);
        } catch (ClientException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }
}
