package action.in.blog.controller;

import action.in.blog.client.MessageClient;
import action.in.blog.domain.MessageGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class RedisController {

    private final MessageClient messageClient;

    @GetMapping(value = {"", "/"})
    public String index(Model model) {
        model.addAttribute("unreadListSize", messageClient.getUnreadMessagesSize());
        return "index";
    }

    @PostMapping("/message")
    public String createMessage(Model model, @ModelAttribute("message") String message) {
        messageClient.pushMessage(message);
        model.addAttribute("unreadListSize", messageClient.getUnreadMessagesSize());
        return "index :: fragment";
    }

    @GetMapping("/unread-list/size")
    public String getUnreadListSize(Model model) {
        model.addAttribute("unreadListSize", messageClient.getUnreadMessagesSize());
        return "index :: fragment";
    }

    @GetMapping("/messages")
    public String messages(Model model) {
        MessageGroup messageGroup = messageClient.readMessageGroup();
        model.addAttribute("readMessages", messageGroup.getReadMessages());
        model.addAttribute("unreadMessages", messageGroup.getUnreadMessages());
        return "messages";
    }

    @PostMapping("/messages/flush")
    @ResponseBody
    public void flushMessages() {
        messageClient.flushUnreadMessages();
    }
}
