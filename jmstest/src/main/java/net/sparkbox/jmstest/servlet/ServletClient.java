package net.sparkbox.jmstest.servlet;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sparkbox.jmstest.entidade.Usuario;

@WebServlet("/ServletClient")
public class ServletClient extends HttpServlet {
	
	 @Resource(mappedName = "jms/remoteCF")
	    private ConnectionFactory connectionFactory;
	 
	    @Resource(mappedName = "java:jboss/exported/jms/queue/ExampleQueue")
	    private Destination destination;
	 
	    private Connection connection;
	    private Session session;
	    private MessageProducer messageProducer;
	 
	    @PostConstruct
	    public void init() {
	        try {
	            connection = connectionFactory.createConnection();
	            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
	            messageProducer = session.createProducer(destination);
	            
	        } catch (JMSException e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }
	    }
	 
	    @PreDestroy
	    public void destroy() {
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (JMSException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	 
	    public void enviarMensagem(Usuario usuario) {
	        ObjectMessage message;
	        try {
	            message = session.createObjectMessage(usuario);
	            messageProducer.send(message);
	            //session.commit();
	        } catch (JMSException e) {
	            e.printStackTrace();
	        }
	    }
 
    
 
    private static final int MSG_COUNT = 5;
 
    @Inject
    @JMSConnectionFactory("jms/remoteCF")
    private JMSContext context;
 
    @Resource(lookup = "java:jboss/exported/jms/queue/ExampleQueue")
    private Queue queue;
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
 
        try {
       
             
            out.write("<h2>Following messages will be send to the destination:</h2>");
            for (int i = 0; i < MSG_COUNT; i++) {
                String text = "This is message " + (i + 1);
                context.createProducer().send(queue, text);
                out.write("Message (" + i + "): " + text + "</br>");
            }
            out.write("<p><i>Go to your JBoss EAP Server console or Server log to see the result of messages processing</i></p>");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
