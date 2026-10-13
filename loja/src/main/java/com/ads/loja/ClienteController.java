package com.ads.loja;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ClienteController {

    // lista em memoria no lugar do banco de dados (TODO usar banco de dados)
    // os dados ficam guardados enquanto a API estiver rodando
    private List<Cliente> lista = new ArrayList<>();
    private int proximoCodigo = 1;

    // ao iniciar a API ja cadastra um cliente de exemplo (codigo 1)
    public ClienteController(){
        var obj = new Cliente();
        obj.setNome("Joao da Silva");
        obj.setEmail("joao@email.com");
        obj.setCpf("123.456.789-00");
        obj.setTelefone("(11) 99999-9999");
        obj.setEndereco("Rua das Flores, 100");
        obj.setSenha("123456");
        gravar(obj);
    }

    // GET - lista todos os clientes gravados
    @GetMapping("/api/cliente/")
    public List<Cliente> listar(){
        return lista;
    }

    // GET - carrega os dados de um cliente pelo codigo
    @GetMapping("/api/cliente/{codigo}")
    public Cliente carregar(@PathVariable("codigo") int id){
        for (Cliente c : lista) {
            if (c.getCodigo() == id) {
                return c;
            }
        }
        return null; // nao encontrado
    }

    // POST - grava um novo cliente
    @PostMapping("/api/cliente/")
    public String gravar(@RequestBody Cliente obj){
        obj.setCodigo(proximoCodigo);
        proximoCodigo++;
        lista.add(obj);
        return "Cliente gravado com sucesso " + obj.getNome() + " - codigo " + obj.getCodigo();
    }

    // PUT - altera os dados de um cliente existente
    @PutMapping("/api/cliente/{codigo}")
    public String alterar(@PathVariable("codigo") int id, @RequestBody Cliente obj){
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo() == id) {
                obj.setCodigo(id);
                lista.set(i, obj);
                return "Cliente " + id + " alterado com sucesso " + obj.getNome();
            }
        }
        return "Cliente " + id + " nao encontrado";
    }

    // DELETE - remove um cliente pelo codigo
    @DeleteMapping("/api/cliente/{codigo}")
    public String remover(@PathVariable("codigo") int id){
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo() == id) {
                lista.remove(i);
                return "Cliente " + id + " removido com sucesso";
            }
        }
        return "Cliente " + id + " nao encontrado";
    }

}
