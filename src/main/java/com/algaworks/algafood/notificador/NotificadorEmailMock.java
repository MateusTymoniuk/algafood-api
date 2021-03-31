package com.algaworks.algafood.notificador;

import com.algaworks.algafood.model.Cliente;

@TipoNotificador(NivelUrgencia.NORMAL)
public class NotificadorEmailMock implements Notificador {

    private boolean caixaAlta;
    private String hostSmtp;

    public NotificadorEmailMock(String hostSmtp) {
        this.hostSmtp = hostSmtp;
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        if (caixaAlta) {
            mensagem = mensagem.toUpperCase();
        }

        System.out.printf("MOCK: Notificando %s pelo email %s pelo host %s: %s\n",
                cliente.getNome(), cliente.getEmail(), this.hostSmtp, mensagem);
    }

    public void setCaixaAlta(boolean caixaAlta) {
        this.caixaAlta = caixaAlta;
    }
}