import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Buffer extends JFrame implements Runnable {
    private JPanel painelPrincipal;
    private JLabel consumidor1;
    private JLabel consumidor2;
    private JLabel produtor1;
    private JLabel produtor2;
    private JLabel produtor3;
    private JLabel buffer;
    private JButton startButton;
    private JButton stopButton;
    static Vector vetor = new Vector();

    Semaphore semaphore = new Semaphore(5);
    Semaphore semaphoreProcess = new Semaphore(1);
    ConsumidorThread consumidorThread;
    ConsumidorThread consumidorThread2;
    ProdutorThread produtorThread;
    ProdutorThread produtorThread2;
    ProdutorThread produtorThread3;

    public Buffer() {
        criarComponentes();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                consumidorThread = new ConsumidorThread(1, semaphore, semaphoreProcess, Buffer.this);
                consumidorThread2 = new ConsumidorThread(2, semaphore, semaphoreProcess, Buffer.this);
                produtorThread = new ProdutorThread(1, semaphore, semaphoreProcess, Buffer.this);
                produtorThread2 = new ProdutorThread(2, semaphore, semaphoreProcess, Buffer.this);
                produtorThread3 = new ProdutorThread(3, semaphore, semaphoreProcess, Buffer.this);

                consumidorThread.start();
                consumidorThread2.start();
                produtorThread.start();
                produtorThread2.start();
                produtorThread3.start();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consumidorThread.setEmProducao(false);
                consumidorThread2.setEmProducao(false);
                produtorThread.setEmProducao(false);
                produtorThread2.setEmProducao(false);
                produtorThread3.setEmProducao(false);
            }
        });
    }

    public void atualizarProducao(int idProdutor) {
        switch (idProdutor) {
            case 1 -> produtor1.setText("Produtor 1 produzindo!");
            case 2 -> produtor2.setText("Produtor 2 produzindo!");
            case 3 -> produtor3.setText("Produtor 3 produzindo!");
        }
    }

    public void atualizarConsumo(int idConsumidor) {
        switch (idConsumidor) {
            case 1 -> consumidor1.setText("Consumidor 1 consumindo!");
            case 2 -> consumidor2.setText("Consumidor 2 consumindo!");
        }
    }

    public void atualizarProdutor(int idProdutor, int numero) {
        switch (idProdutor) {
            case 1 -> produtor1.setText("Produtor 1 produziu " + numero);
            case 2 -> produtor2.setText("Produtor 2 produziu " + numero);
            case 3 -> produtor3.setText("Produtor 3 produziu " + numero);
        }
        atualizarBuffer();
    }

    public void atualizarProdutorParado(int idProdutor) {
        switch (idProdutor) {
            case 1 -> produtor1.setText("Produtor 1 parado!");
            case 2 -> produtor2.setText("Produtor 2 parado!");
            case 3 -> produtor3.setText("Produtor 3 parado!");
        }
    }

    public void atualizarConsumidor(int idConsumidor) {
        switch (idConsumidor) {
            case 1 -> consumidor1.setText("Consumidor 1 consumiu!");
            case 2 -> consumidor2.setText("Consumidor 2 consumiu!");
        }
        atualizarBuffer();
    }

    public void atualizarConsumidorParado(int idConsumidor) {
        switch (idConsumidor) {
            case 1 -> consumidor1.setText("Consumidor 1 parado!");
            case 2 -> consumidor2.setText("Consumidor 2 parado!");
        }
    }

    public void atualizarBuffer() {
        buffer.setText("Buffer = " + vetor.toString());
    }

    public void criarComponentes() {

        buffer.setText("Buffer = " + vetor.toString());
        produtor1.setText("Produtor 1 - Parado");
        produtor2.setText("Produtor 2 - Parado");
        produtor3.setText("Produtor 3 - Parado");
        consumidor1.setText("Produtor 1 - Parado");
        consumidor2.setText("Produtor 2 - Parado");

        setContentPane(painelPrincipal);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void run() {
        if (!isVisible()) {
            setVisible(true);
        } else {
            System.out.println("Já está visível!");
        }
    }

    public static void main(String[] args) {
        Buffer programa = new Buffer();
        programa.run();
    }
}
