import java.io.*;

/**
 * Parses and stores a Java .class file. Parsing is currently incomplete.
 *
 * @author David Cooper
 */
public class ClassFile
{
    private String filename;
    private long magic;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private ReadByteCode readbytecode;

    // ...

    /**
     * Parses a class file an constructs a ClassFile object. At present, this
     * only parses the header and constant pool.
     */
    public ClassFile(String filename, String tag) throws ClassFileParserException,
                                             IOException
    {
        DataInputStream dis =
            new DataInputStream(new FileInputStream(filename));

        this.filename = filename;
        magic = (long)dis.readUnsignedShort() << 16 | dis.readUnsignedShort();
        minorVersion = dis.readUnsignedShort();
        majorVersion = dis.readUnsignedShort();
        constantPool = new ConstantPool(dis);
       
        PrintDetails pd = new PrintDetails(this.constantPool, dis, tag);
        pd.print();

    }

    /** Returns the contents of the class file as a formatted String. */
    public String toString()
    {
        return String.format(
            "Filename: %s\n" +
            "Magic: 0x%08x\n" +
            "Class file format version: %d.%d\n\n" +
            "Constant pool:\n\n%s",
            filename, magic, majorVersion, minorVersion, constantPool);
    }
}

