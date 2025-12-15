package controller;

import model.Credito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.CreditoService;

import java.util.List;

@RestController
@RequestMapping("/api/creditos")
@CrossOrigin(origins = "http://localhost:4200")
public class CreditoController {

    private final CreditoService creditoService;

    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<Credito>> getCreditosPorNfse(@PathVariable String numeroNfse) {
        List<Credito> creditos = creditoService.buscarPorNumeroNfse(numeroNfse);

        if (creditos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<Credito> getCreditoPorNumero(@PathVariable String numeroCredito) {

        return creditoService.buscarPorNumeroCredito(numeroCredito)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}