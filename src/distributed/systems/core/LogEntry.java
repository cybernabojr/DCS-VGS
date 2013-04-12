package distributed.systems.core;

import java.io.Serializable;
import java.net.InetSocketAddress;
import distributed.systems.gridscheduler.model.ControlMessage;
import distributed.systems.gridscheduler.model.Job;


public class LogEntry implements Serializable {

	private static final long serialVersionUID = 8054991011970570003L;
	private InetSocketAddress origin;
	//	private Long id; //Generic id can be JobID, ClusterID and GSID
	private String event; 
	private int[] clock;
	private Job job;

	public LogEntry(ControlMessage message){
		this.setOrigin(message.getInetAddress());
		this.setClock(message.getClock());
		if(message.getJob() != null)
			this.setJob(message.getJob());
		switch(message.getType().toString()){
			case "JobArrival":
				this.setEvent("JOB_ARRIVAL");
				break;
			case "JobStarted":
				this.setEvent("JOB_STARTED");
				break;
			case "JobCompleted":
				this.setEvent("JOB_COMPLETED");
				break;
			case "GSLogJobArrival":
				this.setEvent("JOB_ARRIVAL");
				break;
			case "GSLogJobStarted":
				this.setEvent("JOB_STARTED");
				break;
			case "GSLogJobCompleted":
				this.setEvent("JOB_COMPLETED");
				break;
			case "AddJobAck":
				this.setEvent("JOB_SENT");
				break;
			case "RestartRM":
				this.setEvent("RESTART_RM");
				break;
			case "RestartGS":
				this.setEvent("RESTART_GS");
				break;
			default: 		this.setEvent(message.getType().toString());
		}
	}

	public LogEntry(Job j, String event, int[] clock){
		this.setOrigin(null);
		this.setEvent(event);
		this.setJob(j);
		this.setClock(clock);
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int[] getClock() {
		return clock;
	}

	public void setClock(int[] clock2) {
		this.clock = clock2;
	}

	public InetSocketAddress getOrigin() {
		return origin;
	}

	public void setOrigin(InetSocketAddress origin) {
		this.origin = origin;
	}

	@Override
	public String toString(){
		String s = "[";

		if(clock != null){
			s += "VecClock: [ ";

			for(int i = 0; i< clock.length; i++)
				s+= clock[i] + ", ";

			s+= "]";
			if(origin != null)
				s += "Origin: " + origin.getHostName()+ ":"+origin.getPort() + " | ";

			//		if(id != null)
			//			s += "ID: " + id + " | ";

			if(event != null)
				s += "Event: " + event + " | "; 

			if(job != null)
				s += "Job: " + job.toString() + " | ";

		}

		s+= " ]";
		return s;

	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

}

