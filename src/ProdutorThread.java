import java.util.concurrent.Semaphore;

public class ProdutorThread extends Thread implements Runnable {

    private int idProdutor;
    private Semaphore semaphore, semaphoreProcess;
    private Buffer buffer;

    public ProdutorThread(int idProdutor, Semaphore semaphore, Semaphore semaphoreProcess, Buffer buffer) {
        this.idProdutor = idProdutor;
        this.semaphore = semaphore;
        this.semaphoreProcess = semaphoreProcess;
        this.buffer = buffer;
    }

    public void processar() {
        try {
            buffer.vetor.add(idProdutor);
            buffer.atualizarProdutor(idProdutor);

            semaphoreProcess.release();
            Thread.sleep((long) (Math.random() * 5000));
            buffer.atualizarProdutorParado(idProdutor);

            Thread.sleep((long) (Math.random() * 5000));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                if(semaphore.availablePermits() > 0 && semaphoreProcess.availablePermits() > 0) {
                    semaphore.acquire();
                    semaphoreProcess.acquire();
                    processar();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
