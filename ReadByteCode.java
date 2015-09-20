import java.io.*;

/**
 * Parses and stores a Java .class file. Parsing is currently incomplete.
 *
 * @author Sankalpa
 */
public class ReadByteCode
{
	private int accessFlags;
	private CPEntry entry;
	private CPEntry entry1;
	private ConstantPool cp;
	private String thisClss;
	private ClassData clsData;
	private double avgMByteCount;
	/**
	 * The constructor.
	 * Get the constantpool object 
	 */
	public ReadByteCode(ConstantPool cpx) throws IOException
	{
		this.cp = cpx;
		clsData = new ClassData();
	}
	
	/**
	 * Acces flags for the method are descovered by this method 
	 * This method acept dataInputStream object as input parameter
	 * This method does not return any value
	 */
	public void getAccessFlags(DataInputStream dis)
	{
		try{
			this.accessFlags = dis.readUnsignedShort();
			clsData.setAccesFlags(this.accessFlags);
			//System.out.println("Access flags are "+ this.accessFlags);
		}
		catch(Exception ex){
			System.out.println("Exception in Readbytecode.java/getAccessFlags : "+ex.toString());
		}
	}
	/**
	 * The name of the class is identified 
	 * This method acept dataInputStream object as input parameter
	 * This method does not return any value
	 */
	public void getThisClass(DataInputStream dis)
	{
		
		try{
			int cpentryVal = dis.readUnsignedShort();
			entry1 =cp.getEntry(cpentryVal);
			String abc = entry1.getValues().substring(entry1.getValues().indexOf('=')+1);
			
			entry1 = cp.getEntryByIndexVal(abc);
			String className = entry1.getValues();
			className = className.substring(className.lastIndexOf('=')+2,className.lastIndexOf('"'));
			//System.out.println("This class name : "+className);
			thisClss = className;
			clsData.setClassName(className);//class name is passed to the classData object
		}
		catch(Exception ex){
			thisClss = " ";
			System.out.println("Exception in Readbytecode.java/getThisClass : "+ex.toString());
		}
	
	}
	/**
	 * The name of the supper class is recovered from this method
	 * This method acept dataInputStream object as input parameter
	 * This method does not return any value
	 */
	public void getSupperClass(DataInputStream dis)
	{
		
		try{
			int cpentryVal = dis.readUnsignedShort();
			entry1 =cp.getEntry(cpentryVal);
			String abc = entry1.getValues().substring(entry1.getValues().indexOf('=')+1);
			
			entry1 = cp.getEntryByIndexVal(abc);
			String className = entry1.getValues();
			className = className.substring(className.lastIndexOf('=')+2,className.lastIndexOf('"'));
			//System.out.println("Supper class name : "+className);
			clsData.setSupperClassName(className);//the supper class name is passed to the classData object
		
		}
		catch(Exception ex){
			System.out.println("Exception in Readbytecode.java/getSupperClass : "+ex.toString());
		}
		
	}
	/**
	 * The interface count and the names of the interfaces are recovered from this method
	 * This method acept dataInputStream object as input parameter
	 * Interface count is returned
	 */
	public int getInterfaceCount(DataInputStream dis)
	{
		try{
			int intCount = dis.readUnsignedShort();
			clsData.setInterfaceCount(intCount);
			//System.out.println("Interface Count : "+ intCount);
			if(intCount > 0)
			{
				for(int i=0; i<intCount; i++)
				{
					int interf = dis.readUnsignedShort();
					entry1 =cp.getEntry(interf);
					String abc = entry1.getValues().substring(entry1.getValues().indexOf('=')+1);
					entry1 = cp.getEntryByIndexVal(abc);
					String intName = entry1.getValues();
					intName = intName.substring(intName.lastIndexOf('=')+2,intName.lastIndexOf('"'));
					clsData.setInterfaceNames(intName);
					//System.out.println("Interface name : "+intName);
					
				}
			}
			return intCount;
		}
		catch(Exception ex){
			System.out.println("Exception in Readbytecode.java/getInterfaceCount : "+ex.toString());
			return -1;
		}
	}
	/**
	 * Field count is recovered 
	 * The names of the fields are recovered
	 * This method acept dataInputStream object as input parameter
	 * This method returns filedCount
	 */
	public int getfiledCount(DataInputStream dis)
	{		
			try{
				
				int fieldCount = dis.readUnsignedShort();
				
				//System.out.println("Field Count : "+ fieldCount);
				clsData.setFeildCount(fieldCount);
				if(fieldCount > 0)
				{
					for(int i=0; i<fieldCount; i++)
					{
						int fAccessFlags = dis.readUnsignedShort();
						int nameIndex = dis.readUnsignedShort();
						String nameIn = referCP(nameIndex);
						//System.out.println("Access flags : "+fAccessFlags+" , Name of the field : "+nameIn);
						
						int desriptIn = dis.readUnsignedShort();
						String descript = referCP(desriptIn);
						//System.out.println("The descriptor index is : "+descript);
						int attribCount = dis.readUnsignedShort();
						//System.out.println("The attrribute count is : "+attribCount);
						attribute_info(attribCount, dis);
						
					}
				}
				return fieldCount;
			}
			catch(Exception ex){
				System.out.println("Exception in Readbytecode.java/getfiledCount : "+ex.toString());
				return -1;
			}
	}
	
