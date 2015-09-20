import java.util.*;
import java.io.*;
/**
 *
 * (Requires Java 1.6.)
 *	Details of the class are taken to class variable and they are formated 
 *
 * @author Sankalpa
 */
public class MethodTemplate
{
	private int accessFlags;
	private String mehtodName;
	private int attribCount;
	private String attribName;
	private int attribLength;
	private String className;
	private String descripter;
	private String methodCalls;
	private String tag;
	private int methodBytes;
	
/**
 * Constructor for a class
 * This sets several class parameters
 */
	public MethodTemplate(int accflg, String mName, String descpt, int attCount, String attribName,int attribLenght, String cName)
	{
		this.accessFlags = accflg;
		this.attribCount = attCount;
		this.mehtodName = mName;
		this.attribLength = attribLenght;
		this.attribName = attribName;
		this.className = cName;
		this.descripter = descpt;
	}
	/**
	 * Another constructor for the class
	 */
	public MethodTemplate(String mName, String descpt, String classN, int accflg, String mCl,int methodbytes,String tagx)
	{
		this.mehtodName = mName;
		this.descripter = descpt;
		this.className = classN;
		this.accessFlags = accflg;
		this.methodCalls = mCl;
		this.tag = tagx;
		this.methodBytes = methodbytes;
	}
	public int getAccessFlags(){ return this.accessFlags; }
	public String getMehtodName(){ return this.mehtodName; }
	public int getAttribCount(){ return this.attribCount; }
	public String getAttribName(){ return this.attribName; }
	public int getAttribLength(){ return this.attribLength; }
	public String getClassName(){ return this.className; }
	public String getDescripter(){ return this.descripter; }
	
	
	/**
	 * This method prints the details of the methods
	 * Details of the method formated 
	 */
	public void printMethodData()
	{
		try{
			if(this.tag.equals("-m"))//command line parameter is checked. only the method signature are printed
			{
				System.out.println("");
				System.out.println("  "+methodNameSetter()+";"+ " //Number of bytes for method : "+this.methodBytes);
			}
			else//methods details and method calls are printed
			{
				
				String s[] = methodCalls.split(",");
				//System.out.println("aaaaa"+methodCalls.substring(2));
				System.out.println("");
				System.out.println("  "+methodNameSetter()+"{");
				for(String a: s)
				{
					if(!a.isEmpty())
						System.out.println("    "+a+"();");
				}
				System.out.println("  }"+" //Number of bytes for method : "+this.methodBytes);
				System.out.println("");
			}
		}catch(Exception ex)
		{
			System.out.println("Exception in MethodTemplate.java/printMethodData :"+ex.toString());
		}
	
	}
	/**
	 * This method set the name of the class
	 * If the name is <init> then method name is set as the constructor
	 */
	private String methodNameSetter()
	{
		String name="";
		//System.out.println(this.mehtodName);
		try{
			if(this.mehtodName.equalsIgnoreCase("<init>"))
				name = this.className;
			else
				name = returnTypeSetter()+" "+this.mehtodName;
			
			
		}catch(Exception ex)
		{
			System.out.println("Exception in MethodTemplate/mehtodNameSetter :"+ex.toString());
		}
		return accessFlagSetter()+" "+name+"("+descriptorSetter()+")";
	}
	/**
	 * Return types are identified and they are formated with name 
	 */
	private String returnTypeSetter()
	{
		String returnType="";
		//System.out.println(this.descripter);
		try{
			switch(this.descripter.charAt(this.descripter.length()-1))
			{
				case 'V' : returnType = "void"; break;
				case 'Z' : returnType = "boolean"; break;
				case 'I' : returnType = "int"; break;
				case 'D' : returnType = "double"; break;
				case 'S' : returnType = "String"; break;
				case 'C' : returnType = "char"; break;
				default: returnType = " ";
			}
		}catch(Exception ex)
		{
			System.out.println("Exception in MethodTemplate.java/returnTypeSetter : "+ex.toString());
		}
		return returnType;
	}
	
	/**
	 * Acces flags are taken in the form of hexadecimal 
	 */
	private String accessFlagSetter()
	{
		switch(this.accessFlags)
		{
			case 1 : return "public";
			case 9 : return "public static";
			case 8 : return "static";
			case 257 : return "public native";
			case 1025 : return "public abstract";
			default: return "";
		}
	}
	/**
	 * the desicriptor is formated and the detais are arranged in a reader friendly format
	 */
	private String descriptorSetter()
	{
		String descript="";
			try{
				if(this.descripter.charAt(1)=='L')
				{
					descript = this.descripter.substring(2, this.descripter.lastIndexOf(";"));
					descript = descript.replace('/', '.');
				}
				else
				{
					String dest="";
					for(int i=1; i < this.descripter.lastIndexOf(')');i++)
					{
						if(this.descripter.charAt(i)=='I')
							dest = dest +", "+ "int";
						else if(this.descripter.charAt(i)=='D')
							dest = dest +", "+ "double";
						else if(this.descripter.charAt(i)=='C')
							dest = dest +", "+ "char";
						else if(this.descripter.charAt(i)=='Z')
							dest = dest +", "+ "boolean";
						else if(this.descripter.charAt(i)=='S')
							dest = dest +", "+ "String";
						else
							dest = "  ";
						
						descript = dest.substring(2);
					}
				}
				
			}catch(Exception ex)
			{
				System.out.println("Exception in MethodTemplate.java/descriptorSetter : "+ex.toString());
			}

		return descript.replace('L', ' ').replace(';',',');
	}
	
	
}
