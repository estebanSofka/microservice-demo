package org.example.application.scheduler.scheduler;

import org.example.business.GenerateROIUseCase;
import org.example.domain.command.GenerateROIUseCommand;
import org.example.domain.value.ROIRate;
import org.example.generic.business.IntegrationHandle;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;

@Configuration
@EnableScheduling
public class ROIScheduler {
    private final IntegrationHandle integrationHandle;
    private final GenerateROIUseCase useCase;

    public ROIScheduler(IntegrationHandle integrationHandle, GenerateROIUseCase useCase) {
        this.integrationHandle = integrationHandle;
        this.useCase = useCase;
    }

    // 0 0 * * * * hourly
    @Scheduled(cron = "0 * * * * *")
    public void scheduleTaskUsingCronExpression() {
        var roiRate = new ROIRate(0.01);
        System.out.println("Daily ROI Deposited");
        useCase.andThen(integrationHandle)
                .apply(Mono.just(new GenerateROIUseCommand(roiRate)))
                .subscribe();
    }
}
