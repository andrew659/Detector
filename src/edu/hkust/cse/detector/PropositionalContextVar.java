package edu.hkust.cse.detector;
/**
 * @author andrew	Jun 16, 2011
 */
public class PropositionalContextVar<T> implements ContextType,ContextOperator {
	private short no;
	private String name;
	private short contextType;
	private short operator;
	private T target;
	public PropositionalContextVar(short pcvNo, String pcvName,short type,short op,T tar){
		this.no=pcvNo;
		this.name=pcvName;
		this.contextType=type;
		this.operator=op;
		this.target=tar;
	}
	public short getNo(){
		return this.no;
	}
	public void setNo(short pcvNo){
		this.no=pcvNo;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String pcvName){
		this.name=pcvName;
	}
	public short getType(){
		return this.contextType;
	}
	public void setType(short type){
		this.contextType=type;
	}
	public short getOperator(){
		return this.operator; 
	}
	public void setOperator(short op){
		this.operator=op;
	}
	public T getTarget(){
		return this.target;
	}
	public void setTarget(T tar){
		this.target=tar;
	}

}
