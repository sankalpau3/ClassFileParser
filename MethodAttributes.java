/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sankalpa
 */
import java.io.*;
//this is a child class of AbstractAttribute class
public class MethodAttributes extends Attributes
{
    private byte[] byte_info;
    public MethodAttributes(DataInputStream dis, int i,ConstantPool cp) throws IOException, InvalidConstantPoolIndex
    {
        attribute_NameIndex = i;
        //attribute_Length = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        attribute_Length = dis.readInt();
        byte_info = new byte[(int)attribute_Length];
        dis.readFully(byte_info);
        //System.out.println("Method attribute");
        //System.out.println( ((ConstantUtf8)cp.getEntry(attribute_NameIndex)).getBytes());
    }   
}
