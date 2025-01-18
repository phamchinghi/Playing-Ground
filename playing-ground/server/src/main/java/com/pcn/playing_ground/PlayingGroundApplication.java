package com.pcn.playing_ground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

@EnableAutoConfiguration(exclude = {BatchAutoConfiguration.class})
@SpringBootApplication
public class PlayingGroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayingGroundApplication.class, args);
	}

}
