package ejercicio2;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteBuscador extends Agent
{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void setup()
	{
		//registrar servicios
		DFAgentDescription dfd=new  DFAgentDescription();
		dfd.setName(getAID());
		
		ServiceDescription sd=new ServiceDescription();
		sd.setName("Buscador");
		sd.setType("buscar");
		sd.addOntologies("ontologia");
		sd.addLanguages(new SLCodec().getName());
		dfd.addServices(sd);
		
		//registar comportamientos
		
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		addBehaviour(new CyclicBehaviourBuscador());
	}

}
