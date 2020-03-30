package bo.com.reportate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

	@RequestMapping("/")
	String home(ModelMap modal) {
		return "forward:/index.html";
	}
	@RequestMapping({"/authentication/login","/dashboards/principal","/settings/parameters",
			"/settings/domains"})
	String partialHandler(/*@PathVariable("page") final String page*/) {
		//return page;
		return "forward:/index.html";
	}

}
