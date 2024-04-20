package com.LDLS.Litigation.Project;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppShutdownHandler implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private HikariDataSource dataSource;
    @PostConstruct
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing HikariDataSource...");
            dataSource.close();
            System.out.println("HikariDataSource closed.");
        }));
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        dataSource.close();
    }
}

