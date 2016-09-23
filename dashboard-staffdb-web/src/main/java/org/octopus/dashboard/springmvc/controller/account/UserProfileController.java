package org.octopus.dashboard.springmvc.controller.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/account")
@Controller
public class UserProfileController {

	@RequestMapping(value = "profile")
	public String dashboard() {
		return "profile";
	}
}