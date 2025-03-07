package knitdiary.knitdiary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class knitDiaryController {

    @RequestMapping("/test")
    @ResponseBody
    public String testPage() {
        return "Testisivu";
    }
}
