package io.github.credcardtraing.msavaliadorCred.application;

import feign.FeignException;
import io.github.credcardtraing.msavaliadorCred.application.ex.DadosClienteNotFoundExecption;
import io.github.credcardtraing.msavaliadorCred.application.ex.ErroComunicacaoMicroServiceException;
import io.github.credcardtraing.msavaliadorCred.domain.model.DadosAvaliacao;
import io.github.credcardtraing.msavaliadorCred.domain.model.RetornoAvaliacaoCliente;
import io.github.credcardtraing.msavaliadorCred.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorDeCreditoService avaliadorDeCreditoService;

    @GetMapping
    public String status() {
        return "ok";
    }


    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        try {
            SituacaoCliente situacaoCliente = avaliadorDeCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundExecption e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroServiceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }
    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) throws ErroComunicacaoMicroServiceException, DadosClienteNotFoundExecption {
        try {
           RetornoAvaliacaoCliente retornoAvaliacaoCliente =
                   avaliadorDeCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        }catch (FeignException.FeignClientException e){
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundExecption();
            }
            throw new ErroComunicacaoMicroServiceException(e.getMessage(),status);
        }
    }

}
