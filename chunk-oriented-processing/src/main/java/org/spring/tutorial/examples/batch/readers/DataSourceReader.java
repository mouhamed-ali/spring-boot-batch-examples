package org.spring.tutorial.examples.batch.readers;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class DataSourceReader implements ItemReader<String> {

    private String[] messages = "This is a simple spring batch example".split(" ");

    private int count = 0;

    @Override
    public String read(){

        if (count < messages.length) {
            return messages[count++];
        } else {
            count = 0;
        }
        return null;
    }
}
