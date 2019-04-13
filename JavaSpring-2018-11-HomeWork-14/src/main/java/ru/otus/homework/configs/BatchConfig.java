package ru.otus.homework.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowJob;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.ReviewDao;
import ru.otus.homework.models.jpa.Book;
import ru.otus.homework.models.jpa.Review;
import ru.otus.homework.services.BookJpa2MongoTransformer;
import ru.otus.homework.services.ReviewJpa2MongoTransformer;

import javax.persistence.EntityManagerFactory;

@EnableBatchProcessing
@Configuration
public class BatchConfig
{
    private final Logger logger = LoggerFactory.getLogger("Batch");

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private BookDao bookDao;

    private ReviewDao reviewDao;

    @Autowired
    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                       BookDao bookDao, ReviewDao reviewDao)
    {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory =  stepBuilderFactory;
        this.bookDao = bookDao;
        this.reviewDao = reviewDao;

    }

    @Bean(destroyMethod = "")
    public JpaPagingItemReader<Book> bookItemReader(EntityManagerFactory entityManagerFactory)
    {
        return new JpaPagingItemReaderBuilder<Book>()
            .name("bookItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(10)
            .queryString("SELECT b FROM Book b")
            .build();
    }

    @Bean(destroyMethod = "")
    public JpaPagingItemReader<Review> reviewItemReader(EntityManagerFactory entityManagerFactory)
    {
        return new JpaPagingItemReaderBuilder<Review>()
            .name("reviewItemReader")
            .entityManagerFactory(entityManagerFactory)
            .pageSize(10)
            .queryString("SELECT r FROM Review r")
            .build();
    }

    @Bean
    public ItemProcessor<Book, ru.otus.homework.models.mongo.Book> bookItemProcessor()
    {
        return BookJpa2MongoTransformer::transform;
    }

    @Bean
    public ItemProcessor<Review, ru.otus.homework.models.mongo.Review> bookCommentItemProcessor()
    {
        return ReviewJpa2MongoTransformer::transform;
    }

    @Bean
    public RepositoryItemWriter<ru.otus.homework.models.mongo.Book> bookItemMongoWriter()
    {
        return new RepositoryItemWriterBuilder<ru.otus.homework.models.mongo.Book>()
            .repository(bookDao)
            .methodName("saveWithGenreAndAuthors")
            .build();
    }

    @Bean
    public RepositoryItemWriter<ru.otus.homework.models.mongo.Review> reviewItemMongoWriter()
    {
        return new RepositoryItemWriterBuilder<ru.otus.homework.models.mongo.Review>()
            .repository(reviewDao)
            .methodName("save")
            .build();
    }

    @Bean
    public Job migrateBooksJob(Step migrateBooks, Step migrateBooksReviews)
    {
        FlowJob job = (FlowJob) jobBuilderFactory.get("migrateBooksJob")
            .incrementer(new RunIdIncrementer())
            .flow(migrateBooks)
            .next(migrateBooksReviews)
            .end()
            .listener(new JobExecutionListener() {
                @Override
                public void beforeJob(JobExecution jobExecution) {
                    logger.info("Staring migration");
                }

                @Override
                public void afterJob(JobExecution jobExecution) {
                    logger.info("Stop migration");
                }
            })
            .build();
        job.setRestartable(true);

        return job;
    }

    @Bean
    public Step migrateBooks(JpaPagingItemReader<Book> bookItemReader,
                             ItemWriter<ru.otus.homework.models.mongo.Book> bookItemMongoWriter)
    {
        return stepBuilderFactory.get("migrateBooks")
            .<Book, ru.otus.homework.models.mongo.Book>chunk(2)
            .reader(bookItemReader)
            .processor(bookItemProcessor())
            .writer(bookItemMongoWriter)
            .allowStartIfComplete(true)
            .build();
    }

    @Bean
    public Step migrateBooksReviews(JpaPagingItemReader<Review> reviewItemReader,
                                    ItemWriter<ru.otus.homework.models.mongo.Review> reviewItemMongoWriter)
    {
        return stepBuilderFactory.get("migrateBooksReviews")
            .<Review, ru.otus.homework.models.mongo.Review>chunk(2)
            .reader(reviewItemReader)
            .processor(bookCommentItemProcessor())
            .writer(reviewItemMongoWriter)
            .allowStartIfComplete(true)
            .build();
    }
}
