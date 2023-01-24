package io.github.credcardtraing.msavaliadorCred.application;

import io.github.credcardtraing.msavaliadorCred.domain.model.DadosCliente;
import io.github.credcardtraing.msavaliadorCred.domain.model.SituacaoCliente;
import io.github.credcardtraing.msavaliadorCred.infra.clients.ClientesResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorDeCreditoService {

    private final ClientesResourceClient resource;

    public SituacaoCliente obterSituacaoCliente(String cpf){
        // obeter dados do MSCLIENTES e MMCARTOES
        ResponseEntity<DadosCliente> dadosClienteResponse = resource.dadosCliente(cpf);
        return SituacaoCliente.builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }

}