	/**
	 * Get the attributes of the method and skip the attribute info part 
	 * Attribute info will go for the code or Line nuber table.
	 * As per the basic requirerments code and line number table are not to impliment
	 */
	public void attribute_info(int count, DataInputStream dis)
	{
		try{
			if(count > 0)
			{
				for(int i=0; i < count; i++)
				{
					int attributeNameIndex = dis.readUnsignedShort();
					String atrributeName = referCP(attributeNameIndex);
					//System.out.println("Attribute name : "+atrributeName);
					long attribute_length = dis.readUnsignedShort()<< 16 | dis.readUnsignedShort();
					//System.out.println("attribut length : "+ attribute_length);
					dis.skipBytes((int)attribute_length);
					
				}
			}
		}
		catch(Exception ex)
		{
			//exceptions for attribute count
			System.out.println("Exception in Readbytecode.java/attribute_info : "+ex.toString());
		}
	}
	/**
	 * Get the method count.
	 * Get the parameters of the methods.
	 * This method acept dataInputStream objec and the tag input as the command line input (-m or -c)
	 */
	public void getMethodCount(DataInputStream dis, String tag)
	{
		
		try{
			int methodCount = dis.readUnsignedShort();
			int methodByteCount=0;
			//System.out.println("Method Count : "+ methodCount);
			clsData.setMethodCount(methodCount);// the number of method in the class is sent to the Class data object
			if(methodCount>0)
			{
				for(int i=0; i < methodCount; i++)
				{
					//System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++ Mehtod "+(i+1));
					int methodAccessflags = dis.readUnsignedShort();
					
					//System.out.println("Access flags : "+methodAccessflags);
					int nameIndex = dis.readUnsignedShort();
					String methodName = referCPMethods(nameIndex);
					
					int descriptorIndex = dis.readUnsignedShort();
					String descript = referCPMethods(descriptorIndex);
					//System.out.println("Method name :" + methodName + " "+ descript);
				
					AttributeList attribList = new AttributeList(dis, cp);//attribute list object is created. 
					//method details are passed to the method template object
					methodByteCount += attribList.getMethodBytes();//the total number of bytes for methods in the class are calculated
					MethodTemplate cltempl = new MethodTemplate(methodName,descript,this.thisClss,methodAccessflags, attribList.getMethodCalss(), attribList.getMethodBytes(),tag);
					
					
					cltempl.printMethodData();//details of each method is printed
					this.avgMByteCount = (double)methodByteCount/(double)methodCount;//the avarage number of bytes for a method is calculated
				}
			}
		}
		catch(Exception ex){
			System.out.println("Exception in Readbytecode.java/getMethodCount : "+ex.toString());
		}
	}
	/**
	 * This method is used to refer the constantpool and get the value
	 * This method return the value taken from the constantpool
	 * This method acept set of bytes as an int 
	 * This method refer constantpool for two time recursively
	 */
	private String referCP(int bytes)
	{
		//int bytes = dis.readUnsignedShort();
		try{
			entry1 =cp.getEntry(bytes);
			String abc = entry1.getValues().substring(entry1.getValues().indexOf('=')+1);
			entry1 = cp.getEntryByIndexVal(abc);
			String name = entry1.getValues();
			name = name.substring(name.lastIndexOf('=')+2,name.lastIndexOf('"'));
			return name;
		}
		catch(Exception ex)
		{
			//System.out.println("Exception in Readbytecode.java/referCP : "+ex.toString());
			return "Error";
		}
	}
	/**
	 * This method refer constantpool for one time
	 * This method acept set of bytes as int
	 * This method return the value related to the given constantpool entry as string
	 */
	private String referCPMethods(int bytes)
	{
		try{
			entry1 =cp.getEntry(bytes);
			String EntryVal = entry1.getValues().substring(entry1.getValues().lastIndexOf('=')+2,entry1.getValues().lastIndexOf('"'));
			return EntryVal;
		}
		catch(Exception ex)
		{
			System.out.println("Exception in Readbytecode.java/referCPMethods : "+ex.toString());
			return "Error";
		}
	}
	
	/**
	 * This method retures the classData object which has created privately in the class
	 */
	public ClassData getClassData()
	{
		return this.clsData;
	}
	/**
	 *This method returns the avarage number of bytes per method 
	 */
	public double getAvgBytes()
	{
		return this.avgMByteCount;
	}
}

