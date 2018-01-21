package supplychain.activiti.listener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sendOrderToMSC")
public class SendOrderToMSC implements TaskListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6111970011296715447L;
	@Autowired
	private RuntimeService runtimeService;
	
	@Override
	public void notify(DelegateTask dT) {
		// TODO Auto-generated method stub
		String mpid = dT.getProcessInstanceId();
		HashMap<String , Object>  msgData= (HashMap<String, Object>) runtimeService.getVariables(mpid);
		runtimeService.setVariable(mpid, "SparePartWeight", 3.9); //在Order的时候知道spare part weight
		/*********************************Send to VMC******************************************/
//		msgData.put("msgType" ,"Msg_FilterVPort");
//		runtimeService.startProcessInstanceByMessage("Msg_StartVMC" , msgData);
		
		
		/****************************Send to MSC**********************************************/
		//TODO : 将当前的Order数据发送给msMSC
		System.out.println("Send Ordder to Msc");
		UUID orderId = java.util.UUID.randomUUID();
		System.out.println("orderId : " + orderId);
		msgData.put("M_pid", mpid);
		msgData.put("OrderId", orderId.toString());
		msgData.put("msgType","Msg_StartSupplier");
		msgData.put("SparePartWeight" , 3.9);
		//启动Supplier Process
		runtimeService.startProcessInstanceByMessage("Msg_StartMSC" , msgData);
		System.out.println("MSC实例已启动");
	}
	
	

}