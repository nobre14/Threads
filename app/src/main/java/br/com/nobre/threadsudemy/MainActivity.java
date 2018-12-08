package br.com.nobre.threadsudemy;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button botaoIniciar;
    private Boolean pararExecucao = false;
    private int numero;
    private Handler handler = new Handler(); // perimte enviar código para ser executado em uma thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoIniciar = findViewById(R.id.btnIniciar);
    }

    public void iniciarThread(View view) {
        pararExecucao = false;
        MyRunnable runnable = new MyRunnable(); // Recomendado
        new Thread(runnable).start();

        /*MyThread thread = new MyThread();
        thread.start();*/
    }

    public void pararThread(View view) {
        pararExecucao = true;
    }

    // inner class

    class MyRunnable implements Runnable {  // recomendado

        @Override
        public void run() {

            //Handler handler = new Handler();

            for (numero = 0; numero <= 15; numero++) {
                if (pararExecucao) {
                    return; // quando não há retorno ele encerra a execução do metodo RUN
                }
                Log.d("Thread", "Contador: " + numero);

                runOnUiThread(new Runnable() { // envia para a Thread principal, todos que afetam a interface (Mais fácil)
                    @Override
                    public void run() {
                        botaoIniciar.setText("Contador " + numero);
                    }
                });

                /*handler.post(new Runnable() {
                    @Override
                    public void run() {
                        botaoIniciar.setText("Contador " + numero);
                    }
                });*/
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            for (int x = 0; x <= 15; x++) {
                Log.d("Thread", "Contador: " + x);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
