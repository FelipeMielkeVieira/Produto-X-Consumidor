import java.util.concurrent.Semaphore;

public class ProdutorThread extends Thread implements Runnable {

    private Integer idProdutor;
    private Semaphore semaphore, semaphoreProcess;
    private Buffer buffer;
    private Boolean emProducao = true;

    public void setEmProducao(Boolean emProducao) {
        this.emProducao = emProducao;
    }

    public ProdutorThread(int idProdutor, Semaphore semaphore, Semaphore semaphoreProcess, Buffer buffer) {
        this.idProdutor = idProdutor;
        this.semaphore = semaphore;
        this.semaphoreProcess = semaphoreProcess;
        this.buffer = buffer;
    }

    public void processar() {
        try {
            buffer.atualizarProducao(idProdutor);
            Thread.sleep((long) (Math.random() * 5000));

            while(true) {
                if(semaphoreProcess.availablePermits() > 0) {
                    semaphoreProcess.acquire();
                    Integer numero = (int) (Math.random() * 100);
                    buffer.vetor.add(numero);
                    buffer.atualizarProdutor(idProdutor, numero);

                    semaphoreProcess.release();
                    break;
                }
            }
            Thread.sleep((long) (Math.random() * 5000));
            buffer.atualizarProdutorParado(idProdutor);

            Thread.sleep((long) (Math.random() * 5000));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void run() {
        while (emProducao) {
            try {
                if(semaphore.availablePermits() > 0 && semaphoreProcess.availablePermits() > 0) {
                    semaphore.acquire();
                    processar();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        buffer.atualizarProdutorParado(idProdutor);
    }
}
