  import java.util.*;
import java.io.*;
/**
 * This class is use to print the classes and method in a reader friendly manner
 * The methods used to get the class details and method detail are called inside this class 
 * Details are formated and printed
 * (Requires Java 1.6.)
 *
 * @author Sankalpa
 */
public class PrintDetails
{
	private ConstantPool constantPool;
	private DataInputStream dis;
	private String tag;
	
	/**
	 * Constructor for the class
	 * Accept Constanpool object, datainputstream object and tag
	 */
	public PrintDetails(ConstantPool cp, DataInputStream distream, String tagx)
	{
		this.constantPool = cp;
		this.dis = distream;
		this.tag = tagx;
	}
	
	/**
	 * The details of the class and methods are printed
	 */
	public void print()throws IOException
	{
		ReadByteCode readbytecode = new ReadByteCode(this.constantPool);//object from read byte code is created
        System.out.println("---------------------------------------------");
        
        readbytecode.getAccessFlags(this.dis);//acces flags of the class
        readbytecode.getThisClass(this.dis);//name of the class
        readbytecode.getSupperClass(this.dis);//name of the supper class
        int interf = readbytecode.getInterfaceCount(this.dis);//interfaces implimented in the class
        readbytecode.getfiledCount(this.dis);//class fields
        
        
        
        ClassData clsData = readbytecode.getClassData();//class data object is created
        //class detail are ptinted. class name, supper class name if it is not java/lang/object
        //if there are implimented intefaces they are printed
        System.out.println(accessFlagSetter(clsData)+" class "+clsData.getClassName()+" "+extendedClassSetter(clsData)+" "+interfaceSetter(clsData)+"{");
        readbytecode.getMethodCount(this.dis, tag);// method count and method details are printed
        System.out.println("}");//endling curly brackets of the class
        System.out.print("Avarage number of bytes per method : ");
        System.out.format("%.2f%n", readbytecode.getAvgBytes());
       System.out.println("---------------------------------------------");
	}
	
	/**
	 * Hexadecimal byte vales for acces flags of the class are converted in to readble format
	 * Class Data object is taken as input parameter 
	 * Access flags for the class are returned as string
	 */
	private String accessFlagSetter(ClassData clsData)
	{
		switch(clsData.getAccessFlag())
		{
			case 33 : return "public";
			case 1537 : return "public interface";
			case 257 : return "public native";
			case 1057 : return "public abstract";
			default: return "";//return empty value if the access flags are not identified
		}
	}
	/**
	 * Intface are formated in to reader friendly format
	 * Class data object is acepeted as input parameteres
	 * interface names after impliments key word returned as input parameter
	 */
	private  String interfaceSetter(ClassData clsData)
	{
		String intf;
		if(clsData.getInterfaceCount() == 0)
		{
			intf = "";
		}
		else
		{
			intf = "implements "+clsData.getInterfaceNames().substring(2);
		}
		return intf;
	}
	
	/**
	 * Suppre class name are formated with extented keyword.
	 * Class data object is acepted as input parameter 
	 * Supper class names are oreder after the extended keyword
	 */
	private String extendedClassSetter(ClassData clsData)
	{
		String superClassName;
		if(clsData.getSupperClassName().equalsIgnoreCase("0"))
		{
			superClassName = "";
		}
		else
		{
			superClassName = "extends "+clsData.getSupperClassName();
		}
		return superClassName;
	}
	
}
