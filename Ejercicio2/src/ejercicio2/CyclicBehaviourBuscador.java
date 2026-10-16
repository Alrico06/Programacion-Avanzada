package ejercicio2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import jade.content.lang.sl.SLCodec;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CyclicBehaviourBuscador extends CyclicBehaviour 
{	
		
	@Override
	public void action() 
	{
		//esperar a que me llegue el request
		ACLMessage msg=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchOntology("ontologia"), MessageTemplate.MatchPerformative(ACLMessage.REQUEST)));
		
		
		//llamar a buscarCadena
		try {
			List<String> listaNoticias=buscarCadena((String)msg.getContentObject());
			
			
			//contestar con un inform
			ACLMessage msg2=msg.createReply();
			msg2.setPerformative(ACLMessage.INFORM);
			msg2.setContentObject((Serializable) listaNoticias);
			this.myAgent.send(msg2);
			
			
        	/*ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
        	aclMessage.addReceiver(msg.getSender());
            aclMessage.setOntology("ontologia");
            //el lenguaje que se define para el servicio
            aclMessage.setLanguage(new SLCodec().getName());
            //el mensaje se transmita en XML
            aclMessage.setEnvelope(new Envelope());
			//cambio la codificacion de la carta
			aclMessage.getEnvelope().setPayloadEncoding("ISO8859_1");
            //aclMessage.getEnvelope().setAclRepresentation(FIPANames.ACLCodec.XML); 
    		aclMessage.setContentObject((Serializable) listaNoticias);
    		this.myAgent.send(aclMessage);  
    		*/
			
		} catch (UnreadableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public List<String> buscarCadena(String cadena)
	{
		String sitios[]={"https://www.elmundo.es/index.html","https://www.salamanca24horas.com/sociedad","https://www.elpais.com"};
		URL url;
		List<String> lista=new ArrayList<String>();
		Scanner scanner;
		String temp;
		
		for(int i=0;i<sitios.length;i++)
		{
			try 
			{
				url=new URL(sitios[i]);
				scanner=new Scanner(url.openStream());
				
		        while(true)
		        {
					temp=scanner.findWithinHorizon(Pattern.compile("[^><\"]*"+cadena+"[^><\"]*"), 0);
		            
		            if(temp==null)
		                break;
		            
		            lista.add(temp);
		        }
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return lista;
	}	
}
