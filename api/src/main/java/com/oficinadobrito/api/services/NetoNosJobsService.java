package com.oficinadobrito.api.services;

import com.oficinadobrito.api.config.errosandexceptions.ResourceNotFoundException;
import com.oficinadobrito.api.entities.ProcessDataInfo;
import com.oficinadobrito.api.jobs.PdfProcessJob;
import com.oficinadobrito.api.repositories.TarefaRepository;
import com.oficinadobrito.api.utils.enums.StatusProcessamento;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class NetoNosJobsService {

    private final TarefaRepository tarefaRepository;
    private final JobLauncher jobLauncher;
    private final PdfProcessJob pdfProcessJob;

    public NetoNosJobsService(JobLauncher jobLauncher, TarefaRepository tarefaRepository, PdfProcessJob pdfProcessJob) {
        this.tarefaRepository = tarefaRepository;
        this.jobLauncher = jobLauncher;
        this.pdfProcessJob = pdfProcessJob;
    }

    public ProcessDataInfo iniciarProcessamento(BigInteger imovelId) {
        ProcessDataInfo tarefa = new ProcessDataInfo();
        tarefa.setImovelId(imovelId);
        tarefa.setStatus(StatusProcessamento.PENDENTE);
        tarefa = this.tarefaRepository.save(tarefa);

        // Criando JobParameters com parâmetros exclusivos
        JobParameters jobParameters = new JobParametersBuilder()
            .addLong("processId", tarefa.getProcessId())
            .addString("imovelId", imovelId.toString())
            .toJobParameters();
        Job job = pdfProcessJob.processarPdfJob(tarefa.getProcessId(), imovelId);
        try {
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            tarefa.setStatus(StatusProcessamento.ERRO);
            tarefa.setResultado("Erro ao iniciar job: " + e.getMessage());
            this.tarefaRepository.save(tarefa);
        }
        return tarefa;
    }

    public ProcessDataInfo consultarStatus(Long id) {
        return this.tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não existe processo com esse ID na aplicação"));
    }
}
