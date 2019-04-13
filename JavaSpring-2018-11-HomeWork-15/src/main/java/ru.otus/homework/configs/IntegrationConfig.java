package ru.otus.homework.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@IntegrationComponentScan
@Configuration
public class IntegrationConfig
{
    @Bean
    public QueueChannel itemsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel booksChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    // create default poller
    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow packagingFlow() {
        return IntegrationFlows.from("itemsChannel")
            .split()
            .handle("packagingService", "pack")
            .aggregate()
            .channel("booksChannel")
            .get();
    }
}
