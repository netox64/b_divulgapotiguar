package com.oficinadobrito.api.jobs;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.ProcessDataInfo;
import com.oficinadobrito.api.repositories.TarefaRepository;
import com.oficinadobrito.api.services.ImoveisService;
import com.oficinadobrito.api.utils.enums.StatusProcessamento;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class PdfProcessJob {


    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ImoveisService imoveisService;
    private final TarefaRepository tarefaRepository;

    public PdfProcessJob(JobRepository jobRepository, PlatformTransactionManager transactionManager, ImoveisService imoveisService, TarefaRepository tarefaRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.imoveisService = imoveisService;
        this.tarefaRepository = tarefaRepository;
    }

    public Job processarPdfJob(Long tarefaId, BigInteger imovelId) {
        @SuppressWarnings("unused")
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("tarefaId", tarefaId)
            .addString("imovelId", imovelId.toString())
            .toJobParameters();

        return new JobBuilder("processarPdfJob-" + tarefaId, jobRepository)
            .start(processarPdfStep(tarefaId, imovelId))
            .build();
    }

    @SuppressWarnings("unused")
    private Step processarPdfStep(Long tarefaId, BigInteger imovelId) {
        return new StepBuilder("processarPdfStep-" + tarefaId, jobRepository)
            .tasklet((contribution, chunkContext) -> {
                ProcessDataInfo tarefa = tarefaRepository.findById(tarefaId)
                    .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada para o id: " + tarefaId));

                tarefa.setStatus(StatusProcessamento.EM_PROCESSAMENTO);
                tarefaRepository.save(tarefa);

                try {
                    CompletableFuture<List<String>> futureTextoExtraido = imoveisService.extractTextFromPdfAsync(imovelId);
                    List<String> textoExtraido = futureTextoExtraido.get();

                    tarefa.setResultado(String.join("\n", textoExtraido));
                    tarefa.setStatus(StatusProcessamento.CONCLUIDO);
                } catch (InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                    tarefa.setStatus(StatusProcessamento.ERRO);
                    tarefa.setResultado("Erro: " + e.getMessage());
                }

                tarefaRepository.save(tarefa);
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }
}

