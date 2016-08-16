package org.octopus.dashboard.data;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import org.octopus.dashboard.domain.Message;
import org.springframework.stereotype.Service;

@Singleton
@Service
public class MessageDataBase {
	private Map<String, Message> map = new HashMap<String, Message>();

	@PostConstruct
	public void init() {
		Message msg1 = new Message("code1", "my code1 message");
		Message msg2 = new Message("code2", "my code2 message");
		map.put(msg1.getCode(), msg1);
		map.put(msg2.getCode(), msg2);
	}

	public Map<String, Message> getMessage() {
		return map;
	}
}
