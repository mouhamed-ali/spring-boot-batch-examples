package org.spring.tutorial.examples.batch.step;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.tutorial.examples.batch.constants.ApplicationConstants;
import org.spring.tutorial.examples.batch.dao.IOrderItemTotal;
import org.spring.tutorial.examples.batch.entities.OrderItemTotal;
import org.spring.tutorial.examples.batch.generic.AbstractStep;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowDataStep extends AbstractStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShowDataStep.class);
    private final IOrderItemTotal iOrderItemTotal;
    private List<OrderItemTotal> orderItemTotals;

    public ShowDataStep(IOrderItemTotal iOrderItemTotal) {
        this.iOrderItemTotal = iOrderItemTotal;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        super.beforeStep(stepExecution);
        ExecutionContext context = stepExecution.getJobExecution().getExecutionContext();
        orderItemTotals = (List<OrderItemTotal>) context.get(ApplicationConstants.ORDERS_PRICE);
    }

    @Override
    protected void executeJob(StepContribution stepContribution, ChunkContext chunkContext) {

        iOrderItemTotal.save(orderItemTotals);
        orderItemTotals = iOrderItemTotal.findAll();
        orderItemTotals.forEach(order -> LOGGER.info("{}", order));
    }

    @Override
    protected String getStepName() {
        return "ShowDataStep";
    }
}
