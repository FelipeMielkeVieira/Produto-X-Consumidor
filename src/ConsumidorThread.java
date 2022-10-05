import java.util.concurrent.Semaphore;

public class ConsumidorThread extends Thread implements Runnable {

    private int idConsumidor;
    private Semaphore semaphore, semaphoreProcess;
    private Buffer buffer;

    public ConsumidorThread(int idConsumidor, Semaphore semaphore, Semaphore semaphoreProcess, Buffer buffer) {
        this.idConsumidor = idConsumidor;
        this.semaphore = semaphore;
        this.semaphoreProcess = semaphoreProcess;
        this.buffer = buffer;
    }

    public void processar() {
        try {
            buffer.vetor.remove(0);
            buffer.atualizarConsumidor(idConsumidor);

            semaphore.release();
            semaphoreProcess.release();
            Thread.sleep((long) (Math.random() * 5000));
            buffer.atualizarConsumidorParado(idConsumidor);

            Thread.sleep((long) (Math.random() * 5000));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                if (semaphore.availablePermits() < 5 && semaphoreProcess.availablePermits() > 0) {
                    semaphoreProcess.acquire();
                    processar();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
