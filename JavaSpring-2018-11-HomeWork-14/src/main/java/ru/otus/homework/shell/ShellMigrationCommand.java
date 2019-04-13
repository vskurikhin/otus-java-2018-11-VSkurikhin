package ru.otus.homework.shell;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellMigrationCommand
{
    private JobLauncher jobLauncher;

    private Job migrateBooksJob;

    public ShellMigrationCommand(JobLauncher jobLauncher, Job migrateBooksJob)
    {
        this.jobLauncher = jobLauncher;
        this.migrateBooksJob = migrateBooksJob;
    }

    @ShellMethod("migration")
    public void migration()
    throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException,
           JobInstanceAlreadyCompleteException
    {
        jobLauncher.run(migrateBooksJob, new JobParametersBuilder().toJobParameters());
    }
}
