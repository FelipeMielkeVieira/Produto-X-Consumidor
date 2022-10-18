import java.util.concurrent.Semaphore;

public class ConsumidorThread extends Thread implements Runnable {

    private int idConsumidor;
    private Semaphore semaphore, semaphoreProcess;
    private Buffer buffer;
    private Boolean emProducao = true;

    public void setEmProducao(Boolean emProducao) {
        this.emProducao = emProducao;
    }

    public ConsumidorThread(int idConsumidor, Semaphore semaphore, Semaphore semaphoreProcess, Buffer buffer) {
        this.idConsumidor = idConsumidor;
        this.semaphore = semaphore;
        this.semaphoreProcess = semaphoreProcess;
        this.buffer = buffer;
    }

    public void processar() {
        try {

            buffer.atualizarConsumo(idConsumidor);
            Thread.sleep((long) (Math.random() * 5000));

            while (true) {
                if(semaphoreProcess.availablePermits() > 0) {
                    semaphoreProcess.acquire();

                    buffer.vetor.remove(0);
                    buffer.atualizarConsumidor(idConsumidor);

                    semaphore.release();
                    semaphoreProcess.release();
                    break;
                }
            }
            Thread.sleep((long) (Math.random() * 5000));
            buffer.atualizarConsumidorParado(idConsumidor);

            Thread.sleep((long) (Math.random() * 5000));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void run() {
        while (emProducao) {
            try {
                if (semaphore.availablePermits() < 5 && semaphoreProcess.availablePermits() > 0) {
                    processar();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        buffer.atualizarConsumidorParado(idConsumidor);
    }
}
