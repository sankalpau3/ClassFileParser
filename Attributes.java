
import java.io.*;
import java.util.*;

/**
*
* @author Sankalpa
*/

/**
 * This class defines the structure for the method attributes
 */
public abstract class Attributes 
{
    protected int attribute_NameIndex;
    protected int attribute_Length;
    static int  MethodBytes; 
    private static CPEntry ce;
    private static String methodCalls="";
    //this gives the type of the object
    
    /**
     * the attibuteds are checke and if it is code the code is parsed to get the method calls in the methods.
     * 
     */
    public static Attributes checkType (DataInputStream dis,ConstantPool cp) throws IOException, InvalidConstantPoolIndex, CodeParsingException
    {
        int temp = dis.readUnsignedShort();
        String s = ((ConstantUtf8)cp.getEntry(temp)).getBytes();
        if (s.equals("Code"))//if attribute_name_index is code then its parsed with Code_attribute
        {
            CodeAttributes code = new CodeAttributes(dis, temp, cp);
            int bytes = code.getCodeLength();
            ArrayList<Instruction> insList= code.getInstructList();
            for(Instruction ins: insList)
            {
                Opcode a = ins.getOpcode();
                
                if(a.toString().equalsIgnoreCase("invokevirtual")|a.toString().equalsIgnoreCase("invokespecial")|a.toString().equalsIgnoreCase("invokeinterface")|a.toString().equalsIgnoreCase("invokestatic"))
                {
                   
                    byte f [] = ins.getExtraBytes();  
                    int v = ins.getOperand();
                    if(v!=0)
                    {
                        ce = cp.getEntry(v);
                        //System.out.println(ce);
                        ConstantMethodRef cmf = (ConstantMethodRef) ce; 
                        //System.out.println(String.format("0x%08x",cmf.getNameAndTypeIndex()));
                        ce = cp.getEntry(cmf.getNameAndTypeIndex());
                        ConstantNameAndType cnt = (ConstantNameAndType) ce;
                        //System.out.println(String.format("0x%08x",cnt.getNameIndex()));
                        //System.out.println(((ConstantUtf8)cp.getEntry(cnt.getNameIndex())).getBytes());
                       String calls = ((ConstantUtf8)cp.getEntry(cnt.getNameIndex())).getBytes();
                        //System.out.println(calls);
                       setCallsList(calls);
                      // methodCalls = methodCalls+","+calls;
                       
                        
                    }
                    
                }
               
                
            }
            getBytes(bytes);
            return code;
        }
        else
        {
            return new MethodAttributes(dis, temp,cp);
        }
    }
    public static void getBytes(int bytes)
    {
        MethodBytes = bytes;
    }
    public int returnBytes()
    {
        return MethodBytes;
    }
    public CPEntry returnCPentry()
    {
        return ce;
    }
    public static String getMethodCalls()
    {
    	return methodCalls;
    }
    private static void setCallsList(String calls)
    {
    	methodCalls = methodCalls+","+calls;
    }
    
}
    
