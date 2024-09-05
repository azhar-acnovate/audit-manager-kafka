package AuditManager.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConsumerController {

    @Autowired
    ConsumerService consumerService;

    @GetMapping("/message")
    public List<String> getMessages(@RequestParam("subject") String subject) {
        switch (subject) {
            case "Parts":
                return consumerService.PartsList.isEmpty() ? null : consumerService.PartsList;
            case "Documents":
                return consumerService.DocumentList.isEmpty() ? null : consumerService.DocumentList;
            case "ChangeRequest":
                return consumerService.CRList.isEmpty() ? null : consumerService.CRList;
            case "ChangeNotice":
                return consumerService.CNList.isEmpty() ? null : consumerService.CNList;

        }
        return null;
    }



}
