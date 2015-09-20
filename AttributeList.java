
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

//import com.sun.org.apache.bcel.internal.classfile.Attribute;

/**
 *
 * @author Sankalpa
 */

public class AttributeList 
{
    private Attributes[] attribute_Array; //declare AbstractAttribute type array
    private int attribute_Count; //decalre int type variable to store no of attributes
    private int MethodBytes;
    private String mCalls ="";
   
    
   /**
    * Construcotr method.
    */
    public AttributeList(DataInputStream dis, ConstantPool cp) throws IOException, InvalidConstantPoolIndex, CodeParsingException
    {
        attribute_Count = dis.readUnsignedShort(); //initialize the attribute_count by allocating certain integer value
        attribute_Array = new Attributes[attribute_Count]; //define the attribute array
        
        //System.out.println("Mehtod attribute count : "+attribute_Count);
        for (int k = 0; k < attribute_Count; k++)
        {
            attribute_Array[k] = Attributes.checkType(dis, cp); //check what type of attribute to store inside the attribute_array
            this.mCalls = this.mCalls +","+ Attributes.getMethodCalls(); 
            
            
        }
        for(int i=0;i<attribute_Count;i++)
        {

                MethodBytes = attribute_Array[i].returnBytes();
               // System.out.println(attribute_Array[i].returnBytes());
         
            
        }
    }
    
    public int getMethodBytes()
    {
        return this.MethodBytes;
    }
    public String getMethodCalss()
    {
    	return this.mCalls;
    }
    
  
}

