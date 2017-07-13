/**
 * 
 */
package net.sparkbox.jmstest.entidade;

import java.io.Serializable;

public final class Usuario implements Serializable {
    private static final long serialVersionUID = 8734596722276424601L;
 
    private final String nome;
 
    public Usuario(String nome) {
        this.nome = nome;
    }
 
    public String getNome() {
        return nome;
    }
 
}
