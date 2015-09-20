import java.util.*;
import java.io.*;
/**
 * Represents a single JVM instruction, consisting of an opcode plus zero or
 * more extra bytes.
 *
 * (Requires Java 1.6.)
 *
 * @author Sankalpa
 */
public class ClassData
{
	private int accessFlag;
	private String className;
	private String supperClassName;
	private int interfaceCount;
	private String interfaceNames="";
	private int feildCount;
	private int methodCount;
	
	public ClassData(){
		
	
	}
	
	public void setAccesFlags(int accflg){this.accessFlag = accflg;}
	
	public void setClassName(String clsName){this.className= clsName;}
	
	public void setSupperClassName(String supClsName){this.supperClassName = supClsName;}
	
	public void setInterfaceCount(int intfCount){this.interfaceCount = intfCount;}
	
	public void setInterfaceNames(String intfName)
	{
		try{
			this.interfaceNames = this.interfaceNames+", "+intfName;
			
		}catch(Exception ex)
		{
			System.out.println("Exceptin in class data : "+ex.toString());
		}
	}
	
	public void setFeildCount(int fCount){this.feildCount = fCount;}
	
	public void setMethodCount(int mCount){this.methodCount = mCount;}
	

	public int getAccessFlag() { return accessFlag; }

    public String getClassName() { return className; }

    public String getSupperClassName() { 
    	
    	String returnSupper = this.supperClassName;
    	
    	if(this.supperClassName.equalsIgnoreCase("java/lang/Object"))
    		returnSupper ="0";
    	
    	return returnSupper; 
    
    }

    public int getInterfaceCount() {return interfaceCount; }

    public String getInterfaceNames() { return this.interfaceNames; }

    public int getFeildCount() { return feildCount; }

    public int getMethodCount() { return methodCount; }

	
}
