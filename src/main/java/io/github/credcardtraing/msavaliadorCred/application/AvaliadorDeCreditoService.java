package io.github.credcardtraing.msavaliadorCred.application;

import feign.FeignException;
import io.github.credcardtraing.msavaliadorCred.application.ex.DadosClienteNotFoundExecption;
import io.github.credcardtraing.msavaliadorCred.application.ex.ErroComunicacaoMicroServiceException;
import io.github.credcardtraing.msavaliadorCred.domain.model.*;
import io.github.credcardtraing.msavaliadorCred.infra.clients.CartoesResourceClient;
import io.github.credcardtraing.msavaliadorCred.infra.clients.ClientesResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorDeCreditoService {

    private final ClientesResourceClient resourceClient;
    private final CartoesResourceClient resourceCard;

    public SituacaoCliente obterSituacaoCliente(String cpf)
            throws DadosClienteNotFoundExecption, ErroComunicacaoMicroServiceException {

        try {
            // obeter dados do MSCLIENTES e MMCARTOES
            ResponseEntity<List<CartaoCliente>> dadosCartaoResponse = resourceCard.getCartoesByCliente(cpf);
            ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.dadosCliente(cpf);
            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(dadosCartaoResponse.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
           int status = e.status();
           if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundExecption();
            }
           throw new ErroComunicacaoMicroServiceException(e.getMessage(),status);
        }

    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundExecption, ErroComunicacaoMicroServiceException {
        try{
        ResponseEntity<DadosCliente> dadosClienteResponse = resourceClient.dadosCliente(cpf);
        ResponseEntity<List<Cartao>> cartoesResponse = resourceCard.getCartoesRendaAte(renda);
            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());

                var  fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteApriovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteApriovado);

                return aprovado;

            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundExecption();
            }
            throw new ErroComunicacaoMicroServiceException(e.getMessage(),status);
        }

    }


}
