package net.sparkbox.jmstest.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sparkbox.jmstest.entidade.Usuario;
import net.sparkbox.jmstest.jms.ProdutorDeMensagem;
 
 
@WebServlet("/test")
public class ServletCriaMensagem extends HttpServlet {
    private static final long serialVersionUID = -973490316445738120L;
 
    @Inject
    private ProdutorDeMensagem produtorDeMensagem;
 
    @Override
    protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
        System.out.println(">>>>Criando mensagem de teste..");
        produtorDeMensagem.enviarMensagem(new Usuario("Usuario Teste"));
    }
 
}
