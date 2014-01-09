// Nathan Fuller
import java.util.*;

public class Assignments implements AssignmentsInterface{
	//ArrayList<String> vars;
	//ArrayList<ResultList> rLists;
	ProjectMap<String,ResultList> aMap;
	
	public Assignments(){
		// vars = new ArrayList<String>();
		// rLists = new ArrayList<ResultList>();
		 aMap = new ProjectMap<String,ResultList>();
	}
	
	public boolean add(String variable, ResultList rList) {
		if(variable == null || rList == null)
			return false;
		aMap.put(variable, rList);
		return true;
		/*if(!vars.contains(variable)){
			vars.add(variable);
			rLists.add(vars.indexOf(variable), rList);
			return true;
		}
		else{
			rLists.set(vars.indexOf(variable),rList);
			return true;
		}*/
		
	}
	
	public ProjectMap<String,ResultList> getMap(){
		return aMap;
	}

	public ResultList getValue(String variable) {
		if(!aMap.containsKey(variable))
			return null;
		return aMap.get(variable);
		/*if(!vars.contains(variable))
			return null;
		return rLists.get(vars.indexOf(variable));*/
	}

}
