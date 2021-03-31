package com.algaworks.algafood.notificador;

import com.algaworks.algafood.model.Cliente;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@TipoNotificador(NivelUrgencia.PRIORITARIO)
@Profile("prod")
public class NotificadorSMS implements Notificador {

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("Notificando %s pelo telelfone %s: %s\n",
                cliente.getNome(), cliente.getTelefone(), mensagem);
    }
}
