package org.habv.mnemosyne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.habv.mnemosyne.model.Entry;
import org.habv.mnemosyne.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.mongo.config.annotation.web.http.EnableMongoHttpSession;

@SpringBootApplication
@EnableMongoHttpSession
public class MnemosyneApplication implements CommandLineRunner {

    @Autowired
    private EntryRepository entryRepository;

    public static void main(String[] args) {
        SpringApplication.run(MnemosyneApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("#################################################");
//        entryRepository.deleteAll();
//        List<String> tags = new ArrayList<>();
//        tags.add("tag1");
//        tags.add("tag2");
//        tags.add("tag3");
//        for (int i = 0; i < 5; i++) {
//            Entry entry = new Entry();
//            entry.setTitle("Title " + i);
//            entry.setContent("Content " + i);
//            entry.setCategory("test");
//            entry.setTags(tags);
//            entry.setDate(new Date());
//            entryRepository.save(entry);
//        }
//        System.out.println("#################################################");
    }
}
