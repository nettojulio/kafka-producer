package br.com.julioneto.kafkaproducer;

import br.com.julioneto.kafkaproducer.services.KafkaProducerService;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("Enviando mensagens ...");
        Scanner keyboard = new Scanner(System.in);
        String mensagem;

        do {
            System.out.print("Digite sua mensagem: ");
            mensagem = keyboard.nextLine();
            KafkaProducerService.sendMessage(new Date().getTime() + "", mensagem);
        } while (!mensagem.equals("--exit_chat"));
    }
}
