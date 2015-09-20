
/**
 *
 * @author Sankalpa
 */
import java.io.*;
import java.util.*;
//child class of AbstractAttribute class
public class CodeAttributes extends Attributes
{
    private int maxStack;
    private int maxLocals;
    private int codeLength = 0;
    private byte[] code;
    private int exceptionTableLength;
    private byte[] exceptionTable;
    private AttributeList attributeInfo;
    private ArrayList<Instruction> insList;
    public CodeAttributes(DataInputStream dis, int i, ConstantPool cp) throws IOException,
    InvalidConstantPoolIndex,
    CodeParsingException
    {   
        attribute_NameIndex = i;
        //attribute_Length = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        attribute_Length = dis.readInt();
        maxStack = dis.readUnsignedShort();
        maxLocals = dis.readUnsignedShort();
        //codeLength = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        codeLength = dis.readInt();
        code = new byte[(int)codeLength];
        dis.readFully(code);   
                 
        exceptionTableLength = dis.readUnsignedShort();
        exceptionTable = new byte[exceptionTableLength*8];
        dis.readFully(exceptionTable);
        attributeInfo = new AttributeList(dis, cp);
        CreateInstructionList();
        ArrayList<Instruction> a = getInstructList(); 
        //System.out.println(a +" \n");
        //System.out.println(a.get().getOperandString());
        
        
        
    }
// Populates the instruction list with instructions.
    public ArrayList<Instruction> CreateInstructionList() throws CodeParsingException
    {
        int curOffSet = 0;
        insList = new ArrayList<Instruction>();
        do
        {
            Instruction newInst = new Instruction(code, curOffSet);
            curOffSet += newInst.getSize();
            insList.add(newInst);
        }
    while (curOffSet < code.length);
    return insList;
    }
    public ArrayList<Instruction> getInstructList()
    {
        return insList;
    }
    public int getCodeLength()
    {
        return this.codeLength;
    }
}
